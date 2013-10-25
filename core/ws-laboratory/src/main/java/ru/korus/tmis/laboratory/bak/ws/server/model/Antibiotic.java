package ru.korus.tmis.laboratory.bak.ws.server.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 13:16 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Antibiotic {
    private String code;
    private String name;
    private String concentration;
    private String sensitivity;

    public Antibiotic(String code, String name, String concentration, String sensitivity) {
        this.code = code;
        this.name = name;
        this.concentration = concentration;
        this.sensitivity = sensitivity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getConcentration() {
        return concentration;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Antibiotic{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", concentration='").append(concentration).append('\'');
        sb.append(", sensitivity='").append(sensitivity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
