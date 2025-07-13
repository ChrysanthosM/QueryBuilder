package app.builder.core;

import lombok.Getter;
import app.builder.base.builder.BaseDbField;
import app.builder.base.builder.DbFieldDataType;

import java.util.List;

@Getter
final class DbField {
    private final BaseDbField dbfNameEnum;

    private final String dbfNormalName;
    private final String dbfSystemName;
    private final String dbfAsAlias;

    private final DbFieldDataType dbfDataType;
    private final Boolean dbfInQuotesRequirement;

    private final List<String> dbfAcceptedValues;

    DbField(BaseDbField dbF) {
        this.dbfNameEnum = dbF;

        this.dbfNormalName = this.dbfNameEnum.getName();
        this.dbfSystemName = this.dbfNameEnum.getSystemName();
        this.dbfAsAlias = this.dbfNameEnum.getAsAlias();

        this.dbfDataType = this.dbfNameEnum.getFieldDataType();
        this.dbfInQuotesRequirement = this.dbfDataType == null ? null : this.dbfDataType.getInQuotesRequirement();

        this.dbfAcceptedValues = this.dbfNameEnum.getAcceptedValues();
    }
}
