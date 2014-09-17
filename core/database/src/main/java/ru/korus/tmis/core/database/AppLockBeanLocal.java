package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.lock.ActionWithLockInfo;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AppLockBeanLocal {

    AppLockStatus getAppLock(String table,
                    int recordId,
                    int recordIndex,
                    AuthData userData)
            throws CoreException;

    boolean prolongAppLock(int lockId)
            throws CoreException;

    boolean releaseAppLock(int lockId)
            throws CoreException;

    ActionWithLockInfo getLockInfo(Action action);

}
