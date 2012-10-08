package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FDRecord", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "FDRecord.findAll", query = "SELECT fdr FROM FDRecord fdr")
        })
@XmlType(name = "fdRecord")
@XmlRootElement(name = "fdRecord")
public class FDRecord implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "flatDirectory_id")
    private FlatDirectory flatDirectory;

    @Basic(optional = false)
    @Column(name = "order")
    private Integer order;

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "description")
    private String description = "";

    @Column(name = "dateStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "dateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.fdRecord", cascade = CascadeType.ALL)
    private java.util.List<FDFieldValue> fieldValues = new java.util.LinkedList<FDFieldValue>();

    public java.util.List<FDFieldValue> getFieldValues() {
        return this.fieldValues;
    }

    public void setFieldValues(java.util.List<FDFieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public FDRecord() {
    }

    public FDRecord(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        if (!(object instanceof FDRecord)) {
            return false;
        }
        FDRecord other = (FDRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.FDRecord[id=" + id + "]";
    }
}