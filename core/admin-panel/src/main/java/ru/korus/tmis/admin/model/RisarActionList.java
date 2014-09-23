package ru.korus.tmis.admin.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.09.14, 11:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RisarActionList {

    private Integer id;

    private String name;

    private Boolean remove = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRemove() {
        return remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }
}
