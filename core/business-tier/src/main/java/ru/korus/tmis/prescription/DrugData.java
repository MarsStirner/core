package ru.korus.tmis.prescription;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.database.DbRbUnitBeanLocal;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.DrugIntervalCompParam;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 11:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@JsonIgnoreProperties(ignoreUnknown=true)
public class DrugData {

    @XmlTransient
    private static final String COMMON_UNIT_CODE = "мг";

    private Integer nomen;
    private Integer id;   //componentId
    private String name;
    private Double dose;
    private Double voa;
    private Integer unit;
    private String unitName;
    private String dosageValue;
    private RbUnit dosageValueUnit;
    private List<UnitsData> units = new LinkedList<UnitsData>();

    public DrugData(DrugComponent drugComponent, DbRbUnitBeanLocal dbRbUnitBeanLocal) {
        id = drugComponent.getId();
        final RlsNomen nomen = drugComponent.getNomen();
        this.nomen = nomen == null ? 0 : nomen.getId();
        name = nomen == null || nomen.getRlsTradeName() == null ? null : nomen.getRlsTradeName().getLocalName();
        dose = drugComponent.getDose();
        dosageValue = nomen == null ? null : nomen.getDosageValue();
        dosageValueUnit = nomen == null || nomen.getDosageUnit() == null ? null : nomen.getDosageUnit();
        final RbUnit dosageUnit = drugComponent.getUnit();
        unit = dosageUnit == null ? null : dosageUnit.getId();
        unitName = dosageUnit == null ? null : dosageUnit.getName();
        if(unit != null) {
            units.add(new UnitsData(dosageUnit));
            if( !COMMON_UNIT_CODE.equals(dosageUnit.getCode())) {
                units.add(new UnitsData(dbRbUnitBeanLocal.getByCode(COMMON_UNIT_CODE)));
            }
        }
    }

    public DrugData() {
        nomen = null;
        id = null;
        name = null;
        dose = null;
        unit = null;
        unitName = null;
    }

    public DrugData(DrugIntervalCompParam drugIntervalParam) {

        nomen = drugIntervalParam.getDrugComponent().getNomen().getId();
        dose = drugIntervalParam.getDose();
        voa = drugIntervalParam.getVoa();
        unit = drugIntervalParam.getDrugComponent().getUnit().getId();
    }

    public static String getCommonUnitCode() {
        return COMMON_UNIT_CODE;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getDose() {
        return dose;
    }

    public Integer getUnit() {
        return unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public List<ru.korus.tmis.prescription.UnitsData> getUnits() {
        return units;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnits(List<UnitsData> units) {
        this.units = units;
    }

    public Integer getNomen() {
        return nomen;
    }

    public void setNomen(Integer nomen) {
        this.nomen = nomen;
    }

    public String getDosageValue() {
        return dosageValue;
    }

    public void setDosageValue(String dosageValue) {
        this.dosageValue = dosageValue;
    }

    public RbUnit getDosageValueUnit() {
        return dosageValueUnit;
    }

    public void setDosageValueUnit(RbUnit dosageValueUnit) {
        this.dosageValueUnit = dosageValueUnit;
    }

    public Double getVoa() {
        return voa;
    }

    public void setVoa(Double voa) {
        this.voa = voa;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof DrugData)) {
            return false;
        }

        DrugData drugData = (DrugData) o;

        if (id != null ? !id.equals(drugData.id) : drugData.id != null) {
            return false;
        }

        if (nomen != null ? !nomen.equals(drugData.nomen) : drugData.nomen != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = nomen != null ? nomen.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
