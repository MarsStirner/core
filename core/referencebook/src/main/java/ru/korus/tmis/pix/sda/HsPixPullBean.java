package ru.korus.tmis.pix.sda;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.ClientAllergy;
import ru.korus.tmis.core.entity.model.Diagnostic;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.HSIntegration;
import ru.korus.tmis.core.entity.model.PatientsToHs;
import ru.korus.tmis.pix.sda.ws.SDASoapServiceService;
import ru.korus.tmis.pix.sda.ws.SDASoapServiceServiceSoap;

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
public class HsPixPullBean {

    private static final Logger logger = LoggerFactory.getLogger(HsPixPullBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    @EJB
    DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal;

    @Schedule(hour = "*", minute = "*", second = "30")
    void pullDb() {
        SDASoapServiceService service = new SDASoapServiceService();
        service.setHandlerResolver(new SdaHandlerResolver());
        SDASoapServiceServiceSoap port = service.getSDASoapServiceServiceSoap();

        Integer maxId = em.createQuery("SELECT max(hsi.eventId) FROM HSIntegration hsi", Integer.class).getSingleResult();
        if (maxId == null) {
            maxId = 0;
        }
        List<Event> newEvents =
                em.createQuery("SELECT e FROM Event e WHERE e.id > :max AND (" +
                        "e.eventType.requestType.code = 'clinic' OR " +
                        "e.eventType.requestType.code = 'hospital' OR " +
                        "e.eventType.requestType.code = 'stationary' OR " +
                        "e.eventType.requestType.code = '4' OR " +
                        "e.eventType.requestType.code = '6' )", Event.class).setParameter("max", maxId).getResultList();

        addNewEvent(newEvents);

        // Отправка завершенных обращений
        sendNewEventToHS(port);
        // Отправка карточек новых/обновленных пациентов
        sendPatientsInfo(port);
    }

    /**
     * @param port
     * 
     */
    private void sendPatientsInfo(SDASoapServiceServiceSoap port) {
        List<PatientsToHs> patientsToHs = em.
                createQuery("SELECT pths FROM PatientsToHs pths WHERE pths.sendTime < :now", PatientsToHs.class)
                .setParameter("now", new Timestamp((new Date()).getTime())).getResultList();
        for (PatientsToHs patientToHs : patientsToHs) {
            try {
                int errCount = patientToHs.getErrCount();
                long step = 89 * 1000; // время до слейдующей попытки передачи данных
                patientToHs.setErrCount(errCount + 1);
                patientToHs.setSendTime(new Timestamp(patientToHs.getSendTime().getTime() + (long) (errCount) * step));
                final LinkedList<AllergyInfo> emptyAllergy = new LinkedList<AllergyInfo>();
                List<DiagnosisInfo> emptyDiag = new LinkedList<DiagnosisInfo>();
                List<EpicrisisInfo> emptyEpi = new LinkedList<EpicrisisInfo>();
                port.sendSDA(PixInfo.toSda(new ClientInfo(patientToHs.getPatient(), dbSchemeKladrBeanLocal), null, emptyAllergy, emptyDiag, emptyEpi));
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
    }

    /**
     * @param port
     * 
     */
    private void sendNewEventToHS(SDASoapServiceServiceSoap port) {
        List<Event> newEvents = em.
                createQuery("SELECT hsi.event FROM HSIntegration hsi WHERE hsi.status = :newEvent AND hsi.event.execDate IS NOT NULL AND (" +
                        "hsi.event.eventType.requestType.code = 'clinic' OR " +
                        "hsi.event.eventType.requestType.code = 'hospital' OR " +
                        "hsi.event.eventType.requestType.code = 'stationary' OR " +
                        "hsi.event.eventType.requestType.code = '4' OR " +
                        "hsi.event.eventType.requestType.code = '6' )", Event.class).setParameter("newEvent", HSIntegration.Status.NEW).getResultList();

        for (Event event : newEvents) {
            sendNewEventToHS(event, em, dbSchemeKladrBeanLocal, port);
        }
    }

    public void sendNewEventToHS(Event event, EntityManager em, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal, SDASoapServiceServiceSoap port) {
        final HSIntegration hsIntegration = em.find(HSIntegration.class, event.getId());
        try {
            final ClientInfo clientInfo = new ClientInfo(event.getPatient(), dbSchemeKladrBeanLocal);
            final EventInfo eventInfo = new EventInfo(event);
            port.sendSDA(PixInfo.toSda(clientInfo, eventInfo, getAllergies(event), getDiagnosis(event), getEpicrisis(event, clientInfo)));
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
        }
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
            if (!actionTypes.isEmpty()) {
                if (actionTypes.get(0).getFlatCode().equals("epicrisis")) {
                    res.add(new EpicrisisInfo(a, clientInfo, EventInfo.getOrgFullName(event), dbActionPropertyBeanLocal));
                }
            }
        }
        return res;
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
            res.add(new DiagnosisInfo(event, diagnostic.getDiagnosis(), diagnostic.getDiagnosisType()));
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
            res.add(new AllergyInfo(allergy, EventInfo.getOrgFullName(event)));
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
}
