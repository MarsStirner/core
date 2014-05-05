package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Upatov Egor <br>
 * Date: 25.06.13, 18:05 <br>
 * Company: Korus Consulting IT <br>
 * Description: Таблица для хранения связи категорий помощи и способов оплат <br>
 */
@Entity
@Table(name = "MedicalKindUnit", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "MedicalKindUnit.findAll", query = "SELECT mku FROM MedicalKindUnit mku"),

                @NamedQuery(name = "MedicalKindUnit.findByMedicalKindAndMedicalAidUnit",
                        query = "SELECT mku FROM MedicalKindUnit mku " +
                                "WHERE mku.medicalKind = :medicalKind " +
                                "AND mku.medicalAidUnit = :medicalAidUnit"),

                @NamedQuery(name = "MedicalKindUnit.findByEventType",
                        query = "SELECT mku FROM MedicalKindUnit mku " +
                                "WHERE mku.eventType = :eventType"),

                @NamedQuery(name = "MedicalKindUnit.findByEventTypeAndMedicalKindAndMedicalAidUnit",
                        query = "SELECT mku FROM MedicalKindUnit  mku " +
                                "WHERE mku.eventType = :eventType " +
                                "AND mku.medicalKind = :medicalKind " +
                                "AND mku.medicalAidUnit = :medicalAidUnit")
        })
@XmlType(name = "MedicalKindUnit")
@XmlRootElement(name = "MedicalKindUnit")
public class MedicalKindUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rbMedicalKind_id")
    private RbMedicalKind medicalKind;

    @ManyToOne
    @JoinColumn(name = "eventType_id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "rbMedicalAidUnit_id")
    private RbMedicalAidUnit medicalAidUnit;

    @ManyToOne
    @JoinColumn(name = "rbPayType_id")
    private RbPayType payType;

    @ManyToOne
    @JoinColumn(name = "rbTariffType_id")
    private RbTariffType tariffType;

    public MedicalKindUnit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbMedicalKind getMedicalKind() {
        return medicalKind;
    }

    public void setMedicalKind(RbMedicalKind medicalKind) {
        this.medicalKind = medicalKind;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public RbMedicalAidUnit getMedicalAidUnit() {
        return medicalAidUnit;
    }

    public void setMedicalAidUnit(RbMedicalAidUnit medicalAidUnit) {
        this.medicalAidUnit = medicalAidUnit;
    }

    public RbPayType getPayType() {
        return payType;
    }

    public void setPayType(RbPayType payType) {
        this.payType = payType;
    }

    public RbTariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(RbTariffType tariffType) {
        this.tariffType = tariffType;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalKindUnit that = (MedicalKindUnit) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
