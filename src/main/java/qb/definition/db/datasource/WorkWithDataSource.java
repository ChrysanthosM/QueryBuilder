package qb.definition.db.datasource;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class WorkWithDataSource {
    @Value("${datasource.type}")
    private String datasourceType;

    private final DataSourceResolver dataSourceResolver;
    @Getter
    private DataSourceType defaultDataSourceType;
    @Getter
    private DataSourceProvider defaultDataSourceProvider;

    public WorkWithDataSource(DataSourceResolver dataSourceResolver) {
        this.dataSourceResolver = dataSourceResolver;
    }

    @PostConstruct
    private void init() {
        defaultDataSourceType = DataSourceType.getByPropertyName(datasourceType);
        defaultDataSourceProvider = dataSourceResolver.getDefaultDataSource(defaultDataSourceType);
    }

    @AllArgsConstructor
    @Getter
    public enum DataSourceType {
        SQLITE ("sqlite",  null),
        MSSQL ("mssql",  null),
        DB2_I("db2i",  "$."),
        ;
        private final String propertyName;
        private final String tablePrefixToReplace;

        public static DataSourceType getByPropertyName(String propertyName) {
            for (DataSourceType type : values()) {
                if (type.propertyName.equalsIgnoreCase(propertyName)) return type;
            }
            throw new IllegalArgumentException("No enum constant with property name " + propertyName);
        }
    }


}
