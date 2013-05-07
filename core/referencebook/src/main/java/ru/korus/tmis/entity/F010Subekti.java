package ru.korus.tmis.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор субъектов Российской Федерации (Subekti)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F010_Subekti", catalog = "", schema = "")
public class F010Subekti implements Serializable {
    /**
     * Код ТФОМС
     */
    @Id
    @Column(name = "KOD_TF")
    private String kodtf;

    /**
     * Код по ОКАТО (Приложение А O003)
     */
    @Column(name = "KOD_OKATO")
    private String kodokato;

    /**
     * Наименование субъекта РФ
     */
    @Column(name = "SUBNAME")
    private String subname;

    /**
     * Код федерального округа
     */
    @Column(name = "OKRUG")
    private Long okrug;

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

    public String getKodtf() {
        return kodtf;
    }

    public String getKodokato() {
        return kodokato;
    }

    public String getSubname() {
        return subname;
    }

    public Long getOkrug() {
        return okrug;
    }

    public Date getDatebeg() {
        return datebeg;
    }

    public Date getDateend() {
        return dateend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F010Subekti{");
        sb.append("kodtf='").append(kodtf).append('\'');
        sb.append(", kodokato='").append(kodokato).append('\'');
        sb.append(", subname='").append(subname).append('\'');
        sb.append(", okrug=").append(okrug);
        sb.append(", datebeg=").append(datebeg);
        sb.append(", dateend=").append(dateend);
        sb.append('}');
        return sb.toString();
    }
}
