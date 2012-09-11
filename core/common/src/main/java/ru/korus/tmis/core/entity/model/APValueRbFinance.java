package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_rbFinance", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueRbFinance.findAll", query = "SELECT a FROM APValueRbFinance a")
        })
@XmlType(name = "actionPropertyValueRbFinance")
@XmlRootElement(name = "actionPropertyValueRbFinance")
public class APValueRbFinance extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer value;

    public APValueRbFinance() {
    }

    public APValueRbFinance(IndexedId id) {
        this.id = id;
    }

    public APValueRbFinance(int id, int index) {
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
        if (!(object instanceof APValueRbFinance)) {
            return false;
        }
        APValueRbFinance other = (APValueRbFinance) object;

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
        return "ru.korus.tmis.core.entity.model.APValueRbFinance[id=" + id + "]";
    }
}
