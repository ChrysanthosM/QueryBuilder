package qb.definition.db.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.TTable;
import qb.definition.db.sqlite.schema.structure.DbF;
import qb.definition.db.sqlite.schema.structure.DbT;

@Component
public final class TUsers extends TTable {
    public TUsers() {
        super(DbT.USERS);
        setDbFs(REC_ID, USER_NAME, USER_PASSWORD);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField USER_NAME = getPairOfTableField(DbF.USER_NAME);
    public final PairOfTableField USER_PASSWORD = getPairOfTableField(DbF.USER_PASSWORD);
}
