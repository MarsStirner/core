package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_Time", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueTime.findAll", query = "SELECT a FROM APValueTime a")
        })
@XmlType(name = "actionPropertyValueTime")
@XmlRootElement(name = "actionPropertyValueTime")
public class APValueTime extends AbstractAPValue implements Serializable, APValue {

    private static final DateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "value")
    @Temporal(TemporalType.TIME)
    private Date value;

    public APValueTime() {
    }

    public APValueTime(IndexedId id) {
        this.id = id;
    }

    public APValueTime(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    @Override
    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value != null ? df.format(value) : "";
    }

    
    @Override
    public void setValue(Object value) throws CoreException {
        this.value = (Date)value;
    }
    
    @Override
    public boolean setValueFromString(final String value)
            throws CoreException {
        if ("".equals(value)) {
            this.value = new Date();
            return true;
        }

        try {
            this.value = df.parse(value);
            return true;
        } catch (ParseException ex) {
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
        if (!(object instanceof APValueTime)) {
            return false;
        }
        APValueTime other = (APValueTime) object;

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
        return "ru.korus.tmis.core.entity.model.APValueTime[id=" + id + "]";
    }
}
