package ru.korus.tmis.core.common;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Set;

@Local
public interface AgeSelectorFilterBeanLocal {

    Set<ActionType> filterActionTypes(Set<ActionType> actionTypes,
                                      Event event)
            throws CoreException;
}
