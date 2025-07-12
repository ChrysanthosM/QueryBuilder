package qb.core;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

final class BuildSQLSelectFields extends BuildSQLCore {
    @Getter(AccessLevel.PACKAGE) private String selectedFieldsString;

    static BuildSQLSelectFields createFor(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return new BuildSQLSelectFields(forSQLRetrieverForDB); }
    private BuildSQLSelectFields(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        final List<SqlUserSelection> selectFields = new ArrayList<>();
        if (CollectionUtils.isEmpty(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getSqlUserSelections())) {
            forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbFs().forEach(p -> selectFields.add(new SQLFieldFromPairOfTableField(p)));
        } else {
            selectFields.addAll(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getSqlUserSelections());
        }
        if (CollectionUtils.isEmpty(selectFields)) return;

        List<String> selectedFieldsForSQL = new ArrayList<>();
        for (SqlUserSelection selectedField : selectFields) {
            String selectedFieldForSQL = selectedField.getResolveObjectForSQL(forSQLRetrieverForDB);
            if (StringUtils.isNotBlank(selectedFieldForSQL)) {
                selectedFieldsForSQL.add(selectedFieldForSQL);
                if (StringUtils.isNotBlank(selectedField.getAsAlias())) {
                    forSQLRetrieverForDB.addFieldMapper(LinSQLCommons.fixAsAliasName(selectedField.getAsAlias()));
                }
            }
        }

        this.selectedFieldsString = StringUtils.join(selectedFieldsForSQL, ", ");
        super.setStringForSQL("SELECT " +
                (forSQLRetrieverForDB.getWorkLInSQLBuilderParams().isSelectDistinct() ? "DISTINCT " : StringUtils.EMPTY) +
                this.selectedFieldsString);
    }
}
