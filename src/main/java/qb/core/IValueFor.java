package qb.core;

import qb.definition.db.sqlite.schema.structure.DbF;

public interface IValueFor {
    DbF getForDbF();
    String getValue();
}
