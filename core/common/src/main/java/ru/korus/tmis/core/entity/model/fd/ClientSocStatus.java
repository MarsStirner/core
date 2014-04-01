/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.core.entity.model.fd;

import ru.korus.tmis.core.entity.model.*;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author s0
 */
@Entity
@Table(name = "ClientSocStatus")
@NamedQueries({
    @NamedQuery(name = "ClientSocStatus.findAll", query = "SELECT c FROM ClientSocStatus c"),
    @NamedQuery(name = "ClientSocStatus.findById", query = "SELECT c FROM ClientSocStatus c WHERE c.id = :id"),
    @NamedQuery(name = "ClientSocStatus.findByCreateDatetime", query = "SELECT c FROM ClientSocStatus c WHERE c.createDatetime = :createDatetime"),
    @NamedQuery(name = "ClientSocStatus.findByModifyDatetime", query = "SELECT c FROM ClientSocStatus c WHERE c.modifyDatetime = :modifyDatetime"),
    @NamedQuery(name = "ClientSocStatus.findByDeleted", query = "SELECT c FROM ClientSocStatus c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "ClientSocStatus.findByBegDate", query = "SELECT c FROM ClientSocStatus c WHERE c.begDate = :begDate"),
    @NamedQuery(name = "ClientSocStatus.findByEndDate", query = "SELECT c FROM ClientSocStatus c WHERE c.endDate = :endDate")
    })
public class ClientSocStatus implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public static final String PRIVILEGE_CODE = "5";

    public static final String SOCIAL_STATUS_CODE = "1";

    public static final String DISABILITY_CODE = "2";

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
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "benefitCategory_id")
    private FDRecord benefitCategoryId;

    @Basic(optional = false)
    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "socStatusClass_id")
    private RbSocStatusClass socStatusClass;
    

    @ManyToOne
    @JoinColumn(name = "socStatusType_id")
    private RbSocStatusType socStatusType;
    
    @Basic(optional = false)
    @Column(name = "begDate")
    @Temporal(TemporalType.DATE)
    private Date begDate;
    
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private ClientDocument document;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public ClientDocument getDocument() {
        return document;
    }

    public void setDocument(ClientDocument document) {
        this.document = document;
    }

    public ClientSocStatus() {
    }

    public ClientSocStatus(Integer id) {
        this.id = id;
    }

    public ClientSocStatus(Integer id, Date createDatetime, Date modifyDatetime, boolean deleted, Patient patient, RbSocStatusType socStatusType, Date begDate) {
        this.id = id;
        this.createDatetime = createDatetime;
        this.modifyDatetime = modifyDatetime;
        this.deleted = deleted;
        this.patient = patient;
        this.socStatusType= socStatusType;
        this.begDate = begDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FDRecord getBenefitCategoryId() {
        return benefitCategoryId;
    }

    public void setBenefitCategoryId(FDRecord benefitCategoryId) {
        this.benefitCategoryId = benefitCategoryId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public boolean getDeleted() {
        return deleted;
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

	public Staff getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(Staff modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		if (!patient.getClientSocStatuses().contains(this) || this.id == null) {//TODO: не понимаю, почему здесь не работает contains, когда идет добавление двух новых соцстатусов
			patient.getClientSocStatuses().add(this);
		}
	}

	public RbSocStatusClass getSocStatusClass() {
		return socStatusClass;
	}

	public void setSocStatusClass(RbSocStatusClass socStatusClass) {
		this.socStatusClass = socStatusClass;
	}

	public RbSocStatusType getSocStatusType() {
		return socStatusType;
	}

	public void setSocStatusType(RbSocStatusType socStatusType) {
		this.socStatusType = socStatusType;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientSocStatus)) {
            return false;
        }
        ClientSocStatus other = (ClientSocStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.ClientSocStatus[ id=" + id + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
