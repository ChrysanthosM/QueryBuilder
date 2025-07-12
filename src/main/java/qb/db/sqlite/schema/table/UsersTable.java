package qb.db.sqlite.schema.table;

import qb.builder.AbstractTable;
import qb.builder.PairOfTableField;
import qb.db.sqlite.schema.structure.DbFieldSQLite;
import qb.db.sqlite.schema.structure.DbTableSQLite;
import org.springframework.stereotype.Component;

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
