package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: mark2
 * Date: 3/16/12
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ClientPolicy")
@NamedQueries(
        {
                @NamedQuery(name = "ClientPolicy.findAll", query = "SELECT p FROM ClientPolicy p"),
                @NamedQuery(name = "ClientPolicy.findBySerialAndNumberAndTypeCode",
                        query = "SELECT p FROM ClientPolicy p " +
                                "WHERE p.serial = :serial " +
                                "AND p.number = :number " +
                                "AND p.policyType.code = :typeCode " +
                                "AND p.deleted = false"),
                @NamedQuery(name = "ClientPolicy.deleteAllClientPoliciesByType",
                        query = "UPDATE ClientPolicy p " +
                                "SET p.deleted = true " +
                                "WHERE p.patient.id = :patientId " +
                                "AND p.policyType.code = :policyTypeCode")
        })
@XmlType(name = "policy")
@XmlRootElement(name = "policy")
public class ClientPolicy implements Serializable, Cloneable {

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
    @Column(name = "serial")
    private String serial;

    @Basic(optional = false)
    @Column(name = "number")
    private String number;

    @Basic(optional = false)
    @Column(name = "begDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Basic(optional = true)
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "note")
    private String note;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    /*
    * END DB FIELDS
    * */

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    /*
    * Overrides
    * */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClientPolicy)) {
            return false;
        }
        ClientPolicy other = (ClientPolicy) object;
        if (this.id == null && other.id == null && this != other) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ClientPolicy[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Детальное описание полиса
     * @return строка с описанием
     */
    public String getInfoString(){
        return new StringBuilder("Policy[id=").append(id)
                .append(" Ser.:\"").append(serial)
                .append("\" Num.:\"").append(number)
                .append("\" typeCode:").append(policyType.getCode())
                .append(" insurer:").append(insurer != null ? insurer.getInfisCode() : "null")
                .append(']').toString();
    }

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
        if (!patient.getClientPolicies().contains(this)) {
            patient.getClientPolicies().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "insurer_id")
    private Organisation insurer;

    public Organisation getInsurer() {
        return insurer;
    }

    public void setInsurer(Organisation insurer) {
        this.insurer = insurer;
    }

    @ManyToOne
    @JoinColumn(name = "policyType_id", nullable = true)
    private RbPolicyType policyType;

    public RbPolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(RbPolicyType policyType) {
        this.policyType = policyType;
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////
}
