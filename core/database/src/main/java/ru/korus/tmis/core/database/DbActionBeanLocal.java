package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbActionBeanLocal {

    Action getActionById(int id)
            throws CoreException;

    Action createAction(int eventId,
                        int actionTypeId,
                        AuthData userData)
            throws CoreException;

    Action updateAction(int id,
                        int version,
                        AuthData userData)
            throws CoreException;

    Action updateActionStatus(int id, short status)
            throws CoreException;

}
