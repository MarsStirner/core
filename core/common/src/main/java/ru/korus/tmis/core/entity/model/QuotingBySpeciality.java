package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 15:40<br>
 * Company Korus Consulting IT<br>
 */
@Entity
@Table(name = "QuotingBySpeciality", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "QuotingBySpeciality.findAll", query = "SELECT o FROM QuotingBySpeciality o"),
//              @NamedQuery(name = "QuotingBySpeciality.incrementCouponsRemaining",
//                      query = "UPDATE QuotingBySpeciality o SET o.couponsRemaining = o.couponsRemaining + 1 WHERE o.id = :id"),
//              @NamedQuery(name = "QuotingBySpeciality.decrementCouponsRemaining",
//                      query = "UPDATE QuotingBySpeciality o SET o.couponsRemaining = o.couponsRemaining - 1 WHERE o.id = :id"),
                @NamedQuery(name = "QuotingBySpeciality.findByOrganizationInfis",
                        query = "SELECT o FROM QuotingBySpeciality o WHERE o.organisation.infisCode = :infisCode"),
                @NamedQuery(name = "QuotingBySpeciality.findBySpeciality",
                        query = "SELECT o FROM QuotingBySpeciality o WHERE o.speciality = :speciality"),
                @NamedQuery(name = "QuotingBySpeciality.findByOrganizationInfisAndSpeciality",
                        query = "SELECT o FROM QuotingBySpeciality o where o.organisation.infisCode = :infisCode AND o.speciality = :speciality")
        })
@XmlType(name = "QuotingBySpeciality")
@XmlRootElement(name = "QuotingBySpeciality")
public class QuotingBySpeciality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Basic(optional = true)   //NULLABLE
    @Column(name = "coupons_quote")
    private Integer couponsQuote;


    @Basic(optional = true)   //NULLABLE
    @Column(name = "coupons_remaining")
    private Integer couponsRemaining;

    ////////////////////////////////////////////////////////////////////////////
    // End of columns
    ////////////////////////////////////////////////////////////////////////////

    public QuotingBySpeciality() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Integer getCouponsQuote() {
        return couponsQuote;
    }

    public void setCouponsQuote(Integer couponsQuote) {
        this.couponsQuote = couponsQuote;
    }

    public Integer getCouponsRemaining() {
        return couponsRemaining;
    }

    public void setCouponsRemaining(Integer couponsRemaining) {
        this.couponsRemaining = couponsRemaining;
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
        if (!(object instanceof QuotingBySpeciality)) {
            return false;
        }
        QuotingBySpeciality other = (QuotingBySpeciality) object;
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
        return "ru.korus.tmis.core.database.model.QuotingBySpeciality[id=" + id + ", specialityName=" + speciality.getName() + "]";
    }

}
