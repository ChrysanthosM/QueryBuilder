package j2sql.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Map;

final class SQLRetrieverForDB_SQLite extends SQLRetrieverForDBs {
    private static final Map<LinSQL.TypeOfComparison, String> comparisonType =
            Map.of(
                    LinSQL.TypeOfComparison.IS_NULL, "IS NULL",
                    LinSQL.TypeOfComparison.LT, "<",
                    LinSQL.TypeOfComparison.LE, "<=",
                    LinSQL.TypeOfComparison.EQ, "=",
                    LinSQL.TypeOfComparison.GE, ">=",
                    LinSQL.TypeOfComparison.GT, ">",
                    LinSQL.TypeOfComparison.NE, "<>"
            );
    private static final Map<SortOrder, String> orderByType =
            Map.of(
                    SortOrder.ASCENDING, "ASC",
                    SortOrder.DESCENDING, "DESC"
            );
    private static final Map<LinSQL.TypeOfJoin, String> joinType =
            Map.of(
                    LinSQL.TypeOfJoin.FULL, "FULL OUTER JOIN",
                    LinSQL.TypeOfJoin.JOIN, "INNER JOIN",
                    LinSQL.TypeOfJoin.LEFT, "LEFT JOIN",
                    LinSQL.TypeOfJoin.RIGHT, ""
            );

    SQLRetrieverForDB_SQLite(LinSQL.TypeOfNamingSystemOrNormalized namingSystemOrNormalized) {
        super(namingSystemOrNormalized, StringUtils.EMPTY);
    }

    @Override public String getNullWord() { return "NULL"; }
    @Override public Map<LinSQL.TypeOfComparison, String> getComparisonType() { return comparisonType; }
    @Override public Map<SortOrder, String> getOrderByType() { return orderByType; }
    @Override public Map<LinSQL.TypeOfJoin, String> getJoinType() { return joinType; }

    @Override public String getLimitOffsetForSQL(Pair<BigInteger, BigInteger> setLimitOffset) {
        String limitOffset = StringUtils.EMPTY;
        if (setLimitOffset != null) {
            if (setLimitOffset.getLeft() != null) {limitOffset = "LIMIT ".concat(setLimitOffset.getLeft().toString());}
            if (setLimitOffset.getRight() != null) {limitOffset = limitOffset.concat(" OFFSET ".concat(setLimitOffset.getRight().toString()));}
        }
        return limitOffset;
    }
    @Override public String getOffsetFetchForSQL(Pair<BigInteger, BigInteger> setOffsetFetch) { return StringUtils.EMPTY; }

    @Override
    public String getSQLStatementForSelect() {
        InitializationStatus initSts = initSQLStatementForSelect(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        return super.getWorkBuildSQLSelectFields().getStringForSQL() +
                super.getWorkBuildSQLJoinWith().getSelectedJoinFieldsForSQL() + StringUtils.SPACE +
                super.getSQLStatementFromWhereGroupByOrderBy() + StringUtils.SPACE +
                getLimitOffsetForSQL(super.getWorkLInSQLBuilderParams().getLimitOffset()) + StringUtils.SPACE +
                getOffsetFetchForSQL(super.getWorkLInSQLBuilderParams().getOffsetFetch());
    }

    @Override
    public String resolveSQLStringsFunction(SQLFunction sqlFunction) {
        switch (sqlFunction.getTypeOfSQLFunction()) {
            case CONCAT -> { return sqlFunction.alternateResolver(this, " || "); }
            default -> { return sqlFunction.defaultResolver(this); }
        }
    }
}
