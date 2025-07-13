package app.builder.core;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

final class BuildSQLWorkTable extends BuildSQLCore {
    @Getter(AccessLevel.PACKAGE) private final DbTable dbTable;
    @Getter(AccessLevel.PACKAGE) private final String tableAsAlias;

    static BuildSQLWorkTable createFor(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return new BuildSQLWorkTable(forSQLRetrieverForDB); }
    private BuildSQLWorkTable(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        this.dbTable = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyDbTable();
        this.tableAsAlias = StringUtils.defaultString(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyAsAlias());

        String useTableName = StringUtils.defaultString(forSQLRetrieverForDB.getDbPrefixForTableLocation()) +
                (forSQLRetrieverForDB.getTypeOfNamingSystemOrNormalized() == LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM
                        ? this.dbTable.getDbTableInfo().getDbtSystemName()
                        : this.dbTable.getDbTableInfo().getDbtNormalName());

        super.setStringForSQL(useTableName +
                (StringUtils.isBlank(this.tableAsAlias)
                        ? StringUtils.EMPTY
                        : " AS " + this.tableAsAlias));
    }
}
