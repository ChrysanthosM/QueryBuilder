package app.builder.base.builder;

public record ConfigDbField(String systemName,
                            DbFieldDataType fieldDataType,
                            String asAlias) {
}
