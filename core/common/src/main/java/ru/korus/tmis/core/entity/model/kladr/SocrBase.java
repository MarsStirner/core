package ru.korus.tmis.core.entity.model.kladr;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "SOCRBASE", catalog = "", schema = "kladr")
@NamedQueries(
        {
                @NamedQuery(name = "SocrBase.findAll", query = "SELECT kl FROM SocrBase kl")
        })
@XmlType(name = "socrbase")
@XmlRootElement(name = "socrbase")
public class SocrBase
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //Поля таблицы

    @Basic(optional = false)
    @Column(name = "LEVEL")
    private String level;

    @Basic(optional = false)
    @Column(name = "SCNAME")
    private String scName;

    @Basic(optional = false)
    @Column(name = "SOCRNAME")
    private String socrName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KOD_T_ST")
    private String kodtst;

    @Basic(optional = false)
    @Column(name = "infisCODE")
    private String infisCode;

    //Конструкторы

    public SocrBase() {
    }

    //Методы доступа к приватным данным

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScname() {
        return scName;
    }

    public void setScname(String scname) {
        this.scName = scname;
    }

    public String getSocrname() {
        return socrName;
    }

    public void setSocrname(String socrname) {
        this.socrName = socrname;
    }

    public String getKodtst() {
        return kodtst;
    }

    public void setKodtst(String kodtst) {
        this.kodtst = kodtst;
    }

    public String getInfisCode() {
        return infisCode;
    }

    public void setInfisCode(String infisCode) {
        this.infisCode = infisCode;
    }

    //Переопределенные методы

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodtst != null ? kodtst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SocrBase)) {
            return false;
        }
        SocrBase other = (SocrBase) object;
        if (this.kodtst == null && other.kodtst == null && this != other) {
            return false;
        }
        if ((this.kodtst == null && other.kodtst != null) || (this.kodtst != null && !this.kodtst.equals(other.kodtst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.SocrBase[kodtst=" + kodtst + "]";
    }
}
