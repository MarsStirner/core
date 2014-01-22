package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "EventType")
@NamedQueries(
        {
                @NamedQuery(name = "EventType.findAll", query = "SELECT e FROM EventType e"),
                @NamedQuery(name = "EventType.getQueueEventType", query = "SELECT e FROM EventType e WHERE e.code = 'queue'")
        })
@XmlType(name = "eventType")
@XmlRootElement(name = "eventType")
public class EventType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Column(name = "purpose_id")
    private Integer purposeId;

    @ManyToOne
    @JoinColumn(name = "finance_id")
    private RbFinance finance;

    @Column(name = "scene_id")
    private Integer sceneId;

    @Basic(optional = false)
    @Column(name = "visitServiceModifier")
    private String visitServiceModifier;

    @Basic(optional = false)
    @Column(name = "visitServiceFilter")
    private String visitServiceFilter;

    @Basic(optional = false)
    @Column(name = "visitFinance")
    private boolean visitFinance;

    @Basic(optional = false)
    @Column(name = "actionFinance")
    private boolean actionFinance;

    @Basic(optional = false)
    @Column(name = "period")
    private short period;

    @Basic(optional = false)
    @Column(name = "singleInPeriod")
    private short singleInPeriod;

    @Basic(optional = false)
    @Column(name = "isLong")
    private boolean isLong;

    @Basic(optional = false)
    @Column(name = "dateInput")
    private boolean dateInput;

    @Column(name = "service_id")
    private Integer serviceId;

    @Basic(optional = false)
    @Column(name = "context")
    private String context;

    @Basic(optional = false)
    @Column(name = "form")
    private String form;

    @Basic(optional = false)
    @Column(name = "minDuration")
    private int minDuration;

    @Basic(optional = false)
    @Column(name = "maxDuration")
    private int maxDuration;

    @Basic(optional = false)
    @Column(name = "showStatusActionsInPlanner")
    private boolean showStatusActionsInPlanner;

    @Basic(optional = false)
    @Column(name = "showDiagnosticActionsInPlanner")
    private boolean showDiagnosticActionsInPlanner;

    @Basic(optional = false)
    @Column(name = "showCureActionsInPlanner")
    private boolean showCureActionsInPlanner;

    @Basic(optional = false)
    @Column(name = "showMiscActionsInPlanner")
    private boolean showMiscActionsInPlanner;

    @Basic(optional = false)
    @Column(name = "limitStatusActionsInput")
    private boolean limitStatusActionsInput;

    @Basic(optional = false)
    @Column(name = "limitDiagnosticActionsInput")
    private boolean limitDiagnosticActionsInput;

    @Basic(optional = false)
    @Column(name = "limitCureActionsInput")
    private boolean limitCureActionsInput;

    @Basic(optional = false)
    @Column(name = "limitMiscActionsInput")
    private boolean limitMiscActionsInput;

    @Basic(optional = false)
    @Column(name = "showTime")
    private boolean showTime;

    @Column(name = "medicalAidType_id")
    private Integer medicalAidTypeId;

    @Column(name = "eventProfile_id")
    private Integer eventProfileId;

    @Basic(optional = false)
    @Column(name = "mesRequired")
    private int mesRequired;

    @Column(name = "mesCodeMask")
    private String mesCodeMask;

    @Column(name = "mesNameMask")
    private String mesNameMask;

    @Column(name = "counter_id")
    private Integer counterId;

    @Basic(optional = false)
    @Column(name = "isExternal")
    private boolean isExternal;

    @Basic(optional = false)
    @Column(name = "isAssistant")
    private boolean isAssistant;

    @Basic(optional = false)
    @Column(name = "isCurator")
    private boolean isCurator;

    @Basic(optional = false)
    @Column(name = "canHavePayableActions")
    private boolean canHavePayableActions;

    @Basic(optional = false)
    @Column(name = "isRequiredCoordination")
    private boolean isRequiredCoordination;

    @Basic(optional = false)
    @Column(name = "isOrgStructurePriority")
    private boolean isOrgStructurePriority;

    @Basic(optional = false)
    @Column(name = "isTakenTissue")
    private boolean isTakenTissue;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @ManyToOne
    @JoinColumn(name = "requestType_id")
    private RbRequestType requestType;

    public EventType() {
    }

    public EventType(Integer id) {
        this.id = id;
    }

    public EventType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public String getVisitServiceModifier() {
        return visitServiceModifier;
    }

    public void setVisitServiceModifier(String visitServiceModifier) {
        this.visitServiceModifier = visitServiceModifier;
    }

    public String getVisitServiceFilter() {
        return visitServiceFilter;
    }

    public void setVisitServiceFilter(String visitServiceFilter) {
        this.visitServiceFilter = visitServiceFilter;
    }

    public boolean getVisitFinance() {
        return visitFinance;
    }

    public void setVisitFinance(boolean visitFinance) {
        this.visitFinance = visitFinance;
    }

    public boolean getActionFinance() {
        return actionFinance;
    }

    public void setActionFinance(boolean actionFinance) {
        this.actionFinance = actionFinance;
    }

    public short getPeriod() {
        return period;
    }

    public void setPeriod(short period) {
        this.period = period;
    }

    public short getSingleInPeriod() {
        return singleInPeriod;
    }

    public void setSingleInPeriod(short singleInPeriod) {
        this.singleInPeriod = singleInPeriod;
    }

    public boolean getIsLong() {
        return isLong;
    }

    public void setIsLong(boolean isLong) {
        this.isLong = isLong;
    }

    public boolean getDateInput() {
        return dateInput;
    }

    public void setDateInput(boolean dateInput) {
        this.dateInput = dateInput;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public boolean getShowStatusActionsInPlanner() {
        return showStatusActionsInPlanner;
    }

    public void setShowStatusActionsInPlanner(boolean showStatusActionsInPlanner) {
        this.showStatusActionsInPlanner = showStatusActionsInPlanner;
    }

    public boolean getShowDiagnosticActionsInPlanner() {
        return showDiagnosticActionsInPlanner;
    }

    public void setShowDiagnosticActionsInPlanner(boolean showDiagnosticActionsInPlanner) {
        this.showDiagnosticActionsInPlanner = showDiagnosticActionsInPlanner;
    }

    public boolean getShowCureActionsInPlanner() {
        return showCureActionsInPlanner;
    }

    public void setShowCureActionsInPlanner(boolean showCureActionsInPlanner) {
        this.showCureActionsInPlanner = showCureActionsInPlanner;
    }

    public boolean getShowMiscActionsInPlanner() {
        return showMiscActionsInPlanner;
    }

    public void setShowMiscActionsInPlanner(boolean showMiscActionsInPlanner) {
        this.showMiscActionsInPlanner = showMiscActionsInPlanner;
    }

    public boolean getLimitStatusActionsInput() {
        return limitStatusActionsInput;
    }

    public void setLimitStatusActionsInput(boolean limitStatusActionsInput) {
        this.limitStatusActionsInput = limitStatusActionsInput;
    }

    public boolean getLimitDiagnosticActionsInput() {
        return limitDiagnosticActionsInput;
    }

    public void setLimitDiagnosticActionsInput(boolean limitDiagnosticActionsInput) {
        this.limitDiagnosticActionsInput = limitDiagnosticActionsInput;
    }

    public boolean getLimitCureActionsInput() {
        return limitCureActionsInput;
    }

    public void setLimitCureActionsInput(boolean limitCureActionsInput) {
        this.limitCureActionsInput = limitCureActionsInput;
    }

    public boolean getLimitMiscActionsInput() {
        return limitMiscActionsInput;
    }

    public void setLimitMiscActionsInput(boolean limitMiscActionsInput) {
        this.limitMiscActionsInput = limitMiscActionsInput;
    }

    public boolean getShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public Integer getMedicalAidTypeId() {
        return medicalAidTypeId;
    }

    public void setMedicalAidTypeId(Integer medicalAidTypeId) {
        this.medicalAidTypeId = medicalAidTypeId;
    }

    public Integer getEventProfileId() {
        return eventProfileId;
    }

    public void setEventProfileId(Integer eventProfileId) {
        this.eventProfileId = eventProfileId;
    }

    public int getMesRequired() {
        return mesRequired;
    }

    public void setMesRequired(int mesRequired) {
        this.mesRequired = mesRequired;
    }

    public String getMesCodeMask() {
        return mesCodeMask;
    }

    public void setMesCodeMask(String mesCodeMask) {
        this.mesCodeMask = mesCodeMask;
    }

    public String getMesNameMask() {
        return mesNameMask;
    }

    public void setMesNameMask(String mesNameMask) {
        this.mesNameMask = mesNameMask;
    }

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public boolean getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

    public boolean getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(boolean isAssistant) {
        this.isAssistant = isAssistant;
    }

    public boolean getIsCurator() {
        return isCurator;
    }

    public void setIsCurator(boolean isCurator) {
        this.isCurator = isCurator;
    }

    public boolean getCanHavePayableActions() {
        return canHavePayableActions;
    }

    public void setCanHavePayableActions(boolean canHavePayableActions) {
        this.canHavePayableActions = canHavePayableActions;
    }

    public boolean getIsRequiredCoordination() {
        return isRequiredCoordination;
    }

    public void setIsRequiredCoordination(boolean isRequiredCoordination) {
        this.isRequiredCoordination = isRequiredCoordination;
    }

    public boolean getIsOrgStructurePriority() {
        return isOrgStructurePriority;
    }

    public void setIsOrgStructurePriority(boolean isOrgStructurePriority) {
        this.isOrgStructurePriority = isOrgStructurePriority;
    }

    public boolean getIsTakenTissue() {
        return isTakenTissue;
    }

    public void setIsTakenTissue(boolean isTakenTissue) {
        this.isTakenTissue = isTakenTissue;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public RbFinance getFinance() {
        return finance;
    }

    public void setFinance(RbFinance finance) {
        this.finance = finance;
    }

    public RbRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RbRequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventType)) {
            return false;
        }
        EventType other = (EventType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.EventType[id=" + id + "]";
    }
}
