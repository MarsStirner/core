package ru.korus.tmis.prescription;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.prescription.thservice.*;
import ru.korus.tmis.prescription.thservice.DrugComponent;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 02.09.13, 15:50 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class Helper {
    private static final Logger logger = LoggerFactory.getLogger(Helper.class);


    public static Action createPrescriptionAction(Event requestedEvent, ActionType recievedActionType, Staff recievedPerson, ActionData actionInfo) {
        Date now = new Date();
        Action newPrescriptionAction =  new Action();
        newPrescriptionAction.setCreateDatetime(now);
        newPrescriptionAction.setCreatePerson(recievedPerson);
        newPrescriptionAction.setModifyDatetime(now);
        newPrescriptionAction.setModifyPerson(recievedPerson);
        //newPrescriptionAction.setDeleted(false);   //new Action sets automatically
        newPrescriptionAction.setActionType(recievedActionType);
        newPrescriptionAction.setEvent(requestedEvent);
        newPrescriptionAction.setIdx(0);
        newPrescriptionAction.setDirectionDate(now);
        newPrescriptionAction.setStatus((short) 0);
        newPrescriptionAction.setAssigner(recievedPerson);
        //newPrescriptionAction.setIsUrgent(false);   //new Action sets automatically
        newPrescriptionAction.setPlannedEndDate(now);
        newPrescriptionAction.setEndDate(now);
        if(actionInfo.isSetNote()){
            newPrescriptionAction.setNote(actionInfo.getNote());
        }
        newPrescriptionAction.setExecutor(null);
        newPrescriptionAction.setAmount(0);
        newPrescriptionAction.setUet(0.0);
        newPrescriptionAction.setExpose(1);
        newPrescriptionAction.setCoordDate(now);
        newPrescriptionAction.setUuid(new UUID(java.util.UUID.randomUUID().toString()));
        return newPrescriptionAction;
    }

    public static ActionData createThriftActionDataFromEntityAction(Action action) {
        ActionData result = new ActionData()
                .setId(action.getId())
                .setActionType_id(action.getActionType().getId());
        if (action.getNote() != null) {
            result.setNote(action.getNote());
        } else {
            logger.warn("For Action[{}] not set note (Action.note)", action.getId());
        }
        if (action.getAssigner() != null) {
            result.setSetPerson_id(action.getAssigner().getId());
        } else {
            logger.warn("For Action[{}] not set Assigner (Action.person_id)", action.getId());
        }
        final Map<ActionProperty,List<APValue>> propertyListMap;
        try {
            propertyListMap = PrescriptionServer.getActionPropertyBean().getActionPropertiesByActionIdAndTypeCodes(
                    action.getId(),
                    ImmutableList.of(Constants.MOA, Constants.VOA));
            for (Map.Entry<ActionProperty, List<APValue>> currentProperty : propertyListMap.entrySet()) {
                final String currentPropertyCode = currentProperty.getKey().getType().getCode();
                List<APValue> currentPropertyValue = currentProperty.getValue();
                if(Constants.MOA.equals(currentPropertyCode)){
                    if(!currentPropertyValue.isEmpty()){
                        result.setMoa(((APValueString)currentPropertyValue.get(0)).getValue());
                    } else {
                        logger.warn("MOA: not exists ActionPropertyString");
                    }
                } else if(Constants.VOA.equals(currentPropertyCode)){
                    if(!currentPropertyValue.isEmpty()){
                        result.setVoa(((APValueString) currentPropertyValue.get(0)).getValue());
                    } else {
                        logger.warn("VOA: not exists ActionPropertyString");
                    }
                }
            }
        } catch (CoreException e) {
            logger.warn("No ActionProperties with ActionPropertyType.code IN ({},{}) found.", Constants.MOA, Constants.VOA);
        }
        return result;
    }

    public static DrugInterval createThriftDrugIntervalFromEnityDrugChart(DrugChart drugChart) {
        DrugInterval result = new DrugInterval()
                .setId(drugChart.getId())
                .setAction_id(drugChart.getAction().getId())
                .setBegDateTime(DateConvertions.convertDateToUTCMilliseconds(drugChart.getBegDateTime()))
                .setStatus(drugChart.getStatus());
        if (drugChart.getEndDateTime() != null) {
            result.setEndDateTime(DateConvertions.convertDateToUTCMilliseconds(drugChart.getEndDateTime()));
        } else {
            logger.debug("DrugChart[{}].endDateTime is NULL", drugChart.getId());
        }
        if (drugChart.getStatusDateTime() != null) {
            result.setStatusDateTime(DateConvertions.convertDateToUTCMilliseconds(drugChart.getStatusDateTime()));
        } else {
            logger.debug("DrugChart[{}].statusDateTime is NULL", drugChart.getId());
        }
        if (drugChart.getNote() != null) {
            result.setNote(drugChart.getNote());
        } else {
            logger.debug("DrugChart[{}].note is NULL", drugChart.getId());
        }
        return result;
    }

    public static DrugIntervalExec createThriftDrugIntervalExecFromEntityDrugChart(DrugChart execDrugChart, DrugChart drugChart) {
        DrugIntervalExec result = new DrugIntervalExec()
                .setId(execDrugChart.getId())
                .setAction_id(drugChart.getAction().getId())
                .setMaster_id(drugChart.getId())
                .setBegDateTime(DateConvertions.convertDateToUTCMilliseconds(execDrugChart.getBegDateTime()))
                        //.setEndDateTime(DateConvertions.convertDateToUTCMilliseconds(execDrugChart.getEndDateTime()))
                .setStatus(execDrugChart.getStatus());
        // .setStatusDatetime(DateConvertions.convertDateToUTCMilliseconds(execDrugChart.getStatusDateTime()))
        // .setNote(execDrugChart.getNote());
        if (execDrugChart.getEndDateTime() != null) {
            result.setEndDateTime(DateConvertions.convertDateToUTCMilliseconds(execDrugChart.getEndDateTime()));
        } else {
            logger.debug("Execution DrugChart[{}].endDateTime is NULL", drugChart.getId());
        }
        if (execDrugChart.getStatusDateTime() != null) {
            result.setStatusDateTime(DateConvertions.convertDateToUTCMilliseconds(execDrugChart.getStatusDateTime()));
        } else {
            logger.debug("Execution DrugChart[{}].statusDateTime is NULL", execDrugChart.getId());
        }
        if (execDrugChart.getNote() != null) {
            result.setNote(drugChart.getNote());
        } else {
            logger.debug("Execution DrugChart[{}].note is NULL", drugChart.getId());
        }
        return result;
    }

    public static DrugComponent createThriftDrugComponentFromEntityDrugComponent(ru.korus.tmis.core.entity.model.pharmacy.DrugComponent tableDrugComponent) {
        DrugComponent result = new DrugComponent()
                .setAction_id(tableDrugComponent.getAction().getId())
                .setId(tableDrugComponent.getId())
                .setCreateDateTime(DateConvertions.convertDateToUTCMilliseconds(tableDrugComponent.getCreateDateTime()));
        if(tableDrugComponent.getNomen() != null){
            result.setNomen(tableDrugComponent.getNomen().getId());
        } else {
            logger.warn("DrugComponent[{}].nomem IS NULL", tableDrugComponent.getId());
        }
        if(tableDrugComponent.getName() != null){
            result.setName(tableDrugComponent.getName());
        } else {
            logger.warn("DrugComponent[{}].name IS NULL", tableDrugComponent.getId());
        }
        if(tableDrugComponent.getDose() != null){
            result.setDose(tableDrugComponent.getDose());
        } else {
            logger.warn("DrugComponent[{}].dose IS NULL", tableDrugComponent.getId());
        }
        if(tableDrugComponent.getUnit() != null){
            result.setUnit(tableDrugComponent.getUnit().getId());
        } else {
            logger.warn("DrugComponent[{}].name IS NULL", tableDrugComponent.getId());
        }
        if(tableDrugComponent.getUnit() != null){
            result.setUnit(tableDrugComponent.getUnit().getId());
        } else {
            logger.warn("DrugComponent[{}].unit IS NULL", tableDrugComponent.getId());
        }
        if(tableDrugComponent.getCancelDateTime() != null){
            result.setCancelDateTime(DateConvertions.convertDateToUTCMilliseconds(tableDrugComponent.getCancelDateTime()));
        } else {
            logger.warn("DrugComponent[{}].cancelDateTime IS NULL", tableDrugComponent.getId());
        }
        return result;
    }

    public static ru.korus.tmis.core.entity.model.pharmacy.DrugComponent createEntityDrugComponentFromThriftDrugComponent(DrugComponent drugComponent, Action action)
            throws CoreException {
        Date now = new Date();
        ru.korus.tmis.core.entity.model.pharmacy.DrugComponent result = new ru.korus.tmis.core.entity.model.pharmacy.DrugComponent();
        result.setAction(action);
        result.setCancelDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(drugComponent.getCancelDateTime()));
        result.setCreateDateTime(now);
        result.setDose(drugComponent.getDose());
        result.setName(drugComponent.getName());
        //TODO Доработать!
        //result.setNomen(drugComponent.getNomen());
        result.setUnit(PrescriptionServer.getUnitBean().getById(drugComponent.getUnit()));
        return result;
    }

    public static DrugChart createEntityDrugChartFromThriftDrugInterval(DrugInterval drugInterval, Action action){
        final DrugChart result = new DrugChart();
        result.setAction(action);
        result.setBegDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(drugInterval.getBegDateTime()));
        result.setEndDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(drugInterval.getEndDateTime()));
        result.setMaster(null);
        result.setNote(drugInterval.getNote());
        result.setStatus((short)drugInterval.getStatus());
        result.setStatusDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(drugInterval.getStatusDateTime()));
        return result;
    }

    public static DrugChart createEntityDrugChartFromThriftDrugIntervalExecution(
            DrugIntervalExec intervalExec,
            DrugChart drugChart,
            Action action) {
        final DrugChart result = new DrugChart();
        result.setAction(action);
        result.setBegDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(intervalExec.getBegDateTime()));
        result.setEndDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(intervalExec.getEndDateTime()));
        result.setMaster(drugChart);
        result.setNote(intervalExec.getNote());
        result.setStatus((short)intervalExec.getStatus());
        result.setStatusDateTime(DateConvertions.convertUTCMillisecondsToLocalDate(intervalExec.getStatusDateTime()));
        return result;
    }
}
