package qb.core;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

abstract sealed class AbstractFilter implements IWhere, IResolveObjectForSQL, IFilter
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

    private IWhere attachedFilter = null;
    @Override public IWhere getAttachedFilters() { return this.attachedFilter; }
    public String resolveAttachedFilters(SQLRetrieverForDBs forSQLRetrieverForDB) {
        if (this.attachedFilter == null) return StringUtils.EMPTY;
        return StringUtils.SPACE.concat(((IResolveObjectForSQL) this.attachedFilter).getResolveObjectForSQL(forSQLRetrieverForDB));
    }


    @Override
    public IWhere and(IWhere attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.AND);
        this.attachedFilter = attachFilter;
        return this;
    }
    @Override
    public IWhere or(IWhere attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.OR);
        this.attachedFilter = attachFilter;
        return this;
    }

}
