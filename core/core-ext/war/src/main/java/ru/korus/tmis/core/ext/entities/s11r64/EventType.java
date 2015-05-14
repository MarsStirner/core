package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EventType database table.
 * 
 */
@Entity
public class EventType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private byte actionFinance;

	private String age;

	@Column(name="age_bc")
	private Integer ageBc;

	@Column(name="age_bu")
	private Byte ageBu;

	@Column(name="age_ec")
	private Integer ageEc;

	@Column(name="age_eu")
	private Byte ageEu;

	private Byte canHavePayableActions;

	private String code;

	private String context;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private Byte dateInput;

	private Byte deleted;

	private Integer eventProfile_id;

    @OneToMany
	@JoinColumn(name="finance_id")
	private RbFinance rbFinance;

	private String form;

	private Byte isAssistant;

	private Byte isCurator;

	private Byte isExternal;

	private Byte isLong;

	private Byte isOrgStructurePriority;

	private Byte isRequiredCoordination;

	private Byte isTakenTissue;

	private Byte limitCureActionsInput;

	private Byte limitDiagnosticActionsInput;

	private Byte limitMiscActionsInput;

	private Byte limitStatusActionsInput;

	private Integer maxDuration;

	private Integer medicalAidType_id;

	private String mesCodeMask;

	private String mesNameMask;

	private Integer mesRequired;

	private Integer minDuration;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	private String name;

	private Byte period;

	@Column(name="purpose_id")
	private Integer purposeId;

	private Integer requestType_id;

	@Column(name="scene_id")
	private Integer sceneId;

	@Column(name="service_id")
	private Integer serviceId;

	private Byte sex;

	private Byte showCureActionsInPlanner;

	private Byte showDiagnosticActionsInPlanner;

	private Byte showMiscActionsInPlanner;

	private Byte showStatusActionsInPlanner;

	private Byte showTime;

	private Byte singleInPeriod;

	private Byte visitFinance;

	private String visitServiceFilter;

	private String visitServiceModifier;

	@ManyToOne
	@JoinColumn(name="counter_id")
	private RbCounter rbCounter;

	@ManyToOne
	private RbMedicalKind rbMedicalKind;

	public EventType() {
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte getActionFinance() {
        return actionFinance;
    }

    public void setActionFinance(byte actionFinance) {
        this.actionFinance = actionFinance;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getAgeBc() {
        return ageBc;
    }

    public void setAgeBc(Integer ageBc) {
        this.ageBc = ageBc;
    }

    public Byte getAgeBu() {
        return ageBu;
    }

    public void setAgeBu(Byte ageBu) {
        this.ageBu = ageBu;
    }

    public Integer getAgeEc() {
        return ageEc;
    }

    public void setAgeEc(Integer ageEc) {
        this.ageEc = ageEc;
    }

    public Byte getAgeEu() {
        return ageEu;
    }

    public void setAgeEu(Byte ageEu) {
        this.ageEu = ageEu;
    }

    public Byte getCanHavePayableActions() {
        return canHavePayableActions;
    }

    public void setCanHavePayableActions(Byte canHavePayableActions) {
        this.canHavePayableActions = canHavePayableActions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getCreatePerson_id() {
        return createPerson_id;
    }

    public void setCreatePerson_id(Integer createPerson_id) {
        this.createPerson_id = createPerson_id;
    }

    public Byte getDateInput() {
        return dateInput;
    }

    public void setDateInput(Byte dateInput) {
        this.dateInput = dateInput;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public Integer getEventProfile_id() {
        return eventProfile_id;
    }

    public void setEventProfile_id(Integer eventProfile_id) {
        this.eventProfile_id = eventProfile_id;
    }

    public RbFinance getRbFinance() {
        return rbFinance;
    }

    public void setRbFinance(RbFinance rbFinance) {
        this.rbFinance = rbFinance;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Byte getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(Byte isAssistant) {
        this.isAssistant = isAssistant;
    }

    public Byte getIsCurator() {
        return isCurator;
    }

    public void setIsCurator(Byte isCurator) {
        this.isCurator = isCurator;
    }

    public Byte getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(Byte isExternal) {
        this.isExternal = isExternal;
    }

    public Byte getIsLong() {
        return isLong;
    }

    public void setIsLong(Byte isLong) {
        this.isLong = isLong;
    }

    public Byte getIsOrgStructurePriority() {
        return isOrgStructurePriority;
    }

    public void setIsOrgStructurePriority(Byte isOrgStructurePriority) {
        this.isOrgStructurePriority = isOrgStructurePriority;
    }

    public Byte getIsRequiredCoordination() {
        return isRequiredCoordination;
    }

    public void setIsRequiredCoordination(Byte isRequiredCoordination) {
        this.isRequiredCoordination = isRequiredCoordination;
    }

    public Byte getIsTakenTissue() {
        return isTakenTissue;
    }

    public void setIsTakenTissue(Byte isTakenTissue) {
        this.isTakenTissue = isTakenTissue;
    }

    public Byte getLimitCureActionsInput() {
        return limitCureActionsInput;
    }

    public void setLimitCureActionsInput(Byte limitCureActionsInput) {
        this.limitCureActionsInput = limitCureActionsInput;
    }

    public Byte getLimitDiagnosticActionsInput() {
        return limitDiagnosticActionsInput;
    }

    public void setLimitDiagnosticActionsInput(Byte limitDiagnosticActionsInput) {
        this.limitDiagnosticActionsInput = limitDiagnosticActionsInput;
    }

    public Byte getLimitMiscActionsInput() {
        return limitMiscActionsInput;
    }

    public void setLimitMiscActionsInput(Byte limitMiscActionsInput) {
        this.limitMiscActionsInput = limitMiscActionsInput;
    }

    public Byte getLimitStatusActionsInput() {
        return limitStatusActionsInput;
    }

    public void setLimitStatusActionsInput(Byte limitStatusActionsInput) {
        this.limitStatusActionsInput = limitStatusActionsInput;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Integer getMedicalAidType_id() {
        return medicalAidType_id;
    }

    public void setMedicalAidType_id(Integer medicalAidType_id) {
        this.medicalAidType_id = medicalAidType_id;
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

    public Integer getMesRequired() {
        return mesRequired;
    }

    public void setMesRequired(Integer mesRequired) {
        this.mesRequired = mesRequired;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Integer getModifyPerson_id() {
        return modifyPerson_id;
    }

    public void setModifyPerson_id(Integer modifyPerson_id) {
        this.modifyPerson_id = modifyPerson_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getPeriod() {
        return period;
    }

    public void setPeriod(Byte period) {
        this.period = period;
    }

    public Integer getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
    }

    public Integer getRequestType_id() {
        return requestType_id;
    }

    public void setRequestType_id(Integer requestType_id) {
        this.requestType_id = requestType_id;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getShowCureActionsInPlanner() {
        return showCureActionsInPlanner;
    }

    public void setShowCureActionsInPlanner(Byte showCureActionsInPlanner) {
        this.showCureActionsInPlanner = showCureActionsInPlanner;
    }

    public Byte getShowDiagnosticActionsInPlanner() {
        return showDiagnosticActionsInPlanner;
    }

    public void setShowDiagnosticActionsInPlanner(Byte showDiagnosticActionsInPlanner) {
        this.showDiagnosticActionsInPlanner = showDiagnosticActionsInPlanner;
    }

    public Byte getShowMiscActionsInPlanner() {
        return showMiscActionsInPlanner;
    }

    public void setShowMiscActionsInPlanner(Byte showMiscActionsInPlanner) {
        this.showMiscActionsInPlanner = showMiscActionsInPlanner;
    }

    public Byte getShowStatusActionsInPlanner() {
        return showStatusActionsInPlanner;
    }

    public void setShowStatusActionsInPlanner(Byte showStatusActionsInPlanner) {
        this.showStatusActionsInPlanner = showStatusActionsInPlanner;
    }

    public Byte getShowTime() {
        return showTime;
    }

    public void setShowTime(Byte showTime) {
        this.showTime = showTime;
    }

    public Byte getSingleInPeriod() {
        return singleInPeriod;
    }

    public void setSingleInPeriod(Byte singleInPeriod) {
        this.singleInPeriod = singleInPeriod;
    }

    public Byte getVisitFinance() {
        return visitFinance;
    }

    public void setVisitFinance(Byte visitFinance) {
        this.visitFinance = visitFinance;
    }

    public String getVisitServiceFilter() {
        return visitServiceFilter;
    }

    public void setVisitServiceFilter(String visitServiceFilter) {
        this.visitServiceFilter = visitServiceFilter;
    }

    public String getVisitServiceModifier() {
        return visitServiceModifier;
    }

    public void setVisitServiceModifier(String visitServiceModifier) {
        this.visitServiceModifier = visitServiceModifier;
    }

    public RbCounter getRbCounter() {
        return rbCounter;
    }

    public void setRbCounter(RbCounter rbCounter) {
        this.rbCounter = rbCounter;
    }

    public RbMedicalKind getRbMedicalKind() {
        return rbMedicalKind;
    }

    public void setRbMedicalKind(RbMedicalKind rbMedicalKind) {
        this.rbMedicalKind = rbMedicalKind;
    }
}