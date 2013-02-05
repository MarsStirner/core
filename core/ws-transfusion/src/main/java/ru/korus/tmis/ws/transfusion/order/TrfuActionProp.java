package ru.korus.tmis.ws.transfusion.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;

import ru.korus.tmis.core.entity.model.APValueDate;
import ru.korus.tmis.core.entity.model.APValueDouble;
import ru.korus.tmis.core.entity.model.APValueInteger;
import ru.korus.tmis.core.entity.model.APValueString;
import ru.korus.tmis.core.entity.model.APValueTime;
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
        DIAGNOSIS("Основной клинический диагноз", APValueString.class),
        BLOOD_COMP_TYPE("Требуемый компонент крови", APValueInteger.class),
        TYPE("Вид трансфузии", APValueString.class),
        VOLUME("Объем требуемого компонента крови (все, кроме тромбоцитов)", APValueInteger.class),
        DOSE_COUNT("Количество требуемых донорских доз (тромбоциты)",  APValueDouble.class),
        INDICATION("Показания к проведению трансфузии", APValueString.class),
        ORDER_REQUEST_ID("Результат передачи требования в систему ТРФУ", APValueString.class),
        ORDER_ISSUE_RES_DATE("Дата выдачи КК",APValueDate.class),
        ORDER_ISSUE_RES_TIME("Время выдачи КК",APValueTime.class),
        ORDER_ISSUE_RES_BLOOD_ID("Идентификатор компонента крови",APValueInteger.class),
        ORDER_ISSUE_RES_BLOOD_NUMBER("Паспортные данные компонента крови, №",APValueString.class),
        ORDER_ISSUE_RES_BLOOD_TYPE_ID("Выданный компонент крови", APValueString.class),
        ORDER_ISSUE_RES_DONOR_BLOOD_GROUP("Группа крови донора", APValueString.class),
        ORDER_ISSUE_RES_DONOR_BLOOD_RHESUS("Резус-фактор донора", APValueString.class),
        ORDER_ISSUE_RES_DONOR_VALUE("Объем выданного компонента крови", APValueInteger.class),
        ORDER_ISSUE_RES_DONOR_COUNT("Количество выданных донорских доз", APValueDouble.class),
        ORDER_ISSUE_RES_DONOR_CODE("Код донора", APValueString.class),
        ORDER_ISSUE_RES_COMMENT("Комментарий ТРФУ", APValueString.class);

        @SuppressWarnings("rawtypes")
        private final Class valueClass;

        final String name;
        
        PropType(final String name, @SuppressWarnings("rawtypes") Class valueClass) {
            this.name = name;
            this.valueClass = valueClass;
        }

        String getName() {
            return name;
        }
        @SuppressWarnings("rawtypes")
        Class getValueClass() {
            return valueClass;
        }

        
    }

    private static TrfuActionProp instance;
    
    private final int typeId;

    private final Map<PropType, Integer> propIds;

    public static synchronized TrfuActionProp getInstance(EntityManager em) throws CoreException {
      //  if (instance == null) {
            instance = new TrfuActionProp(em);
    //    }
        return instance;
    }
    
    private TrfuActionProp(EntityManager em) throws CoreException {
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
                msgError.append("'" + propType.getName() + "'; ");
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
    
    public <T> T getProp(EntityManager em, Integer actionId, PropType propType) throws CoreException {
        try {
            return Database.getSingleProp(propType.getValueClass(), em, actionId, propIds.get(propType));
        } catch (CoreException ex) {
            final String value = String.format("Не задано: '%s'", propType.getName());
            Database.addSinglePropBasic(value, propType.getValueClass(), em, actionId, propIds.get(PropType.ORDER_REQUEST_ID), true);
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

    public <T> T getProp(EntityManager em, Integer actionId, PropType propType, T defaultValue) {
        return Database.getSingleProp(propType.getValueClass(), em, actionId, propIds.get(propType), defaultValue);
    }
    
    public <T> void setProp(T value, EntityManager em, Integer actionId, PropType propType, boolean update) throws CoreException {
        Database.addSinglePropBasic(value, propType.getValueClass(), em, actionId, propIds.get(propType), update);
    }
}
