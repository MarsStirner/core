package ru.korus.tmis.core.patient;


import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AppealData;
import ru.korus.tmis.core.data.AppealSimplifiedDataList;
import ru.korus.tmis.core.data.AppealSimplifiedRequestData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

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
}
