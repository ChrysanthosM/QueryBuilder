package app.builder;

import jakarta.annotation.Nullable;
import lombok.NonNull;

final class WhenThenSearched extends WhenThen {
    private final WhereBase searchCondition;

    WhenThenSearched(@NonNull WhereBase searchCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.searchCondition = searchCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return "WHEN " + BuildSQLWhereFilters.getResolveFilterForSQL(forSQLRetrieverForDB, this.searchCondition, true) +
                super.getThen(forSQLRetrieverForDB);
    }
}
