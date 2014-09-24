package ru.korus.tmis.core.entity.model.tfoms;

import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.Patient;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 05.02.14, 18:13 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
//@Entity
public class PatientDocument implements Informationable {
    //@Id
    @Column(name = "id")
    private Integer document;

    @Column(name = "DOCTYPE")
    private String DOCTYPE;

    @Column(name = "DOCSER")
    private String DOCSER;

    @Column(name = "DOCNUM")
    private String DOCNUM;

    public PatientDocument() {
    }

    public PatientDocument(Object[] args) {
        if (args.length >= 4) {
            this.document = (Integer) args[0];
            this.DOCTYPE = String.valueOf(args[1]);
            this.DOCSER = String.valueOf(args[2]);
            this.DOCNUM = String.valueOf(args[3]);
        }
    }

    public Integer getDocument() {
        return document;
    }

    public void setDocument(Integer document) {
        this.document = document;
    }

    public String getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(String DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }

    public String getDOCSER() {
        return DOCSER;
    }

    public void setDOCSER(String DOCSER) {
        this.DOCSER = DOCSER;
    }

    public String getDOCNUM() {
        return DOCNUM;
    }

    public void setDOCNUM(String DOCNUM) {
        this.DOCNUM = DOCNUM;
    }


    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("PatientDocument [ ");
        result.append("ID=").append(document);
        result.append(" DOCTYPE=").append(DOCTYPE);
        result.append(" DOCSER=").append(DOCSER);
        result.append(" DOCNUM=").append(DOCNUM);
        result.append(" ]");
        return result.toString();
    }
}
