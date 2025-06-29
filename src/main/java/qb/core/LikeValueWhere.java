package qb.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

final class LikeValueWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_LIKE; }

    private final String compareValue;
    private final String escapeLeft;
    private final String escapeRight;

    LikeValueWhere(@Nonnull Object whereObject, @Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) {
        super(whereObject);
        this.compareValue = compareValue;
        this.escapeLeft = escapeLeft;
        this.escapeRight = escapeRight;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return super.whereObjectForSQL(forSQLRetrieverForDB) + LinSQLCommons.QUOTE + StringUtils.defaultString(escapeLeft) +
                StringUtils.defaultString(this.compareValue) +
                StringUtils.defaultString(escapeRight) + LinSQLCommons.QUOTE +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
    }
}
