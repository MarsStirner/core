package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_Integer")
@NamedQueries(
        {
                @NamedQuery(name = "APValueRLS.findAll", query = "SELECT a FROM APValueRLS a")
        })
@XmlType(name = "actionPropertyValueRLS")
@XmlRootElement(name = "actionPropertyRLS")
public class APValueRLS extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "value")
    private int key;

    public APValueRLS() {
    }

    public APValueRLS(IndexedId id) {
        this.id = id;
    }

    public APValueRLS(IndexedId id, int value) {
        this.id = id;
        this.key = value;
    }

    public APValueRLS(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int value) {
        this.key = value;
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
        if (!(object instanceof APValueRLS)) {
            return false;
        }
        APValueRLS other = (APValueRLS) object;

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
        return "ru.korus.tmis.core.entity.model.APValueRLS[id=" + id + "]";
    }

    @Override
    public Object getValue() {
        return key;
    }

    @Override
    public String getValueAsString() {
        return Integer.toString(key);
    }

    @Override
    public String getValueAsId() {
        return Integer.toString(key);
    }

    @Override
    public boolean setValueFromString(String value)
            throws CoreException {
        try {
            this.key = Integer.valueOf(value);
            return true;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106, // TODO: Fix me!
                    "Не могу установить " +
                            this.getClass().getSimpleName() +
                            " в значение <" + value + ">"
            );
        }
    }
}
