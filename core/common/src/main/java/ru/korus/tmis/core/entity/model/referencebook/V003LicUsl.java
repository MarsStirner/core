package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор работ (услуг) при лицензировании медицинской помощи (LicUsl)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V003_LicUsl")
public class V003LicUsl implements Serializable {
    /**
     * Код работ (услуг) при лицензировании медицинской помощи
     */
    @Id
    @Column(name = "idrl")
    private long idrl;

    /**
     * Наименование работ (услуг) при лицензировании медицинской помощи
     */
    @Column(name = "licname")
    private String licname;

    /**
     * Код элемента верхнего уровня
     */
    @Column(name = "ierarh")
    private Long ierarh;

    /**
     * Признак допустимости использования
     */
    @Column(name = "prim")
    private Long prim;

    /**
     * Дата начала действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "datebeg")
    private Date datebeg;

    /**
     * Дата окончания действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dateend")
    private Date dateend;

    public V003LicUsl() {
    }

    public V003LicUsl(long idrl, String licname, Long ierarh, Long prim, Date datebeg, Date dateend) {
        this.idrl = idrl;
        this.licname = licname;
        this.ierarh = ierarh;
        this.prim = prim;
        this.datebeg = datebeg;
        this.dateend = dateend;
    }

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
