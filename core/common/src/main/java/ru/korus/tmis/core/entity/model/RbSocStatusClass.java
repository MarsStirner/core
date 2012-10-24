/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.core.entity.model;

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

/**
 * @author s0
 */
@Entity
@Table(name = "rbSocStatusClass")
@NamedQueries({
        @NamedQuery(name = "RbSocStatusClass.findAll", query = "SELECT r FROM RbSocStatusClass r"),
        @NamedQuery(name = "RbSocStatusClass.findById", query = "SELECT r FROM RbSocStatusClass r WHERE r.id = :id"),
        @NamedQuery(name = "RbSocStatusClass.findByGroupId", query = "SELECT r FROM RbSocStatusClass r WHERE r.groupId = :groupId"),
        @NamedQuery(name = "RbSocStatusClass.findByCode", query = "SELECT r FROM RbSocStatusClass r WHERE r.code = :code"),
        @NamedQuery(name = "RbSocStatusClass.findByName", query = "SELECT r FROM RbSocStatusClass r WHERE r.name = :name")})
public class RbSocStatusClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public RbSocStatusClass() {
    }

    public RbSocStatusClass(Integer id) {
        this.id = id;
    }

    public RbSocStatusClass(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof RbSocStatusClass)) {
            return false;
        }
        RbSocStatusClass other = (RbSocStatusClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbSocStatusClass[ id=" + id + " ]";
    }

}
