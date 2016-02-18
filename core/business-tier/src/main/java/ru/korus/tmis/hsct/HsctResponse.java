package ru.korus.tmis.hsct;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 20:33 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctResponse {
    @JsonProperty("error")
    private boolean error;

    @JsonProperty("error_message")
    private String errorMessage;

    public HsctResponse() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(final boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctResponse{");
        sb.append("error=").append(error);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
