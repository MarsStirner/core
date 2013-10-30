package ru.korus.tmis.core.database.epgu;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.communication.QueueTicket;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 28.10.13, 19:55 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class EPGUTicketBean implements EPGUTicketBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(EPGUTicketBean.class);

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public List<QueueTicket> pullDatabase() {
        return em.createNamedQuery("QueueTicket.getChanges", QueueTicket.class)
                .setParameter("statusList", ImmutableList.of(
                        QueueTicket.Status.NEW.toString(),
                        QueueTicket.Status.CANCELLED.toString())
                )
                .getResultList();
    }

    @Override
    public int changeStatus(final QueueTicket ticket, final QueueTicket.Status status) {
        return em.createNamedQuery("QueueTicket.changeStatus", QueueTicket.class)
                .setParameter("ticketId", ticket.getId())
                .setParameter("status", status.toString())
                .setParameter("modifyDateTime", new Date(), TemporalType.TIMESTAMP)
                .executeUpdate();
    }
}
