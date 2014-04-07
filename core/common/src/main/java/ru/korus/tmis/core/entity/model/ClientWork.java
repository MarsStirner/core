/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * @author s0
 */
@Entity
@Table(name = "ClientWork")
@NamedQueries({
        @NamedQuery(name = "ClientWork.findAll", query = "SELECT c FROM ClientWork c"),
        @NamedQuery(name = "ClientWork.findById", query = "SELECT c FROM ClientWork c WHERE c.id = :id"),
        @NamedQuery(name = "ClientWork.findByCreateDatetime", query = "SELECT c FROM ClientWork c WHERE c.createDatetime = :createDatetime"),
        @NamedQuery(name = "ClientWork.findByCreatePersonid", query = "SELECT c FROM ClientWork c WHERE c.createPersonid = :createPersonid"),
        @NamedQuery(name = "ClientWork.findByModifyDatetime", query = "SELECT c FROM ClientWork c WHERE c.modifyDatetime = :modifyDatetime"),
        @NamedQuery(name = "ClientWork.findByModifyPersonid", query = "SELECT c FROM ClientWork c WHERE c.modifyPersonid = :modifyPersonid"),
        @NamedQuery(name = "ClientWork.findByDeleted", query = "SELECT c FROM ClientWork c WHERE c.deleted = :deleted"),
        @NamedQuery(name = "ClientWork.findByClientId", query = "SELECT c FROM ClientWork c WHERE c.patient.id = :clientId"),
        @NamedQuery(name = "ClientWork.findByOrgId", query = "SELECT c FROM ClientWork c WHERE c.organisation.id = :orgId"),
        @NamedQuery(name = "ClientWork.findByFreeInput", query = "SELECT c FROM ClientWork c WHERE c.freeInput = :freeInput"),
        @NamedQuery(name = "ClientWork.findByPost", query = "SELECT c FROM ClientWork c WHERE c.post = :post"),
        @NamedQuery(name = "ClientWork.findByStage", query = "SELECT c FROM ClientWork c WHERE c.stage = :stage"),
        @NamedQuery(name = "ClientWork.findByOkved", query = "SELECT c FROM ClientWork c WHERE c.okved = :okved")})
public class ClientWork implements Serializable, Cloneable {
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
    @Column(name = "createPerson_id")
    private Integer createPersonid;
    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;
    @Column(name = "modifyPerson_id")
    private Integer modifyPersonid;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organisation organisation;
    @Basic(optional = false)
    @Column(name = "freeInput")
    private String freeInput;
    @Basic(optional = false)
    @Column(name = "post")
    private String post;
    @Basic(optional = false)
    @Column(name = "stage")
    private short stage;
    @Basic(optional = false)
    @Column(name = "OKVED")
    private String okved;

    //@ManyToOne
    @Column(name = "arm_id")
    //@JoinColumn(name = "arm_id")
    //@NotFound( action = NotFoundAction.IGNORE )
    private Integer armId;

    //@ManyToOne
    @Column(name = "rank_id")
    //@JoinColumn(name = "rank_id")
    //@NotFound( action = NotFoundAction.IGNORE )
    private Integer rankId;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;


    @OneToMany(mappedBy = "clientWork", cascade = CascadeType.ALL)
    private List<ClientWorkHurt> clientWorkHurts = new LinkedList<ClientWorkHurt>();

    public List<ClientWorkHurt> getClientWorkHurts() {
        return clientWorkHurts;
    }


    public ClientWork() {
    }

    public ClientWork(Integer id) {
        this.id = id;
    }

    public ClientWork(Integer id, Date createDatetime, Date modifyDatetime, boolean deleted, Patient patient, String freeInput, String post, short stage, String okved) {
        this.id = id;
        this.createDatetime = createDatetime;
        this.modifyDatetime = modifyDatetime;
        this.deleted = deleted;
        this.patient = patient;
        this.freeInput = freeInput;
        this.post = post;
        this.stage = stage;
        this.okved = okved;
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

    public Integer getCreatePersonid() {
        return createPersonid;
    }

    public void setCreatePersonid(Integer createPersonid) {
        this.createPersonid = createPersonid;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Integer getModifyPersonid() {
        return modifyPersonid;
    }

    public void setModifyPersonid(Integer modifyPersonid) {
        this.modifyPersonid = modifyPersonid;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (!patient.getClientWorks().contains(this) || this.id == null) {//TODO: не понимаю, почему здесь не работает contains, когда идет добавление двух новых соцстатусов
            patient.getClientWorks().add(this);
        }
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public String getFreeInput() {
        return freeInput;
    }

    public void setFreeInput(String freeInput) {
        this.freeInput = freeInput;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public short getStage() {
        return stage;
    }

    public void setStage(short stage) {
        this.stage = stage;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public Integer getArmId() {
        return armId;
    }

    public void setArmId(Integer armId) {   //FDRecord
        this.armId = armId;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
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
        if (!(object instanceof ClientWork)) {
            return false;
        }
        ClientWork other = (ClientWork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ClientWork[ id=" + id + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
