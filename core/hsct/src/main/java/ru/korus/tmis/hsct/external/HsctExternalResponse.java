package ru.korus.tmis.hsct.external;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 18.03.2016, 16:43 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctExternalResponse {
    @JsonProperty("request")
    private Request request;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonIgnore
    private boolean removeFromQueue = false;

    public HsctExternalResponse() {
    }


    public boolean isRemoveFromQueue() {
        return removeFromQueue;
    }

    public void setRemoveFromQueue(final boolean removeFromQueue) {
        this.removeFromQueue = removeFromQueue;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(final Request request) {
        this.request = request;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
