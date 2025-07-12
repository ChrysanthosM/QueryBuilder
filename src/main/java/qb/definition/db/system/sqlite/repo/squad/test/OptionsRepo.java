package qb.definition.db.system.sqlite.repo.squad.test;

import qb.definition.db.system.commons.repo.RepoBase;

public interface OptionsRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        FIND,
        INSERT,
    }
}
