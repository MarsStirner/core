package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор федеральных округов<br>
 */
@Entity
@Table(name = "rb_F015_FedOkr", catalog = "", schema = "")
public class F015FedOkr {
    /**
     * Код округа
     */
    @Id
    @Column(name = "KOD_OK")
    private long kodok;

    /**
     * Наименование округа РФ
     */
    @Column(name = "OKRNAME")
    private String okrname;

    /**
     * Дата начала действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATEBEG")
    private Date datebeg;

    /**
     * Дата окончания действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATEEND")
    private Date dateend;

    public long getKodok() {
        return kodok;
    }

    public String getOkrname() {
        return okrname;
    }

    public Date getDatebeg() {
        return datebeg;
    }

    public Date getDateend() {
        return dateend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F015FedOkr{");
        sb.append("kodok=").append(kodok);
        sb.append(", okrname='").append(okrname).append('\'');
        sb.append(", datebeg=").append(datebeg);
        sb.append(", dateend=").append(dateend);
        sb.append('}');
        return sb.toString();
    }
}
