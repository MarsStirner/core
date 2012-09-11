package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_HospitalBed", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueHospitalBed.findAll", query = "SELECT a FROM APValueHospitalBed a")
        })
@XmlType(name = "actionPropertyValueHospitalBed")
@XmlRootElement(name = "actionPropertyValueHospitalBed")
public class APValueHospitalBed extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "value")
    private OrgStructureHospitalBed value;

    public APValueHospitalBed() {
    }

    public APValueHospitalBed(IndexedId id) {
        this.id = id;
    }

    public APValueHospitalBed(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public OrgStructureHospitalBed getValue() {
        return value;
    }

    public void setValue(OrgStructureHospitalBed value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value.getName();
    }

    @Override
    public boolean setValueFromString(final String value) {
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof APValueHospitalBed)) {
            return false;
        }
        APValueHospitalBed other = (APValueHospitalBed) object;

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
        return "ru.korus.tmis.core.entity.model.APValueHospitalBed[id=" + id + "]";
    }
}
