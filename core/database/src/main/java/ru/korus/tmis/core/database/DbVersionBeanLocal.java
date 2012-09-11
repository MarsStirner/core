package ru.korus.tmis.core.database;

import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbVersionBeanLocal {

    String getGlobalVersion()
            throws CoreException;

}
