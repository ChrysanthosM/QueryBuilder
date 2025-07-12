package qb.core;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

final class InSubSelectWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_IN_SUB_SELECT; }

    private final String inSubSelect;

    InSubSelectWhere(@NonNull Object whereObject, @NonNull String inSubSelect) {
        super(whereObject);
        this.inSubSelect = inSubSelect;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDbAbstract forSQLRetrieverForDB) {
        return super.whereObjectForSQL(forSQLRetrieverForDB) + "(" + StringUtils.defaultString(this.inSubSelect).trim() + ")" +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
    }
}
