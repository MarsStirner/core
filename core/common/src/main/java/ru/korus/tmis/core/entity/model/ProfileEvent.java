package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Profile", catalog = "", schema = "tmis_core")
@NamedQueries(
        {
                @NamedQuery(name = "ProfileEvent.findAll", query = "SELECT a FROM ProfileEvent a")
        })
@XmlType(name = "profileEvent")
@XmlRootElement(name = "profileEvent")
public class ProfileEvent implements Serializable {

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
    @Column(name = "sessionIdHigh")
    private long sessionIdHigh;

    @Basic(optional = false)
    @Column(name = "sessionIdLow")
    private long sessionIdLow;

    @Basic(optional = false)
    @Column(name = "nestedLevel")
    private int nestedLevel;

    @Basic(optional = false)
    @Column(name = "number")
    private int number;

    @Basic(optional = false)
    @Column(name = "time")
    private long time;

    @Basic(optional = false)
    @Column(name = "className")
    private String className;

    @Basic(optional = false)
    @Column(name = "methodName")
    private String methodName;

    public ProfileEvent() {
    }

    public ProfileEvent(UUID sessionId,
                        int nestedLevel,
                        int number,
                        long time,
                        String className,
                        String methodName) {
        this.sessionIdHigh = sessionId.getMostSignificantBits();
        this.sessionIdLow = sessionId.getLeastSignificantBits();
        this.nestedLevel = nestedLevel;
        this.number = number;
        this.time = time;
        this.className = className;
        this.methodName = methodName;
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

    public long getSessionIdHigh() {
        return sessionIdHigh;
    }

    public void setSessionIdHigh(final long sessionIdHigh) {
        this.sessionIdHigh = sessionIdHigh;
    }

    public long getSessionIdLow() {
        return sessionIdLow;
    }

    public void setSessionIdLow(final long sessionIdLow) {
        this.sessionIdLow = sessionIdLow;
    }

    public int getNestedLevel() {
        return nestedLevel;
    }

    public void setNestedLevel(final int nestedLevel) {
        this.nestedLevel = nestedLevel;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
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
        if (!(object instanceof ProfileEvent)) {
            return false;
        }
        ProfileEvent other = (ProfileEvent) object;

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
        return "ru.korus.tmis.core.entity.model.ProfileEvent[id=" + id + "]";
    }
}
