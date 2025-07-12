package qb.definition.db.system.sqlite.repo.squad.test;

import qb.definition.db.system.commons.repo.RepoBase;

public interface AutoNumberingRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        INSERT,
        DELETE_ALL,
        FIND,
        MAX_NUMBER_PER_ENTITY,
    }
}
