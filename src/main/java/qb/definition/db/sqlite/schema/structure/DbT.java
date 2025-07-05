package qb.definition.db.sqlite.schema.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import qb.definition.db.base.BaseDbT;

import java.util.List;

@AllArgsConstructor
@Getter
public enum DbT implements BaseDbT {
    AUTO_NUMBERING("Sys_AutoNumbering", "AA", List.of(DbF.REC_ID), true, false),

    USERS("Sys_Users", "AC", List.of(DbF.REC_ID), false, false),
    OPTIONS("Sys_Options", "AB", List.of(DbF.REC_ID), true, true),
    ;

    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    private final Boolean autoIncrease;
    private final Boolean putAutoStamp;

    @Override
    public String systemName() { return this.systemName; }
    @Override
    public String tablePrefix() { return this.tablePrefixForFields; }
    @Override
    public List<DbF> keys() { return this.hasKeys; }
    @Override
    public Boolean autoIncrease() { return this.autoIncrease; }
    @Override
    public Boolean putAutoStamp() { return this.putAutoStamp; }
}
