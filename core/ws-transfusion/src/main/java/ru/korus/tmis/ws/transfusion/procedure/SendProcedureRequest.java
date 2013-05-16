package ru.korus.tmis.ws.transfusion.procedure;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.RbTrfuLaboratoryMeasureTypes;
import ru.korus.tmis.core.entity.model.RbTrfuProcedureTypes;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.ws.transfusion.PropType;
import ru.korus.tmis.ws.transfusion.SenderUtils;
import ru.korus.tmis.ws.transfusion.efive.DonorInfo;
import ru.korus.tmis.ws.transfusion.efive.LaboratoryMeasureType;
import ru.korus.tmis.ws.transfusion.efive.OrderResult;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.efive.ProcedureType;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;
import ru.korus.tmis.ws.transfusion.order.TrfuActionProp;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        18.02.2013, 14:24:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@Stateless
public class SendProcedureRequest {

    /**
     * 
     */
    private static final String AP_VALUE = "APValue";

    @EJB
    private Database database;

    private Staff coreUser;

    private SenderUtils senderUtils = new SenderUtils();

    /**
     * 
     */
    private static final String TRFU_PROCEDURE_TRFU_ID_BASE = "trfuProcedure_trfu_id_";
    private static final Logger logger = LoggerFactory.getLogger(SendProcedureRequest.class);

    public static final PropType[] propTypes = {
            PropType.ORDER_REQUEST_ID,
            PropType.DONOR_ID,
            PropType.ORDER_ISSUE_RES_DATE,
            PropType.ORDER_ISSUE_RES_TIME,
            PropType.CONTRAINDICATION,
            PropType.BEFORE_HEMODYNAMICS_PULSE,
            PropType.AFTER_HEMODYNAMICS_PULSE,
            PropType.BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE,
            PropType.AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE,
            PropType.BEFORE_HEMODYNAMICS_TEMPERATURE,
            PropType.AFTER_HEMODYNAMICS_TEMPERATURE,
            PropType.COMPLICATIONS,
            PropType.INITIAL_VOLUME,
            PropType.CHANGE_VOLUME,
            PropType.INITIAL_TBV,
            PropType.CHANGE_TBV,
            PropType.INITIAL_SPEED,
            PropType.CHANGE_SPEED,
            PropType.INITIAL_INLETACRATIO,
            PropType.CHANGE_INLETACRATIO,
            PropType.INITIAL_TIME,
            PropType.CHANGE_TIME,
            PropType.INITIAL_PRODUCT_VOLUME,
            PropType.CHANGE_PRODUCT_VOLUME,
            PropType.ACD_LOAD,
            PropType.NACL_LOAD,
            PropType.CA_LOAD,
            PropType.OTHER_LOAD,
            PropType.TOTAL_LOAD,
            PropType.PACK_REMOVE,
            PropType.OTHER_REMOVE,
            PropType.TOTAL_REMOVE,
            PropType.BALANCE,
            PropType.MAKER,
            PropType.NUMBER,
            PropType.BLOOD_GROUP_ID,
            PropType.RHESUS_FACTOR_ID,
            PropType.VOLUME_PROC_RES,
            PropType.PRODUCTION_DATE,
            PropType.EXPIRATION_DATE,
            PropType.HT,
            PropType.SALINE_VOLUME,
            PropType.FINAL_HT,
            PropType.LAB_MEASURE,
            PropType.FINAL_VOLUME,
    };

    /*
     * (non-Javadoc)
     * 
     * @see ru.korus.tmis.ws.transfusion.order.Pullable#pullDB(ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService)
     */

