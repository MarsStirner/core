package ru.korus.tmis.ws.transfusion.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.PropType;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 16.01.2013, 11:45:31 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

public class TrfuActionProp {

    private final Database database;

    private final Map<PropType, Integer> propIds;

    public TrfuActionProp(final Database databaseBean, final String actionTypeFlatCode, final List<PropType> propConstants) throws CoreException {
        database = databaseBean;
        final List<ActionType> actionType =
                database.getEntityMgr().createQuery("SELECT at FROM ActionType at WHERE at.flatCode = :flatCode AND at.deleted = 0", ActionType.class)
                        .setParameter("flatCode", actionTypeFlatCode).getResultList();
        if (actionType.size() != 1) {
            throw new CoreException(String.format("The Action 'Transfusion Therapy' has been not found. flatCode '%s'", actionTypeFlatCode));
        }

        final List<ActionPropertyType> actionPropTypes = database.getEntityMgr()
                .createQuery("SELECT atp FROM ActionPropertyType atp WHERE atp.actionType.id = :typeId AND atp.deleted = 0", ActionPropertyType.class)
                .setParameter("typeId", actionType.get(0).getId()).getResultList();

        final StringBuffer msgError = new StringBuffer();
        propIds = new HashMap<PropType, Integer>();
        for (final PropType propType : propConstants) {
            boolean isFound = false;
            for (final ActionPropertyType curActionPropType : actionPropTypes) {
                if (propType.getCode().equals(curActionPropType.getCode())) {
                    propIds.put(propType, curActionPropType.getId());
                    isFound = true;
                }
            }
            if (!isFound) {
                msgError.append("'" + propType.getCode() + "'; ");
            }
        }

        if (propIds.size() != propConstants.size()) {
            throw new CoreException("The property has been not found for action with flatCode'" + actionTypeFlatCode + "' : " + msgError);
        }
    }

    public <T> T getProp(final Integer actionId, final PropType propType) throws CoreException {
        try {
            return database.getSingleProp(propType.getValueClass(), actionId, propIds.get(propType));
        } catch (final CoreException ex) {
            final String value = String.format("Не задано: '%s'", propType.getName());
            database.addSinglePropBasic(value, PropType.ORDER_REQUEST_ID.getValueClass(), actionId, propIds.get(PropType.ORDER_REQUEST_ID), true);
            throw ex;
        }
    }

    /**
     * @param orderRequestId
     * @return
     */
    public Integer getPropertyId(final PropType propType) {
        return propIds.get(propType);
    }

    public <T> T getProp(final Integer actionId, final PropType propType, final T defaultValue) {
        return database.getSingleProp(propType.getValueClass(), actionId, propIds.get(propType), defaultValue);
    }

    public <T> void setProp(final T value, final Integer actionId, final PropType propType, final boolean update) throws CoreException {
        if (value != null) {
            database.addSinglePropBasic(value, propType.getValueClass(), actionId, propIds.get(propType), update);
        }
    }

    public void setRequestState(final Integer actionId, final String state) throws CoreException {
        setProp(state, actionId, PropType.ORDER_REQUEST_ID, true);
    }

    public void orderResult2DB(final Action action, final Integer requestId) throws CoreException {
        setRequestState(action.getId(), "Получен идентификатор в системе ТРФУ: " + requestId);
        action.setStatus(Database.ACTION_STATE_WAIT);
    }

    public void setErrorState(final Action action, final String errMsg) throws CoreException {
        setRequestState(action.getId(), errMsg);
        throw new CoreException(errMsg);
    }
}
