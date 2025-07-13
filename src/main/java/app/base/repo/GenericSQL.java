package app.base.repo;

import jakarta.persistence.Query;
import app.builder.J2SQL;

public abstract class GenericSQL<R extends Enum<R>, J extends AbstractJ2<R>, T> {
    protected final J j2sql;
    protected GenericSQL(J j2sql) {
        this.j2sql = j2sql;
    }

    public String getSQL(R nameOfSQL) {
        return j2sql.getSQL(nameOfSQL);
    }

    public J2SQL getJ2SQL(R nameOfSQL) {
        return j2sql.getJ2SQL(nameOfSQL);
    }

    public Query getQuery(R nameOfSQL, Class<T> tableClass) {
        return j2sql.getQuery(nameOfSQL, tableClass);
    }
}


