package ru.korus.tmis.core.entity.model.new_diagnosis;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbDiagnosisTypeN")
public class RbDiagnosisTypeN implements Serializable {

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
    @Column(name = "requireResult")
    private Integer requireResult;

    @Basic(optional = false)
    @Column(name = "rank")
    private Integer rank;


    public RbDiagnosisTypeN() {
    }

    public RbDiagnosisTypeN(Integer id) {
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

    public Integer getRequireResult() {
        return requireResult;
    }

    public void setRequireResult(Integer requireResult) {
        this.requireResult = requireResult;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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
        if (!(object instanceof RbDiagnosisTypeN)) {
            return false;
        }
        RbDiagnosisTypeN other = (RbDiagnosisTypeN) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RbDiagnosisTypeN[" + id + "]['" + code + "'=" + name + ']';
    }
}
