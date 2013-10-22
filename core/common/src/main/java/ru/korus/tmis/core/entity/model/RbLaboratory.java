package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 22.10.13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbLaboratory", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbLaboratory.findAll", query = "SELECT j FROM RbLaboratory j")
        })
@XmlType(name = "rbLaboratory")
@XmlRootElement(name = "rbLaboratory")
public class RbLaboratory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code = "";

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "protocol")
    private Integer protocol;

    @Basic(optional = false)
    @Column(name = "address")
    private String address = "";

    @Basic(optional = false)
    @Column(name = "ownName")
    private String ownName = "";

    @Basic(optional = false)
    @Column(name = "labName")
    private String labName = "";

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

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbLaboratory that = (RbLaboratory) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbLaboratory[id=" + id + "]";
    }
}
