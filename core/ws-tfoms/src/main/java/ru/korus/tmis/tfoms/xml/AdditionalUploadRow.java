package ru.korus.tmis.tfoms.xml;

import ru.korus.tmis.core.entity.model.tfoms.Informationable;

import java.util.Date;
import java.util.Map;

import static ru.korus.tmis.core.entity.model.tfoms.ObjectParser.*;

/**
 * Author: Upatov Egor <br>
 * Date: 15.05.2014, 19:38 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class AdditionalUploadRow implements Informationable {
    private static int nextIdentifier=0;

    private enum FieldNames {
        ID("ID"),
        ACTION("ActionId"),
        EVENT("EventId"),
        RB_SERVICE("rbServiceId"),
        RB_SERVICE_INFIS("rbServiceInfis"),
        RB_SERVICE_FINANCE("rbServiceFinanceId"),
        RB_MEDICAL_KIND_CODE("rbMedicalKindCode"),
        RB_MEDICAL_AID_UNIT("rbMedicalAidUnitId"),
        RB_MEDICAL_AID_UNIT_CODE("rbMedicalAidUnitCode"),
        EXTR("EXTR"),
        DET("DET"),
        DATE_1("DATE_1"),
        DATE_2("DATE_2"),
        DS0("DS0"),
        DS1("DS1"),
        DS2("DS2"),
        RSLT("RSLT"),
        ISHOD("ISHOD"),
        PRVS("PRVS"),
        IDDOKT("IDDOKT"),
        OS_SLUCH("OS_SLUCH"),
        PODR("PODR"),
        PROFIL("PROFIL"),
        CODE_USL("CODE_USL"),
        TARIF("TARIF"),
        TARIFF_ID("tariffId"),
        ED_COL("ED_COL"),
        RSLT_REGIONAL_CODE("RSLTRegionalCode");

        private final String value;

        private FieldNames(final String value) {
            this.value = value;
        }

        public String getValue(){return value;}

        @Override
        public String toString(){
            return getValue();
        }
    }

    private Integer id;
    private Integer action;
    private Integer event;
    private Integer rbService;
    private String rbServiceInfis;
    private Integer rbServiceFinance;
    private String rbMedicalKindCode;
    private Integer rbMedicalAidUnitId;
    private String rbMedicalAidUnitCode;
    private Integer EXTR;
    private Integer DET;
    private Date DATE_1;
    private Date DATE_2;
    private String DS0;
    private String DS1;
    private String DS2;
    private String RSLT;
    private String ISHOD;
    private String PRVS;
    private String IDDOKT;
    private String OS_SLUCH;
    private String PODR;
    private String PROFIL;
    private String CODE_USL;
    private Double TARIF;
    private Integer tariffId;
    private Double ED_COL;
    private String RSLTRegionalCode;

    @Override
    public String getInfo() {
        return toString();
    }


    public AdditionalUploadRow(final Map args) {
        this.id = nextIdentifier++;
        this.action = getIntegerValue(args.get(FieldNames.ACTION.getValue()));
        this.event = getIntegerValue(args.get(FieldNames.EVENT.getValue()));
        this.rbService = getIntegerValue(args.get(FieldNames.RB_SERVICE.getValue()));
        this.rbServiceInfis = getStringValue(args.get(FieldNames.RB_SERVICE_INFIS.getValue()));
        this.rbServiceFinance = getIntegerValue(args.get(FieldNames.RB_SERVICE_FINANCE.getValue()));
        this.rbMedicalKindCode = getStringValue(args.get(FieldNames.RB_MEDICAL_KIND_CODE.getValue()));
        this.rbMedicalAidUnitId  = getIntegerValue(args.get(FieldNames.RB_MEDICAL_AID_UNIT.getValue()));
        this.rbMedicalAidUnitCode = getStringValue(args.get(FieldNames.RB_MEDICAL_AID_UNIT_CODE.getValue()));
        this.EXTR = getIntegerValue(args.get(FieldNames.EXTR.getValue()));
        this.DET = getIntegerValue(args.get(FieldNames.DET.getValue()));
        this.DATE_1 = getDateValue(args.get(FieldNames.DATE_1.getValue()));
        this.DATE_2 = getDateValue(args.get(FieldNames.DATE_2.getValue()));
        this.DS0 = getStringValue(args.get(FieldNames.DS0.getValue()));
        this.DS1 = getStringValue(args.get(FieldNames.DS1.getValue()));
        this.DS2 = getStringValue(args.get(FieldNames.DS2.getValue()));
        this.RSLT = getStringValue(args.get(FieldNames.RSLT.getValue()));
        this.ISHOD = getStringValue(args.get(FieldNames.ISHOD.getValue()));
        this.PRVS = getStringValue(args.get(FieldNames.PRVS.getValue()));
        this.IDDOKT = getStringValue(args.get(FieldNames.IDDOKT.getValue()));
        this.OS_SLUCH = getStringValue(args.get(FieldNames.OS_SLUCH.getValue()));
        this.PODR = getStringValue(args.get(FieldNames.PODR.getValue()));
        this.PROFIL = getStringValue(args.get(FieldNames.PROFIL.getValue()));
        this.CODE_USL = getStringValue(args.get(FieldNames.CODE_USL.getValue()));
        this.TARIF = getDoubleValue(args.get(FieldNames.TARIF.getValue()));
        this.tariffId = getIntegerValue(args.get(FieldNames.TARIFF_ID.getValue()));
        this.ED_COL = getDoubleValue(args.get(FieldNames.ED_COL.getValue()));
        this.RSLTRegionalCode = getStringValue(args.get(FieldNames.RSLT_REGIONAL_CODE.getValue()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdditionalUploadRow[");
        sb.append(id).append("]");
        sb.append("{ action=").append(action);
        sb.append(", event=").append(event);
        sb.append(", rbService=").append(rbService);
        sb.append(", rbServiceInfis='").append(rbServiceInfis).append('\'');
        sb.append(", rbServiceFinance=").append(rbServiceFinance);
        sb.append(", rbMedicalKindCode='").append(rbMedicalKindCode).append('\'');
        sb.append(", rbMedicalAidUnitId=").append(rbMedicalAidUnitId);
        sb.append(", rbMedicalAidUnitCode='").append(rbMedicalAidUnitCode).append('\'');
        sb.append(", EXTR=").append(EXTR);
        sb.append(", DET=").append(DET);
        sb.append(", DATE_1=").append(DATE_1);
        sb.append(", DATE_2=").append(DATE_2);
        sb.append(", DS0='").append(DS0).append('\'');
        sb.append(", DS1='").append(DS1).append('\'');
        sb.append(", DS2='").append(DS2).append('\'');
        sb.append(", RSLT='").append(RSLT).append('\'');
        sb.append(", ISHOD='").append(ISHOD).append('\'');
        sb.append(", PRVS='").append(PRVS).append('\'');
        sb.append(", IDDOKT='").append(IDDOKT).append('\'');
        sb.append(", OS_SLUCH='").append(OS_SLUCH).append('\'');
        sb.append(", PODR='").append(PODR).append('\'');
        sb.append(", PROFIL='").append(PROFIL).append('\'');
        sb.append(", CODE_USL='").append(CODE_USL).append('\'');
        sb.append(", TARIF=").append(TARIF);
        sb.append(", tariffId=").append(tariffId);
        sb.append(", ED_COL=").append(ED_COL);
        sb.append(", RSLTRegionalCode='").append(RSLTRegionalCode).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getAction() {
        return action;
    }

    public Integer getId() {
        return id;
    }

    public String getRSLT() {
        return RSLT;
    }

    public Double getTARIF() {
        return TARIF;
    }

    public Double getED_COL() {
        return ED_COL;
    }

    public Integer getEvent() {
        return event;
    }

    public Integer getRbService() {
        return rbService;
    }

    public String getRbServiceInfis() {
        return rbServiceInfis;
    }

    public Integer getRbServiceFinance() {
        return rbServiceFinance;
    }

    public String getRbMedicalKindCode() {
        return rbMedicalKindCode;
    }

    public Integer getRbMedicalAidUnitId() {
        return rbMedicalAidUnitId;
    }

    public String getRbMedicalAidUnitCode() {
        return rbMedicalAidUnitCode;
    }

    public Integer getEXTR() {
        return EXTR;
    }

    public Integer getDET() {
        return DET;
    }

    public Date getDATE_1() {
        return DATE_1;
    }

    public Date getDATE_2() {
        return DATE_2;
    }

    public String getDS0() {
        return DS0;
    }

    public String getDS1() {
        return DS1;
    }

    public String getDS2() {
        return DS2;
    }

    public String getISHOD() {
        return ISHOD;
    }

    public String getPRVS() {
        return PRVS;
    }

    public String getIDDOKT() {
        return IDDOKT;
    }

    public String getOS_SLUCH() {
        return OS_SLUCH;
    }

    public String getPODR() {
        return PODR;
    }

    public String getPROFIL() {
        return PROFIL;
    }

    public String getCODE_USL() {
        return CODE_USL;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public String getRSLTRegionalCode() {
        return RSLTRegionalCode;
    }
}
