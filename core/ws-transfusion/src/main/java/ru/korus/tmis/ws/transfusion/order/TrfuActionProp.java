package ru.korus.tmis.ws.transfusion.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.Database;

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

    private static final String TRANSFUSION_ACTION_FLAT_CODE = "TransfusionTherapy";

    /**
     * Наименования типов свойства действия "Гемотрансфузионная терапия"
     */
    public enum PropType {
        DIAGNOSIS("Основной клинический диагноз"),
        BLOOD_COMP_TYPE("Требуемый компонент крови"),
        TYPE("Вид трансфузии"),
        VOLUME("Объем требуемого компонента крови (все, кроме тромбоцитов)"),
        DOSE_COUNT("Количество требуемых донорских доз (тромбоциты)"),
        INDICATION("Показания к проведению трансфузии"),
        ORDER_REQUEST_ID("Результат передачи требования в систему ТРФУ");

        private final String name;

        PropType(final String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    private final int typeId;

    private final Map<PropType, Integer> propIds;

    TrfuActionProp(EntityManager em) throws CoreException {
        final List<ActionType> actionType = em.createQuery("SELECT at FROM ActionType at WHERE at.flatCode = :flatCode AND at.deleted = 0", ActionType.class)
                .setParameter("flatCode", TRANSFUSION_ACTION_FLAT_CODE).getResultList();
        if (actionType.size() != 1) {
            throw new CoreException(String.format("The Action 'Transfusion Therapy' has been not found. flatCode '%s'", TRANSFUSION_ACTION_FLAT_CODE));
        }

        typeId = actionType.get(0).getId();

        final List<ActionPropertyType> actionPropTypes = em
                .createQuery("SELECT atp FROM ActionPropertyType atp WHERE atp.actionType.id = :typeId AND atp.deleted = 0", ActionPropertyType.class)
                .setParameter("typeId", typeId).getResultList();

        StringBuffer msgError = new StringBuffer();
        propIds = new HashMap<PropType, Integer>();
        final PropType[] propConstants = PropType.TYPE.getDeclaringClass().getEnumConstants();
        for (PropType propType : propConstants) {
            boolean isFound = false;
            for (ActionPropertyType curActionPropType : actionPropTypes) {
                if (propType.getName().equals(curActionPropType.getName())) {
                    propIds.put(propType, curActionPropType.getId());
                    isFound = true;
                }
            }
            if (!isFound) {
                msgError.append(" " + propType.getName());
            }
        }

        if (propIds.size() != propConstants.length) {
            throw new CoreException("The property has been not found: " + msgError);
        }
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }
    
    public String getPropString(EntityManager em, Integer actionId, PropType propType) throws CoreException {
        try {
            return Database.getSinglePropString(em, actionId, propIds.get(propType));
        } catch (CoreException ex) {
            Database.addSinglePropBasic(em, actionId, propIds.get(PropType.ORDER_REQUEST_ID), String.format("Не задано: '%s'", propType.getName()), true);
            throw ex;
        } 
    }

    /**
     * @param orderRequestId
     * @return
     */
    public Integer getPropertyId(PropType propType) {
        return  propIds.get(propType);
    }

    /**
     * @param em
     * @param id
     * @param volume
     * @param i
     * @return
     */
    public Integer getPropInteger(EntityManager em, Integer actionId, PropType propType, Integer defaultValue) {
        return Database.getSinglePropInteger(em, actionId, propIds.get(propType), defaultValue);

    }
    
    public Double getPropDouble(EntityManager em, Integer actionId, PropType propType, Double defaultValue) {
        return Database.getSinglePropDouble(em, actionId, propIds.get(propType), defaultValue);

    }
}
