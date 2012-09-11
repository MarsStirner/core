package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.exception.CoreException;

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
}
