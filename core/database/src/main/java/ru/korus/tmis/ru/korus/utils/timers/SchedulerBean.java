package ru.korus.tmis.ru.korus.utils.timers;

import ru.korus.tmis.core.database.DbEnumBean;
import ru.korus.tmis.core.database.DbEnumBeanLocal;
import ru.korus.tmis.core.database.DbSettingsBeanLocal;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 12:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Singleton
public class SchedulerBean implements  SchedulerBeanLocal {

    @EJB
    DbEnumBeanLocal dbEnumBean;

    @EJB
    DbSettingsBeanLocal dbSettingsBean;

    @Override
    @Schedule(second = "0", minute = "0", hour = "4")
    public void nightlyUpdate() {
            dbSettingsBean.init();
            dbEnumBean.init();
    }
}
