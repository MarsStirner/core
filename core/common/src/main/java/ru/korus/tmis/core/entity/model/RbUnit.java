package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbUnit")
@NamedQueries(
        {
                @NamedQuery(name = "RbUnit.findAll", query = "SELECT r FROM RbUnit r"),
                @NamedQuery(name = "RbUnit.findByCode", query = "SELECT u FROM RbUnit u WHERE u.code = :code")
        })
@XmlType(name = "rbUnit")
@XmlRootElement(name = "rbUnit")
public class RbUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    ////////////////////////////////////////////////////////////////////////////
    // Custom stuff
    ////////////////////////////////////////////////////////////////////////////

    @Transient
    private static final RbUnit emptyUnit = new RbUnit();

    static {
        emptyUnit.setId(-1);
        emptyUnit.setCode("");
        emptyUnit.setName("");
    }

    public static RbUnit getEmptyUnit() {
        return emptyUnit;
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

    public RbUnit() {
    }

    public RbUnit(Integer id) {
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
        if (!(object instanceof RbUnit)) {
            return false;
        }
        RbUnit other = (RbUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbUnit[id=" + id + "]";
    }
}
