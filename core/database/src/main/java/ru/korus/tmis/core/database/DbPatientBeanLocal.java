package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.PatientRequestData;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Local
public interface DbPatientBeanLocal {

    Iterable<Patient> getAllPatients() throws CoreException;

    Iterable<Patient> getAllPatients(int page, int limit, String sorting, ListDataFilter filter, Function1<Long, Boolean> setRecCount)
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

    List<Patient> findPatient(Map<String, String> params, int clientId) throws CoreException;

    List<Patient> findPatientByPolicy
            (Map<String, String> params, String policySerial, String policyNumber, int policyType)
            throws CoreException;

    List<Patient> findPatientByDocument
            (Map<String, String> params, String documentSerial, String documentNumber, int documentCode)
            throws CoreException;

    List<Patient> findPatientsByParams(Map<String, String> params, Map<String, String> documents);

    Integer savePatientToDataBase(Patient patient) throws CoreException;

    /**
     * Проверяет жив ли пациент
     *
     * @param patient Пациент, факт смерти которого проверяется
     * @return false=мертв, true=жив
     */
    boolean isAlive(Patient patient);

    boolean deletePatient(int id) throws CoreException;

    List<Patient> findPatientWithoutDocuments(Map<String, String> parameters);
}
