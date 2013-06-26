package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 16.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbService_Profile", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbService_Profile.findAll", query = "SELECT s FROM RbServiceProfile s")
        })
@XmlType(name = "serviceProfile")
@XmlRootElement(name = "serviceProfile")
public class RbServiceProfile implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "idx")
    private int idx;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private RbService service;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Basic(optional = false)
    @Column(name = "age_bu")
    private int age_bu;

    @Basic(optional = false)
    @Column(name = "age_bc")
    private int age_bc;

    @Basic(optional = false)
    @Column(name = "age_eu")
    private int age_eu;

    @Basic(optional = false)
    @Column(name = "age_ec")
    private int age_ec;

    @Basic(optional = false)
    @Column(name = "mkbRegExp")
    private String mkbRegExp;

    @Column(name = "medicalAidProfile_id")
    private int medicalAidProfile_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public RbService getService() {
        return service;
    }

    public void setService(RbService service) {
        this.service = service;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAge_bu() {
        return age_bu;
    }

    public void setAge_bu(int age_bu) {
        this.age_bu = age_bu;
    }

    public int getAge_bc() {
        return age_bc;
    }

    public void setAge_bc(int age_bc) {
        this.age_bc = age_bc;
    }

    public int getAge_eu() {
        return age_eu;
    }

    public void setAge_eu(int age_eu) {
        this.age_eu = age_eu;
    }

    public int getAge_ec() {
        return age_ec;
    }

    public void setAge_ec(int age_ec) {
        this.age_ec = age_ec;
    }

    public String getMkbRegExp() {
        return mkbRegExp;
    }

    public void setMkbRegExp(String mkbRegExp) {
        this.mkbRegExp = mkbRegExp;
    }

    public int getMedicalAidProfile_id() {
        return medicalAidProfile_id;
    }

    public void setMedicalAidProfile_id(int medicalAidProfile_id) {
        this.medicalAidProfile_id = medicalAidProfile_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbServiceProfile that = (RbServiceProfile) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbServiceProfile[id=" + id + "]";
    }
}

