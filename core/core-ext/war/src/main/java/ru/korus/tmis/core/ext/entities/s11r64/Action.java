package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Action database table.
 * 
 */
@Entity
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private Byte account;

	private Integer actionType_id;

	private Double amount;

	private String appointmentType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date begDate;

	@Column(name="contract_id")
	private Integer contractId;

	private String coordAgent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date coordDate;

	private String coordInspector;

	@Lob
	private String coordText;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	@Column(name="dcm_study_uid")
	private String dcmStudyUid;

	private Byte deleted;

	@Temporal(TemporalType.TIMESTAMP)
	private Date directionDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name="event_id")
	private Integer eventId;

	private Integer expose;

	@Column(name="finance_id")
	private Integer financeId;

	private String hospitalUidFrom;

	private Integer idx;

	private Integer isUrgent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	@Lob
	private String note;

	private String office;

	private Byte pacientInQueueType;

	private Integer parentAction_id;

	private Integer payStatus;

	@Column(name="person_id")
	private Integer personId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date plannedEndDate;

	@Column(name="prescription_id")
	private Integer prescriptionId;

	private Integer setPerson_id;

	private Byte status;

	private Double uet;

	@Column(name="uuid_id")
	private Integer uuidId;

	private Integer version;


	public Action() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getAccount() {
		return this.account;
	}

	public void setAccount(Byte account) {
		this.account = account;
	}

	public Integer getActionType_id() {
		return this.actionType_id;
	}

	public void setActionType_id(Integer actionType_id) {
		this.actionType_id = actionType_id;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAppointmentType() {
		return this.appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Date getBegDate() {
		return this.begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Integer getContractId() {
		return this.contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getCoordAgent() {
		return this.coordAgent;
	}

	public void setCoordAgent(String coordAgent) {
		this.coordAgent = coordAgent;
	}

	public Date getCoordDate() {
		return this.coordDate;
	}

	public void setCoordDate(Date coordDate) {
		this.coordDate = coordDate;
	}

	public String getCoordInspector() {
		return this.coordInspector;
	}

	public void setCoordInspector(String coordInspector) {
		this.coordInspector = coordInspector;
	}

	public String getCoordText() {
		return this.coordText;
	}

	public void setCoordText(String coordText) {
		this.coordText = coordText;
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

	public String getDcmStudyUid() {
		return this.dcmStudyUid;
	}

	public void setDcmStudyUid(String dcmStudyUid) {
		this.dcmStudyUid = dcmStudyUid;
	}

	public Byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Date getDirectionDate() {
		return this.directionDate;
	}

	public void setDirectionDate(Date directionDate) {
		this.directionDate = directionDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getExpose() {
		return this.expose;
	}

	public void setExpose(Integer expose) {
		this.expose = expose;
	}

	public Integer getFinanceId() {
		return this.financeId;
	}

	public void setFinanceId(Integer financeId) {
		this.financeId = financeId;
	}

	public String getHospitalUidFrom() {
		return this.hospitalUidFrom;
	}

	public void setHospitalUidFrom(String hospitalUidFrom) {
		this.hospitalUidFrom = hospitalUidFrom;
	}

	public Integer getIdx() {
		return this.idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Integer getIsUrgent() {
		return this.isUrgent;
	}

	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Byte getPacientInQueueType() {
		return this.pacientInQueueType;
	}

	public void setPacientInQueueType(Byte pacientInQueueType) {
		this.pacientInQueueType = pacientInQueueType;
	}

	public Integer getParentAction_id() {
		return this.parentAction_id;
	}

	public void setParentAction_id(Integer parentAction_id) {
		this.parentAction_id = parentAction_id;
	}

	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Date getPlannedEndDate() {
		return this.plannedEndDate;
	}

	public void setPlannedEndDate(Date plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public Integer getPrescriptionId() {
		return this.prescriptionId;
	}

	public void setPrescriptionId(Integer prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public Integer getSetPerson_id() {
		return this.setPerson_id;
	}

	public void setSetPerson_id(Integer setPerson_id) {
		this.setPerson_id = setPerson_id;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Double getUet() {
		return this.uet;
	}

	public void setUet(Double uet) {
		this.uet = uet;
	}

	public Integer getUuidId() {
		return this.uuidId;
	}

	public void setUuidId(Integer uuidId) {
		this.uuidId = uuidId;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}