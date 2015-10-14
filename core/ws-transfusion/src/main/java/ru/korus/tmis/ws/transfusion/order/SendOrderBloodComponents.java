package ru.korus.tmis.ws.transfusion.order;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.PropType;
import ru.korus.tmis.ws.transfusion.SenderUtils;
import ru.korus.tmis.ws.transfusion.TrfuActionProp;
import ru.korus.tmis.ws.transfusion.efive.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 11:45:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Передача новых требований на выдачу КК в ТРФУ
 */
@Stateless
public class SendOrderBloodComponents {

    static final String TRANSFUSION_ACTION_FLAT_CODE = "TransfusionTherapy";

    /**
     * Размер кода группы крови ("1+", "1-", и т.д.)
     */
    private static final int BLOOD_CODE_LENGHT = 2;

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
     * Код для миллилитров в табл. rbUnit
     */
    public static final String UNIT_MILILITER = "мл";

    @EJB
    private Database database;

    private SenderUtils senderUtils = new SenderUtils();

    public static final PropType[] propConstants = {
            PropType.DIAGNOSIS, // "Основной клинический диагноз"
            PropType.BLOOD_COMP_TYPE, // "Требуемый компонент крови"
            PropType.TYPE, // "Вид трансфузии"
            PropType.VOLUME, // "Объем требуемого компонента крови (все, кроме тромбоцитов)"
            PropType.ROOT_CAUSE, // "Показания к проведению трансфузии"
            PropType.ORDER_REQUEST_ID, // "Результат передачи требования в систему ТРФУ"
            PropType.ORDER_ISSUE_RES_DATE, // "Дата выдачи КК"
            PropType.ORDER_ISSUE_RES_TIME, // "Время выдачи КК"
            PropType.ORDER_ISSUE_BLOOD_COMP_PASPORT, // "Паспортные данные выданных компонентов крови"
            PropType.REQ_BLOOD_TYPE
    };

    /**
     * ID типа действия запроса КК
     */
    private static final String ACTION_TYPE_TRANSFUSION_ORDER = "TransfusionTherapy";

    /**
     * Тип трансфузии - Плановая
     */
    public static final String TRANSFUSION_TYPE_PLANED = "Плановая";

    /**
     * Тип трансфузии - Экстренная
     */
    public static final String TRANSFUSION_TYPE_EMERGENCY = "Экстренная";

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    private TrfuActionProp trfuActionProp;

    /**
     * Информация о пациенте для предачи требования КК в ТРФУ
     *
     * @param action         - действие, соответсвующее новому требованию КК
     * @param trfuActionProp
     * @return - информацию о пациенте для передачи в ТРФУ
     * @throws CoreException                  - при ошибке во время работы с БД
     * @throws DatatypeConfigurationException - если невозможно преобразовать дату рождения пациента в XMLGregorianCalendar (@see {@link Database#toGregorianCalendar(Date)})
     */
    public static PatientCredentials getPatientCredentials(final Action action, final TrfuActionProp trfuActionProp, EntityManager em) throws CoreException,
            DatatypeConfigurationException {
        if (!checkMovingForPatient(action, em)) {
            trfuActionProp.setRequestState(action.getId(), "Ошибка: Пациент снят с койки");
            return null;
        }
        final PatientCredentials res = new PatientCredentials();
        final Event event = EntityMgr.getSafe(action.getEvent());
        final Patient client = EntityMgr.getSafe(event.getPatient());
        res.setId(client.getId());
        res.setLastName(client.getLastName());
        res.setFirstName(client.getFirstName());
        res.setMiddleName(client.getPatrName());
        res.setBirth(Database.toGregorianCalendar(EntityMgr.getSafe(client.getBirthDate())));
        final RbBloodType clientBloodType = client.getBloodType();
        if (clientBloodType == null || clientBloodType == RbBloodType.getEmptyBloodType()) {
            trfuActionProp.setRequestState(action.getId(), "Ошибка: Не установлена группа крови пациента");
            return null;
        }
        final BloodType bloodType = convertBloodId(clientBloodType.getCode());
        res.setBloodGroupId(bloodType.bloodGroupId);
        res.setRhesusFactorId(bloodType.rhesusFactorId);
        res.setBloodKell(client.getBloodKell().equals(BloodKell.NOT_DEFINED) ? null : client.getBloodKell().equals(BloodKell.POSITIVE));
        if ( client.getRbBloodPhenotype() != null ) {
            for (String type : new String[] {"D","C","E","c","e"})  {
                BloodPhenotype bloodPhenotype = new BloodPhenotype();
                bloodPhenotype.setPhenotype(type);
                bloodPhenotype.setValue(client.getRbBloodPhenotype().getCode().contains(type + "+"));
                res.getBloodPhenotype().add(bloodPhenotype);
            }
        }
        return res;
    }

