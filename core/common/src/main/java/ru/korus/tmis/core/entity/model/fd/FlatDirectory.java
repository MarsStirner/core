package ru.korus.tmis.core.entity.model.fd;

import ru.korus.tmis.core.entity.model.AssignmentHour;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FlatDirectory", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "FlatDirectory.findAll", query = "SELECT fd FROM FlatDirectory fd")
        })
@XmlType(name = "flatDirectory")
@XmlRootElement(name = "flatDirectory")
public class FlatDirectory implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "description")
    private String description = "";

    @OneToMany(mappedBy = "flatDirectory")
    private java.util.List<FDField> fdFields = new java.util.LinkedList<FDField>();

    @OneToMany(mappedBy = "flatDirectory")
    private java.util.List<FDRecord> fdRecords = new java.util.LinkedList<FDRecord>();

    @OneToMany(mappedBy = "flatDirectory")
    private java.util.List<ClientFDProperty> fdClientProperties = new java.util.LinkedList<ClientFDProperty>();

    public FlatDirectory() {
    }

    public FlatDirectory(Integer id) {
        this.id = id;
    }

    public java.util.List<FDField> getFdFields() {
        return fdFields;
    }

    public void setFdFields(java.util.List<FDField> fdFields) {
        this.fdFields = fdFields;
    }

    public void resetFdFields() {
        fdFields.clear();
    }

    public java.util.List<FDRecord> getFdRecords() {
        return fdRecords;
    }

    public void setFdRecords(java.util.List<FDRecord> fdRecords) {
        this.fdRecords = fdRecords;
    }

    public void resetFdRecords() {
        fdRecords.clear();
    }

    public java.util.List<ClientFDProperty> getFdClientProperties() {
        return fdClientProperties;
    }

    public void setFdClientProperties(java.util.List<ClientFDProperty> fdClientProperties) {
        this.fdClientProperties = fdClientProperties;
    }

    public void resetFdClientProperties() {
        fdClientProperties.clear();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlatDirectory)) {
            return false;
        }
        FlatDirectory other = (FlatDirectory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.FlatDirectory[id=" + id + "]";
    }
}
