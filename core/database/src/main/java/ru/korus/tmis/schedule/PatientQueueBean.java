package ru.korus.tmis.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 18.11.13, 16:53 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class PatientQueueBean implements PatientQueueBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @EJB
    private PersonScheduleBeanLocal personScheduleBean;

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(PatientQueueBean.class);

    private ActionType queueActionType = null;
    private EventType queueEventType = null;
    private ActionPropertyType queueActionPropertyType = null;


    @Override
    public List<Ticket> getPatientTickets(Patient patient, Date beginDate, Date endDate) {
        return null;
    }

    @Override
    public boolean checkPatientQueueByDateTime(final Patient patient, final Date date, final Date begTime, final Date endTime) {
        //Все Action пациента, у которых ActionType.code = 'queue' и Action.directionDate в рамках заданной даты
        final List<Action> personQueueActions = em.createQuery("SELECT a FROM Action a " +
                "WHERE a.actionType = :actionType " +
                "AND a.event.patient = :patient " +
                "AND a.deleted = false " +
                "AND FUNC('DATE', a.directionDate) = :checkDate", Action.class)
                //НЕ ОШИБКА, просто вызов функции MYSQL DATE(), checkDate по этой же причине и не видет в запросе, но подставит правильно
                .setParameter("actionType", getQueueActionType())
                .setParameter("patient", patient)
                .setParameter("checkDate", date)
                .getResultList();
        for (Action currentAction : personQueueActions) {
            logger.debug("Patient has queueAction[{}] for this day, beginnig in {}",
                    currentAction.getId(), currentAction.getDirectionDate());
            final Ticket queueTicket = getQueueActionTicket(currentAction);
            if (queueTicket != null && begTime.before(queueTicket.getEndTime()) && endTime.after(queueTicket.getBegTime())) {
                return true;
            }
        }
        return false;
    }

    private Ticket getQueueActionTicket(final Action queueAction) {
        final Action ambulatoryAction = getAmbulatoryActionByQueueAction(queueAction);
        if (ambulatoryAction == null) {
            return null;
        }
        logger.debug("Get endTime for AmbulatoryAction[{}]", ambulatoryAction.getId());
        PersonScheduleBean.PersonSchedule personSchedule = personScheduleBean.newInstanceOfPersonSchedule(ambulatoryAction);
        try {
            personScheduleBean.formTickets(personSchedule);
        } catch (CoreException e) {
            return null;
        }
        int queueIndex = -1;
        for (APValueAction currentAP_A : personSchedule.getQueue()) {
            if (queueAction.equals(currentAP_A.getValue())) {
                queueIndex = currentAP_A.getId().getIndex();
                break;
            }
        }
        return personScheduleBean.getTicketByQueueIndex(personSchedule, queueIndex);
    }

    private Action getAmbulatoryActionByQueueAction(final Action queueAction) {
        final List<Action> queueActionList = em.createQuery("SELECT ap.action FROM APValueAction ap_a, ActionProperty ap " +
                "WHERE ap_a.value = :queueAction " +
                "AND ap.id = ap_a.id.id " +
                "AND ap.actionPropertyType.name = 'queue'", Action.class)
                .setParameter("queueAction", queueAction)
                        //.setParameter("queueActionPropertyType", queueActionPropertyType)
                .getResultList();
        if (queueActionList.isEmpty()) {
            return null;
        }
        return queueActionList.get(0);
    }

    @Override
    public EventType getQueueEventType() {
        if (queueEventType != null) {
            return queueEventType;
        }
        final List<EventType> queueEventTypeList = em.createNamedQuery("EventType.getQueueEventType", EventType.class)
                .getResultList();
        switch (queueEventTypeList.size()) {
            case 0: {
                logger.error("EventType[code='queue'] not exists");
                queueEventType = null;
                break;
            }
            case 1: {
                queueEventType = queueEventTypeList.get(0);
                break;
            }
            default: {
                if (logger.isWarnEnabled()) {
                    for (EventType currentEventType : queueEventTypeList) {
                        logger.warn("EventType[code='queue'] is not unique: id={}", currentEventType.getId());
                    }
                }
                queueEventType = queueEventTypeList.get(0);
                break;
            }
        }
        return queueEventType;
    }

    @Override
    public ActionType getQueueActionType() {
        if (queueActionType != null) {
            return queueActionType;
        }
        final List<ActionType> queueActionTypeList = em.createNamedQuery("ActionType.getQueueActionType", ActionType.class)
                .getResultList();
        switch (queueActionTypeList.size()) {
            case 0: {
                logger.error("ActionType[code='queue'] not exists");
                queueActionType = null;
                break;
            }
            case 1: {
                queueActionType = queueActionTypeList.get(0);
                break;
            }
            default: {
                if (logger.isWarnEnabled()) {
                    for (ActionType currentActionType : queueActionTypeList) {
                        logger.warn("ActionType[code='queue'] is not unique: id={}", currentActionType.getId());
                    }
                }
                queueActionType = queueActionTypeList.get(0);
                break;
            }
        }
        return queueActionType;
    }

    @Override
    public ActionPropertyType getQueueActionPropertyType() {
        if (queueActionPropertyType != null) {
            return queueActionPropertyType;
        }
        final List<ActionPropertyType> actionPropertyTypes = em.createQuery("SELECT aptype FROM ActionPropertyType aptype" +
                " WHERE aptype.name = 'queue' AND aptype.actionType.code = 'amb' AND aptype.actionType.deleted = 0 AND aptype.deleted = 0", ActionPropertyType.class)
                .getResultList();
        switch (actionPropertyTypes.size()) {
            case 0: {
                logger.error("ActionPropertyType[name='queue'] not exists");
                queueActionPropertyType = null;
                break;
            }
            case 1: {
                queueActionPropertyType = actionPropertyTypes.get(0);
                break;
            }
            default: {
                if (logger.isWarnEnabled()) {
                    for (ActionPropertyType currentActionPropertyType : actionPropertyTypes) {
                        logger.warn("ActionType[code='queue'] is not unique: id={}", currentActionPropertyType.getId());
                    }
                }
                queueActionPropertyType = actionPropertyTypes.get(0);
                break;
            }
        }
        return queueActionPropertyType;
    }
}
