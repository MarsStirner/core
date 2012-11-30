package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Entity для работы из ORM с таблицей s11r64.RbQuotaStatus.
 * @author mmakankov
 * @since 1.0.0.48
 */
@Entity
@Table(name = "RbQuotaStatus", catalog = "", schema = "s11r64")
@NamedQueries(
        {
                @NamedQuery(name = "RbQuotaStatus.findAll", query = "SELECT cq FROM RbQuotaStatus cq")
        })
@XmlType(name = "RbQuotaStatus")
@XmlRootElement(name = "RbQuotaStatus")
public class RbQuotaStatus implements Serializable {

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

        RbQuotaStatus that = (RbQuotaStatus) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbQuotaStatus[id=" + id + "]";
    }
}
