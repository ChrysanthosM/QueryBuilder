package qb.definition.db.system.sqlite.schema.structure;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.ConfigDbF;
import qb.definition.db.base.DbFieldDataType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static qb.definition.db.base.DbFieldDataType.*;

@Getter
public enum DbF implements BaseDbF {
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

    private final ConfigDbF configDbF;
    DbF(String systemName) {
        this.configDbF = new ConfigDbF(systemName, null, null);
    }
    DbF(String systemName, DbFieldDataType fieldDataType) {
        this.configDbF = new ConfigDbF(systemName, fieldDataType, Arrays.stream(systemName.toLowerCase().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(StringUtils.SPACE)));
    }
    DbF(String systemName, DbFieldDataType fieldDataType, String asAlias) {
        this.configDbF = new ConfigDbF(systemName, fieldDataType, asAlias);
    }
    @Override
    public String systemName() {
        return this.configDbF.systemName();
    }
    @Override
    public DbFieldDataType fieldDataType() {
        return this.configDbF.fieldDataType();
    }
    @Override
    public String asAlias() {
        return this.configDbF.asAlias();
    }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.configDbF.fieldDataType().getInQuotesRequirement();
    }
}
