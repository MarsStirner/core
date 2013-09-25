package ru.risar.service;

import ru.korus.tmis.core.entity.model.ClientIdentification;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.service.interfaces.DelegatedTMISEntityService;
import ru.korus.tmis.core.service.interfaces.PatientService;

import javax.ejb.Local;
import java.util.Date;

/**
 * @author anosov
 *         Date: 28.08.13 23:57
 */
@Local
public interface RisarPatientService extends DelegatedTMISEntityService<Patient, PatientService>, PatientService {
    ClientIdentification findClientIdentificator(String identifier);
    Patient findBySnilsAndBirthdate(String number, Date birthdate);
    Patient findByBirthCertificateAndBirthdate(String serial, String number, Date birthdate);
    Patient findByPassportAndBirthdate(String serial, String number, Date birthdate);

    void createPatientIdentifier(Patient patient, String identifier);
}
