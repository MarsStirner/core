package ru.korus.tmis.hsct.external;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 15.02.2016, 16:29 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class HsctExternalRequestWrapper {
    @JsonProperty("request")
    @SerializedName("request")
    private HsctExternalRequest request;

    public HsctExternalRequestWrapper() {
    }

    public HsctExternalRequest getRequest() {
        return request;
    }

    public void setRequest(final HsctExternalRequest request) {
        this.request = request;
    }
}
