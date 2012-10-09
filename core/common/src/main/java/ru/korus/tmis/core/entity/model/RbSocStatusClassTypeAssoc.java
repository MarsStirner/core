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
@Table(name = "rbSocStatusClassTypeAssoc")
@NamedQueries({
        @NamedQuery(name = "RbSocStatusClassTypeAssoc.findAll", query = "SELECT r FROM RbSocStatusClassTypeAssoc r"),
        @NamedQuery(name = "RbSocStatusClassTypeAssoc.findById", query = "SELECT r FROM RbSocStatusClassTypeAssoc r WHERE r.id = :id"),
        @NamedQuery(name = "RbSocStatusClassTypeAssoc.findByClassId", query = "SELECT r FROM RbSocStatusClassTypeAssoc r WHERE r.classId = :classId"),
        @NamedQuery(name = "RbSocStatusClassTypeAssoc.findByTypeId", query = "SELECT r FROM RbSocStatusClassTypeAssoc r WHERE r.typeId = :typeId")})
public class RbSocStatusClassTypeAssoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "class_id")
    private int classId;
    @Basic(optional = false)
    @Column(name = "type_id")
    private int typeId;

    public RbSocStatusClassTypeAssoc() {
    }

    public RbSocStatusClassTypeAssoc(Integer id) {
        this.id = id;
    }

    public RbSocStatusClassTypeAssoc(Integer id, int classId, int typeId) {
        this.id = id;
        this.classId = classId;
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof RbSocStatusClassTypeAssoc)) {
            return false;
        }
        RbSocStatusClassTypeAssoc other = (RbSocStatusClassTypeAssoc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbSocStatusClassTypeAssoc[ id=" + id + " ]";
    }

}
