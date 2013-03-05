package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.AbstractAPValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.IndexedId;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.RbBloodType;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;

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

    /**
     * Статус действия: Отменено {Action.status}
     */
    public static final short SIZE_OF_BLOOD_CANSELED = 3;

    /**
     * Статус действия: Без результата {Action.status}
     */
    public static final short SIZE_OF_BLOOD_NO_RESULT = 4;

    /**
     * Идентификатор группы крови, соответсвующий группе 0(I)
     */
    private static final int BLOOD_GROUP_MIN = 1;

    /**
     * Идентификатор группы крови, соответсвующий группе AB(IV)
     */
    private static final int BLOOD_GROUP_MAX = 4;

    /**
     * Символ, соответсвующий положительному резус-фактору
     */
    private static final char RHESUS_FACTOR_POS = '+';

    /**
     * Символ, соответсвующий отрицательному резус-фактору
     */
    private static final char RHESUS_FACTOR_NEGATIVE = '-';

    /**
     * Размер кода группы крови ("1+", "1-", и т.д.)
     */
    private static final int BLOOD_CODE_LENGHT = 2;

    /**
     * Код для миллилитров в табл. rbUnit
     */
    public static final String UNIT_MILILITER = "мл";

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
                em.createQuery("SELECT p FROM " + className.substring(className.lastIndexOf('.') + 1) + " p WHERE p.id = :id", classType)
                        .setParameter("id", new IndexedId(prop.get(0).getId(), 0)).getResultList();

        checkCountProp(actionId, propTypeId, propRes.size());

        return (T) ((APValue) propRes.get(0)).getValue();
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
        Integer newPropId = null;
        final List<ActionProperty> prop = getActionProp(actionId, propTypeId);
        if (prop.size() > 0) {
            if (isUpdate) {
                newPropId = prop.get(0).getId();
            } else {
                new CoreException(String.format("The property %i for action %i has been alredy set", propTypeId, actionId)); // свойство уже установленно
            }
        } else {
            newPropId = addActionProp(actionId, propTypeId);
        }

        final IndexedId actionPropId = new IndexedId();
        actionPropId.setId(newPropId);
        actionPropId.setIndex(0);
        AbstractAPValue actionProp = null;

        final String msg = String.format("Internal error: The type %s is supproted by Database.addSinglePropBasic", value.getClass().getName());
        try {
            actionProp = getPropValue(newPropId, (AbstractAPValue) classType.newInstance());
        } catch (final InstantiationException e) {
            logger.error(msg);
            e.printStackTrace();
            throw new Error(msg);
        } catch (final IllegalAccessException e) {
            logger.error(msg);
            e.printStackTrace();
            e.printStackTrace();
        }

        actionProp.setValue(value);
        actionProp.setId(actionPropId);
        em.persist(actionProp);
        return newPropId;

    }

    /**
     * Получит список подразделений {OrgStructure}
     * 
     * @return список подразделений
     */
    public List<DivisionInfo> getDivisions() {
        final List<DivisionInfo> res = new LinkedList<DivisionInfo>();
        final List<OrgStructure> structs = em.createQuery("SELECT s FROM OrgStructure s WHERE s.deleted = 0", OrgStructure.class).getResultList();
        for (final OrgStructure struct : structs) {
            final DivisionInfo info = new DivisionInfo();
            info.setId(struct.getId());
            info.setName(struct.getName());
            res.add(info);
        }

        return res;
    }

    /**
     * Поиск новых действий
     * 
     * @flatCode - код типа действия
     * @return - список действий с типом, соответсвующим flatCode и статусом 0 - Начато
     */
    public List<Action> getNewActionByFlatCode(final String flatCode) {
        final List<Action> actions =
                em.createQuery("SELECT a FROM Action a WHERE a.status = 0 AND a.actionType.flatCode = :flatCode AND a.deleted = 0 ", Action.class)
                        .setParameter("flatCode", flatCode).getResultList();
        return actions;
    }

    /**
     * Информация о пациенте для предачи требования КК в ТРФУ
     * 
     * @param action
     *            - действие, соответсвующее новому требованию КК
     * @return - информацию о пациенте для передачи в ТРФУ
     * @throws CoreException
     *             - при отсутвии доступа к БД или при отсутвии необходимой информации в БД
     * @throws DatatypeConfigurationException
     *             - если не возможно преобразовать дату рождения пациента в XMLGregorianCalendar (@see {@link Database#toGregorianCalendar(Date)})
     */
    public static PatientCredentials getPatientCredentials(final Action action) throws CoreException, DatatypeConfigurationException {
        final PatientCredentials res = new PatientCredentials();
        final Event event = EntityMgr.getSafe(action.getEvent());
        final Patient client = EntityMgr.getSafe(event.getPatient());
        res.setId(client.getId());
        res.setLastName(client.getLastName());
        res.setFirstName(client.getFirstName());
        res.setMiddleName(client.getPatrName());
        res.setBirth(toGregorianCalendar(EntityMgr.getSafe(client.getBirthDate())));
        final RbBloodType clientBloodType = EntityMgr.getSafe(client.getBloodType());
        final BloodType bloodType = convertBloodId(clientBloodType.getCode());
        res.setBloodGroupId(bloodType.bloodGroupId);
        res.setRhesusFactorId(bloodType.rhesusFactorId);
        return res;
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
        final GregorianCalendar planedDateCalendar = new GregorianCalendar();
        planedDateCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(planedDateCalendar);
    }

    private static class BloodType {
        /**
         * Резус-факиор
         */
        private int rhesusFactorId;

        /**
         * Группа крови
         */
        private int bloodGroupId;
    }

    /**
     * Преобразование кода группы крови из формата БД МИС ("1+", "1-" ... "4+", "4-") в формат протоколоа обмена с ТРФУ (группа: 1- первая 0 (I), 2 – вторая А
     * (II), 3 – третья В (III), 4 – четвертая АВ (IV); резус-фактора: 0 – Положительный, 1 -– Отрицательный)
     * 
     * @param code
     * @return
     * @throws CoreException
     */
    private static BloodType convertBloodId(final String code) throws CoreException {
        // TODO add check that blood type is set in CLient Info
        final String errorMsg = String.format("Incorrect blood group code: '%s'", code);
        if (code == null || code.length() != BLOOD_CODE_LENGHT) {
            throw new CoreException(errorMsg);
        }

        final BloodType res = new BloodType();

        try {
            res.bloodGroupId = Integer.parseInt(code.substring(0, 1));
        } catch (final NumberFormatException ex) {
            throw new CoreException(errorMsg);
        }

        if (res.bloodGroupId < BLOOD_GROUP_MIN || res.bloodGroupId > BLOOD_GROUP_MAX) {
            throw new CoreException(errorMsg);
        }

        if (code.charAt(1) == RHESUS_FACTOR_POS) {
            res.rhesusFactorId = 0;
        } else if (code.charAt(1) == RHESUS_FACTOR_NEGATIVE) {
            res.rhesusFactorId = 1;
        } else {
            throw new CoreException(errorMsg);
        }

        return res;
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
    private List<ActionProperty> getActionProp(final int actionId, final int propTypeId) {
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
        return curProp.size() > 0 ? curProp.get(0) : defaultValue;
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
        logger.info("The new property {} for action {} has been added. Property id: {}", propTypeId, actionId, actionProp.getId());
        return actionProp.getId();
    }

}
