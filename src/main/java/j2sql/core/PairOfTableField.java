package j2sql.core;

import lombok.NonNull;
import lombok.Getter;
import j2sql.definition.db.base.BaseDbField;
import j2sql.definition.db.base.BaseDbTable;


@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(BaseDbTable dbt, BaseDbField dbf) { return new PairOfTableField(dbt, dbf); }

    private final BaseDbTable baseDbTable;
    private final BaseDbField baseDbField;
    private final DbField dbField;
    private PairOfTableField(BaseDbTable baseDbTable, BaseDbField baseDbField) {
        this.baseDbTable = baseDbTable;
        this.baseDbField = baseDbField;
        this.dbField = DbFieldInstances.getMapTableInstance(this.baseDbField);
    }

    public SQLFieldObject as(@NonNull String asAlias) { return new SQLFieldObject(this, asAlias, null); }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.baseDbField.getFieldDataType().getInQuotesRequirement();
    }
}
