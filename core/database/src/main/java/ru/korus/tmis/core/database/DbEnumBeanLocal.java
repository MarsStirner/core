package ru.korus.tmis.core.database;

import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbEnumBeanLocal {

    void init();
}
