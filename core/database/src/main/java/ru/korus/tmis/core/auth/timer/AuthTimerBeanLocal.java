package ru.korus.tmis.core.auth.timer;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 12:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface AuthTimerBeanLocal {

    void  timeoutHandler();
}
