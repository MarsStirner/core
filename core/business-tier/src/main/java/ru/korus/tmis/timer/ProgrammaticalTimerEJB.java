package ru.korus.tmis.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        28.11.13, 18:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  Пробный программируемый таймер<br>
 */
//@Startup
//@Singleton
public class ProgrammaticalTimerEJB {
    private static final Logger logger = LoggerFactory.getLogger(ProgrammaticalTimerEJB.class);

//    @Resource
//    private TimerService timerService;

    public ProgrammaticalTimerEJB() {
    }

//    @PostConstruct
    public void createProgrammaticalTimer() {
//        logger.info("ProgrammaticalTimerEJB initialized");
//        ScheduleExpression everyTenSeconds = new ScheduleExpression()
//                .second("*/10").minute("*").hour("*");
//        timerService.createCalendarTimer(everyTenSeconds, new TimerConfig(
//                "passed message " + new Date(), false));
    }

    @Timeout
    public void handleTimer(final Timer timer) {
//        logger.info("timer received - contained message is: " + timer.getInfo());
    }

}
