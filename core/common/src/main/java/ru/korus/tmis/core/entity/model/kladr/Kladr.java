package ru.korus.tmis.core.entity.model.kladr;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.client.RestTemplate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class  Kladr {

    private static final long serialVersionUID = 1L;

    //Поля таблицы

    private String code;

    private String name;

    @JsonProperty("shorttype")
    private String socr;


    @JsonProperty("postindex")
    private String index;

    private String gninmb;

    private String uno;

    @JsonProperty("okato")
    private String ocatd;

    @JsonProperty("codestate")
    private String status;

    @JsonProperty("identparent")
    private String parent;

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
        return parent == null ? "" : parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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
        return "ru.korus.tmis.core.database.model.KladrRb[code=" + code + "]";
    }

    public String print() {
        final StringBuilder sb = new StringBuilder("Kladr{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", socr='").append(socr).append('\'');
        sb.append(", gninmb='").append(gninmb).append('\'');
        sb.append(", uno='").append(uno).append('\'');
        sb.append(", ocatd='").append(ocatd).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
