package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.base.repo.GenericSQL;
import qb.distribution.sqlite.schema.table.UsersTable;

@Component
public final class UsersSQL extends GenericSQL<UsersRepo.NameOfSQL, UsersJ2SQL, UsersTable> {
    @Autowired
    public UsersSQL(UsersJ2SQL j2sql) {
        super(j2sql);
    }
}

