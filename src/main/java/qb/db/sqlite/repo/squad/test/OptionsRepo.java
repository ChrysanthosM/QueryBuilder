package qb.db.sqlite.repo.squad.test;

import qb.base.repo.RepoBase;

public interface OptionsRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        FIND,
        INSERT,
    }
}
