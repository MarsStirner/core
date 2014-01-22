package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rbCounter")
@NamedQueries(
        {
                @NamedQuery(name = "RbCounter.findAll", query = "SELECT r FROM RbCounter r")
        })
@XmlType(name = "rbCounter")
@XmlRootElement(name = "rbCounter")
public class RbCounter implements Serializable {

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
    @Column(name = "value")
    private Integer value;

    @Basic(optional = true)
    @Column(name = "prefix")
    private String prefix;

    @Basic(optional = true)
    @Column(name = "separator")
    private String separator;

    @Basic(optional = false)
    @Column(name = "reset")
    private Integer reset;

    @Basic(optional = false)
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Basic(optional = true)
    @Column(name = "resetDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resetDate;

    @Basic(optional = false)
    @Column(name = "sequenceFlag")
    private String sequenceFlag;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getSequenceFlag() {
        return sequenceFlag;
    }

    public void setSequenceFlag(String sequenceFlag) {
        this.sequenceFlag = sequenceFlag;
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

    /*
    * Overrides
    * */

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RbCounter)) {
            return false;
        }
        RbCounter other = (RbCounter) object;
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
        return "ru.korus.tmis.core.database.model.RbCounter[id=" + id + "]";
    }
}