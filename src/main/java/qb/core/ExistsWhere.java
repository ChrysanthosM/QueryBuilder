package qb.core;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

final class ExistsWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_EXIST; }

    private final String inSubSelect;

    ExistsWhere(@Nonnull String inSubSelect) {
        super(null);
        this.inSubSelect = inSubSelect;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return super.whereObjectForSQL(forSQLRetrieverForDB) + "(" + StringUtils.defaultString(this.inSubSelect).trim() + ")" +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
    }
}
