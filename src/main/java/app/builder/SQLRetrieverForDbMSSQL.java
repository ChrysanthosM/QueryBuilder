package app.builder;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Map;

final class SQLRetrieverForDbMSSQL extends SQLRetrieverForDbAbstract {
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
                    LinSQL.TypeOfJoin.FULL, "FULL JOIN",
                    LinSQL.TypeOfJoin.JOIN, "INNER JOIN",
                    LinSQL.TypeOfJoin.LEFT, "LEFT JOIN",
                    LinSQL.TypeOfJoin.RIGHT, "RIGHT JOIN"
            );

    SQLRetrieverForDbMSSQL(LinSQL.TypeOfNamingSystemOrNormalized namingSystemOrNormalized, @Nullable String dbPrefixForTable) {
        super(namingSystemOrNormalized, StringUtils.defaultString(dbPrefixForTable));
    }

    @Override public String getNullWord() { return "NULL"; }
    @Override public Map<LinSQL.TypeOfComparison, String> getComparisonType() { return comparisonType; }
    @Override public Map<SortOrder, String> getOrderByType() { return orderByType; }
    @Override public Map<LinSQL.TypeOfJoin, String> getJoinType() { return joinType; }

    @Override public String getLimitOffsetForSQL(Pair<BigInteger, BigInteger> setLimitOffset) { return StringUtils.EMPTY; }
    @Override public String getOffsetFetchForSQL(Pair<BigInteger, BigInteger> setOffsetFetch) {
        String offsetFetch = StringUtils.EMPTY;
        if (setOffsetFetch != null) {
            if (setOffsetFetch.getLeft() != null) { offsetFetch = "OFFSET ".concat(setOffsetFetch.getLeft().toString()).concat(" ROWS "); }
            if (setOffsetFetch.getRight() != null) { offsetFetch = offsetFetch.concat("FETCH NEXT ".concat(setOffsetFetch.getRight().toString()).concat(" ROWS ONLY ")); }
        }
        return offsetFetch;
    }


    @Override
    public String getSQLStatementForSelect() {
        InitializationStatus initSts = initSQLStatementForSelect(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        return super.getWorkBuildSQLSelectFields().getStringForSQL().trim() +
                StringUtils.defaultIfBlank(super.getWorkBuildSQLJoinWith().getSelectedJoinFieldsForSQL(), StringUtils.SPACE) +
                super.getSQLStatementFromWhereGroupByOrderBy() +
                getLimitOffsetForSQL(super.getWorkLInSQLBuilderParams().getLimitOffset()) +
                getOffsetFetchForSQL(super.getWorkLInSQLBuilderParams().getOffsetFetch());
    }



}
