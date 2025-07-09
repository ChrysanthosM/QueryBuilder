package j2sql.core;

sealed interface IBuildSQLCore permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
