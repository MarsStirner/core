package ru.korus.tmis.pharmacy;

import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.event.CreateActionNotification;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.drugstore.event.ExternalEventFacadeBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class PharmacyManagerImpl implements PharmacyManager {

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

    @EJB
    private ExternalEventFacadeBeanLocal externalEventFacade;

    /**
     * Произошло событие, создан Action
     */
    @Override
    public void eventActionCreated(int actionId) {
        try {
            Action action = dbAction.getActionById(actionId);
            Map<ActionProperty, List<APValue>> values = dbActionProperty.getActionPropertiesByActionId(action.getId());
            externalEventFacade.triggerCreateActionNotification(new CreateActionNotification(action, values));
        } catch (CoreException e) {
            e.printStackTrace();
        }

    }
}
