package qb.builder;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

final class SQLFunctionCONCAT extends SQLFunction {
    @Override
    public DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction() { return DeploySQLFunctionsBase.TypeOfSQLFunction.CONCAT; }

    SQLFunctionCONCAT(@NonNull Object... args) { super.init(null,null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        Optional<String> result = super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).stream().reduce((a, b) -> "CONCAT(".concat(a).concat(", ").concat(b).concat(")"));
        return getFinalValueAsAlias(result.orElseThrow(), super.getAsAlias());
    }

    @Override
    public String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        String result = StringUtils.join(super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null), args[0].toString()) ;
        return getFinalValueAsAlias(result, super.getAsAlias());
    }
}
