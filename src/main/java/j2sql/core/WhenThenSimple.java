package j2sql.core;

import lombok.NonNull;
import jakarta.annotation.Nullable;

final class WhenThenSimple extends WhenThen {
    private final Object whenCondition;

    WhenThenSimple(@NonNull Object whenCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.whenCondition = whenCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return "WHEN " + LInSQLBuilderShared.getSqlUserSelection(this.whenCondition).getResolveObjectForSQL(forSQLRetrieverForDB) +
                super.getThen(forSQLRetrieverForDB);
    }
}
