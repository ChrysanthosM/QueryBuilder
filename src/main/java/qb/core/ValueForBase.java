package qb.core;


import qb.definition.db.base.BaseDbField;

public interface ValueForBase {
    BaseDbField getForDbField();
    String getValue();
}
