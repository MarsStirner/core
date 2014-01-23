package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: mark2
 * Date: 3/16/12
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbPolicyType")
@NamedQueries(
        {
                @NamedQuery(name = "RbPolicyType.findAll", query = "SELECT r FROM RbPolicyType r"),
                @NamedQuery(name = "RbPolicyType.findByCode",
                        query = "SELECT r FROM RbPolicyType r WHERE r.code = :code ORDER BY r.id DESC")
        })
@XmlType(name = "policyType")
@XmlRootElement(name = "policyType")
public class RbPolicyType implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /*
    * END DB FIELDS
    * */

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

    /*
    * Overrides
    * */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbPolicyType)) {
            return false;
        }
        RbPolicyType other = (RbPolicyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbPolicyType[id=" + id + "]";
    }
}