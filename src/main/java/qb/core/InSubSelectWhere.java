package qb.core;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

final class InSubSelectWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_IN_SUB_SELECT; }

    private final String inSubSelect;

    InSubSelectWhere(@Nonnull Object whereObject, @Nonnull String inSubSelect) {
        super(whereObject);
        this.inSubSelect = inSubSelect;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return super.whereObjectForSQL(forSQLRetrieverForDB) + "(" + StringUtils.defaultString(this.inSubSelect).trim() + ")" +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
    }
}
