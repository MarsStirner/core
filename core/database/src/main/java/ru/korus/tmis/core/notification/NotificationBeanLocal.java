package ru.korus.tmis.core.notification;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.NotificationAction;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.io.IOException;
import java.net.URI;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.08.14, 11:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface NotificationBeanLocal {

    void addListener(String flatCode, String modulePath);

    void addListener(Integer actionTypeId, String modulePath);

    Set<String> getListener(Integer actionTypeId);

    void sendNotification(NotificationAction notificationAction) throws CoreException;

}
