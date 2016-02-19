package ru.korus.tmis.hsct.external;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 18.02.2016, 21:27 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctExternalRequestForComplete {
    @JsonProperty("request")
    private HsctExternalRequestFroCompleteWrapper request;

    public HsctExternalRequestForComplete() {
    }

    public HsctExternalRequestFroCompleteWrapper getRequest() {
        return request;
    }

    public void setRequest(final HsctExternalRequestFroCompleteWrapper request) {
        this.request = request;
    }
}

