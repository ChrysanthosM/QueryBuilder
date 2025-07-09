package qb.core;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

final class BetweenValuesWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_BETWEEN; }

    private final Pair<Object, Object> betweenValues;

    BetweenValuesWhere(@NonNull Object whereObject, @NonNull Pair<Object, Object> betweenValues) {
        super(whereObject);
        this.betweenValues = betweenValues;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return super.whereObjectForSQL(forSQLRetrieverForDB) + LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getLeft(), super.getInQuotesRequirement()).getResolveObjectForSQL(forSQLRetrieverForDB) +
                " AND " +
                LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getRight(), super.getInQuotesRequirement()).getResolveObjectForSQL(forSQLRetrieverForDB) +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
    }
}
