package qb.core;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import qb.definition.db.base.BaseDbField;
import qb.definition.db.base.BaseDbTable;


@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(BaseDbTable dbt, BaseDbField dbf) { return new PairOfTableField(dbt, dbf); }

    private final BaseDbTable dbt;
    private final BaseDbField dbf;
    private final DbField dbField;
    private PairOfTableField(BaseDbTable dbt, BaseDbField dbf) {
        this.dbt = dbt;
        this.dbf = dbf;
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbf);
    }

    public SQLFieldObject as(@Nonnull String asAlias) { return new SQLFieldObject(this, asAlias, null); }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.dbf.getFieldDataType().getInQuotesRequirement();
    }
}
