package ru.korus.tmis.core.database.dbutil;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.AbstractAPValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.IndexedId;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.EntityMgr;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.01.2013, 10:41:20 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Stateless
public class Database {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    DbStaffBeanLocal dbStaffBeanLocal;

    /**
     * Статус действия: Начато {Action.status}
     */
    public static final short ACTION_STATE_STARTED = 0;

    /**
     * Статус действия: Ожидание {Action.status}
     */
    public static final short ACTION_STATE_WAIT = 1;

    /**
     * Статус действия: Закончено {Action.status}
     */
    public static final short ACTION_STATE_FINISHED = 2;

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    /**
     * Получить значение свойства действия {ActionProperty_<Type>.value}
     * 
     * @param classType
     *            - энтити таблицы значений свойств (например APIntegerValue)
     * @param actionId
     *            - ID действия {Action.Id}
     * @param propTypeId
     *            - тип свойства действия {ActionPropertyType.Id}
     * @return значение свойства дейсвия типа <code>propTypeId</code> для действия <code>actionId</code>
     * @throws CoreException
     *             - если свойсво не найдено или найдено более чем одно значение
     */
    @SuppressWarnings("unchecked")
    public <T> T getSingleProp(@SuppressWarnings("rawtypes") final Class classType,
            final int actionId, final int propTypeId) throws CoreException {

        final List<ActionProperty> prop = getActionProp(actionId, propTypeId);

        checkCountProp(actionId, propTypeId, prop.size());

        final String className = classType.getName();
        final List<Object> propRes =
                em.createQuery("SELECT p FROM " + className.substring(className.lastIndexOf('.') + 1) + " p WHERE p.id.id = :id", classType)
                        .setParameter("id", prop.iterator().next().getId()).getResultList();

        checkCountProp(actionId, propTypeId, propRes.size());

        return (T) ((APValue) propRes.iterator().next()).getValue();
    }

    public Database()
    {
        super();
    }

    public EntityManager getEntityMgr() {
        return em;
    }

    /**
     * Получить из БД значение свойства действия {ActionProperty_<Type>.value} или значение по умолчанию
     * 
     * @param classType
     *            - энтити таблицы значений свойств (например APIntegerValue)
     * @param actionId
     *            - ID действия {Action.Id}
     * @param propTypeId
     *            - тип свойства действия {ActionPropertyType.Id}
     * @param defaultVal
     *            - значение по умолчанию
     * @return - значение свойства дейсвия типа <code>propTypeId</code> для действия <code>actionId</code>, <br>
     *         - либо значение по умолчанию <code>defaultVal</code>, если свойсво не найдено или найдено более чем одно значение
     */
    public <T> T getSingleProp(@SuppressWarnings("rawtypes") final Class classType,
            final int actionId,
            final int propTypeId,
            final T defaultVal) {
        try {
            return getSingleProp(classType, actionId, propTypeId);
        } catch (final CoreException e) {
            return defaultVal;
        }
    }

    /**
     * Проверить количества найденных свойств для действия
     * 
     * @param actionId
     * @param propTypeId
     * @param size
     * @throws CoreException
     */
    private void checkCountProp(final int actionId, final int propTypeId, final int size) throws CoreException {
        if (size == 0) {
            throw new CoreException(0, String.format("The property %d for action %d has been not found", propTypeId, actionId));
        } else if (size > 1) {
            throw new CoreException(1, String.format("More that one property %d for action %d has been found", propTypeId, actionId));
        }
    }

    /**
     * Добавить свойство
     * 
     * @param value
     *            - устанавлемое значение свойства
     * @param classType
     *            - энтити таблицы значений свойств (например APIntegerValue)
     * @param actionId
     *            - ID действия {Action.Id}
     * @param propTypeId
     *            - тип свойства действия {ActionPropertyType.Id}
     * @param isUpdate
     *            - флаг обновления значения для ранее заданных свойств
     * @return - ID установленного свойства
     * @throws CoreException
     *             если згачение свойства уже задано и <code>isUpdate</code> равен <code>false</code>
     * @throws Error
     *             если невозможно создать экземпляр класса энтити таблицы classType (@see Class#newInstance())
     */
    public <T> int addSinglePropBasic(final T value,
            @SuppressWarnings("rawtypes") final Class classType,
            final int actionId,
            final int propTypeId,
            final boolean isUpdate) throws CoreException {
        if (value == null) {
            throw new IllegalArgumentException("The param 'final T value' is null");
        }
        Integer newPropId;
        final List<ActionProperty> prop = getActionProp(actionId, propTypeId);
        if (prop.size() > 0) {
            if (isUpdate) {
                newPropId = prop.iterator().next().getId();
            } else {
                throw new CoreException(String.format("The property %i for action %i has been alredy set", propTypeId, actionId)); // свойство уже установленно
            }
        } else {
            newPropId = addActionProp(actionId, propTypeId);
        }

        final IndexedId actionPropId = new IndexedId();
        actionPropId.setId(newPropId);
        actionPropId.setIndex(0);
        AbstractAPValue actionProp = null;

        final String msg =
                String.format("Internal error: The type %s is not supproted by Database.addSinglePropBasic", value == null ? "null" : value.getClass()
                        .getName());
        try {
            actionProp = getPropValue(newPropId, (AbstractAPValue) classType.newInstance());
        } catch (final InstantiationException e) {
            logger.error(msg);
            e.printStackTrace();
            throw new CoreException(msg);
        } catch (final IllegalAccessException e) {
            logger.error(msg);
            e.printStackTrace();
        }

        actionProp.setValue(value);
        actionProp.setId(actionPropId);
        em.persist(actionProp);
        em.flush();
        return newPropId;

    }

