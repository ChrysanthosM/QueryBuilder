package qb.core;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.BaseDbT;


@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(BaseDbT dbt, BaseDbF dbf) { return new PairOfTableField(dbt, dbf); }

    private final BaseDbT dbt;
    private final BaseDbF dbf;
    private final DbField dbField;
    private PairOfTableField(BaseDbT dbt, BaseDbF dbf) {
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
