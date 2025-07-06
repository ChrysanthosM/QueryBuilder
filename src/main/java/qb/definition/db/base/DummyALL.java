package qb.definition.db.base;

public class DummyALL implements BaseDbField {
    @Override
    public String systemName() {
        return "*";
    }

    @Override
    public DbFieldDataType fieldDataType() {
        return null;
    }

    @Override
    public String asAlias() {
        return null;
    }

    @Override
    public Boolean getInQuotesRequirement() {
        return null;
    }
}
