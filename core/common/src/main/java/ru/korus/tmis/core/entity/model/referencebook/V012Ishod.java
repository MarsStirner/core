package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор исходов заболевания (Ishod)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V012_Ishod")
public class V012Ishod implements Serializable {
    /**
     * Код исхода заболевания
     */
    @Id
    @Column(name = "idiz")
    private long id;

    /**
     * Наименование исхода заболевания
     */
    @Column(name = "izname")
    private String izName;

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

    public V012Ishod() {
    }

    public V012Ishod(long id, String izName, long iduslov, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.izName = izName;
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

    public String getIzName() {
        return izName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V012Ishod{");
        sb.append("id=").append(id);
        sb.append(", izName='").append(izName).append('\'');
        sb.append(", iduslov=").append(iduslov);
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
