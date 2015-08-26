package ru.korus.tmis.core.entity.model.kladr;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Street
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //Поля таблицы

    private String code;

    private String name;

    @JsonProperty("socr")
    private String shorttype;

    @JsonProperty("index")
    private String postindex;

    private String gninmb;

    private String uno;

    @JsonProperty("ocatd")
    private String okato;

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
        return shorttype;
    }

    public String getIndex() {
        return postindex;
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
        return okato;
    }

    public String getInfis() {
        return infis;
    }

    public void setInfis(String infis) {
        this.infis = infis;
    }

    public String getShorttype() {
        return shorttype;
    }

    public void setShorttype(String shorttype) {
        this.shorttype = shorttype;
    }

    public String getPostindex() {
        return postindex;
    }

    public void setPostindex(String postindex) {
        this.postindex = postindex;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
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

    public String print() {
        final StringBuilder sb = new StringBuilder("Street{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", socr='").append(shorttype).append('\'');
        sb.append(", index='").append(postindex).append('\'');
        sb.append(", gninmb='").append(gninmb).append('\'');
        sb.append(", uno='").append(uno).append('\'');
        sb.append(", ocatd='").append(okato).append('\'');
        sb.append(", infis='").append(infis).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
