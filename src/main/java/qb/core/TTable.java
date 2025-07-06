package qb.core;

import lombok.AccessLevel;
import lombok.Getter;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.BaseDbT;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
public abstract non-sealed class TTable extends DbTable {
    private final BaseDbT dbT;
    private final String systemName;
    private final String tablePrefixForFields;
    private final List<BaseDbF> hasKeys;
    @Getter private final Boolean autoIncrease;
    private final Boolean putAutoStamp;

    private List<PairOfTableField> dbFs;

    protected TTable(BaseDbT dbT) {
        this.dbT = dbT;
        this.systemName = dbT.getSystemName();
        this.tablePrefixForFields = dbT.getTablePrefix();
        this.hasKeys = List.copyOf(dbT.getKeys());
        this.autoIncrease = dbT.autoIncrease();
        this.putAutoStamp = dbT.putAutoStamp();
    }

    protected void setDbFs(PairOfTableField... dbFs) { this.dbFs = List.of(dbFs); }

    protected PairOfTableField getPairOfTableField(BaseDbF forDbF) { return PairOfTableField.of(getDbT(), forDbF); }
}
