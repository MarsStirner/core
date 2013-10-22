package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.DrugChart;
import ru.korus.tmis.core.entity.model.DrugComponent;
import ru.korus.tmis.core.entity.model.RlsNomen;

import javax.persistence.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.10.13, 13:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Результат передачи интервалов назначений и исполнений в 1С
 */
@Entity
@Table(name = "PrescriptionSendingRes")
@NamedQueries(
        {
                @NamedQuery(name = "PrescriptionSendingRes.findByIntervalAndNomen", query =
                        "SELECT p FROM PrescriptionSendingRes p WHERE p.drugComponent.id = :compId AND p.drugChart.id = :intervalId")
        })
public class PrescriptionSendingRes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "drugComponent_id", nullable = true)
    private DrugComponent drugComponent;

    @ManyToOne
    @JoinColumn(name = "interval_id", nullable = true)
    private DrugChart drugChart;

    @Column(name = "uuid", columnDefinition = "VARCHAR(100) NULL DEFAULT NULL COMMENT 'идентификатор интервала 1С'")
    private String uuid;

    @Column(name = "version", columnDefinition = "INT(11) NULL DEFAULT NULL COMMENT 'текущая версия'")
    private Integer version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DrugComponent getDrugComponent() {
        return drugComponent;
    }

    public void setDrugComponent(DrugComponent drugComponent) {
        this.drugComponent = drugComponent;
    }

    public DrugChart getDrugChart() {
        return drugChart;
    }

    public void setDrugChart(DrugChart drugChart) {
        this.drugChart = drugChart;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
