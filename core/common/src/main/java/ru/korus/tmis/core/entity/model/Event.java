package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Event", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
        })
@XmlType(name = "event")
@XmlRootElement(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "externalId")
    private String externalId;

    @ManyToOne
    @JoinColumn(name = "eventType_id",
                nullable = false)
    private EventType eventType;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "prevEventDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prevEventDate;

    @Basic(optional = false)
    @Column(name = "setDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date setDate;

    @ManyToOne
    @JoinColumn(name = "setPerson_id")
    private Staff assigner;

    @Column(name = "execDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date execDate;

    @ManyToOne
    @JoinColumn(name = "execPerson_id")
    private Staff executor;

    @Basic(optional = false)
    @Column(name = "isPrimary")
    private boolean isPrimary;

    @Basic(optional = false)
    @Column(name = "order")
    private boolean order;

    @Column(name = "result_id")
    private Integer resultId;

    @Column(name = "nextEventDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextEventDate;

    @Basic(optional = false)
    @Column(name = "payStatus")
    private int payStatus;

    @Column(name = "typeAsset_id")
    private Integer typeAssetId;

    @Basic(optional = false)
    @Lob
    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Staff curator;

    @ManyToOne
    @JoinColumn(name = "assistant_id")
    private Staff assistant;

    @Basic(optional = false)
    @Column(name = "pregnancyWeek")
    private int pregnancyWeek;

    @Column(name = "MES_id")
    private Integer mesId;

    @Column(name = "mesSpecification_id")
    private Integer mesSpecificationId;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (!patient.getEvents().contains(this)) {
            patient.getEvents().add(this);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Date getPrevEventDate() {
        return prevEventDate;
    }

    public void setPrevEventDate(Date prevEventDate) {
        this.prevEventDate = prevEventDate;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public Staff getAssigner() {
        return assigner;
    }

    public void setAssigner(Staff assigner) {
        this.assigner = assigner;
    }

    public Date getExecDate() {
        return execDate;
    }

    public void setExecDate(Date execDate) {
        this.execDate = execDate;
    }

    public Staff getExecutor() {
        return executor;
    }

    public void setExecutor(Staff executor) {
        this.executor = executor;
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public boolean getOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Date getNextEventDate() {
        return nextEventDate;
    }

    public void setNextEventDate(Date nextEventDate) {
        this.nextEventDate = nextEventDate;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getTypeAssetId() {
        return typeAssetId;
    }

    public void setTypeAssetId(Integer typeAssetId) {
        this.typeAssetId = typeAssetId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Staff getCurator() {
        return curator;
    }

    public void setCurator(Staff curator) {
        this.curator = curator;
    }

    public Staff getAssistant() {
        return assistant;
    }

    public void setAssistant(Staff assistant) {
        this.assistant = assistant;
    }

    public int getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(int pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }

    public Integer getMesId() {
        return mesId;
    }

    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public Integer getMesSpecificationId() {
        return mesSpecificationId;
    }

    public void setMesSpecificationId(Integer mesSpecificationId) {
        this.mesSpecificationId = mesSpecificationId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Event[id=" + id + "]";
    }
}
