package app.builder.core;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

public interface DeployFiltersBase {
    default WhereBase lessThan(@Nullable Object compareValue) { return lt(compareValue); }
    default WhereBase lt(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LT, compareValue); }
    default WhereBase notLessThan(@Nullable Object compareValue) { return notLt(compareValue); }
    default WhereBase notLt(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LT, compareValue)); }

    default WhereBase lessEqual(@Nullable Object compareValue) { return le(compareValue); }
    default WhereBase le(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LE, compareValue); }
    default WhereBase notLessEqual(@Nullable Object compareValue) { return notLe(compareValue); }
    default WhereBase notLe(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LE, compareValue)); }

    default WhereBase equal(@Nullable Object compareValue) { return eq(compareValue); }
    default WhereBase eq(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.EQ, compareValue); }
    default WhereBase notEqual(@Nullable Object compareValue) { return ne(compareValue); }
    default WhereBase ne(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.EQ, compareValue)); }

    default WhereBase greaterEqual(@Nullable Object compareValue) { return ge(compareValue); }
    default WhereBase ge(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GE, compareValue); }
    default WhereBase notGreaterEqual(@Nullable Object compareValue) { return notGe(compareValue); }
    default WhereBase notGe(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GE, compareValue)); }

    default WhereBase greaterThan(@Nullable Object compareValue) { return gt(compareValue); }
    default WhereBase gt(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GT, compareValue); }
    default WhereBase notGreaterThan(@Nullable Object compareValue) { return notGt(compareValue); }
    default WhereBase notGt(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GT, compareValue)); }

    default WhereBase in(@NonNull Object... inValues) { return in(Arrays.asList(inValues)); }
    default WhereBase in(@NonNull List<Object> inValues) { return J2SQLShared.Filter.whereInValues(this, inValues); }
    default WhereBase notIn(@NonNull Object... inValues) { return notIn(Arrays.asList(inValues)); }
    default WhereBase notIn(@NonNull List<Object> inValues) { return J2SQLShared.not(J2SQLShared.Filter.whereInValues(this, inValues)); }

    default WhereBase inSubSelect(@NonNull String inSubSelect) { return J2SQLShared.Filter.whereInSubSelect(this, inSubSelect); }
    default WhereBase notInSubSelect(@NonNull String inSubSelect) { return J2SQLShared.not(J2SQLShared.Filter.whereInSubSelect(this, inSubSelect)); }

    default WhereBase between(@NonNull Object fromObject, @NonNull Object toObject) { return J2SQLShared.Filter.whereBetweenValues(this, fromObject, toObject); }
    default WhereBase notBetween(@NonNull Object fromObject, @NonNull Object toObject) { return J2SQLShared.not(J2SQLShared.Filter.whereBetweenValues(this, fromObject, toObject)); }

    default WhereBase like(@Nullable String compareValue) { return like(compareValue, null, null); }
    default WhereBase like(@Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) { return J2SQLShared.Filter.whereLikeValue(this, compareValue, escapeLeft, escapeRight); }
    default WhereBase notLike(@Nullable String compareValue) { return notLike(compareValue, null, null); }
    default WhereBase notLike(@Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) { return J2SQLShared.not(J2SQLShared.Filter.whereLikeValue(this, compareValue, escapeLeft, escapeRight)); }

}
