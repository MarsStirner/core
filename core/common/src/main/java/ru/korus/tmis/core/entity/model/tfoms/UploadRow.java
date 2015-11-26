package ru.korus.tmis.core.entity.model.tfoms;


import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static ru.korus.tmis.core.entity.model.tfoms.ObjectParser.*;

/**
 * Author: Upatov Egor <br>
 * Date: 06.05.2014, 20:54 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */


public class UploadRow implements Informationable {

    private static int nextIdentifier = 1;

    private enum FieldNames {
        ID("ID"),
        EVENT("EventId"),
        EVENT_TYPE("EventTypeId"),
        ACTION("ActionId"),
        RB_SERVICE_FINANCE("rbServiceFinanceId"),
        CLIENT("ClientId"),
        FAM("FAM"),
        IM("IM"),
        OT("OT"),
        W("W"),
        DR("DR"),
        MR("MR"),
        SNILS("SNILS"),
        VNOV_D("VNOV_D"),
        SPOKESMAN("spokesmanId"),
        FAM_P("FAM_P"),
        IM_P("IM_P"),
        OT_P("OT_P"),
        W_P("W_P"),
        DR_P("DR_P"),
        DOCUMENT("DocumentId"),
        DOCTYPE("DOCTYPE"),
        DOCSER("DOCSER"),
        DOCNUM("DOCNUM"),
        POLICY("PolicyId"),
        VPOLIS("VPOLIS"),
        SPOLIS("SPOLIS"),
        NPOLIS("NPOLIS"),
        SMO("SMO"),
        SMO_NAM("SMO_NAM"),
        SMO_OGRN("SMO_OGRN"),
        SMO_OK("SMO_OK"),
        INSURER_AREA("SMO_AREA"),
        NOVOR("NOVOR"),
        USL_OK("USL_OK"),
        VIDPOM("VIDPOM"),
        FOR_POM("FOR_POM"),
        EXTR("EXTR"),
        LPU_1("LPU_1"),
        PODR("PODR"),
        PROFIL("PROFIL"),
        DET("DET"),
        NHISTORY("NHISTORY"),
        DATE_1("DATE_1"),
        DATE_2("DATE_2"),
        DS0("DS0"),
        DS1("DS1"),
        DS2("DS2"),
        CODE_MES1("CODE_MES1"),
        CODE_MES2("CODE_MES2"),
        RSLT("RSLT"),
        ISHOD("ISHOD"),
        PRVS("PRVS"),
        IDDOKT("IDDOKT"),
        OS_SLUCH("OS_SLUCH"),
        IDSP("IDSP"),
        ED_COL("ED_COL"),
        CODE_USL("CODE_USL"),
        TARIF("TARIF"),
        TARIFF_ID("tariffId"),
        RB_MEDICAL_AID_UNIT("rbMedicalAidUnitId"),
        RB_MEDICAL_AID_UNIT_CODE("rbMedicalAidUnitCode"),
        RB_MEDICAL_KIND_CODE("rbMedicalKindCode"),
        AMOUNT("ActionAmount"),
        RB_SERVICE("rbServiceId"),
        RB_SERVICE_INFIS("rbServiceInfis"),
        RSLT_REGIONAL_CODE("RSLTRegionalCode"),
        VID_HMP("VID_HMP"),
        METOD_HMP("METOD_HMP");

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

    private Integer event;
    private Integer eventType;
    private Integer action;

    private Integer rbServiceFinance;

    private PatientProperties client;

    private PatientProperties spokesman;

    private PatientDocument document;

    private PatientPolicy policy;

    private String NOVOR;
    private String USL_OK;
    private String VIDPOM;
    private Integer FOR_POM;
    private Integer EXTR;
    private String LPU_1;
    private String PODR;
    private String PROFIL;
    private Integer DET;
    private String NHISTORY;
    private Date DATE_1;
    private Date DATE_2;
    private String DS0;
    private String DS1;
    private String DS2;
    private String CODE_MES1;
    private String CODE_MES2;
    private String RSLT;
    private String ISHOD;
    private String PRVS;
    private String IDDOKT;
    private String OS_SLUCH;
    private String IDSP;
    private Double ED_COL;
    private String CODE_USL;
    private Double TARIF;
    private Integer tariffId;
    private Integer rbMedicalAidUnitId;
    private String rbMedicalAidUnitCode;
    private String rbMedicalKindCode;
    private Double amount;
    private Integer rbService;
    private String rbServiceInfis;
    private String RSLTRegionalCode;
    private String VID_HMP;
    private String METOD_HMP;


