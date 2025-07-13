package app.builder.base.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public interface BaseDbTable {
    String systemName();
    String tablePrefix();
    List<BaseDbField> keys();
    Boolean autoIncrease();
    Boolean putAutoStamp();

    BaseDbField getUserStampDbF();
    BaseDbField getDateStampDbF();

    default String getSystemName() { return systemName(); }
    default String getTablePrefix() { return tablePrefix(); }
    default List<BaseDbField> getKeys() { return keys(); }
    default Boolean hasAutoIncrement() { return autoIncrease(); }
    default Boolean hasTimestamps() { return putAutoStamp() != null && putAutoStamp(); }

    default String getName() {
        if (this instanceof Enum<?>) {
            return ((Enum<?>) this).name();
        }
        return this.getClass().getSimpleName();
    }
    default BaseDbTable[] getValues() {
        if (this instanceof Enum<?>) {
            Class<?> enumClass = this.getClass();
            try {
                Method valuesMethod = enumClass.getMethod("values");
                Object result = valuesMethod.invoke(null);
                if (result instanceof BaseDbTable[] results) {
                    return results;
                }

                if (result instanceof Enum[]) {
                    Enum<?>[] enumArray = (Enum<?>[]) result;
                    return Arrays.stream(enumArray)
                            .map(e -> (BaseDbTable) e)
                            .toArray(BaseDbTable[]::new);
                }
            } catch (Exception e) {
                return new BaseDbTable[]{this};
            }
        }

        return new BaseDbTable[]{this};
    }
    default List<BaseDbTable> valuesList() {
        return Arrays.asList(getValues());
    }
}
