package ru.korus.tmis.pix.sda;

import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.HSIntegration;
import ru.korus.tmis.pix.sda.ws.SDASoapServiceService;
import ru.korus.tmis.pix.sda.ws.SDASoapServiceServiceSoap;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.List;

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
        sendToHS();
    }

    /**
     * 
     */
    private void sendToHS() {
        List<Event> newEvents = em.
                createQuery("SELECT hsi.event FROM HSIntegration hsi WHERE hsi.status = :newEvent AND hsi.event.execDate IS NOT NULL AND (" +
                        "hsi.event.eventType.requestType.code = 'clinic' OR " +
                        "hsi.event.eventType.requestType.code = 'hospital' OR " +
                        "hsi.event.eventType.requestType.code = 'stationary' OR " +
                        "hsi.event.eventType.requestType.code = '4' OR " +
                        "hsi.event.eventType.requestType.code = '6' )", Event.class).setParameter("newEvent", HSIntegration.Status.NEW).getResultList();

        for (Event event : newEvents) {
            sendToHS(event,em, dbSchemeKladrBeanLocal);
        }
    }

    /**
     * @param event
     * @param entityManager
     */
     public void sendToHS(Event event, EntityManager em, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal) {
        SDASoapServiceService service = new SDASoapServiceService();
        service.setHandlerResolver(new SdaHandlerResolver());
        SDASoapServiceServiceSoap port = service.getSDASoapServiceServiceSoap();
        final HSIntegration hsIntegration = em.find(HSIntegration.class, event.getId());
        try {
            port.sendSDA(PixInfo.toSda(new ClientInfo(event.getPatient(), dbSchemeKladrBeanLocal), new EventInfo(event)));
            hsIntegration.setStatus(HSIntegration.Status.SENDED);
            hsIntegration.setInfo("");
            em.flush();
        } catch (SOAPFaultException  ex) {
            hsIntegration.setStatus(HSIntegration.Status.ERROR);
            hsIntegration.setInfo(ex.getMessage());
            ex.printStackTrace();
            em.flush();
        } catch ( WebServiceException ex) {
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
