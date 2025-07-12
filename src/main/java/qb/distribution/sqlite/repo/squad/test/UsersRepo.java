package qb.distribution.sqlite.repo.squad.test;

import qb.base.repo.RepoBase;

public interface UsersRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        INSERT,
    }
}
