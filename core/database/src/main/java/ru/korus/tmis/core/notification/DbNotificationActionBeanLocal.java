package ru.korus.tmis.core.notification;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 16:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbNotificationActionBeanLocal {
    void pullDb();
}
