package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientAllergy;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;

public interface DbClientAllergyBeanLocal {

    Iterable<ClientAllergy> getAllClientAllergy(int patientId)
            throws CoreException;

    ClientAllergy getClientAllergyById(int id)
            throws CoreException;

    ClientAllergy insertOrUpdateClientAllergy(int id,
                                              int power,
                                              String nameSubstance,
                                              Date createDate,
                                              String notes,
                                              Patient patient,
                                              Staff sessionUser)
            throws CoreException;

    void deleteClientAllergy(int id,
                             Staff sessionUser)
            throws CoreException;
}
