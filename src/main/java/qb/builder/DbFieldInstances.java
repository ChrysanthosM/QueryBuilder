package qb.builder;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import qb.base.builder.BaseDbField;
import qb.distribution.DistributionStructureFinder;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbFieldInstances {
    private static final ConcurrentHashMap<BaseDbField, DbField> mapFieldInstances = new ConcurrentHashMap<>();

    private final DistributionStructureFinder enumFinder;
    public DbFieldInstances(DistributionStructureFinder enumFinder) {
        this.enumFinder = enumFinder;
    }

    @PostConstruct
    public void init() {
        Set<Class<? extends Enum<?>>> enumClasses = enumFinder.findEnumClassesImplementing(BaseDbField.class);

        enumClasses.stream()
                .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants()))
                .map(BaseDbField.class::cast)
                .parallel()
                .forEach(f -> mapFieldInstances.put(f, new DbField(f)));
    }

    static DbField getMapTableInstance(BaseDbField forDbF) {
        return mapFieldInstances.getOrDefault(forDbF, null);
    }


}
