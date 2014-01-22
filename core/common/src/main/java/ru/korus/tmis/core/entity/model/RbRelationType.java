package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: mark2
 * Date: 3/18/12
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbRelationType")
@NamedQueries(
        {
                @NamedQuery(name = "RbRelationType.findAll", query = "SELECT r FROM RbRelationType r")
        })
@XmlType(name = "relationType")
@XmlRootElement(name = "relationType")
public class RbRelationType implements Serializable {

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
    @Column(name = "leftName")
    private String leftName;

    @Basic(optional = false)
    @Column(name = "rightName")
    private String rightName;

    @Basic(optional = false)
    @Column(name = "isDirectGenetic")
    private Integer isDirectGenetic;

    @Basic(optional = false)
    @Column(name = "isBackwardGenetic")
    private Integer isBackwardGenetic;

    @Basic(optional = false)
    @Column(name = "isDirectRepresentative")
    private boolean isDirectRepresentative;

    @Basic(optional = false)
    @Column(name = "isBackwardRepresentative")
    private boolean isBackwardRepresentative;

    @Basic(optional = false)
    @Column(name = "isDirectEpidemic")
    private boolean isDirectEpidemic;

    @Basic(optional = false)
    @Column(name = "isBackwardEpidemic")
    private boolean isBackwardEpidemic;

    @Basic(optional = false)
    @Column(name = "isDirectDonation")
    private boolean isDirectDonation;

    @Basic(optional = false)
    @Column(name = "isBackwardDonation")
    private boolean isBackwardDonation;

    @Basic(optional = false)
    @Column(name = "leftSex")
    private boolean leftSex;

    @Basic(optional = false)
    @Column(name = "rightSex")
    private boolean rightSex;

    @Basic(optional = false)
    @Column(name = "regionalCode")
    private String regionalCode;

    @Basic(optional = false)
    @Column(name = "regionalReverseCode")
    private String regionalReverseCode;

    /*
    * END DB FIELDS
    * */

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

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Integer getDirectGenetic() {
        return isDirectGenetic;
    }

    public void setDirectGenetic(Integer directGenetic) {
        isDirectGenetic = directGenetic;
    }

    public Integer getBackwardGenetic() {
        return isBackwardGenetic;
    }

    public void setBackwardGenetic(Integer backwardGenetic) {
        isBackwardGenetic = backwardGenetic;
    }

    public boolean isDirectRepresentative() {
        return isDirectRepresentative;
    }

    public void setDirectRepresentative(boolean directRepresentative) {
        isDirectRepresentative = directRepresentative;
    }

    public boolean isBackwardRepresentative() {
        return isBackwardRepresentative;
    }

    public void setBackwardRepresentative(boolean backwardRepresentative) {
        isBackwardRepresentative = backwardRepresentative;
    }

    public boolean isDirectEpidemic() {
        return isDirectEpidemic;
    }

    public void setDirectEpidemic(boolean directEpidemic) {
        isDirectEpidemic = directEpidemic;
    }

    public boolean isBackwardEpidemic() {
        return isBackwardEpidemic;
    }

    public void setBackwardEpidemic(boolean backwardEpidemic) {
        isBackwardEpidemic = backwardEpidemic;
    }

    public boolean isDirectDonation() {
        return isDirectDonation;
    }

    public void setDirectDonation(boolean directDonation) {
        isDirectDonation = directDonation;
    }

    public boolean isBackwardDonation() {
        return isBackwardDonation;
    }

    public void setBackwardDonation(boolean backwardDonation) {
        isBackwardDonation = backwardDonation;
    }

    public boolean isLeftSex() {
        return leftSex;
    }

    public void setLeftSex(boolean leftSex) {
        this.leftSex = leftSex;
    }

    public boolean isRightSex() {
        return rightSex;
    }

    public void setRightSex(boolean rightSex) {
        this.rightSex = rightSex;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public String getRegionalReverseCode() {
        return regionalReverseCode;
    }

    public void setRegionalReverseCode(String regionalReverseCode) {
        this.regionalReverseCode = regionalReverseCode;
    }

    /*
   * Overrides
   * */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RbRelationType)) {
            return false;
        }
        RbRelationType other = (RbRelationType) object;
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
        return "ru.korus.tmis.core.entity.model.RbRelationType[id=" + id + "]";
    }
}