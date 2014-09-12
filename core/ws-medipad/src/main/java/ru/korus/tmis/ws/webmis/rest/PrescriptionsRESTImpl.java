package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.prescription.AssigmentIntervalDataArray;
import ru.korus.tmis.prescription.CreatePrescriptionReqData;
import ru.korus.tmis.prescription.ExecuteIntervalsData;
import ru.korus.tmis.prescription.PrescriptionBeanLocal;
import ru.korus.tmis.prescription.PrescriptionGroupBy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Arrays;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.05.14, 15:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class PrescriptionsRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    @EJB
    private PrescriptionBeanLocal prescriptionBeanLocal;

    @GET
    @Produces("application/x-javascript")
    public Object listAction(@Context HttpServletRequest servRequest,
                             @QueryParam("callback") String callback,
                             @QueryParam("eventId") Integer eventId,
                             @QueryParam("dateRangeMin") Long dateRangeMin,
                             @QueryParam("dateRangeMax") Long dateRangeMax,
                             @QueryParam("groupBy") PrescriptionGroupBy groupBy,
                             @QueryParam("administrationId") String admissionId,
                             @QueryParam("drugName") String drugName,
                             @QueryParam("pacientName") String patientName,
                             @QueryParam("setPersonName") String setPersonName,
                             @QueryParam("departmentId") String departmentId)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.getPrescriptions(eventId,
                dateRangeMin,
                dateRangeMax,
                groupBy,
                admissionId,
                drugName,
                patientName,
                setPersonName,
                departmentId,
                wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()))), callback);
    }

    @POST
    @Produces("application/x-javascript")
    public Object listAction(@Context HttpServletRequest servRequest, @QueryParam("callback") String callback, CreatePrescriptionReqData createPrescriptionReqData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.create(createPrescriptionReqData, wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()))), callback);
    }

    @PUT
    @Path("/{prescriptionId}")
    @Produces("application/x-javascript")
    public Object listAction(@Context HttpServletRequest servRequest, @QueryParam("callback") String callback, @PathParam("prescriptionId")Integer actionId, CreatePrescriptionReqData createPrescriptionReqData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.update(actionId, createPrescriptionReqData, wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()))), callback);
    }

    @PUT
    @Path("/intervals")
    @Produces("application/x-javascript")
    public Object updateIntervals(@QueryParam("callback") String callback, AssigmentIntervalDataArray assigmentIntervalDataArray)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.updateIntervals(assigmentIntervalDataArray), callback);
    }

    @PUT
    @Path("/executeIntervals/")
    @Produces("application/x-javascript")
    public Object executeIntervals(@QueryParam("callback") String callback, ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.executeIntervals(executeIntervalsData), callback);
    }

    @PUT
    @Path("/cancelIntervals/")
    @Produces("application/x-javascript")
    public Object cancelIntervals(@QueryParam("callback") String callback, ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.cancelIntervals(executeIntervalsData), callback);
    }

    @PUT
    @Path("/cancelIntervalsExecution/")
    @Produces("application/x-javascript")
    public Object cancelIntervalsExecution(@QueryParam("callback") String callback, ExecuteIntervalsData executeIntervalsData)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.cancelIntervalsExecution(executeIntervalsData), callback);
    }


    @GET
    @Path("/types/")
    @Produces("application/x-javascript")
    public Object getTypes(@QueryParam("callback") String callback)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.getTypes(), callback);
    }

    @GET
    @Path("/template/{actionTypeId}/")
    @Produces("application/x-javascript")
    public Object getTemplate(@QueryParam("callback") String callback, @PathParam("actionTypeId") Integer actionTypeId)throws CoreException {
        return new JSONWithPadding(prescriptionBeanLocal.getTemplate(actionTypeId), callback);
    }

}
