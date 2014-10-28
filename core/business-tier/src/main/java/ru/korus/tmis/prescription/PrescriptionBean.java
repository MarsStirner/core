package ru.korus.tmis.prescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.database.DbActionTypeBeanLocal;
import ru.korus.tmis.core.database.DbRbUnitBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.DbDrugChartBeanLocal;
import ru.korus.tmis.core.pharmacy.DbDrugComponentBeanLocal;
import ru.korus.tmis.core.pharmacy.DbPharmacyBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.05.14, 15:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class PrescriptionBean implements PrescriptionBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionBean.class);

    @EJB
    private DbEventBeanLocal dbEventBeanLocal;

    @EJB
    DbDrugChartBeanLocal dbDrugChartBeanLocal;

    @EJB
    DbPharmacyBeanLocal dbPharmacyBeanLocal;

    @EJB
    DbRbUnitBeanLocal dbRbUnitBeanLocal;

    @EJB
    DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    @EJB
    DbActionTypeBeanLocal dbActionTypeBeanLocal;

    @EJB
    DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal;

    @EJB
    DbActionBeanLocal dbActionBeanLocal;

    @EJB
    DbDrugComponentBeanLocal dbDrugComponentBeanLocal;

    @Override
    public PrescriptionsData getPrescriptions(Integer eventId, Long dateRangeMin, Long dateRangeMax, PrescriptionGroupBy groupBy, String admissionId, String drugName, String patientName, String setPersonName, String departmentId, AuthData auth) throws CoreException {
        if (eventId != null) {
            return getPrescriptionsData(eventId);
        }

        PrescriptionFilter prescriptionFilter = new PrescriptionFilter(dateRangeMin, dateRangeMax, groupBy, admissionId, drugName, patientName, setPersonName, departmentId);
        return new PrescriptionsData(prescriptionFilter, dbDrugChartBeanLocal, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
    }

    private PrescriptionsData getPrescriptionsData(Integer eventId) throws CoreException {
        Event event = getEventById(eventId);
        return new PrescriptionsData(event, dbDrugChartBeanLocal, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
    }

    private Event getEventById(Integer eventId) throws CoreException {
        Event event = dbEventBeanLocal.getEventById(eventId);
        if (event == null) {
            throw new CoreException("Обращение не найдено");
        }
        return event;
    }

    @Override
    public PrescriptionTypeDataArray getTypes() {
        return new PrescriptionTypeDataArray(dbActionTypeBeanLocal);
    }

    @Override
    public TemplateData getTemplate(Integer actionTypeId) throws CoreException {
        ActionType actionType = dbActionTypeBeanLocal.getActionTypeById(actionTypeId);
        if (actionType == null) {
            throw new CoreException("Тип назначения не найден");
        }
        return new TemplateData(actionType, "templateAction", dbActionPropertyTypeBeanLocal);
    }

    @Override
    public PrescriptionsData create(CreatePrescriptionReqData createPrescriptionReqData, AuthData authData) throws CoreException {
        final CreatePrescriptionData data = createPrescriptionReqData.getData();
        final Event event = getEventById(data.getEventId());
        final Action action = createPrescriptionAction(createPrescriptionReqData, authData);
        saveDrugs(action, data.getDrugs());
        saveIntervals(action, data.getNote(), data.getAssigmentIntervals());
        return new PrescriptionsData(event, dbDrugChartBeanLocal, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
    }

    @Override
    public PrescriptionsData update(Integer actionId, CreatePrescriptionReqData createPrescriptionReqData, AuthData auth) throws CoreException {
        final CreatePrescriptionData data = createPrescriptionReqData.getData();
        final Event event = getEventById(data.getEventId());
        final Action action = em.find(Action.class, actionId);
        if (action == null) {
            throw new CoreException("Назначение не найдено");
        }
        updatePrescriptionAction(action, data, auth);
        for (AssigmentIntervalData interval : data.getAssigmentIntervals()) {
            final Integer masterId = interval.getMasterId();
            updateInterval(action, data, interval, masterId);
        }

        updateDrugs(action, data.getDrugs());

        return new PrescriptionsData(event, dbDrugChartBeanLocal, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
    }


    @Override
    public ExecuteIntervalsData executeIntervals(ExecuteIntervalsData executeIntervalsData) {
        dbDrugChartBeanLocal.updateStatus(executeIntervalsData.getData(), (short) 1);
        return executeIntervalsData;
    }

    @Override
    public ExecuteIntervalsData cancelIntervals(ExecuteIntervalsData executeIntervalsData) {
        dbDrugChartBeanLocal.updateStatus(executeIntervalsData.getData(), (short) 2);
        return executeIntervalsData;
    }

    @Override
    public ExecuteIntervalsData cancelIntervalsExecution(ExecuteIntervalsData executeIntervalsData) {
        dbDrugChartBeanLocal.updateStatus(executeIntervalsData.getData(), (short) 0);
        return executeIntervalsData;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public AssigmentIntervalData updateIntervals(AssigmentIntervalDataArray assigmentIntervalDataArray) {
        for (AssigmentIntervalData intervalData : assigmentIntervalDataArray.getData()) {
            DrugChart interval = intervalData.getId() == null ? null : em.find(DrugChart.class, intervalData.getId());
            if (interval == null) {
                createNewInterval(intervalData);
            } else {
                updateInterval(interval,
                        intervalData.getBeginDateTime(),
                        intervalData.getEndDateTime(),
                        intervalData.getMasterId(),
                        intervalData.getStatus(),
                        intervalData.getNote());
            }
        }
        return new AssigmentIntervalData();
    }

    private void updateInterval(DrugChart interval, Long beginDateTime, Long endDateTime, Integer masterId, Short status, String note) {
        interval.setBegDateTime(new Date(beginDateTime));
        interval.setEndDateTime(endDateTime == null ? null : new Date(endDateTime));
        if (masterId != null) {
            interval.setMaster(em.find(DrugChart.class, masterId));
        }
        interval.setStatus(status);
        interval.setNote(note);
    }

    private void createNewInterval(AssigmentIntervalData intervalData) {
        Action action = em.find(Action.class, intervalData.getActionId());
        if (action != null) {
            dbDrugChartBeanLocal.create(action,
                    intervalData.getMasterId(),
                    new Date(intervalData.getBeginDateTime()),
                    new Date(intervalData.getEndDateTime()),
                    intervalData.getStatus(),
                    intervalData.getNote());
        }
    }

    private void updateInterval(Action action, CreatePrescriptionData data, AssigmentIntervalData interval, Integer masterId) throws CoreException {
        if (interval.getId() == null || interval.getId().equals(0)) {
            dbDrugChartBeanLocal.create(action,
                    masterId,
                    interval.getBeginDateTime() == null ? null : new Date(interval.getBeginDateTime()),
                    interval.getEndDateTime() == null ? null : new Date(interval.getEndDateTime()),
                    interval.getStatus(),
                    interval.getNote());
        } else {
            DrugChart drugChart = em.find(DrugChart.class, interval.getId());
            if (drugChart == null) {
                throw new CoreException("Интервал назначения не найден id = " + interval.getId());
            } else {
                updateInterval(drugChart, interval);
                for (AssigmentIntervalData execInterval : interval.getExecutionIntervals()) {
                    updateInterval(action, data, execInterval, masterId);
                }
            }
        }
    }

    private void updateInterval(DrugChart drugChart, AssigmentIntervalData interval) {
        drugChart.setBegDateTime(new Date(interval.getBeginDateTime()));
        drugChart.setEndDateTime(interval.getEndDateTime() == null ? null : new Date(interval.getEndDateTime()));
        drugChart.setNote(interval.getNote());

    }

    private void updatePrescriptionAction(Action action, CreatePrescriptionData data, AuthData auth) throws CoreException {
        action.setNote(data.getNote());
        action.setIsUrgent(data.getIsUrgent());
        action.setModifyPerson(em.merge(auth.getUser()));
        for (ActionPropertyTypeData prop : data.getProperties()) {
            ActionProperty ap = null;
            try {
                ap = dbActionPropertyBeanLocal.getActionPropertyById(prop.getId());
            } catch (CoreException ex) {
                logger.info("wrong property id : " + prop.getId(), ex);
            }

            String  value =  prop.getValueId() == null ? prop.getValue() : String.valueOf(prop.getValueId());

            if (ap != null && value != null && !"этот тип экшен проперти пока не поддерживается".equals(prop.getValue())) {
                APValue apv = dbActionPropertyBeanLocal.setActionPropertyValue(ap, value, 0);
                em.persist(apv);
            }
        }
    }

    private void saveIntervals(Action action, String note, List<AssigmentIntervalData> assigmentIntervals) {
        for (AssigmentIntervalData interval : assigmentIntervals) {
            final Date endDateTime = interval.getEndDateTime() == null ? null : new Date(interval.getEndDateTime());
            dbDrugChartBeanLocal.create(action, interval.getMasterId(), new Date(interval.getBeginDateTime()), endDateTime, interval.getStatus(), note);
        }
    }

    private void updateDrugs(Action action, List<DrugData> drugs) {
        for (DrugData drugData : drugs) {
            DrugComponent drugComponent = drugData.getId() == null ? null : em.find(DrugComponent.class, drugData.getId());
            if (drugComponent == null) {
                dbDrugComponentBeanLocal.create(action, drugData.getNomen(), drugData.getName(), drugData.getDose(), drugData.getUnit());
            } else {
                drugComponent.setAction(action);
                em.persist(drugComponent);
            }
        }
        List<DrugComponent> curDrugs = dbDrugComponentBeanLocal.getComponentsByPrescriptionAction(action.getId());
        final Date now = new Date();
        for (DrugComponent drugComp : curDrugs) {
            if (!isPresrent(drugComp, drugs)) {
                drugComp.setCancelDateTime(now);
            }
        }
    }

    private boolean isPresrent(DrugComponent drugComp, List<DrugData> drugs) {
        for (DrugData drugData : drugs) {
            if (drugComp.getId().equals(drugData.getId())) {
                return true;
            }
        }
        return false;
    }


    private void saveDrugs(Action action, List<DrugData> drugs) {
        for (DrugData drugData : drugs) {
            dbDrugComponentBeanLocal.create(action, drugData.getNomen(), drugData.getName(), drugData.getDose(), drugData.getUnit());
        }
    }

    private Action createPrescriptionAction(CreatePrescriptionReqData createPrescriptionReqData, AuthData authData) throws CoreException {
        final CreatePrescriptionData data = createPrescriptionReqData.getData();
        Action action = dbActionBeanLocal.createAction(data.getEventId(), data.getActionTypeId(), authData);
        em.persist(action);
        for (ActionPropertyTypeData prop : data.getProperties()) {
            ActionProperty ap = dbActionPropertyBeanLocal.createActionProperty(action, prop.getActionPropertyTypeId(), authData);
            em.persist(ap);
            final String value = prop.getValue() == null ? (prop.getValueId() == null ? null : String.valueOf(prop.getValueId())) : prop.getValue();
            if (value != null && !value.isEmpty()) {
                em.flush();
                APValue apv = dbActionPropertyBeanLocal.setActionPropertyValue(ap, value, 0);
                em.persist(apv);
            }
        }
        return action;
    }
}
