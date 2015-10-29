package ru.korus.tmis.core.ext.ambulatoryService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Author: Upatov Egor <br>
 * Date: 29.10.2015, 14:29 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private Integer code;

    private String name;

    public Meta() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
