package app.builder.core;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Description;
import app.builder.base.datasource.WorkWithDataSource;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class J2SQL {
    private static final Set<String> OPERATION_SYMBOLS = Set.of("+", "-", "*", "/");
    private final WorkWithDataSource forDataSource;
    private final LinSQL workLinSQL;
    private final boolean normalizeNames;

    @Description("Returns a new J2SQL with system names")
    public static J2SQL create(WorkWithDataSource forDataSource) { return create(forDataSource, false); }
    @Description("Returns a new J2SQL with System or Normalized names")
    public static J2SQL create(WorkWithDataSource forDataSource, boolean normalizeNames) { return new J2SQL(forDataSource, normalizeNames); }
    private J2SQL(WorkWithDataSource forDataSource, boolean normalizeNames) {
        this.forDataSource = forDataSource;
        this.normalizeNames = normalizeNames;
        this.workLinSQL = LinSQL.create(this.forDataSource, this.normalizeNames);
    }
    
    @Description("Select Operation")
    public static SQLFieldOperation operation(@NonNull Object addSelectedField, @NonNull String operation) {
        Preconditions.checkArgument(OPERATION_SYMBOLS.contains(StringUtils.left(StringUtils.trimToEmpty(operation), 1)));
        return new SQLFieldOperation(addSelectedField, operation);
    }

    @Description("Select SQL Fields/Constants/SQLFunctions as Alias")
    public static SQLFieldObject asAlias(@NonNull Object addSelectedField, @NonNull String asAlias) { return new SQLFieldObject(addSelectedField, asAlias, null); }


    @Description("Clear attributes")
    public void clear() {
        workLinSQL.clear();
    }
    @Description("Get J2SQL for SQL Statement")
    public J2SQL loadSQL() {
        workLinSQL.loadSQL();
        return this;
    }

    @Description("Get the SQL Statement")
    public String getSQL() { return workLinSQL.getSQL(); }

    @Description("Get the Inserted Values from Insert SQL Statement")
    public String getFromInsertOnlyTheValues() { return workLinSQL.getFromInsertOnlyTheValues(); }

    @Description("Attach Comments at SQL Statement")
    public J2SQL attachComments(@NonNull String comments) {
        workLinSQL.attachComments(comments);
        return this;
    }

    @Description("Apply Auto Alias to Selected DB Fields")
    public J2SQL setApplyAutoAlias() {
        workLinSQL.setApplyAutoAlias();
        return this;
    }

    @Description("Set SQL Result to be Distinct")
    public J2SQL distinct() {
        workLinSQL.distinct();
        return this;
    }

    @Description("Set Limit/Offset Rows")
    public J2SQL limitOffset(int setLimit, int setOffset) {
        workLinSQL.limitOffset(setLimit, setOffset);
        return this;
    }
    @Description("Set Offset/Fetch Rows")
    public J2SQL offsetFetch(int setOffset, int setFetch) {
        workLinSQL.offsetFetch(setOffset, setFetch);
        return this;
    }

    @Description("Insert Into Table")
    public J2SQL insertInto(@NonNull DbTable setWorkWithTable) {
        workLinSQL.insertInto(setWorkWithTable);
        return this;
    }
    @Description("Update Into Table")
    public J2SQL updateInto(@NonNull DbTable setWorkWithTable) {
        workLinSQL.updateInto(setWorkWithTable);
        return this;
    }
    @Description("Delete From Table")
    public J2SQL deleteFrom(@NonNull DbTable setWorkWithTable) {
        workLinSQL.deleteFrom(setWorkWithTable);
        return this;
    }

    @Description("Select From Table as Alias")
    public J2SQL from(@NonNull Pair<DbTable, String> setWorkWithTableAsAlias) { return this.from(setWorkWithTableAsAlias.getLeft(), StringUtils.defaultString(setWorkWithTableAsAlias.getRight())); }
    @Description("Select From Table")
    public J2SQL from(@NonNull DbTable setWorkWithTable) { return this.from(setWorkWithTable, StringUtils.EMPTY); }
    @Description("Select From Table as Prefixed Alias")
    public J2SQL from(@NonNull DbTable setWorkWithTable, @NonNull J2SQLShared.PFX asAlias) { return this.from(setWorkWithTable, asAlias.name()); }
    @Description("Select From Table as Alias")
    public J2SQL from(@NonNull DbTable setWorkWithTable, @Nullable String asAlias) {
        workLinSQL.from(setWorkWithTable, asAlias);
        return this;
    }

    @Description("Add selected SQL Fields/Constants/SQLFunctions")
    public J2SQL select(@NonNull Object... addSelectedFields) {
        workLinSQL.select(addSelectedFields);
        return this;
    }
    @Description("Select All with asterisk *")
    public J2SQL selectAll() {
        workLinSQL.selectAll();
        return this;
    }

    @Description("add Filters")
    private J2SQL addFilters(@NonNull WhereBase... filters) {
        workLinSQL.addFilters(filters);
        return this;
    }
    @Description("add Filters")
    public J2SQL where(@NonNull WhereBase... filters) {
        workLinSQL.where(filters);
        return this;
    }
    @Description("add 'AND' Filter")
    public J2SQL and(@NonNull WhereBase... filters) {
        this.addFilters(filters);
        return this;
    }
    @Description("add 'AND NOT' Filter")
    public J2SQL andNot(@NonNull WhereBase... filters) {
        workLinSQL.andNot(filters);
        return this;
    }
    @Description("add 'OR' Filter")
    public J2SQL or(@NonNull WhereBase... filters) {
        workLinSQL.or(filters);
        return this;
    }
    @Description("add 'OR NOT' Filter")
    public J2SQL orNot(@NonNull WhereBase... filters) {
        workLinSQL.orNot(filters);
        return this;
    }
    @Description("add Exists filter query")
    public J2SQL whereExists(@NonNull J2SQL existQuery) {
        workLinSQL.whereExists(existQuery.workLinSQL);
        return this;
    }
    @Description("add Exists filter query")
    public J2SQL whereExists(@NonNull String existQuery) {
        workLinSQL.whereExists(existQuery);
        return this;
    }

    @Description("add Order By - All ASCENDING")
    public J2SQL orderBy(@NonNull Object... addOrderBy) { return this.orderBy(SortOrder.ASCENDING, addOrderBy); }
    @Description("add Order By")
    public J2SQL orderBy(@NonNull SortOrder sortOrderAll, @NonNull Object... addOrderBy) {
        workLinSQL.orderBy(sortOrderAll, addOrderBy);
        return this;
    }
    @Description("add Order By")
    public J2SQL orderBy(@NonNull MutablePair<Object, SortOrder>... addOrderBy) {
        workLinSQL.orderBy(addOrderBy);
        return this;
    }
    @Description("add Order By")
    public J2SQL orderBy(@NonNull List<MutablePair<Object, SortOrder>> addOrderBy) {
        workLinSQL.orderBy(addOrderBy);
        return this;
    }

    @Description("set Group By")
    public J2SQL groupBy(@NonNull Object... setGroupBy) {
        workLinSQL.groupBy(setGroupBy);
        return this;
    }
    @Description("add Having Values")
    public J2SQL having(@NonNull WhereBase... filters) {
        workLinSQL.having(filters);
        return this;
    }


    @Description("Join with Table/Join On fields/Join Where Filters")
    public J2SQL joinWith(@NonNull LinSQL.TypeOfJoin typeOfJoin, @NonNull J2SQL joinWith, @Nullable WhereBase... joinOn) {
        workLinSQL.joinWith(typeOfJoin, joinWith.workLinSQL, joinOn);
        return this;
    }
    @Description("Left Join with") public J2SQL leftJoin(@NonNull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Left Join with") public J2SQL leftJoin(@NonNull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Left Join with") public J2SQL leftJoin(@NonNull DbTable joinWith, @NonNull String asAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Left Join with") public J2SQL leftJoin(@NonNull DbTable joinWith, @NonNull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Right Join with") public J2SQL rightJoin(@NonNull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Right Join with") public J2SQL rightJoin(@NonNull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Right Join with") public J2SQL rightJoin(@NonNull DbTable joinWith, @NonNull String asAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Right Join with") public J2SQL rightJoin(@NonNull DbTable joinWith, @NonNull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Inner Join with") public J2SQL innerJoin(@NonNull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Inner Join with") public J2SQL innerJoin(@NonNull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Inner Join with") public J2SQL innerJoin(@NonNull DbTable joinWith, @NonNull String asAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Inner Join with") public J2SQL innerJoin(@NonNull DbTable joinWith, @NonNull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Full Join with") public J2SQL fullJoin(@NonNull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Full Join with") public J2SQL fullJoin(@NonNull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Full Join with") public J2SQL fullJoin(@NonNull DbTable joinWith, @NonNull String asAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Full Join with") public J2SQL fullJoin(@NonNull DbTable joinWith, @NonNull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Join On") public J2SQL on(@NonNull WhereBase... joinOn) {
        workLinSQL.on(joinOn);
        return this;
    }
    @Description("Join Where Filters, will be added in main Query 'Where' Filters")
    public J2SQL addJoinFilters(@NonNull WhereBase... addWhereFiltersToMainQuery) {
        workLinSQL.addJoinFilters(addWhereFiltersToMainQuery);
        return this;
    }
    @Description("Add selected only SQL Fields/Constants/SQLFunctions to main Query")
    public J2SQL fromJoinSelectOnly(@NonNull Object... addSelectFields) {
        workLinSQL.fromJoinSelectOnly(addSelectFields);
        return this;
    }
    @Description("Add selected only SQL Fields/Constants/SQLFunctions to main Query")
    public J2SQL fromJoinSelectOnlyAsAlias(@NonNull Object addSelectField, @Nullable String asAlias) {
        workLinSQL.fromJoinSelectOnlyAsAlias(addSelectField, asAlias);
        return this;
    }

    @Description("Union with J2SQL")
    public J2SQL UNION(@NonNull J2SQL unionWithLinSQL) {
        workLinSQL.UNION(unionWithLinSQL.workLinSQL);
        return this;
    }

    @Description("Update field, Set value")
    public J2SQL updateFieldSetValue(@NonNull PairOfTableField updField, @NonNull Object setValue) {
        workLinSQL.updateFieldSetValue(updField, setValue);
        return this;
    }

    @Description("Insert Row, set field values")
    public J2SQL insertRow(@NonNull Object... setFieldValues) { return insertRows(Arrays.asList(setFieldValues)); }
    @SafeVarargs
    @Description("Insert Rows, set rows with field values")
    public final J2SQL insertRows(@NonNull List<Object>... setRows) {
        workLinSQL.insertRows(setRows);
        return this;
    }
    @Description("Insert Rows, from J2SQL")
    public J2SQL insertRowsFrom(@NonNull J2SQL fromLinSQL) { return insertRowsFrom(fromLinSQL.getSQL()); }
    @Description("Insert Rows, from Query")
    public J2SQL insertRowsFrom(@NonNull String fromQuery) {
        workLinSQL.insertRowsFrom(fromQuery);
        return this;
    }
}
