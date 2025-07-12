package qb.builder;

sealed interface BuildSQLCoreBase permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
