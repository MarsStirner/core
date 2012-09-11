package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Set;
import javax.ejb.Local;

@Local
public interface DbEventBeanLocal {

    Event getEventById(int id)
            throws CoreException;

    Set<ActionType> getActionTypeFilter(int eventId)
            throws CoreException;

    OrgStructure getOrgStructureForEvent(int eventId)
            throws CoreException;
}
