package qb.definition.db.sqlite.schema.structure;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import qb.core.IDeployFilters;
import qb.core.IDeployOrdering;
import qb.core.IProvideDataTypeForSQL;
import qb.definition.db.base.DbFieldDataType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static qb.definition.db.base.DbFieldDataType.*;

@Getter
public enum DbF implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    ALL("*"),

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

    private final String systemName;
    private final DbFieldDataType fieldDataType;
    private final String asAlias;

    DbF(String systemName) {
        this.systemName = systemName;
        this.fieldDataType = null;
        this.asAlias = null;
    }
    DbF(String systemName, DbFieldDataType fieldDataType) {
        this.systemName = systemName;
        this.fieldDataType = fieldDataType;
        this.asAlias = Arrays.stream(this.systemName.toLowerCase().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(StringUtils.SPACE));
    }
    DbF(String systemName, DbFieldDataType fieldDataType, String asAlias) {
        this.systemName = systemName;
        this.fieldDataType = fieldDataType;
        this.asAlias = asAlias;
    }

    public List<String> getAcceptedValues() {
        return DbFValues.getValues(this);
    }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.getFieldDataType().getInQuotesRequirement();
    }
}
