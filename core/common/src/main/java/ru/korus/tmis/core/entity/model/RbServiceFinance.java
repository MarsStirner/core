package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Upatov Egor <br>
 * Date: 10.06.13, 12:30 <br>
 * Company: Korus Consulting IT <br>
 * Description: Источники финансирования услуг <br>
 */
@Entity
@Table(name = "rbServiceFinance", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbServiceFinance.findAll", query = "SELECT f FROM RbServiceFinance f"),
                @NamedQuery(name = "rbServiceFinance.findByCode", query = "SELECT sf FROM RbServiceFinance sf WHERE sf.code = :code")
        })
@XmlType(name = "serviceFinance")
@XmlRootElement(name = "serviceFinance")
public class RbServiceFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @Basic(optional = false)
    private String code;

    @Column(name = "name")
    @Basic(optional = false)
    private String name;

    public RbServiceFinance() {
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

        RbServiceFinance that = (RbServiceFinance) o;

        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
