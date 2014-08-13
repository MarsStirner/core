package ru.korus.tmis.ru.korus.utils.timers;

import org.joda.time.DateTime;
import ru.korus.tmis.core.database.DbAutoSaveStorageLocal;
import ru.korus.tmis.core.database.DbEnumBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.notification.DbNotificationActionBeanLocal;

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

    @EJB
    DbActionBeanLocal dbAction;

    @EJB
    DbAutoSaveStorageLocal dbAutoSaveStorageLocal;

    @EJB
    DbNotificationActionBeanLocal dbNotificationActionBeanLocal;


    @Schedule(second = "0", minute = "0", hour = "4")
    public void nightlyUpdate() {
            dbSettingsBean.init();
            dbEnumBean.init();
    }

    @Schedule(second = "0", minute = "0", hour = "*",
            info = "Запуск задачи закрытия документов в закрытых историях болезни")
    public void closeAppealsDocs() {
        dbAction.closeAppealsDocs();
    }

    @Schedule(second = "0", minute = "0", hour = "0", dayOfWeek = "1",
            info = "Запуск задачи удаления устаревших (30 дней) записей в таблице автосохранения полей")
    public void removeOldAutoSaveEntries() {
        dbAutoSaveStorageLocal.clean(new DateTime().minusDays(30).toDate());
    }

    @Schedule(hour = "*", minute = "*", second = "33")
    public void removeOldAutoSaveEntries() {
         dbNotificationActionBeanLocal.pullDb();
    }
}
