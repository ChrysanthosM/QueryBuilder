package app.builder;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract sealed class SQLFunction extends SqlUserSelection
        permits SQLFunction1Param, SQLFunction2Params, SQLFunction3Params,
        SQLFunctionAggregates, SQLFunctionAggregatesWithPossibleALL,
        SQLFunctionCASE, SQLFunctionCONCAT, SQLFunctionTRANSLATE {
    static final String NON_SUPPORTED_MSG = "Non Supported Method";

    @Override public Type getTypeOfSelection() { return this.getClass(); }
    abstract DeploySQLFunctionsBase.TypeOfSQLFunction getTypeOfSQLFunction();

    private List<Object> params = new ArrayList<>();
    protected List<Object> getParams() { return this.params; }
    protected Object getParam() { return this.params.getFirst(); }
    protected void setParams(List<Object> params) { this.params = params; }
    protected void addParam(Object param) { this.params.add(param); }
    private List<String> paramsSelectedFieldForSQL = null;
    protected List<String> getParamsSelectedFieldForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        if (CollectionUtils.isNotEmpty(this.paramsSelectedFieldForSQL)) return this.paramsSelectedFieldForSQL;
        this.paramsSelectedFieldForSQL = new ArrayList<>();
        this.params.stream().filter(Objects::nonNull)
                .forEach(arg -> this.paramsSelectedFieldForSQL.add(LInSQLBuilderShared.getSqlUserSelection(arg, inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB)));
        return this.paramsSelectedFieldForSQL;
    }
    protected String getFirstParamSelectedFieldForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        return LInSQLBuilderShared.getSqlUserSelection(this.params.getFirst(), inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
    protected String getLastParamSelectedFieldForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        SqlUserSelection mainParam = LInSQLBuilderShared.getSqlUserSelection(this.params.getLast(), inQuotesRequirement);
        mainParam.setIgnoreTableAsAlias();
        return mainParam.getResolveObjectForSQL(forSQLRetrieverForDB);
    }

    abstract String defaultResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB);
    abstract String alternateResolver(SQLRetrieverForDbAbstract forSQLRetrieverForDB, @Nullable Object... args);

    protected String resolverAllParamsInParenthesis(SQLRetrieverForDbAbstract forSQLRetrieverForDB, DeploySQLFunctionsBase.TypeOfSQLFunction typeOfSQLFunction) {
        List<?> workParams = getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null);
        String result = workParams.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", typeOfSQLFunction.name() + "(", ")"));
        return getFinalValueAsAlias(result, getAsAlias());
    }

    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        Stream.of(args).filter(Objects::nonNull).forEach(arg -> this.params.add(arg));
        super.setAsAlias(asAlias);
        super.setHasPrefix(setPrefix);
    }

    protected static String getFinalValueAsAlias(String value, String asAlias) {
        return LinSQLCommons.applyAsAlias(value, asAlias, true, false);
    }

}

