package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор форм собственности (OKFS)<br>
 */
@Entity
@Table(name = "rb_O004_Okfs", catalog = "", schema = "")
public class O004Okfs {
    /**
     * Код позиции
     */
    @Id
    @Column(name = "KOD")
    private String kod;

    /**
     * Наименование
     */
    @Column(name = "NAME1")
    private String name1;

    /**
     * Алгоритм сбора
     */
    @Column(name = "ALG")
    private String alg;

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

    public String getKod() {
        return kod;
    }

    public String getName1() {
        return name1;
    }

    public String getAlg() {
        return alg;
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
        final StringBuilder sb = new StringBuilder("O004Okfs{");
        sb.append("kod='").append(kod).append('\'');
        sb.append(", name1='").append(name1).append('\'');
        sb.append(", alg='").append(alg).append('\'');
        sb.append(", nomakt='").append(nomakt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", dataupd=").append(dataupd);
        sb.append('}');
        return sb.toString();
    }
}
