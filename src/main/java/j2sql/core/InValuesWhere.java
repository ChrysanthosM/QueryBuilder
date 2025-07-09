package j2sql.core;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


final class InValuesWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WHERE_IN_VALUES; }

    private final List<Object> inValues;

    InValuesWhere(@NonNull Object whereObject, @NonNull List<Object> inValues) {
        super(whereObject);
        this.inValues = inValues;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB));
        if (CollectionUtils.isNotEmpty(this.inValues)) {
            List<String> newInValues = this.inValues.stream()
                    .filter(Objects::nonNull)
                    .map(o -> LInSQLBuilderShared.getSqlUserSelection(o, super.getInQuotesRequirement()).getResolveObjectForSQL(forSQLRetrieverForDB))
                    .toList();
            String valuesJoined = newInValues.stream().collect(Collectors.joining(", ", "(", ")"));
            returnValue.append(valuesJoined);
        }
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
