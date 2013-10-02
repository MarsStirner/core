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
@Table(name = "bbtResult_Text", catalog = "", schema = "")
public class BbtResultText implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "action_id")
    private Integer actionId;

    @Basic(optional = false)
    @Column(name = "idx")
    private Integer index;

    @Basic(optional = false)
    @Column(name = "valueText")
    private String valueText;


    public BbtResultText() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public Integer getIndex() {
        return index;
    }

    public String getValueText() {
        return valueText;
    }

    @Override
    public String toString() {
        return "BbtResultText{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", index=" + index +
                ", valueText='" + valueText + '\'' +
                '}';
    }
}
