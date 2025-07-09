package qb.definition.db.system.sqlite.schema.structure;

import qb.core.IValueFor;
import qb.definition.db.base.BaseDbField;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbFieldValuesSQLite {
    private static final ConcurrentHashMap<BaseDbField, List<String>> bufferValues = new ConcurrentHashMap<>();
    public static List<String> getValues(@NonNull BaseDbField forField) {
        return bufferValues.getOrDefault(forField, null);
    }

    @PostConstruct
    public void init() {
        List<Class<?>> dbfWithValues = Arrays.asList(DbFieldValuesSQLite.class.getDeclaredClasses());
        if (CollectionUtils.isNotEmpty(dbfWithValues)) {
            dbfWithValues.parallelStream().filter(e -> e.isEnum() && IValueFor.class.isAssignableFrom(e)).forEach(e -> {
                List<?> dbfValuesList = Arrays.asList(e.getEnumConstants());
                if (CollectionUtils.isNotEmpty(dbfValuesList)) {
                    dbfValuesList.parallelStream().forEach(dbf -> bufferValues.put(
                            ((IValueFor) dbf).getForDbField(),
                            dbfValuesList.stream().map(v -> ((IValueFor) v).getValue()).toList()));
                }
            });
        }
    }

    @Getter @AllArgsConstructor
    public enum ValuesForEntityType implements IValueFor {
        TEMP_STUCK("E01"), SURROGATE_NUM("E02");
        private final DbFieldSQLite forDbField = DbFieldSQLite.ENTITY_TYPE;
        private final String value;
    }

    @Getter @AllArgsConstructor
    public enum ValuesForOptionType implements IValueFor {
        SYS_PARAM("O01"), FORM_SETTING("O02");
        private final DbFieldSQLite forDbField = DbFieldSQLite.OPTION_TYPE;
        private final String value;
    }
}
