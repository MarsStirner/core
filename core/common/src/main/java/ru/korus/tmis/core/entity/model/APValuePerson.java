package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "ActionProperty_Person", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValuePerson.findAll", query = "SELECT a FROM APValuePerson a")
        })
@XmlType(name = "actionPropertyValuePerson")
@XmlRootElement(name = "actionPropertyValuePerson")
public class APValuePerson extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer personId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private Staff value;

    public APValuePerson() {
    }

    public APValuePerson(IndexedId id) {
        this.id = id;
    }

    public APValuePerson(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public IndexedId getId() {
        return id;
    }

    public void setId(IndexedId id) {
        this.id = id;
    }

    public Staff getValue() {
        return value;
    }

    public void setValue(Staff value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value.getLastName();
    }

    @Override
    public String getValueAsId() {
        return personId != null ? Integer.toString(personId) : "";
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
        if ("".equals(value)) {
            this.personId = null;  //TODO: Возможно будет падать!!!!?????
            return true;
        }
        try {
            this.personId = Integer.valueOf(value);
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
        if (!(object instanceof APValuePerson)) {
            return false;
        }
        APValuePerson other = (APValuePerson) object;

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
        return "ru.korus.tmis.core.entity.model.APValuePerson[id=" + id + "]";
    }
}
