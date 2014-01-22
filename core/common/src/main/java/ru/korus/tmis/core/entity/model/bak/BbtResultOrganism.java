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
@Table(name = "bbtResult_Organism")
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

    public Integer getOrganismId() {
        return organismId;
    }

    public String getConcentration() {
        return concentration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BbtResultOrganism{");
        sb.append("id=").append(id);
        sb.append(", actionId=").append(actionId);
        sb.append(", organismId=").append(organismId);
        sb.append(", concentration='").append(concentration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
