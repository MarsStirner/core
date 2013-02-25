package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

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
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService_Service;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;
import ru.korus.tmis.ws.transfusion.order.TrfuActionProp;
import ru.korus.tmis.ws.transfusion.order.TrfuPullable;
import ru.korus.tmis.ws.transfusion.procedure.SendProcedureRequest;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.01.2013, 10:41:20 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Singleton
public class Database {

    public static final short ACTION_STATE_STARTED = 0;
    public static final short ACTION_STATE_WAIT = 1;
    public static final short ACTION_STATE_FINISHED = 2;
    public static final short SIZE_OF_BLOOD_CANSELED = 3;
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

    private static final int SIZE_OF_BLOOD_CODE = 2;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    @Schedule(hour = "*", minute = "*")
    public void pullDB() {
        try {
            final TransfusionMedicalService_Service service = new TransfusionMedicalService_Service();
            final TransfusionMedicalService transfusionMedicalService = service.getTransfusionMedicalService();
            final TrfuPullable[] pulls = {
                    new SendOrderBloodComponents(), new SendProcedureRequest() };
            for (final TrfuPullable pull : pulls) {
                pull.pullDB(transfusionMedicalService);
            }
        } catch (final WebServiceException ex) {
            logger.error("The TRFU service is not available. Exception description: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSingleProp(@SuppressWarnings("rawtypes") final Class classType,
            final EntityManager em, final int actionId, final int propTypeId) throws CoreException {

        final List<ActionProperty> prop = getActionProp(em, actionId, propTypeId);

        checkCountProp(actionId, propTypeId, prop.size());

        final String className = classType.getName();
        final List<Object> propRes =
                em.createQuery("SELECT p FROM " + className.substring(className.lastIndexOf('.') + 1) + " p WHERE p.id = :id", classType)
                        .setParameter("id", new IndexedId(prop.get(0).getId(), 0)).getResultList();

        checkCountProp(actionId, propTypeId, propRes.size());

        return (T) ((APValue) propRes.get(0)).getValue();
    }

    public static <T> T getSingleProp(@SuppressWarnings("rawtypes") final Class classType,
            final EntityManager em,
            final int actionId,
            final int propTypeId,
            final T defaultVal) {
        try {
            return getSingleProp(classType, em, actionId, propTypeId);
        } catch (final CoreException e) {
            return defaultVal;
        }
    }

    /**
     * @param em
     * @param actionId
     * @param propTypeId
     * @return
     */
    private static List<ActionProperty> getActionProp(final EntityManager em, final int actionId, final int propTypeId) {
        final List<ActionProperty> prop =
                em.createQuery("SELECT p FROM ActionProperty p WHERE p.action.id = :curAction_id AND p.deleted = 0 AND p.actionPropertyType.id = :propTypeId",
                        ActionProperty.class).setParameter("curAction_id", actionId).setParameter("propTypeId", propTypeId).getResultList();
        return prop;
    }

    /**
     * @param actionId
     * @param propTypeId
     * @param size
     * @throws CoreException
     */
    private static void checkCountProp(final int actionId, final int propTypeId, final int size) throws CoreException {
        if (size == 0) {
            throw new CoreException(String.format("The property %d for action %d has been not found", propTypeId, actionId));
        } else if (size > 1) {
            throw new CoreException(String.format("More that one property %d for action %d has been found", propTypeId, actionId));
        }
    }

    public static <T> int addSinglePropBasic(final T value,
            @SuppressWarnings("rawtypes") final Class classType,
            final EntityManager em,
            final int actionId,
            final int propTypeId,
            final boolean update) throws CoreException {
        Integer newPropId = null;
        final List<ActionProperty> prop = getActionProp(em, actionId, propTypeId);
        if (prop.size() > 0) {
            if (update) {
                newPropId = prop.get(0).getId();
            } else {
                new CoreException(String.format("The property %i for action %i has been alredy set", propTypeId, actionId)); // свойство уже установленно
            }
        } else {
            newPropId = addActionProp(em, actionId, propTypeId);
        }

        final IndexedId actionPropId = new IndexedId();
        actionPropId.setId(newPropId);
        actionPropId.setIndex(0);
        AbstractAPValue actionProp = null;

        final String msg = String.format("Internal error: The type %s is supproted by Database.addSinglePropBasic", value.getClass().getName());
        try {
            actionProp = setPropValue(em, newPropId, (AbstractAPValue) classType.newInstance());
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
     * @param em
     * @param newPropId
     * @return
     */
    private static <T> T setPropValue(final EntityManager em, final Integer newPropId, final T propInteger) {
        final String template = "SELECT p FROM %s p WHERE p.id.id = :curProp_id";
        final String className = propInteger.getClass().getName();
        @SuppressWarnings("unchecked")
        final List<T> curProp =
                (List<T>) em.createQuery(String.format(template, className.substring(className.lastIndexOf('.') + 1)), propInteger.getClass())
                        .setParameter("curProp_id", newPropId).getResultList();
        return curProp.size() > 0 ? curProp.get(0) : propInteger;
    }

    /**
     * @param em
     * @param actionId
     * @param propTypeId
     * @return
     * @throws CoreException
     */
    private static Integer addActionProp(final EntityManager em, final int actionId, final int propTypeId) throws CoreException {
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

    /**
     * @return
     */
    public static List<DivisionInfo> getDivisions() {
        final List<DivisionInfo> res = new LinkedList<DivisionInfo>();
        try {
            final EntityManager em = EntityMgr.getEntityManagerForS11r64(logger);
            final List<OrgStructure> structs = em.createQuery("SELECT s FROM OrgStructure s WHERE s.deleted = 0", OrgStructure.class).getResultList();
            for (final OrgStructure struct : structs) {
                final DivisionInfo info = new DivisionInfo();
                info.setId(struct.getId());
                info.setName(struct.getName());
                res.add(info);
            }
        } catch (final CoreException ex) {
            logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());
        }

        return res;
    }

    /**
     * Поиск новых требований КК
     * 
     * @param em
     *            - достуб к БД
     * @return - список действий с типом, соответсвующим требованию КК и статусом 0 - Начато
     * @throws CoreException
     */
    public static List<Action> getNewActionByFlatCode(final EntityManager em, final String flatCode) throws CoreException {
        final List<Action> actions =
                em.createQuery("SELECT a FROM Action a WHERE a.status = 0 AND a.actionType.flatCode = :flatCode AND a.deleted = 0 ", Action.class)
                        .setParameter("flatCode", flatCode).getResultList();
        return actions;
    }

    /**
     * @param em
     * @param action
     * @param state
     * @throws CoreException
     */
    public static void
            setRequestState(final EntityManager em, final Integer actionId, final String state, final TrfuActionProp trfuActionProp) throws CoreException {
        trfuActionProp.setProp(state, em, actionId, PropType.ORDER_REQUEST_ID, true);
    }

    /**
     * Информация о пациенте для предачи требования КК в ТРФУ
     * 
     * @param em
     *            - достуб к БД
     * @param action
     *            - действие, соответсвующее новому требованию КК
     * @return - информацию о пациенте для передачи в ТРФУ
     * @throws CoreException
     *             - при отсутвии доступа к БД или при отсутвии необходимой информации в БД
     * @throws DatatypeConfigurationException
     */
    public static PatientCredentials getPatientCredentials(final EntityManager em, final Action action) throws CoreException, DatatypeConfigurationException {
        final PatientCredentials res = new PatientCredentials();
        final Event event = EntityMgr.getSafe(action.getEvent());
        final Patient client = EntityMgr.getSafe(event.getPatient());
        res.setId(client.getId());
        res.setLastName(client.getLastName());
        res.setFirstName(client.getFirstName());
        res.setMiddleName(client.getPatrName());
        res.setBirth(getGregorianCalendar(EntityMgr.getSafe(client.getBirthDate())));
        final RbBloodType clientBloodType = EntityMgr.getSafe(client.getBloodType());
        final BloodType bloodType = convertBloodId(clientBloodType.getCode());
        res.setBloodGroupId(bloodType.bloodGroupId);
        res.setRhesusFactorId(bloodType.rhesusFactorId);
        return res;
    }

    /**
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar getGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        final GregorianCalendar planedDateCalendar = new GregorianCalendar();
        planedDateCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(planedDateCalendar);
    }

    private static class BloodType {
        private int rhesusFactorId;
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
        if (code == null || code.length() != SIZE_OF_BLOOD_CODE) {
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
     * @param em
     * @param id
     * @param requestId
     * @throws CoreException
     */
    public static void
            orderResult2DB(final EntityManager em, final Action action, final Integer requestId, final TrfuActionProp trfuActionProp) throws CoreException {
        Database.setRequestState(em, action.getId(), "Получен идентификатор в системе ТРФУ: " + requestId, trfuActionProp);
        action.setStatus(ACTION_STATE_WAIT);
    }

    /**
     * @param requestId
     * @param em
     * @return
     */
    public static Action getAction(final EntityManager em, final Integer requestId) {
        final List<Action> actions =
                em.createQuery("SELECT a FROM Action a WHERE a.id = :requestId", Action.class).setParameter("requestId", requestId).getResultList();
        return actions.isEmpty() ? null : actions.get(0);
    }
}
