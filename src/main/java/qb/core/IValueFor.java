package qb.core;


import qb.definition.db.base.BaseDbField;

public interface IValueFor {
    BaseDbField getForDbField();
    String getValue();
}
