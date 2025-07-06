package qb.core;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import qb.definition.db.base.BaseDbField;
import qb.definition.db.base.BaseDbTable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class SQLFieldFromTable extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private BaseDbField dbF;
    private DbField dbField = null;

    SQLFieldFromTable(@Nonnull BaseDbField dbF) {
        super();
        init(null, null, dbF);
    }
    SQLFieldFromTable(@Nonnull BaseDbField dbF, @Nullable String asAlias) {
        super();
        init(null, asAlias, dbF);
    }
    SQLFieldFromTable(@Nonnull BaseDbField dbF, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, dbF);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        Preconditions.checkElementIndex(0, args.length);
        Preconditions.checkNotNull(args[0]);
        Preconditions.checkArgument(args[0] instanceof BaseDbField);
        this.dbF = (BaseDbField) args[0];
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbF);
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    BaseDbField getDbFieldEnum() { return this.dbF; }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return getResolveObjectForSQLMain(forSQLRetrieverForDB, null);
    }
    String getResolveObjectForSQLMain(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable BaseDbTable forDbt) {
        String returnName = StringUtils.defaultString(this.getHasPrefix());

        String tableHasPrefixForFields = (forDbt == null ? StringUtils.EMPTY : StringUtils.defaultString(DbTableInstances.getMapTableInstance(forDbt).getTablePrefixForFields()));

        if (this.dbF == BaseDbField.dummyALL) {
            returnName = returnName.concat(LinSQLCommons.ASTERISK);
        } else {
            if (forSQLRetrieverForDB.getTypeOfNamingSystemOrNormalized() == LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM) {
                returnName = returnName.concat(tableHasPrefixForFields.concat(this.dbField.getDbfSystemName()));
            } else {
                returnName = returnName.concat(this.dbField.getDbfNormalName());
            }
        }

        if (StringUtils.isBlank(this.getHasPrefix())) {
            String tableAsAlias = StringUtils.defaultString(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyAsAlias());
            Set<Triple<DbTable, String, List<BaseDbField>>> availableTablesWithFields = forSQLRetrieverForDB.getAvailableTablesWithFields();
            if (CollectionUtils.isNotEmpty(availableTablesWithFields)) {
                List<Triple<DbTable, String, List<BaseDbField>>> tablesList = new ArrayList<>(availableTablesWithFields.stream().toList());
                for (Triple<DbTable, String, List<BaseDbField>> availableTableWithFields : tablesList.reversed()) {
                    List<BaseDbField> tblFields = availableTableWithFields.getRight();
                    if (CollectionUtils.isNotEmpty(tblFields)
                            && tblFields.contains(this.dbField.getDbfNameEnum())) {
                        tableAsAlias = availableTableWithFields.getMiddle();
                        break;
                    }

                }
            }
            if (StringUtils.isNotBlank(tableAsAlias)) returnName = tableAsAlias.concat(".").concat(returnName);
        }

        if (this.dbF != BaseDbField.dummyALL) {
            if (StringUtils.isBlank(super.getAsAlias())
                    && forSQLRetrieverForDB.getWorkLInSQLBuilderParams().isApplyAutoAlias()
                    && !super.isIgnoreTableAsAlias()) {
                super.setAsAlias(this.dbField.getDbfAsAlias());
            }
        }

        return LinSQLCommons.applyAsAlias(returnName, super.getAsAlias(), false, false);
    }
}
