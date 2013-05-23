package ru.korus.tmis.entity;

import nsi.F009Type;
import ru.korus.tmis.utils.DateUtil;

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
@Table(name = "rb_F009_StatZL", catalog = "", schema = "")
public class F009StatZL implements Serializable {
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

    public F009StatZL() {
    }

    private F009StatZL(String id, String statusName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.statusName = statusName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static F009StatZL getInstance(F009Type type) {
        return new F009StatZL(
                type.getIDStatus(),
                type.getStatusName(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );
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
