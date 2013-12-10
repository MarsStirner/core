package ru.korus.tmis.core.entity.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbHospitalBedProfile", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbHospitalBedProfile.findAll", query = "SELECT hbp FROM RbHospitalBedProfile hbp")
        })
@XmlType(name = "rbHospitalBedProfile")
@XmlRootElement(name = "rbHospitalBedProfile")
public class RbHospitalBedProfile implements Serializable {

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
    @Column(name = "service_id")
    private Integer serviceId;

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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    /*
    * Overrides
    * */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbHospitalBedProfile)) {
            return false;
        }
        RbHospitalBedProfile other = (RbHospitalBedProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbHospitalBedProfile[id=" + id + "]";
    }

    static public RbHospitalBedProfile newInstance(String name) {
        RbHospitalBedProfile res = new RbHospitalBedProfile();
        res.name = name;
        res.id = -1;
        return res;
    }
}