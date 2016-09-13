package ru.bars.open.pacs.multivox.config.entities;

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

    @JsonProperty("AT_FLAT_CODE_PREFIX")
    @SerializedName("AT_FLAT_CODE_PREFIX")
    private String AT_FLAT_CODE_PREFIX; // "multivox_"

    @JsonProperty("APT_CODE_SEND_DATE")
    @SerializedName("APT_CODE_SEND_DATE")
    private String APT_CODE_SEND_DATE; //"multivox_send_date"

    @JsonProperty("APT_CODE_SEND_TIME")
    @SerializedName("APT_CODE_SEND_TIME")
    private String APT_CODE_SEND_TIME; //"multivox_send_time"

    @JsonProperty("APT_CODE_RESULT")
    @SerializedName("APT_CODE_RESULT")
    private String APT_CODE_RESULT;   //"multivox_result"

    @JsonProperty("APT_CODE_APP_LINK")
    @SerializedName("APT_CODE_APP_LINK")
    private String APT_CODE_APP_LINK; //"multivox_app_link"

    @JsonProperty("APV_APP_LINK")
    @SerializedName("APV_APP_LINK")
    private String APV_APP_LINK;  // "mvox: -cmd:Load -StudyExternalID:M"

    @JsonProperty("APV_RESULT")
    @SerializedName("APV_RESULT")
    private String APV_RESULT; // "http://10.1.0.124/webpacs/#/images?StudyExternalID=M"

    public Settings() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Settings{");
        sb.append("AT_FLAT_CODE_PREFIX='").append(AT_FLAT_CODE_PREFIX).append('\'');
        sb.append(", APT_CODE_SEND_DATE='").append(APT_CODE_SEND_DATE).append('\'');
        sb.append(", APT_CODE_SEND_TIME='").append(APT_CODE_SEND_TIME).append('\'');
        sb.append(", APT_CODE_RESULT='").append(APT_CODE_RESULT).append('\'');
        sb.append(", APT_CODE_APP_LINK='").append(APT_CODE_APP_LINK).append('\'');
        sb.append(", APV_APP_LINK='").append(APV_APP_LINK).append('\'');
        sb.append(", APV_RESULT='").append(APV_RESULT).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAT_FLAT_CODE_PREFIX() {
        return AT_FLAT_CODE_PREFIX;
    }

    public void setAT_FLAT_CODE_PREFIX(final String AT_FLAT_CODE_PREFIX) {
        this.AT_FLAT_CODE_PREFIX = AT_FLAT_CODE_PREFIX;
    }

    public String getAPT_CODE_SEND_DATE() {
        return APT_CODE_SEND_DATE;
    }

    public void setAPT_CODE_SEND_DATE(final String APT_CODE_SEND_DATE) {
        this.APT_CODE_SEND_DATE = APT_CODE_SEND_DATE;
    }

    public String getAPT_CODE_SEND_TIME() {
        return APT_CODE_SEND_TIME;
    }

    public void setAPT_CODE_SEND_TIME(final String APT_CODE_SEND_TIME) {
        this.APT_CODE_SEND_TIME = APT_CODE_SEND_TIME;
    }

    public String getAPT_CODE_RESULT() {
        return APT_CODE_RESULT;
    }

    public void setAPT_CODE_RESULT(final String APT_CODE_RESULT) {
        this.APT_CODE_RESULT = APT_CODE_RESULT;
    }

    public String getAPT_CODE_APP_LINK() {
        return APT_CODE_APP_LINK;
    }

    public void setAPT_CODE_APP_LINK(final String APT_CODE_APP_LINK) {
        this.APT_CODE_APP_LINK = APT_CODE_APP_LINK;
    }

    public String getAPV_APP_LINK() {
        return APV_APP_LINK;
    }

    public void setAPV_APP_LINK(final String APV_APP_LINK) {
        this.APV_APP_LINK = APV_APP_LINK;
    }

    public String getAPV_RESULT() {
        return APV_RESULT;
    }

    public void setAPV_RESULT(final String APV_RESULT) {
        this.APV_RESULT = APV_RESULT;
    }


}
