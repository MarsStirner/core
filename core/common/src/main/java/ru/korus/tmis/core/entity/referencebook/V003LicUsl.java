package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор работ (услуг) при лицензировании медицинской помощи (LicUsl)<br>
 */
@Entity
@Table(name = "rb_V003_LicUsl", catalog = "", schema = "")
public class V003LicUsl {
    /**
     * Код работ (услуг) при лицензировании медицинской помощи
     */
    @Id
    @Column(name = "IDRL")
    private long idrl;

    /**
     * Наименование работ (услуг) при лицензировании медицинской помощи
     */
    @Column(name = "LICNAME")
    private String licname;

    /**
     * Код элемента верхнего уровня
     */
    @Column(name = "IERARH")
    private Long ierarh;

    /**
     * Признак допустимости использования
     */
    @Column(name = "PRIM")
    private Long prim;

    /**
     * Дата начала действия записи
     */
    @Column(name = "DATEBEG")
    private Date datebeg;

    /**
     * Дата окончания действия записи
     */
    @Column(name = "DATEEND")
    private Date dateend;


    public long getIdrl() {
        return idrl;
    }

    public String getLicname() {
        return licname;
    }

    public Long getIerarh() {
        return ierarh;
    }

    public Long getPrim() {
        return prim;
    }

    public Date getDatebeg() {
        return datebeg;
    }

    public Date getDateend() {
        return dateend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V003LicUsl{");
        sb.append("idrl=").append(idrl);
        sb.append(", licname='").append(licname).append('\'');
        sb.append(", ierarh=").append(ierarh);
        sb.append(", prim=").append(prim);
        sb.append(", datebeg=").append(datebeg);
        sb.append(", dateend=").append(dateend);
        sb.append('}');
        return sb.toString();
    }
}
