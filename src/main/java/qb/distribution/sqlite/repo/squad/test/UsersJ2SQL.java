package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qb.builder.J2SQL;
import qb.base.repo.LoadJ2SQL;
import qb.base.repo.AbstractJ2;
import qb.distribution.sqlite.schema.table.UsersTable;

@Component
public final class UsersJ2SQL extends AbstractJ2<UsersRepo.NameOfSQL> implements UsersRepo {
    private final UsersTable usersTable;
    @Autowired
    private UsersJ2SQL(UsersTable usersTable) {
        super(NameOfSQL.class);
        this.usersTable = usersTable;
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadList() {
        addLoader(NameOfSQL.LIST, J2SQL.create(getWorkWithDataSource()).from(usersTable));
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadInsert() {
        addLoader(NameOfSQL.INSERT, J2SQL.create(getWorkWithDataSource()).insertInto(usersTable).insertRow());
    }
}
