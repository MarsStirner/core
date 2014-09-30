package ru.korus.tmis.lis.data.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 13:16 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Модель Антибиотика <br>
 */
public class Antibiotic {
    /**
     * Код антибиотика
     */
    private String code;
    /**
     * Название антибиотика
     */
    private String name;
    /**
     * Концентрация
     */
    private String concentration = "";
    /**
     * Чувствительность R,S
     */
    private String sensitivity = "";
    /**
     * Комментарий БАК
     */
    private String comment = "";

    public Antibiotic(String code, String name) {
        this.code = code;
        this.name = name;
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

    public String getComment() {
        return comment;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Antibiotic{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", concentration='").append(concentration).append('\'');
        sb.append(", sensitivity='").append(sensitivity).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
