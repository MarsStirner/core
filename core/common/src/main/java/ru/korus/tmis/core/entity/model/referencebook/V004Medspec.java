package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор медицинских специальностей (Medspeс)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V004_Medspec", catalog = "", schema = "")
public class V004Medspec implements Serializable {
    /**
     * Код медицинской специальности
     */
    @Id
    @Column(name = "idmsp")
    private long id;

    /**
     * Наименование медицинской специальности
     */
    @Column(name = "mspname")
    private String mspName;

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

    public V004Medspec() {
    }

    public V004Medspec(long id, String mspName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.mspName = mspName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public String getMspName() {
        return mspName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V004Medspec{");
        sb.append("id='").append(id).append('\'');
        sb.append(", mspName='").append(mspName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
