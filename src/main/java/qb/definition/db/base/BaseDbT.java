package qb.definition.db.base;

import qb.definition.db.sqlite.schema.structure.DbF;

import java.util.List;

public interface BaseDbT {
    String systemName();
    String tablePrefix();
    List<DbF> keys();
    Boolean autoIncrease();
    Boolean putAutoStamp();

    default String getSystemName() { return systemName(); }
    default String getTablePrefix() { return tablePrefix(); }
    default List<DbF> getKeys() { return keys(); }
    default Boolean hasAutoIncrement() { return autoIncrease(); }
    default Boolean hasTimestamps() { return putAutoStamp() != null && putAutoStamp(); }
}
