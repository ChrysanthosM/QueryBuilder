package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.base.repo.GenericSQL;
import qb.distribution.sqlite.schema.table.AutoNumberingTable;

@Component
public final class AutoNumberingSQL extends GenericSQL<AutoNumberingRepo.NameOfSQL, AutoNumberingJ2SQL, AutoNumberingTable> {
    @Autowired
    public AutoNumberingSQL(AutoNumberingJ2SQL j2sql) {
        super(j2sql);
    }
}

