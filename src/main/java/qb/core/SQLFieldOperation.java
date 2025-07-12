package qb.core;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

final class SQLFieldOperation extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private Object object;
    private String operation;

    SQLFieldOperation(Object object, String operation) {
        super();
        init(null, operation, object);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String expression, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        this.object = args[0];
        this.operation = expression;
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        SqlUserSelection sqlUserSelection = LInSQLBuilderShared.getSqlUserSelection(this.object, false);
        String keepAlias = sqlUserSelection.getAsAlias();
        sqlUserSelection.setAsAlias(null);
        sqlUserSelection.setIgnoreTableAsAlias();
        return sqlUserSelection.getResolveObjectForSQL(forSQLRetrieverForDB) +
                StringUtils.SPACE + LinSQLCommons.asAliasMain(keepAlias) +
                StringUtils.SPACE + this.operation;
    }
}
