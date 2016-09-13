package ru.bars.open.tmis.lis.innova.config.entities;

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

    @JsonProperty("SERVICE_URL")
    @SerializedName("SERVICE_URL")
    private String SERVICE_URL;

    @JsonProperty("LAB_CODE")
    @SerializedName("LAB_CODE")
    private String LAB_CODE;

    @JsonProperty("ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE")
    @SerializedName("ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE")
    private String ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE;

    public Settings() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Settings{");
        sb.append("SERVICE_URL='").append(SERVICE_URL).append('\'');
        sb.append(", LAB_CODE='").append(LAB_CODE).append('\'');
        sb.append(", ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE='").append(ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getSERVICE_URL() {
        return SERVICE_URL;
    }

    public void setSERVICE_URL(final String SERVICE_URL) {
        this.SERVICE_URL = SERVICE_URL;
    }

    public String getLAB_CODE() {
        return LAB_CODE;
    }

    public void setLAB_CODE(final String LAB_CODE) {
        this.LAB_CODE = LAB_CODE;
    }

    public String getADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE() {
        return ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE;
    }

    public void setADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE(final String ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE) {
        this.ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE = ADMINISTRATIVE_PERMISSION_RB_FINANCE_CODE;
    }
}
