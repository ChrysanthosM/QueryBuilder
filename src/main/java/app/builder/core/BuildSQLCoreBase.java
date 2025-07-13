package app.builder.core;

sealed interface BuildSQLCoreBase permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
