package ru.korus.tmis.laboratory.bak.ws.server.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Doctor {
    /**
     * Идентификатор врача
     */
    private int id;
    /**
     * ФИО врача
     */
    private String fullname;
    /**
     * Код ЛИС
     */
    private String codeLis;

    public Doctor(int id, String fullname, String codeLis) {
        this.id = id;
        this.fullname = fullname;
        this.codeLis = codeLis;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCodeLis() {
        return codeLis;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Doctor{");
        sb.append("id=").append(id);
        sb.append(", fullname='").append(fullname).append('\'');
        sb.append(", codeLis='").append(codeLis).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
