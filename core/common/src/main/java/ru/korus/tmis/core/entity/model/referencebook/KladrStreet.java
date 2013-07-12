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
 */
@Entity
@Table(name = "rb_KladrStreet", catalog = "", schema = "")
public class KladrStreet implements Serializable {
    //  <rec name="Абадзехская" socr="ул" code="01000001000000100" index="385000" gninmb="0105" uno="" ocatd="79401000000"/>
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

    public KladrStreet() {
    }

    public KladrStreet(String code, String name, String socr, String idx, String gninmb, String uno, String ocatd) {
        this.code = code;
        this.name = name;
        this.socr = socr;
        this.idx = idx;
        this.gninmb = gninmb;
        this.uno = uno;
        this.ocatd = ocatd;
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

    public String getIdx() {
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

    @Override
    public String toString() {
        return "KladrStreet{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", socr='" + socr + '\'' +
                ", index='" + idx + '\'' +
                ", gninmb='" + gninmb + '\'' +
                ", uno='" + uno + '\'' +
                ", ocatd='" + ocatd + '\'' +
                '}';
    }
}
