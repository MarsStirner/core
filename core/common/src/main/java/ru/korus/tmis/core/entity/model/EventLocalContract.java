package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the EventLocalContract database table.
 * 
 */
@Entity
@Table(name="Event_LocalContract")
public class EventLocalContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date birthDate;

	@Column(nullable=false, length=128)
	private String coordAgent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date coordDate;

	@Column(nullable=false, length=128)
	private String coordInspector;

	@Lob
	@Column(nullable=false)
	private String coordText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date createDatetime;

    @ManyToOne
    @JoinColumn(name="createPerson_id")
    private Staff createPerson;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dateContract;

	@Column(nullable=false)
	private byte deleted;

    @ManyToOne
    @JoinColumn(name="documentType_id")
	private RbDocumentType rbDocumentType;

	@Column(nullable=false, length=30)
	private String firstName;

	@Column(nullable=false, length=30)
	private String lastName;

    @ManyToOne
    @JoinColumn(name="master_id", nullable=false)
	private Event event;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name="modifyPerson_id")
	private Staff modifyPerson;

	@Column(nullable=false, length=16)
	private String number;

	@Column(nullable=false, length=64)
	private String numberContract;

    @ManyToOne
    @JoinColumn(name="org_id")
	private Organisation organisation;

	@Column(nullable=false, length=30)
	private String patrName;

	@Column(nullable=false, length=64)
	private String regAddress;

	@Column(nullable=false, length=8)
	private String serialLeft;

	@Column(nullable=false, length=8)
	private String serialRight;

	@Column(nullable=false)
	private double sumLimit;

	public EventLocalContract() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public Staff getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(Staff createPerson_id) {
		this.createPerson = createPerson_id;
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

	public RbDocumentType getRbDocumentType() {
		return this.rbDocumentType;
	}

	public void setRbDocumentType(int RbDocumentType) {
		this.rbDocumentType = rbDocumentType;
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

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Staff getModifyPerson() {
		return this.modifyPerson;
	}

	public void setModifyPerson_id(Staff modifyPerson) {
		this.modifyPerson = modifyPerson;
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

	public Organisation getOrganisation() {
		return this.organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
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