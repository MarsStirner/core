package ru.korus.tmis.core.service.interfaces;


import ru.korus.tmis.core.entity.model.Patient;

import javax.ejb.Local;

/**
 * DAO для работы с сущностью "Пациенты"
 * {@see ru.korus.tmis.core.entity.model.Patient}
 * <p/>
 * Рефакторинг класса {@see ru.korus.tmis.core.database.common.DbPatientBean}
 * <p/>
 * конкретные реализации:
 * {@see ru.risar.service.RisarPatientService} - интеграция с РИСАР
 *
 * @author anosov
 *         Date: 28.08.13 23:34
 */
@Local
public interface PatientService extends TMISEntityService<Patient> {
    Patient findByPolicy(String number, String serial, Integer typeId);

    Patient findBySnils(String number);

    Patient findByPassport(String serial, String number);

    Patient findByBirthCertificate(String serial, String number);
}
