package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface AppLockBeanLocal {

    int acquireLock(String table,
                    int recordId,
                    int recordIndex,
                    AuthData userData)
            throws CoreException;

    boolean releaseLock(int lockId)
            throws CoreException;

}
