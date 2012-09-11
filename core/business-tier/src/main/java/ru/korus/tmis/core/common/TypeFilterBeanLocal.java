package ru.korus.tmis.core.common;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Set;
import javax.ejb.Local;

@Local
public interface TypeFilterBeanLocal {

    Set<ActionType> filterActionTypes(Set<ActionType> actionTypes,
                                      int departmentId,
                                      int eventId)
            throws CoreException;
}
