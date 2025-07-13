package app.base.builder;

public record ConfigDbField(String systemName,
                            DbFieldDataType fieldDataType,
                            String asAlias) {
}
