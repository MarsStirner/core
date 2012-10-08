package ru.korus.tmis.core.entity.model.kladr;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "STREET", catalog = "", schema = "kladr")
@NamedQueries(
        {
                @NamedQuery(name = "Street.findAll", query = "SELECT st FROM Street st")
        })
@XmlType(name = "street")
@XmlRootElement(name = "street")
public class Street
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //Поля таблицы

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE")
    private String code;

    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @Column(name = "SOCR")
    private String socr;

    @Basic(optional = false)
    @Column(name = "INDEX")
    private String index;

    @Basic(optional = false)
    @Column(name = "GNINMB")
    private String gninmb;

    @Basic(optional = false)
    @Column(name = "UNO")
    private String uno;

    @Basic(optional = false)
    @Column(name = "OCATD")
    private String ocatd;

    @Basic(optional = false)
    @Column(name = "infis")
    private String infis;

    //Конструкторы

    public Street() {
    }

    public Street(String code) {
        this.code = code;
    }

    //Методы доступа к приватным данным

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocr() {
        return socr;
    }

    public void setSocr(String socr) {
        this.socr = socr;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getGninmb() {
        return gninmb;
    }

    public void setGninmb(String gninmb) {
        this.gninmb = gninmb;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getOcatd() {
        return ocatd;
    }

    public void setOcatd(String ocatd) {
        this.ocatd = ocatd;
    }

    public String getInfis() {
        return infis;
    }

    public void setInfis(String infis) {
        this.infis = infis;
    }

    //Переопределенные методы

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Street)) {
            return false;
        }
        Street other = (Street) object;
        if (this.code == null && other.code == null && this != other) {
            return false;
        }
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.Street[code=" + code + "]";
    }
}
