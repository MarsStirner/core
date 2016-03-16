package ru.korus.tmis.core.ext.config.entities;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Author: Upatov Egor <br>
 * Date: 14.03.2016, 17:32 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceSettings {

    @JsonProperty("CONNECTION_URL")
    @SerializedName("CONNECTION_URL")
    private String connectionUrl;


    @JsonProperty("USERNAME")
    @SerializedName("USERNAME")
    private String userName;


    @JsonProperty("PASSWORD")
    @SerializedName("PASSWORD")
    private String password;


    @JsonProperty("DRIVER_CLASS_NAME")
    @SerializedName("DRIVER_CLASS_NAME")
    private String driverClassName;

    public DataSourceSettings() {
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(final String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(final String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSourceSettings{");
        sb.append("connectionUrl='").append(connectionUrl).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", driverClassName='").append(driverClassName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
