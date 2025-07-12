package qb.core;

sealed interface DeploySQLStatementsBase extends DefaultsSQLRetrieverForDBsBase
        permits SQLRetrieverCore {
    default String getSQLStatementForSelect() { return getDefaultSQLStatementForSelect(); }
    default String getSQLStatementForDelete() { return getDefaultSQLStatementForDelete(); }
    default String getSQLStatementForUpdate() { return getDefaultSQLStatementForUpdate(); }
    default String getSQLStatementForInsert() { return getDefaultSQLStatementForInsert(); }
    default String getSQLStatementForInsertGetOnlyValues() { return getDefaultSQLStatementForInsertGetOnlyValues(); }
}
