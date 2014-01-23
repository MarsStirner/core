package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

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

    List<ActionPropertyType> getActionPropertyTypesByFlatCodes(Set<String> codes)
            throws CoreException;

    LinkedList<Object> getActionPropertyTypeValueDomainsWithFilter(int page, int limit,
                                                                   String sorting,
                                                                   ListDataFilter filter)
            throws CoreException;

    /**
     * Получение ActionPropertyType по идентификатору типа действия(ActionType.id) и коду с учетом флажка deleted
     * @param actionTypeId    идентификатор типа действия
     * @param code   код типа свойства действия
     * @param deleted  флажок удаления (проверяется на равенство)
     * @return    ActionPropertyType
     */
    ActionPropertyType getActionPropertyTypeByActionTypeIdAndTypeCode(int actionTypeId, String code, boolean deleted);
}
