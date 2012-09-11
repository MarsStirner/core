package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "ActionProperty_Image")
@XmlRootElement
@NamedQueries(
        {
                @NamedQuery(name = "APValueImage.findAll", query = "SELECT a FROM APValueImage a"),
                @NamedQuery(name = "APValueImage.findById", query = "SELECT a FROM APValueImage a WHERE a.id.id = :id"),
                @NamedQuery(name = "APValueImage.findByIndex", query = "SELECT a FROM APValueImage a WHERE a.id.index = :index")
        })
public class APValueImage extends AbstractAPValue implements Serializable, APValue {
    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "value")
    private byte[] value;

    public APValueImage() {
    }

    public APValueImage(IndexedId id) {
        this.id = id;
    }

    public APValueImage(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
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
        if (!(object instanceof APValueImage)) {
            return false;
        }
        APValueImage other = (APValueImage) object;

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
        return "ru.korus.tmis.core.entity.model.ActionPropertyImage[ actionPropertyImagePK=" + id + " ]";
    }

    @Override
    public String getValueAsString() {
        final byte[] bytes = getValue();
        if (bytes != null) {
            return DatatypeConverter.printBase64Binary(getValue());
        } else {
            return "";
        }
    }

    @Override
    public boolean setValueFromString(String value) {
        final byte[] bytes = DatatypeConverter.parseBase64Binary(value);
        setValue(bytes);
        return true;
    }
}
