package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор статуса застрахованного лица (StatZL)<br>
 */
@Entity
@Table(name = "rb_F009_StatZL", catalog = "", schema = "")
public class F009StatZL {
    /**
     * Код статус застрахованного лица
     */
    @Id
    @Column(name = "IDStatus")
    private String id;

    /**
     * Наименование статуса застрахованного лица
     */
    @Column(name = "StatusName")
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
