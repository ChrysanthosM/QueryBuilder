package app.builder;

import com.google.common.base.Preconditions;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.context.annotation.Description;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface J2SQLShared {
    enum TypeOfSQLStatement {
        SQL_SELECT,
        SQL_DELETE,
        SQL_UPDATE,
        SQL_INSERT
    }

    enum PFX {
        A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, AA, AB, AC, AD, AE, AF, AG, AH, AI, AJ, AK, AL, AM, AN, AO, AP, AQ, AR, AS, AT, AU, AV, AW, AX, AY, AZ,
        T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, TD, TE, TF, TG, TH, TI, TJ, TK, TL, TM, TN, TO, TP, TQ, TR, TS, TT, TU, TV, TW, TX, TY, TZ,
        J0, J1, J2, J3, J4, J5, J6, J7, J8, J9, JA, JB, JC, JD, JE, JF, JG, JH, JI, JJ, JK, JL, JM, JN, JO, JP, JQ, JR, JS, JT, JU, JV, JW, JX, JY, JZ,
        ;
        public static SQLFieldFromPairOfTableField a0(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a1(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a2(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a3(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a4(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a5(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a6(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a7(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a8(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a9(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aa(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ab(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ac(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ad(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ae(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField af(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ag(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ah(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ai(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aj(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ak(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField al(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField am(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField an(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ao(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ap(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aq(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ar(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField as(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField at(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField au(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField av(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aw(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ax(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ay(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField az(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AZ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t0(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t1(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t2(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t3(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t4(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t5(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t6(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t7(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t8(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t9(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ta(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tb(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tc(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField td(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField te(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tf(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tg(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField th(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ti(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tj(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tk(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tl(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tm(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tn(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField to(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tp(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tq(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tr(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ts(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tt(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tu(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tv(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tw(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tx(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ty(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tz(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TZ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j0(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j1(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j2(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j3(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j4(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j5(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j6(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j7(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j8(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j9(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ja(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jb(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jc(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jd(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField je(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jf(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jg(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jh(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ji(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jj(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jk(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jl(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jm(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jn(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jo(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jp(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jq(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jr(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField js(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jt(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ju(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jv(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jw(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jx(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jy(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jz(@NonNull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JZ.name().concat(".")); }
    }

    static MutablePair<Object, SortOrder> asc(@NonNull Object addOrderBy) { return MutablePair.of(addOrderBy, SortOrder.ASCENDING); }
    static MutablePair<Object, SortOrder> desc(@NonNull Object addOrderBy) { return MutablePair.of(addOrderBy, SortOrder.DESCENDING); }


    static WhereBase not(@NonNull WhereBase filter) {
        ((AbstractFilter) filter).setInvertSelection(true);
        return filter;
    }

    //-------Create Filters
    @UtilityClass
    class Filter {
        @Description("Filter Where Value")
        public static WhereBase whereValue(@NonNull Object whereObject, @Nullable LinSQL.TypeOfComparison typeOfComparison, @Nullable Object compareValue) {
            return new ValueWhere(whereObject, typeOfComparison, compareValue);
        }

        @Description("Filter Where IN Values")
        public static WhereBase whereInValues(@NonNull Object whereObject, @NonNull List<Object> inValues) {
            return new InValuesWhere(whereObject, inValues);
        }
        @Description("Filter Where IN Values")
        public static WhereBase whereInValues(@NonNull Object whereObject, @NonNull Object... inValues) {
            return new InValuesWhere(whereObject, List.of(inValues));
        }

        @Description("Filter Where BETWEEN Values")
        public static WhereBase whereBetweenValues(@NonNull Object whereObject, @NonNull Object fromObject, @NonNull Object toObject) {
            return new BetweenValuesWhere(whereObject, MutablePair.of(fromObject, toObject));
        }

        @Description("Filter Where LIKE Values")
        public static WhereBase whereLikeValue(@NonNull Object whereObject, @Nullable String compareValue) {
            return new LikeValueWhere(whereObject, compareValue, null, null);
        }
        @Description("Filter Where LIKE Values")
        public static WhereBase whereLikeValue(@NonNull Object whereObject, @Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) {
            return new LikeValueWhere(whereObject, compareValue, escapeLeft, escapeRight);
        }

        @Description("Filter Where IN SubSelect")
        public static WhereBase whereInSubSelect(@NonNull Object whereObject, @NonNull String inSubSelect) {
            return new InSubSelectWhere(whereObject, inSubSelect);
        }

        @Description("Filter Where EXISTS")
        public static WhereBase whereExists(@NonNull String existQuery) {
            return new ExistsWhere(existQuery);
        }
        @Description("Filter Where EXISTS")
        public static WhereBase whereExists(@NonNull LinSQL existQuery) {
            return new ExistsWhere(existQuery.getSQL());
        }
    }


    //-------SQLFunctions
    @AllArgsConstructor
    final class SQLFunctionObject implements DeployFiltersBase, ProvideDataTypeForSQLBase {
        private final SQLFunction sqlFunction;
        private static SQLFunctionObject of(SQLFunction sqlFunction) { return new SQLFunctionObject(sqlFunction); }
        public Object getSqlFunction() { return sqlFunction; }

        @Override
        public Boolean getInQuotesRequirement() {
            return this.sqlFunction.getTypeOfSQLFunction().getInQuotesRequirement();
        }
    }

    static SQLFunctionObject TRIM(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.TRIM, arg)); }
    static SQLFunctionObject LTRIM(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.LTRIM, arg)); }
    static SQLFunctionObject RTRIM(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.RTRIM, arg)); }
    static SQLFunctionObject LENGTH(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.LENGTH, arg)); }
    static SQLFunctionObject UPPER(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.UPPER, arg)); }
    static SQLFunctionObject LOWER(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.LOWER, arg)); }
    static SQLFunctionObject INITCAP(@NonNull Object arg) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.INITCAP, arg)); }
    static SQLFunctionObject SPACE(int numOfSpaces) {
        Preconditions.checkArgument(numOfSpaces >= 0);
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.SPACE, numOfSpaces));
    }
    static SQLFunctionObject INSTR(@NonNull Object arg, @NonNull Object find) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.INSTR, Stream.of(arg, find).toArray())); }
    static SQLFunctionObject LEFT(@NonNull Object arg, int len) {
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.LEFT, Stream.of(arg, len).toArray()));
    }
    static SQLFunctionObject RIGHT(@NonNull Object arg, int len) {
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.RIGHT, Stream.of(arg, len).toArray()));
    }
    static SQLFunctionObject REPEAT(@NonNull Object arg, int times) {
        Preconditions.checkArgument(times > 0);
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.REPEAT, Stream.of(arg, times).toArray()));
    }
    static SQLFunctionObject REPLACE(@NonNull Object arg, @NonNull Object find, @NonNull Object replaceWith) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.REPLACE, Stream.of(arg, find, replaceWith).toArray())); }
    static SQLFunctionObject SUBSTR(@NonNull Object arg, int from, int len) {
        Preconditions.checkArgument(from > 0);
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.SUBSTR, Stream.of(arg, from, len).toArray()));
    }
    static SQLFunctionObject LPAD(@NonNull Object arg, @NonNull Object totalLength, @NonNull Object leadingChar) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.RPAD, Stream.of(arg, totalLength, leadingChar).toArray())); }
    static SQLFunctionObject RPAD(@NonNull Object arg, @NonNull Object totalLength, @NonNull Object trailingChar) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.RPAD, Stream.of(arg, totalLength, trailingChar).toArray())); }
    static SQLFunctionObject CONCAT(@NonNull Object... args) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.CONCAT, args)); }
    static SQLFunctionObject TRANSLATE(@NonNull Object arg, @NonNull Object find, @NonNull Object replaceWith, @Nullable Object pad) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.TRANSLATE, Stream.of(arg, find, replaceWith, pad).toArray())); }

    static SQLFunctionObject CASE(Boolean inQuotesRequirement, Object caseExpression, Object elseExpression, WhenBase... args) {
        List<Object> argList = new ArrayList<>(List.of(inQuotesRequirement, Optional.ofNullable(caseExpression), elseExpression));
        argList.addAll(Arrays.asList(args));
        return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.CASE, argList.toArray()));
    }
    static SQLFunctionObject CASE1s(@Nullable Object elseExpression, @NonNull WhenBase... args) { return CASE(true, null, elseExpression, args); }
    static SQLFunctionObject CASE1n(@Nullable Object elseExpression, @NonNull WhenBase... args) { return CASE(false, null, elseExpression, args); }
    static WhenBase WHEN(@NonNull WhereBase searchCondition, @NonNull Object thenExpression) { return new WhenThenSearched(searchCondition, thenExpression); }
    static SQLFunctionObject CASE2s(@NonNull Object caseExpression, @Nullable Object elseExpression, @NonNull WhenBase... args) { return CASE(true, caseExpression, elseExpression, args); }
    static SQLFunctionObject CASE2n(@NonNull Object caseExpression, @Nullable Object elseExpression, @NonNull WhenBase... args) { return CASE(false, caseExpression, elseExpression, args); }
    static WhenBase WHEN(@NonNull Object whenExpression, @NonNull Object thenExpression) { return new WhenThenSimple(whenExpression, thenExpression); }

    static SQLFunctionObject MIN(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.MIN, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject MIN(boolean distinct) { return MIN(null, distinct); }
    static SQLFunctionObject MIN(@Nullable Object arg) { return MIN(arg, false); }
    static SQLFunctionObject MIN() { return MIN(null, false); }
    static SQLFunctionObject MAX(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.MAX, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject MAX(boolean distinct) { return MAX(null, distinct); }
    static SQLFunctionObject MAX(@Nullable Object arg) { return MAX(arg, false); }
    static SQLFunctionObject MAX() { return MAX(null, false); }
    static SQLFunctionObject AVG(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.AVG, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject AVG(boolean distinct) { return AVG(null, distinct); }
    static SQLFunctionObject AVG(@Nullable Object arg) { return AVG(arg, false); }
    static SQLFunctionObject AVG() { return AVG(null, false); }
    static SQLFunctionObject COUNT(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.COUNT, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject COUNT(boolean distinct) { return COUNT(null, distinct); }
    static SQLFunctionObject COUNT(@Nullable Object arg) { return COUNT(arg, false); }
    static SQLFunctionObject COUNT() { return COUNT(null, false); }
    static SQLFunctionObject COUNT_BIG(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.COUNT_BIG, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject COUNT_BIG(boolean distinct) { return COUNT_BIG(null, distinct); }
    static SQLFunctionObject COUNT_BIG(@Nullable Object arg) { return COUNT_BIG(arg, false); }
    static SQLFunctionObject COUNT_BIG() { return COUNT_BIG(null, false); }
    static SQLFunctionObject SUM(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(DeploySQLFunctionsBase.create(DeploySQLFunctionsBase.TypeOfSQLFunction.SUM, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject SUM(boolean distinct) { return SUM(null, distinct); }
    static SQLFunctionObject SUM(@Nullable Object arg) { return SUM(arg, false); }
    static SQLFunctionObject SUM() { return SUM(null, false); }

}
