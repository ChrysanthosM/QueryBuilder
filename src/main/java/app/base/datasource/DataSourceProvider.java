package app.base.datasource;

import javax.sql.DataSource;

public sealed interface DataSourceProvider permits AbstractDataSourceProvider {
    DataSource getDS();

    default boolean isAutomaticTransactional() { return true; }
    default int getMaxParamsAllowedInQuery() { return 999999999; }
}
