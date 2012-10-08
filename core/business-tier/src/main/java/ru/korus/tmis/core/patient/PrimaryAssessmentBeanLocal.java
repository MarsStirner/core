package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.JSONCommonData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.StringId;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface PrimaryAssessmentBeanLocal {

    JSONCommonData createPrimaryAssessmentForEventId(int eventId, JSONCommonData assessment, String title, AuthData userData)
            throws CoreException;

    JSONCommonData modifyPrimaryAssessmentById(int assessmentId, JSONCommonData assessment, String title, AuthData userData)
            throws CoreException;

    JSONCommonData getEmptyStructure(String code, String title, List<String> listForConverter, List<StringId> listForSummary, AuthData userData)
            throws CoreException;

    void deletePreviousAssessmentById(int assessmentId, AuthData userData)
            throws CoreException;

    JSONCommonData getPrimaryAssessmentById(int assessmentId, String title, AuthData userData)
            throws CoreException;

    boolean updateStatusById(int eventId, int actionId, short status)
            throws CoreException;

    JSONCommonData insertAssessmentAsConsultation(int eventId, int actionTypeId, int executorId, Date bDate, Date eDate, boolean urgent, Object request, AuthData userData)
            throws CoreException;

    JSONCommonData createAssessmentsForEventIdFromCommonData(int eventId, CommonData assessments, String title, Object request, AuthData userData)
            throws CoreException;
}
