package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Client database table.
 * 
 */
@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String birthPlace;

	@Temporal(TemporalType.DATE)
	private Date bloodDate;

	private String bloodKell;

	@Lob
	private String bloodNotes;

	private Integer bloodType_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private Byte deleted;

	private String embryonalPeriodWeek;

	private String firstName;

	private String growth;

	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	@Lob
	private String notes;

	private String patrName;

	private Byte sex;

	private String snils;

	@Column(name="uuid_id")
	private Integer uuidId;

	private Integer version;

	private String weight;

	//bi-directional many-to-one association to RbBloodPhenotype
	@ManyToOne
	@JoinColumn(name="bloodPhenotype_id")
	private RbBloodPhenotype rbBloodPhenotype;

	public Client() {
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

	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Date getBloodDate() {
		return this.bloodDate;
	}

	public void setBloodDate(Date bloodDate) {
		this.bloodDate = bloodDate;
	}

	public String getBloodKell() {
		return this.bloodKell;
	}

	public void setBloodKell(String bloodKell) {
		this.bloodKell = bloodKell;
	}

	public String getBloodNotes() {
		return this.bloodNotes;
	}

	public void setBloodNotes(String bloodNotes) {
		this.bloodNotes = bloodNotes;
	}

	public Integer getBloodType_id() {
		return this.bloodType_id;
	}

	public void setBloodType_id(Integer bloodType_id) {
		this.bloodType_id = bloodType_id;
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

	public Byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public String getEmbryonalPeriodWeek() {
		return this.embryonalPeriodWeek;
	}

	public void setEmbryonalPeriodWeek(String embryonalPeriodWeek) {
		this.embryonalPeriodWeek = embryonalPeriodWeek;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGrowth() {
		return this.growth;
	}

	public void setGrowth(String growth) {
		this.growth = growth;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPatrName() {
		return this.patrName;
	}

	public void setPatrName(String patrName) {
		this.patrName = patrName;
	}

	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getSnils() {
		return this.snils;
	}

	public void setSnils(String snils) {
		this.snils = snils;
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

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}