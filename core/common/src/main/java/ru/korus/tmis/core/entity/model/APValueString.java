package ru.korus.tmis.core.entity.model;

import au.id.jericho.lib.html.Source;
import ru.korus.tmis.core.exception.CoreException;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_String", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueString.findAll", query = "SELECT a FROM APValueString a")
        })
@XmlType(name = "actionPropertyValueString")
@XmlRootElement(name = "actionPropertyValueString")
public class APValueString extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    public final static APValue EmptyAPValue;

    static {
        EmptyAPValue = new APValueString();
        try {
            EmptyAPValue.setValueFromString("");
        } catch (CoreException ex) {
            // Never gonna happen...
        }
    }

    @Basic(optional = false)
    @Lob
    @Column(name = "value")
    private String value;

    public APValueString() {
    }

    public APValueString(IndexedId id) {
        this.id = id;
    }

    public APValueString(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        try {
            final Source htmlSource =
                    new Source(
                            new StringReader(
                                    value.toString().replace('\0', '\n')));
            return htmlSource
                    .getRenderer()
                    .setMaxLineLength(Integer.MAX_VALUE)
                    .toString()
                    .trim();
        } catch (IOException e) {
            // Never gonna happen
        }

        return "";
    }

    @Override
    public boolean setValueFromString(final String value) {
        this.value = value;
        return true;
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
        if (!(object instanceof APValueString)) {
            return false;
        }
        APValueString other = (APValueString) object;

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
        return "ru.korus.tmis.core.entity.model.APValueString[id=" + id + "]";
    }
}
