package ru.korus.tmis.tfoms.xml;

import ru.korus.tmis.core.entity.model.tfoms.Informationable;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static ru.korus.tmis.core.entity.model.tfoms.ObjectParser.*;

/**
 * Author: Upatov Egor <br>
 * Date: 15.05.2014, 19:38 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class AdditionalUploadRow implements Informationable {
    private static int nextIdentifier = 1;

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

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
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
        for (Map.Entry entry : (Iterable<Map.Entry>) args.entrySet()) {
            String key = entry.getKey().toString();
            if (FieldNames.ACTION.getValue().equalsIgnoreCase(key)) {
                this.action = getIntegerValue(entry.getValue());
            } else if (FieldNames.EVENT.getValue().equalsIgnoreCase(key)) {
                this.event = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE.getValue().equalsIgnoreCase(key)) {
                this.rbService = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE_INFIS.getValue().equalsIgnoreCase(key)) {
                this.rbServiceInfis = getStringValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE_FINANCE.getValue().equalsIgnoreCase(key)) {
                this.rbServiceFinance = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_KIND_CODE.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalKindCode = getStringValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_AID_UNIT.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalAidUnitId = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_AID_UNIT_CODE.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalAidUnitCode = getStringValue(entry.getValue());
            } else if (FieldNames.EXTR.getValue().equalsIgnoreCase(key)) {
                this.EXTR = getIntegerValue(entry.getValue());
            } else if (FieldNames.DET.getValue().equalsIgnoreCase(key)) {
                this.DET = getIntegerValue(entry.getValue());
            } else if (FieldNames.DATE_1.getValue().equalsIgnoreCase(key)) {
                this.DATE_1 = getDateValue(entry.getValue());
            } else if (FieldNames.DATE_2.getValue().equalsIgnoreCase(key)) {
                this.DATE_2 = getDateValue(entry.getValue());
            } else if (FieldNames.DS0.getValue().equalsIgnoreCase(key)) {
                this.DS0 = getStringValue(entry.getValue());
            } else if (FieldNames.DS1.getValue().equalsIgnoreCase(key)) {
                this.DS1 = getStringValue(entry.getValue());
            } else if (FieldNames.DS2.getValue().equalsIgnoreCase(key)) {
                this.DS2 = getStringValue(entry.getValue());
            } else if (FieldNames.RSLT.getValue().equalsIgnoreCase(key)) {
                this.RSLT = getStringValue(entry.getValue());
            } else if (FieldNames.ISHOD.getValue().equalsIgnoreCase(key)) {
                this.ISHOD = getStringValue(entry.getValue());
            } else if (FieldNames.PRVS.getValue().equalsIgnoreCase(key)) {
                this.PRVS = getStringValue(entry.getValue());
            } else if (FieldNames.IDDOKT.getValue().equalsIgnoreCase(key)) {
                this.IDDOKT = getStringValue(entry.getValue());
            } else if (FieldNames.OS_SLUCH.getValue().equalsIgnoreCase(key)) {
                this.OS_SLUCH = getStringValue(entry.getValue());
            } else if (FieldNames.PODR.getValue().equalsIgnoreCase(key)) {
                this.PODR = getStringValue(entry.getValue());
            } else if (FieldNames.PROFIL.getValue().equalsIgnoreCase(key)) {
                this.PROFIL = getStringValue(entry.getValue());
            } else if (FieldNames.CODE_USL.getValue().equalsIgnoreCase(key)) {
                this.CODE_USL = getStringValue(entry.getValue());
            } else if (FieldNames.TARIF.getValue().equalsIgnoreCase(key)) {
                this.TARIF = getDoubleValue(entry.getValue());
            } else if (FieldNames.TARIFF_ID.getValue().equalsIgnoreCase(key)) {
                this.tariffId = getIntegerValue(entry.getValue());
            } else if (FieldNames.ED_COL.getValue().equalsIgnoreCase(key)) {
                this.ED_COL = getDoubleValue(entry.getValue());
            }   else if (FieldNames.RSLT_REGIONAL_CODE.getValue().equalsIgnoreCase(key)) {
                this.RSLTRegionalCode = getStringValue(entry.getValue());
            }
        }
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
