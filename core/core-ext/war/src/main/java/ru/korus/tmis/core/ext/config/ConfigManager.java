package ru.korus.tmis.core.ext.config;

import ru.korus.tmis.core.ext.config.entities.DataSourceSettings;
import ru.korus.tmis.core.ext.config.entities.Settings;

/**
 * Author: Upatov Egor <br>
 * Date: 11.03.2016, 14:14 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */

public class ConfigManager {

    private String ambulatoryUrl;

    private DataSourceSettings datasource;

    public ConfigManager(final Settings settings) {
        this.ambulatoryUrl = settings.getAmbulatoryUrl();
        this.datasource = settings.getDataSource();
    }

    public ConfigManager() {
    }

    public String getAmbulatoryUrl() {
        return ambulatoryUrl;
    }

    public void setAmbulatoryUrl(final String ambulatoryUrl) {
        this.ambulatoryUrl = ambulatoryUrl;
    }

    public DataSourceSettings getDatasource() {
        return datasource;
    }

    public void setDatasource(final DataSourceSettings datasource) {
        this.datasource = datasource;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigManager{");
        sb.append("ambulatoryUrl='").append(ambulatoryUrl).append('\'');
        sb.append(", datasource=").append(datasource);
        sb.append('}');
        return sb.toString();
    }
}
