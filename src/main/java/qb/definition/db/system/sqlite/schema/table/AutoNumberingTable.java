package qb.definition.db.system.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.TTable;
import qb.definition.db.system.sqlite.schema.structure.DbFieldSQLite;
import qb.definition.db.system.sqlite.schema.structure.DbTableSQLite;

@Component
public final class AutoNumberingTable extends TTable {
    private AutoNumberingTable() {
        super(DbTableSQLite.AUTO_NUMBERING);
        setDbFs(REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbFieldSQLite.REC_ID);
    public final PairOfTableField ENTITY_TYPE = getPairOfTableField(DbFieldSQLite.ENTITY_TYPE);
    public final PairOfTableField ENTITY_NUMBER = getPairOfTableField(DbFieldSQLite.ENTITY_NUMBER);
}
