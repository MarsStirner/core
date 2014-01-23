package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Fault")
@NamedQueries(
        {
                @NamedQuery(name = "FaultEvent.findAll", query = "SELECT a FROM FaultEvent a")
        })
@XmlType(name = "faultEvent")
@XmlRootElement(name = "faultEvent")
public class FaultEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "timeshot")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeshot;

    @Basic(optional = false)
    @Column(name = "inMessage")
    private String inMessage;

    @Basic(optional = false)
    @Column(name = "outMessage")
    private String outMessage;

    public FaultEvent() {
    }

    public FaultEvent(String inMessage, String outMessage) {
        this.inMessage = inMessage;
        this.outMessage = outMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Date getTimeshot() {
        return timeshot;
    }

    public void setTimeshot(final Date timeshot) {
        this.timeshot = timeshot;
    }

    public String getInMessage() {
        return inMessage;
    }

    public void setInMessage(final String inMessage) {
        this.inMessage = inMessage;
    }

    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(final String outMessage) {
        this.outMessage = outMessage;
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
        if (!(object instanceof FaultEvent)) {
            return false;
        }
        FaultEvent other = (FaultEvent) object;

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
        return "ru.korus.tmis.core.entity.model.FaultEvent[id=" + id + "]";
    }
}
