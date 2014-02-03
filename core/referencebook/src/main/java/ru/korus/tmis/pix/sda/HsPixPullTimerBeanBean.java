package ru.korus.tmis.pix.sda;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.02.14, 10:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class HsPixPullTimerBeanBean {

    @EJB
    HsPixPullBeanLocal hsPixPullBeanLocal;

    @Schedule(hour = "*", minute = "*", second = "30")
    void  pull() {
        hsPixPullBeanLocal.pullDb();
    }
}
