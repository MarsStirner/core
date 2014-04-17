package ru.korus.tmis.ws.finance;

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
public class FinanceScheduleBean {

    @EJB
    FinancePullBeanLocal financePullBeanLocal;

    @Schedule(hour = "*", minute = "*", second = "45")
    void  pull() {
        financePullBeanLocal.pullDb();
    }
}
