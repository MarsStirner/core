package ru.korus.tmis.pix.sda;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbQueryBeanLocal;
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.RbMedicalAidProfileBeanLocal;
import ru.korus.tmis.core.database.RbMedicalAidTypeBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;
import ru.korus.tmis.core.exception.CoreException;
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
import java.sql.Timestamp;
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
public class HsPixPullBean implements HsPixPullTimerBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(HsPixPullBean.class);
    private static final int MAX_RESULT = 100;

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
    private RbMedicalAidTypeBeanLocal dbRbMedicalAidTypeBeanLocal;

    @EJB
    private DbQueryBeanLocal dbQueryBean;

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
                        em.createNamedQuery("Event.toHS", Event.class).setParameter("max", maxId).setMaxResults(MAX_RESULT).getResultList();

                addNewEvent(newEvents);

                EMRReceiverServiceSoap port = getEmrReceiverServiceSoap();

                // Отправка завершенных обращений
                sendNewEventToHS(port);
                // Отправка карточек новых/обновленных пациентов
                sendPatientsInfo(port);
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

    /**
     * @param port
     */
    private void sendPatientsInfo(EMRReceiverServiceSoap port) {
        List<PatientsToHs> patientsToHs = em.
                createNamedQuery("PatientsToHs.ToSend", PatientsToHs.class)
                .setParameter("now", new Timestamp((new Date()).getTime())).setMaxResults(MAX_RESULT).getResultList();
        for (PatientsToHs patientToHs : patientsToHs) {
            try {
                logger.info("HS integration processing. Sending patient info. PatientsToHs.client_id = {}", patientToHs.getPatientId());
                sendPatientInfo(port, patientToHs);
            } catch (Exception ex) {
                logger.error("Sending patient info. HS integration internal error.", ex);
            }
        }
    }

    private void sendPatientInfo(EMRReceiverServiceSoap port, PatientsToHs patientToHs) {
        try {
            logger.info("HS integration processing PatientsToHs.patientId = {}", patientToHs.getPatientId());
            int errCount = patientToHs.getErrCount();
            long step = 89 * 1000; // время до слейдующей попытки передачи данных
            patientToHs.setErrCount(errCount + 1);
            patientToHs.setSendTime(new Timestamp(patientToHs.getSendTime().getTime() + (long) (errCount) * step));
            final LinkedList<AllergyInfo> emptyAllergy = new LinkedList<AllergyInfo>();
            List<DiagnosisInfo> emptyDiag = new LinkedList<DiagnosisInfo>();
            List<EpicrisisInfo> emptyEpi = new LinkedList<EpicrisisInfo>();
            port.container(
                    SdaBuilder.toSda(new ClientInfo(
                            patientToHs.getPatient(),
                            dbSchemeKladrBeanLocal),
                            new EventInfo(getDefOrgName()),
                            emptyAllergy,
                            emptyDiag,
                            new LinkedList<DisabilitiesInfo>(),
                            emptyEpi,
                            new LinkedList<ServiceInfo>()));
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
    }

    /**
     * @param port
     */
    private void sendNewEventToHS(EMRReceiverServiceSoap port) {
        List<Event> newEvents = em.
                createQuery("SELECT hsi.event FROM HSIntegration hsi WHERE hsi.status = :newEvent AND hsi.event.execDate IS NOT NULL AND (" +
                        "hsi.event.eventType.requestType.code = 'clinic' OR " +
                        "hsi.event.eventType.requestType.code = 'hospital' OR " +
                        "hsi.event.eventType.requestType.code = 'stationary' OR " +
                        "hsi.event.eventType.requestType.code = 'policlinic' OR " +
                        "hsi.event.eventType.requestType.code = '4' OR " +
                        "hsi.event.eventType.requestType.code = '6' )", Event.class).setParameter("newEvent", HSIntegration.Status.NEW).getResultList();

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
            final EventInfo eventInfo = new EventInfo(event, actionsByTypeCode, dbActionPropertyBeanLocal);
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
            ex.printStackTrace();
            em.flush();
        } catch (WebServiceException ex) {
            hsIntegration.setInfo(ex.getMessage());
            ex.printStackTrace();
            em.flush();
        } catch (CoreException e) {
            hsIntegration.setInfo(e.getMessage());
            e.printStackTrace();
            em.flush();
        }
    }

    private List<ServiceInfo> getServices(final Event event, final Multimap<String, Action> actionsByTypeCode) {
        //TODO move to DbAtionBean!
        List<Action> services = em.createQuery("SELECT a FROM Action a WHERE a.event.id = :eventId AND a.deleted = 0 AND a.actionType.service IS NOT NULL", Action.class)
                .setParameter("eventId", event.getId()).getResultList();
        List<ServiceInfo> res = new LinkedList<ServiceInfo>();
        for (Action action : services) {
            res.add(new ServiceInfo(action, actionsByTypeCode, dbCustomQueryBean, dbRbMedicalAidProfileBean, dbRbMedicalAidTypeBeanLocal));
        }
        return res;
    }

    private List<DisabilitiesInfo> getDisabilities(Patient patient) {
        List<DisabilitiesInfo> res = new LinkedList<DisabilitiesInfo>();
        for (ClientSocStatus clientSocStatus : patient.getClientSocStatuses()) {
            if (ClientSocStatus.DISABILITY_CODE.equals(clientSocStatus.getSocStatusClass().getCode())) {
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
