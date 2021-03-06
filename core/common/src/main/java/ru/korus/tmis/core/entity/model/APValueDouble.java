package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

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
@Table(name = "ActionProperty_Double")
@NamedQueries(
        {
                @NamedQuery(name = "APValueDouble.findAll", query = "SELECT a FROM APValueDouble a")
        })
@XmlType(name = "actionPropertyValueDouble")
@XmlRootElement(name = "actionPropertyValueDouble")
public class APValueDouble extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "value")
    private Double value;

    public APValueDouble() {
    }

    public APValueDouble(IndexedId id) {
        this.id = id;
    }

    public APValueDouble(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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
            this.value = 0.0;
            return true;
        }

        try {
            this.value = TextUtils.getRobustDouble(value);
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
        if (!(object instanceof APValueDouble)) {
            return false;
        }
        APValueDouble other = (APValueDouble) object;

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
        return "ru.korus.tmis.core.entity.model.APValueDouble[id=" + id + "]";
    }
}
