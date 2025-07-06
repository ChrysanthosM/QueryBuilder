package qb.core;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.system.EnumStructureFinder;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbFieldInstances {
    private static final ConcurrentHashMap<BaseDbF, DbField> mapFieldInstances = new ConcurrentHashMap<>();

    private final EnumStructureFinder enumFinder;
    public DbFieldInstances(EnumStructureFinder enumFinder) {
        this.enumFinder = enumFinder;
    }

    @PostConstruct
    public void init() {
        Set<Class<? extends Enum<?>>> enumClasses = enumFinder.findEnumClassesImplementing(BaseDbF.class);

        enumClasses.stream()
                .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants()))
                .map(BaseDbF.class::cast)
                .parallel()
                .forEach(f -> mapFieldInstances.put(f, new DbField(f)));
    }

    static DbField getMapTableInstance(BaseDbF forDbF) {
        return mapFieldInstances.getOrDefault(forDbF, null);
    }


}
