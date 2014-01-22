package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 17:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "bbtResult_Text")
public class BbtResultText implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "action_id")
    private Integer actionId;

    @Basic(optional = false)
    @Column(name = "valueText")
    private String valueText;


    public BbtResultText() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public Integer getId() {
        return id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public String getValueText() {
        return valueText;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BbtResultText{");
        sb.append("id=").append(id);
        sb.append(", actionId=").append(actionId);
        sb.append(", valueText='").append(valueText).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
