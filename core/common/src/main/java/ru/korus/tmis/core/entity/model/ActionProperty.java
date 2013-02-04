package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ActionProperty", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "ActionProperty.findAll", query = "SELECT a FROM ActionProperty a")
        })
@XmlType(name = "actionProperty")
@XmlRootElement(name = "actionProperty")
public class ActionProperty
        implements Serializable, Cloneable {

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
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ActionPropertyType actionPropertyType;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private RbUnit unit;

    @Basic(optional = false)
    @Column(name = "norm")
    private String norm;

    @Basic(optional = false)
    @Column(name = "isAssigned")
    private boolean isAssigned = false;

    @Column(name = "evaluation")
    private Boolean evaluation;

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
        if (!action.getActionProperties().contains(this)) {
            action.getActionProperties().add(this);
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @Transient
    private Class valueClass;

    @Transient
    private static Map<String, Class> valueTypeMap =
            new HashMap<String, Class>();

    static {
        valueTypeMap.put("Action", APValueAction.class);
        valueTypeMap.put("OperationType", APValueInteger.class);
        valueTypeMap.put("AnalysisStatus", APValueAnalysisStatus.class);
        valueTypeMap.put("Constructor", APValueString.class);
        valueTypeMap.put("Date", APValueDate.class);
        valueTypeMap.put("Double", APValueDouble.class);
        valueTypeMap.put("HospitalBed", APValueHospitalBed.class);
        valueTypeMap.put("Html", APValueString.class);
        valueTypeMap.put("Integer", APValueInteger.class);
        valueTypeMap.put("JobTicket", APValueJobTicket.class);
        valueTypeMap.put("OrgStructure", APValueOrgStructure.class);
        valueTypeMap.put("Organisation", APValueOrganisation.class);
        valueTypeMap.put("Person", APValuePerson.class);
        valueTypeMap.put("String", APValueString.class);
        valueTypeMap.put("Text", APValueString.class);
        valueTypeMap.put("Time", APValueTime.class);
        valueTypeMap.put("RLS", APValueRLS.class);
        valueTypeMap.put("MKB", APValueMKB.class);
        valueTypeMap.put("Image", APValueImage.class);
        valueTypeMap.put("Жалобы", APValueString.class);
        valueTypeMap.put("DiagnosticEpicrisisPartitional", APValueString.class);
        valueTypeMap.put("FlatDictionary", APValueFlatDirectory.class);
        valueTypeMap.put("FlatDirectory", APValueFlatDirectory.class);
        valueTypeMap.put("Legal_representative_id", APValueInteger.class);//че за тип такой?
        valueTypeMap.put("rbReasonOfAbsence", APValueRbReasonOfAbsence.class); //Причина отсутствия

    }

    public Class getValueClass() {
        return valueClass;
    }

    public void setValueClass(final Class valueClass) {
        this.valueClass = valueClass;
    }

    ////////////////////////////////////////////////////////////////////////////

    @Transient
    private int idx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(final int idx) {
        this.idx = idx;
    }

    ////////////////////////////////////////////////////////////////////////////

    @PostLoad
    private void onPostLoad() {
        updateValueClass();

        if (actionPropertyType == null) {
            idx = 0;
            return;
        }

        idx = actionPropertyType.getIdx();

//        if (unit == null &&
//                actionPropertyType.getUnit() != RbUnit.getEmptyUnit()) {
//            unit = actionPropertyType.getUnit();
//        }

        if (norm == null) {
            norm = actionPropertyType.getNorm();
        }
    }

    private void updateValueClass() {
        if (actionPropertyType == null) {
            valueClass = null;
            return;
        }

        String propertyType = actionPropertyType.getTypeName();

        if ("Reference".equals(propertyType)) {
            propertyType = actionPropertyType.getValueDomain();
        }

        if (valueTypeMap.containsKey(propertyType)) {
            valueClass = valueTypeMap.get(propertyType);
        } else {
            valueClass = null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @PrePersist
    private void onPrePersist() {
        if (actionPropertyType != null) {
            if (unit == null &&
                    actionPropertyType.getUnit() != RbUnit.getEmptyUnit()) {
                unit = actionPropertyType.getUnit();
            }
            if (norm == null) {
                norm = actionPropertyType.getNorm();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public ActionProperty() {
    }

    public ActionProperty(Integer id) {
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

    public ActionPropertyType getType() {
        return actionPropertyType;
    }

    public void setType(ActionPropertyType actionPropertyType) {
        this.actionPropertyType = actionPropertyType;
        updateValueClass();
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public Boolean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Boolean evaluation) {
        this.evaluation = evaluation;
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
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionProperty)) {
            return false;
        }
        ActionProperty other = (ActionProperty) object;

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
        return "ru.korus.tmis.core.entity.model.ActionProperty[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
