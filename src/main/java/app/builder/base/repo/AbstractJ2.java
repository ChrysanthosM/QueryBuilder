package app.builder.base.repo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import app.ApplicationSQLRun;
import app.builder.base.datasource.WorkWithDataSource;
import app.builder.core.J2SQL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;

import static app.builder.base.repo.LoadParams.LOAD_TIMEOUT;

@Log
public abstract class AbstractJ2<E extends Enum<E>> {
    @Getter(AccessLevel.PROTECTED) private @Autowired WorkWithDataSource workWithDataSource;

    @Getter(AccessLevel.PRIVATE) private final Class<E> nameOfSQL;
    private final Map<E, J2SQL> bufferJ2SQLs = new ConcurrentHashMap<>();
    private final Map<E, String> bufferSQLs = new ConcurrentHashMap<>();
    private final Deque<Pair<E, CompletableFuture<J2SQL>>> loadBuffers = new ConcurrentLinkedDeque<>();

    @PersistenceContext
    private EntityManager entityManager;

    protected AbstractJ2(Class<E> nameOfSQL) { this.nameOfSQL = nameOfSQL; }

    protected void addLoader(E nameOfSQL, J2SQL j2SQL) { loadBuffers.add(Pair.of(nameOfSQL, CompletableFuture.supplyAsync(() -> j2SQL))); }

    public J2SQL getJ2SQL(E nameOfSQL) { return bufferJ2SQLs.getOrDefault(nameOfSQL, null); }
    public String getSQL(E nameOfSQL) { return bufferSQLs.getOrDefault(nameOfSQL, null); }
    public <T> Query getQuery(E nameOfSQL, Class<T> resultClass) { return entityManager.createNativeQuery(bufferSQLs.getOrDefault(nameOfSQL, null), resultClass); }

    @PostConstruct
    public void load() {
        long startLoadingTime = System.currentTimeMillis();
        loadBuffers();
        long loadingTime = System.currentTimeMillis() - startLoadingTime;
        log.log(Level.INFO, this.getClass().getSimpleName() + " loaded in " + loadingTime);
        ApplicationSQLRun.addLoadingTime(loadingTime);
    }

    private void loadBuffers() {
        List<Method> loaders = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(LoadJ2SQL.class))
                .toList();
        if (CollectionUtils.isNotEmpty(loaders)) {
            loaders.parallelStream().forEach(this::invokeThis);
        }
        if (loadBuffers.isEmpty()) return;

        loadBuffers.parallelStream().forEach(m -> {
            try {
                bufferJ2SQLs.put(m.getKey(), m.getValue().get(LOAD_TIMEOUT, TimeUnit.SECONDS));
                bufferSQLs.put(m.getKey(), bufferJ2SQLs.get(m.getKey()).getSQL());
                loadBuffers.remove(m);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void invokeThis(Method m) {
        try {
            m.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
