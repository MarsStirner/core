package ru.korus.tmis.pix.sda;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbQueryBeanLocal;
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.RbMedicalAidProfileBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.HospitalBedBeanLocal;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.hs.HsPixPullTimerBeanLocal;
import ru.korus.tmis.pix.sda.ws.EMRReceiverService;
import ru.korus.tmis.pix.sda.ws.EMRReceiverServiceSoap;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.06.2013, 14:40:34 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 *
 */
@Stateless
public class HsPixPullBean implements HsPixPullTimerBeanLocal, Sender {

    private static final Logger logger = LoggerFactory.getLogger(HsPixPullBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    @EJB
    DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal;
    private String defOrgName;

    @EJB
    DbOrganizationBeanLocal organizationBeanLocal;

    @EJB
    DbEventBeanLocal dbEventBeanLocal;

    @EJB
    private DbQueryBeanLocal dbCustomQueryBean;

    @EJB
    private RbMedicalAidProfileBeanLocal dbRbMedicalAidProfileBean;

    @EJB
    private DbQueryBeanLocal dbQueryBean;

    @EJB
    private TransmitterLocal transmitterLocal;

    @EJB
    private HospitalBedBeanLocal hospitalBedBeanLocal;

    private EMRReceiverServiceSoap port = null;

    @Override
    public void setPort(EMRReceiverServiceSoap port) {
        this.port = port;
    }

    public void pullDb(boolean ignoreSetting) {
        try {
            logger.info("HS integration entry...");
            if (ignoreSetting || ConfigManager.HealthShare().isSdaActive()) {
                logger.info("HS integration is active...");

                logger.info("HS integration ... SDASoapServiceServiceSoap is avalable");
                Integer maxId = em.createQuery("SELECT max(hsi.eventId) FROM HSIntegration hsi", Integer.class).getSingleResult();
                if (maxId == null) {
                    maxId = 0;
                }
                logger.info("HS integration ... maxId = {}", maxId);
                List<Event> newEvents =
                        em.createNamedQuery("Event.toHS", Event.class).setParameter("max", maxId).setMaxResults(TransmitterLocal.MAX_RESULT).getResultList();

                addNewEvent(newEvents);

                // Отправка завершенных обращений
                sendNewEventToHS();
                // Отправка карточек новых/обновленных пациентов
                sendPatientsInfo();
            } else {
                logger.info("HS integration is disabled...");
            }
        } catch (Exception ex) {
            logger.error("HS integration internal error.", ex);
        }

    }

    private EMRReceiverServiceSoap getEmrReceiverServiceSoap() {
        if (port == null) {
            EMRReceiverService service = new EMRReceiverService();
            service.setHandlerResolver(new SdaHandlerResolver());
            setPort(service.getEMRReceiverServiceSoap());
        }
        return port;
    }


    private void sendPatientsInfo() {
        transmitterLocal.send(this, PatientsToHs.class, "PatientsToHs.ToSend");
       /* List<PatientsToHs> patientsToHs = em.
                createNamedQuery("PatientsToHs.ToSend", PatientsToHs.class)
                .setParameter("now", new Timestamp((new Date()).getTime())).setMaxResults(MAX_RESULT).getResultList();

        for (Transmittable patientToHs : patientsToHs) {
            try {
                logger.info("Integration processing. Sending {} info. Transmittable.getId = {}", patientToHs.getClass().getCanonicalName(), patientToHs.getId());
                sendPatientInfo(patientToHs);
            } catch (Exception ex) {
                logger.error("Sending patient info. HS integration internal error.", ex);
            }
        }*/
    }

  /*  private void sendPatientInfo(Transmittable patientToHs) {
        try {
            logger.info("Integration processing Transmittable.patientId = {}",  patientToHs.getClass().getCanonicalName(), patientToHs.getId());
            int errCount = patientToHs.getErrCount();
            long step = 89 * 1000; // время до слейдующей попытки передачи данных
            patientToHs.setErrCount(errCount + 1);
            patientToHs.setSendTime(new Timestamp(patientToHs.getSendTime().getTime() + (long) (errCount) * step));
            sendEntity(patientToHs);
            em.remove(patientToHs);
        } catch (SOAPFaultException ex) {
            patientToHs.setInfo(ex.getMessage());
            ex.printStackTrace();
            em.flush();
        } catch (WebServiceException ex) {
            patientToHs.setInfo(ex.getMessage());
            ex.printStackTrace();
            em.flush();
        }
    }*/

    public void sendEntity(Object patientToHs) throws CoreException {
        assert patientToHs instanceof PatientsToHs;
        EMRReceiverServiceSoap port = getEmrReceiverServiceSoap();
        final LinkedList<AllergyInfo> emptyAllergy = new LinkedList<AllergyInfo>();
        List<DiagnosisInfo> emptyDiag = new LinkedList<DiagnosisInfo>();
        List<EpicrisisInfo> emptyEpi = new LinkedList<EpicrisisInfo>();
        port.container(
                SdaBuilder.toSda(new ClientInfo(
                        ((PatientsToHs) patientToHs).getPatient(),
                        dbSchemeKladrBeanLocal),
                        new EventInfo(getDefOrgName()),
                        emptyAllergy,
                        emptyDiag,
                        new LinkedList<DisabilitiesInfo>(),
                        emptyEpi,
                        new LinkedList<ServiceInfo>()));
    }

    private void sendNewEventToHS() {
        EMRReceiverServiceSoap port = getEmrReceiverServiceSoap();
        List<Event> newEvents = em.
                createQuery("SELECT hsi.event FROM HSIntegration hsi WHERE hsi.status = :newEvent AND hsi.event.execDate IS NOT NULL AND (" +
                        "hsi.event.eventType.requestType.code = 'clinic' OR " +
                        "hsi.event.eventType.requestType.code = 'hospital' OR " +
                        "hsi.event.eventType.requestType.code = 'stationary' OR " +
                        "hsi.event.eventType.requestType.code = 'policlinic' OR " +
                        "hsi.event.eventType.requestType.code = '4' OR " +
                        "hsi.event.eventType.requestType.code = '6' )", Event.class).setParameter("newEvent", HSIntegration.Status.NEW).setMaxResults(50).getResultList();

        for (Event event : newEvents) {
            try {
                logger.info("HS integration processing Event.Id = {}", event.getId());
                sendNewEventToHS(event, dbSchemeKladrBeanLocal, port);
            } catch (Exception ex) {
                logger.error("Sending event info. HS integration internal error. Event.Id = " + event.getId(), ex);
            }
        }
    }


    private void sendNewEventToHS(Event event, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal, EMRReceiverServiceSoap port) {
        final HSIntegration hsIntegration = em.find(HSIntegration.class, event.getId());
        try {
            final ClientInfo clientInfo = new ClientInfo(event.getPatient(), dbSchemeKladrBeanLocal);

            final HashSet<String> flatCodes = new HashSet<String>(Arrays.asList("received", "moving"));
            final Multimap<String, Action> actionsByTypeCode = dbEventBeanLocal.getActionsByTypeCode(event, flatCodes);
            final EventInfo eventInfo = new EventInfo(event, actionsByTypeCode, dbActionPropertyBeanLocal, hospitalBedBeanLocal);
            port.container(SdaBuilder.toSda(
                    clientInfo,
                    eventInfo,
                    getAllergies(event),
                    getDiagnosis(event),
                    getDisabilities(event.getPatient()),
                    getEpicrisis(event, clientInfo),
                    getServices(event, actionsByTypeCode)));
            hsIntegration.setStatus(HSIntegration.Status.SENDED);
            hsIntegration.setInfo("");
            em.flush();
        } catch (SOAPFaultException ex) {
            hsIntegration.setStatus(HSIntegration.Status.ERROR);
            hsIntegration.setInfo(ex.getMessage());
            logger.error("HS integration SOAP error. Event.Id = " + event.getId(), ex);
            em.flush();
        } catch (WebServiceException ex) {
            hsIntegration.setStatus(HSIntegration.Status.ERROR);
            hsIntegration.setInfo(ex.getMessage());
            logger.error("HS integration web service error. Event.Id = " + event.getId(), ex);
            em.flush();
        } catch (CoreException ex) {
            hsIntegration.setStatus(HSIntegration.Status.ERROR);
            hsIntegration.setInfo(ex.getMessage());
            logger.error("HS integration core error. Event.Id = " + event.getId(), ex);
            em.flush();
        } catch (Exception ex) {
            hsIntegration.setStatus(HSIntegration.Status.ERROR);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            hsIntegration.setInfo(ex.toString() + " " +  sw.toString());
            logger.error("HS integration internal error. Event.Id = " + event.getId(), ex);
            em.flush();
        }

    }

    private List<ServiceInfo> getServices(final Event event, final Multimap<String, Action> actionsByTypeCode) {
        //TODO move to DbAtionBean!
        List<Action> services = em.createQuery("SELECT a FROM Action a WHERE a.event.id = :eventId AND a.deleted = 0 AND a.actionType.service IS NOT NULL", Action.class)
                .setParameter("eventId", event.getId()).getResultList();
        List<ServiceInfo> res = new LinkedList<ServiceInfo>();
        for (Action action : services) {
            res.add(new ServiceInfo(action, actionsByTypeCode, dbCustomQueryBean, dbRbMedicalAidProfileBean, hospitalBedBeanLocal));
        }
        return res;
    }

    private List<DisabilitiesInfo> getDisabilities(Patient patient) {
        List<DisabilitiesInfo> res = new LinkedList<DisabilitiesInfo>();
        for (ClientSocStatus clientSocStatus : patient.getClientSocStatuses()) {
            if (clientSocStatus.getSocStatusClass() != null && ClientSocStatus.DISABILITY_CODE.equals(clientSocStatus.getSocStatusClass().getCode())) {
                final DisabilitiesInfo disability = new DisabilitiesInfo(clientSocStatus);
                res.add(disability);
            }
        }
        return res;
    }

    /**
     * @param event
     * @return
     */
    private List<EpicrisisInfo> getEpicrisis(Event event, ClientInfo clientInfo) {
        List<EpicrisisInfo> res = new LinkedList<EpicrisisInfo>();
        List<Action> actions = em.createQuery("SELECT a FROM Action a WHERE a.event.id = :eventId AND a.deleted = 0", Action.class)
                .setParameter("eventId", event.getId()).getResultList();
        for (Action a : actions) {
            List<ActionType> actionTypes = em.createQuery("SELECT at FROM ActionType at WHERE at.id = :groupId AND at.deleted = 0", ActionType.class)
                    .setParameter("groupId", a.getActionType().getGroupId()).getResultList();
            if ((!actionTypes.isEmpty() && actionTypes.iterator().next().getFlatCode().equals("epicrisis")) ||
                    isMedicalDocument(a.getActionType().getMnemonic())) {
                res.add(new EpicrisisInfo(a, clientInfo, dbActionPropertyBeanLocal));
            }
        }
        return res;
    }

    private boolean isMedicalDocument(String mnemonic) {
        //TODO Add javadoc for mnemonics!
        final Set<String> mnemonicSet = new HashSet<String>() {{
            add("EXAM");
            add("EPI");
            add("ORD");
            add("JOUR");
            add("NOT");
            add("CONS");
            add("THER");
            add("DIR");
            add("EPIAMB");
        }};
        if (mnemonic == null) {
            return false;
        }
        return mnemonicSet.contains(mnemonic);
    }

    /**
     * @param event
     * @return
     */
    private List<DiagnosisInfo> getDiagnosis(Event event) {
        List<DiagnosisInfo> res = new LinkedList<DiagnosisInfo>();
        List<Diagnostic> diagnostics =
                em.createQuery("SELECT d FROM Diagnostic d WHERE d.event.id = :eventId AND d.deleted = 0 AND d.diagnosis IS NOT NULL", Diagnostic.class)
                        .setParameter("eventId", event.getId()).getResultList();
        for (Diagnostic diagnostic : diagnostics) {
            res.add(new DiagnosisInfo(event, diagnostic, dbQueryBean));
        }
        return res;
    }

    /**
     * @param event
     * @return
     */
    private List<AllergyInfo> getAllergies(Event event) {
        List<AllergyInfo> res = new LinkedList<AllergyInfo>();
        for (ClientAllergy allergy : event.getPatient().getClientAllergies()) {
            res.add(new AllergyInfo(allergy));
        }
        return res;
    }

    /**
     * @param newEvents
     */
    private void addNewEvent(List<Event> newEvents) {
        for (Event event : newEvents) {
            HSIntegration hsi = new HSIntegration();
            hsi.setEvent(event);
            hsi.setStatus(HSIntegration.Status.NEW);
            em.persist(hsi);
            em.flush();
        }
    }

    public CodeNameSystem getDefOrgName() {
        Organisation organization = null;
        try {
            organization = organizationBeanLocal.getOrganizationById(ConfigManager.Common().OrgId());
        } catch (CoreException e) {
        }
        return EventInfo.getOrgCodeName(organization);
    }
}
