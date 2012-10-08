package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssessmentsListRequestData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

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

    Action getAppealActionByEventId(int eventId, String code)
            throws CoreException;

    Action getActionByEventExternalId(String externalId)
            throws CoreException;

    List<Action> getActionsByEventIdWithFilter(int eventId, AuthData userData, AssessmentsListRequestData requestData)
            throws CoreException;

    List<Action> getActionsByTypeCode(String code, AuthData userData)
            throws CoreException;

    List<Action> getActionsByTypeCodeAndEventId(Set<String> codes, int eventId, String sort, AuthData userData)
            throws CoreException;
}
