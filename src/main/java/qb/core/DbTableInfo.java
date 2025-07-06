package qb.core;

import lombok.Getter;
import qb.definition.db.base.BaseDbF;
import qb.definition.db.base.BaseDbT;
import qb.definition.db.base.DbFieldDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
final class DbTableInfo {
    private final BaseDbT dbtNameEnum;

    private final String dbtNormalName;
    private final String dbtSystemName;

    private final List<BaseDbF> dbtHasKeys;
    private final Boolean dbtIsAutoIncrease;
    private final Boolean dbtPutAutoStamp;
    private final BaseDbF dbtUserStampDbF;
    private final BaseDbF dbtDateStampDbF;

    private final String dbtHasFieldsPrefix;
    private final List<BaseDbF> dbtHasDbFieldNamesEnum;
    private final List<DbField> dbtHasDbFields;

    private final List<String> dbtHasFieldsNormalNames;
    private final List<String> dbtHasFieldsSystemNames;
    private final List<String> dbtHasFieldsAsAlias;
    private final List<DbFieldDataType> dbtHasFieldsDataType ;
    private final List<Boolean> dbtHasInQuotesRequirement;

    private final Map<BaseDbF, String> dbtHasFieldsNameEnum_NormalName;
    private final Map<BaseDbF, String> dbtHasFieldsNameEnum_SystemName;
    private final Map<BaseDbF, String> dbtHasFieldsNameEnum_AsAlias;
    private final Map<BaseDbF, DbFieldDataType> dbtHasFieldsNameEnum_DataType;
    private final Map<? extends BaseDbF, ? extends Boolean> dbtHasFieldsNameEnum_InQuotesRequirement;

    DbTableInfo(DbTable dbTable) {
        this.dbtNameEnum = dbTable.getDbT();

        this.dbtNormalName = this.dbtNameEnum.getName();
        this.dbtSystemName = dbTable.getSystemName();

        this.dbtHasKeys = dbTable.getHasKeys();
        this.dbtIsAutoIncrease = dbTable.getAutoIncrease();
        this.dbtPutAutoStamp = dbTable.getPutAutoStamp();
        this.dbtUserStampDbF = this.dbtNameEnum.getUserStampDbF();
        this.dbtDateStampDbF = this.dbtNameEnum.getDateStampDbF();

        this.dbtHasFieldsPrefix = dbTable.getTablePrefixForFields();
        this.dbtHasDbFieldNamesEnum = dbTable.getDbFs().stream().map(PairOfTableField::getDbf).toList();

        this.dbtHasDbFields = this.dbtHasDbFieldNamesEnum.stream().map(DbFieldInstances::getMapTableInstance).toList();
        this.dbtHasFieldsNormalNames = this.dbtHasDbFields.stream().map(DbField::getDbfNormalName).toList();
        this.dbtHasFieldsSystemNames = this.dbtHasDbFields.stream().map(DbField::getDbfSystemName).toList();
        this.dbtHasFieldsAsAlias = this.dbtHasDbFields.stream().map(DbField::getDbfAsAlias).toList();
        this.dbtHasFieldsDataType = this.dbtHasDbFields.stream().map(DbField::getDbfDataType).toList();
        this.dbtHasInQuotesRequirement = this.dbtHasDbFields.stream().map(DbField::getDbfInQuotesRequirement).toList();

        this.dbtHasFieldsNameEnum_NormalName = Map.copyOf((Map<? extends BaseDbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsNormalNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_SystemName = Map.copyOf((Map<? extends BaseDbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsSystemNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_AsAlias = Map.copyOf((Map<? extends BaseDbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsAsAlias::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_DataType = Map.copyOf((Map<? extends BaseDbF, ? extends DbFieldDataType>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsDataType::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_InQuotesRequirement = Map.copyOf((Map<? extends BaseDbF, ? extends Boolean>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasInQuotesRequirement::get, (existing, replacement) -> existing, HashMap::new)));
    }
}
