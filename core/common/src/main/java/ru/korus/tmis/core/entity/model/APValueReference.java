package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.02.2013, 17:37:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Entity
@Table(name = "ActionProperty_Reference")
@NamedQueries(
        {
                @NamedQuery(name = "APValueReference.findAll", query = "SELECT a FROM APValueReference a")
        })
@XmlType(name = "actionPropertyValueInteger")
@XmlRootElement(name = "actionPropertyValueInteger")
public class APValueReference extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "value")
    private Integer value;

    public APValueReference() {
    }

    public APValueReference(IndexedId id) {
        this.id = id;
    }

    public APValueReference(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }

    @Override
    public boolean setValueFromString(final String value)
            throws CoreException {
        if ("".equals(value)) {
            this.value = 0;
            return true;
        }

        try {
            this.value = TextUtils.getRobustInt(value);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof APValueReference)) {
            return false;
        }
        APValueReference other = (APValueReference) object;

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
        return "ru.korus.tmis.core.entity.model.APValueReference[id=" + id + "]";
    }
}
