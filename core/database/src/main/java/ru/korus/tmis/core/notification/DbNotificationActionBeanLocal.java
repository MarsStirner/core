package ru.korus.tmis.core.notification;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.NotificationActionType;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 16:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbNotificationActionBeanLocal {

    void pullDb();

    List<NotificationActionType> getActionsByPath(String baseUrl);

    void removeFromNotification(Integer id);

    List<ActionType> getRisarCandidatActions();
}
