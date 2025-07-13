package app.builder;

import com.google.common.base.Joiner;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.stream.Stream;

final class GroupOfWheres extends AbstractFilter {
    private final List<WhereBase> whereFilters;

    GroupOfWheres(@NonNull List<WhereBase> whereFilters) {
        this.whereFilters = whereFilters;
    }

    @Override public void addParenthesisLeft() { ((FilterBase) this.whereFilters.getFirst()).addParenthesisLeft(); }
    @Override public void addParenthesisRight() { ((FilterBase) this.whereFilters.getLast()).addParenthesisRight(); }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        addParenthesisLeft();
        addParenthesisRight();
        List<String> whereFiltersForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, this.whereFilters, true);
        if (CollectionUtils.isEmpty(whereFiltersForSQL)) return null;

        StringBuilder returnValue = new StringBuilder();
        if (super.getTypeOfLogicalOperator() != null) returnValue.append(super.getTypeOfLogicalOperator().name()).append(StringUtils.SPACE);
        if (super.isInvertSelection()) returnValue.append("NOT").append(StringUtils.SPACE);
        returnValue.append(Joiner.on(StringUtils.SPACE).join(whereFiltersForSQL));
        return returnValue.toString();
    }

    @Description("create Filters (enclosed in Parenthesis)")
    static WhereBase getGroupOfFilters(@Nullable LinSQL.TypeOfLogicalOperator typeOfLogicalOperator, boolean invertSelection, @NonNull WhereBase... filters) {
        List<WhereBase> whereList = Stream.of(filters).toList();
        if (CollectionUtils.isEmpty(whereList)) return null;
        GroupOfWheres wheres = new GroupOfWheres(whereList);
        if (typeOfLogicalOperator != null) wheres.setTypeOfLogicalOperator(typeOfLogicalOperator);
        wheres.setInvertSelection(invertSelection);
        return wheres;
    }
}
