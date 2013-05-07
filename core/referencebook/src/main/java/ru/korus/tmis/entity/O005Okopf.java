package ru.korus.tmis.entity;

import nsi.O005Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор организационно-правовых форм (OKOPF)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_O005_Okopf", catalog = "", schema = "")
public class O005Okopf implements Serializable {
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

    public O005Okopf() {
    }

    private O005Okopf(String kod, String name1, String alg, String nomakt, Long status, Date dataupd) {
        this.kod = kod;
        this.name1 = name1;
        this.alg = alg;
        this.nomakt = nomakt;
        this.status = status;
        this.dataupd = dataupd;
    }

    public static O005Okopf getInstance(O005Type type) {
        return new O005Okopf(
                type.getKOD(),
                type.getNAME1(),
                type.getALG(),
                type.getNOMAKT(),
                type.getSTATUS(),
                DateUtil.getDate(type.getDATAUPD())
        );
    }

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
        final StringBuilder sb = new StringBuilder("O005Okopf{");
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
