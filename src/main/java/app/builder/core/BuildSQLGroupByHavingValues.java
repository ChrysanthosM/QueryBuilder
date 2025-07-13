package app.builder.core;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class BuildSQLGroupByHavingValues extends BuildSQLCore {

    static BuildSQLGroupByHavingValues createFor(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return new BuildSQLGroupByHavingValues(forSQLRetrieverForDB); }
    private BuildSQLGroupByHavingValues(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        MutablePair<List<SqlUserSelection>, List<WhereBase>> groupBySelectionsHavingValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getGroupBySelectionsHavingValues();
        if (groupBySelectionsHavingValues == null) return;
        List<SqlUserSelection> groupByFields = groupBySelectionsHavingValues.left;
        if (CollectionUtils.isEmpty(groupByFields)) return;

        List<String> groupByList = new ArrayList<>();
        groupByFields.stream().filter(Objects::nonNull).forEach(g -> {
            g.setIgnoreTableAsAlias();
            groupByList.add(g.getResolveObjectForSQL(forSQLRetrieverForDB));
        });

        if (CollectionUtils.isEmpty(groupByList)) return;
        super.setStringForSQL(groupByList.stream().collect(Collectors.joining(", ", "GROUP BY ", StringUtils.EMPTY)));

        List<WhereBase> havingValues = groupBySelectionsHavingValues.right;
        if (CollectionUtils.isEmpty(havingValues)) return;
        List<String> havingValuesForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, havingValues, true);
        if (CollectionUtils.isEmpty(havingValuesForSQL)) return;
        super.setStringForSQL(super.getStringForSQL() + " HAVING " + String.join(", ", havingValuesForSQL));
    }
}
