package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OptionsSQL {
    private final OptionsJ2SQL j2Options;
    @Autowired
    public OptionsSQL(OptionsJ2SQL j2Options) {
        this.j2Options = j2Options;
    }

    public String getSQL(OptionsRepo.NameOfSQL type) { return j2Options.getSQL(type); }
}
