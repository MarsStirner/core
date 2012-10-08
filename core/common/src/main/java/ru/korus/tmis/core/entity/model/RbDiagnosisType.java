package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbDiagnosisType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbDiagnosisType.findAll", query = "SELECT dt FROM RbDiagnosisType dt")
        })
@XmlType(name = "rbDiagnosisType")
@XmlRootElement(name = "rbDiagnosisType")
public class RbDiagnosisType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "replaceInDiagnosis")
    private String replaceInDiagnosis;

    public RbDiagnosisType() {
    }

    public RbDiagnosisType(Integer id) {
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

    public String getReplaceInDiagnosis() {
        return replaceInDiagnosis;
    }

    public void setReplaceInDiagnosis(String replaceInDiagnosis) {
        this.replaceInDiagnosis = replaceInDiagnosis;
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
        if (!(object instanceof RbDiagnosisType)) {
            return false;
        }
        RbDiagnosisType other = (RbDiagnosisType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbDiagnosisType[id=" + id + "]";
    }
}
