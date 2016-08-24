package ru.korus.tmis.core.data.out;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 25.05.2016, 18:36 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FDRecord {
    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("order")
    @SerializedName("order")
    private Integer order;

    @JsonProperty("values")
    @SerializedName("values")
    private List<FDValue> values;


    public FDRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public List<FDValue> getValues() {
        return values;
    }

    public void setValues(final List<FDValue> values) {
        this.values = values;
    }
}
