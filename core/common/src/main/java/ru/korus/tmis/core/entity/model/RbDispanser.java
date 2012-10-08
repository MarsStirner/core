package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbDispanser", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbDispanser.findAll", query = "SELECT dis FROM RbDispanser dis")
        })
@XmlType(name = "rbDispanser")
@XmlRootElement(name = "rbDispanser")
public class RbDispanser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "observed")
    private short observed;

    public RbDispanser() {
    }

    public RbDispanser(Integer id) {
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

    public short getObserved() {
        return observed;
    }

    public void setObserved(short observed) {
        this.observed = observed;
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
        if (!(object instanceof RbDispanser)) {
            return false;
        }
        RbDispanser other = (RbDispanser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbDispanser[id=" + id + "]";
    }
}
