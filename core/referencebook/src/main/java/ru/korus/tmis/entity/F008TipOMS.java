package ru.korus.tmis.entity;

import nsi.F008Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор типов документов, подтверждающих факт страхования по ОМС (TipOMS)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F008_TipOMS", catalog = "", schema = "")
public class F008TipOMS implements Serializable {
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

    public F008TipOMS() {
    }

    private F008TipOMS(long id, String docName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.docName = docName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static F008TipOMS getInstance(F008Type type) {
        return new F008TipOMS(
                type.getIDDOC(),
                type.getDOCNAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND()));
    }

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
