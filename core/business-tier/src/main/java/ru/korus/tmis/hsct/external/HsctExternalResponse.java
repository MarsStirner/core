package ru.korus.tmis.hsct.external;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 15.02.2016, 13:57 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonRootName(value = "request")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctExternalResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("error")
    private List<HsctExternalRequest> error;

    @JsonIgnore
    private String raw;


    public HsctExternalResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public List<HsctExternalRequest> getError() {
        return error;
    }

    public void setError(final List<HsctExternalRequest> error) {
        this.error = error;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(final String raw) {
        this.raw = raw;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctExternalResponse{");
        sb.append("id=").append(id);
        sb.append(", error=").append(error);
        sb.append('}');
        return sb.toString();
    }
}
