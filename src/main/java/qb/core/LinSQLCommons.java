package qb.core;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
class LinSQLCommons {
    static final String QUOTE =  "'";
    static final String ASTERISK =  "*";

    private static final String UNDERSCORE =  "_";

    // Functions
    static String applyAsAlias(@NonNull String toResult, @Nullable String applyAlias, boolean inParenthesis, boolean inQuotes) {
        if (StringUtils.isBlank(toResult)) return toResult;
        if (inQuotes) toResult = StringUtils.wrap(toResult.replace(QUOTE, StringUtils.EMPTY), QUOTE);
        if (StringUtils.isBlank(applyAlias)) return toResult;
        return (inParenthesis ? "(" : StringUtils.EMPTY) + toResult + asAliasMain(applyAlias) + (inParenthesis ? ")" : StringUtils.EMPTY);
    }
    static String asAliasMain(@Nullable String applyAlias) {
        if (StringUtils.isBlank(applyAlias)) return StringUtils.EMPTY;
        applyAlias = fixAsAliasName(applyAlias);
        return " AS \"" + applyAlias.trim() + "\"";
    }
    static String fixAsAliasName(@NonNull String applyAlias) { return applyAlias.replaceAll(StringUtils.SPACE, UNDERSCORE); }


}
