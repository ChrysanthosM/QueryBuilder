package qb.core;

import jakarta.annotation.Nullable;
import lombok.NonNull;


final class SQLFunction2Params extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    SQLFunction2Params(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @NonNull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null, null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return resolverAllParamsInParenthesis(forSQLRetrieverForDB, typeOfSQLFunction);
    }
    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
