package j2sql.definition.db.base;

public record ConfigDbField(String systemName,
                            DbFieldDataType fieldDataType,
                            String asAlias) {
}
