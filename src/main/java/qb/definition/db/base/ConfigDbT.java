package qb.definition.db.base;

import java.util.List;

public record ConfigDbT(String systemName,
                        String tablePrefixForFields,
                        List<BaseDbF> hasKeys,
                        Boolean autoIncrease,
                        Boolean putAutoStamp) {
}
