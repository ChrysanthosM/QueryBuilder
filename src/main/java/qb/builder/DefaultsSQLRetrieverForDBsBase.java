package qb.builder;

sealed interface DefaultsSQLRetrieverForDBsBase
        permits DeploySQLFunctionsBase, DeploySQLStatementsBase, SQLRetrieverForDbAbstract {
    String getDefaultSQLStatementForSelect();
    String getDefaultSQLStatementForDelete();
    String getDefaultSQLStatementForUpdate();
    String getDefaultSQLStatementForInsert();
    String getDefaultSQLStatementForInsertGetOnlyValues();

}
