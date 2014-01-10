package ru.korus.tmis.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.DrugChart;
import ru.korus.tmis.core.entity.model.DrugComponent;
import ru.korus.tmis.core.entity.model.Event;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.01.14, 12:07 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PrescriptionInfo {

    public static class IntervalInfo {

        final Date begDateTime;

        final Date endDateTime;

        final boolean isUrgent;

        final List<ComponentInfo> componentInfoList;

        public IntervalInfo(Map.Entry<DrugChart, List<DrugComponent>> intervalWithComp) {
            final DrugChart interval = intervalWithComp.getKey();
            begDateTime = interval.getBegDateTime();
            endDateTime = interval.getEndDateTime();
            isUrgent =interval.getAction().getIsUrgent();
            componentInfoList = initComponentInfoList(intervalWithComp.getValue());
        }

        private List<ComponentInfo> initComponentInfoList(Iterable<DrugComponent> drugComponents) {
            final List<ComponentInfo> res = new LinkedList<ComponentInfo>();
            for(DrugComponent drugComponent : drugComponents) {
                res.add(new ComponentInfo(drugComponent));
            }
            return res;
        }

        public List<ComponentInfo> getComponentInfoList() {
            return componentInfoList;
        }

        public Date getBegDateTime() {
            return begDateTime;
        }

        public Date getEndDateTime() {
            return endDateTime;
        }

        public boolean isUrgent() {
            return isUrgent;
        }
    }

    public static class ComponentInfo {

        private final Integer code;

        private final Double dose;

        private final String unitCode;

        private final  String localName;

        public ComponentInfo(DrugComponent drugComponent) {
            code = drugComponent.getId();
            dose = drugComponent.getDose();
            unitCode = drugComponent.getNomen().getUnit().getCode();
            localName = drugComponent.getNomen().getRlsTradeName().getLocalName();
        }

        public Integer getCode() {
            return code;
        }

        public Double getDose() {
            return dose;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public String getLocalName() {
            return localName;
        }
    }

    private final String routeOfAdministration;

    private final String financeType;

    private AssignmentType assignmentType;

    /**
     * Флаг отмены. Если negationInd == true, то интервал отменен
     */
    private Boolean negationInd;

    /**
     * UUID листа назанчений
     */
    final private String uuidDocument;

    /**
     * UUID интервала
     */
    private String prescrUUID;

    /**
     * Номер амбулаторной карты пациента
     */
    final String externalId;

    final Date createDatetime;

    final List<IntervalInfo> intervalInfoList;

    public PrescriptionInfo(Event event, Action action, Map<DrugChart, List<DrugComponent>> intervalsWithDrugComp, String routeOfAdministration, String financeType) {
        this.routeOfAdministration = routeOfAdministration;
        this.financeType = financeType;
        this.uuidDocument = event.getUuid().getUuid();
        this.externalId = event.getExternalId();
        this.createDatetime = action.getCreateDatetime();
        this.intervalInfoList = initPrescriptionIntervals(intervalsWithDrugComp);
    }

    private List<IntervalInfo> initPrescriptionIntervals(Map<DrugChart, List<DrugComponent>> intervalsWithDrugComp) {
        LinkedList<IntervalInfo> res = new LinkedList<IntervalInfo>();
        for (Map.Entry<DrugChart, List<DrugComponent>> intervalWithDrugComp : intervalsWithDrugComp.entrySet()) {
            res.add(new IntervalInfo(intervalWithDrugComp));
        }
        return res;
    }

    public List<IntervalInfo> getIntervalInfoList() {
        return intervalInfoList;
    }

    public String getUuidDocument() {
        return uuidDocument;
    }

    public String getExternalId() {
        return externalId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public PrescriptionInfo setAssignmentType(AssignmentType type) {
        this.assignmentType = type;
        return this;
    }

    public PrescriptionInfo setNegationInd(Boolean negationInd) {
        this.negationInd = negationInd;
        return this;
    }

    public PrescriptionInfo setPrescrUUID(String prescrUUID) {
        this.prescrUUID = prescrUUID;
        return this;
    }

    public String getRouteOfAdministration() {
        return routeOfAdministration;
    }

    public String getFinanceType() {
        return financeType;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public Boolean getNegationInd() {
        return negationInd;
    }

    public String getPrescrUUID() {
        return prescrUUID;
    }
}
