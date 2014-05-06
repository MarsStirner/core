package ru.korus.tmis.core.entity.model.tfoms;

import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.Organisation;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 05.02.14, 18:40 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
//@Entity
public class PatientPolicy implements Informationable {

    //@Id
    @Column(name = "id")
    private Integer policy;

    @Column(name = "VPOLIS")
    private String VPOLIS;

    @Column(name = "SPOLIS")
    private String SPOLIS;

    @Column(name = "NPOLIS")
    private String NPOLIS;

    @Column(name = "INSURER")
    private Integer insurer;

    @Column(name = "SMO")
    private String SMO;

    @Column(name = "SMO_OGRN")
    private String SMO_OGRN;

    @Column(name = "SMO_OK")
    private String SMO_OK;

    @Column(name = "SMO_NAM")
    private String SMO_NAM;

    public PatientPolicy(Object[] args) {
        if(args.length >= 9){
            this.policy = (Integer) args[0];
            this.VPOLIS = (String) args[1];
            this.SPOLIS = (String) args[2];
            this.NPOLIS = (String) args[3];
            this.insurer = (Integer) args[4];
            this.SMO = (String) args[5];
            this.SMO_OGRN = (String) args[6];
            this.SMO_OK = (String) args[7];
            this.SMO_NAM = (String) args[8];
        }
    }

    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("PatientPolicy");
        result.append("[ ID=").append(policy);
        result.append(" VPOLIS=").append(VPOLIS);
        result.append(" SPOLIS=").append(SPOLIS);
        result.append(" NPOLIS=").append(NPOLIS);
        if (insurer != null) {
            result.append(" INSURER=").append(insurer);
            result.append(" SMO=").append(SMO);
            result.append(" SMO_OGRN=").append(SMO_OGRN);
            result.append(" SMO_OK=").append(SMO_OK);
            result.append(" SMO_NAM=").append(SMO_NAM);
        }
        result.append(" ]");
        return result.toString();
    }

    public PatientPolicy() {
    }

    public Integer getPolicy() {
        return policy;
    }

    public void setPolicy(Integer policy) {
        this.policy = policy;
    }

    public String getVPOLIS() {
        return VPOLIS;
    }

    public void setVPOLIS(String VPOLIS) {
        this.VPOLIS = VPOLIS;
    }

    public String getSPOLIS() {
        return SPOLIS;
    }

    public void setSPOLIS(String SPOLIS) {
        this.SPOLIS = SPOLIS;
    }

    public String getNPOLIS() {
        return NPOLIS;
    }

    public void setNPOLIS(String NPOLIS) {
        this.NPOLIS = NPOLIS;
    }

    public Integer getInsurer() {
        return insurer;
    }

    public void setInsurer(Integer insurer) {
        this.insurer = insurer;
    }

    public String getSMO() {
        return SMO;
    }

    public void setSMO(String SMO) {
        this.SMO = SMO;
    }

    public String getSMO_OGRN() {
        return SMO_OGRN;
    }

    public void setSMO_OGRN(String SMO_OGRN) {
        this.SMO_OGRN = SMO_OGRN;
    }

    public String getSMO_OK() {
        return SMO_OK;
    }

    public void setSMO_OK(String SMO_OK) {
        this.SMO_OK = SMO_OK;
    }

    public String getSMO_NAM() {
        return SMO_NAM;
    }

    public void setSMO_NAM(String SMO_NAM) {
        this.SMO_NAM = SMO_NAM;
    }


}
