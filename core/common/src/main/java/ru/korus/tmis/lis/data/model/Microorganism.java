package ru.korus.tmis.lis.data.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 13:15 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Модель микроорганизмов в результате БАК-посева<br>
 */
public class Microorganism {
    /**
     * Код микроорганизма
     */
    private String code;
    /**
     * Название микроорганизма
     */
    private String name;
    /**
     * Комментарий БАК
     */
    private String comment;
    /**
     * Список применявшихся антибиотиков
     */
    private List<Antibiotic> antibioticList;

    public Microorganism(String code, String name, String comment) {
        this.code = code;
        this.name = name;
        this.comment = comment;
        this.antibioticList = new LinkedList<Antibiotic>();
    }

    public void addAntibiotic(final Antibiotic antibiotic) {
        if (antibiotic != null) {
            antibioticList.add(antibiotic);
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<Antibiotic> getAntibioticList() {
        return new ArrayList<Antibiotic>(antibioticList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Microorganism{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", antibioticList=").append(antibioticList);
        sb.append('}');
        return sb.toString();
    }
}
