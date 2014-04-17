package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.ws.finance.odvd.WsPoliclinicPortType;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 9:20 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface FinancePullBeanLocal {

    void setPort(WsPoliclinicPortType port);

    void pullDb();

    void sendClosedActions(Event event, List<Action> actionList);
}
