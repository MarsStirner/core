package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OrgStructure", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "OrgStructure.findAll", query = "SELECT o FROM OrgStructure o")
        })
@XmlType(name = "orgStructure")
@XmlRootElement(name = "orgStructure")
public class OrgStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    @Basic(optional = false)
    @Column(name = "type")
    private int type;

    @Column(name = "net_id")
    private Integer netId;

    @Basic(optional = false)
    @Column(name = "isArea")
    private short isArea;

    @Basic(optional = false)
    @Column(name = "hasHospitalBeds")
    private boolean hasHospitalBeds;

    @Basic(optional = false)
    @Column(name = "hasStocks")
    private boolean hasStocks;

    @Basic(optional = false)
    @Column(name = "infisCode")
    private String infisCode;

    @Basic(optional = false)
    @Column(name = "infisInternalCode")
    private String infisInternalCode;

    @Basic(optional = false)
    @Column(name = "infisDepTypeCode")
    private String infisDepTypeCode;

    @Basic(optional = false)
    @Column(name = "infisTariffCode")
    private String infisTariffCode;

    @Basic(optional = false)
    @Column(name = "availableForExternal")
    private int availableForExternal;

    @Basic(optional = false)
    @Column(name = "Address")
    private String address;

    @Basic(optional = false)
    @Column(name = "inheritEventTypes")
    private boolean inheritEventTypes;

    @Basic(optional = false)
    @Column(name = "inheritActionTypes")
    private boolean inheritActionTypes;

    @Basic(optional = false)
    @Column(name = "inheritGaps")
    private boolean inheritGaps;


//    @ManyToOne
//    @JoinColumn(name = "uuid_id")
    private UUID uuid;


    public OrgStructure() {
    }

    public OrgStructure(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Organisation getOrganization() {
        return organisation;
    }

    public void setOrganization(Organisation organisation) {
        this.organisation = organisation;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    public short getIsArea() {
        return isArea;
    }

    public void setIsArea(short isArea) {
        this.isArea = isArea;
    }

    public boolean getHasHospitalBeds() {
        return hasHospitalBeds;
    }

    public void setHasHospitalBeds(boolean hasHospitalBeds) {
        this.hasHospitalBeds = hasHospitalBeds;
    }

    public boolean getHasStocks() {
        return hasStocks;
    }

    public void setHasStocks(boolean hasStocks) {
        this.hasStocks = hasStocks;
    }

    public String getInfisCode() {
        return infisCode;
    }

    public void setInfisCode(String infisCode) {
        this.infisCode = infisCode;
    }

    public String getInfisInternalCode() {
        return infisInternalCode;
    }

    public void setInfisInternalCode(String infisInternalCode) {
        this.infisInternalCode = infisInternalCode;
    }

    public String getInfisDepTypeCode() {
        return infisDepTypeCode;
    }

    public void setInfisDepTypeCode(String infisDepTypeCode) {
        this.infisDepTypeCode = infisDepTypeCode;
    }

    public String getInfisTariffCode() {
        return infisTariffCode;
    }

    public void setInfisTariffCode(String infisTariffCode) {
        this.infisTariffCode = infisTariffCode;
    }

    public int getAvailableForExternal() {
        return availableForExternal;
    }

    public void setAvailableForExternal(int availableForExternal) {
        this.availableForExternal = availableForExternal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getInheritEventTypes() {
        return inheritEventTypes;
    }

    public void setInheritEventTypes(boolean inheritEventTypes) {
        this.inheritEventTypes = inheritEventTypes;
    }

    public boolean getInheritActionTypes() {
        return inheritActionTypes;
    }

    public void setInheritActionTypes(boolean inheritActionTypes) {
        this.inheritActionTypes = inheritActionTypes;
    }

    public boolean getInheritGaps() {
        return inheritGaps;
    }

    public void setInheritGaps(boolean inheritGaps) {
        this.inheritGaps = inheritGaps;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        if (!(object instanceof OrgStructure)) {
            return false;
        }
        OrgStructure other = (OrgStructure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.OrgStructure[id=" + id + "]";
    }
}
