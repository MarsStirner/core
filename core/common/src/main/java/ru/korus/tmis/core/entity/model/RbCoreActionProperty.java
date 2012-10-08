package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbCoreActionProperty", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbCoreActionProperty.findAll", query = "SELECT cap FROM RbCoreActionProperty cap")
        })
@XmlType(name = "rbCoreActionProperty")
@XmlRootElement(name = "rbCoreActionProperty")
public class RbCoreActionProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "actionType_id")
    private ActionType actionType;

    @OneToOne
    @JoinColumn(name = "actionPropertyType_id")
    private ActionPropertyType actionPropertyType;


    public RbCoreActionProperty() {
    }

    public RbCoreActionProperty(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionPropertyType getActionPropertyType() {
        return actionPropertyType;
    }

    public void setActionPropertyType(ActionPropertyType actionPropertyType) {
        this.actionPropertyType = actionPropertyType;
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
        if (!(object instanceof RbCoreActionProperty)) {
            return false;
        }
        RbCoreActionProperty other = (RbCoreActionProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbCoreActionProperty[id=" + id + "]";
    }
}