package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.DbRbUnitBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.DrugIntervalCompParam;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.05.14, 15:49 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class PrescriptionData {

    /**
     * Action.id
     */
    final private Integer id;

    final private Integer actionTypeId;

    final private Integer eventId;

    /**
     * ActionType.name
     */
    final private String name;

    /**
     * Action.IsUrgent
     */
    final private Boolean isUrgent;

    /**
     * Action.note
     */
    final private String note;

    final private PersonData client;

    final private List<AssigmentIntervalData> assigmentIntervals = new LinkedList<AssigmentIntervalData>();

    final private PersonData createPerson;

    final private PersonData doctor;

    final private List<DrugData> drugs = new LinkedList<DrugData>();

    final private PersonData modifyPerson;

    final private List<PropertiesData> properties = new LinkedList<PropertiesData>();

    final private PersonData setPerson;

    public PrescriptionData(Action action,
                            DbDrugChartBeanLocal dbDrugChartBeanLocal,
                            DbDrugIntervalCompParamLocal dbDrugIntervalCompParam,
                            DbPharmacyBeanLocal dbPharmacyBeanLocal,
                            DbRbUnitBeanLocal dbRbUnitBeanLocal,
                            DbActionPropertyBeanLocal dbActionPropertyBeanLocal) throws CoreException {
        id = action.getId();
        final ActionType actionType = action.getActionType();
        actionTypeId = actionType == null ? null : actionType.getId();
        final Event event = action.getEvent();
        eventId = event == null ? null : event.getId();
        name = actionType == null ? null : actionType.getName();

        isUrgent = action.getIsUrgent();
        note = action.getNote();
        client = (event == null || event.getPatient() == null) ?  null : new PersonData(event.getPatient());
        List<DrugComponent> drugComponentList = dbPharmacyBeanLocal.getDrugComponent(action);
        List<DrugChart> prescriptionIntervals = dbDrugChartBeanLocal.getPrescriptionIntervals(id);
        for (DrugChart drugChart : prescriptionIntervals) {
            List<DrugIntervalCompParam>  drugParams = dbDrugIntervalCompParam.getCompParamByInterval(drugChart);
            assigmentIntervals.add(new AssigmentIntervalData(drugChart, drugParams, dbDrugChartBeanLocal));
        }
        createPerson = action.getCreatePerson() == null ? null : new PersonData(action.getCreatePerson());
        doctor = event == null || event.getExecutor() == null ? null : new PersonData(event.getExecutor());
        for (DrugComponent drugComponent : drugComponentList) {
            drugs.add(new DrugData(drugComponent, dbRbUnitBeanLocal));
        }
        modifyPerson = action.getModifyPerson() == null ? null : new PersonData(action.getModifyPerson());
        Map<ActionProperty, List<APValue>> actionPropertyMap = dbActionPropertyBeanLocal.getActionPropertiesByActionId(action.getId());
        for(Map.Entry<ActionProperty, List<APValue>> entry : actionPropertyMap.entrySet()) {
            properties.add(new PropertiesData(entry.getKey(), entry.getValue()));
        }
        setPerson = action.getAssigner() == null ? null : new PersonData(action.getAssigner());
    }

    public PrescriptionData() {
        id = null;
        actionTypeId = null;
        eventId = null;
        name = null;
        isUrgent = null;
        note = null;
        client = null;
        createPerson = null;
        doctor = null;
        modifyPerson = null;
        setPerson = null;
    }

    public Integer getId() {
        return id;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public String getNote() {
        return note;
    }

    public ru.korus.tmis.prescription.PersonData getClient() {
        return client;
    }

    public List<ru.korus.tmis.prescription.AssigmentIntervalData> getAssigmentIntervals() {
        return assigmentIntervals;
    }

    public ru.korus.tmis.prescription.PersonData getCreatePerson() {
        return createPerson;
    }

    public ru.korus.tmis.prescription.PersonData getDoctor() {
        return doctor;
    }

    public List<ru.korus.tmis.prescription.DrugData> getDrugs() {
        return drugs;
    }

    public ru.korus.tmis.prescription.PersonData getModifyPerson() {
        return modifyPerson;
    }

    public List<ru.korus.tmis.prescription.PropertiesData> getProperties() {
        return properties;
    }

    public ru.korus.tmis.prescription.PersonData getSetPerson() {
        return setPerson;
    }
}
