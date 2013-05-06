package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Номенклатура работ и услуг в здравоохранении (NomeclR)<br>
 */
@Entity
@Table(name = "rb_V001_NomerclR", catalog = "", schema = "")
public class V001NomerclR {
    /**
     * Код работы (услуги)
     */
    @Id
    @Column(name = "idrb")
    private long id;

    /**
     * Наименование работы (услуги)
     */
    @Column(name = "rbname")
    private String rbName;

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

    public String getRbName() {
        return rbName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V001NomerclR{");
        sb.append("id=").append(id);
        sb.append(", rbName='").append(rbName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
