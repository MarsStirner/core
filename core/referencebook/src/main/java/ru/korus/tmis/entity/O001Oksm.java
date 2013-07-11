package ru.korus.tmis.entity;

import nsi.O001Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор стран мира (OKSM)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_O001_Oksm", catalog = "", schema = "")
public class O001Oksm implements Serializable {
    /**
     * Цифровой код
     */
    @Id
    @Column(name = "kod")
    private String kod;

    /**
     * Наименование
     */
    @Column(name = "name11")
    private String name11;

    /**
     * продолжение наименования
     */
    @Column(name = "name12")
    private String name12;

    /**
     * Буквенный код альфа-2
     */
    @Column(name = "alfa2")
    private String alfa2;

    /**
     * Буквенный код альфа-3
     */
    @Column(name = "ALFA3")
    private String alfa3;

    /**
     * Описание (пояснение) может содержать до 250 символов
     */
    @Column(name = "nomdescr")
    private String nomdescr;

    /**
     * Номер последнего изменения
     */
    @Column(name = "nomakt")
    private String nomakt;

    /**
     * Тип последнего изменения (фактически –
     * 1 символ перед запятой), где
     * 1 - аннулировать;
     * 2 - изменить реквизит, кроме кода;
     * 3 - включить;
     * 0 - начальная загрузка
     */
    @Column(name = "status")
    private Long status;

    /**
     * Дата последнего изменения
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "data_upd")
    private Date dataupd;

    public O001Oksm() {
    }

    private O001Oksm(String kod, String name11, String name12, String alfa2, String alfa3, String nomdescr, String nomakt, Long status, Date dataupd) {
        this.kod = kod;
        this.name11 = name11;
        this.name12 = name12;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nomdescr = nomdescr;
        this.nomakt = nomakt;
        this.status = status;
        this.dataupd = dataupd;
    }

    public static O001Oksm getInstance(O001Type type) {
        return new O001Oksm(
                type.getKOD(),
                type.getNAME11(),
                type.getNAME12(),
                type.getALFA2(),
                type.getALFA3(),
                type.getNOMDESCR().length() > 255 ? type.getNOMDESCR().substring(0, 255) : type.getNOMDESCR(),
                type.getNOMAKT(),
                type.getSTATUS(),
                DateUtil.getDate(type.getDATAUPD())
        );
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

    public String getAlfa2() {
        return alfa2;
    }

    public String getAlfa3() {
        return alfa3;
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
        final StringBuilder sb = new StringBuilder("O001Oksm{");
        sb.append("kod='").append(kod).append('\'');
        sb.append(", name11='").append(name11).append('\'');
        sb.append(", name12='").append(name12).append('\'');
        sb.append(", alfa2='").append(alfa2).append('\'');
        sb.append(", alfa3='").append(alfa3).append('\'');
        sb.append(", nomdescr='").append(nomdescr).append('\'');
        sb.append(", nomakt='").append(nomakt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", dataupd=").append(dataupd);
        sb.append('}');
        return sb.toString();
    }
}
