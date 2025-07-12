package qb.builder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import qb.base.datasource.WorkWithDataSource;


final class LInSQLBuilder {
    @Getter(AccessLevel.PACKAGE) private final LInSQLBuilderParams workLInSQLBuilderParams = new LInSQLBuilderParams();

    private final SQLStatementRetrieve sqlStatementRetrieve;
    SQLRetrieverForDbAbstract getSqlRetrieverForDB() { return this.sqlStatementRetrieve.getSqlRetrieverForDB(); }

    static LInSQLBuilder createDefault(WorkWithDataSource.DataSourceType dataSourceType, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized) {
        return new LInSQLBuilder(dataSourceType, typeOfNamingSystemOrNormalized, StringUtils.EMPTY);
    }
    static LInSQLBuilder createWithTablePrefix(WorkWithDataSource.DataSourceType dataSourceType, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized, String dbPrefixForTable) {
        return new LInSQLBuilder(dataSourceType, typeOfNamingSystemOrNormalized, dbPrefixForTable);
    }

    private LInSQLBuilder(WorkWithDataSource.DataSourceType typeOfDB, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized, String dbPrefixForTable) {
        this.sqlStatementRetrieve = new SQLStatementRetrieve(typeOfDB, typeOfNamingSystemOrNormalized, dbPrefixForTable);
    }


    void clearSQLProperties() { this.workLInSQLBuilderParams.clearSQLPropertiesMain(); }

    private String sqlStatement = null;
    String getSQLStatement() {
        if (StringUtils.isNotBlank(this.sqlStatement)) return this.sqlStatement;

        if (this.workLInSQLBuilderParams.isApplyAutoAlias()) {
            this.workLInSQLBuilderParams.getJoinWith().forEach(j -> j.getMiddle().setApplyAutoAlias());
            this.workLInSQLBuilderParams.getUnionWithQueries().forEach(LinSQL::setApplyAutoAlias);
        }

        this.sqlStatementRetrieve.setWorkLInSQLBuilderParams(this.workLInSQLBuilderParams);
        this.sqlStatement = switch (this.workLInSQLBuilderParams.getTypeOfSQL()) {
            case SQL_SELECT -> this.sqlStatementRetrieve.getSQLStatementForSelect();
            case SQL_INSERT -> this.sqlStatementRetrieve.getSQLStatementForInsert();
            case SQL_UPDATE -> this.sqlStatementRetrieve.getSQLStatementForUpdate();
            case SQL_DELETE -> this.sqlStatementRetrieve.getSQLStatementForDelete();
        };

        this.sqlStatement = formatSql(this.sqlStatement);
        return this.sqlStatement;
    }
    String getFromInsertOnlyTheValues() {
        if (StringUtils.isNotBlank(this.sqlStatement)) return this.sqlStatement;
        this.sqlStatementRetrieve.setWorkLInSQLBuilderParams(this.workLInSQLBuilderParams);
        this.sqlStatement = this.sqlStatementRetrieve.getSQLStatementForInsertGetOnlyValues();
        this.sqlStatement = formatSql(this.sqlStatement);
        return this.sqlStatement;
    }
    private static String formatSql(@NonNull String sqlStatement) {
        return StringUtils.trimToEmpty(sqlStatement.replaceAll("\\s+", StringUtils.SPACE).replaceAll("\\s+,", ","));
    }

    BuildSQLWorkTable getWorkBuildSQLWorkTable() { return this.sqlStatementRetrieve.getWorkBuildSQLWorkTable(); }
    BuildSQLJoinWith getWorkBuildSQLJoinWith() { return this.sqlStatementRetrieve.getWorkBuildSQLJoinWith(); }
    BuildSQLSelectFields getWorkBuildSQLSelectFields() { return this.sqlStatementRetrieve.getWorkBuildSQLSelectFields(); }
    BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return this.sqlStatementRetrieve.getWorkBuildSQLWhereFilters(); }
    BuildSQLOrderBy getWorkBuildSQLOrderBy() { return this.sqlStatementRetrieve.getWorkBuildSQLOrderBy(); }
    BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return this.sqlStatementRetrieve.getWorkBuildSQLGroupByHavingValues(); }
}
