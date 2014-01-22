package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbBloodType")
@NamedQueries(
        {
                @NamedQuery(name = "RbBloodType.findAll", query = "SELECT r FROM RbBloodType r")
        })
@XmlType(name = "bloodType")
@XmlRootElement(name = "bloodType")
public class RbBloodType implements Serializable {

    private static final long serialVersionUID = 1L;

    ////////////////////////////////////////////////////////////////////////////
    // Custom stuff
    ////////////////////////////////////////////////////////////////////////////

    @Transient
    private static final RbBloodType emptyBloodType = new RbBloodType();

    static {
        emptyBloodType.setId(-1);
        emptyBloodType.setName("");
        emptyBloodType.setCode("");
    }

    public static RbBloodType getEmptyBloodType() {
        return emptyBloodType;
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom stuff
    ////////////////////////////////////////////////////////////////////////////

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public RbBloodType() {
    }

    public RbBloodType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbBloodType)) {
            return false;
        }
        RbBloodType other = (RbBloodType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbBloodType[id=" + id + "]";
    }
}
