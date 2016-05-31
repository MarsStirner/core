package ru.korus.tmis.core.data.out;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 25.05.2016, 18:41 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FDValue {
    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("fdField_id")
    @SerializedName("fdField_id")
    private Integer fdFieldId;

    @JsonProperty("value")
    @SerializedName("value")
    private String value;

    public FDValue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getFdFieldId() {
        return fdFieldId;
    }

    public void setFdFieldId(final Integer fdFieldId) {
        this.fdFieldId = fdFieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
