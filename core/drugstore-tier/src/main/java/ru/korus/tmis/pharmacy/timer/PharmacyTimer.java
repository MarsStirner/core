package ru.korus.tmis.pharmacy.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.pharmacy.PharmacyBeanLocal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br> EUpatov
 * Date:        28.11.13, 18:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:   Пуллинг по расписанию для полинга 1С Аптеки<br>
 */
@Startup
@Singleton
public class PharmacyTimer {

    @EJB
    private PharmacyBeanLocal pharmacyBean;

    @Schedule(hour = "*", minute = "*", second = "25", persistent = false)
    public void pullDB() {
        pharmacyBean.pooling();
    }
}