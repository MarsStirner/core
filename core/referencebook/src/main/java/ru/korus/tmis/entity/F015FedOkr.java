package ru.korus.tmis.entity;

import nsi.F015Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор федеральных округов<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F015_FedOkr", catalog = "", schema = "")
public class F015FedOkr implements Serializable {
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

    public F015FedOkr() {
    }

    private F015FedOkr(long kodok, String okrname, Date datebeg, Date dateend) {
        this.kodok = kodok;
        this.okrname = okrname;
        this.datebeg = datebeg;
        this.dateend = dateend;
    }

    public static F015FedOkr getInstance(F015Type type) {
        return new F015FedOkr(
                type.getKODOK(),
                type.getOKRNAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );
    }

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
