package qb.core;

import lombok.experimental.UtilityClass;
import qb.definition.db.sqlite.schema.structure.DbF;

@UtilityClass
class LInSQLBuilderShared {
    public static SqlUserSelection getSqlUserSelection(Object selectionObject) {
        return getSqlUserSelectionMain(selectionObject, null, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, String asAlias) {
        return getSqlUserSelectionMain(selectionObject, asAlias, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, Boolean inQuotesRequirement) {
        return getSqlUserSelectionMain(selectionObject, null, inQuotesRequirement);
    }

    private static SqlUserSelection getSqlUserSelectionMain(Object selectionObject, String asAlias, Boolean inQuotesRequirement) {
        return switch (selectionObject) {
            case SQLFieldOperation op -> op;
            case SQLFieldObject obj -> obj;
            case SQLFieldFromTable table -> table;
            case SQLFieldFromPairOfTableField pair -> pair;
            case DbF dbf -> new SQLFieldFromTable(dbf, asAlias);
            case PairOfTableField pair -> new SQLFieldFromPairOfTableField(pair, asAlias);
            case J2SQLShared.SQLFunctionObject stringsFunction -> {
                ((SQLFunction) stringsFunction.getSqlFunction()).setAsAlias(asAlias);
                yield ((SQLFunction) stringsFunction.getSqlFunction());
            }
            default -> new SQLFieldFromConstant(selectionObject, asAlias, inQuotesRequirement);
        };
    }
}
