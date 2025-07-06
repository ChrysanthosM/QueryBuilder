package qb.core;


import qb.definition.db.base.BaseDbF;

public interface IValueFor {
    BaseDbF getForDbF();
    String getValue();
}
