package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Event database table.
 * 
 */
@Entity
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="assistant_id")
	private Integer assistantId;

    @ManyToOne
    @JoinColumn(name="client_id")
	private Client client;

	@Column(name="contract_id")
	private Integer contractId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	@Column(name="curator_id")
	private Integer curatorId;

	private byte deleted;

	private Integer eventType_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date execDate;

	private Integer execPerson_id;

	private String externalId;

	private byte isPrimary;

	@Column(name="lpu_transfer")
	private String lpuTransfer;

	private Integer MES_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nextEventDate;

	@Lob
	private String note;

	private byte order;

	@Column(name="org_id")
	private Integer orgId;

	private Integer orgStructure_id;

	private Integer payStatus;

	private Integer pregnancyWeek;

	@Temporal(TemporalType.TIMESTAMP)
	private Date prevEventDate;

	private byte privilege;

	@Column(name="result_id")
	private Integer resultId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date setDate;

	private Integer setPerson_id;

	private Integer typeAsset_id;

	private byte urgent;

	@Column(name="uuid_id")
	private Integer uuidId;

	private Integer version;

	//bi-directional many-to-one association to RbAcheResult
	@ManyToOne
	private RbAcheResult rbAcheResult;

	//bi-directional many-to-one association to RbMesSpecification
	@ManyToOne
	@JoinColumn(name="mesSpecification_id")
	private RbMesSpecification rbMesSpecification;

	//bi-directional many-to-one association to Event_LocalContract
	@ManyToOne
	@JoinColumn(name="localContract_id")
	private Event_LocalContract eventLocalContract;

	public Event() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssistantId() {
		return this.assistantId;
	}

	public void setAssistantId(Integer assistantId) {
		this.assistantId = assistantId;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClientId(Client client) {
		this.client = client;
	}

	public Integer getContractId() {
		return this.contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
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

	public Integer getCuratorId() {
		return this.curatorId;
	}

	public void setCuratorId(Integer curatorId) {
		this.curatorId = curatorId;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public Integer getEventType_id() {
		return this.eventType_id;
	}

	public void setEventType_id(Integer eventType_id) {
		this.eventType_id = eventType_id;
	}

	public Date getExecDate() {
		return this.execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	public Integer getExecPerson_id() {
		return this.execPerson_id;
	}

	public void setExecPerson_id(Integer execPerson_id) {
		this.execPerson_id = execPerson_id;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public byte getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(byte isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getLpuTransfer() {
		return this.lpuTransfer;
	}

	public void setLpuTransfer(String lpuTransfer) {
		this.lpuTransfer = lpuTransfer;
	}

	public Integer getMES_id() {
		return this.MES_id;
	}

	public void setMES_id(Integer MES_id) {
		this.MES_id = MES_id;
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

	public Date getNextEventDate() {
		return this.nextEventDate;
	}

	public void setNextEventDate(Date nextEventDate) {
		this.nextEventDate = nextEventDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public byte getOrder() {
		return this.order;
	}

	public void setOrder(byte order) {
		this.order = order;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgStructure_id() {
		return this.orgStructure_id;
	}

	public void setOrgStructure_id(Integer orgStructure_id) {
		this.orgStructure_id = orgStructure_id;
	}

	public Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPregnancyWeek() {
		return this.pregnancyWeek;
	}

	public void setPregnancyWeek(Integer pregnancyWeek) {
		this.pregnancyWeek = pregnancyWeek;
	}

	public Date getPrevEventDate() {
		return this.prevEventDate;
	}

	public void setPrevEventDate(Date prevEventDate) {
		this.prevEventDate = prevEventDate;
	}

	public byte getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(byte privilege) {
		this.privilege = privilege;
	}

	public Integer getResultId() {
		return this.resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public Date getSetDate() {
		return this.setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public Integer getSetPerson_id() {
		return this.setPerson_id;
	}

	public void setSetPerson_id(Integer setPerson_id) {
		this.setPerson_id = setPerson_id;
	}

	public Integer getTypeAsset_id() {
		return this.typeAsset_id;
	}

	public void setTypeAsset_id(Integer typeAsset_id) {
		this.typeAsset_id = typeAsset_id;
	}

	public byte getUrgent() {
		return this.urgent;
	}

	public void setUrgent(byte urgent) {
		this.urgent = urgent;
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

	public RbAcheResult getRbAcheResult() {
		return this.rbAcheResult;
	}

	public void setRbAcheResult(RbAcheResult rbAcheResult) {
		this.rbAcheResult = rbAcheResult;
	}

	public RbMesSpecification getRbMesSpecification() {
		return this.rbMesSpecification;
	}

	public void setRbMesSpecification(RbMesSpecification rbMesSpecification) {
		this.rbMesSpecification = rbMesSpecification;
	}

	public Event_LocalContract getEventLocalContract() {
		return this.eventLocalContract;
	}

	public void setEventLocalContract(Event_LocalContract eventLocalContract) {
		this.eventLocalContract = eventLocalContract;
	}


}