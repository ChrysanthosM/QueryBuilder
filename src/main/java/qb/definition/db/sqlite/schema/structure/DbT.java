package qb.definition.db.sqlite.schema.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor @Getter
public enum DbT {
    AUTO_NUMBERING("Sys_AutoNumbering", null, List.of(DbF.REC_ID), true, false),

    USERS("Sys_Users", "AC", List.of(DbF.REC_ID), false, false),
    OPTIONS("Sys_Options", "AB", List.of(DbF.REC_ID), true, true),
    ;

    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    private final Boolean autoIncrease;
    private final Boolean putAutoStamp;
}
