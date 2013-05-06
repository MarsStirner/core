package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор ведомственной принадлежности медицинской организации(Vedom)<br>
 */
@Entity
@Table(name = "rb_F007_Vedom", catalog = "", schema = "")
public class F007Vedom {
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
