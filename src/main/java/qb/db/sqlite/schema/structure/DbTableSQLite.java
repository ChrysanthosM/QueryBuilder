package qb.db.sqlite.schema.structure;

import qb.base.builder.BaseDbField;
import qb.base.builder.BaseDbTable;
import qb.base.builder.ConfigDbTable;

import java.util.List;

public enum DbTableSQLite implements BaseDbTable {
    AUTO_NUMBERING("Sys_AutoNumbering", "AA", List.of(DbFieldSQLite.REC_ID), true, false),

    USERS("Sys_Users", "AC", List.of(DbFieldSQLite.REC_ID), false, false),
    OPTIONS("Sys_Options", "AB", List.of(DbFieldSQLite.REC_ID), true, true),
    ;

    private final ConfigDbTable configDbTable;
    DbTableSQLite(String systemName, String tablePrefixForFields, List<BaseDbField> hasKeys, Boolean autoIncrease, Boolean putAutoStamp) {
        this.configDbTable = new ConfigDbTable(systemName, tablePrefixForFields, hasKeys, autoIncrease, putAutoStamp);
    }
    @Override
    public String systemName() { return this.configDbTable.systemName(); }
    @Override
    public String tablePrefix() { return this.configDbTable.tablePrefixForFields(); }
    @Override
    public List<BaseDbField> keys() { return this.configDbTable.hasKeys(); }
    @Override
    public Boolean autoIncrease() { return this.configDbTable.autoIncrease(); }
    @Override
    public Boolean putAutoStamp() { return this.configDbTable.putAutoStamp(); }

    @Override
    public BaseDbField getUserStampDbF() { return DbFieldSQLite.USER_STAMP; }
    @Override
    public BaseDbField getDateStampDbF() { return DbFieldSQLite.DATE_STAMP; }
}
