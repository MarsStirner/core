package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity для работы из ORM с таблицей s11r64.Client_Quoting.
 * @author mmakankov
 * @since 1.0.0.48
 */
@Entity
@Table(name = "Client_Quoting", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "ClientQuoting.findAll", query = "SELECT cq FROM ClientQuoting cq")
        })
@XmlType(name = "сlientQuoting")
@XmlRootElement(name = "сlientQuoting")
public class ClientQuoting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @ManyToOne
    @JoinColumn(name = "MKB", referencedColumnName = "DiagID")
    private Mkb mkb;

    @Basic(optional = false)
    @Column(name = "amount")
    private int amount = 0;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "dateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @Basic(optional = false)
    @Column(name = "dateRegistration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistration;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "directionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date directionDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Basic(optional = false)
    @Column(name = "freeInput")
    private String freeInput;

    @Basic(optional = false)
    @Column(name = "identifier")
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Patient master;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @ManyToOne
    @JoinColumn(name = "orgStructure_id")
    private OrgStructure orgStructure;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "pacientModel_id")
    private Integer pacientModelId = 0;

    @Column(name = "quotaTicket")
    private String quotaTicket;

    @ManyToOne
    @JoinColumn(name = "quotaType_id")
    private QuotaType quotaType;

    @Column(name = "regionCode")
    private String regionCode;

    @Column(name = "request")
    private int request;

    @Column(name = "stage")
    private int stage;

    @Column(name = "statment")
    private String statement;

    @ManyToOne
    @JoinColumn(name = "status")
    private RbQuotaStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Mkb getMkb() {
        return mkb;
    }

    public void setMkb(Mkb mkb) {
        this.mkb = mkb;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDirectionDate() {
        return directionDate;
    }

    public void setDirectionDate(Date directionDate) {
        this.directionDate = directionDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getFreeInput() {
        return freeInput;
    }

    public void setFreeInput(String freeInput) {
        this.freeInput = freeInput;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Patient getMaster() {
        return master;
    }

    public void setMaster(Patient master) {
        this.master = master;
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

    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getPacientModelId() {
        return pacientModelId;
    }

    public void setPacientModelId(Integer pacientModelId) {
        this.pacientModelId = pacientModelId;
    }

    public String getQuotaTicket() {
        return quotaTicket;
    }

    public void setQuotaTicket(String quotaTicket) {
        this.quotaTicket = quotaTicket;
    }

    public QuotaType getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(QuotaType quotaType) {
        this.quotaType = quotaType;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public RbQuotaStatus getStatus() {
        return status;
    }

    public void setStatus(RbQuotaStatus status) {
        this.status = status;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    @Column(name = "treatment_id")
    private Integer treatmentId = 0;

    //TODO: Попросить добавить поле version
    /*
    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    */



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientQuoting that = (ClientQuoting) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ClientQuoting[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static ClientQuoting clone(ClientQuoting self) throws CloneNotSupportedException {
        ClientQuoting newClientQuoting = (ClientQuoting) self.clone();
        return newClientQuoting;
    }
}
