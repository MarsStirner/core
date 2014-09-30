package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_ImageMap")
@NamedQueries(
        {
                @NamedQuery(name = "APValueImageMap.findAll", query = "SELECT a FROM APValueImageMap a")
        })
@XmlType(name = "actionPropertyValueImageMap")
@XmlRootElement(name = "actionPropertyValueImageMap")
public class APValueImageMap implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "index")
    private int index;

    @Lob
    @Column(name = "value")
    private String value;

    public APValueImageMap() {
    }

    public APValueImageMap(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
        return value;
    }

    @Override
    public String getValueAsId() {
        return "";
    }

    @Override
    public boolean setValueFromString(final String value) {
        return false;
    }

    @Override
    public boolean setValue(Object value) throws CoreException {
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
        if (!(object instanceof APValueImageMap)) {
            return false;
        }
        APValueImageMap other = (APValueImageMap) object;

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
        return "ru.korus.tmis.core.entity.model.APValueImageMap[id=" + id + "]";
    }

    @Override
    public void linkToActionProperty(ActionProperty ap, int index) {
        this.id = ap.getId();
        this.index = ap.getIdx();
    }

    @Override
    public APValue unwrap() {
        return this;
    }
}
