package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * User: eupatov<br>
 * Date: 07.02.13 at 19:05<br>
 * Company Korus Consulting IT<br>
 */

@Entity
@Table(name = "rbAttachType")
@NamedQueries(
        {
                @NamedQuery(name = "rbAttachType.findAll", query = "SELECT r FROM RbAttachType r")
        })
@XmlType(name = "rbAttachType")
@XmlRootElement(name = "rbAttachType")
public class RbAttachType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "temporary")
    private Short temporary;
    //Временное прикрепление

    @Basic(optional = false)
    @Column(name = "outcome")
    private Short outcome;
    //Признак выбытия

    @ManyToOne
    @JoinColumn(name = "finance_id")
    private RbFinance finance;
    //Тип финансирования {rbFinance}

    public RbAttachType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Short getTemporary() {
        return temporary;
    }

    public void setTemporary(Short temporary) {
        this.temporary = temporary;
    }

    public Short getOutcome() {
        return outcome;
    }

    public void setOutcome(Short outcome) {
        this.outcome = outcome;
    }

    public RbFinance getFinance() {
        return finance;
    }

    public void setFinance(RbFinance finance) {
        this.finance = finance;
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
        if (!(object instanceof RbAttachType)) {
            return false;
        }
        RbAttachType other = (RbAttachType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbAttachType[id=" + id + "]";
    }
}
