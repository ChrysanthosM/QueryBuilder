package qb.core;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.definition.db.base.BaseDbTable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbTableInstances {
    private static final ConcurrentHashMap<BaseDbTable, DbTable> mapTableInstances = new ConcurrentHashMap<>();
    private final List<IDbTable> implementations;

    @Autowired
    private DbTableInstances(List<IDbTable> implementations) {
        this.implementations = List.copyOf(implementations);
    }
    @PostConstruct
    public void init() {
        this.implementations.parallelStream().forEach(dbT -> mapTableInstances.put(((DbTable) dbT).getBaseDbTable(), (DbTable) dbT));
    }


    static DbTable getMapTableInstance(BaseDbTable forDbT) {
        return mapTableInstances.getOrDefault(forDbT, null);
    }
}
