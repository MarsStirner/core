package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbSpeciality")
@NamedQueries(
        {
                @NamedQuery(name = "Speciality.findAll", query = "SELECT s FROM Speciality s"),
                @NamedQuery(name = "Speciality.getUnquoted", query = " SELECT s FROM Speciality s WHERE s.quotingEnabled = false")
        })
@XmlType(name = "speciality")
@XmlRootElement(name = "speciality")
public class Speciality implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Basic(optional = false)
    @Column(name = "OKSOName")
    private String oksoName;

    @Basic(optional = false)
    @Column(name = "OKSOCode")
    private String oksoCode;

    @Column(name = "service_id")
    private Integer serviceId;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Basic(optional = false)
    @Column(name = "mkbFilter")
    private String mkbFilter;

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    @Column(name = "quotingEnabled", nullable = true)
    Boolean quotingEnabled;

    public Speciality() {
    }

    public Speciality(Integer id) {
        this.id = id;
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

    public String getOKSOName() {
        return oksoName;
    }

    public void setOKSOName(String oksoName) {
        this.oksoName = oksoName;
    }

    public String getOKSOCode() {
        return oksoCode;
    }

    public void setOKSOCode(String oksoCode) {
        this.oksoCode = oksoCode;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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

    public String getMkbFilter() {
        return mkbFilter;
    }

    public void setMkbFilter(String mkbFilter) {
        this.mkbFilter = mkbFilter;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public Boolean getQuotingEnabled() {
        return quotingEnabled;
    }

    public void setQuotingEnabled(Boolean quotingEnabled) {
        this.quotingEnabled = quotingEnabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Speciality)) {
            return false;
        }
        Speciality other = (Speciality) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Speciality[id=" + id + "]";
    }
}
