package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор типов документов, подтверждающих факт страхования по ОМС (TipOMS)<br>
 */
@Entity
@Table(name = "rb_F008_TipOMS", catalog = "", schema = "")
public class F008TipOMS {
    /**
     * Код типа документа, подтверждающего факт страхования по ОМС
     */
    @Id
    @Column(name = "iddoc")
    private long id;

    /**
     * Наименование документа, подтверждающего факт страхования по ОМС
     */
    @Column(name = "docname")
    private String docName;

    /**
     * Дата начала действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "datebeg")
    private Date dateBegin;

    /**
     * Дата окончания действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dateend")
    private Date dateEnd;

    public long getId() {
        return id;
    }

    public String getDocName() {
        return docName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        return "F008TipOMS{" +
                "id=" + id +
                ", docName='" + docName + '\'' +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
