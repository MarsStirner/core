package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbPatientBeanLocal {

    Iterable<Patient> getAllPatients()
            throws CoreException;

    Patient getPatientById(int id)
            throws CoreException;
}
