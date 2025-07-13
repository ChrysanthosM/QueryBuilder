package app.builder;

import jakarta.annotation.Nullable;
import lombok.NonNull;

final class SQLFunction1Param extends SQLFunction {
    private final DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    SQLFunction1Param(DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction, @NonNull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return resolverAllParamsInParenthesis(forSQLRetrieverForDB, typeOfSQLFunction);
    }
    @Override
    public String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
