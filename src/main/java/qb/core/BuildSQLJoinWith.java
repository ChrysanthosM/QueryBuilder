package qb.core;

import com.google.common.base.Joiner;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final class BuildSQLJoinWith extends BuildSQLCore {
    private String selectedJoinFieldsForSQL = StringUtils.EMPTY;
    private String joinWithTablesOnFieldsForSQL = StringUtils.EMPTY;
    @Getter private String whereJoinFiltersForSQL = StringUtils.EMPTY;

    static BuildSQLJoinWith createFor(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return new BuildSQLJoinWith(forSQLRetrieverForDB); }
    private BuildSQLJoinWith(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        List<MutableTriple<LinSQL.TypeOfJoin, LinSQL, List<WhereBase>>> joinWiths = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getJoinWith();
        if (CollectionUtils.isEmpty(joinWiths)) return;

        forSQLRetrieverForDB.addAvailableTableWithFields(Triple.of(
                forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable(),
                forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getTableAsAlias(),
                forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtHasDbFieldNamesEnum()));

        List<String> selectedJoinFieldsList = new ArrayList<>();
        List<String> joinWithTablesOnFieldsList = new ArrayList<>();
        List<String> whereJoinFiltersList = new ArrayList<>();
        for (MutableTriple<LinSQL.TypeOfJoin, LinSQL, List<WhereBase>> joinWith : joinWiths) {
            LinSQL joinLinSQL = joinWith.getMiddle();
            joinLinSQL.getSQL();

            selectedJoinFieldsList.add(joinLinSQL.getWorkBuildSQLSelectFields().getSelectedFieldsString());
            whereJoinFiltersList.add(joinLinSQL.getWorkBuildSQLWhereFilters().getStringForSQL());

            forSQLRetrieverForDB.addAvailableTableWithFields(Triple.of(
                    joinLinSQL.getSqlRetrieverForDB().getWorkBuildSQLWorkTable().getDbTable(),
                    joinLinSQL.getSqlRetrieverForDB().getWorkBuildSQLWorkTable().getTableAsAlias(),
                    joinLinSQL.getSqlRetrieverForDB().getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtHasDbFieldNamesEnum()));
            joinLinSQL.getSqlRetrieverForDB().addAvailableTableWithFields(forSQLRetrieverForDB.getAvailableTablesWithFields());

            String joinWithTable = forSQLRetrieverForDB.getJoinType().get(joinWith.getLeft()) +
                    StringUtils.SPACE +
                    joinLinSQL.getWorkBuildSQLWorkTable().getStringForSQL();
            List<String> joinOnFields = BuildSQLWhereFilters.getResolveFiltersForSQL(joinLinSQL.getSqlRetrieverForDB(), joinWith.getRight(), true);
            String joinOn = StringUtils.EMPTY;
            if (CollectionUtils.isNotEmpty(joinOnFields)) joinOn = joinOnFields.stream().collect(Collectors.joining(" AND ", "ON (", ")"));
            joinWithTablesOnFieldsList.add(joinWithTable + joinOn);
        }
        if (CollectionUtils.isNotEmpty(selectedJoinFieldsList)) this.selectedJoinFieldsForSQL = ", " + String.join(", ", selectedJoinFieldsList);
        if (CollectionUtils.isNotEmpty(joinWithTablesOnFieldsList)) this.joinWithTablesOnFieldsForSQL = Joiner.on(StringUtils.SPACE).join(joinWithTablesOnFieldsList);
        if (CollectionUtils.isNotEmpty(whereJoinFiltersList)) this.whereJoinFiltersForSQL = Joiner.on(" AND ").join(whereJoinFiltersList);
    }

    public String getSelectedJoinFieldsForSQL() { return this.selectedJoinFieldsForSQL.concat(StringUtils.SPACE); }
    public String getJoinWithTablesOnFieldsForSQL() { return this.joinWithTablesOnFieldsForSQL.concat(StringUtils.SPACE); }
}