    /**
     * Поиск новых действий
     * 
     * @flatCode - код типа действия
     * @return - список действий с типом, соответсвующим flatCode и статусом 0 - Начато
     */
    public List<Action> getNewActionByFlatCode(final String flatCode) {
        final List<Action> actions =
                em.createNamedQuery("Action.findNewByFlatCode", Action.class)
                        .setParameter("flatCode", flatCode).getResultList();
        return actions;
    }

    /**
     * Преобразовать Date в XMLGregorianCalendar
     * 
     * @param date
     * @return XMLGregorianCalendar соответсвующий <code>date</code>
     * @throws DatatypeConfigurationException
     *             если не возможно создать экземпляр XMLGregorianCalendar (@see {@link DatatypeFactory#newInstance()})
     */
    public static XMLGregorianCalendar toGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        if (date == null) {
            throw new DatatypeConfigurationException();
        }
        final GregorianCalendar planedDateCalendar = new GregorianCalendar();
        planedDateCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(planedDateCalendar);
    }

    /**
     * Получить список свойств для заданного действия {ActionProperty}
     * 
     * @param actionId
     *            - ID действия {Action.Id}
     * @param propTypeId
     *            - тип свойства действия {ActionPropertyType.Id}
     * @return список энтити {ActionProperty} соответвующий свойствам типа <code>propTypeId</code> для действия <code>actionId</code>
     */
    public List<ActionProperty> getActionProp(final int actionId, final int propTypeId) {
        final List<ActionProperty> prop =
                em.createQuery("SELECT p FROM ActionProperty p WHERE p.action.id = :curAction_id AND p.deleted = 0 AND p.actionPropertyType.id = :propTypeId",
                        ActionProperty.class).setParameter("curAction_id", actionId).setParameter("propTypeId", propTypeId).getResultList();
        return prop;
    }

    /**
     * Получить значение свойства по ID или значение по умолчанию
     * 
     * @param newPropId
     * @param defaultValue
     * @return
     */
    private <T> T getPropValue(final Integer newPropId, final T defaultValue) {
        final String template = "SELECT p FROM %s p WHERE p.id.id = :curProp_id";
        final String className = defaultValue.getClass().getName();
        @SuppressWarnings("unchecked")
        final List<T> curProp =
                (List<T>) em.createQuery(String.format(template, className.substring(className.lastIndexOf('.') + 1)), defaultValue.getClass())
                        .setParameter("curProp_id", newPropId).getResultList();
        return curProp.size() > 0 ? curProp.iterator().next() : defaultValue;
    }

    /**
     * @see {@link Database#addSinglePropBasic}
     */
    private Integer addActionProp(final int actionId, final int propTypeId) throws CoreException {
        final Action action = EntityMgr.getSafe(em.find(Action.class, new Integer(actionId)));
        final ActionPropertyType actionPropType = EntityMgr.getSafe(em.find(ActionPropertyType.class, new Integer(propTypeId)));
        final ActionProperty actionProp = new ActionProperty();
        final Date now = new Date();
        actionProp.setCreateDatetime(now);
        actionProp.setCreatePerson(action.getCreatePerson());
        actionProp.setModifyDatetime(now);
        actionProp.setModifyPerson(action.getCreatePerson());
        actionProp.setType(actionPropType);
        final RbUnit unit = actionPropType.getUnit();
        actionProp.setUnit(unit == RbUnit.getEmptyUnit() ? null : unit);
        actionProp.setNorm(actionPropType.getName());
        actionProp.setNorm(null);
        actionProp.setVersion(0);
        actionProp.setAction(action);
        em.persist(actionProp);
        em.flush();
//        logger.info("The new property {} for action {} has been added. Property id: {}", propTypeId, actionId, actionProp.getId());
        return actionProp.getId();
    }

    public Staff getCoreUser() {
        return dbStaffBeanLocal.getCoreUser();
    }
}
