package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dnosov
 * Date: 04.10.12
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "rbFinance")
@NamedQueries(
        {
                @NamedQuery(name = "rbFinance.findAll", query = "SELECT r FROM RbFinance r"),
                @NamedQuery(name = "rbFinance.findById", query = "SELECT r FROM RbFinance r WHERE r.id = :id")
        })
@XmlType(name = "rbFinance")
@XmlRootElement(name = "rbFinance")
public class RbFinance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    private String name;

    public RbFinance() {
    }

    public RbFinance(Integer id) {
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
        if (!(object instanceof RbFinance)) {
            return false;
        }
        RbFinance other = (RbFinance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbFinance [" + id + "], code [" + code + "], name [" + name + "]";
    }

}