    public void pullDB(final TransfusionMedicalService trfuService) {
        try {
            initCoreUser();
            updateProcedureType(trfuService);
            updateLaboratoryMeasure(trfuService);
            sendNewProcedure(trfuService);
        } catch (final CoreException ex) {
            logger.error("Cannot create entety manager in SendProcedureRequest. Error description: '{}'", ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * 
     */
    private void initCoreUser() {
        coreUser = database.getCoreUser();
    }

    /**
     * Отправка запроса на проведелиен лечебной процедуры ТРФУ
     * 
     * @param em
     * @param trfuService
     * @throws CoreException
     * @throws DatatypeConfigurationException
     */
    private void sendNewProcedure(final TransfusionMedicalService trfuService) throws CoreException {
        final List<Action> actions = getNewActions(database.getEntityMgr());
        final Map<String, TrfuActionProp> actionProp = new HashMap<String, TrfuActionProp>();
        for (final Action action : actions) {
            try {
                final String curFlatCode = action.getActionType().getFlatCode();
                if (actionProp.get(action.getActionType().getId()) == null) {
                    actionProp.put(curFlatCode, new TrfuActionProp(database, curFlatCode, Arrays.asList(propTypes)));
                }
                OrderResult orderResult = new OrderResult();
                actionProp.get(curFlatCode).setRequestState(action.getId(), "");
                final PatientCredentials patientCredentials = SendOrderBloodComponents.getPatientCredentials(action, actionProp.get(curFlatCode));
                if (patientCredentials != null) {
                    final DonorInfo donorInfo = getDonorInfo(database.getEntityMgr(), action, actionProp.get(curFlatCode));
                    final ru.korus.tmis.ws.transfusion.efive.ProcedureInfo procedureInfo =
                            getProcedureInfo(database.getEntityMgr(), action, actionProp.get(curFlatCode));
                    try {
                        orderResult = trfuService.orderMedicalProcedure(donorInfo, patientCredentials, procedureInfo);
                    } catch (final Exception ex) {
                        logger.error(
                                "The procedure {} was not registrate in TRFU. TRFU web method 'orderMedicalProcedure' has thrown runtime exception."
                                        + "  Error description: '{}'",
                                action.getId(), ex.getMessage());
                        ex.printStackTrace();
                    }
                    if (orderResult.isResult()) { // если подситема ТРФУ зарегистрировала требование КК
                        try {
                            actionProp.get(curFlatCode).orderResult2DB(action, orderResult.getRequestId());
                            logger.info("Processing transfusion procedure action {}... The order has been successfully registered in TRFU. TRFU id: {}",
                                    action.getId(), orderResult.getRequestId());
                        } catch (final CoreException ex) {
                            logger.error("The procedure {} was not registrate in TRFU. Cannot save the result into DB. Error description: '{}'",
                                    action.getId(),
                                    ex.getMessage());
                        }
                    } else {
                        logger.error("The procedure {} was not registrate in TRFU. TRFU service return the error satatus. Error description: '{}'",
                                action.getId(),
                                orderResult.getDescription());
                        actionProp.get(curFlatCode).setRequestState(action.getId(), "Ответ системы ТРФУ: " + orderResult.getDescription());
                    }
                }
            } catch (final CoreException ex) {
                logger.error("Error in SendProcedureRequest. Error description: '{}'", ex.getMessage());
                return;
            } catch (final DatatypeConfigurationException e) {
                logger.error("The TRFU procedure {} was not registrate in TRFU. Cannot create the date information. Error description: '{}'", action.getId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private ru.korus.tmis.ws.transfusion.efive.ProcedureInfo
            getProcedureInfo(final EntityManager em, final Action action, TrfuActionProp trfuActionProp) throws CoreException,
                    DatatypeConfigurationException {
        final ru.korus.tmis.ws.transfusion.efive.ProcedureInfo res = new ru.korus.tmis.ws.transfusion.efive.ProcedureInfo();
        res.setId(action.getId());
        final ActionType actionType = EntityMgr.getSafe(action.getActionType());
        res.setOperationType(getTrfuProcType(actionType.getFlatCode()));
        final Staff assigner = senderUtils.getAssigner(action, trfuActionProp);
        final Staff createPerson = EntityMgr.getSafe(assigner);
        res.setDivisionId(senderUtils.getOrgStructure(action, createPerson, trfuActionProp));
        final Event event = EntityMgr.getSafe(action.getEvent());
        res.setIbNumber(senderUtils.getIbNumbre(action, event, trfuActionProp));
        final Date plannedEndDate = senderUtils.getPlannedData(action, trfuActionProp);
        res.setRegistrationDate(Database.toGregorianCalendar(plannedEndDate));
        res.setAttendingPhysicianId(createPerson.getId());
        res.setAttendingPhysicianFirstName(createPerson.getFirstName());
        res.setAttendingPhysicianLastName(createPerson.getLastName());
        res.setAttendingPhysicianMiddleName(createPerson.getPatrName());
        return res;
    }

    /**
     * @param em
     * @param action
     * @param trfuActionProp
     * @return
     * @throws CoreException
     */
    private DonorInfo getDonorInfo(final EntityManager em, final Action action, final TrfuActionProp trfuActionProp) {
        final DonorInfo res = new DonorInfo();
        Integer donorId;
        try {
            donorId = trfuActionProp.getProp(action.getId(), PropType.DONOR_ID);
            final Patient client = em.find(Patient.class, donorId);
            res.setId(donorId);
            if (client != null) {
                res.setLastName(client.getLastName());
                res.setMiddleName(client.getPatrName());
                res.setFirstName(client.getFirstName());
            }
        } catch (final CoreException e) {
            // Донор не задан
        }
        return res;
    }

    /**
     * @param em
     * @return
     * @throws CoreException
     */
    private List<Action> getNewActions(final EntityManager em) throws CoreException {
        final List<RbTrfuProcedureTypes> procedureTypesDb =
                em.createQuery("SELECT p FROM RbTrfuProcedureTypes p WHERE p.unused = 0", RbTrfuProcedureTypes.class).getResultList();
        final List<Action> actions = new LinkedList<Action>();
        for (final RbTrfuProcedureTypes proc : procedureTypesDb) {
            actions.addAll(database.getNewActionByFlatCode(getFlatCode(proc)));
        }
        return actions;
    }

    /**
     * @param trfuService
     */
    private void updateProcedureType(final TransfusionMedicalService trfuService) {
        final List<ProcedureType> procedureTypes = trfuService.getProcedureTypes();
        final EntityManager em = database.getEntityMgr();
        logger.info("The Reference book for TRFU procedure types has been received from TRFU. The count of procedure: {}", procedureTypes.size());
        final List<RbTrfuProcedureTypes> procedureTypesDb = em.createQuery("SELECT p FROM RbTrfuProcedureTypes p", RbTrfuProcedureTypes.class).getResultList();
        final List<RbTrfuProcedureTypes> procedureTypesTrfu = convertToDb(procedureTypes);
        for (final RbTrfuProcedureTypes procedureDb : procedureTypesDb) {
            if (procedureTypesTrfu.remove(procedureDb)) {
                procedureDb.setUnused(false);
            } else {
                procedureDb.setUnused(true);
                logger.info("The blood components unused in TRFU. The component: {}", procedureDb);
            }
        }
        for (final RbTrfuProcedureTypes procedure : procedureTypesTrfu) {
            createActionType(procedure);
            em.persist(procedure);
        }

        em.flush();
    }

    /**
     * @param em
     * @param trfuService
     */
    private void updateLaboratoryMeasure(final TransfusionMedicalService trfuService) {
        final EntityManager em = database.getEntityMgr();
        final List<LaboratoryMeasureType> measureTypes = trfuService.getLaboratoryMeasureTypes();
        logger.info("The Reference book for TRFU laboratory measure has been received from TRFU. The count of procedure: {}", measureTypes.size());
        final List<RbTrfuLaboratoryMeasureTypes> measureTypesDb =
                em.createQuery("SELECT p FROM RbTrfuLaboratoryMeasureTypes p", RbTrfuLaboratoryMeasureTypes.class).getResultList();
        final List<RbTrfuLaboratoryMeasureTypes> measureTypesTrfu = convertLabMeasuresToDb(measureTypes);
        for (final RbTrfuLaboratoryMeasureTypes measureDb : measureTypesDb) {
            measureTypesTrfu.remove(measureDb);
        }
        for (final RbTrfuLaboratoryMeasureTypes measureType : measureTypesTrfu) {
            em.persist(measureType);
        }
        em.flush();

    }

    /**
     * @param measureTypes
     * @return
     */
    private List<RbTrfuLaboratoryMeasureTypes> convertLabMeasuresToDb(final List<LaboratoryMeasureType> measureTypes) {
        final List<RbTrfuLaboratoryMeasureTypes> res = new LinkedList<RbTrfuLaboratoryMeasureTypes>();
        for (final LaboratoryMeasureType procedure : measureTypes) {
            final RbTrfuLaboratoryMeasureTypes compDb = new RbTrfuLaboratoryMeasureTypes();
            compDb.setTrfuId(procedure.getId());
            compDb.setName(procedure.getValue());
            res.add(compDb);
        }
        return res;
    }

    /**
     * @param procedure
     */
    private void createActionType(final RbTrfuProcedureTypes procedure) {
        final EntityManager em = database.getEntityMgr();
        final String flatCode = getFlatCode(procedure);
        final ActionType actionType = getActionTypeByFlatCode(em, flatCode);
        if (actionType == null) {
            final String groupFlatCode = "ExtracorporealMethods";
            final ActionType actionTypes = getActionTypeByFlatCode(em, groupFlatCode);
            final ActionType at = new ActionType();
            at.setGroupId(actionTypes != null ? actionTypes.getId() : null);
            at.setFlatCode(flatCode);
            at.setName(procedure.getName());
            final Date curDate = new Date();
            at.setCreateDatetime(curDate);
            at.setModifyDatetime(curDate);
            at.setTypeClass((short) 2);
            at.setTypeClass((short) 2);
            at.setCode("");
            at.setTitle("");
            at.setSex((short) 0);
            at.setAge("");
            at.setOffice("");
            at.setShowInForm(false);
            at.setGenTimetable(false);
            at.setContext("");
            at.setAmount(0);
            at.setDefaultDirectionDate((short) 2);
            at.setDefaultPlannedEndDate(false);
            at.setDefaultEndDate((short) 0);
            at.setDefaultPersonInEvent((short) 4);
            at.setDefaultPersonInEditor((short) 4);
            at.setMaxOccursInEvent(0);
            at.setShowTime(false);
            at.setIsMES(0);
            at.setCreatePerson(coreUser);
            em.persist(at);
            em.flush();
            createProperties(at);
        } else {
            final String name = actionType.getName();
            if (name != null && name.equals(procedure.getName())) {
                logger.info("The action type with flat code '{}' for same TRFU procedure: {}", flatCode, procedure);
            } else {
                logger.info("The action type with flat code '{}' for different TRFU procedure '{}'. New procedure description: {}", flatCode, name, procedure);
            }
        }

    }

    /**
     * @param em
     * @param flatCode
     * @return
     */
    private ActionType getActionTypeByFlatCode(final EntityManager em, final String flatCode) {
        final List<ActionType> actionTypes =
                em.createQuery("SELECT at FROM ActionType at WHERE at.flatCode = :flatCode", ActionType.class).setParameter("flatCode", flatCode)
                        .getResultList();
        if (actionTypes.isEmpty()) {
            return null;
        } else {
            return actionTypes.get(0);
        }
    }

    /**
     * @param em
     * @param at
     */
    private void createProperties(final ActionType at) {
        for (int idx = 0; idx < propTypes.length; ++idx) {
            final PropType curProp = propTypes[idx];
            final ActionPropertyType apt = new ActionPropertyType();
            apt.setActionType(at);
            apt.setIdx(idx);
            apt.setCode(curProp.getCode());
            apt.setName(curProp.getName());
            apt.setDescr(curProp.getName());
            apt.setUnit(getRbUnit(database.getEntityMgr(), curProp.getUnitCode()));
            final String canonicalName = curProp.getValueClass().getCanonicalName();
            final String typeName =
                    curProp.getTypeName() == null ? canonicalName.substring(canonicalName.indexOf(AP_VALUE) + AP_VALUE.length()) : curProp.getTypeName();
            apt.setTypeName(typeName);
            final String valueDomain = curProp.getValueDomain() == null ? "" : curProp.getValueDomain();
            apt.setValueDomain(valueDomain);
            apt.setNorm("");
            apt.setSex((short) 0);
            apt.setAge("");
            apt.setDefaultValue("");
            apt.setReadOnly(curProp.isReadOnly());
            apt.setMandatory(curProp.isMandatory());
            database.getEntityMgr().persist(apt);
        }
    }

    /**
     * @param em
     * @param unit
     * @return
     */
    private RbUnit getRbUnit(final EntityManager em, final String unitCode) {
        if (unitCode != null) {
            final List<RbUnit> units =
                    em.createQuery("SELECT u FROM RbUnit u WHERE u.code = :code", RbUnit.class).setParameter("code", unitCode).getResultList();
            return units.isEmpty() ? null : units.get(0);
        }
        return null;
    }

    /**
     * @param procedure
     * @return
     */
    private String getFlatCode(final RbTrfuProcedureTypes procedure) {
        return TRFU_PROCEDURE_TRFU_ID_BASE + procedure.getTrfuId();
    }

    private Integer getTrfuProcType(final String flatCode) {
        try {
            return Integer.parseInt(flatCode.substring(TRFU_PROCEDURE_TRFU_ID_BASE.length()));
        } catch (final NumberFormatException ex) {
            logger.error("Cannot convert flatCode {} to TRFU procedure ID the result into DB. Error description: '{}'", flatCode, ex.getMessage());
            return null;
        }
    }

    private List<RbTrfuProcedureTypes> convertToDb(final List<ProcedureType> procedureTypes) {
        final List<RbTrfuProcedureTypes> res = new LinkedList<RbTrfuProcedureTypes>();
        for (final ProcedureType procedure : procedureTypes) {
            final RbTrfuProcedureTypes compDb = new RbTrfuProcedureTypes();
            compDb.setTrfuId(procedure.getId());
            compDb.setName(procedure.getValue());
            compDb.setUnused(false);
            res.add(compDb);
        }
        return res;
    }

}
