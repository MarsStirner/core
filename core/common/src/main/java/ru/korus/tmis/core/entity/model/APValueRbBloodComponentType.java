package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.korus.tmis.core.exception.CoreException;

@Entity
@Table(name = "ActionProperty_rbBloodComponentType", catalog = "", schema = "")
public class APValueRbBloodComponentType extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private RbTrfuBloodComponentType value;

    public APValueRbBloodComponentType() {
    }

    public APValueRbBloodComponentType(IndexedId id) {
        this.id = id;
    }

    public APValueRbBloodComponentType(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    @Override
    public RbTrfuBloodComponentType getValue() {
        return value;
    }

    public void setValue(RbTrfuBloodComponentType value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value != null ? value.getName() : null;
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
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
        if (!(object instanceof APValueRbBloodComponentType)) {
            return false;
        }
        APValueRbBloodComponentType other = (APValueRbBloodComponentType) object;

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
        return "ru.korus.tmis.core.entity.model.APValueRbBloodComponentType[id=" + id + "]";
    }
}
