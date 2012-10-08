package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface DbOrgStructureBeanLocal {

    List<OrgStructure> getAllOrgStructures();

    List<OrgStructure> getAllOrgStructuresByRequest(int limit,
                                                    int page,
                                                    String sortingField,
                                                    String sortingMethod,
                                                    String filter)
            throws CoreException;

    long getCountAllOrgStructuresWithFilter(String filter)
            throws CoreException;


    Set<ActionType> getActionTypeFilter(int departmentId)
            throws CoreException;

    OrgStructure getOrgStructureByHospitalBedId(int bedId) throws CoreException;
}
