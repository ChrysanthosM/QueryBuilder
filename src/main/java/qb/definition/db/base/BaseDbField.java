package qb.definition.db.base;

import qb.core.IDeployFilters;
import qb.core.IDeployOrdering;
import qb.core.IProvideDataTypeForSQL;
import qb.definition.db.system.sqlite.schema.structure.DbFieldValuesSQLite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public interface BaseDbField extends IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    String systemName();
    DbFieldDataType fieldDataType();
    String asAlias();

    default String getSystemName() { return systemName(); }
    default DbFieldDataType getFieldDataType() { return fieldDataType(); }
    default String getAsAlias() { return asAlias(); }

    default List<String> getAcceptedValues() { return DbFieldValuesSQLite.getValues(this); }

    default String getName() {
        if (this instanceof Enum<?>) {
            return ((Enum<?>) this).name();
        }
        return this.getClass().getSimpleName();
    }
    default BaseDbField[] getValues() {
        if (this instanceof Enum<?>) {
            Class<?> enumClass = this.getClass();
            try {
                Method valuesMethod = enumClass.getMethod("values");
                Object result = valuesMethod.invoke(null);
                if (result instanceof BaseDbField[] results) {
                    return results;
                }

                if (result instanceof Enum[]) {
                    Enum<?>[] enumArray = (Enum<?>[]) result;
                    return Arrays.stream(enumArray)
                            .map(e -> (BaseDbField) e)
                            .toArray(BaseDbField[]::new);
                }
            } catch (Exception e) {
                return new BaseDbField[]{this};
            }
        }

        return new BaseDbField[]{this};
    }
    default List<BaseDbField> getValuesList() {
        return Arrays.asList(getValues());
    }


    DummyALL dummyALL = new DummyALL();
    default BaseDbField ALL() { return dummyALL; }
}
