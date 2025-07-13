package app.builder;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.util.List;

final class SQLFunctionTRANSLATE extends SQLFunction {
    @Override
    public DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction() { return DeploySQLFunctionsBase.TypeOfSQLFunction.TRANSLATE; }

    SQLFunctionTRANSLATE(@NonNull Object... args) { super.init(null, null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        List<?> params = super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null);
        String result = "TRANSLATE(" + super.getFirstParamSelectedFieldForSQL(forSQLRetrieverForDB, null) + ", " +
                params.get(1) + ", " + params.get(2);
        if (super.getParams().size() == 4)
            result = result.concat(", ".concat(super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(3)));
        result = result.concat(")");

        return getFinalValueAsAlias(result, super.getAsAlias());
    }

    @Override
    public String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
