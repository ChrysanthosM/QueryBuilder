package app.builder;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

abstract sealed class AbstractWhere extends AbstractFilter
        permits BetweenValuesWhere, ExistsWhere, InSubSelectWhere, InValuesWhere, LikeValueWhere, ValueWhere {
    abstract TypeOfWhere getTypeOfWhere();

    private final Object whereObject;
    private Boolean inQuotesRequirement = null;

    protected AbstractWhere(Object whereObject) {
        this.whereObject = whereObject;
        if (this.whereObject instanceof ProvideDataTypeForSQLBase provideDataTypeForSQLBase) this.inQuotesRequirement = provideDataTypeForSQLBase.getInQuotesRequirement();

    }

    protected Boolean getInQuotesRequirement() { return this.inQuotesRequirement; }

    protected String resolveParenthesisLeft() { return (super.getParenthesisLeft() > 0) ? Strings.repeat("(", super.getParenthesisLeft()) : StringUtils.EMPTY; }
    protected String resolveParenthesisRight() { return (super.getParenthesisRight() > 0) ? Strings.repeat(")", super.getParenthesisRight()) : StringUtils.EMPTY; }


    protected String whereObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return whereObjectForSQL(forSQLRetrieverForDB, null); }
    protected String whereObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable String putComparisonClause) {
        StringBuilder returnValue = new StringBuilder();
        if (super.getTypeOfLogicalOperator() != null) returnValue.append(super.getTypeOfLogicalOperator().name().concat(StringUtils.SPACE));
        returnValue.append(resolveParenthesisLeft());

        if (this.whereObject != null) {
            SqlUserSelection userSelection = LInSQLBuilderShared.getSqlUserSelection(this.whereObject);
            userSelection.setIgnoreTableAsAlias();
            returnValue.append(userSelection.getResolveObjectForSQL(forSQLRetrieverForDB)).append(StringUtils.SPACE);
        }
        if (super.isInvertSelection()) returnValue.append("NOT").append(StringUtils.SPACE);
        returnValue.append(StringUtils.defaultIfBlank(getTypeOfWhere().getPutClause(), StringUtils.defaultString(putComparisonClause)));

        return returnValue.append(StringUtils.SPACE).toString();
    }
}
