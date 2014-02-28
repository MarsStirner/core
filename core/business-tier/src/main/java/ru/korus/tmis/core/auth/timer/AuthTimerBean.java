package ru.korus.tmis.core.auth.timer;

import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.DbEnumBeanLocal;
import ru.korus.tmis.core.database.DbSettingsBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 12:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class AuthTimerBean implements AuthTimerBeanLocal {

    @Resource
    TimerService timerService;

    @EJB
    AuthStorageBeanLocal authStorageBeanLocal;


    @PostConstruct
    void init(){
        // Таймер для удаления токенов с истекшим сроком действия
        timerService.createIntervalTimer(
                ConfigManager.TmisAuth().AuthTokenPeriod(),
                ConfigManager.TmisAuth().AuthTokenPeriod(),
                null);
    }

    @Timeout
    @Lock(LockType.WRITE)
    @Override
    public void timeoutHandler() {
        authStorageBeanLocal.timeoutHandler();
    }

}
