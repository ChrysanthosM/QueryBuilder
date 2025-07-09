package j2sql.core;

import lombok.Getter;
import j2sql.definition.db.base.BaseDbField;
import j2sql.definition.db.base.BaseDbTable;
import j2sql.definition.db.base.DbFieldDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
final class DbTableInfo {
    private final BaseDbTable dbtNameEnum;

    private final String dbtNormalName;
    private final String dbtSystemName;

    private final List<BaseDbField> dbtHasKeys;
    private final Boolean dbtIsAutoIncrease;
    private final Boolean dbtPutAutoStamp;
    private final BaseDbField dbtUserStampDbF;
    private final BaseDbField dbtDateStampDbF;

    private final String dbtHasFieldsPrefix;
    private final List<BaseDbField> dbtHasDbFieldNamesEnum;
    private final List<DbField> dbtHasDbFields;

    private final List<String> dbtHasFieldsNormalNames;
    private final List<String> dbtHasFieldsSystemNames;
    private final List<String> dbtHasFieldsAsAlias;
    private final List<DbFieldDataType> dbtHasFieldsDataType ;
    private final List<Boolean> dbtHasInQuotesRequirement;

    private final Map<BaseDbField, String> dbtHasFieldsNameEnum_NormalName;
    private final Map<BaseDbField, String> dbtHasFieldsNameEnum_SystemName;
    private final Map<BaseDbField, String> dbtHasFieldsNameEnum_AsAlias;
    private final Map<BaseDbField, DbFieldDataType> dbtHasFieldsNameEnum_DataType;
    private final Map<? extends BaseDbField, ? extends Boolean> dbtHasFieldsNameEnum_InQuotesRequirement;

    DbTableInfo(DbTable dbTable) {
        this.dbtNameEnum = dbTable.getBaseDbTable();

        this.dbtNormalName = this.dbtNameEnum.getName();
        this.dbtSystemName = dbTable.getSystemName();

        this.dbtHasKeys = dbTable.getHasKeys();
        this.dbtIsAutoIncrease = dbTable.getAutoIncrease();
        this.dbtPutAutoStamp = dbTable.getPutAutoStamp();
        this.dbtUserStampDbF = this.dbtNameEnum.getUserStampDbF();
        this.dbtDateStampDbF = this.dbtNameEnum.getDateStampDbF();

        this.dbtHasFieldsPrefix = dbTable.getTablePrefixForFields();
        this.dbtHasDbFieldNamesEnum = dbTable.getDbFs().stream().map(PairOfTableField::getBaseDbField).toList();

        this.dbtHasDbFields = this.dbtHasDbFieldNamesEnum.stream().map(DbFieldInstances::getMapTableInstance).toList();
        this.dbtHasFieldsNormalNames = this.dbtHasDbFields.stream().map(DbField::getDbfNormalName).toList();
        this.dbtHasFieldsSystemNames = this.dbtHasDbFields.stream().map(DbField::getDbfSystemName).toList();
        this.dbtHasFieldsAsAlias = this.dbtHasDbFields.stream().map(DbField::getDbfAsAlias).toList();
        this.dbtHasFieldsDataType = this.dbtHasDbFields.stream().map(DbField::getDbfDataType).toList();
        this.dbtHasInQuotesRequirement = this.dbtHasDbFields.stream().map(DbField::getDbfInQuotesRequirement).toList();

        this.dbtHasFieldsNameEnum_NormalName = Map.copyOf((Map<? extends BaseDbField, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsNormalNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_SystemName = Map.copyOf((Map<? extends BaseDbField, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsSystemNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_AsAlias = Map.copyOf((Map<? extends BaseDbField, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsAsAlias::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_DataType = Map.copyOf((Map<? extends BaseDbField, ? extends DbFieldDataType>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsDataType::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_InQuotesRequirement = Map.copyOf((Map<? extends BaseDbField, ? extends Boolean>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasInQuotesRequirement::get, (existing, replacement) -> existing, HashMap::new)));
    }
}
