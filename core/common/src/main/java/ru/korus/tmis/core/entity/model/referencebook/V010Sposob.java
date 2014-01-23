package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор способов оплаты медицинской помощи (Sposob)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V010_Sposob")
public class V010Sposob implements Serializable {
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

    public V010Sposob() {
    }

    public V010Sposob(long id, String spnName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.spnName = spnName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

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
