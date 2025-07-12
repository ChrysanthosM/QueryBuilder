package qb.builder;

import com.google.common.base.Preconditions;
import qb.base.builder.BaseDbField;
import qb.base.datasource.WorkWithDataSource;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

final class LinSQL {
    public enum TypeOfNamingSystemOrNormalized { SYSTEM, NORMALIZED }
    public enum TypeOfLogicalOperator { AND, OR }
    public enum TypeOfComparison { IS_NULL, LT, LE, EQ, GE, GT, NE }
    public enum TypeOfJoin { FULL, JOIN, LEFT, RIGHT }

    private final LInSQLBuilder workLInSQLBuilder;
    public SQLRetrieverForDbAbstract getSqlRetrieverForDB() { return workLInSQLBuilder.getSqlRetrieverForDB(); }
    public BuildSQLWorkTable getWorkBuildSQLWorkTable() { return workLInSQLBuilder.getWorkBuildSQLWorkTable(); }
    public BuildSQLJoinWith getWorkBuildSQLJoinWith() { return workLInSQLBuilder.getWorkBuildSQLJoinWith(); }
    public BuildSQLSelectFields getWorkBuildSQLSelectFields() { return workLInSQLBuilder.getWorkBuildSQLSelectFields(); }
    public BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return workLInSQLBuilder.getWorkBuildSQLWhereFilters(); }
    public BuildSQLOrderBy getWorkBuildSQLOrderBy() { return workLInSQLBuilder.getWorkBuildSQLOrderBy(); }
    public BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return workLInSQLBuilder.getWorkBuildSQLGroupByHavingValues(); }
    public List<String> getFieldMapper() { return workLInSQLBuilder.getSqlRetrieverForDB().getFieldMapper(); }

    public static LinSQL create(WorkWithDataSource dataSource, boolean normalizeNames) { return new LinSQL(dataSource, normalizeNames); }
    private LinSQL(WorkWithDataSource dataSource, boolean normalizeNames) {
        TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized = normalizeNames ? TypeOfNamingSystemOrNormalized.NORMALIZED : TypeOfNamingSystemOrNormalized.SYSTEM;

        switch (dataSource.getDefaultDataSourceType()) {
            case DB2_I -> workLInSQLBuilder = LInSQLBuilder.createWithTablePrefix(dataSource.getDefaultDataSourceType(), typeOfNamingSystemOrNormalized, dataSource.getDefaultDataSourceType().getTablePrefixToReplace());

            default -> workLInSQLBuilder = LInSQLBuilder.createDefault(dataSource.getDefaultDataSourceType(), typeOfNamingSystemOrNormalized);
        }
    }


    public void clear() { workLInSQLBuilder.clearSQLProperties(); }

    public void loadSQL() {
        workLInSQLBuilder.getSQLStatement();
    }

    public String getSQL() { return workLInSQLBuilder.getSQLStatement(); }

    public String getFromInsertOnlyTheValues() { return workLInSQLBuilder.getFromInsertOnlyTheValues(); }

    public void attachComments(@NonNull String comments) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setComments(comments);
    }

    public void setApplyAutoAlias() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(true);
    }

    public void distinct() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setSelectDistinct();
    }

    public void limitOffset(int setLimit, int setOffset) {
        Preconditions.checkArgument(setLimit >= 0);
        Preconditions.checkArgument(setOffset >= 0);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setLimitOffset(MutablePair.of(BigInteger.valueOf(setLimit), BigInteger.valueOf(setOffset)));
    }
    public void offsetFetch(int setOffset, int setFetch) {
        Preconditions.checkArgument(setOffset >= 0);
        Preconditions.checkArgument(setFetch >= 0);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setOffsetFetch(MutablePair.of(BigInteger.valueOf(setOffset), BigInteger.valueOf(setFetch)));
    }

    public void insertInto(@NonNull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void updateInto(@NonNull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_UPDATE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void deleteFrom(@NonNull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_DELETE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void from(@NonNull DbTable setWorkWithTable, @Nullable String asAlias) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_SELECT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.defaultString(asAlias));
    }

    public void select(@NonNull Object... addSelectedFields) {
        Stream.of(addSelectedFields).filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addUserSelection(o, StringUtils.EMPTY));
    }
    public void selectAll() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUserSelection(BaseDbField.ALL(), StringUtils.EMPTY);
    }

    public void addFilters(@NonNull WhereBase... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.AND, false, filters));
    }
    public void where(@NonNull WhereBase... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(null, false, filters));
    }
    public void andNot(@NonNull WhereBase... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.AND, true, filters));
    }
    public void or(@NonNull WhereBase... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.OR, false, filters));
    }
    public void orNot(@NonNull WhereBase... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.OR, true, filters));
    }
    public void whereExists(@NonNull LinSQL existQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(J2SQLShared.Filter.whereExists(existQuery));
    }
    public void whereExists(@NonNull String existQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(J2SQLShared.Filter.whereExists(existQuery));
    }

    public void orderBy(@NonNull SortOrder sortOrder, @NonNull Object... addOrderBy) {
        Preconditions.checkArgument(sortOrder == SortOrder.ASCENDING || sortOrder == SortOrder.DESCENDING);
        Stream.of(addOrderBy).filter(Objects::nonNull).forEach(s -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(s, sortOrder));
    }
    @SafeVarargs
    public final void orderBy(@NonNull MutablePair<Object, SortOrder>... addOrderBy) {
        Stream.of(addOrderBy).filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(o.left, o.right));
    }
    public void orderBy(@NonNull List<MutablePair<Object, SortOrder>> addOrderBy) {
        addOrderBy.stream().filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(o.left, o.right));
    }

    public void groupBy(@NonNull Object... setGroupBy) {
        List<Object> groupBy = Stream.of(setGroupBy).filter(Objects::nonNull).toList();
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setGroupBySelectionsHavingValues(MutablePair.of(groupBy, new ArrayList<>()));
    }
    public void having(@NonNull WhereBase... filters) {
        List<WhereBase> whereList = Stream.of(filters).filter(Objects::nonNull).toList();
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getGroupBySelectionsHavingValues().getRight().addAll(whereList);
    }


    public void joinWith(@NonNull TypeOfJoin typeOfJoin, @NonNull LinSQL joinWith, @Nullable WhereBase... joinOn) {
        List<WhereBase> joinOnList = new ArrayList<>();
        if (joinOn != null) Stream.of(joinOn).filter(Objects::nonNull).forEach(joinOnList::add);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addJoinWith(MutableTriple.of(typeOfJoin, joinWith, joinOnList));
    }
    public LinSQL on(@NonNull WhereBase... joinOn) {
        List<WhereBase> joinOnList = Stream.of(joinOn).filter(Objects::nonNull).toList();
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().setRight(joinOnList);
        return this;
    }
    public void addJoinFilters(@NonNull WhereBase... addWhereFiltersToMainQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().addFilters(addWhereFiltersToMainQuery);
    }
    public void fromJoinSelectOnly(@NonNull Object... addSelectFields) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().select(addSelectFields);
    }
    public void fromJoinSelectOnlyAsAlias(@NonNull Object addSelectField, @Nullable String asAlias) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().select(addSelectField, asAlias);
    }

    public void UNION(@NonNull LinSQL unionWithLinSQL) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUnionWithQuery(unionWithLinSQL);
    }

    public void updateFieldSetValue(@NonNull PairOfTableField updField, @NonNull Object setValue) {
        Preconditions.checkArgument(updField.getBaseDbField() != BaseDbField.ALL());
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_UPDATE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUpdateFieldSetValue(updField, setValue);
    }

    @SafeVarargs
    public final void insertRows(@NonNull List<Object>... setRows) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        Stream.of(setRows).filter(CollectionUtils::isNotEmpty).forEach(rowValues -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addInsertRowsFieldValues(rowValues));
    }
    public void insertRowsFrom(@NonNull String fromQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setInsertRowsFromQuery(fromQuery);
    }
}