    private static boolean checkMovingForPatient(Action action, EntityManager em) {
        final List<Action> movings = TrfuActionProp.getMovings(action, em);
        return !movings.isEmpty();
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void pullDB(final TransfusionMedicalService trfuService) {
        try {
            trfuActionProp = new TrfuActionProp(database, ACTION_TYPE_TRANSFUSION_ORDER, Arrays.asList(propConstants));
        } catch (final CoreException ex) {
            logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());
            return;
        }
        logger.info("Periodic check new TRFU order...");
        updateBloodCompTable(trfuService);
        //TODO старые заявки нельзя отправлять т.к. они кривые
        final LocalDate after = new LocalDate(2015, 8, 26);
        final List<Action> orderActions = database.getNewActionByFlatCodeAfterDate(ACTION_TYPE_TRANSFUSION_ORDER, after.toDate());
        logger.info("Periodic check new TRFU order... the count of new action: {}", orderActions.size());

        for (final Action action : orderActions) {
            logger.info("Processing transfusion action {}...", action.getId());
            try {
                OrderResult orderResult = new OrderResult();
                trfuActionProp.setRequestState(action.getId(), "");
                final PatientCredentials patientCredentials = getPatientCredentials(action, trfuActionProp, database.getEntityMgr());
                if (patientCredentials != null) {
                    final OrderInformation orderInfo = getOrderInformation(action);
                    logger.info("Processing transfusion action {}... Order Information: {}", action.getId(), orderInfo);
                    try {
                        orderResult = trfuService.orderBloodComponents(patientCredentials, orderInfo);
                        logger.info("Processing transfusion action {}... The response was recived from TRFU: {}", action.getId(), orderResult);
                    } catch (final Exception ex) {
                        logger.error(
                                "The order {} was not registrate in TRFU. TRFU web method 'orderBloodComponents'"
                                        + " has thrown runtime exception.  Error description: '{}'",
                                action.getId(), ex.getMessage());
                        ex.printStackTrace();
                    }
                    if (orderResult.isResult()) { // если подситема ТРФУ зарегистрировала требование КК
                        try {
                            trfuActionProp.orderResult2DB(action, orderResult.getRequestId());
                            logger.info("Processing transfusion action {}... The order has been successfully registered in TRFU. TRFU id: {}", action.getId(),
                                    orderResult.getRequestId());
                        } catch (final CoreException ex) {
                            logger.error("The order {} was not registrate in TRFU. Cannot save the result into DB. Error description: '{}'", action.getId(),
                                    ex.getMessage());
                        }
                    } else {
                        logger.error("The order {} was not registrate in TRFU. TRFU service return the error satatus. Error description: '{}'", action.getId(),
                                orderResult.getDescription());
                        trfuActionProp.setRequestState(action.getId(), "Ответ системы ТРФУ: " + orderResult.getDescription());
                    }
                }
            } catch (final DatatypeConfigurationException e) {
                logger.error("The order {} was not registrate in TRFU. Cannot create the date information. Error description: '{}'", action.getId(),
                        e.getMessage());
            } catch (final CoreException e) {
                logger.error("The order {} was not registrate in TRFU. Internal core error. Error description: '{}'", action.getId(),
                        e.getMessage());
                e.printStackTrace();
            } catch (final Exception ex) {
                logger.error("General exception in trfu integration (in SendOrderBloodComponents):", ex);
            }
        }
    }

