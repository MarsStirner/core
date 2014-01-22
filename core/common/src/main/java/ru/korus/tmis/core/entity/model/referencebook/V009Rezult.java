package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор результатов обращения за медицинской помощью (Rezult)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V009_Rezult")
public class V009Rezult implements Serializable {
    /**
     * Код результата обращения
     */
    @Id
    @Column(name = "idrmp")
    private long id;

    /**
     * Наименование результата обращения
     */
    @Column(name = "rmpname")
    private String rmpName;

    /**
     * Соответствует условиям оказания МП (V006)
     */
    @Column(name = "iduslov")
    private long iduslov;

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

    public V009Rezult() {
    }

    public V009Rezult(long id, String rmpName, long iduslov, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.rmpName = rmpName;
        this.iduslov = iduslov;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public long getIduslov() {
        return iduslov;
    }

    public String getRmpName() {
        return rmpName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V009Rezult{");
        sb.append("id=").append(id);
        sb.append(", rmpName='").append(rmpName).append('\'');
        sb.append(", iduslov=").append(iduslov);
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
