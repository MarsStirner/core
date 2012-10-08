/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author s0
 */
@Entity
@Table(name = "rbSocStatusType")
@NamedQueries({
        @NamedQuery(name = "RbSocStatusType.findAll", query = "SELECT r FROM RbSocStatusType r"),
        @NamedQuery(name = "RbSocStatusType.findById", query = "SELECT r FROM RbSocStatusType r WHERE r.id = :id"),
        @NamedQuery(name = "RbSocStatusType.findByCode", query = "SELECT r FROM RbSocStatusType r WHERE r.code = :code"),
        @NamedQuery(name = "RbSocStatusType.findByName", query = "SELECT r FROM RbSocStatusType r WHERE r.name = :name"),
        @NamedQuery(name = "RbSocStatusType.findBySocCode", query = "SELECT r FROM RbSocStatusType r WHERE r.socCode = :socCode"),
        @NamedQuery(name = "RbSocStatusType.findByTFOMSCode", query = "SELECT r FROM RbSocStatusType r WHERE r.tFOMSCode = :tFOMSCode"),
        @NamedQuery(name = "RbSocStatusType.findByRegionalCode", query = "SELECT r FROM RbSocStatusType r WHERE r.regionalCode = :regionalCode")})
public class RbSocStatusType implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "socCode")
    private String socCode;
    @Column(name = "TFOMSCode")
    private Integer tFOMSCode;
    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    public RbSocStatusType() {
    }

    public RbSocStatusType(Integer id) {
        this.id = id;
    }

    public RbSocStatusType(Integer id, String code, String name, String socCode, String regionalCode) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.socCode = socCode;
        this.regionalCode = regionalCode;
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

    public String getSocCode() {
        return socCode;
    }

    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public Integer getTFOMSCode() {
        return tFOMSCode;
    }

    public void setTFOMSCode(Integer tFOMSCode) {
        this.tFOMSCode = tFOMSCode;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
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
        if (!(object instanceof RbSocStatusType)) {
            return false;
        }
        RbSocStatusType other = (RbSocStatusType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbSocStatusType[ id=" + id + " ]";
    }

}
