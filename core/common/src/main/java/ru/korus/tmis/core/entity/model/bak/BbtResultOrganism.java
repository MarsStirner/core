package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 17:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "bbtResult_Organism", catalog = "", schema = "")
public class BbtResultOrganism implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "organism_id")
    private Integer organismId;

    @Basic(optional = false)
    @Column(name = "concentration")
    private String concentration;

    public BbtResultOrganism() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public void setOrganismId(Integer organismId) {
        this.organismId = organismId;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
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

    public Integer getOrganismId() {
        return organismId;
    }

    public String getConcentration() {
        return concentration;
    }

    @Override
    public String toString() {
        return "BbtResultOrganism{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", idx=" + idx +
                ", organismId=" + organismId +
                ", concentration='" + concentration + '\'' +
                '}';
    }
}
