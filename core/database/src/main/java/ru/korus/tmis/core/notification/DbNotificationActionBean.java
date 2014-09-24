package ru.korus.tmis.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.NotificationAction;
import ru.korus.tmis.core.entity.model.NotificationActionType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 16:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Singleton
public class DbNotificationActionBean implements DbNotificationActionBeanLocal, Sender {

    @EJB
    private TransmitterLocal transmitterLocal;

    @EJB
    private NotificationBeanLocal notificationBeanLocal;

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    private static final Logger logger = LoggerFactory.getLogger(DbNotificationActionBean.class);

    @Override
    public void pullDb() {
        try {
            logger.info("Notifications entry...");
            if (ConfigManager.Common().isNotificationActive()) {
                logger.info("Notification is active...");
                transmitterLocal.send(this, NotificationAction.class, "NotificationAction.ToSend");
            } else {
                logger.info("Notifications is disabled...");
            }
        } catch (Exception ex) {
            logger.error("Notifications internal error.", ex);
        }
    }

    @Override
    public List<NotificationActionType> getActionsByPath(String baseUrl) {
        return em.createNamedQuery( "NotificationActionType.findByUrl", NotificationActionType.class).
                setParameter("baseUrl", "%" + baseUrl + "%").getResultList();
    }

    @Override
    public void removeFromNotification(Integer actionTypeId) {
        final List<NotificationActionType> notificationActionTypes = getNotificationUrls(actionTypeId);
        for (NotificationActionType notificationActionType : notificationActionTypes) {
            em.remove(notificationActionType);
        }
    }

    @Override
    public List<ActionType> getRisarCandidatActions() {
        return em.createNamedQuery("ActionType.findRisarActionType", ActionType.class).getResultList();

    }

    @Override
    public void addNotification(Integer actionTypeId, String path) {
        NotificationActionType notificationActionType = new NotificationActionType();
        notificationActionType.setActionType(em.find(ActionType.class, actionTypeId));
        notificationActionType.setBaseUrl(path);
        em.persist(notificationActionType);
    }

    @Override
    public void sendEntity(Object entity) throws CoreException {
        if (entity instanceof NotificationAction) {
            final NotificationAction notificationAction = (NotificationAction) entity;
            if (notificationAction.getAction() != null &&
                    notificationAction.getAction().getActionType() != null) {
                final Integer actionTypeId = notificationAction.getAction().getActionType().getId();
                final Set<String> listeners = notificationBeanLocal.getListener(actionTypeId);
                //" Lazy" инициализация нотификации
                if ( listeners == null || listeners.isEmpty()) {//Добавляем новые url для нотификации
                    //Удаление url и добавление к ранее заданному actionId требует перезапуск ядра
                    final List<NotificationActionType> notificationActionTypes = getNotificationUrls(actionTypeId);
                    for (NotificationActionType notificationActionType : notificationActionTypes) {
                        notificationBeanLocal.addListener(actionTypeId, notificationActionType.getBaseUrl());
                    }
                }
                notificationBeanLocal.sendNotification(notificationAction.getAction());
            } else {
                throw new CoreException("Error: NotificationAction.actionId is not valid or incorrect action type for NotificationAction.actionId. " +
                        "actionId : " + notificationAction.getAction() );
            }
        } else {
            throw new CoreException("Type mismatch. entity : " + entity );
        }
    }

    private List<NotificationActionType> getNotificationUrls(Integer actionTypeId) {
        return em.createNamedQuery("NotificationActionType.findByActionType", NotificationActionType.class).setParameter("actionTypeId", actionTypeId).getResultList();
    }
}
