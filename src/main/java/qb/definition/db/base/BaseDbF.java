package qb.definition.db.base;

import qb.core.IDeployFilters;
import qb.core.IDeployOrdering;
import qb.core.IProvideDataTypeForSQL;
import qb.definition.db.system.sqlite.schema.structure.DbFValues;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public interface BaseDbF extends IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    String systemName();
    DbFieldDataType fieldDataType();
    String asAlias();

    default String getSystemName() { return systemName(); }
    default DbFieldDataType getFieldDataType() { return fieldDataType(); }
    default String getAsAlias() { return asAlias(); }

    default List<String> getAcceptedValues() { return DbFValues.getValues(this); }

    default String getName() {
        if (this instanceof Enum<?>) {
            return ((Enum<?>) this).name();
        }
        return this.getClass().getSimpleName();
    }
    default BaseDbF[] getValues() {
        if (this instanceof Enum<?>) {
            Class<?> enumClass = this.getClass();
            try {
                Method valuesMethod = enumClass.getMethod("values");
                Object result = valuesMethod.invoke(null);
                if (result instanceof BaseDbF[] results) {
                    return results;
                }

                if (result instanceof Enum[]) {
                    Enum<?>[] enumArray = (Enum<?>[]) result;
                    return Arrays.stream(enumArray)
                            .map(e -> (BaseDbF) e)
                            .toArray(BaseDbF[]::new);
                }
            } catch (Exception e) {
                return new BaseDbF[]{this};
            }
        }

        return new BaseDbF[]{this};
    }
    default List<BaseDbF> getValuesList() {
        return Arrays.asList(getValues());
    }


    DummyALL dummyALL = new DummyALL();
    default BaseDbF ALL() { return dummyALL; }
}
