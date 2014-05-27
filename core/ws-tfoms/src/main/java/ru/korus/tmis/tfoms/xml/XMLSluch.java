package ru.korus.tmis.tfoms.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Account;
import ru.korus.tmis.core.entity.model.tfoms.*;
import ru.korus.tmis.tfoms.Constants;
import ru.korus.tmis.tfoms.DateConvertions;
import ru.korus.tmis.tfoms.TFOMSServer;
import ru.korus.tmis.tfoms.thriftgen.*;
import ru.korus.tmis.tfoms.thriftgen.Patient;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 07.05.2014, 16:21 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class XMLSluch implements Informationable {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(XMLSluch.class);
    //Формат даты, которая будет подставляться в качестве параметра
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
    private static int identifier = 0;


    private final int id;
    private final int action;
    private final int patient;
    private double SUMV = 0.0;
    private final PatientProperties patientProperties;
    private final PatientProperties spokesman;

    private Sluch result;

    private final List<UploadRow> itemList;
    private final Map<String, String> paramMap;

    public XMLSluch(final UploadRow item) {
        this.itemList = new ArrayList<UploadRow>();
        itemList.add(item);
        this.id = ++identifier;
        this.action = item.getAction();
        this.patient = item.getClient().getId();
        this.patientProperties = item.getClient();
        this.spokesman = item.getSpokesman();
        SUMV += item.getTARIF() * item.getED_COL();
        this.paramMap = new HashMap<String, String>();
    }

    public void addItem(final UploadRow item) {
        itemList.add(item);
        SUMV += item.getTARIF() * item.getED_COL();
    }

    public boolean needAdditionalPoliclinicSluch() {
        final UploadRow item = itemList.iterator().next();
        if ("2".equals(item.getRbMedicalAidUnitCode()) && "P".equals(item.getRbMedicalKindCode())) {
            paramMap.put(Constants.PARAM_NAME_CLIENT_ID, String.valueOf(item.getClient().getId()));
            paramMap.put(Constants.PARAM_NAME_RB_SERVICE_INFIS, '\'' + item.getRbServiceInfis() + '\'');
            paramMap.put(Constants.PARAM_NAME_EVENT_TYPE_ID, String.valueOf(item.getEventType()));
            paramMap.put(Constants.PARAM_NAME_CHECK_DATE, dateTimeFormat.format(item.getDATE_2()));
            paramMap.put(Constants.PARAM_NAME_INSURER_AREA, item.getPolicy() != null ? '\'' + item.getPolicy().getInsurerArea() + '\'' : "");
            return true;
        } else {
            return false;
        }
    }

    public boolean needAdditionalDispanserizationSluch() {
        final UploadRow item = itemList.iterator().next();
        if (Constants.DISPANSERIZATION_IDSP_LIST.contains(item.getIDSP())
                && (
                Constants.DISPANSERIZATION_SERVICE_INFIS_PART.contains(item.getRbServiceInfis().substring(2, 5))
                        && !"P".equals(item.getRbMedicalKindCode())
        ) || (
                "2".equals(item.getRSLTRegionalCode())
                        && "P".equals(item.getRbMedicalKindCode())
        )
                ) {
            paramMap.put(Constants.PARAM_NAME_CLIENT_ID, String.valueOf(item.getClient().getId()));
            paramMap.put(Constants.PARAM_NAME_RB_SERVICE_INFIS, '\'' + item.getRbServiceInfis() + '\'');
            paramMap.put(Constants.PARAM_NAME_EVENT_TYPE_ID, String.valueOf(item.getEventType()));
            paramMap.put(Constants.PARAM_NAME_CHECK_DATE, dateTimeFormat.format(item.getDATE_2()));
            paramMap.put(Constants.PARAM_NAME_INSURER_AREA, item.getPolicy() != null ? '\'' + item.getPolicy().getInsurerArea() + '\'' : "");
            paramMap.put(Constants.PARAM_NAME_ACTION_ID, String.valueOf(action));
            return true;
        } else {
            return true;
        }
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public Person formPersonStructure(final Set<PersonOptionalFields> personOptionalFields, boolean needForSpokesman) {
        final Person result = new Person();
        result.setPatientId(patient);
        result.setFAM(patientProperties.getFAM());
        result.setIM(patientProperties.getIM());
        result.setOT(patientProperties.getOT());
        result.setDR(DateConvertions.convertDateToUTCMilliseconds(patientProperties.getDR()));
        result.setW(patientProperties.getSex().shortValue());
        // Если представитель нужен, и он найден - то формируем структуру
        if (needForSpokesman && spokesman != null) {
            result.setSpokesman(new Spokesman());
        } else if (needForSpokesman) {
            logger.debug("SPOKESMAN: not founded spokesman");
        }
        for (PersonOptionalFields optionalField : personOptionalFields) {
            switch (optionalField) {
                case SNILS: {
                    result.setSNILS(patientProperties.getSNILS() != null ? patientProperties.getSNILS() : Constants.NULL);
                    break;
                }
                case MR: {
                    result.setMR(patientProperties.getMR() != null ? patientProperties.getMR() : Constants.NULL);
                    break;
                }
                case OKATOG: {
                    String okatog = fillOKATO(Constants.QUERY_NAME_PATIENT_OKATOG);
                    if (okatog != null && !okatog.isEmpty()) {
                        result.setOKATOG(okatog);
                        logger.debug("OKATOG={}", okatog);
                    } else {
                        logger.warn("OKATOG: not founded");
                    }
                    break;
                }
                case OKATOP: {
                    String okatop = fillOKATO(Constants.QUERY_NAME_PATIENT_OKATOP);
                    if (okatop != null && !okatop.isEmpty()) {
                        result.setOKATOP(okatop);
                        logger.debug("OKATOP={}", okatop);
                    } else {
                        logger.warn("OKATOP: not founded");
                    }
                    break;
                }
                // Представитель пациента
                case FAM_P: {
                    if (spokesman != null) {
                        final String property = spokesman.getFAM();
                        if (property != null && !property.isEmpty()) {
                            result.getSpokesman().setFAM_P(property);
                        } else {
                            logger.warn("FAM_P: property not filled");
                        }
                    }
                    break;
                }
                case IM_P: {
                    // Найден ли представитель ?
                    if (spokesman != null) {
                        final String property = spokesman.getIM();
                        if (property != null && !property.isEmpty()) {
                            result.getSpokesman().setIM_P(property);
                        } else {
                            logger.warn("IM_P: property not filled");
                        }
                    }
                    break;
                }
                case OT_P: {
                    // Найден ли представитель ?
                    if (spokesman != null) {
                        final String property = spokesman.getOT();
                        if (property != null && !property.isEmpty()) {
                            result.getSpokesman().setOT_P(property);
                        } else {
                            logger.warn("OT_P: property not filled");
                        }
                    }
                    break;
                }
                case W_P: {
                    // Найден ли представитель ?
                    if (spokesman != null) {
                        final Short property = spokesman.getSex().shortValue();
                        if (property != 0) {
                            result.getSpokesman().setW_P(property);
                        } else {
                            logger.warn("W_P: property not filled");
                        }
                    }
                    break;
                }
                case DR_P: {
                    // Найден ли представитель ?
                    if (spokesman != null) {
                        final Date property = spokesman.getDR();
                        if (property != null) {
                            result.getSpokesman().setDR_P(DateConvertions.convertDateToUTCMilliseconds(property));
                        } else {
                            logger.warn("DR_P: property not filled");
                        }
                    }
                    break;
                }
                default: {
                    logger.warn("Unknown patient optional field with name [{}]", optionalField.name());
                    break;
                }
            }
        }
        return result;
    }

    private String fillOKATO(final String queryName) {
        try {
            return TFOMSServer.getQueryChache().executeNamedQueryForSingleStringResult(queryName, paramMap);
        } catch (Exception e) {
            logger.error("Exception while executing OKATO properties query. Returning null.", e);
            return null;
        }
    }

    public List<Sluch> formSluchStructure(
            final Account account,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final Set<PatientOptionalFields> patientOptionalFields,
            final String infisCode,
            final String obsoleteInfisCode) {
        final List<Sluch> resultList = new ArrayList<Sluch>(1);
        final UploadRow item = itemList.iterator().next();
        result = new Sluch();
        result.setUSL_OK(getShortValue(item.getUSL_OK()));
        result.setVIDPOM(getShortValue(item.getVIDPOM()));
        result.setFOR_POM(getShortValue(item.getFOR_POM()));
        result.setLPU(infisCode);
        result.setPROFIL(getShortValue(item.getPROFIL()));

        result.setNHISTORY(item.getNHISTORY() != null ? item.getNHISTORY() : "");

        result.setDATE_1(getLongValue(item.getDATE_1()));
        result.setDATE_2(getLongValue(item.getDATE_2()));

        if (item.getDS1() != null) {
            result.setDS1(item.getDS1());
        }

        result.setRSLT(getShortValue(item.getRSLT()));
        result.setISHOD(getShortValue(item.getISHOD()));

        result.setPRVS(getShortValue(item.getPRVS()));
        result.setIDDOKT(item.getIDDOKT() != null ? item.getIDDOKT() : "");

        result.setIDSP(getShortValue(item.getIDSP()));
        result.setED_COL(item.getED_COL());
        //TODO уточнить статус полей ВМП
        result.setVID_HMP(item.getVID_HMP());
        result.setMETOD_HMP(item.getMETOD_HMP());
        //Заполнение дополнительных полей
        for (SluchOptionalFields optionalField : sluchOptionalFields) {
            switch (optionalField) {
                case NPR_MO: {
                    //TODO NPR_MO
//                    if (item.getNPR_MO() != null) {
//                        result.setNPR_MO(item.getNPR_MO());
//                    }
                    break;
                }
                case EXTR: {
                    result.setEXTR(getShortValue(item.getEXTR()));
                    break;
                }
                case LPU_1: {
                    if (item.getLPU_1() != null && !item.getLPU_1().isEmpty()) {
                        result.setLPU_1(item.getLPU_1());
                    } else {
                        result.setLPU_1(obsoleteInfisCode);
                    }
                    break;
                }
                case PODR: {
                    if (item.getPODR() != null && !item.getPODR().isEmpty()) {
                        result.setPODR(item.getPODR());
                    }
                    break;
                }
                case DET: {
                    if (item.getDET() != null) {
                        result.setDET(item.getDET() > 0);
                    }
                    break;
                }
                case DS0: {
                    if (item.getDS0() != null) {
                        result.setDS0(item.getDS0());
                    }
                    break;
                }
                case DS2: {
                    if (item.getDS2() != null) {
                        result.setDS2(item.getDS2());
                    }
                    break;
                }
                case CODE_MES1: {
                    if (item.getCODE_MES1() != null && !item.getCODE_MES1().isEmpty()) {
                        result.setCODE_MES1(item.getCODE_MES1());
                    } else {
                        logger.warn("CODE_MES1: not founded");
                    }
                    break;
                }
                case CODE_MES2: {
                    if (item.getCODE_MES2() != null && !item.getCODE_MES2().isEmpty()) {
                        result.setCODE_MES2(item.getCODE_MES2());
                    } else {
                        logger.warn("CODE_MES2: not founded");
                    }
                    break;
                }
                case OS_SLUCH: {
                    List<Integer> a = new ArrayList<Integer>(2);
                    if (!item.getOS_SLUCH().isEmpty()) {
                        for (String b : item.getOS_SLUCH().split(",")) {
                            try {
                                a.add(Integer.parseInt(b));
                            } catch (NumberFormatException e) {
                                logger.debug("OS_SLUCH: Cannot parse[{}] to integer", b);
                            }
                        }
                    } else {
                        a.add(0);
                    }
                    result.setOS_SLUCH(a);
                    break;
                }
                default: {
                    logger.warn("Unknown optional Field with name \'{}\'", optionalField.name());
                    break;
                }
            }
        }
        result.setPatient(formPacientStructure(item, patientOptionalFields));
        result.setUSL(formUslStructure());
        //Установка общей суммы всех услуг в случае
        result.setSUMV(SUMV);
        result.setIDCASE(insertAccountItem(account, result, item));
        resultList.add(result);
        /* WMIS-75
            При вводе стоматологических услуг пользователи могут указывать количество, в котором была оказана каждая услуга
            (чтобы не дублировать одну и ту же услугу несколько раз).
            В этом случае выбарнное количество сохраняется в Action.amount.
            При выгрузке в этом случае тег <SLUCH> и входящие в него теги <USL> нужно дублировать столько раз,
            сколько раз была оказана услуга.
            При этом в каждом <SLUCH> в тегах, связанных с количеством, указывается число УЕТ на одну услугу.
            Тариф также указывается на одну УЕТ, а сумму - на всю услугу в целом.
         */
        if (Constants.STOMATOLOGY_MEDICAL_KIND_CODE.equals(item.getRbMedicalKindCode()) && Constants.UET_UNIT_CODE.equals(item.getRbMedicalAidUnitCode())) {
            double neededSluchCount = item.getAmount();
            logger.debug("It is stomatology Sluch with Action.amount[{}]", neededSluchCount);
            if (neededSluchCount > 1) {
                for (int i = 1; i < neededSluchCount; i++) {
                    logger.debug("T: insert duplicate #{}", i);
                    Sluch duplicate = result.deepCopy();
                    duplicate.setIDCASE(insertAccountItem(account, duplicate, item));
                    resultList.add(duplicate);
                }
            }
        }
        return resultList;
    }

    private List<Usl> formUslStructure() {
        final List<Usl> resultList = new ArrayList<Usl>(itemList.size());
        for (UploadRow uslItem : itemList) {
            Usl current = new Usl();
            current.setCODE_USL(uslItem.getCODE_USL());
            current.setContract_TariffId(uslItem.getTariffId() != null ? uslItem.getTariffId() : -1);
            current.setKOL_USL(uslItem.getED_COL());
            current.setTARIF(uslItem.getTARIF());
            resultList.add(current);
        }
        return resultList;
    }

    private Patient formPacientStructure(final UploadRow item, Set<PatientOptionalFields> patientOptionalFields) {
        //Данные для тега пациента в случае
        Patient patient = new Patient();
        patient.setNOVOR(item.getNOVOR());
        patient.setPatientId(this.patient);
        patient.setNPOLIS(item.getPolicy() != null ? item.getPolicy().getNPOLIS() : "");
        patient.setSMO(item.getPolicy() != null ? item.getPolicy().getSMO() : "");
        patient.setVPOLIS(getShortValue(item.getPolicy() != null ? item.getPolicy().getVPOLIS() : ""));
        for (PatientOptionalFields optionalField : patientOptionalFields) {
            switch (optionalField) {
                case VNOV_D: {
                    patient.setVNOV_D(getShortValue(patientProperties.getVNOV_D()));
                    break;
                }
                case DOCNUM: {
                    if (item.getDocument() != null && item.getDocument().getDOCNUM() != null && !item.getDocument().getDOCNUM().isEmpty()) {
                        patient.setDOCNUM(item.getDocument().getDOCNUM());
                    }
                    break;
                }
                case DOCSER: {
                    if (item.getDocument() != null && item.getDocument().getDOCSER() != null && !item.getDocument().getDOCSER().isEmpty()) {
                        patient.setDOCSER(item.getDocument().getDOCSER());
                    }
                    break;
                }
                case DOCTYPE: {
                    if (item.getDocument() != null && item.getDocument().getDOCTYPE() != null && !item.getDocument().getDOCTYPE().isEmpty()) {
                        patient.setDOCTYPE(item.getDocument().getDOCTYPE());
                    }
                    break;
                }
                case SPOLIS: {
                    if (item.getPolicy() != null && item.getPolicy().getSPOLIS() != null && !item.getPolicy().getSPOLIS().isEmpty()) {
                        patient.setSPOLIS(item.getPolicy().getSPOLIS());
                    }
                    break;
                }
                case SMO_NAM: {
                    if (item.getPolicy() != null && item.getPolicy().getSMO_NAM() != null && !item.getPolicy().getSMO_NAM().isEmpty()) {
                        patient.setSMO_NAM(item.getPolicy().getSMO_NAM());
                    }
                    break;
                }
                case SMO_OGRN: {
                    if (item.getPolicy() != null && item.getPolicy().getSMO_OGRN() != null && !item.getPolicy().getSMO_OGRN().isEmpty()) {
                        patient.setSMO_OGRN(item.getPolicy().getSMO_OGRN());
                    }
                    break;
                }
                case SMO_OK: {
                    if (item.getPolicy() != null && item.getPolicy().getSMO_OK() != null && !item.getPolicy().getSMO_OK().isEmpty()) {
                        patient.setSMO_OK(item.getPolicy().getSMO_OK());
                    }
                    break;
                }
            }
        }
        return patient;
    }

    /**
     * Сохранение позиции счета и проставление тега IDCASE
     *
     * @param account счет, к которому надо привязать создаваемую позицию счета
     * @param sluch   случай, для которого выставляется позиция счета
     * @return IDCASE
     */
    public int insertAccountItem(final Account account, final Sluch sluch, final UploadRow item) {
        final ru.korus.tmis.core.entity.model.AccountItem newItem = new ru.korus.tmis.core.entity.model.AccountItem();
        newItem.setDeleted(false);
        newItem.setMaster(account);
        newItem.setServiceDate(item.getDATE_2());
        newItem.setClient(new ru.korus.tmis.core.entity.model.Patient(patient));
        newItem.setEvent(new Event(item.getEvent()));
        newItem.setAction(new Action(item.getAction()));
        newItem.setPrice(sluch.getSUMV());
        newItem.setUnit(new RbMedicalAidUnit(item.getRbMedicalAidUnitId()));
        newItem.setAmount(item.getED_COL());
        newItem.setSum(sluch.getSUMV());
        newItem.setDate(null);
        newItem.setNumber(""); //Появится уже после загрузки ответа из ТФОМС
        newItem.setRefuseType(null);
        newItem.setNote("Выгрузка в ТФОМС");
        newItem.setTariff(null);
        newItem.setService(new RbService(item.getRbService()));
        newItem.setNotUploadAnymore(false);
        final ru.korus.tmis.core.entity.model.AccountItem accountItem = TFOMSServer.getAccountItemBean().persistNewItem(newItem);
        logger.debug("FOR Action[{}] NEW AccountItem[{}]", item.getAction(), accountItem.getId());
        //Перевыстваление позиции
        final int reexposeItemCount = TFOMSServer.getAccountItemBean().reexposeItems(accountItem.getId(), item.getAction());
        logger.debug("reexposed {} items", reexposeItemCount);
        return newItem.getId();
    }


    private long getLongValue(final Date param) {
        return DateConvertions.convertDateToUTCMilliseconds(param);
    }

    private short getShortValue(final Integer param) {
        if (param > Short.MIN_VALUE || param < Short.MAX_VALUE) {
            return param.shortValue();
        } else {
            logger.error("Value[{}] is out of range for shortType. return -1;", param);
            return -1;
        }
    }

    private short getShortValue(final String param) {
        try {
            return Short.parseShort(param);
        } catch (NumberFormatException e) {
            logger.error("Cannot parse string[{}] to short. return -1;", param);
            return -1;
        }
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("XMLSluch[");
        sb.append(id).append("-").append(action).append("]");
        for (UploadRow item : itemList) {
            sb.append("\n").append(item);
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public int getAction() {
        return action;
    }

    public int getPatient() {
        return patient;
    }

    public Sluch getResult() {
        return result;
    }
}
