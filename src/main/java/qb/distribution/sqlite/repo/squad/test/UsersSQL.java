package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UsersSQL {
    private final UsersJ2SQL j2Users;
    @Autowired
    public UsersSQL(UsersJ2SQL j2Users) {
        this.j2Users = j2Users;
    }

    public String getSQL(UsersRepo.NameOfSQL type) { return j2Users.getSQL(type); }
}
