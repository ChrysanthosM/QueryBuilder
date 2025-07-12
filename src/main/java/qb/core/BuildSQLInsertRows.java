package qb.core;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import qb.definition.db.base.BaseDbField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class BuildSQLInsertRows extends BuildSQLCore {
    private String insertIntoFieldsForSQL = null;

    static BuildSQLInsertRows createFor(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return new BuildSQLInsertRows(forSQLRetrieverForDB); }
    private BuildSQLInsertRows(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        boolean putAutoStamp = forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getPutAutoStamp();

        final List<BaseDbField> intoDbFs = Lists.newArrayList();
        if (Boolean.FALSE.equals(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getAutoIncrease())) {
            intoDbFs.addAll(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys());
        }
        forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtHasDbFields().stream()
                .filter(f -> !forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys().contains(f.getDbfNameEnum()))
                .forEach(f -> intoDbFs.add(f.getDbfNameEnum()));
        if (CollectionUtils.isEmpty(intoDbFs)) return;

        final List<List<Object>> insertRowsFieldValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getInsertRowsFieldValues();
        if (CollectionUtils.isEmpty(insertRowsFieldValues)) {
            insertRowsFieldValues.add(Lists.newArrayList(Collections.nCopies(intoDbFs.size(), "?")));
        }
        String[] insertRowsForSQL = new String[insertRowsFieldValues.size()];
        IntStream.range(0, insertRowsForSQL.length).parallel()
                .forEach(i -> insertRowsForSQL[i] = getInsertRowForSQL(forSQLRetrieverForDB, intoDbFs, putAutoStamp, insertRowsFieldValues.get(i)));
        super.setStringForSQL(Joiner.on(", ").join(insertRowsForSQL));

        if (putAutoStamp) {
            intoDbFs.add(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtUserStampDbF());
            intoDbFs.add(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtDateStampDbF());
        }

        String tableHasPrefixForFields = StringUtils.defaultString(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getTablePrefixForFields());
        final List<String> tempInsertIntoFieldsForSQL = Lists.newArrayList();
        intoDbFs.forEach(f -> tempInsertIntoFieldsForSQL.add(forSQLRetrieverForDB.isNamingIsNormalized() ? f.getName() : tableHasPrefixForFields.concat(f.getSystemName())));
        this.insertIntoFieldsForSQL = tempInsertIntoFieldsForSQL.stream().collect(Collectors.joining(", ", "(", ")"));
    }

    public String getInsertIntoFieldsForSQL() { return this.insertIntoFieldsForSQL.concat(StringUtils.SPACE); }


    private String getInsertRowForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB,
                                      List<BaseDbField> intoDbF, boolean isAutoStamp, List<Object> rowFieldValues) {
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
