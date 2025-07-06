package qb.definition.db.system.sqlite.schema.structure;

import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.BaseDbT;
import qb.definition.db.base.ConfigDbT;

import java.util.List;

public enum DbT implements BaseDbT {
    AUTO_NUMBERING("Sys_AutoNumbering", "AA", List.of(DbF.REC_ID), true, false),

    USERS("Sys_Users", "AC", List.of(DbF.REC_ID), false, false),
    OPTIONS("Sys_Options", "AB", List.of(DbF.REC_ID), true, true),
    ;

    private final ConfigDbT configDbT;
    DbT(String systemName, String tablePrefixForFields, List<BaseDbF> hasKeys, Boolean autoIncrease, Boolean putAutoStamp) {
        this.configDbT = new ConfigDbT(systemName, tablePrefixForFields, hasKeys, autoIncrease, putAutoStamp);
    }
    @Override
    public String systemName() { return this.configDbT.systemName(); }
    @Override
    public String tablePrefix() { return this.configDbT.tablePrefixForFields(); }
    @Override
    public List<BaseDbF> keys() { return this.configDbT.hasKeys(); }
    @Override
    public Boolean autoIncrease() { return this.configDbT.autoIncrease(); }
    @Override
    public Boolean putAutoStamp() { return this.configDbT.putAutoStamp(); }

    @Override
    public BaseDbF getUserStampDbF() { return DbF.USER_STAMP; }
    @Override
    public BaseDbF getDateStampDbF() { return DbF.DATE_STAMP; }
}
