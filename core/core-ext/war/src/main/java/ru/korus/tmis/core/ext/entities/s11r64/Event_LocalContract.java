package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Event_LocalContract database table.
 * 
 */
@Entity
public class Event_LocalContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String coordAgent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date coordDate;

	private String coordInspector;

	@Lob
	private String coordText;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	@Temporal(TemporalType.DATE)
	private Date dateContract;

	private byte deleted;

	private Integer documentType_id;

	private String firstName;

	private String lastName;

	@Column(name="master_id")
	private Integer masterId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	private String number;

	private String numberContract;

	@Column(name="org_id")
	private Integer orgId;

	private String patrName;

	private String regAddress;

	private String serialLeft;

	private String serialRight;

	private double sumLimit;

	public Event_LocalContract() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public Date getDateContract() {
		return this.dateContract;
	}

	public void setDateContract(Date dateContract) {
		this.dateContract = dateContract;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public Integer getDocumentType_id() {
		return this.documentType_id;
	}

	public void setDocumentType_id(Integer documentType_id) {
		this.documentType_id = documentType_id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getMasterId() {
		return this.masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
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

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumberContract() {
		return this.numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getPatrName() {
		return this.patrName;
	}

	public void setPatrName(String patrName) {
		this.patrName = patrName;
	}

	public String getRegAddress() {
		return this.regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getSerialLeft() {
		return this.serialLeft;
	}

	public void setSerialLeft(String serialLeft) {
		this.serialLeft = serialLeft;
	}

	public String getSerialRight() {
		return this.serialRight;
	}

	public void setSerialRight(String serialRight) {
		this.serialRight = serialRight;
	}

	public double getSumLimit() {
		return this.sumLimit;
	}

	public void setSumLimit(double sumLimit) {
		this.sumLimit = sumLimit;
	}


}