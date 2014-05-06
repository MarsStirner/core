package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 05.02.14, 14:22 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
// @Entity
public class UploadItemProperties implements Informationable {

    //  @Id
    //@Column(name = "id")
    //private Integer id;

    @Column(name = "NHISTORY")
    private String NHISTORY;

    @Column(name = "RSLT")
    private String RSLT;

    @Column(name = "RSLTRegionalCode")
    private String RegionalCode;

    @Column(name = "ISHOD")
    private String ISHOD;

    @Column(name = "DS1")
    private String DS1;

    @Column(name = "DS0")
    private String DS0;

    @Column(name = "DS2")
    private String DS2;

    public UploadItemProperties(Object[] args) {
      if(args.length >= 7){
          this.NHISTORY = (String) args[0];
          this.RSLT = (String) args[1];
          this.RegionalCode = (String) args[2];
          this.ISHOD = (String) args[3];
          this.DS1 = (String) args[4];
          this.DS0 = (String) args[5];
          this.DS2 = (String) args[6];
      }
    }

    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("UploadItemProperties[");//.append(id).append("]");
        result.append(" RSLT=").append(RSLT);
        result.append(" NHISTORY=").append(NHISTORY);
        result.append(" ISHOD=").append(ISHOD);
        result.append(" DS1=").append(DS1);
        result.append(" DS0=").append(DS0);
        result.append(" DS2=").append(DS2);
        result.append(" RSLT.regionalCode=").append(RegionalCode);
        result.append(" ]");
        return result.toString();
    }


    public UploadItemProperties() {
    }

    public String getRSLT() {
        return RSLT;
    }

    public void setRSLT(String RSLT) {
        this.RSLT = RSLT;
    }

    public String getISHOD() {
        return ISHOD;
    }

    public void setISHOD(String ISHOD) {
        this.ISHOD = ISHOD;
    }

    public String getDS1() {
        return DS1;
    }

    public void setDS1(String DS1) {
        this.DS1 = DS1;
    }

    public String getDS0() {
        return DS0;
    }

    public void setDS0(String DS0) {
        this.DS0 = DS0;
    }

    public String getDS2() {
        return DS2;
    }

    public void setDS2(String DS2) {
        this.DS2 = DS2;
    }

    public String getRegionalCode() {
        return RegionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        RegionalCode = regionalCode;
    }

    public String getNHISTORY() {
        return NHISTORY;
    }

    public void setNHISTORY(String NHISTORY) {
        this.NHISTORY = NHISTORY;
    }
}
