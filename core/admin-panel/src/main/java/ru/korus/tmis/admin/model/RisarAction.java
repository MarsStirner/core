package ru.korus.tmis.admin.model;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.NotificationActionType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.09.14, 11:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RisarAction {

    private Integer id;

    private String name;

    private Boolean remove = false;

    private Boolean add = false;

    public RisarAction() {

    }

    public RisarAction(NotificationActionType nat) {
        this(nat.getActionType());
        remove = false;
    }

    public RisarAction(ActionType at) {
        id = at.getId();
        name = at.getName();
        add = true;
    }

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

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }
}
