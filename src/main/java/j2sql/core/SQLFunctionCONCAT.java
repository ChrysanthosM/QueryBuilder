package j2sql.core;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

final class SQLFunctionCONCAT extends SQLFunction {
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return IDeploySQLFunctions.TypeOfSQLFunction.CONCAT; }

    SQLFunctionCONCAT(@NonNull Object... args) { super.init(null,null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        Optional<String> result = super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).stream().reduce((a, b) -> "CONCAT(".concat(a).concat(", ").concat(b).concat(")"));
        return getFinalValueAsAlias(result.orElseThrow(), super.getAsAlias());
    }

    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        String result = StringUtils.join(super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null), args[0].toString()) ;
        return getFinalValueAsAlias(result, super.getAsAlias());
    }
}
