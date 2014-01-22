package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * User: eupatov<br>
 * Date: 07.02.13 at 19:18<br>
 * Company Korus Consulting IT<br>
 */

@Entity
@Table(name = "ClientAttach")
@NamedQueries(
        {
                @NamedQuery(name = "ClientAttach.findAll", query = "SELECT p FROM ClientAttach p")
        })
@XmlType(name = "ClientAttach")
@XmlRootElement(name = "ClientAttach")

public class ClientAttach implements Serializable, Cloneable {

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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Patient client;

    @ManyToOne
    @JoinColumn(name = "attachType_id")
    private RbAttachType attachType;

    @ManyToOne
    @JoinColumn(name = "LPU_id")
    private Organisation LPU;

    @ManyToOne
    @JoinColumn(name = "orgStructure_id")
    private OrgStructure orgStructure;

    @Basic(optional = true)
    @Column(name = "begDate")
    @Temporal(TemporalType.DATE)
    private Date begDate = new Date();

    @Basic(optional = true)
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate = new Date();

    @ManyToOne
    @JoinColumn(name = "document_id")
    private ClientDocument document;

    public ClientAttach() {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Patient getClient() {
        return client;
    }

    public void setClient(Patient client) {
        this.client = client;
    }

    public RbAttachType getAttachType() {
        return attachType;
    }

    public void setAttachType(RbAttachType attachType) {
        this.attachType = attachType;
    }

    public Organisation getLPU() {
        return LPU;
    }

    public void setLPU(Organisation LPU) {
        this.LPU = LPU;
    }

    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
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

    public ClientDocument getDocument() {
        return document;
    }

    public void setDocument(ClientDocument document) {
        this.document = document;
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
        if (!(object instanceof ClientAttach)) {
            return false;
        }
        ClientAttach other = (ClientAttach) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ClientAttach[id=" + id + "]";
    }
}
