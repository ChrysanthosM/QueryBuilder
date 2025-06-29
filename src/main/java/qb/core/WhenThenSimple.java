package qb.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class WhenThenSimple extends WhenThen {
    private final Object whenCondition;

    WhenThenSimple(@Nonnull Object whenCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.whenCondition = whenCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return "WHEN " + LInSQLBuilderShared.getSqlUserSelection(this.whenCondition).getResolveObjectForSQL(forSQLRetrieverForDB) +
                super.getThen(forSQLRetrieverForDB);
    }
}
