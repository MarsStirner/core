package ru.korus.tmis.vmp.entities.s11r64;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Client_Quoting database table.
 * 
 */
@Entity
public class Client_Quoting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private Integer amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateRegistration;

    @Column(columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean deleted;

	@Temporal(TemporalType.TIMESTAMP)
	private Date directionDate;

    @ManyToOne
	@JoinColumn(name="event_id")
	private Event event;

	private String freeInput;

	private String identifier;

    @ManyToOne
	@JoinColumn(name="master_id")
	private Client client;

    @ManyToOne
    @JoinColumn(name="MKB", referencedColumnName ="diagID")
	private Mkb mkb;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	@Column(name="org_id")
	private Integer orgId;

	private Integer orgStructure_id;

	private Integer prevTalon_event_id;

	private String quotaTicket;

	private String regionCode;

	private Integer request;

	private Integer stage;

	private String statment;

	private Integer status;

	private Integer version;

	//bi-directional many-to-one association to VMPQuotaDetail
	@ManyToOne
	@JoinColumn(name="quotaDetails_id")
	private VMPQuotaDetail vmpquotaDetail;

	public Client_Quoting() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateRegistration() {
		return this.dateRegistration;
	}

	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDirectionDate() {
		return this.directionDate;
	}

	public void setDirectionDate(Date directionDate) {
		this.directionDate = directionDate;
	}

	public String getFreeInput() {
		return this.freeInput;
	}

	public void setFreeInput(String freeInput) {
		this.freeInput = freeInput;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

    public Mkb getMkb() {
        return mkb;
    }

    public void setMkb(Mkb mkb) {
        this.mkb = mkb;
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

	public Integer getPrevTalon_event_id() {
		return this.prevTalon_event_id;
	}

	public void setPrevTalon_event_id(Integer prevTalon_event_id) {
		this.prevTalon_event_id = prevTalon_event_id;
	}

	public String getQuotaTicket() {
		return this.quotaTicket;
	}

	public void setQuotaTicket(String quotaTicket) {
		this.quotaTicket = quotaTicket;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Integer getRequest() {
		return this.request;
	}

	public void setRequest(Integer request) {
		this.request = request;
	}

	public Integer getStage() {
		return this.stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getStatment() {
		return this.statment;
	}

	public void setStatment(String statment) {
		this.statment = statment;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public VMPQuotaDetail getVmpquotaDetail() {
		return this.vmpquotaDetail;
	}

	public void setVmpquotaDetail(VMPQuotaDetail vmpquotaDetail) {
		this.vmpquotaDetail = vmpquotaDetail;
	}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}