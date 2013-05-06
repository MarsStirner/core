package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Международный классификатор болезней (MKB10)<br>
 */
@Entity
@Table(name = "rb_M001_MKB10", catalog = "", schema = "")
public class M001MKB10 {
    /**
     * Код диагноза заболевани
     */
    @Id
    @Column(name = "idds")
    private String id;

    /**
     * Наименование диагноза
     */
    @Column(name = "dsname")
    private String dsName;

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

    public String getDsName() {
        return dsName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("M001MKB10{");
        sb.append("id='").append(id).append('\'');
        sb.append(", dsName='").append(dsName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
