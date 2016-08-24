package ru.korus.tmis.core.data.out;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import ru.korus.tmis.core.data.FieldDescriptionData;


import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 25.05.2016, 18:28 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FDContainer {

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("code")
    @SerializedName("code")
    private String code;

    @JsonProperty("fields")
    @SerializedName("fields")
    private List<FieldDescriptionData> fields;

    @JsonProperty("records")
    @SerializedName("records")
    private List<FDRecord> records;

    public FDContainer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<FieldDescriptionData> getFields() {
        return fields;
    }

    public void setFields(final List<FieldDescriptionData> fields) {
        this.fields = fields;
    }

    public List<FDRecord> getRecords() {
        return records;
    }

    public void setRecords(final List<FDRecord> records) {
        this.records = records;
    }
}
