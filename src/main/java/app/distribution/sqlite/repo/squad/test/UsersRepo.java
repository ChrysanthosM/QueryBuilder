package app.distribution.sqlite.repo.squad.test;

import app.base.repo.RepoBase;

public interface UsersRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        INSERT,
    }
}
