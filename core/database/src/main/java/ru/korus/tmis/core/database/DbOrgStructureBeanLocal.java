package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public interface DbOrgStructureBeanLocal {

    List<OrgStructure> getAllOrgStructures();

    Set<ActionType> getActionTypeFilter(int departmentId)
            throws CoreException;
}
