package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.prescription.PrescriptionBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Базовый класс для registry REST-сервисов
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 13:58 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
@Stateless
@Path("/tms-registry/")
@Produces("application/json")
public class BaseRegistryRESTImpl implements Serializable {

    private static final long serialVersionUID = 2L;

    @EJB private WebMisREST wsImpl;

    @EJB private PatientRegistryRESTImpl patientRegistryREST;

    @EJB private JobImpl jobImpl;

    @EJB private PrescriptionsRESTImpl prescriptionsRESTImpl;

    @EJB private AppealsInfoRESTImpl appealsInfoREST;

    @EJB private RlsDataImpl rlsData;

    @EJB private AutoSaveStorageREST autoSaveStorageREST;

    @EJB private HospitalBedsInfoRESTImpl hospitalBedsInfoREST;

    @EJB private DiagnosticsInfoRESTImpl diagnosticsInfoREST;


    @Path("/")
    public CustomInfoRESTImpl getCustomInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                    @QueryParam("callback") String callback,
                                                    @QueryParam("limit") int limit,
                                                    @QueryParam("page") int page,
                                                    @QueryParam("sortingField") String sortingField,
                                                    @QueryParam("sortingMethod") String sortingMethod) {
        return new CustomInfoRESTImpl(wsImpl, callback, limit, page, sortingField, sortingMethod, makeAuth(servRequest));
    }

    @Path("/dir/")
    public DirectoryInfoRESTImpl getDirectoryInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                          @QueryParam("test") String test,
                                                          @QueryParam("callback") String callback,
                                                          @QueryParam("limit") int limit,
                                                          @QueryParam("page") int page,
                                                          @QueryParam("sortingField") String sortingField,
                                                          @QueryParam("sortingMethod") String sortingMethod) {
        return new DirectoryInfoRESTImpl(wsImpl, servRequest, callback, limit, page, sortingField, sortingMethod, makeAuth(servRequest));
    }

    @Path("/patients/")
    public PatientRegistryRESTImpl getPatientRegistryRESTImpl() { return patientRegistryREST; }

    @Path("/appeals/")
    public AppealsInfoRESTImpl getAppealsInfoRESTImpl() { return appealsInfoREST; }

    @Path("/diagnostics/")
    public DiagnosticsInfoRESTImpl getDiagnosticsInfoRESTImpl() { return diagnosticsInfoREST; }

    @Path("/hospitalbed/")
    public HospitalBedsInfoRESTImpl getHospitalBedsInfoRESTImpl() { return hospitalBedsInfoREST; }

    @Path("/autosave")
    public AutoSaveStorageREST getAutoSaveStorage() { return autoSaveStorageREST; }

    @Path("/rls")
    public RlsDataImpl getRlsDataImpl() { return rlsData; }
    @Path("/prescriptions")
    public PrescriptionsRESTImpl getPrescriptions() { return prescriptionsRESTImpl; }

    @Path("/job")
    public JobImpl getJobImpl() { return jobImpl; }

    private AuthData makeAuth(HttpServletRequest servRequest) {
        return this.wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }

}
