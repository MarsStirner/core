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

    List<OrgStructure> getAllOrgStructuresByRequest(int limit,
                                                    int page,
                                                    String sortingField,
                                                    String sortingMethod,
                                                    Object filter)
            throws CoreException;

    long getCountAllOrgStructuresWithFilter(Object filter)
            throws CoreException;


    Set<ActionType> getActionTypeFilter(int departmentId)
            throws CoreException;

    OrgStructure getOrgStructureByHospitalBedId(int bedId) throws CoreException;
}
