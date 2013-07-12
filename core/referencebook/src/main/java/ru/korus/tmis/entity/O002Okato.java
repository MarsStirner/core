package ru.korus.tmis.entity;

import nsi.O002Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор административно-территориального деления (OKATO)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_O002_Okato", catalog = "", schema = "")
public class O002Okato implements Serializable {
    /**
     * Код территории
     */
    @Id
    @Column(name = "ter")
    private String ter;

    /**
     * Код района/города
     */
    @Column(name = "kod1")
    private String kod1;

    /**
     * Код РП/сельсовета
     */
    @Column(name = "kod2")
    private String kod2;

    /**
     * Код сельского населенного пункта
     */
    @Column(name = "kod3")
    private String kod3;

    /**
     * Код раздела
     */
    @Column(name = "razdel")
    private String razdel;

    /**
     * Наименование
     */
    @Column(name = "name1")
    private String name1;

    /**
     * Дополнительные данные
     */
    @Column(name = "centrum")
    private String centrum;

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

    public O002Okato() {
    }

    private O002Okato(String ter, String kod1, String kod2, String kod3, String razdel, String name1, String centrum, String nomdescr, String nomakt, Long status, Date dataupd) {
        this.ter = ter;
        this.kod1 = kod1;
        this.kod2 = kod2;
        this.kod3 = kod3;
        this.razdel = razdel;
        this.name1 = name1;
        this.centrum = centrum;
        this.nomdescr = nomdescr;
        this.nomakt = nomakt;
        this.status = status;
        this.dataupd = dataupd;
    }

    public static O002Okato getInstance(O002Type type) {
        return new O002Okato(
                type.getTER(),
                type.getKOD1(),
                type.getKOD2(),
                type.getKOD3(),
                type.getRAZDEL(),
                type.getNAME1(),
                type.getCENTRUM(),
                type.getNOMDESCR(),
                type.getNOMAKT(),
                type.getSTATUS(),
                DateUtil.getDate(type.getDATAUPD())
        );
    }

    public String getTer() {
        return ter;
    }

    public String getKod1() {
        return kod1;
    }

    public String getKod2() {
        return kod2;
    }

    public String getKod3() {
        return kod3;
    }

    public String getRazdel() {
        return razdel;
    }

    public String getName1() {
        return name1;
    }

    public String getCentrum() {
        return centrum;
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
        final StringBuilder sb = new StringBuilder("O002Okato{");
        sb.append("ter='").append(ter).append('\'');
        sb.append(", kod1='").append(kod1).append('\'');
        sb.append(", kod2='").append(kod2).append('\'');
        sb.append(", kod3='").append(kod3).append('\'');
        sb.append(", razdel='").append(razdel).append('\'');
        sb.append(", name1='").append(name1).append('\'');
        sb.append(", centrum='").append(centrum).append('\'');
        sb.append(", nomdescr='").append(nomdescr).append('\'');
        sb.append(", nomakt='").append(nomakt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", dataupd=").append(dataupd);
        sb.append('}');
        return sb.toString();
    }
}
