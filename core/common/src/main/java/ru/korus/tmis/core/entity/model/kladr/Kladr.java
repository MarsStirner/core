package ru.korus.tmis.core.entity.model.kladr;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "KLADR", catalog = "", schema = "kladr")
@NamedQueries(
        {
                @NamedQuery(name = "Kladr.findAll", query = "SELECT kl FROM Kladr kl")
        })
@XmlType(name = "kladr")
@XmlRootElement(name = "kladr")
public class Kladr
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //Поля таблицы

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "STATUS")
    private String status;

    @Basic(optional = false)
    @Column(name = "parent")
    private String parent;

    @Basic(optional = false)
    @Column(name = "infis")
    private String infis;

    @Basic(optional = false)
    @Column(name = "prefix")
    private String prefix;

    //Конструкторы

    public Kladr() {
    }

    public Kladr(String code) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getInfis() {
        return infis;
    }

    public void setInfis(String infis) {
        this.infis = infis;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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
        if (!(object instanceof Kladr)) {
            return false;
        }
        Kladr other = (Kladr) object;
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
        return "ru.korus.tmis.core.database.model.Kladr[code=" + code + "]";
    }
}
