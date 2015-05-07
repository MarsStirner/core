package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ActionType database table.
 * 
 */
@Entity
public class ActionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String age;

	@Column(name="age_bc")
	private Integer ageBc;

	@Column(name="age_bu")
	private Byte ageBu;

	@Column(name="age_ec")
	private Integer ageEc;

	@Column(name="age_eu")
	private Byte ageEu;

	private double amount;

	private Integer amountEvaluation;

	@Column(name="class")
	private Byte class_;

	private String code;

	private String context;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private Byte defaultDirectionDate;

	private Byte defaultEndDate;

	private Byte defaultPersonInEditor;

	private Byte defaultPersonInEvent;

	private Byte defaultPlannedEndDate;

	private Byte defaultStatus;

	private Byte deleted;

	private String flatCode;

	private Byte genTimetable;

	@Column(name="group_id")
	private Integer groupId;

	private Byte hidden;

	private Integer isMES;

	private Byte isPreferable;

	private Byte isRequiredCoordination;

	private Byte isRequiredTissue;

	@Lob
	private String layout;

	private Integer maxOccursInEvent;

	private String mnem;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	private String name;

	private Integer nomenclativeService_id;

	private String office;

	private Integer prescribedType_id;

	private Integer quotaType_id;

	@Column(name="service_id")
	private Integer serviceId;

	private Byte sex;

	@Column(name="shedule_id")
	private Integer sheduleId;

	private Byte showInForm;

	private Byte showTime;

	private String title;


	public ActionType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getAgeBc() {
		return this.ageBc;
	}

	public void setAgeBc(Integer ageBc) {
		this.ageBc = ageBc;
	}

	public Byte getAgeBu() {
		return this.ageBu;
	}

	public void setAgeBu(Byte ageBu) {
		this.ageBu = ageBu;
	}

	public Integer getAgeEc() {
		return this.ageEc;
	}

	public void setAgeEc(Integer ageEc) {
		this.ageEc = ageEc;
	}

	public Byte getAgeEu() {
		return this.ageEu;
	}

	public void setAgeEu(Byte ageEu) {
		this.ageEu = ageEu;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getAmountEvaluation() {
		return this.amountEvaluation;
	}

	public void setAmountEvaluation(Integer amountEvaluation) {
		this.amountEvaluation = amountEvaluation;
	}

	public Byte getClass_() {
		return this.class_;
	}

	public void setClass_(Byte class_) {
		this.class_ = class_;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getCreatePerson_id() {
		return this.createPerson_id;
	}

	public void setCreatePerson_id(Integer createPerson_id) {
		this.createPerson_id = createPerson_id;
	}

	public Byte getDefaultDirectionDate() {
		return this.defaultDirectionDate;
	}

	public void setDefaultDirectionDate(Byte defaultDirectionDate) {
		this.defaultDirectionDate = defaultDirectionDate;
	}

	public Byte getDefaultEndDate() {
		return this.defaultEndDate;
	}

	public void setDefaultEndDate(Byte defaultEndDate) {
		this.defaultEndDate = defaultEndDate;
	}

	public Byte getDefaultPersonInEditor() {
		return this.defaultPersonInEditor;
	}

	public void setDefaultPersonInEditor(Byte defaultPersonInEditor) {
		this.defaultPersonInEditor = defaultPersonInEditor;
	}

	public Byte getDefaultPersonInEvent() {
		return this.defaultPersonInEvent;
	}

	public void setDefaultPersonInEvent(Byte defaultPersonInEvent) {
		this.defaultPersonInEvent = defaultPersonInEvent;
	}

	public Byte getDefaultPlannedEndDate() {
		return this.defaultPlannedEndDate;
	}

	public void setDefaultPlannedEndDate(Byte defaultPlannedEndDate) {
		this.defaultPlannedEndDate = defaultPlannedEndDate;
	}

	public Byte getDefaultStatus() {
		return this.defaultStatus;
	}

	public void setDefaultStatus(Byte defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	public Byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public String getFlatCode() {
		return this.flatCode;
	}

	public void setFlatCode(String flatCode) {
		this.flatCode = flatCode;
	}

	public Byte getGenTimetable() {
		return this.genTimetable;
	}

	public void setGenTimetable(Byte genTimetable) {
		this.genTimetable = genTimetable;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Byte getHidden() {
		return this.hidden;
	}

	public void setHidden(Byte hidden) {
		this.hidden = hidden;
	}

	public Integer getIsMES() {
		return this.isMES;
	}

	public void setIsMES(Integer isMES) {
		this.isMES = isMES;
	}

	public Byte getIsPreferable() {
		return this.isPreferable;
	}

	public void setIsPreferable(Byte isPreferable) {
		this.isPreferable = isPreferable;
	}

	public Byte getIsRequiredCoordination() {
		return this.isRequiredCoordination;
	}

	public void setIsRequiredCoordination(Byte isRequiredCoordination) {
		this.isRequiredCoordination = isRequiredCoordination;
	}

	public Byte getIsRequiredTissue() {
		return this.isRequiredTissue;
	}

	public void setIsRequiredTissue(Byte isRequiredTissue) {
		this.isRequiredTissue = isRequiredTissue;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Integer getMaxOccursInEvent() {
		return this.maxOccursInEvent;
	}

	public void setMaxOccursInEvent(Integer maxOccursInEvent) {
		this.maxOccursInEvent = maxOccursInEvent;
	}

	public String getMnem() {
		return this.mnem;
	}

	public void setMnem(String mnem) {
		this.mnem = mnem;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Integer getModifyPerson_id() {
		return this.modifyPerson_id;
	}

	public void setModifyPerson_id(Integer modifyPerson_id) {
		this.modifyPerson_id = modifyPerson_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNomenclativeService_id() {
		return this.nomenclativeService_id;
	}

	public void setNomenclativeService_id(Integer nomenclativeService_id) {
		this.nomenclativeService_id = nomenclativeService_id;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Integer getPrescribedType_id() {
		return this.prescribedType_id;
	}

	public void setPrescribedType_id(Integer prescribedType_id) {
		this.prescribedType_id = prescribedType_id;
	}

	public Integer getQuotaType_id() {
		return this.quotaType_id;
	}

	public void setQuotaType_id(Integer quotaType_id) {
		this.quotaType_id = quotaType_id;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Integer getSheduleId() {
		return this.sheduleId;
	}

	public void setSheduleId(Integer sheduleId) {
		this.sheduleId = sheduleId;
	}

	public Byte getShowInForm() {
		return this.showInForm;
	}

	public void setShowInForm(Byte showInForm) {
		this.showInForm = showInForm;
	}

	public Byte getShowTime() {
		return this.showTime;
	}

	public void setShowTime(Byte showTime) {
		this.showTime = showTime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}