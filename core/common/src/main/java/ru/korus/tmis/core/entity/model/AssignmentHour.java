package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "AssignmentHour", catalog = "", schema = "")
public class AssignmentHour
        implements Serializable, Cloneable {

    @Embeddable
    public static class PK {
        @Column(name = "action_id")
        private int actionId;

        @Column(name = "createDatetime")
        @Temporal(value = TemporalType.TIMESTAMP)
        private Date createDatetime;

        @Column(name = "hour")
        private int hour;

        public PK() {
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(final int actionId) {
            this.actionId = actionId;
        }

        public Date getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(final Date createDatetime) {
            this.createDatetime = createDatetime;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(final int hour) {
            this.hour = hour;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PK)) {
                return false;
            }

            final PK pk = (PK) o;

            if (actionId != pk.actionId) {
                return false;
            }
            if (hour != pk.hour) {
                return false;
            }
            if (createDatetime != null ? !createDatetime.equals(pk.createDatetime) : pk.createDatetime != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = actionId;
            result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
            result = 31 * result + hour;
            return result;
        }
    }

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PK id;

    @Column(name = "complete")
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "action_id",
            insertable = false,
            updatable = false)
    private Action action;

    public AssignmentHour() {
    }

    public AssignmentHour(final int actionId,
                          final Date date) {
        final Calendar c = GregorianCalendar.getInstance();
        c.setTime(date);
        final int hour = c.get(Calendar.HOUR_OF_DAY);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        final PK id = new PK();
        id.setActionId(actionId);
        id.setCreateDatetime(c.getTime());
        id.setHour(hour);

        this.id = id;
    }

    public AssignmentHour(final int actionId,
                          final long date,
                          final int hour) {
        final Calendar c = GregorianCalendar.getInstance();
        c.setTimeInMillis(date);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        final PK id = new PK();
        id.setActionId(actionId);
        id.setCreateDatetime(c.getTime());
        id.setHour(hour);

        this.id = id;
    }

    public PK getId() {
        return id;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    public Action getAction() {
        return action;
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
        if (!(object instanceof AssignmentHour)) {
            return false;
        }
        AssignmentHour other = (AssignmentHour) object;
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
        return "ru.korus.tmis.core.entity.model.AssignmentHour[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
