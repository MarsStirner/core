package ru.korus.tmis.pix.sda;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.HSIntegration;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;
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

    @EJB
    Database db;

    @EJB
    DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal;

    @Schedule(hour = "*", minute = "*")
    void pullDb() {
        Integer maxId = db.getEntityMgr().createQuery("SELECT max(hsi.eventId) FROM HSIntegration hsi", Integer.class).getSingleResult();
        if (maxId == null) {
            maxId = 0;
        }
        List<Event> newEvents =
                db.getEntityMgr().createQuery("SELECT e FROM Event e WHERE e.id > :max", Event.class).setParameter("max", maxId).getResultList();

        addNewEvent(newEvents);
        sendToHS();
    }

    /**
     * 
     */
    private void sendToHS() {
        List<Event> newEvents = db.getEntityMgr().
                createQuery("SELECT hsi.event FROM HSIntegration hsi WHERE hsi.status = :newEvent AND hsi.event.execDate IS NOT NULL AND (" +
                        "hsi.event.eventType.requestType.code = 'clinic' OR " +
                        "hsi.event.eventType.requestType.code = 'hospital' OR " +
                        "hsi.event.eventType.requestType.code = 'stationary' OR " +
                        "hsi.event.eventType.requestType.code = '4' OR " +
                        "hsi.event.eventType.requestType.code = '6' )", Event.class).setParameter("newEvent", HSIntegration.Status.NEW).getResultList();

        for (Event event : newEvents) {
            SendToHS(event, db.getEntityMgr(), dbSchemeKladrBeanLocal);
        }
    }

    /**
     * @param event
     * @param entityManager
     */
    static public void SendToHS(Event event, EntityManager em, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal) {
        SDASoapServiceService service = new SDASoapServiceService();
        service.setHandlerResolver(new AuthentificationHeaderHandlerResolver());
        SDASoapServiceServiceSoap port = service.getSDASoapServiceServiceSoap();
        try {
            port.sendSDA(PixInfo.toSda(new ClientInfo(event.getPatient(), dbSchemeKladrBeanLocal), new EventInfo(event)));
            em.find(HSIntegration.class, event.getId()).setStatus(HSIntegration.Status.SENDED);
            em.flush();
        } catch (SOAPFaultException ex) {
            ex.printStackTrace();
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
            db.getEntityMgr().persist(hsi);
        }
        db.getEntityMgr().flush();
    }
}
