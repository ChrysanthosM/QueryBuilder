package qb.core;

import jakarta.annotation.Nullable;
import lombok.Getter;

import java.lang.reflect.Type;

abstract non-sealed class SqlUserSelection implements ISqlUserSelection {
    @Getter private boolean ignoreTableAsAlias = false;
    void setIgnoreTableAsAlias() { this.ignoreTableAsAlias = true; }

    private String hasPrefix = null;
    @Override public String getHasPrefix() { return this.hasPrefix; }
    @Override public void setHasPrefix(@Nullable String hasPrefix) { this.hasPrefix = hasPrefix; }

    private String asAlias = null;
    @Override public String getAsAlias() { return this.asAlias; }
    @Override public void setAsAlias(String asAlias) { this.asAlias = asAlias; }
}
