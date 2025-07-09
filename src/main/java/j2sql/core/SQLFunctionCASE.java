package j2sql.core;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import lombok.NonNull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

final class SQLFunctionCASE extends SQLFunction {
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return IDeploySQLFunctions.TypeOfSQLFunction.CASE; }

    SQLFunctionCASE(@NonNull Object... args) { super.init(null,null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        Preconditions.checkElementIndex(0, super.getParams().size());
        Preconditions.checkElementIndex(1, super.getParams().size());

        Boolean inQuotesRequirement = (Boolean) super.getParams().get(0);

        Optional<Object> caseOpt = (Optional<Object>) super.getParams().get(1);
        String caseExpression = caseOpt.map(o -> LInSQLBuilderShared.getSqlUserSelection(o, inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB)).orElse(null);

        String elseExpression = LInSQLBuilderShared.getSqlUserSelection(super.getParams().get(2), inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB);

        List<Object> whenList = super.getParams().subList(3, super.getParams().size()) ;
        whenList.stream().filter(Objects::nonNull).forEach(f -> ((WhenThen) f).setInQuotesRequirement(inQuotesRequirement));
        List<String> searchListResolved = whenList.stream()
                .filter(Objects::nonNull)
                .map(when -> ((WhenThen) when).getResolveObjectForSQL(forSQLRetrieverForDB))
                .toList();
        String whenExpression = Joiner.on(StringUtils.SPACE).join(searchListResolved);

        return "CASE " + StringUtils.defaultString(caseExpression).concat(StringUtils.SPACE) +
                whenExpression + " ELSE " + elseExpression + " END";
    }

    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(NON_SUPPORTED_MSG); }
}
