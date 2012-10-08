package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Local
public interface DbActionPropertyTypeBeanLocal {

    ActionPropertyType getActionPropertyTypeById(int id)
            throws CoreException;

    Set<ActionPropertyType> getDepartmentAPT()
            throws CoreException;

    Set<ActionPropertyType> getHospitalBedAPT()
            throws CoreException;

    Set<ActionPropertyType> getAnamnesisAPT()
            throws CoreException;

    Set<ActionPropertyType> getAllergoanamnesisAPT()
            throws CoreException;

    Set<ActionPropertyType> getDiagnosisAPT()
            throws CoreException;

    Set<ActionPropertyType> getDrugNomenAPT()
            throws CoreException;

    Set<ActionPropertyType> getDosageAPT()
            throws CoreException;

    List<ActionPropertyType> getActionPropertyTypeByActionTypeIdWithCode(String code)
            throws CoreException;

    List<ActionPropertyType> getActionPropertyTypesByActionTypeId(int actionTypeId)
            throws CoreException;

    LinkedList<Object> getActionPropertyTypeValueDomainsWithFilter(int page, int limit,
                                                                   String sortingField, String sortingMethod,
                                                                   Object filter)
            throws CoreException;
}
