package ru.korus.tmis.core.ext.config;

import org.springframework.stereotype.Component;

/**
 * Author: Upatov Egor <br>
 * Date: 11.03.2016, 14:14 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Component
public class ConfigManager {

    private String ambulatoryUrl;

    public ConfigManager(final Settings settings) {
        this.ambulatoryUrl = settings.getAmbulatoryUrl();
    }

    public ConfigManager() {
    }

    public String getAmbulatoryUrl() {
        return ambulatoryUrl;
    }

    public void setAmbulatoryUrl(final String ambulatoryUrl) {
        this.ambulatoryUrl = ambulatoryUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigManager{");
        sb.append("ambulatoryUrl='").append(ambulatoryUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
