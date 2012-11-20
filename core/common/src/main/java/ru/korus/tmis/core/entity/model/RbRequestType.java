package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Entity для работы из ORM с таблицей s11r64.rbRequestType.
 * @author Ivan Dmitriev
 * @since 1.0.0.45
 */
@Entity
@Table(name = "rbRequestType", catalog = "", schema = "s11r64")
@NamedQueries(
        {
          @NamedQuery(name = "RbRequestType.findAll", query = "SELECT rt FROM RbRequestType rt")
        })
@XmlType(name = "rbRequestType")
@XmlRootElement(name = "rbRequestType")
public class RbRequestType  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbRequestType that = (RbRequestType) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbRequestType[id=" + id + "]";
    }
}
