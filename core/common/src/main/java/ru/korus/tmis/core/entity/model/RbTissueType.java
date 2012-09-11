package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.util.PublicClonable;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "rbTissueType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RbTissueType.findAll", query = "SELECT r FROM RbTissueType r"),
    @NamedQuery(name = "RbTissueType.findById", query = "SELECT r FROM RbTissueType r WHERE r.id = :id")
})
public class RbTissueType implements Serializable, PublicClonable<RbTissueType> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "code")
    private String code;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private short sex;
        
    @Basic(optional = false)
    @Column(name = "group_id")
    private int groupId;

    public RbTissueType() {
    }

    public RbTissueType(Integer id) {
        this.id = id;
    }

    public RbTissueType(Integer id, String code, String name, short sex) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbTissueType)) {
            return false;
        }
        RbTissueType other = (RbTissueType) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbTissueType[ id=" + id + " ]";
    }

    public RbTissueType clone() {
        RbTissueType that = new RbTissueType(id);

        that.code = code;
        that.groupId = groupId;
        that.name = name;
        that.sex = sex;

        return that;
    }
}
