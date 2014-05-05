package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.14, 20:34 <br>
 * Company: Korus Consulting IT <br>
 * Description: Таблица для хранения числа УЕТ, соответствующих услуге в зависимости от возрастных ограничений<br>
 */
@Entity
@Table(name = "rbServiceUET", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbServiceUET.findAll", query = "SELECT s FROM RbServiceUET s"),
                @NamedQuery(name = "rbServiceUET.findByService",
                        query = "SELECT s FROM RbServiceUET s WHERE s.service = :service")
        })
@XmlType(name = "serviceUET")
@XmlRootElement(name = "serviceUET")
public class RbServiceUET implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rbService_id")
    RbService service;

    @Column(name = "age")
    @Basic(optional = false)
    String age;

    @Column(name = "UET")
    @Basic(optional = false)
    Double uet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbService getService() {
        return service;
    }

    public void setService(RbService service) {
        this.service = service;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Double getUet() {
        return uet;
    }

    public void setUet(Double uet) {
        this.uet = uet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbServiceUET that = (RbServiceUET) o;

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
        return this.getClass().getName()+"[id=" + id + "]";
    }

}
