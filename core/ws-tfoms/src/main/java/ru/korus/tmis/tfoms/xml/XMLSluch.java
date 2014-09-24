package ru.korus.tmis.tfoms.xml;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Account;
import ru.korus.tmis.core.entity.model.AccountItem;
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
 * Date: 06.02.14, 15:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: Случай <br>
 */
public class XMLSluch {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(XMLSluch.class);
    //Формат даты, которая будет подставляться в качестве параметра
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");

    //Карта параметров
    private Map<String, String> paramMap;

    //Общая сумма всех услуг
    private Double SUMV = 0.0;

    //Оказанная услуга
    private UploadItem item;
    //Свойства пациента (ФИО,пол ....)
    private PatientProperties patientProperties = null;

    //Свойства усуги (Зависят от типа случая NHISTORY, DS012, RSLT, ISHOD, RSLTRegionalCode)
    private UploadItemProperties itemProperties = null;
    //Свойства случая (не зависящие от типа случая NOVOR, NPR_MO, EXTR, PRVS, IDDOKT)
    private SluchProperties sluchProperties = null;

    //Представитель пациента
    private PatientProperties spokesman = null;
    //Флаг для определения происходил ли уже поиск представителя или нет
    private boolean spokesmanIsSearched = false;

    //Действовавший на момент оказания услуги полис
    private PatientPolicy policy = null;
    //Действовавший на момент оказания услуги документ
    private PatientDocument document = null;

    //Свойства тарифов  (MKU, AID_UNIT, IDSP, ED_COL )
    private UploadUslProperties uslProperties = null;
    //найденные тарифы
    List<UploadUslItem> uslList = null;

    public XMLSluch(final UploadItem item) {
        this.item = item;
        this.paramMap = new HashMap<String, String>();
        this.paramMap.put(Constants.EVENT_ID_PARAM, String.valueOf(item.getEvent()));
        this.paramMap.put(Constants.ACTION_ID_PARAM, String.valueOf(item.getAction()));
        this.paramMap.put(Constants.MEDICAL_KIND_ID_PARAM, String.valueOf(item.getMedicalKind()));
        this.paramMap.put(Constants.MEDICAL_KIND_CODE_PARAM, '\'' + item.getMedicalKindCode() + '\'');
        this.paramMap.put(Constants.PATIENT_ID_PARAM, String.valueOf(item.getPatient()));
        this.paramMap.put(Constants.EVENT_TYPE_ID_PARAM, String.valueOf(item.getEventType()));
        this.paramMap.put(Constants.SERVICE_ID_PARAM, String.valueOf(item.getService()));
        this.paramMap.put(Constants.CHECK_DATE_PARAM, dateTimeFormat.format(item.getEndDate()));
        this.paramMap.put(Constants.SERVICE_INFIS_PARAM, '\'' + item.getServiceInfis() + '\'');
        this.paramMap.put(Constants.PROFIL_CODE_PARAM, '\'' + item.getPROFIL() + '\'');
        if (item.getBegDate() != null) {
            this.paramMap.put(Constants.CHECK_DATE_BEGIN_PARAM, dateTimeFormat.format(item.getBegDate()));
        } else {
            logger.warn("This sluch has not setted Action.begDate");
            //this.paramMap.put(Constants.CHECK_DATE_BEGIN_PARAM, Constants.NULL);
        }
    }

    public XMLSluch(final UploadItem item, final XMLSluch masterSluch) {
        this(item);
        //Master sluch
        this.paramMap.put(Constants.MASTER_SHECK_DATE_PARAM, masterSluch.getParameters().get(Constants.CHECK_DATE_PARAM));
        this.paramMap.put(Constants.MASTER_EVENT_ID_PARAM, masterSluch.getParameters().get(Constants.EVENT_ID_PARAM));
        this.paramMap.put(Constants.MASTER_EVENT_TYPE_ID, masterSluch.getParameters().get(Constants.EVENT_TYPE_ID_PARAM));
        this.paramMap.put(Constants.MASTER_DS1, masterSluch.getParameters().get(Constants.DS1_PARAM));
    }

