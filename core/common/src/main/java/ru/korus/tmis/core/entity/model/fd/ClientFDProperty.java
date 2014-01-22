package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "ClientFDProperty")
@NamedQueries(
        {
                @NamedQuery(name = "ClientFDProperty.findAll", query = "SELECT cp FROM ClientFDProperty cp")
        })
@XmlType(name = "clientFDProperty")
@XmlRootElement(name = "clientFDProperty")
public class ClientFDProperty implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "flatDirectory_id")
    private FlatDirectory flatDirectory;

    @Basic(optional = false)
    @Column(name = "name")
    private String name = "";

    @Basic(optional = false)
    @Column(name = "description")
    private String description = "";

    public ClientFDProperty() {
    }

    public ClientFDProperty(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientFDProperty)) {
            return false;
        }
        ClientFDProperty other = (ClientFDProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.ClientFDProperty[id=" + id + "]";
    }
}