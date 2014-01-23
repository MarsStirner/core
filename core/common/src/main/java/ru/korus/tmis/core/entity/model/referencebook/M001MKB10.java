package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Международный классификатор болезней (MKB10)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_M001_MKB10")
public class M001MKB10 implements Serializable {
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

    public M001MKB10() {
    }

    public M001MKB10(String id, String dsName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.dsName = dsName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

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
