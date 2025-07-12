package qb.builder;


import qb.base.builder.BaseDbField;

public interface ValueForBase {
    BaseDbField getForDbField();
    String getValue();
}
