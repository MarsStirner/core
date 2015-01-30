package ru.korus.tmis.core.entity.model.tfoms;

import ru.korus.tmis.util.TextFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 05.02.14, 18:03 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
// @Entity
public class PatientProperties implements  Informationable{
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd");
    //@Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "FAM")
    private String FAM;

    @Column(name = "IM")
    private String IM;

    @Column(name = "OT")
    private String OT;

    @Column(name = "W")
    private Integer sex;

    @Column(name = "DR")
    @Temporal(TemporalType.DATE)
    private Date DR;

    @Column(name = "MR")
    private String MR;

    @Column(name = "SNILS")
    private String SNILS;

    @Column(name = "VNOV_D")
    private String VNOV_D;

    public PatientProperties(final Object[] args) {
        if(args.length >= 9){
            this.id = (Integer) args[0];
            this.FAM = (String) args[1];
            this.IM = (String) args[2];
            this.OT = (String) args[3];
            this.sex = (Integer) args[4];
            this.DR = (Date) args[5];
            this.MR = (String) args[6];
            this.SNILS = (String) args[7];
            this.VNOV_D = (String) args[8];
        }
    }

    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("PatientProperties");
        result.append("[ ID=").append(id);
        result.append(" FAM=").append(FAM);
        result.append(" IM=").append(IM);
        result.append(" OT=").append(OT);
        result.append(" W=").append(sex);
        result.append(" DR=").append(datetimeFormat.format(DR));
        result.append(" MR=").append(MR);
        result.append(" SNILS=").append(SNILS);
        result.append(" VNOV_D=").append(VNOV_D);
        result.append(" ]");
        return result.toString();
    }

    public PatientProperties() {
    }

    public String getFAM() {
        return FAM;
    }

    public void setFAM(String FAM) {
        this.FAM = FAM;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String IM) {
        this.IM = IM;
    }

    public String getOT() {
        return OT;
    }

    public void setOT(String OT) {
        this.OT = OT;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getDR() {
        return DR;
    }

    public void setDR(Date DR) {
        this.DR = DR;
    }

    public String getMR() {
        return MR;
    }

    public void setMR(String MR) {
        this.MR = MR;
    }

    public String getSNILS() {
        return SNILS;
    }

    public void setSNILS(String SNILS) {
        this.SNILS = SNILS;
    }

    public String getVNOV_D() {
        return VNOV_D;
    }

    public void setVNOV_D(String VNOV_D) {
        this.VNOV_D = VNOV_D;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
