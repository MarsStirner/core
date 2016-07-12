package ru.bars.open.tmis.lis.innova.config.entities;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Author: Upatov Egor <br>
 * Date: 11.03.2016, 14:46 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("meta")
public class Meta {
    @JsonProperty("code")
    @SerializedName("code")
    private Integer code;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    public Meta() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Meta{");
        sb.append("code=").append(code);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
