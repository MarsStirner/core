package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "FDField", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "FDField.findAll", query = "SELECT fdf FROM FDField fdf")
        })
@XmlType(name = "fdField")
@XmlRootElement(name = "fdField")
public class FDField implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fdFieldType_id")
    private FDFieldType fdFieldType;

    @ManyToOne
    @JoinColumn(name = "flatDirectory_id")
    private FlatDirectory flatDirectory;

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "description")
    private String description = "";

    @Basic(optional = false)
    @Column(name = "mask")
    private String mask = "";

    @Basic(optional = false)
    @Column(name = "mandatory")
    private short mandatory;

    @Basic(optional = false)
    @Column(name = "order")
    private Integer order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.fdField", cascade = CascadeType.ALL)
    private java.util.List<FDFieldValue> fieldValues = new java.util.LinkedList<FDFieldValue>();

    public java.util.List<FDFieldValue> getFieldValue() {
        return this.fieldValues;
    }

    public void getFieldValue(java.util.List<FDFieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public FDField() {
    }

    public FDField(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FDFieldType getFDFieldType() {
        return fdFieldType;
    }

    public void setFDFieldType(FDFieldType fdFieldType) {
        this.fdFieldType = fdFieldType;
    }

    public FlatDirectory getFlatDirectory() {
        return flatDirectory;
    }

    public void setFlatDirectory(FlatDirectory flatDirectory) {
        this.flatDirectory = flatDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public short getMandatory() {
        return mandatory;
    }

    public void setMandatory(short mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
        if (!(object instanceof FDField)) {
            return false;
        }
        FDField other = (FDField) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.FDField[id=" + id + "]";
    }
}
