package ru.korus.tmis.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionStatus;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.DbPrescriptionSendingResBeanLocal;

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

        private final Date begDateTime;

        private final Date endDateTime;

        private final boolean isUrgent;

        private final List<ComponentInfo> componentInfoList;

        private final PrescriptionStatus status;

        private final boolean isPrescription;

        public IntervalInfo(Map.Entry<DrugChart, List<DrugComponent>> intervalWithComp, DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal) throws CoreException {
            final DrugChart interval = intervalWithComp.getKey();
            begDateTime = interval.getBegDateTime();
            endDateTime = interval.getEndDateTime();
            isUrgent =interval.getAction().getIsUrgent();
            componentInfoList = initComponentInfoList(interval, intervalWithComp.getValue(), dbPrescriptionSendingResBeanLocal);
            this.status = interval.getStatusEnum();
            this.isPrescription = interval.getMaster() == null;
        }

        private List<ComponentInfo> initComponentInfoList(DrugChart interval, Iterable<DrugComponent> drugComponents,
                                                          DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal) throws CoreException {
            final List<ComponentInfo> res = new LinkedList<ComponentInfo>();
            for(DrugComponent drugComponent : drugComponents) {
                res.add(new ComponentInfo(interval, drugComponent, dbPrescriptionSendingResBeanLocal));
            }
            return res;
        }

        public boolean isPrescription() {
            return isPrescription;
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

        public PrescriptionStatus getStatus() {
            return status;
        }
    }

    public static class ComponentInfo {

        private final Integer code;

        private final Double dose;

        private final String unitCode;

        private final  String localName;

        private final String uuid;

        public ComponentInfo(DrugChart interval, DrugComponent drugComponent, DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal) throws CoreException {
            if(drugComponent == null)
                throw new IllegalArgumentException("DrugComponent cannot be null");
            if(drugComponent.getNomen() == null)
                throw new CoreException("DrugComponent with id=" + drugComponent.getId() + " has no entry in rlsNomen");

            code = drugComponent.getNomen().getId();
            dose = drugComponent.getDose();
            final RbUnit unit = drugComponent.getUnit();
            unitCode = unit == null ? null : unit.getCode();
            localName = drugComponent.getNomen().getRlsTradeName().getLocalName();
            uuid = dbPrescriptionSendingResBeanLocal.getIntervalUUID(interval, drugComponent);
        }

        public String getUuid() {
            return uuid;
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

    public PrescriptionInfo(Event event, Action action, Map<DrugChart,
            List<DrugComponent>> intervalsWithDrugComp,
                            String routeOfAdministration,
                            String financeType,
                            DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal) throws CoreException {
        this.routeOfAdministration = routeOfAdministration;
        this.financeType = financeType;
        this.uuidDocument = event.getUuid().getUuid();
        this.externalId = event.getExternalId();
        this.createDatetime = action.getCreateDatetime();
        this.intervalInfoList = initPrescriptionIntervals(intervalsWithDrugComp, dbPrescriptionSendingResBeanLocal);
    }

    private List<IntervalInfo> initPrescriptionIntervals(Map<DrugChart, List<DrugComponent>> intervalsWithDrugComp, DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal) throws CoreException {
        LinkedList<IntervalInfo> res = new LinkedList<IntervalInfo>();
        for (Map.Entry<DrugChart, List<DrugComponent>> intervalWithDrugComp : intervalsWithDrugComp.entrySet()) {
            res.add(new IntervalInfo(intervalWithDrugComp, dbPrescriptionSendingResBeanLocal));
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
