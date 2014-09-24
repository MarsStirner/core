package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.DbRbUnitBeanLocal;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;

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
public class DrugData {

    @XmlTransient
    private static final String COMMON_UNIT_CODE = "мг";

    private Integer nomen;
    private Integer id;   //TODO remove!
    private String name;
    private Double dose;
    private Integer unit;
    private String unitName;
    private List<UnitsData> units = new LinkedList<UnitsData>();

    public DrugData(DrugComponent drugComponent, DbRbUnitBeanLocal dbRbUnitBeanLocal) {
        nomen = id = drugComponent.getId();
        final RlsNomen nomen = drugComponent.getNomen();
        name = nomen == null || nomen.getRlsTradeName() == null ? null : nomen.getRlsTradeName().getLocalName();
        dose = drugComponent.getDose();
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
        nomen = id = null;
        name = null;
        dose = null;
        unit = null;
        unitName = null;
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
}
