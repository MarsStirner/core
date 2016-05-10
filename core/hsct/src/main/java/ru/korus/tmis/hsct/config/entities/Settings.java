package ru.korus.tmis.hsct.config.entities;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 11.03.2016, 14:49 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings {

    @JsonProperty("RECEIVE_USER")
    @SerializedName("RECEIVE_USER")
    private String receiveUser;
    @JsonProperty("RECEIVE_PASSWORD")
    @SerializedName("RECEIVE_PASSWORD")
    private String receivePassword;
    @JsonProperty("RECEIVE_ACTIVE")
    @SerializedName("RECEIVE_ACTIVE")
    private String receiveActive;

    @JsonProperty("SEND_USER")
    @SerializedName("SEND_USER")
    private String sendUser;
    @JsonProperty("SEND_PASSWORD")
    @SerializedName("SEND_PASSWORD")
    private String sendPassword;
    @JsonProperty("SEND_ACTIVE")
    @SerializedName("SEND_ACTIVE")
    private String sendActive;

    @JsonProperty("SERVICE_URL")
    @SerializedName("SERVICE_URL")
    private String serviceUrl;


    public Settings() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Settings{");
        sb.append("receiveUser='").append(receiveUser).append('\'');
        sb.append(", receivePassword='").append(receivePassword).append('\'');
        sb.append(", receiveActive='").append(receiveActive).append('\'');
        sb.append(", sendUser='").append(sendUser).append('\'');
        sb.append(", sendPassword='").append(sendPassword).append('\'');
        sb.append(", sendActive='").append(sendActive).append('\'');
        sb.append(", serviceUrl='").append(serviceUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(final String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getReceivePassword() {
        return receivePassword;
    }

    public void setReceivePassword(final String receivePassword) {
        this.receivePassword = receivePassword;
    }

    public String getReceiveActive() {
        return receiveActive;
    }

    public void setReceiveActive(final String receiveActive) {
        this.receiveActive = receiveActive;
    }


    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(final String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSendPassword() {
        return sendPassword;
    }

    public void setSendPassword(final String sendPassword) {
        this.sendPassword = sendPassword;
    }

    public String getSendActive() {
        return sendActive;
    }

    public void setSendActive(final String sendActive) {
        this.sendActive = sendActive;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
