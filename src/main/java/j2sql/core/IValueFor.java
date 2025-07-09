package j2sql.core;


import j2sql.definition.db.base.BaseDbField;

public interface IValueFor {
    BaseDbField getForDbField();
    String getValue();
}
