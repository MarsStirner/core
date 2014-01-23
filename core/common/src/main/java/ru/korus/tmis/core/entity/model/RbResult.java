/*package ru.korus.tmis.core.entity.model;

public class RbResult {
}*/

package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbResult")
@NamedQueries(
        {
                @NamedQuery(name = "RbResult.findAll", query = "SELECT res FROM RbResult res")
        })
@XmlType(name = "rbResult")
@XmlRootElement(name = "rbResult")
public class RbResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "eventPurpose_id")
    private RbEventTypePurpose eventPurposeType;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "continued")
    private short continued;

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    public RbResult() {
    }

    public RbResult(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbEventTypePurpose getEventPurposeType() {
        return eventPurposeType;
    }

    public void setEventPurposeType(RbEventTypePurpose eventPurposeType) {
        this.eventPurposeType = eventPurposeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getContinued() {
        return continued;
    }

    public void setContinued(short continued) {
        this.continued = continued;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
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
        if (!(object instanceof RbResult)) {
            return false;
        }
        RbResult other = (RbResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbResult[id=" + id + "]";
    }
}