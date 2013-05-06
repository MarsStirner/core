package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Номенклатура МО (NomMO)<br>
 */
@Entity
@Table(name = "rb_V007_NomMO", catalog = "", schema = "")
public class V007NomMO {
    /**
     * Код типа медицинской организации
     */
    @Id
    @Column(name = "idnmo")
    private long id;

    /**
     * Наименование типа медицинской организации
     */
    @Column(name = "nmoname")
    private String nmoName;

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

    public String getNmoName() {
        return nmoName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V007NomMO{");
        sb.append("id=").append(id);
        sb.append(", nmoName='").append(nmoName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
