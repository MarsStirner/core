package ru.korus.tmis.core.notification;

import ru.korus.tmis.core.entity.model.Action;

import javax.ejb.Local;
import java.net.URI;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.08.14, 11:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface NotificationBeanLocal {

    void addListener(String flatCode, String modulePath);

    void sendNotification(Action action);

}
