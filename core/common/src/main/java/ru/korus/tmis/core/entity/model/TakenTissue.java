package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author belyaev
 *         Журнал забора тканей
 */
@Entity
@Table(name = "TakenTissueJournal")
@NamedQueries({@NamedQuery(name = "TakenTissue.findAll", query = "SELECT t FROM TakenTissue t")})
@NamedNativeQueries({@NamedNativeQuery(name = "TTJ.native.GET_ACTIONS_BY_TTJ_AND_LAB_CODE",
        query = "SELECT a.* \n" +
                "FROM Action a \n" +
                "INNER JOIN ActionProperty ap ON ap.action_id = a.id AND ap.deleted = 0 \n" +
                "INNER JOIN ActionPropertyType apt ON apt.id = ap.type_id AND apt.deleted = 0 \n" +
                "INNER JOIN rbTest tst ON tst.id = apt.test_id AND tst.deleted = 0 \n" +
                "INNER JOIN rbLaboratory_Test lab_tst ON lab_tst.test_id = tst.id \n" +
                "INNER JOIN rbLaboratory lab ON lab.id = lab_tst.master_id \n" +
                "INNER JOIN Action_TakenTissueJournal a_ttj ON a_ttj.action_id = a.id \n" +
                "WHERE a_ttj.takenTissueJournal_id = ?1 \n" +
                "AND lab.code = ?2 \n" +
                "GROUP BY a.id ",
        resultClass = Action.class)})
@XmlType(name = "takenTissue")
@XmlRootElement(name = "takenTissue")
public class TakenTissue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "externalId")
    private String externalId;

    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;

    @Basic(optional = false)
    @Column(name = "datetimeTaken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Basic(optional = false)
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
    @Column(name = "barcode")
    private Integer barcode;

    @Basic(optional = false)
    @Column(name = "period")
    private Integer period;
    @OneToMany(mappedBy = "takenTissue", cascade = CascadeType.ALL)
    private List<Action> actions = new LinkedList<Action>();

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

    public List<Action> getActions() {
        if (actions == null) {
            actions = new LinkedList<Action>();
        }
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