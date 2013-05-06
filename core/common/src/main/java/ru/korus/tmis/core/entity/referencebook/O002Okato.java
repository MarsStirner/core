package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Общероссийский классификатор административно-территориального деления (OKATO)<br>
 */
@Entity
@Table(name = "rb_O002_Okato", catalog = "", schema = "")
public class O002Okato {
    /**
     * Код территории
     */
    @Id
    @Column(name = "TER")
    private String ter;

    /**
     * Код района/города
     */
    @Column(name = "KOD1")
    private String kod1;

    /**
     * Код РП/сельсовета
     */
    @Column(name = "KOD2")
    private String kod2;

    /**
     * Код сельского населенного пункта
     */
    @Column(name = "KOD3")
    private String kod3;

    /**
     * Код раздела
     */
    @Column(name = "RAZDEL")
    private String razdel;

    /**
     * Наименование
     */
    @Column(name = "NAME1")
    private String name1;

    /**
     * Дополнительные данные
     */
    @Column(name = "CENTRUM")
    private String centrum;

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
