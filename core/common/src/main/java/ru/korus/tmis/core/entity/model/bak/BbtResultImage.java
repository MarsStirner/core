package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 17:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "bbtResult_Image", catalog = "", schema = "")
public class BbtResultImage implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "action_id")
    private Integer actionId;

    @Basic(optional = false)
    @Column(name = "idx")
    private Integer idx;

    @Basic(optional = false)
    @Column(name = "description")
    private String description;

//    @Column(name = "image")
//    private Blob image;


    public BbtResultImage() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getActionId() {
        return actionId;
    }

    public Integer getIdx() {
        return idx;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "BbtResultImage{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", idx=" + idx +
                ", description='" + description + '\'' +
                '}';
    }
}
