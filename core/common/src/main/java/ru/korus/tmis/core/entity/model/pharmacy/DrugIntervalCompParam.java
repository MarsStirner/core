package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.RbMethodOfAdministration;

import javax.persistence.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.12.2014, 17:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "DrugIntervalCompParam")
@NamedQueries(
        {
                @NamedQuery(name = "DrugIntervalCompParam.getByDrugChartAndComp",
                        query = "SELECT dic FROM DrugIntervalCompParam dic WHERE dic.drugChart.id = :drugChartId AND dic.drugComponent.id = :drugComponentId"),
                @NamedQuery(name = "DrugIntervalCompParam.getByDrugChart",
                        query = "SELECT dic FROM DrugIntervalCompParam dic WHERE dic.drugChart.id = :drugChartId")
        }
)
public class DrugIntervalCompParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "drugChart_id", nullable = false)
    private DrugChart drugChart;

    @ManyToOne
    @JoinColumn(name = "drugComponent_id", nullable = false)
    private DrugComponent drugComponent;

    @ManyToOne
    @JoinColumn(name = "moa_id", nullable = false)
    private RbMethodOfAdministration moa;

    @Column
    private Double dose;

    @Column
    private Double voa;


    public void setDrugChart(DrugChart drugChart) {
        this.drugChart = drugChart;
    }

    public DrugChart getDrugChart() {
        return drugChart;
    }

    public void setDrugComponent(DrugComponent drugComponent) {
        this.drugComponent = drugComponent;
    }

    public DrugComponent getDrugComponent() {
        return drugComponent;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Double getDose() {
        return dose;
    }

    public void setVoa(Double voa) {
        this.voa = voa;
    }

    public Double getVoa() {
        return voa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbMethodOfAdministration getMoa() {
        return moa;
    }

    public void setMoa(RbMethodOfAdministration moa) {
        this.moa = moa;
    }
}
