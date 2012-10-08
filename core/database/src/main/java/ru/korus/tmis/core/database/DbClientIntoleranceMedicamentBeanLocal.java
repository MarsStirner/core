package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientIntoleranceMedicament;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;

public interface DbClientIntoleranceMedicamentBeanLocal {

    Iterable<ClientIntoleranceMedicament> getAllClientIntoleranceMedicament(int patientId)
            throws CoreException;

    ClientIntoleranceMedicament getClientIntoleranceMedicamentById(int id)
            throws CoreException;

    ClientIntoleranceMedicament insertOrUpdateClientIntoleranceMedicament(int id,
                                                                          int power,
                                                                          String nameMedicament,
                                                                          Date createDate,
                                                                          String notes,
                                                                          Patient patient,
                                                                          Staff sessionUser)
            throws CoreException;

    void deleteClientIntoleranceMedicament(int id,
                                           Staff sessionUser)
            throws CoreException;
}
