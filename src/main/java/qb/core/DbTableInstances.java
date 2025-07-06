package qb.core;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.definition.db.base.BaseDbT;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbTableInstances {
    private static final ConcurrentHashMap<BaseDbT, DbTable> mapTableInstances = new ConcurrentHashMap<>();
    private final List<IDbTable> implementations;

    @Autowired
    private DbTableInstances(List<IDbTable> implementations) {
        this.implementations = List.copyOf(implementations);
    }
    @PostConstruct
    public void init() {
        this.implementations.parallelStream().forEach(dbT -> mapTableInstances.put(((DbTable) dbT).getDbT(), (DbTable) dbT));
    }


    static DbTable getMapTableInstance(BaseDbT forDbT) {
        return mapTableInstances.getOrDefault(forDbT, null);
    }
}
