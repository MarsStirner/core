package ru.korus.tmis.core.patient;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.JSONCommonData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.StringId;
import scala.Function2;

import javax.ejb.Local;
import java.net.URI;
import java.util.List;

@Local
public interface PrimaryAssessmentBeanLocal {

    JSONCommonData createOrUpdatePrimaryAssessmentForEventId(Integer eventId, JSONCommonData assessment, Integer assessmentId, AuthData userData, Staff staff, URI baseUri, Function2<java.util.List<Action>, URI, Boolean> notify, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing)
            throws CoreException;

    JSONCommonData getEmptyStructure(int actionTypeId, String title, List<String> listForConverter, List<StringId> listForSummary, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing, Patient patient)
            throws CoreException;

    void deletePreviousAssessmentById(int assessmentId, Staff staff)
            throws CoreException;

    JSONCommonData getPrimaryAssessmentById(int assessmentId, String title, Function2<JSONCommonData, Boolean, JSONCommonData> postProcessing, Boolean reId)
            throws CoreException;

    boolean updateStatusById(int eventId, int actionId, short status)
            throws CoreException;
}
