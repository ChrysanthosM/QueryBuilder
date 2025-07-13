package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.base.repo.GenericSQL;
import qb.distribution.sqlite.schema.table.OptionsTable;

@Component
public final class OptionsSQL extends GenericSQL<OptionsRepo.NameOfSQL, OptionsJ2SQL, OptionsTable> {
    @Autowired
    public OptionsSQL(OptionsJ2SQL j2sql) {
        super(j2sql);
    }
}

