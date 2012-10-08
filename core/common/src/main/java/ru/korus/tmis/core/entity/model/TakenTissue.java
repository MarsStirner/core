package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author belyaev
 */
@Entity
@Table(name = "TakenTissueJournal")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TakenTissue.findAll", query = "SELECT t FROM TakenTissue t")
})
public class TakenTissue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "externalId")
    private String externalId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetimeTaken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "execPerson_id", referencedColumnName = "id")
    @ManyToOne
    private Staff person;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne
    private RbUnit unit;
    @JoinColumn(name = "tissueType_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RbTissueType type;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patient;

    @Basic(optional = false)
    @NotNull
    @Column(name = "barcode")
    private int barcode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "period")
    private int period;

    public TakenTissue() {
    }

    public TakenTissue(Integer id) {
        this.id = id;
    }

    public TakenTissue(Integer id, String externalId, int amount, Date date, String note) {
        this.id = id;
        this.externalId = externalId;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDatetimeTaken() {
        return date;
    }

    public void setDatetimeTaken(Date datetimeTaken) {
        this.date = datetimeTaken;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Staff getPerson() {
        return person;
    }

    public void setPerson(Staff person) {
        this.person = person;
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    public RbTissueType getType() {
        return type;
    }

    public void setType(RbTissueType type) {
        this.type = type;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @OneToMany(mappedBy = "takenTissue", cascade = CascadeType.ALL)
    private List<Action> actions = new LinkedList<Action>();

    public List<Action> getActions() {
        if (actions == null) actions = new LinkedList<Action>();
        return actions;
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
        if (!(object instanceof TakenTissue)) {
            return false;
        }
        TakenTissue other = (TakenTissue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.TakenTissue[ id=" + id + " ]";
    }

}