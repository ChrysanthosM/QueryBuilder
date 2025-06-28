package qb.core;

import org.apache.commons.lang3.tuple.Pair;
import qb.definition.db.sqlite.schema.structure.DbF;

import java.util.List;
import java.util.NoSuchElementException;

public final class DtoFieldValue {
    public static <T> T getValue(DbF forDbf, List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
        return (T) columnNamesValues.stream().filter(col -> col.getKey().equals(forDbf.getSystemName())).findFirst().orElseThrow().getValue();
    }
}