    /**
     * Заполнение свойств случая (такие как RSLT, ISHOD, DS0, DS1, DS2)
     *
     * @return true-заполены, false-не заполнены
     */
    public boolean fillUploadItemProperties(final String queryName) {
        List<UploadItemProperties> propertyList;
        try {
            propertyList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, UploadItemProperties.class);
        } catch (Exception e) {
            logger.error("Exception while executing uploadItemProperties query. Returning false.", e);
            return false;
        }
        if (!propertyList.isEmpty()) {
            this.itemProperties = propertyList.iterator().next();
            XMLHelper.toLog(this.itemProperties);
            if (itemProperties.getRSLT() != null) {
                this.paramMap.put(Constants.RSLT_PARAM, '\'' + itemProperties.getRSLT() + '\'');
            }
            if (itemProperties.getISHOD() != null) {
                this.paramMap.put(Constants.ISHOD_PARAM, '\'' + itemProperties.getISHOD() + '\'');
            }
            if (itemProperties.getDS0() != null) {
                this.paramMap.put(Constants.DS0_PARAM, '\'' + itemProperties.getDS0() + '\'');
            }
            if (itemProperties.getDS1() != null) {
                this.paramMap.put(Constants.DS1_PARAM, '\'' + itemProperties.getDS1() + '\'');
            }
            if (itemProperties.getDS2() != null) {
                this.paramMap.put(Constants.DS2_PARAM, '\'' + itemProperties.getDS2() + '\'');
            }
            if (itemProperties.getNHISTORY() != null) {
                this.paramMap.put(Constants.NHISTORY_PARAM, '\'' + itemProperties.getNHISTORY() + '\'');
            }
            if (this.itemProperties.getRegionalCode() != null) {
                this.paramMap.put(Constants.RSLT_REGIONAL_CODE_PARAM, '\'' + itemProperties.getRegionalCode() + '\'');
            }
            return true;
        }
        return false;
    }

    /**
     * Заполнение документа пациента, который дествовал на момент оказания услуги
     *
     * @return true- документ найден \  false - документ не найден
     */
    public boolean fillPatientDocument(final String queryName) {
        List<PatientDocument> patientDocumentList;
        try {
            patientDocumentList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, PatientDocument.class);
        } catch (Exception e) {
            logger.error("Exception while executing document query. Returning false.", e);
            return false;
        }
        if (!patientDocumentList.isEmpty()) {
            this.document = patientDocumentList.iterator().next();
            this.paramMap.put(Constants.DOCUMENT_ID_PARAM, String.valueOf(this.document.getDocument()));
            XMLHelper.toLog(this.document);
            return true;
        } else {
            //У пациента не найдено документа, получим его представителя и проверим документ представителя
            logger.debug("Patient[{}] has not document", item.getPatient());
            if (spokesmanIsSearched) {
                //Поиск представителя уже проводился
                if (spokesman == null) {
                    // И представитель не был найден
                    logger.debug("Patient[{}] has not document and Spokesman.", item.getPatient());
                    return false;
                } else {
                    //Представитель был найден, ищем документы представителя
                    return fillSpokesmanDocument(queryName);
                }
            } else {
                // Поиск представителя еще не проводился, пробуем его найти
                if (!fillSpokesman(Constants.PATIENT_SPOKESMAN)) {
                    // Не нашли представителя
                    logger.debug("Patient[{}] has not document and Spokesman.", item.getPatient());
                    return false;
                } else {
                    //Нашли представителя, теперь ищем документы представителя
                    return fillSpokesmanDocument(queryName);
                }
            }
        }
    }

    /**
     * Заполнение документа из документов представителя
     *
     * @return найден документ или нет
     */
    private boolean fillSpokesmanDocument(final String queryName) {
        //Подменяем идентификатор пациента на идентификатор представителя
        paramMap.put(Constants.PATIENT_ID_PARAM, String.valueOf(spokesman.getId()));
        final String query = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                queryName,
                paramMap
        );
        //Возвращаем старое значение идетификатора пациента
        paramMap.put(Constants.PATIENT_ID_PARAM, String.valueOf(item.getPatient()));
        if (query == null) {
            logger.error("PatientDocument query with name=\'{}\' not exists", queryName);
            return false;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Spokesman document query:\n{}", query);
        }
        List<PatientDocument> patientDocumentList;
        try {
            patientDocumentList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForDocument(query);
        } catch (Exception e) {
            logger.error("Exception while executing document query. Returning false.", e);
            return false;
        }
        if (!patientDocumentList.isEmpty()) {
            logger.debug("Document found by spokesman");
            this.document = patientDocumentList.iterator().next();
            this.paramMap.put(Constants.SPOKESMAN_DOCUMENT_ID_PARAM, String.valueOf(document.getDocument()));
            XMLHelper.toLog(document);
            return true;
        }
        return false;
    }

    /**
     * Заполнение полиса пациента, который дествовал на момент оказания услуги
     *
     * @return true- полис найден \  false - полис не найден
     */
    public boolean fillPatientPolicy(final String queryName) {
        List<PatientPolicy> patientPolicyList;
        try {
            patientPolicyList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, PatientPolicy.class);
        } catch (Exception e) {
            logger.error("Exception while executing policy query. Returning false.", e);
            return false;
        }
        if (!patientPolicyList.isEmpty()) {
            this.policy = patientPolicyList.iterator().next();
            XMLHelper.toLog(this.policy);
            // Полис самого пациента
            paramMap.put(Constants.POLICY_ID_PARAM, String.valueOf(policy.getPolicy()));
            // Страховщик
            paramMap.put(Constants.INSURER_ID_PARAM, String.valueOf(policy.getInsurer()));
            return true;
        } else {
            //У пациента не найдено полиса, получим его представителя и проверим полис представителя
            logger.debug("Patient[{}] has not policy", item.getPatient());
            if (spokesmanIsSearched) {
                //Поиск представителя уже проводился
                if (spokesman == null) {
                    // И представитель не был найден
                    logger.debug("Patient[{}] has not policy and Spokesman.", item.getPatient());
                    return false;
                } else {
                    //Представитель был найден, ищем документы представителя
                    return fillSpokesmanPolicy(queryName);
                }
            } else {
                // Поиск представителя еще не проводился, пробуем его найти
                if (!fillSpokesman(Constants.PATIENT_SPOKESMAN)) {
                    // Не нашли представителя
                    logger.debug("Patient[{}] has not policy and Spokesman.", item.getPatient());
                    return false;
                } else {
                    //Нашли представителя, теперь ищем полис представителя
                    return fillSpokesmanPolicy(queryName);
                }
            }
        }
    }

    /**
     * Заполение полиса из полиса представителя
     *
     * @return найден ли полис представителя
     */
    private boolean fillSpokesmanPolicy(final String queryName) {
        //Подменяем идентификатор пациента на идентификатор представителя
        paramMap.put(Constants.PATIENT_ID_PARAM, String.valueOf(spokesman.getId()));
        final String query = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                queryName,
                paramMap
        );
        //Возвращаем старое значение идетификатора пациента
        paramMap.put(Constants.PATIENT_ID_PARAM, String.valueOf(item.getPatient()));
        if (query == null) {
            logger.error("PatientPolicy query with name=\'{}\' not exists", queryName);
            return false;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Spokesman policy query:\n{}", query);
        }
        List<PatientPolicy> patientPolicyList;
        try {
            patientPolicyList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForPolicy(query);
        } catch (Exception e) {
            logger.error("Exception while executing policy query. Returning false.", e);
            return false;
        }
        if (!patientPolicyList.isEmpty()) {
            this.policy = patientPolicyList.iterator().next();
            XMLHelper.toLog(this.policy);
            // Полис представителя пациента
            this.paramMap.put(Constants.SPOKESMAN_POLICY_ID_PARAM, String.valueOf(policy.getPolicy()));
            // Страховщик (ОБЩЕЕ поле)
            this.paramMap.put(Constants.INSURER_ID_PARAM, String.valueOf(policy.getInsurer()));
            return true;
        }
        return false;
    }

    /**
     * Заполнение информации о пациенте
     *
     * @return найдена информация или нет
     */
    public boolean fillPatientProperties(final String queryName) {
        List<PatientProperties> patientPropertiesList;
        try {
            patientPropertiesList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, PatientProperties.class);
        } catch (Exception e) {
            logger.error("Exception while executing patient properties query. Returning false.", e);
            return false;
        }
        if (!patientPropertiesList.isEmpty()) {
            this.patientProperties = patientPropertiesList.iterator().next();
            XMLHelper.toLog(this.patientProperties);
            // Пол
            this.paramMap.put(Constants.SEX_PARAM, String.valueOf(patientProperties.getSex()));
            // Дата рождения
            this.paramMap.put(Constants.BIRTH_DATE_PARAM, dateTimeFormat.format(patientProperties.getDR()));
            return true;
        }
        return false;
    }

    private String fillOKATOG(final String queryName) {
        try {
            return TFOMSServer.getQueryChache().executeNamedQueryForSingleStringResult(queryName, paramMap);
        } catch (Exception e) {
            logger.error("Exception while executing OKATOG properties query. Returning null.", e);
            return null;
        }
    }

    private String fillOKATOP(final String queryName) {
        try {
            return TFOMSServer.getQueryChache().executeNamedQueryForSingleStringResult(queryName, paramMap);
        } catch (Exception e) {
            logger.error("Exception while executing OKATOG properties query. Returning null.", e);
            return null;
        }
    }


    /**
     * Поиск и заполнение представителя пациента
     *
     * @return найден ли представитель или нет
     */
    private boolean fillSpokesman(final String queryName) {
        this.spokesmanIsSearched = true;
        List<PatientProperties> spokesmanList;
        try {
            spokesmanList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, PatientProperties.class);
        } catch (Exception e) {
            logger.error("Exception while executing patient spokesman query. Returning false.", e);
            return false;
        }
        if (!spokesmanList.isEmpty()) {
            this.spokesman = spokesmanList.iterator().next();
            logger.debug("Spokesman:");
            XMLHelper.toLog(spokesman);
            // Ид представителя пациента
            this.paramMap.put(Constants.SPOKESMAN_ID_PARAM, String.valueOf(spokesman.getId()));
            return true;
        }
        return false;
    }

    public boolean fillSluchProperties(final String queryName) {
        List<SluchProperties> sluchPropertiesList;
        try {
            sluchPropertiesList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, SluchProperties.class);
        } catch (Exception e) {
            logger.error("Exception while executing sluchProperties query. Returning false.", e);
            return false;
        }
        if (!sluchPropertiesList.isEmpty()) {
            this.sluchProperties = sluchPropertiesList.iterator().next();
            XMLHelper.toLog(sluchProperties);
            return true;
        }
        return false;
    }

    public boolean isException(final String queryName) {
        final String query = TFOMSServer.getQueryChache().getQueryWithSettedParams(queryName, paramMap);
        if (query == null) {
            logger.debug("IF Exception Query not exists. it means that sluch is correct.");
            return false;
        }
        List resultList;
        try {
            resultList = TFOMSServer.getSpecialPreferencesBean().executeNamedQuery(query);
            if (((Number) resultList.get(0)).intValue() == 0) {
                logger.debug("This sluch is Exception!");
                return true;
            } else {
                logger.debug("It is NOT exception sluch. Continue processing...");
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception while executing sluchProperties query. Returning true.", e);
            return true;
        }
    }

    public boolean fillUploadUslProperties(final String queryName) {
        List<UploadUslProperties> resultList;
        try {
            resultList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, UploadUslProperties.class);
        } catch (Exception e) {
            logger.error("Exception while executing uslProperties query. Returning false.", e);
            return false;
        }
        if (!resultList.isEmpty()) {
            this.uslProperties = resultList.iterator().next();
            XMLHelper.toLog(uslProperties);
            // MKU
            if (uslProperties.getMku() != null) {
                this.paramMap.put(Constants.MKU_ID_PARAM, String.valueOf(uslProperties.getMku()));
            }
            // Кол-во оказанных услуг
            if (uslProperties.getED_COL() != null) {
                this.paramMap.put(Constants.ED_COL_PARAM, String.valueOf(uslProperties.getED_COL()));
            }
            // Код способа оплаты
            if (uslProperties.getIDSP() != null) {
                this.paramMap.put(Constants.IDSP_PARAM, uslProperties.getIDSP());
            }
            // Ид единицы учета услуги
            if (uslProperties.getUnit() != null) {
                this.paramMap.put(Constants.UNIT_ID_PARAM, String.valueOf(uslProperties.getUnit()));
            }
            if (uslProperties.getCsg() != null) {
                this.paramMap.put(Constants.CSG_GROUP_ID_PARAM, String.valueOf(uslProperties.getCsg()));
            }
            return true;
        }
        return false;

    }

    /**
     * Получение тарифов для оказанной услуги
     *
     * @return true- найден хоть один тариф / false - не найдено ни одного тарифа
     */
    public boolean fillUsl(final String queryName, final boolean withEmptySum) {
        List<UploadUslItem> uslItemList;
        try {
            uslItemList = TFOMSServer.getQueryChache().executeNamedQuery(queryName, paramMap, UploadUslItem.class);
        } catch (Exception e) {
            logger.error("Exception while executing tariff query. Returning false.", e);
            return false;
        }
        if (!uslItemList.isEmpty()) {
            this.uslList = uslItemList;
            XMLHelper.toLog(uslList);
            if (!withEmptySum) {
                for (UploadUslItem current : uslItemList) {
                    //Увеличение общей стоимости случая на стоимость конкретной услуги
                    SUMV += current.getTARIF() * (current.getKOL_USL() != null ? current.getKOL_USL() : 1.0);
                }
            } else {
                for (UploadUslItem current : uslItemList) {
                    current.setTARIF(0.0);
                }
                SUMV = 0.0;
            }
            return true;
        }
        return false;
    }


    public boolean needAdditionalPoliclinicSluch() {
        return "2".equals(uslProperties.getUnitCode()) && "P".equals(item.getMedicalKindCode());
    }

    public boolean needAdditionalDispanserizationSluch() {
        return Constants.DISPANSERIZATION_IDSP_LIST.contains(uslProperties.getIDSP())
                && (
                Constants.DISPANSERIZATION_SERVICE_INFIS_PART.contains(item.getServiceInfis().substring(2, 5))
                        && !"P".equals(item.getMedicalKindCode())
        ) || (
                "2".equals(itemProperties.getRegionalCode())
                        && "P".equals(item.getMedicalKindCode())
        );
    }

    public boolean isChildDispanserizationUnder3Years() {
        return Constants.DISPANSERIZATION_MEDICAL_KIND_CODE.equals(item.getMedicalKindCode())
                && Years.yearsBetween(new DateTime(patientProperties.getDR()), new DateTime(item.getEndDate().getTime())).getYears() < 3;
    }


    public List<Sluch> formSluchStructure(
            final Account account,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final Set<PatientOptionalFields> patientOptionalFields,
            final String infisCode,
            final String obsoleteInfisCode,
            final boolean isPoliclinic) {
        final List<Sluch> resultList = new ArrayList<Sluch>(1);

        final Sluch result = new Sluch();
        result.setUSL_OK(getUSL_OK());
        result.setVIDPOM(getVIDPOM());
        result.setFOR_POM(getFOR_POM());
        result.setLPU(infisCode);
        result.setPROFIL(getPROFIL());

        result.setNHISTORY(getNHISTORY());

        result.setDATE_1(getBeginDate());
        result.setDATE_2(getEndDate());

        if (itemProperties.getDS1() != null) {
            result.setDS1(itemProperties.getDS1());
        }

        result.setRSLT(getRSLT());
        result.setISHOD(getISHOD());

        result.setPRVS(getPRVS());
        result.setIDDOKT(getIDDOKT());

        result.setIDSP(getIDSP());
        result.setED_COL(getED_COL());
        //Заполнение дополнительных полей
        for (SluchOptionalFields optionalField : sluchOptionalFields) {
            switch (optionalField) {
                case NPR_MO: {
                    if (sluchProperties != null && sluchProperties.getNPR_MO() != null) {
                        result.setNPR_MO(sluchProperties.getNPR_MO());
                    }
                    break;
                }
                case EXTR: {
                    if (sluchProperties != null && sluchProperties.getEXTR() != null) {
                        try {
                            result.setEXTR(Short.parseShort(sluchProperties.getEXTR()));
                        } catch (NumberFormatException e) {
                            logger.warn("EXTR: incorrect value [{}]", sluchProperties.getEXTR());
                        }
                    }
                    break;
                }
                case LPU_1: {
                    if (sluchProperties != null && sluchProperties.getLPU_1() != null && !sluchProperties.getLPU_1().isEmpty()) {
                        result.setLPU_1(sluchProperties.getLPU_1());
                    } else {
                        result.setLPU_1(obsoleteInfisCode);
                    }
                    break;
                }
                case PODR: {
                    if (sluchProperties != null && sluchProperties.getPODR() != null) {
                        result.setPODR(sluchProperties.getPODR());
                    }
                    break;
                }
                case DET: {
                    if (sluchProperties != null && sluchProperties.getDET() != null) {
                        result.setDET(!"0".equals(sluchProperties.getDET()));
                    }
                    break;
                }
                case DS0: {
                    if (itemProperties.getDS0() != null) {
                        result.setDS0(itemProperties.getDS0());
                    }
                    break;
                }
                case DS2: {
                    if (itemProperties.getDS2() != null) {
                        result.setDS2(itemProperties.getDS2());
                    }
                    break;
                }
                case CODE_MES1: {
                    String CODE_MES1 = fillCODE_MES1(isPoliclinic);
                    if (CODE_MES1 != null && !CODE_MES1.isEmpty()) {
                        result.setCODE_MES1(CODE_MES1);
                    } else {
                        logger.warn("CODE_MES1: not founded");
                    }
                    break;
                }
                case CODE_MES2: {
                    if (isPoliclinic) {
                        String CODE_MES2 = fillCODE_MES2(isPoliclinic);
                        if (CODE_MES2 != null && !CODE_MES2.isEmpty()) {
                            result.setCODE_MES2(CODE_MES2);
                        } else {
                            logger.warn("CODE_MES2: not founded");
                        }
                    } else {
                        logger.debug("CODE_MES2: not needed for stationary");
                    }
                    break;
                }
                case OS_SLUCH: {
                    result.setOS_SLUCH(fillOS_SLUCH(Constants.OS_SLUCH));
                    break;
                }
                default: {
                    logger.warn("Unknown optional Field with name \'{}\'", optionalField.name());
                    break;
                }
            }
        }
        //Данные для тега пациента в случае
        Patient patient = new Patient();
        patient.setNOVOR(getNOVOR());
        patient.setPatientId(item.getPatient());
        patient.setNPOLIS(getNPOLIS());
        patient.setSMO(getSMO());
        patient.setVPOLIS(getVPOLIS());
        for (PatientOptionalFields optionalField : patientOptionalFields) {
            switch (optionalField) {
                case VNOV_D: {
                    if (Constants.VNOV_D_CSG_CODE_LIST.contains(uslProperties.getCsgCode())
                            && Years.yearsBetween(new DateTime(patientProperties.getDR()), new DateTime(item.getBegDate().getTime())).getYears() < 18) {
                        try {
                            patient.setVNOV_D(Integer.parseInt(patientProperties.getVNOV_D()));
                        } catch (NumberFormatException e) {
                            logger.warn("VNOV_D: incorrect value [{}}", patientProperties.getVNOV_D());
                        }
                    }
                    break;
                }
                case DOCNUM: {
                    final String fieldValue = getDOCNUM();
                    if (fieldValue != null) {
                        patient.setDOCNUM(fieldValue);
                    }
                    break;
                }
                case DOCSER: {
                    final String fieldValue = getDOCSER();
                    if (fieldValue != null) {
                        patient.setDOCSER(fieldValue);
                    }
                    break;
                }
                case DOCTYPE: {
                    final String fieldValue = getDOCTYPE();
                    if (fieldValue != null) {
                        patient.setDOCTYPE(fieldValue);
                    }
                    break;
                }
                case SPOLIS: {
                    final String fieldValue = getSPOLIS();
                    if (fieldValue != null) {
                        patient.setSPOLIS(fieldValue);
                    }
                    break;
                }
                case SMO_NAM: {
                    final String fieldValue = getSMO_NAM();
                    if (fieldValue != null) {
                        patient.setSMO_NAM(fieldValue);
                    }
                    break;
                }
                case SMO_OGRN: {
                    final String fieldValue = getSMO_OGRN();
                    if (fieldValue != null) {
                        patient.setSMO_OGRN(fieldValue);
                    }
                    break;
                }
                case SMO_OK: {
                    if (policy != null) {
                        patient.setSMO_OK(getSMO_OK());
                    } else {
                        logger.debug("SMO_OK: policy not founded");
                    }
                    break;
                }
            }
        }
        result.setPatient(patient);

        result.setUSL(formUslStructure());
        //Установка общей суммы всех услуг в случае
        result.setSUMV(SUMV);
        result.setIDCASE(insertAccountItem(account, result));
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
        if (Constants.STOMATOLOGY_MEDICAL_KIND_CODE.equals(item.getMedicalKindCode()) && Constants.UET_UNIT_CODE.equals(uslProperties.getUnitCode())) {
            double neededSluchCount = item.getActionAmount();
            logger.debug("It is stomatology Sluch with Action.amount[{}]", neededSluchCount);
            if (neededSluchCount > 1) {
                for (int i = 1; i < neededSluchCount; i++) {
                    logger.debug("T: insert duplicate #{}", i);
                    Sluch duplicate = result.deepCopy();
                    duplicate.setIDCASE(insertAccountItem(account, duplicate));
                    resultList.add(duplicate);
                }
            }
        }
        return resultList;
    }

    private String getSMO_OK() {
        if (policy != null) {
            return policy.getSMO_OK() != null ? policy.getSMO_OK() : null;
        } else {
            logger.debug("SMO_OK: policy not founded");
            return null;
        }
    }

    private String getSMO_OGRN() {
        if (policy != null) {
            return policy.getSMO_OGRN() != null ? policy.getSMO_OGRN() : null;
        } else {
            logger.debug("SMO_OGRN: policy not founded");
            return null;
        }
    }

    private String getSMO_NAM() {
        if (policy != null) {
            return policy.getSMO_NAM() != null ? policy.getSMO_NAM() : null;
        } else {
            logger.debug("SMO_NAM: policy not founded");
            return null;
        }
    }

    private String getDOCTYPE() {
        if (document != null) {
            return document.getDOCTYPE() != null ? document.getDOCTYPE() : null;
        } else {
            logger.debug("DOCTYPE: document not founded");
            return null;
        }
    }

    private String getDOCSER() {
        if (document != null) {
            return document.getDOCSER() != null ? document.getDOCSER() : null;
        } else {
            logger.debug("DOCSER: document not founded");
            return null;
        }
    }

    private double getED_COL() {
        return uslList.iterator().next().getKOL_USL() != null ? uslList.iterator().next().getKOL_USL() : 0.0;
    }

    private String getDOCNUM() {
        if (document != null) {
            return document.getDOCNUM() != null ? document.getDOCNUM() : null;
        } else {
            logger.debug("DOCNUM: document not founded");
            return null;
        }
    }

    private List<Integer> fillOS_SLUCH(final String queryName) {
        final String query = TFOMSServer.getQueryChache().getQueryWithSettedParams(queryName, paramMap);
        final ArrayList<Integer> result = new ArrayList<Integer>(2);
        if (query == null || query.isEmpty()) {
            logger.warn("OS_SLUCH query with name =\'{}\' not exists. Return 0.", queryName);
            logger.debug("OS_SLUCH: 0");
            result.add(0);
            return result;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("OS_SLUCH query:\n{}", query);
        }
        List queryResult;
        try {
            queryResult = TFOMSServer.getSpecialPreferencesBean().executeNamedQuery(query);
        } catch (Exception e) {
            logger.error("Exception while executing OS_SLUCH properties query. Returning 0.", e);
            logger.debug("OS_SLUCH: 0");
            result.add(0);
            return result;
        }
        if (!queryResult.isEmpty()) {
            for (Object current : queryResult) {
                int currentValue = ((Number) current).intValue();
                logger.debug("OS_SLUCH: {}", currentValue);
                result.add(currentValue);
            }
            return result;
        }
        logger.debug("OS_SLUCH: 0");
        result.add(0);
        return result;
    }

    private String fillCODE_MES1(boolean isPoliclinic) {
        final String queryName = isPoliclinic ? Constants.POLICLINIC_CODE_MES1 : Constants.STATIONARY_CODE_MES1;
        try {
            return TFOMSServer.getQueryChache().executeNamedQueryForSingleStringResult(queryName, paramMap);
        } catch (Exception e) {
            logger.error("Exception while executing CODE_MES2 properties query. Returning null.", e);
            return null;
        }
    }

    private String fillCODE_MES2(boolean isPoliclinic) {
        final String queryName = isPoliclinic ? Constants.POLICLINIC_CODE_MES2 : Constants.STATIONARY_CODE_MES2;
        try {
            return TFOMSServer.getQueryChache().executeNamedQueryForSingleStringResult(queryName, paramMap);
        } catch (Exception e) {
            logger.error("Exception while executing CODE_MES2 properties query. Returning null.", e);
            return null;
        }
    }

    private short getIDSP() {
        if (uslProperties.getIDSP() != null) {
            //Перевод в число
            try {
                return Short.parseShort(uslProperties.getIDSP());
            } catch (NumberFormatException e) {
                logger.warn("IDSP: incorrect value [{}]", uslProperties.getIDSP());
                return -1;
            }
        } else {
            logger.warn("IDSP:NULL");
            return -1;
        }
    }

    private String getNOVOR() {
        if (sluchProperties != null && sluchProperties.getNOVOR() != null) {
            return sluchProperties.getNOVOR();
        } else {
            logger.debug("NOVOR: NULL");
            return Constants.NULL;
        }
    }


    private String getNHISTORY() {
        return itemProperties.getNHISTORY() != null ? itemProperties.getNHISTORY() : Constants.NULL;
    }

    private String getIDDOKT() {
        if (sluchProperties != null && sluchProperties.getIDDOKT() != null) {
            return sluchProperties.getIDDOKT();
        } else {
            logger.debug("IDDOKT: NULL");
            return Constants.NULL;
        }
    }

    private int getPRVS() {
        if (sluchProperties != null && sluchProperties.getPRVS() != null) {
            try {
                return Integer.parseInt(sluchProperties.getPRVS());
            } catch (NumberFormatException e) {
                logger.warn("PRVS: incorrect value [{}]", sluchProperties.getPRVS());
                return -1;
            }
        } else {
            logger.warn("PRVS: null");
            return -1;
        }
    }

    private short getPROFIL() {
        if (item.getPROFIL() != null) {
            try {
                return Short.parseShort(item.getPROFIL());
            } catch (NumberFormatException e) {
                logger.warn("PROFIL: incorrect value [{}]", item.getPROFIL());
                return -1;
            }
        } else {
            logger.warn("PROFIL: null");
            return -1;
        }
    }

    private String getSMO() {
        if (policy != null) {
            return policy.getSMO() != null ? policy.getSMO() : Constants.NULL;
        } else {
            logger.debug("SMO: policy not founded");
            return Constants.NULL;
        }
    }

    private String getNPOLIS() {
        if (policy != null) {
            return policy.getNPOLIS() != null ? policy.getNPOLIS() : Constants.NULL;
        } else {
            logger.debug("NPOLIS: policy not founded");
            return Constants.NULL;
        }
    }

    private String getSPOLIS() {
        if (policy != null) {
            return policy.getSPOLIS() != null ? policy.getSPOLIS() : null;
        } else {
            logger.debug("SPOLIS: policy not founded");
            return null;
        }
    }

    private short getVPOLIS() {
        if (policy != null) {
            try {
                return Short.parseShort(policy.getVPOLIS());
            } catch (NumberFormatException e) {
                logger.warn("VPOLIS: incorrect value [{}]", policy.getVPOLIS());
            }
            return -1;
        } else {
            logger.debug("VPOLIS: policy not founded");
            return -1;
        }
    }

    private short getFOR_POM() {
        if (item.getFOR_POM() != null) {
            try {
                return Short.parseShort(item.getFOR_POM());
            } catch (NumberFormatException e) {
                logger.warn("FOR_POM: incorrect value [{}]", item.getFOR_POM());
                return -1;
            }
        } else {
            logger.warn("FOR_POM: null");
            return -1;
        }
    }


    private short getISHOD() {
        if (itemProperties.getISHOD() != null) {
            try {
                return Short.parseShort(itemProperties.getISHOD());
            } catch (NumberFormatException e) {
                logger.warn("ISHOD: value [{}] is incorrect", itemProperties.getISHOD());
                return -1;
            }
        } else {
            logger.warn("ISHOD: NULL");
            return -1;
        }
    }

    private short getRSLT() {
        if (itemProperties.getRSLT() != null) {
            try {
                return Short.parseShort(itemProperties.getRSLT());
            } catch (NumberFormatException e) {
                logger.warn("RSLT: value [{}] is incorrect", itemProperties.getRSLT());
                return -1;
            }
        } else {
            logger.warn("RSLT: NULL");
            return -1;
        }
    }

    private long getEndDate() {
        return DateConvertions.convertDateToUTCMilliseconds(item.getEndDate());
    }

    private long getBeginDate() {
        return DateConvertions.convertDateToUTCMilliseconds(item.getBegDate());
    }

    private short getVIDPOM() {
        if (item.getVIDPOM() != null) {
            try {
                return Short.parseShort(item.getVIDPOM());
            } catch (NumberFormatException e) {
                logger.error("VIDPOM: incorrect value [{}]", item.getVIDPOM());
                return -1;
            }
        } else {
            logger.error("VIDPOM: null");
            return -1;
        }
    }

    private short getUSL_OK() {
        if (item.getUSL_OK() != null) {
            try {
                return Short.parseShort(item.getUSL_OK());
            } catch (NumberFormatException e) {
                logger.error("USL_OK: incorrect value [{}]", item.getUSL_OK());
                return -1;
            }
        } else {
            logger.error("USL_OK: null");
            return -1;
        }
    }


    private List<Usl> formUslStructure() {
        final List<Usl> resultList = new ArrayList<Usl>(uslList.size());
        for (UploadUslItem uslItem : uslList) {
            Usl current = new Usl();
            current.setCODE_USL(uslItem.getCODE_USL());
            current.setContract_TariffId(uslItem.getContractTariff() != null ? uslItem.getContractTariff() : -1);
            current.setKOL_USL(uslItem.getKOL_USL());
            current.setTARIF(uslItem.getTARIF());
            resultList.add(current);
        }
        return resultList;
    }

    /**
     * Сохранение позиции счета и проставление тега IDCASE
     *
     * @param account счет, к которому надо привязать создаваемую позицию счета
     * @param sluch   случай, для которого выставляется позиция счета
     * @return IDCASE
     */
    public int insertAccountItem(final Account account, final Sluch sluch) {
        final AccountItem newItem = new AccountItem();
        newItem.setDeleted(false);
        newItem.setMaster(account);
        newItem.setServiceDate(item.getEndDate());
        newItem.setClient(new ru.korus.tmis.core.entity.model.Patient(item.getPatient()));
        newItem.setEvent(new Event(item.getEvent()));
        newItem.setAction(new Action(item.getAction()));
        newItem.setPrice(sluch.getSUMV());
        newItem.setUnit(new RbMedicalAidUnit(uslProperties.getUnit()));
        newItem.setAmount(uslList.iterator().next().getKOL_USL());
        newItem.setSum(sluch.getSUMV());
        newItem.setDate(null);
        newItem.setNumber(""); //Появится уже после загрузки ответа из ТФОМС
        newItem.setRefuseType(null);
        newItem.setNote("Выгрузка в ТФОМС");
        newItem.setTariff(null);
        newItem.setService(new RbService(item.getService()));
        newItem.setNotUploadAnymore(false);
        final AccountItem accountItem = TFOMSServer.getAccountItemBean().persistNewItem(newItem);
        logger.debug("FOR Action[{}] NEW AccountItem[{}]", item.getAction(), accountItem.getId());
        //Перевыстваление позиции
        final int reexposeItemCount = TFOMSServer.getAccountItemBean().reexposeItems(accountItem.getId(), item.getAction());
        logger.debug("reexposed {} items", reexposeItemCount);
        return newItem.getId();
    }

    public Person formPersonStructure(final Set<PersonOptionalFields> personOptionalFields, boolean needForSpokesman) {
        final Person result = new Person();
        result.setPatientId(item.getPatient());
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
                    String okatog = fillOKATOG(Constants.PATIENT_OKATOG);
                    if (okatog != null && !okatog.isEmpty()) {
                        result.setOKATOG(okatog);
                    } else {
                        logger.warn("OKATOG: not founded");
                    }
                    break;
                }
                case OKATOP: {
                    String okatop = fillOKATOP(Constants.PATIENT_OKATOP);
                    if (okatop != null && !okatop.isEmpty()) {
                        result.setOKATOP(okatop);
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
                        if (property != null) {
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

    /////////////////////////////////////////////////////////////// GET & SET ///////////////////////

    public UploadItem getItem() {
        return item;
    }

    public void setItem(UploadItem item) {
        this.item = item;
    }

    public UploadItemProperties getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(UploadItemProperties itemProperties) {
        this.itemProperties = itemProperties;
    }

    public PatientPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(PatientPolicy policy) {
        this.policy = policy;
    }

    public PatientDocument getDocument() {
        return document;
    }

    public void setDocument(PatientDocument document) {
        this.document = document;
    }

    public PatientProperties getPatientProperties() {
        return patientProperties;
    }

    public void setPatientProperties(PatientProperties patientProperties) {
        this.patientProperties = patientProperties;
        // Пол
        this.paramMap.put(Constants.SEX_PARAM, String.valueOf(patientProperties.getSex()));
        // Дата рождения
        this.paramMap.put(Constants.BIRTH_DATE_PARAM, dateTimeFormat.format(patientProperties.getDR()));
    }

    public SluchProperties getSluchProperties() {
        return sluchProperties;
    }

    public void setSluchProperties(SluchProperties sluchProperties) {
        this.sluchProperties = sluchProperties;
    }

    public UploadUslProperties getUslProperties() {
        return uslProperties;
    }

    public void setUslProperties(UploadUslProperties uslProperties) {
        this.uslProperties = uslProperties;
    }

    //Получение карты параметров
    public Map<String, String> getParameters() {
        return paramMap;
    }
}
