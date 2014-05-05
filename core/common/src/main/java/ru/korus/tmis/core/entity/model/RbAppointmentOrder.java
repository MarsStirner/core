package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Upatov Egor <br>
 * Date: 29.01.14, 13:35 <br>
 * Company: Korus Consulting IT <br>
 * Description: Порядок обращения <br>
 */

@Entity
@Table(name = "rbAppointmentOrder")
@NamedQueries(
        {
                @NamedQuery(name = "RbAppointmentOrder.findAll", query = "SELECT a FROM RbAppointmentOrder a"),
                @NamedQuery(name = "RbAppointmentOrder.findByCode",
                        query = "SELECT a FROM RbAppointmentOrder a WHERE a.code = :code")
        }
)
@XmlType(name = "RbAppointmentOrder")
@XmlRootElement(name = "RbAppointmentOrder")
public class RbAppointmentOrder {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Column(name = "TFOMScode_hosp")
    private String TFOMSHospitalCode;

    @Column(name = "TFOMScode_account")
    private String TFOMSAccountCode;
    // ///////////////////////////////////////////////////
    public RbAppointmentOrder() {
    }

    public RbAppointmentOrder(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbAppointmentOrder that = (RbAppointmentOrder) o;

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

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }

    /**
     * Getters & Setters
     */

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

    public String getTFOMSHospitalCode() {
        return TFOMSHospitalCode;
    }

    public void setTFOMSHospitalCode(String TFOMSHospitalCode) {
        this.TFOMSHospitalCode = TFOMSHospitalCode;
    }

    public String getTFOMSAccountCode() {
        return TFOMSAccountCode;
    }

    public void setTFOMSAccountCode(String TFOMSAccountCode) {
        this.TFOMSAccountCode = TFOMSAccountCode;
    }
}
