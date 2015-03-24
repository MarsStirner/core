package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientRelation;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventClientRelation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.03.2015, 16:10 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbEventClientRelationBean implements DbEventClientRelationBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public EventClientRelation insertOrUpdate(Event event, ClientRelation clientRelation, String note) {
        EventClientRelation res = findByEventAndClientRelation(event, clientRelation);
        if(res == null) {
            res = insertNew(event, clientRelation, note);
        }
        return res;
    }

    @Override
    public List<EventClientRelation> getByEvent(Event event) {
        return   em.createNamedQuery("Event_ClientRelation.findByEvent", EventClientRelation.class)
                .setParameter("eventId", event.getId())
                .getResultList();
    }

    private EventClientRelation insertNew(Event event, ClientRelation clientRelation, String note) {
        EventClientRelation res = new EventClientRelation();
        res.setEvent(event);
        res.setClientRelation(clientRelation);
        res.setNote(note);
        em.persist(res);
        return res;
    }

    private EventClientRelation findByEventAndClientRelation(Event event, ClientRelation clientRelation) {
        List<EventClientRelation> res = em.createNamedQuery("Event_ClientRelation.findByEventAndClientRealtion", EventClientRelation.class)
                .setParameter("eventId", event.getId())
                .setParameter("clientRelationId", clientRelation.getId())
                .getResultList();
        return res.isEmpty() ? null : res.get(0);
    }
}