    /**
     * Создание требования на выдачу КК
     *
     * @param action - действие, соответствующее требованию КК
     * @return - параметры, заданные врачом для передаваемого требования на выдачу КК
     * @throws CoreException                  - при ошибке во время работы с БД
     * @throws DatatypeConfigurationException - если невозможно преобразовать дату рождения пациента в XMLGregorianCalendar (@see {@link Database#toGregorianCalendar(Date)})
     */
    private OrderInformation getOrderInformation(final Action action) throws CoreException,
            DatatypeConfigurationException {
        final EntityManager em = database.getEntityMgr();
        final OrderInformation res = new OrderInformation();
        res.setNumber("");
        res.setId(action.getId());

        final Staff assigner = senderUtils.getAssigner(action, trfuActionProp);
        final Staff createPerson = EntityMgr.getSafe(assigner);
        res.setDivisionId(trfuActionProp.getOrgStructure(action));
        final Event event = EntityMgr.getSafe(action.getEvent());
        res.setIbNumber(senderUtils.getIbNumbre(action, event, trfuActionProp));
        res.setDiagnosis((String) trfuActionProp.getProp(action.getId(), PropType.DIAGNOSIS));
        final RbTrfuBloodComponentType compType = trfuActionProp.getProp(action.getId(), PropType.BLOOD_COMP_TYPE);
        final Integer compTypeId = compType != null ? compType.getId() : null;
        res.setComponentTypeId(convertComponentType(em, action.getId(), compTypeId));
        res.setVolume(trfuActionProp.getProp(action.getId(), PropType.VOLUME, 0));
        res.setIndication(convertFromXml((String) trfuActionProp.getProp(action.getId(), PropType.ROOT_CAUSE)));
        res.setDoseCount(0.0); //данный параметер не используется
        res.setTransfusionType(convertTrfuType((String) trfuActionProp.getProp(action.getId(), PropType.TYPE)));
        final Date plannedEndDate = senderUtils.getPlannedData(action, trfuActionProp);
        res.setPlanDate(Database.toGregorianCalendar(plannedEndDate));
        res.setRegistrationDate(Database.toGregorianCalendar(new Date()));
        res.setAttendingPhysicianId(createPerson.getId());
        res.setAttendingPhysicianFirstName(createPerson.getFirstName());
        res.setAttendingPhysicianLastName(createPerson.getLastName());
        res.setAttendingPhysicianMiddleName(createPerson.getPatrName());
        try {
            String bloodTypeReq = trfuActionProp.getProp(action.getId(), PropType.REQ_BLOOD_TYPE);
            if (bloodTypeReq != null && !bloodTypeReq.isEmpty()) {
                initReqBloodType(res, bloodTypeReq);
            }
        } catch (CoreException ex)
        {

        }
        return res;
    }

    private void initReqBloodType(OrderInformation res, String bloodTypeReq) {
        List<String> groups = Arrays.asList("0(I)Rh-","0(I)Rh+","A(II)Rh-","A(II)Rh+","B(III)Rh-","B(III)Rh+","AB(IV)Rh-","AB(IV)Rh+" );
        if(groups.contains(bloodTypeReq)) {
            res.setBloodGroupId(groups.indexOf(bloodTypeReq)/2 + 1);
            res.setRhesusFactorId(bloodTypeReq.endsWith("-")? 1 : 0);
        } else {
            res.setBloodGroupId(-1);
            res.setRhesusFactorId(-1);
        }
    }

