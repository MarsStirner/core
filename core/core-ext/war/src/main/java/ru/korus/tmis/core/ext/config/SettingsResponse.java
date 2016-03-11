package ru.korus.tmis.core.ext.config;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 11.03.2016, 14:43 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsResponse {
    @JsonProperty("meta")
    @SerializedName("meta")
    private Meta meta;

    @JsonProperty("result")
    @SerializedName("result")
    private Settings result;

    public SettingsResponse() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(final Meta meta) {
        this.meta = meta;
    }

    public Settings getResult() {
        return result;
    }

    public void setResult(final Settings result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SettingsResponse{");
        sb.append("meta=").append(meta);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
