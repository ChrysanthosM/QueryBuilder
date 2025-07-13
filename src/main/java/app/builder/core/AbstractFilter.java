package app.builder.core;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

abstract sealed class AbstractFilter implements WhereBase, ResolveObjectForSQLBase, FilterBase
        permits AbstractWhere, GroupOfWheres {

    private LinSQL.TypeOfLogicalOperator typeOfLogicalOperator = null;
    @Override public LinSQL.TypeOfLogicalOperator getTypeOfLogicalOperator() { return this.typeOfLogicalOperator; }
    @Override public void setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator typeOfLogicalOperator) { this.typeOfLogicalOperator = typeOfLogicalOperator; }

    private boolean invertSelection;
    @Override public boolean isInvertSelection() { return this.invertSelection; }
    @Override public void setInvertSelection(boolean invertSelection) { this.invertSelection = invertSelection; }

    @Getter private int parenthesisLeft = 0;
    @Getter private int parenthesisRight = 0;
    @Override public void addParenthesisLeft() { this.parenthesisLeft += 1; }
    @Override public void addParenthesisRight() { this.parenthesisRight += 1; }

    private WhereBase attachedFilter = null;
    @Override public WhereBase getAttachedFilters() { return this.attachedFilter; }
    public String resolveAttachedFilters(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        if (this.attachedFilter == null) return StringUtils.EMPTY;
        return StringUtils.SPACE.concat(((ResolveObjectForSQLBase) this.attachedFilter).getResolveObjectForSQL(forSQLRetrieverForDB));
    }


    @Override
    public WhereBase and(WhereBase attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.AND);
        this.attachedFilter = attachFilter;
        return this;
    }
    @Override
    public WhereBase or(WhereBase attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.OR);
        this.attachedFilter = attachFilter;
        return this;
    }

}
