package j2sql.core;

import lombok.NonNull;
import jakarta.annotation.Nullable;

final class WhenThenSearched extends WhenThen {
    private final IWhere searchCondition;

    WhenThenSearched(@NonNull IWhere searchCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.searchCondition = searchCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return "WHEN " + BuildSQLWhereFilters.getResolveFilterForSQL(forSQLRetrieverForDB, this.searchCondition, true) +
                super.getThen(forSQLRetrieverForDB);
    }
}
