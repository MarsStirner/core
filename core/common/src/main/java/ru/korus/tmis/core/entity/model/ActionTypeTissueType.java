package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * ActionTypeTissueType Entity
 * Author: idmitriev Systema-Soft
 * Date: 2/18/13 10:47 PM
 * Since: 1.0.0.64
 */
@Entity
@Table(name = "ActionType_TissueType")
@NamedQueries(
        {
                @NamedQuery(name = "ActionTypeTissueType.findAll", query = "SELECT attp FROM ActionTypeTissueType attp")
        })
@XmlType(name = "ActionTypeTissueType")
@XmlRootElement(name = "ActionTypeTissueType")
public class ActionTypeTissueType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="master_id", updatable = false, insertable = false)
    private ActionType actionType;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx = 0;

    @ManyToOne
    @JoinColumn(name = "tissueType_id")
    private RbTissueType tissueType;

    @Basic(optional = false)
    @Column(name = "amount")
    private int amount = 0;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private RbUnit unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RbTissueType getTissueType() {
        return tissueType;
    }

    public void setTissueType(RbTissueType tissueType) {
        this.tissueType = tissueType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionTypeTissueType that = (ActionTypeTissueType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ActionTypeTissueType[id=" + id + "]";
    }
}
