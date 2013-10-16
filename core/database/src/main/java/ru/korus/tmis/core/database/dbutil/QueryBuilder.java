package ru.korus.tmis.core.database.dbutil;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov
 *         Date: 09.08.13 0:39
 */
public class QueryBuilder {
    List<Operation> operations = new ArrayList<Operation>();

    public QueryBuilder where() {
        operations.add(new Operation(" WHERE "));
        return this;
    }

    public QueryBuilder put(String name, String value) {
        if (!name.contains(":")) {
            throw new IllegalArgumentException("Param 'name' must be contains symbol ':'");
        }
        operations.add(new Param(name, value));
        return this;
    }

    public QueryBuilder or() {
        operations.add(new Operation(" OR "));
        return this;
    }

    public QueryBuilder and() {
        operations.add(new Operation(" AND "));
        return this;
    }

    public <T> String toString(TypedQuery<T> typedQuery) {
//        for (Operation operation : operations) {
//
//        }
        return null;
    }

    public QueryBuilder clear() {
        operations.clear();
        return this;
    }

    class Param extends Operation {
        String value;
        OperationType type = OperationType.PAR;

        Param(String name, String value) {
            super(name);
            this.value = value;
        }
    }

    class Operation {
        String name;
        OperationType type = OperationType.OP;

        Operation(String name) {
            this.name = name;
        }
    }

    enum OperationType {
        OP, PAR;
    }
}
