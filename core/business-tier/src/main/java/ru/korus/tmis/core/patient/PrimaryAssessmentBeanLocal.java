package ru.korus.tmis.core.patient;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.JSONCommonData;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.StringId;
import scala.Function2;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PrimaryAssessmentBeanLocal {

    JSONCommonData createPrimaryAssessmentForEventId(int eventId, JSONCommonData assessment, String title, AuthData userData, Function2<JSONCommonData, Boolean, JSONCommonData> preProcessing, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing)
            throws CoreException;

    JSONCommonData modifyPrimaryAssessmentById(int assessmentId, JSONCommonData assessment, String title,AuthData userData, Function2<JSONCommonData, Boolean, JSONCommonData> preProcessing, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing)
            throws CoreException;

    JSONCommonData getEmptyStructure(int actionTypeId, String title, List<String> listForConverter, List<StringId> listForSummary, AuthData userData, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing, Patient patient)
            throws CoreException;

    void deletePreviousAssessmentById(int assessmentId, AuthData userData)
            throws CoreException;

    JSONCommonData getPrimaryAssessmentById(int assessmentId, String title, AuthData userData, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing, Boolean reId)
            throws CoreException;

    boolean updateStatusById(int eventId, int actionId, short status)
            throws CoreException;
}
