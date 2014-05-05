package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Upatov Egor <br>
 * Date: 25.06.13, 18:16 <br>
 * Company: Korus Consulting IT <br>
 * Description:Способы оплаты услуг <br>
 */

@Entity
@Table(name = "rbPayType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbPayType.findAll", query = "SELECT pt FROM RbPayType pt")
        })
@XmlType(name = "RbPayType")
@XmlRootElement(name = "RbPayType")
public class RbPayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public RbPayType() {
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
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbPayType rbPayType = (RbPayType) o;

        if (!code.equals(rbPayType.code)) return false;
        if (!id.equals(rbPayType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
