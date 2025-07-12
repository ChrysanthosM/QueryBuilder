package qb.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AutoNumberingSQL {
    private final AutoNumberingJ2SQL j2AutoNumbering;
    @Autowired
    public AutoNumberingSQL(AutoNumberingJ2SQL j2AutoNumbering) {
        this.j2AutoNumbering = j2AutoNumbering;
    }

    public String getSQL(AutoNumberingRepo.NameOfSQL type) { return j2AutoNumbering.getSQL(type); }
}
