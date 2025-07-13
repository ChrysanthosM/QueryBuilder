package app.builder.core;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;

import java.lang.reflect.Type;

final class SQLFieldObject extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private Object object;

    SQLFieldObject(@Nullable Object object, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, object);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        Preconditions.checkElementIndex(0, args.length);
        Preconditions.checkNotNull(args[0]);
        this.object = args[0];
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return LInSQLBuilderShared.getSqlUserSelection(this.object, super.getAsAlias()).getResolveObjectForSQL(forSQLRetrieverForDB) ;
    }
}
