package qb.definition.db.sqlite.schema.table;

import org.springframework.stereotype.Component;
import qb.core.PairOfTableField;
import qb.core.TTable;
import qb.definition.db.sqlite.schema.structure.DbF;
import qb.definition.db.sqlite.schema.structure.DbT;

@Component
public final class TAutoNumbering extends TTable {
    private TAutoNumbering() {
        super(DbT.AUTO_NUMBERING);
        setDbFs(REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField ENTITY_TYPE = getPairOfTableField(DbF.ENTITY_TYPE);
    public final PairOfTableField ENTITY_NUMBER = getPairOfTableField(DbF.ENTITY_NUMBER);
}
