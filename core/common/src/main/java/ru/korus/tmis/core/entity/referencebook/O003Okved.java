package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор видов экономической деятельности (OKVED)<br>
 */
@Entity
@Table(name = "rb_O003_Okved", catalog = "", schema = "")
public class O003Okved {
    /**
     * Код раздела
     */
    @Id
    @Column(name = "RAZDEL")
    private String razdel;

    /**
     * Код подраздела
     */
    @Column(name = "PRAZDEL")
    private String prazdel;

    /**
     * Код позиции
     */
    @Column(name = "KOD")
    private String kod;

    /**
     * наименование
     */
    @Column(name = "NAME11")
    private String name11;

    /**
     * продолжение наименования
     */
    @Column(name = "NAME12")
    private String name12;

    /**
     * Описание (пояснение) может содержать до 250 символов
     */
    @Column(name = "NOMDESCR")
    private String nomdescr;

    /**
     * Номер последнего изменения
     */
    @Column(name = "NOMAKT")
    private String nomakt;

    /**
     * Тип последнего изменения (фактически –
     * 1 символ перед запятой), где
     * 1 - аннулировать;
     * 2 - изменить реквизит, кроме кода;
     * 3 - включить;
     * 0 - начальная загрузка
     */
    @Column(name = "STATUS")
    private Long status;

    /**
     * Дата последнего изменения
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_UPD")
    private Date dataupd;

    public String getRazdel() {
        return razdel;
    }

    public String getPrazdel() {
        return prazdel;
    }

    public String getKod() {
        return kod;
    }

    public String getName11() {
        return name11;
    }

    public String getName12() {
        return name12;
    }

    public String getNomdescr() {
        return nomdescr;
    }

    public String getNomakt() {
        return nomakt;
    }

    public Long getStatus() {
        return status;
    }

    public Date getDataupd() {
        return dataupd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("O003Okved{");
        sb.append("razdel='").append(razdel).append('\'');
        sb.append(", prazdel='").append(prazdel).append('\'');
        sb.append(", kod='").append(kod).append('\'');
        sb.append(", name11='").append(name11).append('\'');
        sb.append(", name12='").append(name12).append('\'');
        sb.append(", nomdescr='").append(nomdescr).append('\'');
        sb.append(", nomakt='").append(nomakt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", dataupd=").append(dataupd);
        sb.append('}');
        return sb.toString();
    }
}
