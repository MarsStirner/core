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
@Table(name = "bbtOrganism_SensValues", catalog = "", schema = "")
public class BbtOrganismSensValues implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "bbtResult_Organism_id")
    private Integer bbtResultOrganismId;

    @Basic(optional = false)
    @Column(name = "antibiotic_id")
    private Integer antibioticId;

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

    public void setBbtResultOrganismId(Integer bbtResultOrganismId) {
        this.bbtResultOrganismId = bbtResultOrganismId;
    }

    public void setAntibioticId(Integer antibioticId) {
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

    public Integer getBbtResultOrganismId() {
        return bbtResultOrganismId;
    }

    public Integer getAntibioticId() {
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
        sb.append(", bbtResultOrganismId=").append(bbtResultOrganismId);
        sb.append(", antibioticId=").append(antibioticId);
        sb.append(", mic='").append(mic).append('\'');
        sb.append(", activity='").append(activity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
