package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 7/3/13
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "rbOperationType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbOperationType.findAll", query = "SELECT ot FROM RbOperationType ot"),
                @NamedQuery(name = "RbOperationType.findById", query = "SELECT ot FROM RbOperationType ot WHERE ot.id = :id")
        })
@XmlType(name = "rbOperationType")
@XmlRootElement(name = "rbOperationType")
public class RbOperationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cd_r")
    private Integer cdR;

    @Column(name = "cd_subr")
    private Integer cdSubr;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Column(name = "ktso")
    private Integer ktso;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public RbOperationType() {
    }

    public RbOperationType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCdR() {
        return cdR;
    }

    public void setCdR(Integer cdR) {
        this.cdR = cdR;
    }

    public Integer getCdSubr() {
        return cdSubr;
    }

    public void setCdSubr(Integer cdSubr) {
        this.cdSubr = cdSubr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getKtso() {
        return ktso;
    }

    public void setKtso(Integer ktso) {
        this.ktso = ktso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbOperationType that = (RbOperationType) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbOperationType[id=" + id + "]";
    }
}
