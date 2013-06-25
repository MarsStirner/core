package ru.korus.tmis.pix.sda;

import java.sql.Timestamp;
import java.util.Date;
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

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
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
                long step = 89*1000; //время до слейдующей попытки передачи данных
                patientToHs.setErrCount(errCount + 1);
                patientToHs.setSendTime(new Timestamp(patientToHs.getSendTime().getTime() + (long)(errCount)*step));
                port.sendSDA(PixInfo.toSda(new ClientInfo(patientToHs.getPatient(), dbSchemeKladrBeanLocal), null));
                em.remove(patientToHs);
            } catch (SOAPFaultException  ex) {
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
            port.sendSDA(PixInfo.toSda(new ClientInfo(event.getPatient(), dbSchemeKladrBeanLocal), new EventInfo(event)));
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
     * @param newEvents
     */
    private void addNewEvent(List<Event> newEvents) {
        for (Event event : newEvents) {
            HSIntegration hsi = new HSIntegration();
            hsi.setEvent(event);
            hsi.setStatus(HSIntegration.Status.NEW);
            em.persist(hsi);
        }
        em.flush();
    }
}