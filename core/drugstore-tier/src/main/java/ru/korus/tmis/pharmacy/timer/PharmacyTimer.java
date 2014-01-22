package ru.korus.tmis.pharmacy.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.pharmacy.PharmacyBeanLocal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        28.11.13, 18:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:   Программируемый таймер для полинга 1С Аптеки<br>
 */
@Startup
@Singleton
public class PharmacyTimer {
    private static final Logger logger = LoggerFactory.getLogger(PharmacyTimer.class);

    @EJB
    private PharmacyBeanLocal pharmacyBean;

    @Resource
    private TimerService timerService;

    @PostConstruct
    public void createProgrammaticalTimer() {
        logger.info("ProgrammaticalTimerEJB initialized");
        ScheduleExpression everyTenSeconds = new ScheduleExpression()
                .second("*/59").minute("*").hour("*");
        timerService.createCalendarTimer(everyTenSeconds, new TimerConfig(
                "passed message " + new Date(), false));
    }

    @Timeout
    public void handleTimer(final Timer timer) {
      //  logger.info("timer received - contained message is: " + timer.getInfo());
        pharmacyBean.pooling();
    }
}