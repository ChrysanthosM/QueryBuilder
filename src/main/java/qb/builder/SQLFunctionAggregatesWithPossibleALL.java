package qb.builder;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

final class SQLFunctionAggregatesWithPossibleALL extends SQLFunction {
    private final DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    SQLFunctionAggregatesWithPossibleALL(DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction, @NonNull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        String funcParam = StringUtils.EMPTY;
        if (super.getParams().size() == 1) {
            funcParam = LinSQLCommons.ASTERISK;
        } else {
            if (super.getParam() == Boolean.valueOf(true)) {
                funcParam = "DISTINCT ";
            }
        }

        String result = this.typeOfSQLFunction.name() + "(" + funcParam +
                (funcParam.equals(LinSQLCommons.ASTERISK) ? StringUtils.EMPTY : super.getLastParamSelectedFieldForSQL(forSQLRetrieverForDB, null)) +
                ")";
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
