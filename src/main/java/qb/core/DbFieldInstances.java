package qb.core;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import qb.definition.db.sqlite.schema.structure.DbF;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbFieldInstances {
    private static final ConcurrentHashMap<DbF, DbField> mapFieldInstances = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Arrays.stream(DbF.values()).parallel().forEach(f -> mapFieldInstances.put(f, new DbField(f)));
    }

    static DbField getMapTableInstance(DbF forDbF) {
        return mapFieldInstances.getOrDefault(forDbF, null);
    }
}
