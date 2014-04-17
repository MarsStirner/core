package ru.korus.tmis.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventsToODVD;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 9:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class FinancePullBean implements FinancePullBeanLocal, Sender {

    @EJB
    private TransmitterLocal transmitterLocal;

    private static final Logger logger = LoggerFactory.getLogger(FinancePullBean.class);

    @Override
    public void pullDb() {
        try {
            logger.info("1C ODVD integration entry...");
            if ( ConfigManager.Finance().isFinanceActive()) {
                logger.info("1C ODVD integration is active...");
                transmitterLocal.send(this, EventsToODVD.class, "EventsToODVD.ToSend");
            } else {
                logger.info("1C ODVD integration is disabled...");
            }
        } catch (Exception ex) {
            logger.error("1C ODVD integration internal error.", ex);
        }

    }

    @Override
    public void sendEntity(Object entity) {
        assert entity instanceof EventsToODVD;
        //TODO implements me!
    }
}
