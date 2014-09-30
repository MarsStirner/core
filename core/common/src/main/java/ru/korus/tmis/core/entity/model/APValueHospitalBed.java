package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_HospitalBed")
@NamedQueries(
        {
                @NamedQuery(name = "APValueHospitalBed.findAll", query = "SELECT a FROM APValueHospitalBed a")
        })
@XmlType(name = "actionPropertyValueHospitalBed")
@XmlRootElement(name = "actionPropertyValueHospitalBed")
public class APValueHospitalBed extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer bedId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
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
        return value == null ? null : value.getName();
    }

    @Override
    public String getValueAsId() {
        return bedId != null ? Integer.toString(bedId) : "";
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
        if ("".equals(value)) {
            this.bedId = null;  //TODO: Возможно будет падать!!!!?????
            return true;
        }
        try {
            this.bedId = TextUtils.getRobustInt(value);
            return true;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106,
                    "Не могу установить " + this.getClass().getSimpleName() + " в значение <" + value + ">"
            );
        }
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
