package ru.korus.tmis.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        31.05.13, 15:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlRootElement
public class JsonSetting {
    private static final Logger logger = LoggerFactory.getLogger(JsonSetting.class);

    private String path;

    private String value;

    private String description;

    public JsonSetting(String path, String value, String description) {
        this.path = path;
        this.value = value;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JsonSetting{");
        sb.append("path='").append(path).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
