package ru.korus.tmis.core.ext.config;

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

    @JsonProperty("AMBULATORY_URL")
    @SerializedName("AMBULATORY_URL")
    private String ambulatoryUrl;

    public String getAmbulatoryUrl() {
        return ambulatoryUrl;
    }

    public void setAmbulatoryUrl(final String ambulatoryUrl) {
        this.ambulatoryUrl = ambulatoryUrl;
    }

    public Settings() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Settings{");
        sb.append("ambulatoryUrl='").append(ambulatoryUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
