package ru.korus.tmis.core.entity.model.new_diagnosis;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbDiagnosisKind")
public class RbDiagnosisKind implements Serializable {

    public static final String MAIN = "main";
    public static final String COMPLICATION = "complication";
    public static final String ASSOCIATED = "associated";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    public RbDiagnosisKind() {
    }

    public RbDiagnosisKind(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbDiagnosisKind)) {
            return false;
        }
        RbDiagnosisKind other = (RbDiagnosisKind) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RbDiagnosisKind[" + id + "]['" + code + "'=" + name + ']';
    }
}
