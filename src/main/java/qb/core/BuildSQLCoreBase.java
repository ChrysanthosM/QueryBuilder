package qb.core;

sealed interface BuildSQLCoreBase permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
