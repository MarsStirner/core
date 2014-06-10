package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(optional = false)
    @JoinColumn(name = "organism_id", unique = true, nullable = false)
    private RbMicroorganism organism;

    @Basic(optional = false)
    @Column(name = "concentration")
    private String concentration;

    @OneToMany(mappedBy = "bbtResultOrganism")
    //@JoinColumn(name = "organism_id", referencedColumnName = "bbtResult_Organism_id")
    private List<BbtOrganismSensValues> sensValues = new ArrayList<BbtOrganismSensValues>();

    public BbtResultOrganism() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public void setOrganism(RbMicroorganism organismId) {
        this.organism = organismId;
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

    public RbMicroorganism getOrganism() {
        return organism;
    }

    public String getConcentration() {
        return concentration;
    }

    public List<BbtOrganismSensValues> getSensValues() {
        return sensValues;
    }

    public void setSensValues(List<BbtOrganismSensValues> sensValues) {
        this.sensValues = sensValues;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BbtResultOrganism{");
        sb.append("id=").append(id);
        sb.append(", actionId=").append(actionId);
        sb.append(", organismId=").append(organism);
        sb.append(", concentration='").append(concentration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
