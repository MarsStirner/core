package ru.korus.tmis.hsct.external;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Author: Upatov Egor <br>
 * Date: 15.02.2016, 13:57 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonRootName(value = "request")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    @JsonProperty("id")
    private Integer id;

    public Request() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("request{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
