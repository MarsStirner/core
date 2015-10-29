package ru.korus.tmis.core.ext.ambulatoryService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Author: Upatov Egor <br>
 * Date: 29.10.2015, 14:24 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condition {
    private String age;
    private Integer sex;

    public Condition() {
    }

    public String getAge() {
        return age;
    }

    public void setAge(final String age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(final Integer sex) {
        this.sex = sex;
    }
}
