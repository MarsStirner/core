package ru.korus.tmis.lis.innova.rest.entities.json.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 12.05.2016, 18:59 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendActionsToLaboratoryResponse {

    @JsonProperty("data")
    private Map<Integer, String> data = new HashMap<>();


    public Map<Integer, String> getData() {
        return data;
    }

    public void setData(final Map<Integer, String> data) {
        this.data = data;
    }

    public SendActionsToLaboratoryResponse() {
    }

    public SendActionsToLaboratoryResponse(final Map<Integer, String> data) {

        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
