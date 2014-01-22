package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author:   Dmitriy E. Nosov <br>
 * Date:     05.07.13, 16:58 <br>
 * Company:  Korus Consulting IT <br>
 * Revision: $Id$ <br>
 * Description: <br>
 * <rec name="Адыгея" socr="Респ" code="0100000000000" index="385000" gninmb="0100" uno="" ocatd="79000000000" status="0"/>
 */
@Entity
@Table(name = "rb_Kladr")
public class KladrRb implements Serializable {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "socr")
    private String socr;

    @Column(name = "idx")
    private String idx;

    @Column(name = "gninmb")
    private String gninmb;

    @Column(name = "uno")
    private String uno;

    @Column(name = "ocatd")
    private String ocatd;

    @Column(name = "status")
    private String status;

    public KladrRb() {
    }

    public KladrRb(String code, String name, String socr, String index, String gninmb, String uno, String ocatd, String status) {
        this.code = code;
        this.name = name;
        this.socr = socr;
        this.idx = index;
        this.gninmb = gninmb;
        this.uno = uno;
        this.ocatd = ocatd;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSocr() {
        return socr;
    }

    public String getIndex() {
        return idx;
    }

    public String getGninmb() {
        return gninmb;
    }

    public String getUno() {
        return uno;
    }

    public String getOcatd() {
        return ocatd;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "KladrRb{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", socr='" + socr + '\'' +
                ", index='" + idx + '\'' +
                ", gninmb='" + gninmb + '\'' +
                ", uno='" + uno + '\'' +
                ", ocatd='" + ocatd + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}