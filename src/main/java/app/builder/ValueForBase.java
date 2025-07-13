package app.builder;


import app.base.builder.BaseDbField;

public interface ValueForBase {
    BaseDbField getForDbField();
    String getValue();
}
