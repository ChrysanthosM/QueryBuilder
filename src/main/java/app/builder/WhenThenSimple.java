package app.builder;

import jakarta.annotation.Nullable;
import lombok.NonNull;

final class WhenThenSimple extends WhenThen {
    private final Object whenCondition;

    WhenThenSimple(@NonNull Object whenCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.whenCondition = whenCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return "WHEN " + LInSQLBuilderShared.getSqlUserSelection(this.whenCondition).getResolveObjectForSQL(forSQLRetrieverForDB) +
                super.getThen(forSQLRetrieverForDB);
    }
}
