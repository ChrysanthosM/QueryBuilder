package app.builder.core;

public sealed interface WhereBase permits AbstractFilter {
    enum TypeOfWhere {
        WHERE_VALUE(null),
        WHERE_IN_VALUES("IN"),
        WHERE_BETWEEN("BETWEEN"),
        WHERE_LIKE("LIKE"),

        WHERE_IN_SUB_SELECT("IN"),
        WHERE_EXIST("EXISTS"),
        ;

        private final String putClause;
        TypeOfWhere(String putClause) {
            this.putClause = putClause;
        }
        String getPutClause() { return this.putClause; }
    }

    WhereBase and(WhereBase attachFilter);
    WhereBase or(WhereBase attachFilter);
    WhereBase getAttachedFilters();
}
