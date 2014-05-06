package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 12:30 <br>
 * Company: Korus Consulting IT <br>
 * Description: Справочник видов тарификации услуг <br>
 */
@Entity
@Table(name = "rbTariffType", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "rbTariffType.findAll", query = "SELECT f FROM RbTariffType f"),
                @NamedQuery(name = "rbTariffType.findByMedicalKindAndMedicalAidUnit",
                        query = "SELECT mku.tariffType FROM MedicalKindUnit mku WHERE mku.medicalKind = :medicalKind AND mku.medicalAidUnit = :medicalAidUnit")
        })
public class RbTariffType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @Basic(optional = false)
    private String code;

    @Column(name = "name")
    @Basic(optional = false)
    private String name;

    public RbTariffType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbTariffType that = (RbTariffType) o;

        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }
}
