package qb.core;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import qb.definition.db.sqlite.schema.structure.DbF;
import qb.definition.db.sqlite.schema.structure.DbT;


@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(DbT dbt, DbF dbf) { return new PairOfTableField(dbt, dbf); }

    private final DbT dbt;
    private final DbF dbf;
    private final DbField dbField;
    private PairOfTableField(DbT dbt, DbF dbf) {
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
