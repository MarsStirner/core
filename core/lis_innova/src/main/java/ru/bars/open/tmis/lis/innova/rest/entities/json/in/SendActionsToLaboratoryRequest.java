package ru.bars.open.tmis.lis.innova.rest.entities.json.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 12.05.2016, 17:51 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendActionsToLaboratoryRequest {
    /**
     * Спсиок идентифкаторов заборов для отправки LIST(TakenTissueJournal.id)
     */
    @JsonProperty("ids")
    private List<Integer> ids = new ArrayList<>();

    public SendActionsToLaboratoryRequest() {
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(final List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"ids\":").append(ids);
        sb.append('}');
        return sb.toString();
    }
}
