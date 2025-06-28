package qb.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

final class SQLFunctionAggregatesWithPossibleALL extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    SQLFunctionAggregatesWithPossibleALL(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
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
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
