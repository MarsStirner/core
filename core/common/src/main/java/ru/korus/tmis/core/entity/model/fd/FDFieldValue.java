package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FDFieldValue")
public class FDFieldValue implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value")
    private String value = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdRecord_id")
    private FDRecord record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdField_id")
    private FDField fdField;


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

    public FDRecord getFDRecord() {
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
    }

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