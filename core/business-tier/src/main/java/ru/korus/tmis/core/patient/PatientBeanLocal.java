package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.PatientEntry;
import ru.korus.tmis.core.data.PatientRequestData;
import ru.korus.tmis.core.data.PatientsListRequestData;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Date;

@Local
public interface PatientBeanLocal {

    CommonData getCurrentPatientsForDoctor(AuthData userData)
            throws CoreException;

    CommonData getCurrentPatientsForDepartment(AuthData userData)
            throws CoreException;

    /**
     * Сервис для получения списка пациентов по идентификатору отделения (для MediPad)
     * @param id Идентификатор отделения
     * @return Возвращает список данных о пациента в виде CommonData
     * @throws CoreException
     * @see CommonData
     */
    CommonData getCurrentPatientsByDepartmentId( int id)
            throws CoreException;

    Patient getPatientById(int id)
            throws CoreException;

    Iterable<Patient> getAllPatients(PatientRequestData requestData)
            throws CoreException;

    PatientEntry savePatient(PatientEntry patientEntry, AuthData userData)
            throws CoreException;

    String getAllPatientsForDepartmentIdAndDoctorIdByPeriod(PatientsListRequestData requestData, int role, AuthData authData)
            throws CoreException;

    LinkedHashMap<Integer, LinkedList<Kladr>> getKLADRAddressMapForPatient(Patient patient) throws CoreException;

    LinkedHashMap<Integer, Street> getKLADRStreetForPatient(Patient patient) throws CoreException;

    Boolean checkSNILSNumber(String number) throws CoreException;

    Boolean checkPolicyNumber(String number, String serial, int typeId) throws CoreException;
}
