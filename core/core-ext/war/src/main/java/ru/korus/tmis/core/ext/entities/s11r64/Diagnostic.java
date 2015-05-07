package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Diagnostic database table.
 * 
 */
@Entity
public class Diagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="action_id")
	private Integer actionId;

	@Column(name="character_id")
	private Integer characterId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private byte deleted;

	@Column(name="diagnosis_id")
	private Integer diagnosisId;

	private Integer diagnosisType_id;

	@Column(name="dispanser_id")
	private Integer dispanserId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name="event_id")
	private Integer eventId;

	private Integer healthGroup_id;

	private byte hospital;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	@Lob
	private String notes;

	@Column(name="person_id")
	private Integer personId;

	@Column(name="phase_id")
	private Integer phaseId;

	@Column(name="result_id")
	private Integer resultId;

	private byte sanatorium;

	@Temporal(TemporalType.TIMESTAMP)
	private Date setDate;

	@Column(name="speciality_id")
	private Integer specialityId;

	@Column(name="stage_id")
	private Integer stageId;

	private Integer traumaType_id;

	private Integer version;

	//bi-directional many-to-one association to RbAcheResult
	@ManyToOne
	private RbAcheResult rbAcheResult;

	public Diagnostic() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getCharacterId() {
		return this.characterId;
	}

	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
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

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public Integer getDiagnosisId() {
		return this.diagnosisId;
	}

	public void setDiagnosisId(Integer diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public Integer getDiagnosisType_id() {
		return this.diagnosisType_id;
	}

	public void setDiagnosisType_id(Integer diagnosisType_id) {
		this.diagnosisType_id = diagnosisType_id;
	}

	public Integer getDispanserId() {
		return this.dispanserId;
	}

	public void setDispanserId(Integer dispanserId) {
		this.dispanserId = dispanserId;
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

	public Integer getHealthGroup_id() {
		return this.healthGroup_id;
	}

	public void setHealthGroup_id(Integer healthGroup_id) {
		this.healthGroup_id = healthGroup_id;
	}

	public byte getHospital() {
		return this.hospital;
	}

	public void setHospital(byte hospital) {
		this.hospital = hospital;
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getPhaseId() {
		return this.phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getResultId() {
		return this.resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public byte getSanatorium() {
		return this.sanatorium;
	}

	public void setSanatorium(byte sanatorium) {
		this.sanatorium = sanatorium;
	}

	public Date getSetDate() {
		return this.setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public Integer getSpecialityId() {
		return this.specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}

	public Integer getStageId() {
		return this.stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public Integer getTraumaType_id() {
		return this.traumaType_id;
	}

	public void setTraumaType_id(Integer traumaType_id) {
		this.traumaType_id = traumaType_id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public RbAcheResult getRbAcheResult() {
		return this.rbAcheResult;
	}

	public void setRbAcheResult(RbAcheResult rbAcheResult) {
		this.rbAcheResult = rbAcheResult;
	}

}