package ru.korus.tmis.laboratory.bak.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.11.13, 19:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class IndicatorMetodic {

    private String name;
    private String code;

    public IndicatorMetodic(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IndicatorMetodic{");
        sb.append("name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
