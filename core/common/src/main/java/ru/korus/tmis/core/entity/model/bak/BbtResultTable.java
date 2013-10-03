package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 17:37 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "bbtResultTable", catalog = "", schema = "")
public class BbtResultTable implements Serializable {

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
    @Column(name = "indicator_id")
    private Integer indicatorId;

    @Basic(optional = false)
    @Column(name = "normString")
    private String normString;

    @Basic(optional = false)
    @Column(name = "normalityIndex")
    private Double normalityIndex;

    @Basic(optional = false)
    @Column(name = "unit")
    private String unit;

    @Basic(optional = false)
    @Column(name = "signDateTime")
    @Temporal(TemporalType.DATE)
    private Date signDateTime;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @Column(name = "comment")
    private String comment;

    public BbtResultTable() {

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

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public String getNormString() {
        return normString;
    }

    public Double getNormalityIndex() {
        return normalityIndex;
    }

    public String getUnit() {
        return unit;
    }

    public Date getSignDateTime() {
        return signDateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "BbtResultTable{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", idx=" + idx +
                ", indicatorId=" + indicatorId +
                ", normString='" + normString + '\'' +
                ", normalityIndex=" + normalityIndex +
                ", unit='" + unit + '\'' +
                ", signDateTime=" + signDateTime +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
