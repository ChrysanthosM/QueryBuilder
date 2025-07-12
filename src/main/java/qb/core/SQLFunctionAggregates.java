package qb.core;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

final class SQLFunctionAggregates extends SQLFunction {
    private final DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    SQLFunctionAggregates(DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction, @NonNull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        String funcParam = StringUtils.EMPTY;
        if (super.getParams().size() > 1
                && super.getParam() == Boolean.valueOf(true)) {
            funcParam = "DISTINCT ";
        }

        String result = this.typeOfSQLFunction.name() + "(" +
                funcParam.concat(super.getLastParamSelectedFieldForSQL(forSQLRetrieverForDB, null)) +
                ")";
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
