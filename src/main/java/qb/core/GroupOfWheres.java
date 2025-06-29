package qb.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class GroupOfWheres extends AbstractFilter {
    private final List<IWhere> whereFilters;

    GroupOfWheres(@Nonnull List<IWhere> whereFilters) {
        this.whereFilters = whereFilters;
    }

    @Override public void addParenthesisLeft() { ((IFilter) this.whereFilters.getFirst()).addParenthesisLeft(); }
    @Override public void addParenthesisRight() { ((IFilter) this.whereFilters.getLast()).addParenthesisRight(); }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        addParenthesisLeft();
        addParenthesisRight();
        List<String> whereFiltersForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, this.whereFilters, true);
        if (CollectionUtils.isEmpty(whereFiltersForSQL)) return null;

        StringBuilder returnValue = new StringBuilder();
        if (super.getTypeOfLogicalOperator() != null) returnValue.append(super.getTypeOfLogicalOperator().name()).append(StringUtils.SPACE);
        if (super.isInvertSelection()) returnValue.append("NOT").append(StringUtils.SPACE);
        returnValue.append(String.join(StringUtils.SPACE, whereFiltersForSQL));
        return returnValue.toString();
    }

    @Description("create Filters (enclosed in Parenthesis)")
    static IWhere getGroupOfFilters(@Nullable LinSQL.TypeOfLogicalOperator typeOfLogicalOperator, boolean invertSelection, @Nonnull IWhere... filters) {
        List<IWhere> whereList = Stream.of(filters).filter(Objects::nonNull).toList();
        if (CollectionUtils.isEmpty(whereList)) return null;
        GroupOfWheres wheres = new GroupOfWheres(whereList);
        if (typeOfLogicalOperator != null) wheres.setTypeOfLogicalOperator(typeOfLogicalOperator);
        wheres.setInvertSelection(invertSelection);
        return wheres;
    }
}
