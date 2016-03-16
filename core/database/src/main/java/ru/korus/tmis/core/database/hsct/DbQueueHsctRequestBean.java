package ru.korus.tmis.core.database.hsct;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.hsct.QueueHsctRequest;
import ru.korus.tmis.core.entity.model.hsct.Status;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 04.02.2016, 17:27 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */

@Stateless
public class DbQueueHsctRequestBean {
    private static final Logger LOGGER = LoggerFactory.getLogger("HSCT");

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    public QueueHsctRequest getRequestByAction(final Action action) {
        return em.find(QueueHsctRequest.class, action.getId());
    }

    public QueueHsctRequest getRequestByActionId(final int actionId) {
        return em.find(QueueHsctRequest.class, actionId);
    }

    public QueueHsctRequest insertNewRequest(final Action action, final Staff user) {
        final QueueHsctRequest res = new QueueHsctRequest(action, user);
        em.persist(res);
        return res;
    }

    public boolean markActive(final QueueHsctRequest entry, final Staff user) {
        switch (entry.getStatus()) {
            case NEW:
                // У новой заявки надо сменить пользователя и дату след отправки
                entry.setSendDateTime(new Date());
                entry.setPerson(user);
                em.merge(entry);
                return true;
            case IN_PROGRESS:
                LOGGER.warn("QueueRequest[{}] is in IN_PROGRESS state and could not be changed", entry);
                return false;
            case CANCELED:
                // У отмененнной заявки выставляем статус как у новой , затем меняем пользователя сделавшего изменение и дату оптарвки на сейчас
                entry.setStatus(Status.NEW);
                entry.setSendDateTime(new Date());
                entry.setPerson(user);
                em.merge(entry);
                return true;
            case ERROR:
                // не меняем статус, выставляем только дату отправки и нового пользователя
                entry.setSendDateTime(new Date());
                entry.setPerson(user);
                em.merge(entry);
                return true;
            case FINISHED:
                LOGGER.warn("QueueRequest[{}] is in FINISHED state and could not be resend", entry);
                return false;
            default:
                LOGGER.error("UNKNOWN STATE [{}]", entry);
                return false;
        }
    }


    public boolean markCanceled(final QueueHsctRequest entry, final Staff user) {
        switch (entry.getStatus()) {
            case NEW:
                // У новой заявки надо сменить пользователя
                entry.setStatus(Status.CANCELED);
                entry.setPerson(user);
                em.merge(entry);
                return true;
            case IN_PROGRESS:
                LOGGER.warn("QueueRequest[{}] is in IN_PROGRESS state and could not be changed", entry);
                return false;
            case CANCELED:
                LOGGER.warn("Already CANCELED [{}]", entry);
                return true;
            case ERROR:
                // не меняем статус, выставляем только дату отправки и нового пользователя
                entry.setStatus(Status.ERROR);
                entry.setPerson(user);
                em.merge(entry);
                return true;
            case FINISHED:
                LOGGER.warn("QueueRequest[{}] is in FINISHED state and could not be resend", entry);
                return false;
            default:
                LOGGER.error("UNKNOWN STATE [{}]", entry);
                return false;
        }
    }

    public List<QueueHsctRequest> getAllActive() {
        try {
            return em.createNamedQuery("QueueHsctRequest.findAllByStatuses", QueueHsctRequest.class).setParameter(
                    "statusList", ImmutableList.of(Status.NEW, Status.ERROR, Status.IN_PROGRESS)
            ).getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception while getAllActive", e);
            return new ArrayList<>(0);
        }
    }
}
