package ru.korus.tmis.entity;

import nsi.F007Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор ведомственной принадлежности медицинской организации(Vedom)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F007_Vedom", catalog = "", schema = "")
public class F007Vedom implements Serializable {
    /**
     * Код типа ведомства
     */
    @Id
    @Column(name = "idved")
    private long id;

    /**
     * Наименование ведомства
     */
    @Column(name = "vedname")
    private String vedName;

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

    public F007Vedom() {
    }

    private F007Vedom(long id, String vedName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.vedName = vedName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static F007Vedom getInstance(F007Type type) {
        return new F007Vedom(
                type.getIDVED(),
                type.getVEDNAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );
    }

    public long getId() {
        return id;
    }

    public String getVedName() {
        return vedName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        return "F007Vedom{" +
                "id=" + id +
                ", vedName='" + vedName + '\'' +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
