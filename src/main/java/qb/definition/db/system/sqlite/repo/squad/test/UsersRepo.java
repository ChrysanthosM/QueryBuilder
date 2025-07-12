package qb.definition.db.system.sqlite.repo.squad.test;

import qb.definition.db.system.commons.repo.RepoBase;

public interface UsersRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        INSERT,
    }
}
