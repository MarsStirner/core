package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created with IntelliJ IDEA.
 * User: EUpatov
 * Date: 03.06.13
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "rbMedicalAidUnit", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbMedicalAidUnit.findAll", query = "SELECT mu FROM RbMedicalAidUnit mu"),
                @NamedQuery(name = "rbMedicalAidUnit.findByCode", query = "SELECT mu FROM RbMedicalAidUnit mu WHERE mu.code = :code")
        })
@XmlType(name = "unit")
@XmlRootElement(name = "unit")
public class RbMedicalAidUnit {
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
    @Column(name = "descr")
    private String description;

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    public RbMedicalAidUnit() {
    }

    public RbMedicalAidUnit(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbMedicalAidUnit that = (RbMedicalAidUnit) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (regionalCode != null ? regionalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getName() +
                "{name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", id=" + id +
                '}';
    }
}
