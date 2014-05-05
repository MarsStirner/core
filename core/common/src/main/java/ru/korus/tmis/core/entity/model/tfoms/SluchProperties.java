package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 02.03.14, 3:51 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
//@Entity
public class SluchProperties implements Informationable {

    //@Id
    @Column(name = "NOVOR")
    private String NOVOR;

    @Column(name = "NPR_MO")
    private String NPR_MO;

    @Column(name = "EXTR")
    private String EXTR;

    @Column(name = "DET")
    private String DET;

    @Column(name = "IDDOKT")
    private String IDDOKT;

    @Column(name = "PRVS")
    private String PRVS;

    @Column(name = "LPU_1")
    private String LPU_1;

    @Column(name = "PODR")
    private String PODR;

    public SluchProperties() {
    }

    public SluchProperties(Object[] args) {
        if (args.length >= 8) {
            this.NOVOR = (String) args[0];
            this.NPR_MO = (String) args[1];
            this.EXTR = (String) args[2];
            this.DET = (String) args[3];
            this.IDDOKT = (String) args[4];
            this.PRVS = (String) args[5];
            this.LPU_1 = (String) args[6];
            this.PODR = (String) args[7];
        }
    }

    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("SluchProperties[");
        result.append(" NOVOR=").append(NOVOR);
        result.append(" NPR_MO=").append(NPR_MO);
        result.append(" EXTR=").append(EXTR);
        result.append(" DET=").append(DET);
        result.append(" IDDOKT=").append(IDDOKT);
        result.append(" PRVS=").append(PRVS);
        result.append(" LPU_1=").append(LPU_1);
        result.append(" PODR=").append(PODR);
        result.append(" ]");
        return result.toString();
    }


    public String getNOVOR() {
        return NOVOR;
    }

    public void setNOVOR(String NOVOR) {
        this.NOVOR = NOVOR;
    }

    public String getNPR_MO() {
        return NPR_MO;
    }

    public void setNPR_MO(String NPR_MO) {
        this.NPR_MO = NPR_MO;
    }

    public String getEXTR() {
        return EXTR;
    }

    public void setEXTR(String EXTR) {
        this.EXTR = EXTR;
    }

    public String getDET() {
        return DET;
    }

    public void setDET(String DET) {
        this.DET = DET;
    }

    public String getIDDOKT() {
        return IDDOKT;
    }

    public void setIDDOKT(String IDDOKT) {
        this.IDDOKT = IDDOKT;
    }

    public String getPRVS() {
        return PRVS;
    }

    public void setPRVS(String PRVS) {
        this.PRVS = PRVS;
    }

    public String getLPU_1() {
        return LPU_1;
    }

    public void setLPU_1(String LPU_1) {
        this.LPU_1 = LPU_1;
    }

    public String getPODR() {
        return PODR;
    }

    public void setPODR(String PODR) {
        this.PODR = PODR;
    }


}
