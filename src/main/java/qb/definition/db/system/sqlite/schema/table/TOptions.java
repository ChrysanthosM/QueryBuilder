package qb.definition.db.system.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.TTable;
import qb.definition.db.system.sqlite.schema.structure.DbFieldSQLite;
import qb.definition.db.system.sqlite.schema.structure.DbTableSQLite;

@Component
public final class TOptions extends TTable {
    public TOptions() {
        super(DbTableSQLite.OPTIONS);
        setDbFs(REC_ID, OPTION_TYPE, OPTION_NAME, OPTION_VALUE, OPTION_DETAILS);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbFieldSQLite.REC_ID);
    public final PairOfTableField OPTION_TYPE = getPairOfTableField(DbFieldSQLite.OPTION_TYPE);
    public final PairOfTableField OPTION_NAME = getPairOfTableField(DbFieldSQLite.OPTION_NAME);
    public final PairOfTableField OPTION_VALUE = getPairOfTableField(DbFieldSQLite.OPTION_VALUE);
    public final PairOfTableField OPTION_DETAILS = getPairOfTableField(DbFieldSQLite.OPTION_DETAILS);
    public final PairOfTableField USER_STAMP = getPairOfTableField(DbFieldSQLite.USER_STAMP);
    public final PairOfTableField DATE_STAMP = getPairOfTableField(DbFieldSQLite.DATE_STAMP);
}
