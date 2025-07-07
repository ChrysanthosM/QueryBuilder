package qb.definition.db.system.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.AbstractTable;
import qb.definition.db.system.sqlite.schema.structure.DbFieldSQLite;
import qb.definition.db.system.sqlite.schema.structure.DbTableSQLite;

@Component
public final class UsersTable extends AbstractTable {
    public UsersTable() {
        super(DbTableSQLite.USERS);
        setDbFs(REC_ID, USER_NAME, USER_PASSWORD);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbFieldSQLite.REC_ID);
    public final PairOfTableField USER_NAME = getPairOfTableField(DbFieldSQLite.USER_NAME);
    public final PairOfTableField USER_PASSWORD = getPairOfTableField(DbFieldSQLite.USER_PASSWORD);
}
