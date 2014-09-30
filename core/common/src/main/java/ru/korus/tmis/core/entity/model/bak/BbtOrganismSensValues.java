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
@Table(name = "bbtOrganism_SensValues")
@NamedQueries(
        {
                @NamedQuery(name = "BbtOrganismSensValues.deleteBy_bbtResultOrganismId", query = "DELETE FROM BbtOrganismSensValues a WHERE a.bbtResultOrganism.id = :id")
        })
public class BbtOrganismSensValues implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbtResult_Organism_id")
    private BbtResultOrganism bbtResultOrganism;

    @OneToOne(optional = false)
    @JoinColumn(name = "antibiotic_id", unique = true, nullable = false)
    private RbAntibiotic antibioticId;

    @Basic(optional = false)
    @Column(name = "MIC")
    private String mic;

    @Basic(optional = false)
    @Column(name = "activity")
    private String activity;

    public BbtOrganismSensValues() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBbtResultOrganism(BbtResultOrganism bbtResultOrganism) {
        this.bbtResultOrganism = bbtResultOrganism;
    }

    public BbtResultOrganism getBbtResultOrganism() {
        return bbtResultOrganism;
    }

    public void setAntibioticId(RbAntibiotic antibioticId) {
        this.antibioticId = antibioticId;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public RbAntibiotic getAntibioticId() {
        return antibioticId;
    }

    public String getMic() {
        return mic;
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BbtOrganismSensValues{");
        sb.append("id=").append(id);
        sb.append(", bbtResultOrganismId=").append(bbtResultOrganism);
        sb.append(", antibioticId=").append(antibioticId);
        sb.append(", mic='").append(mic).append('\'');
        sb.append(", activity='").append(activity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
