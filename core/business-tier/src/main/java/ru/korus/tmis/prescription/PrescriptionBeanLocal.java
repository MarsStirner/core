package ru.korus.tmis.prescription;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.05.14, 15:05 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Local
public interface PrescriptionBeanLocal {

    PrescriptionsData getPrescriptions(Integer eventId, Long dateRangeMin, Long dateRangeMax, PrescriptionGroupBy groupBy, String admissionId, String drugName, String patientName, String setPersonName, String departmentId, AuthData auth) throws CoreException;

    PrescriptionTypeDataArray getTypes();

    TemplateData getTemplate(Integer actionTypeId) throws CoreException;

    PrescriptionsData create(CreatePrescriptionReqData createPrescriptionReqData, AuthData authData) throws CoreException;

    PrescriptionsData update(Integer actionId, CreatePrescriptionReqData createPrescriptionReqData, AuthData auth) throws CoreException;

    ExecuteIntervalsData executeIntervals(ExecuteIntervalsData executeIntervalsData);

    ExecuteIntervalsData cancelIntervals(ExecuteIntervalsData executeIntervalsData);

    ExecuteIntervalsData cancelIntervalsExecution(ExecuteIntervalsData executeIntervalsData);

    AssigmentIntervalData updateIntervals(AssigmentIntervalDataArray assigmentIntervalDataArray);
}
