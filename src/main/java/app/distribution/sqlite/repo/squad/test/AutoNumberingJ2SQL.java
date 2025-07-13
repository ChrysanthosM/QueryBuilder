package app.distribution.sqlite.repo.squad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.builder.base.repo.AbstractJ2;
import app.builder.base.repo.LoadJ2SQL;
import app.builder.core.J2SQL;
import app.distribution.sqlite.schema.table.AutoNumberingTable;

import static app.builder.core.J2SQLShared.MAX;

@Component
public final class AutoNumberingJ2SQL extends AbstractJ2<AutoNumberingRepo.NameOfSQL> implements AutoNumberingRepo {
    private final AutoNumberingTable autoNumberingTable;
    @Autowired
    private AutoNumberingJ2SQL(AutoNumberingTable autoNumberingTable) {
        super(NameOfSQL.class);
        this.autoNumberingTable = autoNumberingTable;
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadList() {
        addLoader(NameOfSQL.LIST, J2SQL.create(getWorkWithDataSource()).from(autoNumberingTable));
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadInsert() {
        addLoader(NameOfSQL.INSERT, J2SQL.create(getWorkWithDataSource()).insertInto(autoNumberingTable).insertRow());
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadFind() {
        addLoader(NameOfSQL.FIND, J2SQL.create(getWorkWithDataSource()).from(autoNumberingTable).where(autoNumberingTable.ENTITY_TYPE.eq("?")));
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadMaxNumberPerEntity() {
        addLoader(NameOfSQL.MAX_NUMBER_PER_ENTITY, J2SQL.create(getWorkWithDataSource()).from(autoNumberingTable).
                select(autoNumberingTable.ENTITY_TYPE, MAX(autoNumberingTable.ENTITY_NUMBER))
                .groupBy(autoNumberingTable.ENTITY_TYPE)
                .orderBy(autoNumberingTable.ENTITY_TYPE));
    }

    @LoadJ2SQL @SuppressWarnings("unused")
    public void loadDeleteAll() {
        addLoader(NameOfSQL.DELETE_ALL, J2SQL.create(getWorkWithDataSource()).deleteFrom(autoNumberingTable));
    }

}
