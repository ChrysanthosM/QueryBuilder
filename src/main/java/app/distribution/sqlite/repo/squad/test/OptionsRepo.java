package app.distribution.sqlite.repo.squad.test;

import app.builder.base.repo.RepoBase;

public interface OptionsRepo extends RepoBase {
    enum NameOfSQL {
        LIST,
        FIND,
        INSERT,
    }
}