    public UploadRow(final Object[] args) throws InstantiationException {
        if (args.length >= 67) {
            this.id = getIntegerValue(args[0]);
            this.event = getIntegerValue(args[1]);
            this.eventType = getIntegerValue(args[2]);
            this.action = getIntegerValue(args[3]);
            this.rbServiceFinance = getIntegerValue(args[4]);
            this.client = new PatientProperties(
                    getIntegerValue(args[5]), //ID
                    getStringValue(args[6]), //FAM
                    getStringValue(args[7]), //IM
                    getStringValue(args[8]), //OT
                    getIntegerValue(args[9]), //sex
                    getDateValue(args[10]),  //DR
                    getStringValue(args[11]), //MR
                    getStringValue(args[12]), //SNILS
                    getStringValue(args[13]) //VNOV_D
            );
            if (args[14] != null) {
                this.spokesman = new PatientProperties(
                        getIntegerValue(args[14]),//ID
                        getStringValue(args[15]), //FAM
                        getStringValue(args[16]), //IM
                        getStringValue(args[17]), //OT
                        getIntegerValue(args[18]), //SEX
                        getDateValue(args[19])   //DR
                );
            }
            if (args[20] != null) {
                this.document = new PatientDocument(
                        getIntegerValue(args[20]), //ID
                        getStringValue(args[21]), //DOCTYPE
                        getStringValue(args[22]), //DOCSER
                        getStringValue(args[23]) //DOCNUM
                );
            }
            if (args[24] != null) {
                this.policy = new PatientPolicy(
                        getIntegerValue(args[24]), //ID
                        getStringValue(args[25]), //VPOLIS
                        getStringValue(args[26]), //SPOLIS
                        getStringValue(args[27]), //NPOLIS
                        getStringValue(args[28]), //SMO
                        getStringValue(args[29]), //SMO_NAM
                        getStringValue(args[30]), //SMO_OGRN
                        getStringValue(args[31]), //SMO_OK
                        getStringValue(args[32])  //Area
                );
            }
            this.NOVOR = getStringValue(args[33]);
            this.USL_OK = getStringValue(args[34]);
            this.VIDPOM = getStringValue(args[35]);
            this.FOR_POM = getIntegerValue(args[36]);
            this.EXTR = getIntegerValue(args[37]);
            this.LPU_1 = getStringValue(args[38]);
            this.PODR = getStringValue(args[39]);
            this.PROFIL = getStringValue(args[40]);
            this.DET = getIntegerValue(args[41]);
            this.NHISTORY = getStringValue(args[42]);
            this.DATE_1 = getDateValue(args[43]);
            this.DATE_2 = getDateValue(args[44]);
            this.DS0 = getStringValue(args[45]);
            this.DS1 = getStringValue(args[46]);
            this.DS2 = getStringValue(args[47]);
            this.CODE_MES1 = getStringValue(args[48]);
            this.CODE_MES2 = getStringValue(args[49]);
            this.RSLT = getStringValue(args[50]);
            this.ISHOD = getStringValue(args[51]);
            this.PRVS = getStringValue(args[52]);
            this.IDDOKT = getStringValue(args[53]);
            this.OS_SLUCH = getStringValue(args[54]);
            this.IDSP = getStringValue(args[55]);
            this.ED_COL = getDoubleValue(args[56]);
            this.CODE_USL = getStringValue(args[57]);
            this.TARIF = getDoubleValue(args[58]);
            this.tariffId = getIntegerValue(args[59]);
            this.rbMedicalAidUnitId = getIntegerValue(args[60]);
            this.rbMedicalAidUnitCode = getStringValue(args[61]);
            this.rbMedicalKindCode = getStringValue(args[62]);
            this.amount = getDoubleValue(args[63]);
            this.rbService = getIntegerValue(args[64]);
            this.rbServiceInfis = getStringValue(args[65]);
            this.RSLTRegionalCode = getStringValue(args[66]);
            this.VID_HMP = getStringValue(args[67]);
            this.METOD_HMP = getStringValue(args[68]);
        } else {
            throw new InstantiationException("Invalid param count");
        }
    }

