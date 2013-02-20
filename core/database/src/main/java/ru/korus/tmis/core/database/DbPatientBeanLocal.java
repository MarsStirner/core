package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.PatientRequestData;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Local
public interface DbPatientBeanLocal {

    Iterable<Patient> getAllPatients(int limit, int page, String sortField, String sortMethod,
                                     PatientRequestData requestData)
            throws CoreException;

    Iterable<Patient> getPatientsWithCode(int limit, int page, String sortField, String sortMethod,
                                          int patientCode, PatientRequestData requestData)
            throws CoreException;

    Iterable<Patient> getPatientsWithDocumentPattern(int limit, int page, String sortField, String sortMethod,
                                                     String documentPattern, PatientRequestData requestData)
            throws CoreException;

    Iterable<Patient> getPatientsWithFullNamePattern(int limit, int page, String sortField, String sortMethod,
                                                     String fullNamePattern, PatientRequestData requestData)
            throws CoreException;

    Iterable<Patient> getPatientsWithBirthDate(int limit, int page, String sortField, String sortMethod,
                                               Date birthDate, PatientRequestData requestData)
            throws CoreException;

    Iterable<Patient> getPatientsWithBirthDateAndFullNamePattern(int limit, int page, String sortField, String sortMethod,
                                                                 Date birthDate, String fullNamePattern, PatientRequestData requestData)
            throws CoreException;

    Patient getPatientById(int id)
            throws CoreException;

    Patient insertOrUpdatePatient(int id,
                                  String firstName,
                                  String middleName,
                                  String lastName,
                                  Date birthDate,
                                  String birthPlace,
                                  String sex,
                                  String weight,
                                  String height,
                                  String snils,
                                  Date bloodDate,
                                  int rbBloodTypeId,
                                  String bloodNotes,
                                  String notes,
                                  Staff sessionUser,
                                  int version)
            throws CoreException;

    Boolean checkSNILSNumber(String number) throws CoreException;

    List<Patient> findPatient(Map<String, String> params) throws CoreException;

    Integer savePatientToDataBase(Patient patient) throws CoreException;

    /**
     * Проверяет жив ли пациент
     *
     * @param patient Пациент, факт смерти которого проверяется
     * @return false=мертв, true=жив
     */
    boolean isAlive(Patient patient);
}
