package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор медицинских специальностей (Medspeс)<br>
 */
@Entity
@Table(name = "rb_V004_Medspec", catalog = "", schema = "")
public class V004Medspec {
    /**
     * Код медицинской специальности
     */
    @Id
    @Column(name = "idmsp")
    private String id;

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

    public String getId() {
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
