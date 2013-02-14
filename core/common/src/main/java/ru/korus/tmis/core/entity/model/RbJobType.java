package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RbJobType Entity
 * Author: idmitriev Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.64
 */
@Entity
@Table(name = "rbJobType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbJobType.findAll", query = "SELECT j FROM RbJobType j")
        })
@XmlType(name = "rbJobType")
@XmlRootElement(name = "rbJobType")
public class RbJobType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "group_id")
    private Integer group;

    @Basic(optional = false)
    @Column(name = "code")
    private String code = "";

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode = "";

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "laboratory_id")
    private Integer laboratory;

    @Basic(optional = false)
    @Column(name = "isInstant")
    private boolean isInstant = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Integer laboratory) {
        this.laboratory = laboratory;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public void setInstant(boolean instant) {
        isInstant = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbJobType rbJobType = (RbJobType) o;

        if (id != null ? !id.equals(rbJobType.id) : rbJobType.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbJobType[id=" + id + "]";
    }
}
