package app.builder.core;


import app.builder.base.builder.BaseDbField;

public interface ValueForBase {
    BaseDbField getForDbField();
    String getValue();
}
