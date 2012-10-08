package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface DbActionTypeBeanLocal {
    ActionType getActionTypeById(int id)
            throws CoreException;

    Set<ActionType> getAssessmentTypes()
            throws CoreException;

    Set<ActionType> getDiagnosticTypes()
            throws CoreException;

    Set<ActionType> getTreatmentTypes()
            throws CoreException;

    Set<ActionType> getDrugTreatmentTypes()
            throws CoreException;

    List<ActionPropertyType> getActionTypePropertiesById(int actionTypeId)
            throws CoreException;

    ActionType getActionTypeByCode(String code)
            throws CoreException;

    Set<ActionType> getActionTypesByCode(String code)
            throws CoreException;

    List<ActionType> getAllActionTypeWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    long getCountAllActionTypeWithFilter(Object filter)
            throws CoreException;
}
