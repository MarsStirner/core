package ru.korus.tmis.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.NotificationAction;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 16:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
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
            logger.error("1C ODVD integration internal error.", ex);
        }
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
                    final List<String> urls = getNotificationUrls(actionTypeId);
                    for (String url : urls) {
                        notificationBeanLocal.addListener(actionTypeId, url);
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

    private List<String> getNotificationUrls(Integer actionTypeId) {
        return em.createNamedQuery( "NotificationActionType.findUrlsByActionType", String.class).setParameter("actionTypeId", actionTypeId).getResultList();
    }
}
