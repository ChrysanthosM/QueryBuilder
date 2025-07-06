package qb.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.BaseDbT;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
abstract sealed class DbTable implements IDbTable permits TTable {
    public static final BaseDbF ALL = BaseDbF.dummyALL;

    protected abstract BaseDbT getDbT();
    protected abstract String getSystemName();
    protected abstract String getTablePrefixForFields();
    protected abstract List<BaseDbF> getHasKeys();
    protected abstract Boolean getAutoIncrease();
    protected abstract Boolean getPutAutoStamp();

    protected abstract List<PairOfTableField> getDbFs();

    private DbTableInfo dbTableInfo = null;

    @PostConstruct
    private void loadTableInfo() {
        dbTableInfo = new DbTableInfo(this);
    }

    public Pair<DbTable, String> as(@Nonnull J2SQLShared.PFX asAlias) { return as(asAlias.name()); }
    public Pair<DbTable, String> as(@Nonnull String asAlias) { return Pair.of(this, asAlias); }
}
