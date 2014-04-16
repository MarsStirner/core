package ru.korus.tmis.core.database.finance;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;

import javax.ejb.Local;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 15:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbEventPaymentLocal {

    /**
     * Сохраняетф в БД информацию о платеже
     *
     * @param event - ID обращения
     * @param date - дата платежа
     * @param servicePaidInfo - информация об оплаченной услуги
     */
    void savePaidInfo(Event event, Date date, Action action, ServicePaidInfo servicePaidInfo);
}
