package qb.definition.db.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.TTable;
import qb.definition.db.sqlite.schema.structure.DbF;
import qb.definition.db.sqlite.schema.structure.DbT;

@Component
public final class TOptions extends TTable {
    public TOptions() {
        super(DbT.OPTIONS);
        setDbFs(REC_ID, OPTION_TYPE, OPTION_NAME, OPTION_VALUE, OPTION_DETAILS);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField OPTION_TYPE = getPairOfTableField(DbF.OPTION_TYPE);
    public final PairOfTableField OPTION_NAME = getPairOfTableField(DbF.OPTION_NAME);
    public final PairOfTableField OPTION_VALUE = getPairOfTableField(DbF.OPTION_VALUE);
    public final PairOfTableField OPTION_DETAILS = getPairOfTableField(DbF.OPTION_DETAILS);
    public final PairOfTableField USER_STAMP = getPairOfTableField(DbF.USER_STAMP);
    public final PairOfTableField DATE_STAMP = getPairOfTableField(DbF.DATE_STAMP);
}
