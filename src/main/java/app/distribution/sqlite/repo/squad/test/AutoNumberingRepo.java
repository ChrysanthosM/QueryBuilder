package app.distribution.sqlite.repo.squad.test;

import app.base.repo.RepoBase;

public interface AutoNumberingRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        INSERT,
        DELETE_ALL,
        FIND,
        MAX_NUMBER_PER_ENTITY,
    }
}
