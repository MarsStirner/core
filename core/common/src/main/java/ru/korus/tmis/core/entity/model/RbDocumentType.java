package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbDocumentType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbDocumentType.findAll", query = "SELECT r FROM RbDocumentType r"),
                @NamedQuery(name = "RbDocumentType.findByCode",
                        query = "SELECT r FROM RbDocumentType r WHERE r.code = :code ORDER BY r.id DESC")
        })
@XmlType(name = "documentType")
@XmlRootElement(name = "documentType")
public class RbDocumentType implements Serializable {

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
    @Column(name = "regionalCode")
    private String regionalCode;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private RbDocumentTypeGroup documentTypeGroup;

    @Basic(optional = false)
    @Column(name = "serial_format")
    private Integer serial_format;

    @Basic(optional = false)
    @Column(name = "number_format")
    private Integer number_format;

    @Basic(optional = false)
    @Column(name = "federalCode")
    private String federalCode;

    @Basic(optional = false)
    @Column(name = "socCode")
    private String socCode;

    @Column(name = "TFOMSCode")
    private String TFOMSCode;

    /*
    * END DB FIELDS
    * */

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

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RbDocumentTypeGroup getDocumentTypeGroup() {
        return documentTypeGroup;
    }

    public void setDocumentTypeGroup(RbDocumentTypeGroup documentTypeGroup) {
        this.documentTypeGroup = documentTypeGroup;
    }

    public Integer getSerial_format() {
        return serial_format;
    }

    public void setSerial_format(Integer serial_format) {
        this.serial_format = serial_format;
    }

    public Integer getNumber_format() {
        return number_format;
    }

    public void setNumber_format(Integer number_format) {
        this.number_format = number_format;
    }

    public String getFederalCode() {
        return federalCode;
    }

    public void setFederalCode(String federalCode) {
        this.federalCode = federalCode;
    }

    public String getSocCode() {
        return socCode;
    }

    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public String getTFOMSCode() {
        return TFOMSCode;
    }

    public void setTFOMSCode(String TFOMSCode) {
        this.TFOMSCode = TFOMSCode;
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
        if (!(object instanceof RbDocumentType)) {
            return false;
        }
        RbDocumentType other = (RbDocumentType) object;
        if (this.id == null && other.id == null && this != other) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbDocumentType[id=" + id + "]";
    }

}
