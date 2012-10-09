package ru.korus.tmis.core.patient;


import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AppealBeanLocal {

    int insertAppealForPatient(AppealData appealData, int patientId, AuthData authData) throws CoreException;

    java.util.HashMap<Event, java.util.Map<Action, java.util.Map<String, java.util.List<Object>>>> getAppealById(int id)
            throws CoreException;

    AppealSimplifiedDataList getAllAppealsByPatient(AppealSimplifiedRequestData request, AuthData authData)
            throws CoreException;

    java.util.HashMap<Event, Object> getAllAppealsForReceivedPatientByPeriod(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    long getCountOfAppealsForReceivedPatientByPeriod(Object filter) throws CoreException;
    // JSONArray getBasicInfoOfDiseaseHistory(int patientId, String externalId, AuthData authData)
    //         throws CoreException;

    Boolean checkAppealNumber(String number)
            throws CoreException;

    Event revokeAppealById(Event event, int resultId, AuthData authData) throws CoreException;

    List<String> getAppealTypeCodesWithFlatDirectoryId(int id) throws CoreException;
}
