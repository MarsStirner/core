package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор способов оплаты медицинской помощи (Sposob)<br>
 */
@Entity
@Table(name = "rb_V010_Sposob", catalog = "", schema = "")
public class V010Sposob {
    /**
     * Код способа оплаты медицинской помощи
     */
    @Id
    @Column(name = "idsp")
    private long id;

    /**
     * Наименование способа оплаты медицинской помощи
     */
    @Column(name = "spname")
    private String spnName;

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

    public String getSpnName() {
        return spnName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V010Sposob{");
        sb.append("id=").append(id);
        sb.append(", spnName='").append(spnName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
