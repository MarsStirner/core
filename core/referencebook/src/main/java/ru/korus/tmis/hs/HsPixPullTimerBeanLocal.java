package ru.korus.tmis.hs;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.02.14, 10:39 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface HsPixPullTimerBeanLocal {
    public void pullDb(boolean ignoreSetting);
}
