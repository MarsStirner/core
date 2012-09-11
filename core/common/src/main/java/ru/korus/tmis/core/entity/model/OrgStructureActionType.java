package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "OrgStructure_ActionType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "OrgStructureActionType.findAll", query = "SELECT o FROM OrgStructureActionType o")
        })
@XmlType(name = "orgStructureActionType")
@XmlRootElement(name = "orgStructureActionType")
public class OrgStructureActionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "master_id")
    private OrgStructure masterDepartment;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @ManyToOne
    @JoinColumn(name = "actionType_id")
    private ActionType actionType;

    public OrgStructureActionType() {
    }

    public OrgStructureActionType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrgStructure getMasterDepartment() {
        return masterDepartment;
    }

    public void setMasterDepartment(OrgStructure masterDepartment) {
        this.masterDepartment = masterDepartment;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
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
        if (!(object instanceof OrgStructureActionType)) {
            return false;
        }
        OrgStructureActionType other = (OrgStructureActionType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.OrgStructureActionType[id=" + id + "]";
    }
}
