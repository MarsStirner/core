package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Tissue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tissue.findAll", query = "SELECT t FROM Tissue t"),
    @NamedQuery(name = "Tissue.findById", query = "SELECT t FROM Tissue t WHERE t.id = :id")
})
public class Tissue implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "barcode")
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private RbTissueType tissueType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Tissue() {
    }

    public Tissue(Integer id) {
        this.id = id;
    }

    public Tissue(Integer id, Date date, String barcode) {
        this.id = id;
        this.date = date;
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public RbTissueType getTissueType() {
        return tissueType;
    }

    public void setTissueType(RbTissueType tissueType) {
        this.tissueType = tissueType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        if (!(object instanceof Tissue)) {
            return false;
        }
        Tissue other = (Tissue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Tissue[ id=" + id + " ]";
    }    
}
