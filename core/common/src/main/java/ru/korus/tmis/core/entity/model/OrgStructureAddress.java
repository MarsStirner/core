package ru.korus.tmis.core.entity.model;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OrgStructure_Address", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "OrgStructureAddress.findAll", query = "SELECT o FROM OrgStructureAddress o"),
                @NamedQuery(name = "OrgStructureAddress.findByOrgStructure",
                        query = "SELECT o  FROM OrgStructureAddress o   WHERE o.master= :orgStructure")
        })
@XmlType(name = "orgStructureAddress")
@XmlRootElement(name = "orgStructureAddress")
public class OrgStructureAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="master_id")
    private OrgStructure master;

    @ManyToOne
    @JoinColumn(name="house_id")
    private AddressHouse addressHouse;

    @Column(name="firstFlat")
    private Integer firstFlat;

    @Column(name="lastFlat")
    private Integer lastFlat;

    public OrgStructureAddress() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrgStructure getMaster() {
        return master;
    }

    public void setMaster(OrgStructure master) {
        this.master = master;
    }

    public AddressHouse getAddressHouseList() {
        return addressHouse;
    }

    public void setAddressHouseList(AddressHouse addressHouse) {
        this.addressHouse = addressHouse;
    }

    public Integer getFirstFlat() {
        return firstFlat;
    }

    public void setFirstFlat(Integer firstFlat) {
        this.firstFlat = firstFlat;
    }

    public Integer getLastFlat() {
        return lastFlat;
    }

    public void setLastFlat(Integer lastFlat) {
        this.lastFlat = lastFlat;
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
        if (!(object instanceof OrgStructureAddress)) {
            return false;
        }
        OrgStructureAddress other = (OrgStructureAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.OrgStructureAddress[id=" + id + "]";
    }
}
