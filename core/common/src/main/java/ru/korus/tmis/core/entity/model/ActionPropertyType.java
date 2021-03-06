package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import ru.korus.tmis.util.PublicClonable;
@Entity
@Table(name = "ActionPropertyType")
@NamedQueries(
{
        @NamedQuery(name = "ActionPropertyType.findAll", query = "SELECT a FROM ActionPropertyType a")
})
public class ActionPropertyType implements Serializable, PublicClonable<ActionPropertyType> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "actionType_id")
    private ActionType actionType;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @Column(name = "template_id")
    private Integer templateId;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "descr")
    private String descr;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private RbUnit unit;

    @Basic(optional = false)
    @Column(name = "typeName")
    private String typeName;

    @Basic(optional = false)
    @Lob
    @Column(name = "valueDomain")
    private String valueDomain;

    @Basic(optional = false)
    @Column(name = "defaultValue")
    private String defaultValue;

    @Basic(optional = false)
    @Column(name = "isVector")
    private boolean isVector;

    @Basic(optional = false)
    @Column(name = "norm")
    private String norm;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Basic(optional = false)
    @Column(name = "age_bu")
    private int age_bu;

    @Basic(optional = false)
    @Column(name = "age_bc")
    private int age_bc;

    @Basic(optional = false)
    @Column(name = "age_eu")
    private int age_eu;

    @Basic(optional = false)
    @Column(name = "age_ec")
    private int age_ec;

    @Basic(optional = false)
    @Column(name = "penalty")
    private int penalty;

    @Basic(optional = false)
    @Column(name = "visibleInJobTicket")
    private boolean visibleInJobTicket;

    @Basic(optional = false)
    @Column(name = "isAssignable")
    private boolean isAssignable;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private RbTest test;

    @Basic(optional = false)
    @Column(name = "defaultEvaluation")
    private boolean defaultEvaluation;

    @Basic(optional = false)
    @Column(name = "mandatory")
    private boolean mandatory;

    @Basic(optional = false)
    @Column(name = "readOnly")
    private boolean readOnly;

    @OneToMany(mappedBy = "objectType", cascade = CascadeType.ALL)
    private Set<ActionPropertyRelation> actionPropertyRelation;

    // //////////////////////////////////////////////////////////////////////////
    // Custom stuff
    // //////////////////////////////////////////////////////////////////////////

    /**
     * @return the mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @param mandatory
     *            the mandatory to set
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly
     *            the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Transient
    public boolean isConstructor() {
        return typeName != null && typeName.equals("Constructor");
    }

    @Transient
    public String getConstructorValueDomain() {
        return isConstructor() ? valueDomain : null;
    }

    // //////////////////////////////////////////////////////////////////////////
    // End of custom stuff
    // //////////////////////////////////////////////////////////////////////////

    public ActionPropertyType() {
    }

    public ActionPropertyType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public RbUnit getUnit() {
        if (unit != null) {
            return unit;
        } else {
            return RbUnit.getEmptyUnit();
        }
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValueDomain() {
        return valueDomain;
    }

    public void setValueDomain(String valueDomain) {
        this.valueDomain = valueDomain;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsVector() {
        return isVector;
    }

    public void setIsVector(boolean isVector) {
        this.isVector = isVector;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAge_bu() {
        return age_bu;
    }

    public void setAge_bu(int age_bu) {
        this.age_bu = age_bu;
    }

    public int getAge_bc() {
        return age_bc;
    }

    public void setAge_bc(int age_bc) {
        this.age_bc = age_bc;
    }

    public int getAge_eu() {
        return age_eu;
    }

    public void setAge_eu(int age_eu) {
        this.age_eu = age_eu;
    }

    public int getAge_ec() {
        return age_ec;
    }

    public void setAge_ec(int age_ec) {
        this.age_ec = age_ec;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public boolean getVisibleInJobTicket() {
        return visibleInJobTicket;
    }

    public void setVisibleInJobTicket(boolean visibleInJobTicket) {
        this.visibleInJobTicket = visibleInJobTicket;
    }

    public boolean getIsAssignable() {
        return isAssignable;
    }

    public void setIsAssignable(boolean isAssignable) {
        this.isAssignable = isAssignable;
    }

    public RbTest getTest() {
        return test;
    }

    public void setTest(RbTest test) {
        this.test = test;
    }

    public boolean getDefaultEvaluation() {
        return defaultEvaluation;
    }

    public void setDefaultEvaluation(boolean defaultEvaluation) {
        this.defaultEvaluation = defaultEvaluation;
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
        if (!(object instanceof ActionPropertyType)) {
            return false;
        }
        ActionPropertyType other = (ActionPropertyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ActionPropertyType[id=" + id + "]";
    }

    public ActionPropertyType clone() {
        ActionPropertyType that = new ActionPropertyType(id);

        that.actionType = actionType;
        that.age = age;
        that.defaultEvaluation = defaultEvaluation;
        that.defaultValue = defaultValue;
        that.deleted = deleted;
        that.descr = descr;
        that.idx = idx;
        that.isAssignable = isAssignable;
        that.isVector = isVector;
        that.name = name;
        that.norm = norm;
        that.penalty = penalty;
        that.sex = sex;
        that.templateId = templateId;
        that.test = test;
        that.typeName = typeName;
        that.unit = unit;
        that.valueDomain = valueDomain;
        that.visibleInJobTicket = visibleInJobTicket;

        return that;
    }

    public Set<ActionPropertyRelation> getActionPropertyRelation() {
        return actionPropertyRelation;
    }

    public void setActionPropertyRelation(Set<ActionPropertyRelation> actionPropertyRelation) {
        this.actionPropertyRelation = actionPropertyRelation;
    }
}
