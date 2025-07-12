package qb.builder;

import lombok.AccessLevel;
import lombok.Getter;
import qb.base.builder.BaseDbField;
import qb.base.builder.BaseDbTable;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
public abstract non-sealed class AbstractTable extends DbTable {
    private final BaseDbTable baseDbTable;
    private final String systemName;
    private final String tablePrefixForFields;
    private final List<BaseDbField> hasKeys;
    @Getter private final Boolean autoIncrease;
    private final Boolean putAutoStamp;

    private List<PairOfTableField> dbFs;

    protected AbstractTable(BaseDbTable baseDbTable) {
        this.baseDbTable = baseDbTable;
        this.systemName = baseDbTable.getSystemName();
        this.tablePrefixForFields = baseDbTable.getTablePrefix();
        this.hasKeys = List.copyOf(baseDbTable.getKeys());
        this.autoIncrease = baseDbTable.autoIncrease();
        this.putAutoStamp = baseDbTable.putAutoStamp();
    }

    protected void setDbFs(PairOfTableField... dbFs) { this.dbFs = List.of(dbFs); }

    protected PairOfTableField getPairOfTableField(BaseDbField forDbF) { return PairOfTableField.of(this.getBaseDbTable(), forDbF); }
}
