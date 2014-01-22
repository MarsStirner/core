package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 15:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор типов документов, удостоверяющих личность(Tipdoc)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F011_Tipdoc")
public class F011Tipdoc implements Serializable {
    /**
     * Код типа документа
     */
    @Id
    @Column(name = "iddoc")
    private long idDoc;

    /**
     * Наименование типа документа
     */
    @Column(name = "docname")
    private String docName;

    /**
     * Маска серии документа
     */
    @Column(name = "docser")
    private String docSer;

    /**
     * Маска номера документа
     */
    @Column(name = "docnum")
    private String docNum;

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

    public F011Tipdoc() {
    }

    public F011Tipdoc(long idDoc, String docName, String docSer, String docNum, Date datebeg, Date dateend) {
        this.idDoc = idDoc;
        this.docName = docName;
        this.docSer = docSer;
        this.docNum = docNum;
        this.datebeg = datebeg;
        this.dateend = dateend;
    }

    public long getIdDoc() {
        return idDoc;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocSer() {
        return docSer;
    }

    public String getDocNum() {
        return docNum;
    }

    public Date getDatebeg() {
        return datebeg;
    }

    public Date getDateend() {
        return dateend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F011Tipdoc{");
        sb.append("idDoc=").append(idDoc);
        sb.append(", docName='").append(docName).append('\'');
        sb.append(", docSer='").append(docSer).append('\'');
        sb.append(", docNum='").append(docNum).append('\'');
        sb.append(", datebeg=").append(datebeg);
        sb.append(", dateend=").append(dateend);
        sb.append('}');
        return sb.toString();
    }
}
