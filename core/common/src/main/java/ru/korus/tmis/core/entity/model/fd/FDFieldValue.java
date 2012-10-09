package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "FDFieldValue", catalog = "", schema = "")
@AssociationOverrides({
        @AssociationOverride(name = "pk.fdRecord",
                joinColumns = @JoinColumn(name = "fdRecord_id")),
        @AssociationOverride(name = "pk.fdField",
                joinColumns = @JoinColumn(name = "fdField_id"))})
public class FDFieldValue implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "value")
    private String value = "";

    private FDFieldValueId pk = new FDFieldValueId();

    /*@ManyToOne
    @JoinColumn(name = "fdRecord_id")
    private FDRecord record;

    @ManyToOne
    @JoinColumn(name = "fdField_id")
    private FDField fdField; */
    @EmbeddedId
    public FDFieldValueId getPk() {
        return pk;
    }

    public void setPk(FDFieldValueId pk) {
        this.pk = pk;
    }

    @Transient
    public FDRecord getFDRecord() {
        return getPk().getFDRecord();
    }

    public void setFDRecord(FDRecord fdRecord) {
        getPk().setFDRecord(fdRecord);
    }

    @Transient
    public FDField getFDField() {
        return getPk().getFDField();
    }

    public void setFDField(FDField fdField) {
        getPk().setFDField(fdField);
    }

    public FDFieldValue() {
    }

    public FDFieldValue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public FDRecord getFDRecord() {
        return record;
    }

    public void setFDRecord(FDRecord record) {
        this.record = record;
    }

    public FDField getFDField() {
        return fdField;
    }

    public void setFdField(FDField fdField) {
        this.fdField = fdField;
    } */

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FDFieldValue)) {
            return false;
        }
        FDFieldValue other = (FDFieldValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.FDFieldValue[id=" + id + "]";
    }
}