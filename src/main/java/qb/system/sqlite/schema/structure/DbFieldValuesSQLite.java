package qb.db.sqlite.schema.structure;

import qb.builder.ValueForBase;
import qb.base.builder.BaseDbField;
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
            dbfWithValues.parallelStream().filter(e -> e.isEnum() && ValueForBase.class.isAssignableFrom(e)).forEach(e -> {
                List<?> dbfValuesList = Arrays.asList(e.getEnumConstants());
                if (CollectionUtils.isNotEmpty(dbfValuesList)) {
                    dbfValuesList.parallelStream().forEach(dbf -> bufferValues.put(
                            ((ValueForBase) dbf).getForDbField(),
                            dbfValuesList.stream().map(v -> ((ValueForBase) v).getValue()).toList()));
                }
            });
        }
    }

    @Getter @AllArgsConstructor
    public enum ValuesForEntityType implements ValueForBase {
        TEMP_STUCK("E01"), SURROGATE_NUM("E02");
        private final DbFieldSQLite forDbField = DbFieldSQLite.ENTITY_TYPE;
        private final String value;
    }

    @Getter @AllArgsConstructor
    public enum ValuesForOptionType implements ValueForBase {
        SYS_PARAM("O01"), FORM_SETTING("O02");
        private final DbFieldSQLite forDbField = DbFieldSQLite.OPTION_TYPE;
        private final String value;
    }
}
