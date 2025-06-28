package qb.core;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

final class BuildSQLOrderBy extends BuildSQLCore {

    static BuildSQLOrderBy createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLOrderBy(forSQLRetrieverForDB); }
    private BuildSQLOrderBy(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<MutablePair<SqlUserSelection, SortOrder>> orderByFields = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) return;

        List<String> orderByList = new ArrayList<>();
        for (MutablePair<SqlUserSelection, SortOrder> pair : orderByFields) {
            if (pair.left != null) {
                pair.left.setIgnoreTableAsAlias();
                String selectedFieldForSQL = pair.left.getResolveObjectForSQL(forSQLRetrieverForDB);
                if (StringUtils.isNotBlank(selectedFieldForSQL)) {
                    if (pair.right != null) {
                        selectedFieldForSQL = selectedFieldForSQL.concat(StringUtils.SPACE)
                                .concat(forSQLRetrieverForDB.getOrderByType().get(pair.right));
                    }
                    orderByList.add(selectedFieldForSQL);
                }
            }
        }
        super.setStringForSQL("ORDER BY " + String.join(", ", orderByList));
    }
}
