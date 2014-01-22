package ru.korus.tmis.core.entity.model.fd;

import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ClientFlatDirectory")
@NamedQueries(
        {
                @NamedQuery(name = "ClientFlatDirectory.findAll", query = "SELECT cd FROM ClientFlatDirectory cd")
        })
@XmlType(name = "clientFlatDirectory")
@XmlRootElement(name = "clientFlatDirectory")
public class ClientFlatDirectory implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "clientFDProperty_id")
    private ClientFDProperty clientFDProperty;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "fdRecord_id")
    private FDRecord fdRecord;

    @Basic(optional = false)
    @Column(name = "dateStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Basic(optional = false)
    @Column(name = "dateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

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

    @ManyToOne
    @NotNull
    @JoinColumn(name = "client_id")
    private Patient patient;

    @Basic(optional = false)
    @Column(name = "comment")
    private String comment = "";

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public ClientFlatDirectory() {
    }

    public ClientFlatDirectory(Integer id) {
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

    public ClientFDProperty getClientFDProperty() {
        return clientFDProperty;
    }

    public void setClientFDProperty(ClientFDProperty clientFDProperty) {
        this.clientFDProperty = clientFDProperty;
    }

    public FDRecord getFDRecord() {
        return fdRecord;
    }

    public void setFDRecord(FDRecord fdRecord) {
        this.fdRecord = fdRecord;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof ClientFlatDirectory)) {
            return false;
        }
        ClientFlatDirectory other = (ClientFlatDirectory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.ClientFlatDirectory[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}