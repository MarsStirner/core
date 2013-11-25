package ru.korus.tmis.laboratory.bak.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.11.13, 19:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Diagnoz {
    private String code;
    private String name;

    public Diagnoz(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Diagnoz{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
