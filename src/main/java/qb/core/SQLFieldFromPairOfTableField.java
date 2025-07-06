package qb.core;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import qb.definition.db.base.BaseDbF;

import java.lang.reflect.Type;

public final class SQLFieldFromPairOfTableField extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private PairOfTableField pairOfTableField;
    private SQLFieldFromTable sqlFieldFromTable;

    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField) {
        super();
        init(null, null, pairOfTableField);
    }
    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField, @Nullable String asAlias) {
        super();
        init(null, asAlias, pairOfTableField);
    }
    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, pairOfTableField);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        Preconditions.checkElementIndex(0, args.length);
        Preconditions.checkNotNull(args[0]);
        Preconditions.checkArgument(args[0] instanceof PairOfTableField);

        this.pairOfTableField = (PairOfTableField) args[0];
        this.sqlFieldFromTable = new SQLFieldFromTable(this.pairOfTableField.getDbf(), asAlias, setPrefix);
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    public BaseDbF getDbFieldEnum() { return this.pairOfTableField.getDbf(); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        if (isIgnoreTableAsAlias()) this.sqlFieldFromTable.setIgnoreTableAsAlias();
        return sqlFieldFromTable.getResolveObjectForSQLMain(forSQLRetrieverForDB, this.pairOfTableField.getDbt());
    }
}
