package qb.builder;

import org.apache.commons.lang3.StringUtils;

abstract sealed class BuildSQLCore implements BuildSQLCoreBase
        permits BuildSQLGroupByHavingValues, BuildSQLInsertRows, BuildSQLJoinWith, BuildSQLOrderBy, BuildSQLSelectFields,
        BuildSQLUnionWith, BuildSQLUpdateFields, BuildSQLWhereFilters, BuildSQLWorkTable {

    private String stringForSQL = StringUtils.EMPTY;
    @Override public String getStringForSQL() { return this.stringForSQL.concat(StringUtils.SPACE); }
    @Override public void setStringForSQL(String setString) { this.stringForSQL = setString; }

}
