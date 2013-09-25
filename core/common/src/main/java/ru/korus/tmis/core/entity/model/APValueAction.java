package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_Action", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueAction.findAll", query = "SELECT a FROM APValueAction a")
        })
@XmlType(name = "actionPropertyValueAction")
@XmlRootElement(name = "actionPropertyValueAction")
public class APValueAction extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "value")
    private Action value;

    public APValueAction() {
    }

    public APValueAction(IndexedId id) {
        this.id = id;
    }

    public APValueAction(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public Action getValue() {
        return value;
    }

    public void setValue(Action value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value != null ? value.toString() : "<EMPTY>";
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
        try {
            if (value == null) {
                this.setValue(null);
            } else {
                this.setValue(new Action(TextUtils.getRobustInt(value)));
            }
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
        if (!(object instanceof APValueAction)) {
            return false;
        }
        APValueAction other = (APValueAction) object;

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
        return "ru.korus.tmis.core.entity.model.APValueAction[id=" + id + "]";
    }
}
