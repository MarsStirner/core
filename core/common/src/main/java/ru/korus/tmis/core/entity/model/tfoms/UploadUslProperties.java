package ru.korus.tmis.core.entity.model.tfoms;

import ru.korus.tmis.core.entity.model.MedicalKindUnit;
import ru.korus.tmis.core.entity.model.RbMedicalAidUnit;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.14, 4:22 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
//@Entity
public class UploadUslProperties implements Informationable {

    //@Id
    //@Column(name = "id")
    //private Integer id;

    @Column(name = "IDSP")
    private String IDSP;

    @Column(name = "MKU")
    private Integer mku;

    @Column(name = "UNIT")
    private Integer unit;

    @Column(name = "unitCode")
    private String unitCode;

    @Column(name = "ED_COL")
    private Double ED_COL;

    /**
     * Группа rbCSG (для стационара)
     */
    @Column(name ="CSG")
    private Integer csg;

    @Column(name = "CSGCode")
    private String csgCode;

    public UploadUslProperties(Object[] args) {
        if(args.length >= 7){
            this.IDSP = (String) args[0];
            this.mku = (Integer) args[1];
            this.unit = (Integer) args[2];
            this.unitCode = (String) args[3];
            this.ED_COL = (Double) args[4];
            this.csg = (Integer) args[5];
            this.csgCode = (String) args[6];
        }
    }


    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("USLProperties[");//.append(id).append("]");
        result.append(" IDSP=").append(IDSP);
        result.append(" MKU=").append(mku);
        result.append(" UNIT=").append(unit).append(" ").append(unitCode);
        result.append(" ED_COL=").append(ED_COL);
        result.append(" CSG=").append(csg).append(" ").append(csgCode);
        result.append(" ]");
        return result.toString();
    }

    public UploadUslProperties() {
    }

    public String getIDSP() {
        return IDSP;
    }

    public void setIDSP(String IDSP) {
        this.IDSP = IDSP;
    }

    public Integer getMku() {
        return mku;
    }

    public void setMku(Integer mku) {
        this.mku = mku;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Double getED_COL() {
        return ED_COL;
    }

    public void setED_COL(Double ED_COL) {
        this.ED_COL = ED_COL;
    }

    public Integer getCsg() {
        return csg;
    }

    public void setCsg(Integer csg) {
        this.csg = csg;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getCsgCode() {
        return csgCode;
    }

    public void setCsgCode(String csgCode) {
        this.csgCode = csgCode;
    }
}
