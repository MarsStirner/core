package ru.korus.tmis.laboratory.bak.ws.server.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 13:15 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Microorganism {
    private String code;
    private String name;
    private String comment;
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
        return antibioticList;
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
