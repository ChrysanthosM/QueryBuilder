package app.test;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.builder.base.datasource.WorkWithDataSource;
import app.builder.core.J2SQL;
import app.distribution.sqlite.schema.structure.DbFieldValuesSQLite;
import app.distribution.sqlite.schema.table.AutoNumberingTable;
import app.distribution.sqlite.schema.table.OptionsTable;

import java.util.List;

import static app.builder.core.J2SQL.*;
import static app.builder.core.J2SQLShared.*;
import static app.builder.core.J2SQLShared.PFX.*;


@SpringBootTest
class TestSQLStatementsSQLite {
    private final List<String> stmts = Lists.newArrayList();
    private @Autowired WorkWithDataSource workDataSource;
    private @Autowired AutoNumberingTable autoNumberingTable;
    private @Autowired OptionsTable optionsTable;

    private String checkResult(String stmt, String shouldBe) {
        boolean areSame = StringUtils.equalsIgnoreCase(stmt, shouldBe);
        return areSame + " - " + stmt;
    }

    @Test
    void testSQLStatements() {
        long startTime = System.currentTimeMillis();
        testSQLStatementsMain(true);
        testSQLStatementsMain(false);
        long durationTime = System.currentTimeMillis() - startTime;
        stmts.forEach(System.out::println);
        System.out.println("Test finished in " + durationTime + " ms");
    }
    private void testSQLStatementsMain(boolean normalizeNames) {
        stmts.add("****************************** Start Test with normalizeNames: " + normalizeNames + " ******************************");

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).setApplyAutoAlias().insertInto(autoNumberingTable).insertRow("AAA", 1)
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO AUTO_NUMBERING (ENTITY_TYPE, ENTITY_NUMBER) VALUES ('AAA', 1)"
                        : "INSERT INTO Sys_AutoNumbering (AASys_EntityType, AASys_EntityNumber) VALUES ('AAA', 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).insertInto(autoNumberingTable).insertRows(List.of("AAA", 1), List.of("BBB", 2), List.of("CCC", 3))
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO AUTO_NUMBERING (ENTITY_TYPE, ENTITY_NUMBER) VALUES ('AAA', 1), ('BBB', 2), ('CCC', 3)"
                        : "INSERT INTO Sys_AutoNumbering (AASys_EntityType, AASys_EntityNumber) VALUES ('AAA', 1), ('BBB', 2), ('CCC', 3)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).insertInto(autoNumberingTable).insertRowsFrom(
                        J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE, autoNumberingTable.ENTITY_NUMBER).where(autoNumberingTable.ENTITY_NUMBER.eq(1)))
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO AUTO_NUMBERING SELECT ENTITY_TYPE, ENTITY_NUMBER FROM AUTO_NUMBERING WHERE (ENTITY_NUMBER = 1)"
                        : "INSERT INTO Sys_AutoNumbering SELECT AASys_EntityType, AASys_EntityNumber FROM Sys_AutoNumbering WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).distinct().setApplyAutoAlias()
                        .where(autoNumberingTable.ENTITY_TYPE.eq(1))
                        .getSQL(),
                normalizeNames
                        ? "SELECT DISTINCT REC_ID AS \"Sys_Recid\", ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"Sys_Entitynumber\" FROM AUTO_NUMBERING WHERE (ENTITY_TYPE = '1')"
                        : "SELECT DISTINCT AASys_RecID AS \"Sys_Recid\", AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"Sys_Entitynumber\" FROM Sys_AutoNumbering WHERE (AASys_EntityType = '1')"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE, autoNumberingTable.ENTITY_NUMBER, operation(autoNumberingTable.ENTITY_NUMBER, "+1")).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"Sys_Entitynumber\", ENTITY_NUMBER +1 FROM AUTO_NUMBERING"
                        : "SELECT AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"Sys_Entitynumber\", AASys_EntityNumber +1 FROM Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE.as("asTheAlias"))
                        .getSQL(),
                normalizeNames
                        ? "SELECT ENTITY_TYPE AS \"asTheAlias\" FROM AUTO_NUMBERING"
                        : "SELECT AASys_EntityType AS \"asTheAlias\" FROM Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).selectAll()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM AUTO_NUMBERING AS T0"
                        : "SELECT T0.* FROM Sys_AutoNumbering AS T0"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).select(autoNumberingTable.ALL)
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM AUTO_NUMBERING AS T0"
                        : "SELECT T0.* FROM Sys_AutoNumbering AS T0"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).deleteFrom(autoNumberingTable).where(autoNumberingTable.ENTITY_NUMBER.eq(1))
                        .getSQL(),
                normalizeNames
                        ? "DELETE FROM AUTO_NUMBERING WHERE (ENTITY_NUMBER = 1)"
                        : "DELETE FROM Sys_AutoNumbering WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0))
                        .whereExists(J2SQL.create(workDataSource, normalizeNames).from(optionsTable.as(A1)).selectAll().where(t0(autoNumberingTable.ENTITY_TYPE).eq(a1(optionsTable.OPTION_TYPE)))).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.REC_ID AS \"Sys_Recid\", T0.ENTITY_TYPE AS \"Sys_Entitytype\", T0.ENTITY_NUMBER AS \"Sys_Entitynumber\" FROM AUTO_NUMBERING AS T0 " +
                        "WHERE EXISTS (SELECT A1.* FROM OPTIONS AS A1 WHERE (T0.ENTITY_TYPE = A1.OPTION_TYPE))"
                        : "SELECT T0.AASys_RecID AS \"Sys_Recid\", T0.AASys_EntityType AS \"Sys_Entitytype\", T0.AASys_EntityNumber AS \"Sys_Entitynumber\" FROM Sys_AutoNumbering AS T0 " +
                        "WHERE EXISTS (SELECT A1.* FROM Sys_Options AS A1 WHERE (T0.AASys_EntityType = A1.ABSys_OptionType))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(autoNumberingTable)
                        .select(autoNumberingTable.ENTITY_TYPE, autoNumberingTable.ENTITY_NUMBER.as("asAlias"), "const1", 2, 4.5,
                                asAlias(5, "asAlias5"), CONCAT(autoNumberingTable.ENTITY_TYPE, "AAA", 1, LTRIM(autoNumberingTable.ENTITY_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"asAlias\", 'const1', '2', '4.5', '5' AS \"asAlias5\", " +
                        "ENTITY_TYPE AS \"Sys_Entitytype\" || 'AAA' || '1' || LTRIM(ENTITY_TYPE AS \"Sys_Entitytype\") " +
                        "FROM AUTO_NUMBERING"
                        : "SELECT AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"asAlias\", 'const1', '2', '4.5', '5' AS \"asAlias5\", " +
                        "AASys_EntityType AS \"Sys_Entitytype\" || 'AAA' || '1' || LTRIM(AASys_EntityType AS \"Sys_Entitytype\") " +
                        "FROM Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(autoNumberingTable.as("T"))
                        .select(asAlias(MIN(autoNumberingTable.ENTITY_TYPE), "functionAsAlias"))
                        .orderBy(autoNumberingTable.ENTITY_TYPE).limitOffset(1, 2)
                        .getSQL(),
                normalizeNames
                        ? "SELECT (MIN(T.ENTITY_TYPE) AS \"functionAsAlias\") FROM AUTO_NUMBERING AS T ORDER BY T.ENTITY_TYPE ASC LIMIT 1 OFFSET 2"
                        : "SELECT (MIN(T.AASys_EntityType) AS \"functionAsAlias\") FROM Sys_AutoNumbering AS T ORDER BY T.AASys_EntityType ASC LIMIT 1 OFFSET 2"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable)
                        .select(autoNumberingTable.ENTITY_TYPE, COUNT())
                        .groupBy(autoNumberingTable.ENTITY_TYPE).having(COUNT().gt(1))
                        .orderBy(autoNumberingTable.ENTITY_TYPE.desc(), autoNumberingTable.ENTITY_NUMBER.asc())
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT ENTITY_TYPE AS \"Sys_Entitytype\", COUNT(*) FROM AUTO_NUMBERING GROUP BY ENTITY_TYPE HAVING COUNT(*) > 1 ORDER BY ENTITY_TYPE DESC, ENTITY_NUMBER ASC"
                        : "SELECT AASys_EntityType AS \"Sys_Entitytype\", COUNT(*) FROM Sys_AutoNumbering GROUP BY AASys_EntityType HAVING COUNT(*) > 1 ORDER BY AASys_EntityType DESC, AASys_EntityNumber ASC"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).selectAll()
                        .leftJoin(optionsTable.as(J1)).fromJoinSelectOnly(optionsTable.ALL).on(j1(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.* FROM AUTO_NUMBERING AS T0 LEFT JOIN OPTIONS AS J1 ON (J1.ENTITY_TYPE = T0.OPTION_TYPE)"
                        : "SELECT T0.*, J1.* FROM Sys_AutoNumbering AS T0 LEFT JOIN Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0))
                        .leftJoin(optionsTable.as(J1)).on(j1(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.REC_ID AS \"Sys_Recid\", T0.ENTITY_TYPE AS \"Sys_Entitytype\", T0.ENTITY_NUMBER AS \"Sys_Entitynumber\", J1.REC_ID AS \"Sys_Recid\", J1.OPTION_TYPE AS \"Sys_Optiontype\", J1.OPTION_NAME AS \"Sys_Optionname\", J1.OPTION_VALUE AS \"Sys_Optionvalue\", J1.OPTION_DETAILS AS \"Sys_Optiondetails\" FROM AUTO_NUMBERING AS T0 LEFT JOIN OPTIONS AS J1 ON (J1.ENTITY_TYPE = T0.OPTION_TYPE)"
                        : "SELECT T0.AASys_RecID AS \"Sys_Recid\", T0.AASys_EntityType AS \"Sys_Entitytype\", T0.AASys_EntityNumber AS \"Sys_Entitynumber\", J1.ABSys_RecID AS \"Sys_Recid\", J1.ABSys_OptionType AS \"Sys_Optiontype\", J1.ABSys_OptionName AS \"Sys_Optionname\", J1.ABSys_OptionValue AS \"Sys_Optionvalue\", J1.ABSys_OptionDetails AS \"Sys_Optiondetails\" FROM Sys_AutoNumbering AS T0 LEFT JOIN Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).select(autoNumberingTable.ALL)
                        .leftJoin(optionsTable.as(J1)).fromJoinSelectOnly(optionsTable.ALL).on(j1(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE))).addJoinFilters(optionsTable.OPTION_TYPE.gt(1).and(optionsTable.OPTION_TYPE.lt(1000)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.* FROM AUTO_NUMBERING AS T0 LEFT JOIN OPTIONS AS J1 ON (J1.ENTITY_TYPE = T0.OPTION_TYPE) WHERE (J1.OPTION_TYPE > '1' AND J1.OPTION_TYPE < '1000')"
                        : "SELECT T0.*, J1.* FROM Sys_AutoNumbering AS T0 LEFT JOIN Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType) WHERE (J1.ABSys_OptionType > '1' AND J1.ABSys_OptionType < '1000')"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).select(autoNumberingTable.ALL)
                        .leftJoin(optionsTable.as(J1)).fromJoinSelectOnly(optionsTable.ALL).on(j1(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE))).addJoinFilters(optionsTable.OPTION_TYPE.gt(1).and(optionsTable.OPTION_TYPE.lt(1000)))
                        .leftJoin(optionsTable.as(J2)).fromJoinSelectOnly(optionsTable.ALL).on(j2(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE))).addJoinFilters(optionsTable.OPTION_TYPE.gt(1001).and(optionsTable.OPTION_TYPE.lt(2000)))
                        .leftJoin(optionsTable.as(J3)).fromJoinSelectOnly(optionsTable.ALL).on(j3(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE))).addJoinFilters(optionsTable.OPTION_TYPE.gt(2001).and(optionsTable.OPTION_TYPE.lt(3000)))
                        .leftJoin(optionsTable.as(J4)).fromJoinSelectOnly(optionsTable.ALL).on(j4(autoNumberingTable.ENTITY_TYPE).eq(t0(optionsTable.OPTION_TYPE))).addJoinFilters(optionsTable.OPTION_TYPE.gt(3001).and(optionsTable.OPTION_TYPE.lt(4000)))
                        .where(autoNumberingTable.ENTITY_NUMBER.gt(0))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.*, J2.*, J3.*, J4.* FROM AUTO_NUMBERING AS T0 " +
                        "LEFT JOIN OPTIONS AS J1 ON (J1.ENTITY_TYPE = T0.OPTION_TYPE) " +
                        "LEFT JOIN OPTIONS AS J2 ON (J2.ENTITY_TYPE = T0.OPTION_TYPE) " +
                        "LEFT JOIN OPTIONS AS J3 ON (J3.ENTITY_TYPE = T0.OPTION_TYPE) " +
                        "LEFT JOIN OPTIONS AS J4 ON (J4.ENTITY_TYPE = T0.OPTION_TYPE) " +
                        "WHERE (T0.ENTITY_NUMBER > 0) AND ((J1.OPTION_TYPE > '1' AND J1.OPTION_TYPE < '1000') AND (J2.OPTION_TYPE > '1001' AND J2.OPTION_TYPE < '2000') AND (J3.OPTION_TYPE > '2001' AND J3.OPTION_TYPE < '3000') AND (J4.OPTION_TYPE > '3001' AND J4.OPTION_TYPE < '4000'))"
                        : "SELECT T0.*, J1.*, J2.*, J3.*, J4.* FROM Sys_AutoNumbering AS T0 " +
                        "LEFT JOIN Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN Sys_Options AS J2 ON (J2.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN Sys_Options AS J3 ON (J3.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN Sys_Options AS J4 ON (J4.AASys_EntityType = T0.ABSys_OptionType) " +
                        "WHERE (T0.AASys_EntityNumber > 0) AND ((J1.ABSys_OptionType > '1' AND J1.ABSys_OptionType < '1000') AND (J2.ABSys_OptionType > '1001' AND J2.ABSys_OptionType < '2000') AND (J3.ABSys_OptionType > '2001' AND J3.ABSys_OptionType < '3000') AND (J4.ABSys_OptionType > '3001' AND J4.ABSys_OptionType < '4000'))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).where(autoNumberingTable.ENTITY_TYPE.eq("?")).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT REC_ID AS \"Sys_Recid\", ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"Sys_Entitynumber\" FROM AUTO_NUMBERING WHERE (ENTITY_TYPE = ?)"
                        : "SELECT AASys_RecID AS \"Sys_Recid\", AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"Sys_Entitynumber\" FROM Sys_AutoNumbering WHERE (AASys_EntityType = ?)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE)
                        .UNION(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE))
                        .UNION(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT ENTITY_TYPE AS \"Sys_Entitytype\" FROM AUTO_NUMBERING " +
                        "UNION SELECT ENTITY_TYPE AS \"Sys_Entitytype\" FROM AUTO_NUMBERING " +
                        "UNION SELECT ENTITY_TYPE AS \"Sys_Entitytype\" FROM AUTO_NUMBERING"
                        : "SELECT AASys_EntityType AS \"Sys_Entitytype\" FROM Sys_AutoNumbering " +
                        "UNION SELECT AASys_EntityType AS \"Sys_Entitytype\" FROM Sys_AutoNumbering " +
                        "UNION SELECT AASys_EntityType AS \"Sys_Entitytype\" FROM Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable.as(T0)).select(autoNumberingTable.ALL).attachComments("sample comment")
                        .setApplyAutoAlias().getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM AUTO_NUMBERING AS T0 /*sample comment*/"
                        : "SELECT T0.* FROM Sys_AutoNumbering AS T0 /*sample comment*/"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).distinct().from(autoNumberingTable).where(autoNumberingTable.ENTITY_NUMBER.eq(1)).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT DISTINCT REC_ID AS \"Sys_Recid\", ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"Sys_Entitynumber\" FROM AUTO_NUMBERING WHERE (ENTITY_NUMBER = 1)"
                        : "SELECT DISTINCT AASys_RecID AS \"Sys_Recid\", AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"Sys_Entitynumber\" FROM Sys_AutoNumbering WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable)
                        .where(autoNumberingTable.ENTITY_TYPE.eq(DbFieldValuesSQLite.ValuesForEntityType.SURROGATE_NUM))
                        .and(autoNumberingTable.ENTITY_TYPE.inSubSelect(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE).getSQL()))
                        .and(not(autoNumberingTable.ENTITY_TYPE.like("AB%")))
                        .and(autoNumberingTable.ENTITY_TYPE.between(11, 22).or(autoNumberingTable.ENTITY_NUMBER.between(1, 2)))
                        .or(autoNumberingTable.ENTITY_TYPE.in(List.of(1, 2)))
                        .or(autoNumberingTable.ENTITY_TYPE.in(3, 4))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT REC_ID AS \"Sys_Recid\", ENTITY_TYPE AS \"Sys_Entitytype\", ENTITY_NUMBER AS \"Sys_Entitynumber\" FROM AUTO_NUMBERING " +
                        "WHERE (ENTITY_TYPE = 'E02') " +
                        "AND (ENTITY_TYPE IN (SELECT ENTITY_TYPE FROM AUTO_NUMBERING)) " +
                        "AND (ENTITY_TYPE NOT LIKE 'AB%') " +
                        "AND (ENTITY_TYPE BETWEEN '11' AND '22' OR ENTITY_NUMBER BETWEEN 1 AND 2) " +
                        "OR (ENTITY_TYPE IN ('1', '2')) " +
                        "OR (ENTITY_TYPE IN ('3', '4'))"
                        : "SELECT AASys_RecID AS \"Sys_Recid\", AASys_EntityType AS \"Sys_Entitytype\", AASys_EntityNumber AS \"Sys_Entitynumber\" FROM Sys_AutoNumbering " +
                        "WHERE (AASys_EntityType = 'E02') " +
                        "AND (AASys_EntityType IN (SELECT AASys_EntityType FROM Sys_AutoNumbering)) " +
                        "AND (AASys_EntityType NOT LIKE 'AB%') " +
                        "AND (AASys_EntityType BETWEEN '11' AND '22' OR AASys_EntityNumber BETWEEN 1 AND 2) " +
                        "OR (AASys_EntityType IN ('1', '2')) " +
                        "OR (AASys_EntityType IN ('3', '4'))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(autoNumberingTable.as(T0))
                        .select(autoNumberingTable.ENTITY_NUMBER.as("asEntNum"))
                        .where(autoNumberingTable.ENTITY_NUMBER.gt(1)).andNot(autoNumberingTable.ENTITY_TYPE.like("Α%")).and(autoNumberingTable.ENTITY_TYPE.notLike("B%"))
                        .and(autoNumberingTable.ENTITY_NUMBER.between(2, 2000))
                        .and(autoNumberingTable.ENTITY_TYPE.inSubSelect(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable).select(autoNumberingTable.ENTITY_TYPE).getSQL()))
                        .and(autoNumberingTable.ENTITY_NUMBER.eq(1), autoNumberingTable.ENTITY_NUMBER.eq(2), autoNumberingTable.ENTITY_NUMBER.eq(3), autoNumberingTable.ENTITY_NUMBER.eq(4))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.ENTITY_NUMBER AS \"asEntNum\" FROM AUTO_NUMBERING AS T0 " +
                        "WHERE (T0.ENTITY_NUMBER > 1) " +
                        "AND NOT (T0.ENTITY_TYPE LIKE 'Α%') " +
                        "AND (T0.ENTITY_TYPE NOT LIKE 'B%') " +
                        "AND (T0.ENTITY_NUMBER BETWEEN 2 AND 2000) " +
                        "AND (T0.ENTITY_TYPE IN (SELECT ENTITY_TYPE FROM AUTO_NUMBERING)) " +
                        "AND (T0.ENTITY_NUMBER = 1 AND T0.ENTITY_NUMBER = 2 AND T0.ENTITY_NUMBER = 3 AND T0.ENTITY_NUMBER = 4)"
                        : "SELECT T0.AASys_EntityNumber AS \"asEntNum\" FROM Sys_AutoNumbering AS T0 " +
                        "WHERE (T0.AASys_EntityNumber > 1) " +
                        "AND NOT (T0.AASys_EntityType LIKE 'Α%') " +
                        "AND (T0.AASys_EntityType NOT LIKE 'B%') " +
                        "AND (T0.AASys_EntityNumber BETWEEN 2 AND 2000) " +
                        "AND (T0.AASys_EntityType IN (SELECT AASys_EntityType FROM Sys_AutoNumbering)) " +
                        "AND (T0.AASys_EntityNumber = 1 AND T0.AASys_EntityNumber = 2 AND T0.AASys_EntityNumber = 3 AND T0.AASys_EntityNumber = 4)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(autoNumberingTable.as(T0))
                        .select(autoNumberingTable.ENTITY_NUMBER.as("asEntNum"))
                        .where(autoNumberingTable.ENTITY_TYPE.like("Α%").or(autoNumberingTable.ENTITY_TYPE.like("B%").or(autoNumberingTable.ENTITY_TYPE.like("C%").or(autoNumberingTable.ENTITY_TYPE.like("D%").or(autoNumberingTable.ENTITY_TYPE.like("E%"))))))
                        .or(autoNumberingTable.ENTITY_TYPE.eq(1).or(autoNumberingTable.ENTITY_TYPE.eq(2).and(autoNumberingTable.ENTITY_NUMBER.gt(0))))
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.ENTITY_NUMBER AS \"asEntNum\" FROM AUTO_NUMBERING AS T0 " +
                        "WHERE (T0.ENTITY_TYPE LIKE 'Α%' OR T0.ENTITY_TYPE LIKE 'B%' OR T0.ENTITY_TYPE LIKE 'C%' OR T0.ENTITY_TYPE LIKE 'D%' OR T0.ENTITY_TYPE LIKE 'E%') " +
                        "OR (T0.ENTITY_TYPE = '1' OR T0.ENTITY_TYPE = '2' AND T0.ENTITY_NUMBER > 0)"
                        : "SELECT T0.AASys_EntityNumber AS \"asEntNum\" FROM Sys_AutoNumbering AS T0 " +
                        "WHERE (T0.AASys_EntityType LIKE 'Α%' OR T0.AASys_EntityType LIKE 'B%' OR T0.AASys_EntityType LIKE 'C%' OR T0.AASys_EntityType LIKE 'D%' OR T0.AASys_EntityType LIKE 'E%') " +
                        "OR (T0.AASys_EntityType = '1' OR T0.AASys_EntityType = '2' AND T0.AASys_EntityNumber > 0)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(autoNumberingTable)
                        .select(CASE1n(11,
                                WHEN(autoNumberingTable.ENTITY_TYPE.eq(DbFieldValuesSQLite.ValuesForEntityType.SURROGATE_NUM), 22),
                                WHEN(autoNumberingTable.ENTITY_TYPE.lt(DbFieldValuesSQLite.ValuesForEntityType.SURROGATE_NUM), 33)))
                        .select(CASE2s(autoNumberingTable.ENTITY_TYPE, "11",
                                WHEN(2, "22"),
                                WHEN(3, "33")))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT CASE " +
                            "WHEN ENTITY_TYPE = 'E02' THEN 22 " +
                            "WHEN ENTITY_TYPE < 'E02' THEN 33 " +
                            "ELSE 11 END, " +
                        "CASE ENTITY_TYPE AS \"Sys_Entitytype\" " +
                            "WHEN '2' THEN '22' " +
                            "WHEN '3' THEN '33' " +
                            "ELSE '11' END FROM AUTO_NUMBERING"
                        : "SELECT CASE " +
                            "WHEN AASys_EntityType = 'E02' THEN 22 " +
                            "WHEN AASys_EntityType < 'E02' THEN 33 " +
                            "ELSE 11 END, " +
                        "CASE AASys_EntityType AS \"Sys_Entitytype\" " +
                            "WHEN '2' THEN '22' " +
                            "WHEN '3' THEN '33' " +
                            "ELSE '11' END FROM Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(autoNumberingTable).updateFieldSetValue(autoNumberingTable.ENTITY_NUMBER, optionsTable.OPTION_VALUE)
                        .getSQL(),
                normalizeNames
                        ? "UPDATE AUTO_NUMBERING SET ENTITY_NUMBER = OPTION_VALUE"
                        : "UPDATE Sys_AutoNumbering SET AASys_EntityNumber = ABSys_OptionValue"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(autoNumberingTable).updateFieldSetValue(autoNumberingTable.ENTITY_NUMBER, 1)
                        .getSQL(),
                normalizeNames
                        ? "UPDATE AUTO_NUMBERING SET ENTITY_NUMBER = 1"
                        : "UPDATE Sys_AutoNumbering SET AASys_EntityNumber = 1"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(autoNumberingTable).updateFieldSetValue(autoNumberingTable.ENTITY_NUMBER, operation(autoNumberingTable.ENTITY_NUMBER, "+1"))
                        .getSQL(),
                normalizeNames
                        ? "UPDATE AUTO_NUMBERING SET ENTITY_NUMBER = ENTITY_NUMBER +1"
                        : "UPDATE Sys_AutoNumbering SET AASys_EntityNumber = AASys_EntityNumber +1"));
    }
}
