package ru.korus.tmis.prescription;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.14, 17:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePrescriptionData {

    private Integer actionTypeId = null;
    private Integer eventId = null;
    private List<ActionPropertyTypeData> properties = new LinkedList<ActionPropertyTypeData>();
    private List<DrugData> drugs = new LinkedList<DrugData>();
    private List<AssigmentIntervalData> assigmentIntervals = new LinkedList<AssigmentIntervalData>();
    private String note = null;
    private boolean isUrgent = false;

    public CreatePrescriptionData(ActionType actionType, DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal) throws CoreException {
        actionTypeId = actionType.getId();
        List<ActionPropertyType> actionPropTypeList = dbActionPropertyTypeBeanLocal.getActionPropertyTypesByActionTypeId(actionType.getId());
        for(ActionPropertyType actionPropertyType : actionPropTypeList) {
            properties.add(new ActionPropertyTypeData(actionPropertyType));
        }
        eventId = null;
    }

    public CreatePrescriptionData() {
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public List<ActionPropertyTypeData> getProperties() {
        return properties;
    }

    public Integer getEventId() {
        return eventId;
    }

    public List<DrugData> getDrugs() {
        return drugs;
    }

    public List<AssigmentIntervalData> getAssigmentIntervals() {
        return assigmentIntervals;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setProperties(List<ActionPropertyTypeData> properties) {
        this.properties = properties;
    }

    public void setDrugs(List<DrugData> drugs) {
        this.drugs = drugs;
    }

    public void setAssigmentIntervals(List<AssigmentIntervalData> assigmentIntervals) {
        this.assigmentIntervals = assigmentIntervals;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getIsUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
}
