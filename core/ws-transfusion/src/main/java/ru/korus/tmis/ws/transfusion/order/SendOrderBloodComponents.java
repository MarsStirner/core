package ru.korus.tmis.ws.transfusion.order;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbTrfuBloodComponentType;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.Database;
import ru.korus.tmis.ws.transfusion.PropType;
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
@Stateless
public class SendOrderBloodComponents {

    static final String TRANSFUSION_ACTION_FLAT_CODE = "TransfusionTherapy";

    @EJB
    private Database database;

    public static final PropType[] propConstants = {
            PropType.DIAGNOSIS, // "Основной клинический диагноз"
            PropType.BLOOD_COMP_TYPE, // "Требуемый компонент крови"
            PropType.TYPE, // "Вид трансфузии"
            PropType.VOLUME, // "Объем требуемого компонента крови (все, кроме тромбоцитов)"
            PropType.DOSE_COUNT, // "Количество требуемых донорских доз (тромбоциты)"
            PropType.ROOT_CAUSE, // "Показания к проведению трансфузии"
            PropType.ORDER_REQUEST_ID, // "Результат передачи требования в систему ТРФУ"
            PropType.ORDER_ISSUE_RES_DATE, // "Дата выдачи КК"
            PropType.ORDER_ISSUE_RES_TIME, // "Время выдачи КК"
            PropType.ORDER_ISSUE_BLOOD_COMP_PASPORT, // "Паспортные данные выданных компонентов крови"
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

    /*
     * (non-Javadoc)
     * 
     * @see ru.korus.tmis.ws.transfusion.order.Pullable#pullDB(ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService)
     */

    public void pullDB(final TransfusionMedicalService trfuService) {
        try {
            trfuActionProp = new TrfuActionProp(database, ACTION_TYPE_TRANSFUSION_ORDER, Arrays.asList(propConstants));
        } catch (final CoreException ex) {
            logger.error("Cannot create entety manager. Error description: '{}'", ex.getMessage());
            return;
        }
        logger.info("Periodic check new TRFU order...");
        updateBloodCompTable(trfuService);
        List<Action> orderActions = database.getNewActionByFlatCode(ACTION_TYPE_TRANSFUSION_ORDER);
        logger.info("Periodic check new TRFU order... the count of new action: {}", orderActions.size());

        for (final Action action : orderActions) {
            logger.info("Processing transfusion action {}...", action.getId());
            try {
                OrderResult orderResult = new OrderResult();
                trfuActionProp.setRequestState(action.getId(), "");
                final PatientCredentials patientCredentials = Database.getPatientCredentials(action);
                logger.info("Processing transfusion action {}... Patient Credentials: {}", action.getId(), patientCredentials);
                final OrderInformation orderInfo = getOrderInformation(database.getEntityMgr(), action, trfuService);
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
            } catch (final DatatypeConfigurationException e) {
                logger.error("The order {} was not registrate in TRFU. Cannot create the date information. Error description: '{}'", action.getId(),
                        e.getMessage());
            } catch (final CoreException e) {
                logger.error("The order {} was not registrate in TRFU. Internal core error. Error description: '{}'", action.getId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * @param em
     * @param action
     * @param trfuService
     * @return
     * @throws CoreException
     * @throws DatatypeConfigurationException
     */
    private OrderInformation
            getOrderInformation(final EntityManager em, final Action action, final TransfusionMedicalService trfuService) throws CoreException,
                    DatatypeConfigurationException {
        final OrderInformation res = new OrderInformation();
        res.setNumber("");
        res.setId(action.getId());
        Integer orgStructItd = new Integer(0);
        final Staff createPerson = EntityMgr.getSafe(action.getAssigner());
        final OrgStructure orgStructure = createPerson.getOrgStructure();
        if (orgStructure != null) {
            orgStructItd = orgStructure.getId();
        } else {
            logger.error("Wrong orgStriucture information for person {}, action id {}", createPerson.getId(), action.getId());
        }
        res.setDivisionId(orgStructItd);
        final Event event = EntityMgr.getSafe(action.getEvent());
        res.setIbNumber(event.getExternalId());
        res.setDiagnosis((String) trfuActionProp.getProp(action.getId(), PropType.DIAGNOSIS));
        final RbTrfuBloodComponentType compType = trfuActionProp.getProp(action.getId(), PropType.BLOOD_COMP_TYPE);
        final Integer compTypeId = compType != null ? compType.getId() : null;
        res.setComponentTypeId(convertComponentType(em, action.getId(), compTypeId));
        res.setVolume(trfuActionProp.getProp(action.getId(), PropType.VOLUME, 0));
        res.setDoseCount(trfuActionProp.getProp(action.getId(), PropType.DOSE_COUNT, 0.0));
        res.setIndication((String) trfuActionProp.getProp(action.getId(), PropType.ROOT_CAUSE));
        res.setTransfusionType(convertTrfuType((String) trfuActionProp.getProp(action.getId(), PropType.TYPE)));
        final Date plannedEndDate = action.getPlannedEndDate();
        if (plannedEndDate != null) {
            res.setPlanDate(Database.getGregorianCalendar(plannedEndDate));
        }
        res.setRegistrationDate(Database.getGregorianCalendar(new Date()));
        res.setAttendingPhysicianId(createPerson.getId());
        res.setAttendingPhysicianFirstName(createPerson.getFirstName());
        res.setAttendingPhysicianLastName(createPerson.getLastName());
        res.setAttendingPhysicianMiddleName(createPerson.getPatrName());
        return res;
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

    /**
     * @param compBloodTypesTrfu
     * @return
     */
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

    /**
     * @param trfuType
     * @return
     */
    private String convertFromXml(final String trfuType) {
        String res = trfuType.substring(trfuType.indexOf('>') + 1);
        final int endIndex = res.indexOf('<');
        if (endIndex > 0) {
            res = res.substring(0, endIndex);
        }

        return res;

    }

}
