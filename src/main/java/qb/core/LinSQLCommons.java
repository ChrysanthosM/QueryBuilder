package qb.core;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
class LinSQLCommons {
    static final String QUOTE =  "'";
    static final String ASTERISK =  "*";

    private static final String UNDERSCORE =  "_";

    // Functions
    static String applyAsAlias(@Nonnull String toResult, String applyAlias, boolean inParenthesis, boolean inQuotes) {
        if (StringUtils.isBlank(toResult)) return toResult;
        if (inQuotes) toResult = StringUtils.wrap(toResult.replace(QUOTE, StringUtils.EMPTY), QUOTE);
        if (StringUtils.isBlank(applyAlias)) return toResult;
        return (inParenthesis ? "(" : StringUtils.EMPTY) + toResult + asAliasMain(applyAlias) + (inParenthesis ? ")" : StringUtils.EMPTY);
    }
    static String asAliasMain(@Nonnull String applyAlias) {
        if (StringUtils.isBlank(applyAlias)) return StringUtils.EMPTY;
        applyAlias = fixAsAliasName(applyAlias);
        return " AS \"" + applyAlias.trim() + "\"";
    }
    static String fixAsAliasName(@Nonnull String applyAlias) { return applyAlias.replaceAll(StringUtils.SPACE, UNDERSCORE); }


}
