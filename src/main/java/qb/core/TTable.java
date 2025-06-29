package qb.core;

import lombok.AccessLevel;
import lombok.Getter;
import qb.definition.db.sqlite.schema.structure.DbF;
import qb.definition.db.sqlite.schema.structure.DbT;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
public abstract non-sealed class TTable extends DbTable {
    private final DbT dbT;
    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    @Getter private final Boolean autoIncrease;
    private final Boolean putAutoStamp;

    private List<PairOfTableField> dbFs;

    protected TTable(DbT dbT) {
        this.dbT = dbT;
        this.systemName = dbT.getSystemName();
        this.tablePrefixForFields = dbT.getTablePrefixForFields();
        this.hasKeys = List.copyOf(dbT.getHasKeys());
        this.autoIncrease = dbT.getAutoIncrease();
        this.putAutoStamp = dbT.getPutAutoStamp();
    }

    protected void setDbFs(PairOfTableField... dbFs) { this.dbFs = List.of(dbFs); }

    protected PairOfTableField getPairOfTableField(DbF forDbF) { return PairOfTableField.of(getDbT(), forDbF); }
}
