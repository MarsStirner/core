package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Upatov Egor <br>
 * Date: 05.08.13, 13:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: Данные о диспансеризации для выгрузки (коды половозрастных групп)<br>
 */
@Entity
@Table(name = "rbDispInfo", schema = "", catalog = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbDispInfo.findAll", query = "SELECT dis FROM RbDispInfo dis"),
                @NamedQuery(name = "rbDispInfo.findByCodeAndMedicalKind",
                        query = "SELECT dis FROM RbDispInfo dis WHERE dis.code = :code AND dis.medicalKind = :medicalKind"),
                @NamedQuery(name = "rbDispInfo.findByCode",
                        query = "SELECT dis FROM RbDispInfo dis WHERE dis.code = :code"),
                @NamedQuery(name = "rbDispInfo.findByCodeAndSex",
                        query = "SELECT dis FROM RbDispInfo dis WHERE dis.code = :code AND (dis.sex = :sex OR dis.sex = 0)"),
                @NamedQuery(name = "rbDispInfo.findBySexAndMedicalKindCode",
                        query = "SELECT dis FROM RbDispInfo dis WHERE (dis.sex = :sex OR dis.sex = 0) AND dis.medicalKind.code = :code")
        })
public class RbDispInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    Integer id;

    @Column(name = "code")
    @Basic(optional = false)
    String code;

    @Column(name = "sex")
    @Basic(optional = false)
    Short sex;

    @Column(name = "age")
    @Basic(optional = false)
    String age;

    @ManyToOne
    @JoinColumn(name = "rbMedicalKind_id")
    RbMedicalKind medicalKind;

    public RbDispInfo() {
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

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public RbMedicalKind getMedicalKind() {
        return medicalKind;
    }

    public void setMedicalKind(RbMedicalKind medicalKind) {
        this.medicalKind = medicalKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbDispInfo that = (RbDispInfo) o;

        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;
        if (medicalKind != null ? !medicalKind.equals(that.medicalKind) : that.medicalKind != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + (medicalKind != null ? medicalKind.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }
}
