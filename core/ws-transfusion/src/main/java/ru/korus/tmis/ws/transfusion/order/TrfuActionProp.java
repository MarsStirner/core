package ru.korus.tmis.ws.transfusion.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.Database;
import ru.korus.tmis.ws.transfusion.PropType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.01.2013, 11:39:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */

public class TrfuActionProp {

    private final Map<PropType, Integer> propIds;

    public TrfuActionProp(final EntityManager em, final String actionTypeFlatCode, final List<PropType> propConstants) throws CoreException {
        final List<ActionType> actionType = em.createQuery("SELECT at FROM ActionType at WHERE at.flatCode = :flatCode AND at.deleted = 0", ActionType.class)
                .setParameter("flatCode", actionTypeFlatCode).getResultList();
        if (actionType.size() != 1) {
            throw new CoreException(String.format("The Action 'Transfusion Therapy' has been not found. flatCode '%s'", actionTypeFlatCode));
        }

        final List<ActionPropertyType> actionPropTypes = em
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

    public <T> T getProp(final EntityManager em, final Integer actionId, final PropType propType) throws CoreException {
        try {
            return Database.getSingleProp(propType.getValueClass(), em, actionId, propIds.get(propType));
        } catch (final CoreException ex) {
            final String value = String.format("Не задано: '%s'", propType.getName());
            Database.addSinglePropBasic(value, propType.getValueClass(), em, actionId, propIds.get(PropType.ORDER_REQUEST_ID), true);
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

    public <T> T getProp(final EntityManager em, final Integer actionId, final PropType propType, final T defaultValue) {
        return Database.getSingleProp(propType.getValueClass(), em, actionId, propIds.get(propType), defaultValue);
    }

    public <T> void setProp(final T value, final EntityManager em, final Integer actionId, final PropType propType, final boolean update) throws CoreException {
        Database.addSinglePropBasic(value, propType.getValueClass(), em, actionId, propIds.get(propType), update);
    }
}
