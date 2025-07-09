package qb.definition.db.system.sqlite.schema.structure;

import qb.definition.db.base.BaseDbField;
import qb.definition.db.base.ConfigDbField;
import qb.definition.db.base.DbFieldDataType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static qb.definition.db.base.DbFieldDataType.DATATYPE_INTEGER;
import static qb.definition.db.base.DbFieldDataType.DATATYPE_TEXT;

@Getter
public enum DbFieldSQLite implements BaseDbField {
    REC_ID("Sys_RecID", DATATYPE_INTEGER),
    USER_STAMP("Sys_UserStamp", DATATYPE_TEXT),
    DATE_STAMP("Sys_DateStamp", DATATYPE_TEXT),

    USER_NAME("Sys_UserName", DATATYPE_TEXT),
    USER_PASSWORD("Sys_Password", DATATYPE_TEXT),

    ENTITY_TYPE("Sys_EntityType", DATATYPE_TEXT),
    ENTITY_NUMBER("Sys_EntityNumber", DATATYPE_INTEGER),

    OPTION_TYPE("Sys_OptionType", DATATYPE_TEXT),
    OPTION_NAME("Sys_OptionName", DATATYPE_TEXT),
    OPTION_VALUE("Sys_OptionValue", DATATYPE_TEXT),
    OPTION_DETAILS("Sys_OptionDetails", DATATYPE_TEXT),

    ;

    private final ConfigDbField configDbField;
    DbFieldSQLite(String systemName) {
        this.configDbField = new ConfigDbField(systemName, null, null);
    }
    DbFieldSQLite(String systemName, DbFieldDataType fieldDataType) {
        this.configDbField = new ConfigDbField(systemName, fieldDataType, Arrays.stream(systemName.toLowerCase().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(StringUtils.SPACE)));
    }
    DbFieldSQLite(String systemName, DbFieldDataType fieldDataType, String asAlias) {
        this.configDbField = new ConfigDbField(systemName, fieldDataType, asAlias);
    }
    @Override
    public String systemName() {
        return this.configDbField.systemName();
    }
    @Override
    public DbFieldDataType fieldDataType() {
        return this.configDbField.fieldDataType();
    }
    @Override
    public String asAlias() {
        return this.configDbField.asAlias();
    }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.configDbField.fieldDataType().getInQuotesRequirement();
    }
}
