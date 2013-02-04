package ru.korus.tmis.ws.transfusion.order;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.RbBloodType;
import ru.korus.tmis.core.entity.model.Rbbloodcomponenttype;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.Database;
import ru.korus.tmis.ws.transfusion.efive.ComponentType;
import ru.korus.tmis.ws.transfusion.efive.OrderInformation;
import ru.korus.tmis.ws.transfusion.efive.OrderResult;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 11:45:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Передача новых требований на выдачу КК в ТРФУ
 */
public class SendOrderBloodComponents {

    private static final int SIZE_OF_BLOOD_CODE = 2;
    /**
     * ID типа действия запроса КК
     */
    private static final Integer ACTION_TYPE_ID_TRANSFUSION_ORDER = 760;

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
     * Тип трансфузии - Плановая
     */
    private static final String TRANSFUSION_TYPE_PLANED = "<span style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Плановая</span>";

    /**
     * Тип трансфузии - Экстренная
     */
    private static final String TRANSFUSION_TYPE_EMERGENCY = "<span style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Экстренная</span>";

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    private static TrfuActionProp trfuActionProp;

    /**
     * Поиск новых требований КК и передача их в ТРФУ
     * 
     * @throws CoreException
     * @throws CoreException
     * @throws DatatypeConfigurationException
     */
   public void pullDB(TransfusionMedicalService trfuService) {
        EntityManager em = null;
        try {
            logger.info("Periodic check new TRFU order...");
            try {
                em = EntityMgr.getEntityManagerForS11r64(logger);

                if (trfuActionProp == null) {
                    trfuActionProp = new TrfuActionProp(em);
                }
            } catch (CoreException ex) {
                logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());
                return;
            }

            List<Action> orderActions = null;
            try {
                orderActions = getOrders(em);
                logger.info("Periodic check new TRFU order... the count of new action: {}", orderActions.size());
            } catch (CoreException ex) {
                logger.error("Cannot get new TRFU orders. Error description: '{}'", ex.getMessage());
                return;
            }

            for (Action action : orderActions) {
                logger.info("Processing transfusion action {}...", action.getId());
                try {
                    OrderResult orderResult = new OrderResult();
                    setRequestState(em, action.getId(), "");
                    PatientCredentials patientCredentials = getPatientCredentials(em, action);
                    logger.info("Processing transfusion action {}... Patient Credentials: {}", action.getId(), patientCredentials);
                    OrderInformation orderInfo = getOrderInformation(em, action, trfuService);
                    logger.info("Processing transfusion action {}... Order Information: {}", action.getId(), orderInfo);
                    try {
                        orderResult = trfuService.orderBloodComponents(patientCredentials, orderInfo);
                        logger.info("Processing transfusion action {}... The response was recived from TRFU: {}", action.getId(), orderResult);
                    } catch (Exception ex) {
                        logger.error(
                                "The order {} was not registrate in TRFU. TRFU web method 'orderBloodComponents' has thrown runtime exception.  Error description: '{}'",
                                action.getId(), ex.getMessage());
                        ex.printStackTrace();
                    }
                    if (orderResult.isResult()) { // если подситема ТРФУ зарегистрировала требование КК
                        try {
                            orderResult2DB(em, action, orderResult.getRequestId());
                            logger.info("Processing transfusion action {}... The order has been successfully registered in TRFU. TRFU id: {}", action.getId(),
                                    orderResult.getRequestId());
                        } catch (CoreException ex) {
                            logger.error("The order {} was not registrate in TRFU. Cannot save the result into DB. Error description: '{}'", action.getId(),
                                    ex.getMessage());
                        }
                    } else {
                        logger.error("The order {} was not registrate in TRFU. TRFU service return the error satatus. Error description: '{}'", action.getId(),
                                orderResult.getDescription());
                    }
                } catch (DatatypeConfigurationException e) {
                    logger.error("The order {} was not registrate in TRFU. Cannot create the date information. Error description: '{}'", action.getId(),
                            e.getMessage());
                } catch (CoreException e) {
                    logger.error("The order {} was not registrate in TRFU. Cannot create the date information. Error description: '{}'", action.getId(),
                            e.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            if (em != null) {
                EntityMgr.closeEntityManagerFactory();
            }
        }
    }

    /**
     * @param em
     * @param id
     * @param requestId
     * @throws CoreException
     */
    private void orderResult2DB(EntityManager em, Action action, Integer requestId) throws CoreException {
        setRequestState(em, action.getId(), "Получен идентификатор в системе ТРФУ: " + requestId);
        action.setStatus((short) 1);
    }

    /**
     * @param em
     * @param action
     * @param state
     * @throws CoreException
     */
    private void setRequestState(EntityManager em, Integer actionId, final String state) throws CoreException {
        Database.addSinglePropBasic(em, actionId, trfuActionProp.getPropertyId(TrfuActionProp.PropType.ORDER_REQUEST_ID),
                state, true);
    }

    /**
     * @param em
     * @param action
     * @param trfuService
     * @return
     * @throws CoreException
     * @throws DatatypeConfigurationException
     */
    private OrderInformation getOrderInformation(EntityManager em, Action action, TransfusionMedicalService trfuService) throws CoreException,
            DatatypeConfigurationException {
        OrderInformation res = new OrderInformation();
        res.setNumber(""); // TODO Remove!
        res.setId(action.getId());
        Integer orgStructIt = new Integer(0);
        final Staff createPerson = EntityMgr.getSafe(action.getAssigner());
        final OrgStructure orgStructure = createPerson.getOrgStructure();
        if (orgStructure != null) {
            orgStructIt = orgStructure.getId();
        }
        res.setDivisionId(orgStructIt);
        final Event event = EntityMgr.getSafe(action.getEvent());
        res.setIbNumber(event.getExternalId());
        res.setDiagnosis(trfuActionProp.getPropString(em, action.getId(), TrfuActionProp.PropType.DIAGNOSIS));
        final String compType = trfuActionProp.getPropString(em, action.getId(), TrfuActionProp.PropType.BLOOD_COMP_TYPE);
        res.setComponentTypeId(convertComponentType(em, action.getId(), compType, trfuService));
        res.setVolume(trfuActionProp.getPropInteger(em, action.getId(), TrfuActionProp.PropType.VOLUME, 0));
        res.setDoseCount(trfuActionProp.getPropDouble(em, action.getId(), TrfuActionProp.PropType.DOSE_COUNT, 0.0));
        res.setIndication(trfuActionProp.getPropString(em, action.getId(), TrfuActionProp.PropType.INDICATION));
        res.setTransfusionType(convertTrfuType(trfuActionProp.getPropString(em, action.getId(), TrfuActionProp.PropType.TYPE)));
        res.setPlanDate(getGregorianCalendar(action.getPlannedEndDate()));
        res.setRegistrationDate(getGregorianCalendar(new Date()));
        res.setAttendingPhysicianId(createPerson.getId());
        res.setAttendingPhysicianFirstName(createPerson.getFirstName());
        res.setAttendingPhysicianLastName(createPerson.getLastName());
        res.setAttendingPhysicianMiddleName(createPerson.getPatrName());
        return res;
    }

    /**
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     */
    private XMLGregorianCalendar getGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        final GregorianCalendar planedDateCalendar = new GregorianCalendar();
        planedDateCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(planedDateCalendar);
    }

    private Integer convertComponentType(EntityManager em, Integer actionId, String compTypeName, TransfusionMedicalService trfuService) throws CoreException {
        Map<String, Integer> compTypes = updateBloodCompTable(em, trfuService);
        Integer res = compTypes.get(compTypeName);
        if (res == null) {
            setRequestState(em, actionId, String.format("Недопустимое значение компонента крови:'%s'",compTypeName));
            throw new CoreException(String.format("The blood component '%s' has been not found", compTypeName));
        }
        return res;
    }

    private Map<String, Integer> updateBloodCompTable(EntityManager em, TransfusionMedicalService trfuService) {
        List<ComponentType> compBloodTypesTRFU = trfuService.getComponentTypes();
        List<Rbbloodcomponenttype> compBloodTypesDB = em.createQuery("SELECT c FROM Rbbloodcomponenttype c", Rbbloodcomponenttype.class).getResultList();
        logger.info("The Reference book for components blood has been received from TRFU. The count of blood component: {}", compBloodTypesDB.size());
        Map<Integer, String> compBloodMapTRFU = new HashMap<Integer, String>();
        Map<String, Integer> res = new HashMap<String, Integer>();
        for (ComponentType compBloodType : compBloodTypesTRFU) {
            compBloodMapTRFU.put(compBloodType.getId(), compBloodType.getCode() + compBloodType.getValue());
            res.put(compBloodType.getValue(), compBloodType.getId());

        }
        Map<Integer, String> compBloodMapDB = new HashMap<Integer, String>();
        for (Rbbloodcomponenttype comp : compBloodTypesDB) {
            compBloodMapDB.put(comp.getId(), comp.getCode() + comp.getName());
        }

        if (!compBloodMapDB.equals(compBloodMapTRFU)) {
            saveNewCompTable(em, compBloodTypesTRFU);
        }

        return res;

    }

    private void saveNewCompTable(final EntityManager em, final List<ComponentType> compBloodTypesTRFU) {
        logger.info("Update the Reference book for components blood...");
        int deletedCount = em.createQuery("DELETE FROM Rbbloodcomponenttype c", Rbbloodcomponenttype.class).executeUpdate();
        logger.info("The Reference book for components blood has been cleared. The count of deleted records form RbBloodComponentType: {}", deletedCount);
        for (ComponentType compBloodType : compBloodTypesTRFU) {
            final Rbbloodcomponenttype curComp = new Rbbloodcomponenttype();
            curComp.setId(compBloodType.getId());
            curComp.setCode(compBloodType.getCode());
            curComp.setName(compBloodType.getValue());
            em.persist(curComp);
            logger.info("Added the blood component: {}", compBloodType.toString() );
        }
    }

    private Integer convertTrfuType(String trfuType) throws CoreException {
        if (TRANSFUSION_TYPE_PLANED.equals(trfuType)) {
            return 0;
        } else if (TRANSFUSION_TYPE_EMERGENCY.equals(trfuType)) {
            return 1;
        }

        throw new CoreException(String.format("Wrong transfusion type: '%s'", trfuType));
    }

    private class BloodType {
        int rhesusFactorId;
        int bloodGroupId;
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
    private PatientCredentials getPatientCredentials(EntityManager em, Action action) throws CoreException, DatatypeConfigurationException {
        final PatientCredentials res = new PatientCredentials();
        final Event event = EntityMgr.getSafe(action.getEvent());
        final Patient client = EntityMgr.getSafe(event.getPatient());
        res.setId(client.getId());
        res.setLastName(client.getLastName());
        res.setFirstName(client.getFirstName());
        res.setMiddleName(client.getPatrName());
        res.setBirth(getGregorianCalendar(EntityMgr.getSafe(client.getBirthDate())));
        final RbBloodType clientBloodType = EntityMgr.getSafe(client.getBloodType());
        BloodType bloodType = convertBloodId(clientBloodType.getCode());
        res.setBloodGroupId(bloodType.bloodGroupId);
        res.setRhesusFactorId(bloodType.rhesusFactorId);
        return res;
    }

    /**
     * Преобразование кода группы крови из формата БД МИС ("1+", "1-" ... "4+", "4-") в формат протоколоа обмена с ТРФУ (группа: 1- первая 0 (I), 2 – вторая А
     * (II), 3 – третья В (III), 4 – четвертая АВ (IV); резус-фактора: 0 – Положительный, 1 -– Отрицательный)
     * 
     * @param code
     * @return
     * @throws CoreException
     */
    private BloodType convertBloodId(String code) throws CoreException {
        final String errorMsg = String.format("Incorrect blood group code: '%s'", code);
        if (code == null || code.length() != SIZE_OF_BLOOD_CODE) {
            throw new CoreException(errorMsg);
        }

        BloodType res = new BloodType();

        try {
            res.bloodGroupId = Integer.parseInt(code.substring(0, 1));
        } catch (NumberFormatException ex) {
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
     * Поиск новых требований КК
     * 
     * @param em
     *            - достуб к БД
     * @return - список действий с типом, соответсвующим требованию КК и статусом 0 - Начато
     * @throws CoreException
     */
    private List<Action> getOrders(EntityManager em) throws CoreException {
        final List<Action> actions = em.createQuery("SELECT a FROM Action a WHERE a.status = 0 AND a.actionType.id = :type AND a.deleted = 0", Action.class)
                .setParameter("type", ACTION_TYPE_ID_TRANSFUSION_ORDER).getResultList();
        return actions;
    }
}
