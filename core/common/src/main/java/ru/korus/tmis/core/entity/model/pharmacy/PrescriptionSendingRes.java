package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.DrugChart;
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
@Table
@NamedQueries(
        {
                @NamedQuery(name = "PrescriptionSendingRes.findByIntervalAndNomen", query =
                        "SELECT p FROM PrescriptionSendingRes p WHERE p.rlsNomen.id = :nomenId AND p.drugChart.id = :intervalId")
        })
public class PrescriptionSendingRes {

    @Id
    @Column(name = "id", columnDefinition = "COMMENT 'Идентификатор события {Event.event_id}'")
    private int id;

    @ManyToOne
    @JoinColumn(name = "nomen_id", nullable = false, insertable = false, updatable = false)
    private RlsNomen rlsNomen;

    @ManyToOne
    @JoinColumn(name = "interval_id", nullable = false, insertable = false, updatable = false)
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

    public RlsNomen getRlsNomen() {
        return rlsNomen;
    }

    public void setRlsNomen(RlsNomen rlsNomen) {
        this.rlsNomen = rlsNomen;
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
