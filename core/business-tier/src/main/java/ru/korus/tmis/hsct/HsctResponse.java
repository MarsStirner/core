package ru.korus.tmis.hsct;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 20:33 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctResponse {
    @XmlElement(name="error")
    private boolean error;

    @XmlElement(name="error_message")
    private String errorMessage;

    @XmlElement(name="hsct_request_id")
    private Integer hsctRequestId;

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

    public Integer getHsctRequestId() {
        return hsctRequestId;
    }

    public void setHsctRequestId(final Integer hsctRequestId) {
        this.hsctRequestId = hsctRequestId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctResponse{");
        sb.append("error=").append(error);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", hsctRequestId=").append(hsctRequestId);
        sb.append('}');
        return sb.toString();
    }
}
