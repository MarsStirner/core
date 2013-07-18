package ru.korus.tmis.ws.transfusion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 16.01.2013, 11:45:31 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

/**
 * Класс для работы со свойствами действия
 */
public class TrfuActionProp {

    private final Database database;

    /**
     * Таблица свойств действия. Key - тип свойства действия, Value - ActionPropertyType.Id
     */
    private final Map<PropType, Integer> propIds;

    /**
     * Создание класса для работы со свойствами действия
     * 
     * @param databaseBean
     *            - доступ к БД
     * @param actionTypeFlatCode
     *            - код типа действия для
     * @param propConstants
     *            - список свойств дейсвия
     * @throws CoreException
     *             - при ошибке во время работы с БД
     */
    public TrfuActionProp(final Database databaseBean, final String actionTypeFlatCode, final List<PropType> propConstants) throws CoreException {
        database = databaseBean;
        final List<ActionType> actionType =
                database.getEntityMgr().createQuery("SELECT at FROM ActionType at WHERE at.flatCode = :flatCode AND at.deleted = 0", ActionType.class)
                        .setParameter("flatCode", actionTypeFlatCode).getResultList();
        if (actionType.size() != 1) {
            throw new CoreException(String.format(
                    "The Action 'Transfusion Therapy' has been not found or more that one has been found. flatCode '%s', count of action type: '%d'",
                    actionTypeFlatCode, actionType.size()));
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

    public static List<Action> getMovings(Action action, EntityManager em) {
        final List<ActionType> typeMovings = em
                .createQuery("SELECT at FROM ActionType at WHERE at.deleted = 0 AND at.flatCode = 'moving'", ActionType.class).getResultList();
        if (typeMovings.isEmpty()) {
            return new Vector<Action>();
        }
        final List<Action> movings = em
                .createQuery("SELECT a FROM Action a WHERE a.deleted = 0 AND a.actionType.deleted = 0 AND a.actionType.flatCode = 'moving'" +
                        " AND a.status != 2 AND a.event.patient.id = :patientId", Action.class)
                .setParameter("patientId", action.getEvent().getPatient().getId()).getResultList();
        return movings;
    }
    public Integer getOrgStructure(Action action) {
        List<Action> movings = getMovings(action, database.getEntityMgr());
        for (Action moving : movings) {
            Integer res = getOrgStructureForAction(moving);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    private Integer getOrgStructureForAction(Action moving) {
        final String propTypeCode  = "hospOrgStruct";
        final EntityManager em = database.getEntityMgr();
        final List<ActionPropertyType> actionPropTypes = em
                .createQuery("SELECT atp FROM ActionPropertyType atp WHERE atp.actionType.id = :typeId AND atp.deleted = 0 AND atp.code = :propTypeCode", ActionPropertyType.class)
                .setParameter("typeId", moving.getActionType().getId())
                .setParameter("propTypeCode", propTypeCode).getResultList();
        for(ActionPropertyType propType: actionPropTypes)  {
            List<ActionProperty> actionProps = database.getActionProp(moving.getId(), propType.getId());
            for(ActionProperty prop : actionProps) {
                final List<APValueOrgStructure> propRes =
                    em.createQuery("SELECT p FROM APValueOrgStructure p WHERE p.id = :id", APValueOrgStructure.class)
                            .setParameter("id", new IndexedId(prop.getId(), 0)).getResultList();
                if (!propRes.isEmpty()) {
                    if (propRes.get(0).getValue() != null) {
                        return propRes.get(0).getValue().getId();
                    }
                }
            }
        }
        return  null;
    }

}