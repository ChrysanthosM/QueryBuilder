package qb.core;

import lombok.Setter;

abstract sealed class WhenThen implements WhenBase, ResolveObjectForSQLBase
        permits WhenThenSearched, WhenThenSimple {

    private final Object thenExpression;
    @Setter private Boolean inQuotesRequirement;

    protected WhenThen(Object thenExpression) {
        this.thenExpression = thenExpression;
    }

    protected String getThen(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return " THEN " + LInSQLBuilderShared.getSqlUserSelection(this.thenExpression, this.inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
}
