package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор статуса застрахованного лица (StatZL)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F009_StatZL")
public class F009StatZL implements Serializable {
    /**
     * Код статус застрахованного лица
     */
    @Id
    @Column(name = "idstatus")
    private String id;

    /**
     * Наименование статуса застрахованного лица
     */
    @Column(name = "statusname")
    private String statusName;

    /**
     * Дата начала действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "datebeg")
    private Date dateBegin;

    /**
     * Дата окончания действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dateend")
    private Date dateEnd;

    public F009StatZL() {
    }

    public F009StatZL(String id, String statusName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.statusName = statusName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public String getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        return "F009StatZL{" +
                "id='" + id + '\'' +
                ", statusName='" + statusName + '\'' +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