    private Integer convertComponentType(final EntityManager em, final Integer actionId, final Integer compTypeId) throws CoreException {
        final List<RbTrfuBloodComponentType> compTypes = em
                .createQuery("SELECT c FROM RbTrfuBloodComponentType c WHERE c.id = :compTypeId AND c.unused = 0", RbTrfuBloodComponentType.class)
                .setParameter("compTypeId", compTypeId).getResultList();
        if (compTypes.size() != 1) {
            trfuActionProp.setRequestState(actionId, String.format("Недопустимое значение идентификатора компонента крови:'%d'", compTypeId));
            throw new CoreException(String.format("The blood component id=%d has been not found or unused", compTypeId));
        }
        return compTypes.get(0).getTrfuId();
    }

    private void updateBloodCompTable(final TransfusionMedicalService trfuService) {
        final List<ComponentType> compTypesTrfu = trfuService.getComponentTypes();
        final List<RbTrfuBloodComponentType> compBloodTypesDb =
                database.getEntityMgr().createQuery("SELECT c FROM RbTrfuBloodComponentType c", RbTrfuBloodComponentType.class).getResultList();
        logger.info("The Reference book for blood components has been received from TRFU. The count of blood component: {}", compTypesTrfu.size());
        final List<RbTrfuBloodComponentType> compBloodTypesTrfu = convertToDb(compTypesTrfu);
        for (final RbTrfuBloodComponentType compDb : compBloodTypesDb) {
            if (compBloodTypesTrfu.remove(compDb)) {
                compDb.setUnused(false);
            } else {
                compDb.setUnused(true);
                logger.info("The blood components unused in TRFU. The component: {}", compDb);
            }
        }
        for (final RbTrfuBloodComponentType compTrfu : compBloodTypesTrfu) {
            database.getEntityMgr().persist(compTrfu);
        }
        database.getEntityMgr().flush();
    }

    private List<RbTrfuBloodComponentType> convertToDb(final List<ComponentType> compBloodTypesTrfu) {
        final List<RbTrfuBloodComponentType> res = new LinkedList<RbTrfuBloodComponentType>();
        for (final ComponentType compTrfu : compBloodTypesTrfu) {
            final RbTrfuBloodComponentType compDb = new RbTrfuBloodComponentType();
            compDb.setTrfuId(compTrfu.getId());
            compDb.setCode(compTrfu.getCode());
            compDb.setName(compTrfu.getValue());
            compDb.setUnused(false);
            res.add(compDb);
        }
        return res;
    }

    private Integer convertTrfuType(final String trfuType) throws CoreException {
        final String type = convertFromXml(trfuType);
        if (TRANSFUSION_TYPE_PLANED.equals(type)) {
            return 0;
        } else if (TRANSFUSION_TYPE_EMERGENCY.equals(type)) {
            return 1;
        }

        throw new CoreException(String.format("Wrong transfusion type: '%s'", type));
    }

    private String convertFromXml(final String value) {
        String res = value.substring(value.indexOf('>') + 1);
        if ("".equals(res)) {
            return value;
        }
        final int endIndex = res.indexOf('<');
        if (endIndex > 0) {
            res = res.substring(0, endIndex);
        }

        return res;

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
            res.bloodGroupId = -1;
            res.rhesusFactorId = -1;
        }

        if (res.bloodGroupId < BLOOD_GROUP_MIN || res.bloodGroupId > BLOOD_GROUP_MAX) {
            res.bloodGroupId = -1;
            res.rhesusFactorId = -1;
        }

        if (code.charAt(1) == RHESUS_FACTOR_POS) {
            res.rhesusFactorId = 0;
        } else if (code.charAt(1) == RHESUS_FACTOR_NEGATIVE) {
            res.rhesusFactorId = 1;
        } else {
            res.rhesusFactorId = -1;
        }

        return res;
    }

}
