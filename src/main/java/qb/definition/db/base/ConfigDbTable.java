package qb.definition.db.base;

import java.util.List;

public record ConfigDbTable(String systemName,
                            String tablePrefixForFields,
                            List<BaseDbField> hasKeys,
                            Boolean autoIncrease,
                            Boolean putAutoStamp) {
}
