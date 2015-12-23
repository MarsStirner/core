package ru.korus.tmis.ws.webmis.rest;



import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.Serializable;

/**
 * Базовый класс для registry REST-сервисов
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 13:58 PM
 * Since: 1.0.0.74
 */

@Stateless
@Path("/tms-registry/")
@Produces("application/json")
public class BaseRegistryRESTImpl implements Serializable {

    private static final long serialVersionUID = 2L;

    @EJB
    private WebMisREST wsImpl;

    @EJB
    private PatientRegistryRESTImpl patientRegistryREST;

    @EJB
    private JobImpl jobImpl;

    @EJB
    private PrescriptionsRESTImpl prescriptionsRESTImpl;

    @EJB
    private AppealsInfoRESTImpl appealsInfoREST;

    @EJB
    private RlsDataImpl rlsData;

    @EJB
    private AutoSaveStorageREST autoSaveStorageREST;

    @EJB
    private HospitalBedsInfoRESTImpl hospitalBedsInfoREST;

    @EJB
    private DiagnosticsInfoRESTImpl diagnosticsInfoREST;

    @EJB
    private DirectoryInfoRESTImpl directoryInfoREST;

    @EJB
    private CustomInfoRESTImpl customInfoREST;

    @EJB
    private APQLEndPoint apqlEndPoint;

    @EJB
    private HsctRestImpl hsctImpl;


    @Path("/")
    public CustomInfoRESTImpl getCustomInfoRESTImpl() {
        return customInfoREST;
    }

    @Path("/dir/")
    public DirectoryInfoRESTImpl getDirectoryInfoRESTImpl() {
        return directoryInfoREST;
    }

    @Path("/patients/")
    public PatientRegistryRESTImpl getPatientRegistryRESTImpl() {
        return patientRegistryREST;
    }

    @Path("/appeals/")
    public AppealsInfoRESTImpl getAppealsInfoRESTImpl() {
        return appealsInfoREST;
    }

    @Path("/diagnostics/")
    public DiagnosticsInfoRESTImpl getDiagnosticsInfoRESTImpl() {
        return diagnosticsInfoREST;
    }

    @Path("/hospitalbed/")
    public HospitalBedsInfoRESTImpl getHospitalBedsInfoRESTImpl() {
        return hospitalBedsInfoREST;
    }

    @Path("/autosave")
    public AutoSaveStorageREST getAutoSaveStorage() {
        return autoSaveStorageREST;
    }

    @Path("/rls")
    public RlsDataImpl getRlsDataImpl() {
        return rlsData;
    }

    @Path("/prescriptions")
    public PrescriptionsRESTImpl getPrescriptions() {
        return prescriptionsRESTImpl;
    }

    @Path("/job")
    public JobImpl getJobImpl() {
        return jobImpl;
    }

    @Path("/hsct")
    public HsctRestImpl getHsctImpl() { return hsctImpl;}

    @Path("/apql")
    public APQLEndPoint getAPQL() {
        return apqlEndPoint;
    }

}
