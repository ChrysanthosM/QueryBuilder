package app.builder.core;

import jakarta.annotation.Nullable;

import java.lang.reflect.Type;

sealed interface SqlUserSelectionBase extends ResolveObjectForSQLBase, DeployFiltersBase
        permits SqlUserSelection {
    Type getTypeOfSelection();
    void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args);

    String getHasPrefix();
    void setHasPrefix(@Nullable String hasPrefix);

    String getAsAlias();
    void setAsAlias(@Nullable String asAlias);
}
