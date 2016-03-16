package ru.korus.tmis.core.ext.config.entities;

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


    @JsonProperty("DATASOURCE")
    @SerializedName("DATASOURCE")
    private DataSourceSettings dataSource;


    public String getAmbulatoryUrl() {
        return ambulatoryUrl;
    }

    public void setAmbulatoryUrl(final String ambulatoryUrl) {
        this.ambulatoryUrl = ambulatoryUrl;
    }

    public DataSourceSettings getDataSource() {
        return dataSource;
    }

    public void setDataSource(final DataSourceSettings dataSource) {
        this.dataSource = dataSource;
    }

    public Settings() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Settings{");
        sb.append("ambulatoryUrl='").append(ambulatoryUrl).append('\'');
        sb.append(", dataSource=").append(dataSource);
        sb.append('}');
        return sb.toString();
    }
}
