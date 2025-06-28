package qb.core;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import qb.definition.db.sqlite.schema.structure.DbF;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class BuildSQLInsertRows extends BuildSQLCore {
    private String insertIntoFieldsForSQL = null;

    static BuildSQLInsertRows createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLInsertRows(forSQLRetrieverForDB); }
    private BuildSQLInsertRows(SQLRetrieverForDBs forSQLRetrieverForDB) {
        boolean putAutoStamp = forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getPutAutoStamp();

        final List<DbF> intoDbFs = new ArrayList<>();
        if (!forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getAutoIncrease()) {
            intoDbFs.addAll(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys());
        }
        forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtHasDbFields().stream()
                .filter(f -> !forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys().contains(f.getDbfNameEnum()))
                .forEach(f -> intoDbFs.add(f.getDbfNameEnum()));
        if (CollectionUtils.isEmpty(intoDbFs)) return;

        final List<List<Object>> insertRowsFieldValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getInsertRowsFieldValues();
        if (CollectionUtils.isEmpty(insertRowsFieldValues)) {
            insertRowsFieldValues.add(List.of(Collections.nCopies(intoDbFs.size(), "?")));
        }
        String[] insertRowsForSQL = new String[insertRowsFieldValues.size()];
        IntStream.range(0, insertRowsForSQL.length).parallel()
                .forEach(i -> insertRowsForSQL[i] = getInsertRowForSQL(forSQLRetrieverForDB, intoDbFs, putAutoStamp, insertRowsFieldValues.get(i)));
        super.setStringForSQL(String.join(", ", insertRowsForSQL));

        if (putAutoStamp) {
            intoDbFs.add(DbF.USER_STAMP);
            intoDbFs.add(DbF.DATE_STAMP);
        }

        String tableHasPrefixForFields = StringUtils.defaultString(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getTablePrefixForFields());
        final List<String> tempInsertIntoFieldsForSQL = new ArrayList<>();
        intoDbFs.forEach(f -> tempInsertIntoFieldsForSQL.add(forSQLRetrieverForDB.isNamingIsNormalized() ? f.name() : tableHasPrefixForFields.concat(f.getSystemName())));
        this.insertIntoFieldsForSQL = tempInsertIntoFieldsForSQL.stream().collect(Collectors.joining(", ", "(", ")"));
    }

    public String getInsertIntoFieldsForSQL() { return this.insertIntoFieldsForSQL.concat(StringUtils.SPACE); }


    private String getInsertRowForSQL(SQLRetrieverForDBs forSQLRetrieverForDB,
                                      List<DbF> intoDbF, boolean isAutoStamp, List<Object> rowFieldValues) {
        List<String> fieldValuesForSQL = IntStream.range(0, intoDbF.size())
                .mapToObj(i -> LInSQLBuilderShared.getSqlUserSelection(rowFieldValues.get(i), intoDbF.get(i).getFieldDataType().getInQuotesRequirement())
                        .getResolveObjectForSQL(forSQLRetrieverForDB))
                .collect(Collectors.toList());
        if (isAutoStamp) {
            fieldValuesForSQL.add(System.getenv("USERNAME"));
            fieldValuesForSQL.add(new Timestamp(System.currentTimeMillis()).toString());
        }
        return fieldValuesForSQL.stream().collect(Collectors.joining(", ", "(", ")"));
    }
}
