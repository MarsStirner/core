package ru.korus.tmis.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 16:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbNotificationActionBean implements DbNotificationActionBeanLocal, Sender {
    @EJB
    private TransmitterLocal transmitterLocal;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