    public UploadRow(final Map args) {
        for (Iterator<Map.Entry> iterator = args.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = iterator.next();
            String key = entry.getKey().toString();
            if (FieldNames.ID.getValue().equalsIgnoreCase(key)) {
                this.id = getIntegerValue(entry.getValue());
            } else if (FieldNames.EVENT.getValue().equalsIgnoreCase(key)) {
                this.event = getIntegerValue(entry.getValue());
            } else if (FieldNames.EVENT_TYPE.getValue().equalsIgnoreCase(key)) {
                this.eventType = getIntegerValue(entry.getValue());
            } else if (FieldNames.ACTION.getValue().equalsIgnoreCase(key)) {
                this.action = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE_FINANCE.getValue().equalsIgnoreCase(key)) {
                this.rbServiceFinance = getIntegerValue(entry.getValue());
            } else if (FieldNames.CLIENT.getValue().equalsIgnoreCase(key)) {
                this.client = new PatientProperties(
                        getIntegerValue(entry.getValue()), //ID
                        getStringValue(args.get(FieldNames.FAM.getValue())), //FAM
                        getStringValue(args.get(FieldNames.IM.getValue())), //IM
                        getStringValue(args.get(FieldNames.OT.getValue())), //OT
                        getIntegerValue(args.get(FieldNames.W.getValue())), //sex
                        getDateValue(args.get(FieldNames.DR.getValue())),  //DR
                        getStringValue(args.get(FieldNames.MR.getValue())), //MR
                        getStringValue(args.get(FieldNames.SNILS.getValue())), //SNILS
                        getStringValue(args.get(FieldNames.VNOV_D.getValue())) //VNOV_D
                );
            } else if (FieldNames.SPOKESMAN.getValue().equalsIgnoreCase(key)) {
                if (entry.getValue() != null) {
                    this.spokesman = new PatientProperties(
                            getIntegerValue(entry.getValue()),//ID
                            getStringValue(args.get(FieldNames.FAM_P.getValue())), //FAM
                            getStringValue(args.get(FieldNames.IM_P.getValue())), //IM
                            getStringValue(args.get(FieldNames.OT_P.getValue())), //OT
                            getIntegerValue(args.get(FieldNames.W_P.getValue())), //SEX
                            getDateValue(args.get(FieldNames.DR_P.getValue()))   //DR
                    );
                }
            } else if (FieldNames.DOCUMENT.getValue().equalsIgnoreCase(key)) {
                if (entry.getValue() != null) {
                    this.document = new PatientDocument(
                            getIntegerValue(entry.getValue()),//ID
                            getStringValue(args.get(FieldNames.DOCTYPE.getValue())),
                            getStringValue(args.get(FieldNames.DOCSER.getValue())),
                            getStringValue(args.get(FieldNames.DOCNUM.getValue()))
                    );
                }
            } else if (FieldNames.POLICY.getValue().equalsIgnoreCase(key)) {
                if (entry.getValue() != null) {
                    this.policy = new PatientPolicy(
                            getIntegerValue(entry.getValue()),//ID
                            getStringValue(args.get(FieldNames.VPOLIS.getValue())),
                            getStringValue(args.get(FieldNames.SPOLIS.getValue())),
                            getStringValue(args.get(FieldNames.NPOLIS.getValue())),
                            getStringValue(args.get(FieldNames.SMO.getValue())),
                            getStringValue(args.get(FieldNames.SMO_NAM.getValue())),
                            getStringValue(args.get(FieldNames.SMO_OGRN.getValue())),
                            getStringValue(args.get(FieldNames.SMO_OK.getValue())),
                            getStringValue(args.get(FieldNames.INSURER_AREA.getValue()))
                    );
                }
            } else if (FieldNames.NOVOR.getValue().equalsIgnoreCase(key)) {
                this.NOVOR = getStringValue(entry.getValue());
            } else if (FieldNames.USL_OK.getValue().equalsIgnoreCase(key)) {
                this.USL_OK = getStringValue(entry.getValue());
            } else if (FieldNames.VIDPOM.getValue().equalsIgnoreCase(key)) {
                this.VIDPOM = getStringValue(entry.getValue());
            } else if (FieldNames.FOR_POM.getValue().equalsIgnoreCase(key)) {
                this.FOR_POM = getIntegerValue(entry.getValue());
            } else if (FieldNames.EXTR.getValue().equalsIgnoreCase(key)) {
                this.EXTR = getIntegerValue(entry.getValue());
            } else if (FieldNames.LPU_1.getValue().equalsIgnoreCase(key)) {
                this.LPU_1 = getStringValue(entry.getValue());
            } else if (FieldNames.PODR.getValue().equalsIgnoreCase(key)) {
                this.PODR = getStringValue(entry.getValue());
            } else if (FieldNames.PROFIL.getValue().equalsIgnoreCase(key)) {
                this.PROFIL = getStringValue(entry.getValue());
            } else if (FieldNames.DET.getValue().equalsIgnoreCase(key)) {
                this.DET = getIntegerValue(entry.getValue());
            } else if (FieldNames.NHISTORY.getValue().equalsIgnoreCase(key)) {
                this.NHISTORY = getStringValue(entry.getValue());
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
            } else if (FieldNames.CODE_MES1.getValue().equalsIgnoreCase(key)) {
                this.CODE_MES1 = getStringValue(entry.getValue());
            } else if (FieldNames.CODE_MES2.getValue().equalsIgnoreCase(key)) {
                this.CODE_MES2 = getStringValue(entry.getValue());
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
            } else if (FieldNames.IDSP.getValue().equalsIgnoreCase(key)) {
                this.IDSP = getStringValue(entry.getValue());
            } else if (FieldNames.ED_COL.getValue().equalsIgnoreCase(key)) {
                this.ED_COL = getDoubleValue(entry.getValue());
            } else if (FieldNames.CODE_USL.getValue().equalsIgnoreCase(key)) {
                this.CODE_USL = getStringValue(entry.getValue());
            } else if (FieldNames.TARIF.getValue().equalsIgnoreCase(key)) {
                this.TARIF = getDoubleValue(entry.getValue());
            } else if (FieldNames.TARIFF_ID.getValue().equalsIgnoreCase(key)) {
                this.tariffId = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_AID_UNIT.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalAidUnitId = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_AID_UNIT_CODE.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalAidUnitCode = getStringValue(entry.getValue());
            } else if (FieldNames.RB_MEDICAL_KIND_CODE.getValue().equalsIgnoreCase(key)) {
                this.rbMedicalKindCode = getStringValue(entry.getValue());
            } else if (FieldNames.AMOUNT.getValue().equalsIgnoreCase(key)) {
                this.amount = getDoubleValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE.getValue().equalsIgnoreCase(key)) {
                this.rbService = getIntegerValue(entry.getValue());
            } else if (FieldNames.RB_SERVICE_INFIS.getValue().equalsIgnoreCase(key)) {
                this.rbServiceInfis = getStringValue(entry.getValue());
            }  else if (FieldNames.RSLT_REGIONAL_CODE.getValue().equalsIgnoreCase(key)) {
                this.RSLTRegionalCode = getStringValue(entry.getValue());
            } else if (FieldNames.VID_HMP.getValue().equalsIgnoreCase(key)) {
                this.VID_HMP = getStringValue(entry.getValue());
            } else if (FieldNames.METOD_HMP.getValue().equalsIgnoreCase(key)) {
                this.METOD_HMP = getStringValue(entry.getValue());
            }
        }
        if(id == null){
            id = nextIdentifier++;
        }
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadRow[");
        sb.append(id).append("] {");
        sb.append("event=").append(event);
        sb.append(", eventType=").append(eventType);
        sb.append(", action=").append(action);
        sb.append(", rbServiceFinance=").append(rbServiceFinance);
        sb.append(", client=").append(client);
        sb.append(", spokesman=").append(spokesman);
        sb.append(", document=").append(document);
        sb.append(", policy=").append(policy);
        sb.append(", NOVOR='").append(NOVOR).append('\'');
        sb.append(", USL_OK='").append(USL_OK).append('\'');
        sb.append(", VIDPOM='").append(VIDPOM).append('\'');
        sb.append(", FOR_POM=").append(FOR_POM);
        sb.append(", EXTR=").append(EXTR);
        sb.append(", LPU_1='").append(LPU_1).append('\'');
        sb.append(", PODR='").append(PODR).append('\'');
        sb.append(", PROFIL='").append(PROFIL).append('\'');
        sb.append(", DET=").append(DET);
        sb.append(", NHISTORY='").append(NHISTORY).append('\'');
        sb.append(", DATE_1=").append(DATE_1);
        sb.append(", DATE_2=").append(DATE_2);
        sb.append(", DS0='").append(DS0).append('\'');
        sb.append(", DS1='").append(DS1).append('\'');
        sb.append(", DS2='").append(DS2).append('\'');
        sb.append(", CODE_MES1='").append(CODE_MES1).append('\'');
        sb.append(", CODE_MES2='").append(CODE_MES2).append('\'');
        sb.append(", RSLT='").append(RSLT).append('\'');
        sb.append(", ISHOD='").append(ISHOD).append('\'');
        sb.append(", PRVS='").append(PRVS).append('\'');
        sb.append(", IDDOKT='").append(IDDOKT).append('\'');
        sb.append(", OS_SLUCH='").append(OS_SLUCH).append('\'');
        sb.append(", IDSP='").append(IDSP).append('\'');
        sb.append(", ED_COL=").append(ED_COL);
        sb.append(", CODE_USL='").append(CODE_USL).append('\'');
        sb.append(", TARIF=").append(TARIF);
        sb.append(", tariffId=").append(tariffId);
        sb.append(", rbMedicalAidUnitId=").append(rbMedicalAidUnitId);
        sb.append(", rbMedicalAidUnitCode='").append(rbMedicalAidUnitCode).append('\'');
        sb.append(", rbMedicalKindCode='").append(rbMedicalKindCode).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", rbService=").append(rbService);
        sb.append(", rbServiceInfis='").append(rbServiceInfis).append('\'');
        sb.append(", RSLTRegionalCode='").append(RSLTRegionalCode).append('\'');
        sb.append(", VID_HMP='").append(VID_HMP).append('\'');
        sb.append(", METOD_HMP='").append(METOD_HMP).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public Integer getEvent() {
        return event;
    }

    public Integer getEventType() {
        return eventType;
    }

    public Integer getAction() {
        return action;
    }

    public Integer getRbServiceFinance() {
        return rbServiceFinance;
    }

    public PatientProperties getClient() {
        return client;
    }

    public PatientProperties getSpokesman() {
        return spokesman;
    }

    public PatientDocument getDocument() {
        return document;
    }

    public PatientPolicy getPolicy() {
        return policy;
    }

    public String getNOVOR() {
        return NOVOR;
    }

    public String getUSL_OK() {
        return USL_OK;
    }

    public String getVIDPOM() {
        return VIDPOM;
    }

    public Integer getFOR_POM() {
        return FOR_POM;
    }

    public Integer getEXTR() {
        return EXTR;
    }

    public String getLPU_1() {
        return LPU_1;
    }

    public String getPODR() {
        return PODR;
    }

    public String getPROFIL() {
        return PROFIL;
    }

    public Integer getDET() {
        return DET;
    }

    public String getNHISTORY() {
        return NHISTORY;
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

    public String getCODE_MES1() {
        return CODE_MES1;
    }

    public String getCODE_MES2() {
        return CODE_MES2;
    }

    public String getRSLT() {
        return RSLT;
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

    public String getIDSP() {
        return IDSP;
    }

    public Double getED_COL() {
        return ED_COL;
    }

    public String getCODE_USL() {
        return CODE_USL;
    }

    public Double getTARIF() {
        return TARIF;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public Integer getRbMedicalAidUnitId() {
        return rbMedicalAidUnitId;
    }

    public String getRbMedicalAidUnitCode() {
        return rbMedicalAidUnitCode;
    }

    public String getRbMedicalKindCode() {
        return rbMedicalKindCode;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getRbService() {
        return rbService;
    }

    public String getRbServiceInfis() {
        return rbServiceInfis;
    }

    public String getRSLTRegionalCode() {
        return RSLTRegionalCode;
    }

    public String getVID_HMP() {
        return VID_HMP;
    }

    public String getMETOD_HMP() {
        return METOD_HMP;
    }
}
