package qb.definition.db.sqlite.schema.structure;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import qb.core.IValueFor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DbFValues {
    private static final ConcurrentHashMap<DbF, List<String>> bufferValues = new ConcurrentHashMap<>();
    public static List<String> getValues(@Nonnull DbF forField) {
        return bufferValues.getOrDefault(forField, null);
    }

    @PostConstruct
    public void init() {
        List<Class<?>> dbfWithValues = Arrays.asList(DbFValues.class.getDeclaredClasses());
        if (CollectionUtils.isNotEmpty(dbfWithValues)) {
            dbfWithValues.parallelStream().filter(e -> e.isEnum() && IValueFor.class.isAssignableFrom(e)).forEach(e -> {
                List<?> dbfValuesList = Arrays.asList(e.getEnumConstants());
                if (CollectionUtils.isNotEmpty(dbfValuesList)) {
                    dbfValuesList.parallelStream().forEach(dbf -> bufferValues.put(
                            ((IValueFor) dbf).getForDbF(),
                            dbfValuesList.stream().map(v -> ((IValueFor) v).getValue()).toList()));
                }
            });
        }
    }

    @Getter @AllArgsConstructor
    public enum ValuesForEntityType implements IValueFor {
        TEMP_STUCK("E01"), SURROGATE_NUM("E02");
        private final DbF forDbF = DbF.ENTITY_TYPE;
        private final String value;
    }

    @Getter @AllArgsConstructor
    public enum ValuesForOptionType implements IValueFor {
        SYS_PARAM("O01"), FORM_SETTING("O02");
        private final DbF forDbF = DbF.OPTION_TYPE;
        private final String value;
    }
}
