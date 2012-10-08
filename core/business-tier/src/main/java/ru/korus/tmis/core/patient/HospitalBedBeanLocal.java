package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.HospitalBedData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

//Коечный фонд
@Local
public interface HospitalBedBeanLocal {

    Action registryPatientToHospitalBed(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    Action modifyPatientToHospitalBed(int actionId, HospitalBedData data, AuthData authData) throws CoreException;

    Action movingPatientToDepartment(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    HospitalBedData getRegistryOriginalForm(Action action, AuthData authData) throws CoreException;

    Object getRegistryFormWithChamberList(Action action, AuthData authData) throws CoreException;

    java.util.LinkedHashMap<Integer, Boolean> getCaseHospitalBedsByDepartmentId(int departmentId) throws CoreException;

    HospitalBedData getMovingListByEventIdAndFilter(Object filter, AuthData authData) throws CoreException;

    boolean callOffHospitalBedForPatient(int actionId, AuthData authData) throws CoreException;
}
