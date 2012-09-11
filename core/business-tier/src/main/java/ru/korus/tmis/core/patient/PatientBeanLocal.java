package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface PatientBeanLocal {

    CommonData getCurrentPatientsForDoctor(AuthData userData)
            throws CoreException;

    CommonData getCurrentPatientsForDepartment(AuthData userData)
            throws CoreException;

    Patient getPatientById(int id)
            throws CoreException;
}
