package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.prescription.AssigmentIntervalDataArray;
import ru.korus.tmis.prescription.CreatePrescriptionReqData;
import ru.korus.tmis.prescription.ExecuteIntervalsData;
import ru.korus.tmis.prescription.PrescriptionBeanLocal;
import ru.korus.tmis.prescription.PrescriptionGroupBy;

import javax.ws.rs.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.05.14, 15:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PrescriptionsRESTImpl {

    final private PrescriptionBeanLocal prescriptionBeanLocal;
    final private AuthData auth;
    final private String callback;

    public PrescriptionsRESTImpl(PrescriptionBeanLocal prescriptionBeanLocal, AuthData auth, String callback) {
        this.prescriptionBeanLocal = prescriptionBeanLocal;
        this.auth = auth;
        this.callback = callback;
    }

    @GET
    @Path("/")
    @Produces("application/x-javascript")
    public Object listAction(@QueryParam("eventId") Integer eventId,
                             @QueryParam("dateRangeMin") Long dateRangeMin,
                             @QueryParam("dateRangeMax") Long dateRangeMax,
                             @QueryParam("groupBy") PrescriptionGroupBy groupBy,
                             @QueryParam("administrationId") String admissionId,
                             @QueryParam("drugName") String drugName,
                             @QueryParam("pacientName") String patientName,
                             @QueryParam("setPersonName") String setPersonName,
                             @QueryParam("departmentId") String departmentId)throws CoreException {
        //http://webmis/api/v1/prescriptions/?callback=jQuery18205942272304091603_1400733998545&
        // groupBy=interval&
        // dateRangeMin=1400745600&
        // dateRangeMax=1400832000&
        // administrationId=2&
        // drugName=a&
        // pacientName=P&
        // setPersonName=V&
        // departmentId=26&_=1400734043649
        return new JSONWithPadding(prescriptionBeanLocal.getPrescriptions(eventId,
                dateRangeMin,
                dateRangeMax,
                groupBy,
                admissionId,
                drugName,
                patientName,
                setPersonName,
                departmentId,
                auth), callback);
    }

    @POST
    @Path("/")
    @Produces("application/x-javascript")
    public Object listAction(CreatePrescriptionReqData createPrescriptionReqData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.create(createPrescriptionReqData, auth), callback);
    }

    @PUT
    @Path("/{prescriptionId}")
    @Produces("application/x-javascript")
    public Object listAction(@PathParam("prescriptionId")Integer actionId, CreatePrescriptionReqData createPrescriptionReqData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.update(actionId, createPrescriptionReqData, auth), callback);
    }

    @PUT
    @Path("/intervals")
    @Produces("application/x-javascript")
    public Object updateIntervals(AssigmentIntervalDataArray assigmentIntervalDataArray)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.updateIntervals(assigmentIntervalDataArray), callback);
    }

    @PUT
    @Path("/executeIntervals/")
    @Produces("application/x-javascript")
    public Object executeIntervals(ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.executeIntervals(executeIntervalsData), callback);
    }

    @PUT
    @Path("/cancelIntervals/")
    @Produces("application/x-javascript")
    public Object cancelIntervals(ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.cancelIntervals(executeIntervalsData), callback);
    }

    @PUT
    @Path("/cancelIntervalsExecution/")
    @Produces("application/x-javascript")
    public Object cancelIntervalsExecution(ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.cancelIntervalsExecution(executeIntervalsData), callback);
    }


    @GET
    @Path("/types/")
    @Produces("application/x-javascript")
    public Object getTypes()throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.getTypes(), callback);
    }

    @GET
    @Path("/template/{actionTypeId}/")
    @Produces("application/x-javascript")
    public Object getTemplate(@PathParam("actionTypeId") Integer actionTypeId)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.getTemplate(actionTypeId), callback);
    }



}
