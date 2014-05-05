package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Upatov Egor <br>
 * Date: 06.06.13, 13:55 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

@Entity
@Table(name = "rbMedicalKind", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbMedicalKind.findAll", query = "SELECT mk FROM RbMedicalKind mk"),
                @NamedQuery(name = "rbMedicalKind.findByCode", query = "SELECT mk FROM RbMedicalKind mk WHERE mk.code = :code")
        })
@XmlType(name = "kind")
@XmlRootElement(name = "kind")
public class RbMedicalKind {
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

    public RbMedicalKind() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbMedicalKind that = (RbMedicalKind) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
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
