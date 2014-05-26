package ru.korus.tmis.tfoms.xml;

import org.apache.thrift.TException;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Account;
import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.tfoms.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.tfoms.Constants;
import ru.korus.tmis.tfoms.TFOMSErrors;
import ru.korus.tmis.tfoms.TFOMSServer;
import ru.korus.tmis.tfoms.thriftgen.*;
import scala.collection.immutable.Stream;
import scala.tools.nsc.backend.icode.analysis.CopyPropagation;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 10.06.13, 13:55 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public final class XMLHelper {
    //Логгер
    private static final Logger logger = LoggerFactory.getLogger(XMLHelper.class);

    /**
     * Выгрузка всех поликлинических услуг
     *
     * @param registry реестр куда попадет результат
     */
    public static void processPoliclinicSluch(
            final Map<Person, List<Sluch>> registry,
            final boolean primaryAccount,
            final Account account,
            final String infisCode,
            final String obsoleteInfisCode,
            final Set<PatientOptionalFields> patientOptionalFields,
            final Set<PersonOptionalFields> personOptionalFields,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final boolean needForSpokesman) {
        final List<UploadRow> itemList;
        try {
            itemList = TFOMSServer.getQueryChache().executeNamedQuery(
                    Constants.QUERY_NAME_POLICLINIC_MAIN,
                    UploadRow.class,
                    null,
                    true
            );
            XMLHelper.toLog(itemList);
        } catch (Exception e) {
            logger.error("Exception while getting result of Policlinic main query", e);
            logger.info("Processing policlinic sluch skipped");
            return;
        }
        List<XMLSluch> sluchList = groupSluchByActionId(itemList);
        logger.info("Start processing sluchList...");
        for (XMLSluch sluch : sluchList) {
            logger.debug("##### Start of Sluch[{}] #####\n{}", sluch.getId(), sluch.getInfo());
            addSluchToRegistry(
                    registry,
                    account,
                    sluch,
                    infisCode,
                    obsoleteInfisCode,
                    patientOptionalFields,
                    personOptionalFields,
                    sluchOptionalFields,
                    needForSpokesman
            );
            //дополнительные случаи
            //WMIS-22-40
            if (sluch.needAdditionalPoliclinicSluch()) {
                //WMIS-22
                logger.debug("Need Additional policlinic Sluch.");
                processAdditionalPoliclinicSluch(
                        sluch,
                        registry,
                        account,
                        infisCode,
                        sluchOptionalFields
                );
            } else if (sluch.needAdditionalDispanserizationSluch()) {
                //WMIS-40
                logger.debug("Need Additional dispanserization Sluch.");
                processAdditionalDispanserizationSluch(
                        sluch,
                        registry,
                        account,
                        infisCode,
                        sluchOptionalFields
                );
            }
        }
    }

    private static void processAdditionalDispanserizationSluch(final XMLSluch primarySluch,
                                                               final Map<Person, List<Sluch>> registry,
                                                               final Account account,
                                                               final String infisCode,
                                                               final Set<SluchOptionalFields> sluchOptionalFields
    ) {
        final List<AdditionalUploadRow> itemList;
        try {
            itemList = TFOMSServer.getQueryChache().executeNamedQueryWithMap(
                    Constants.QUERY_NAME_DISPANSERIZATION_ADDITIONAL,
                    AdditionalUploadRow.class,
                    primarySluch.getParamMap(),
                    true
            );
            XMLHelper.toLog(itemList);
        } catch (Exception e) {
            logger.error("Exception while getting result of Policlinic Additional query", e);
            logger.info("Processing additional policlinic sluch skipped");
            return;
        }
        List<AdditionalXMLSluch> sluchList = groupSluchForAdditionalDispanserization(itemList, primarySluch);
        //Each row processing
        for (AdditionalXMLSluch sluch : sluchList) {
            logger.debug("##### ADDITIONAL Start of Sluch[{}] #####\n{}", sluch.getId(), sluch.getInfo());
            addSluchToRegistry(
                    registry,
                    account,
                    sluch,
                    infisCode,
                    sluchOptionalFields
            );
            logger.debug(" ADDITIONAL # End of Sluch[{}]", sluch.getId());
        }
    }

    private static void processAdditionalPoliclinicSluch(final XMLSluch primarySluch,
                                                         final Map<Person, List<Sluch>> registry,
                                                         final Account account,
                                                         final String infisCode,
                                                         final Set<SluchOptionalFields> sluchOptionalFields) {
        final List<AdditionalUploadRow> itemList;
        try {
            itemList = TFOMSServer.getQueryChache().executeNamedQueryWithMap(
                    Constants.QUERY_NAME_POLICLINIC_ADDITIONAL,
                    AdditionalUploadRow.class,
                    primarySluch.getParamMap(),
                    true
            );
            XMLHelper.toLog(itemList);
        } catch (Exception e) {
            logger.error("Exception while getting result of Policlinic Additional query", e);
            logger.info("Processing additional policlinic sluch skipped");
            return;
        }
        final List<AdditionalXMLSluch> sluchList = groupSluchForAdditionalPoliclinic(itemList, primarySluch);
        //Each row processing
        for (AdditionalXMLSluch sluch : sluchList) {
            logger.debug("##### ADDITIONAL Start of Sluch[{}] #####\n{}", sluch.getId(), sluch.getInfo());
            addSluchToRegistry(
                    registry,
                    account,
                    sluch,
                    infisCode,
                    sluchOptionalFields
            );
            logger.debug(" ADDITIONAL # End of Sluch[{}]", sluch.getId());
        }
    }


    private static List<AdditionalXMLSluch> groupSluchForAdditionalPoliclinic(final List<AdditionalUploadRow> itemList, XMLSluch primarySluch) {
        final List<AdditionalXMLSluch> sluchList = new ArrayList<AdditionalXMLSluch>(itemList.size());
        logger.debug("Start grouping Sluch by actionId for Additional Policlinic (2P)");
        //Each row processing
        //Grouping sluch by actionId
        final long startTime = System.currentTimeMillis();
        for (AdditionalUploadRow item : itemList) {
            //RSLT = "304"
            if (Constants.ADDITIONAL_SKIP_RESULT.equals(item.getRSLT())) {
                logger.debug("Stop grouping at item[{}] (RSLT={})", item.getId(), Constants.ADDITIONAL_SKIP_RESULT);
                break;
            }
            int currentItemAction = item.getAction();
            boolean isNewSluch = true;
            for (AdditionalXMLSluch currentSluch : sluchList) {
                if (currentItemAction == currentSluch.getAction()) {
                    logger.debug("Item[{}] has same actionId[{}] to Sluch[{}] group them", item.getId(), currentItemAction, currentSluch.getId());
                    currentSluch.addItem(item);
                    isNewSluch = false;
                    break;
                }
            }
            if (isNewSluch) {
                AdditionalXMLSluch sluch = new AdditionalXMLSluch(item, primarySluch);
                sluchList.add(sluch);
            }
            logger.debug("End of Item[{}]", item.getId());
        }
        logger.debug("Grouping complete in ({}) seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
        return sluchList;
    }

    private static List<AdditionalXMLSluch> groupSluchForAdditionalDispanserization(
            final List<AdditionalUploadRow> itemList,
            final XMLSluch primarySluch) {
        final List<AdditionalXMLSluch> sluchList = new ArrayList<AdditionalXMLSluch>(itemList.size());
        logger.debug("Start grouping Sluch by actionId for Additional Dispanserization (HCVZ)");
        //Each row processing
        //Grouping sluch by actionId
        final long startTime = System.currentTimeMillis();
        for (AdditionalUploadRow item : itemList) {
            if (Constants.DISPANSERIZATION_SERVICE_INFIS_PART.contains(item.getRbServiceInfis().substring(2,5)) ) {
                logger.info("SKIP OTHER DISPANSERIZATION SLUCH ON :: {}", item.getInfo());
                break;
            }
            int currentItemAction = item.getAction();
            boolean isNewSluch = true;
            for (AdditionalXMLSluch currentSluch : sluchList) {
                if (currentItemAction == currentSluch.getAction()) {
                    logger.debug("Item[{}] has same actionId[{}] to Sluch[{}] group them", item.getId(), currentItemAction, currentSluch.getId());
                    currentSluch.addItem(item);
                    isNewSluch = false;
                    break;
                }
            }
            if (isNewSluch) {
                AdditionalXMLSluch sluch = new AdditionalXMLSluch(item, primarySluch);
                sluchList.add(sluch);
            }
            logger.debug("End of Item[{}]", item.getId());
        }
        logger.debug("Grouping complete in ({}) seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
        return sluchList;
    }


    private static List<XMLSluch> groupSluchByActionId(List<UploadRow> itemList) {
        final List<XMLSluch> sluchList = new ArrayList<XMLSluch>(itemList.size());
        //Each row processing
        //Grouping sluch by actionId
        logger.debug("Start of grouping Sluch by actionId");
        final long startTime = System.currentTimeMillis();
        for (UploadRow item : itemList) {
            int currentItemAction = item.getAction();
            boolean isNewSluch = true;
            for (XMLSluch currentSluch : sluchList) {
                if (currentItemAction == currentSluch.getAction()) {
                    logger.debug("Item[{}] has same actionId[{}] to Sluch[{}] group them", item.getId(), currentItemAction, currentSluch.getId());
                    currentSluch.addItem(item);
                    isNewSluch = false;
                    break;
                }
            }
            if (isNewSluch) {
                XMLSluch sluch = new XMLSluch(item);
                sluchList.add(sluch);
            }
            logger.debug("End of Item[{}]", item.getId());
        }
        logger.debug("Grouping complete in ({}) seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
        return sluchList;
    }

    public static void processStationarySluch(
            final Map<Person, List<Sluch>> registry,
            final boolean primaryAccount,
            final Account account,
            final String infisCode,
            final String obsoleteInfisCode,
            final Set<PatientOptionalFields> patientOptionalFields,
            final Set<PersonOptionalFields> personOptionalFields,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final boolean needForSpokesman) {
        final List<UploadRow> itemList;
        try {
            itemList = TFOMSServer.getQueryChache().executeNamedQuery(
                    Constants.QUERY_NAME_STATIONARY_MAIN,
                    UploadRow.class,
                    null,
                    true
            );
            XMLHelper.toLog(itemList);
        } catch (Exception e) {
            logger.error("Exception while getting result of Stationary main query", e);
            logger.info("Processing stationary sluch skipped");
            return;
        }
        List<XMLSluch> sluchList = groupSluchByActionId(itemList);
        logger.info("Start processing sluchList...");
        for (XMLSluch sluch : sluchList) {
            logger.debug("##### Start of Sluch[{}] #####\n{}", sluch.getId(), sluch.getInfo());
            addSluchToRegistry(
                    registry,
                    account,
                    sluch,
                    infisCode,
                    obsoleteInfisCode,
                    patientOptionalFields,
                    personOptionalFields,
                    sluchOptionalFields,
                    needForSpokesman
            );
            logger.debug("End of Sluch[{}]", sluch.getId());
        }
    }

    private static void addSluchToRegistry(
            final Map<Person, List<Sluch>> registry,
            final Account account,
            final XMLSluch sluch,
            final String infisCode,
            final String obsoleteInfisCode,
            final Set<PatientOptionalFields> patientOptionalFields,
            final Set<PersonOptionalFields> personOptionalFields,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final boolean needForSpokesman) {
        //Идшник пациента из формируемого случая
        final int sluchPatient = sluch.getPatient();
        for (Map.Entry<Person, List<Sluch>> entry : registry.entrySet()) {
            if (entry.getKey().getPatientId() == sluchPatient) {
                //Patient exists. adding sluch to patient
                logger.debug("Patient[{}] exists in registry. Adding new Sluch to this patient", sluchPatient);
                entry.getValue().addAll(sluch.formSluchStructure(account, sluchOptionalFields, patientOptionalFields, infisCode, obsoleteInfisCode));
                return;
            }
        }
        //Если внутри цикла не нашли пациента - то это новый пациент, формируем еще одну запись в реестре
        logger.debug("This Patient[{}] is new in registry.", sluchPatient);
        final ArrayList<Sluch> sluchList = new ArrayList<Sluch>(1);
        sluchList.addAll(sluch.formSluchStructure(account, sluchOptionalFields, patientOptionalFields, infisCode, obsoleteInfisCode));
        registry.put(sluch.formPersonStructure(personOptionalFields, needForSpokesman), sluchList);
    }

    private static void addSluchToRegistry(
            final Map<Person, List<Sluch>> registry,
            final Account account,
            final AdditionalXMLSluch sluch,
            final String infisCode,
            final Set<SluchOptionalFields> sluchOptionalFields
    ) {
        //Идшник пациента из формируемого случая
        final int sluchPatient = sluch.getPatient();
        for (Map.Entry<Person, List<Sluch>> entry : registry.entrySet()) {
            if (entry.getKey().getPatientId() == sluchPatient) {
                //Patient exists. adding sluch to patient
                logger.debug("Patient[{}] exists in registry. Adding new Sluch to this patient", sluchPatient);
                entry.getValue().addAll(sluch.formSluchStructure(account, sluchOptionalFields, infisCode));
                return;
            }
        }
        //Если внутри цикла не нашли пациента - то это ошибка т.к. пациент болжен быть в соновном случае
        logger.error("This Patient[{}] must EXISTS IN REGISTRY.", sluchPatient);
        //TODO Exception
    }


    public static String getOrgStructureListAsString(final List<Integer> orgStructureIdList) {
        final StringBuilder orgStructureStringBuilder = new StringBuilder("\'");
        final Iterator<Integer> iterator = orgStructureIdList.iterator();
        while (iterator.hasNext()) {
            orgStructureStringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                orgStructureStringBuilder.append(',');
            }
        }
        orgStructureStringBuilder.append('\'');
        return orgStructureStringBuilder.toString();
    }


    public static Contract checkContract(final int contractId, final int requestNum) throws TException {
        try {
            final Contract contract = TFOMSServer.getContractBean().getContractById(contractId);
            logger.debug("By id[{}] founded Contract[{}]", contractId, contract.getNumber());
            return contract;
        } catch (CoreException e) {
            logger.error("End of #{}. No such Contract[{}]", requestNum, contractId);
            throw TFOMSErrors.CONTRACT_NOT_EXISTS.getException();
        }
    }

    public static Organisation checkOrganisation(final String organisationInfis, final int requestNum) throws TException {
        try {
            final Organisation selectedOrganisation = TFOMSServer.getOrganisationBean().getOrganizationByInfisCode(organisationInfis);
            logger.debug("By infis[{}] founded organisation[{}]", organisationInfis, selectedOrganisation.getId());
            return selectedOrganisation;
        } catch (CoreException e) {
            logger.error("End of #{}. No such Organisation exists", requestNum);
            throw TFOMSErrors.ORGANISATION_NOT_EXISTS.getException();
        }
    }

    /**
     * Проверка правильности заданного диапозона дат
     *
     * @param beginInterval дата начала интервала
     * @param endInterval   дата окончания интервала
     * @param requestNum    номер запроса
     * @throws TException исключение, если интервал задан неверно
     */
    public static void checkDates(Date beginInterval, Date endInterval, int requestNum) throws TException {
        if (beginInterval.after(endInterval)) {
            logger.error("End of #{}. Invalid period (endDate[{}] is smaller then beginDate[{}])",
                    requestNum, endInterval.getTime(), beginInterval.getTime());
            throw TFOMSErrors.INVALID_DATES.getException();
        } else if (logger.isDebugEnabled()) {
            logger.debug("Interval from [{}] to [{}]", beginInterval.toString(), endInterval.toString());
        }
    }

    /**
     * Формирование имени файла реестра пациентов
     *
     * @param obsoleteInfisCode устаревший инфис-код
     * @param smoNumber         номер СМО
     * @param date              дата формирования
     * @param packetNumber      номер пакета
     * @return имя файла реестра пациентов
     */
    public static String getPatientFileName(
            final String obsoleteInfisCode,
            final String smoNumber,
            final Date date,
            final int packetNumber) {
        //HPiNiPpNp_YYMMN.XML, где:
        //        H – константа, обозначающая передаваемые данные. Принимает следующие значения:
        //        Н – для файла со сведениями об оказанной медицинской помощи (реестр счетов);
        //        L – для файла со сведениями о персональных данных (реестр пациентов).
        return "L".concat(getFileName(obsoleteInfisCode, smoNumber, date, packetNumber));
    }

    /**
     * Формирование имени файла реестра услуг
     *
     * @param obsoleteInfisCode устаревший инфис-код
     * @param smoNumber         номер СМО
     * @param date              дата формирования
     * @param packetNumber      номер пакета
     * @return имя файла реестра услуг
     */
    public static String getServiceFileName(
            final String obsoleteInfisCode,
            final String smoNumber,
            final Date date,
            final int packetNumber) {
        //HPiNiPpNp_YYMMN.XML, где:
        //        H – константа, обозначающая передаваемые данные. Принимает следующие значения:
        //        Н – для файла со сведениями об оказанной медицинской помощи (реестр счетов);
        //        L – для файла со сведениями о персональных данных (реестр пациентов).
        return "H".concat(getFileName(obsoleteInfisCode, smoNumber, date, packetNumber));
    }

    /**
     * Формирование имени файла
     *
     * @param obsoleteInfisCode устаревший инфис-код
     * @param smoNumber         номер СМО
     * @param date              дата формирования
     * @param packetNumber      номер пакета
     * @return часть имени файла
     */
    private static String getFileName(
            final String obsoleteInfisCode,
            final String smoNumber,
            final Date date,
            final int packetNumber) {
        //Pi – Параметр, определяющий организацию-источник (нам нужно указывать M):
        //T – ТФОМС;
        //S – СМО;
        //M – МО.
        return new StringBuilder("M") //M
                //Ni – Номер источника (двузначный код ТФОМС, или двухзначный реестровый номер СМО, или трехзначный код МО).
                //Нужно указывать первые 3 цифры кода ЛПУ.
                .append(obsoleteInfisCode.substring(0, 3))
                        //Pp – Параметр, определяющий организацию-получателя (нам нужно указывать T):
                        //T – ТФОМС;
                        //S – СМО;
                        //M – МО.
                .append('T') //T
                        //Np – Номер получателя (двузначный код ТФОМС, или двухзначный реестровый номер СМО, или трехзначный код МО).
                        // Например, для Пензы нужно указывать «58», т.е. номер СМО.
                .append(smoNumber)
                        //разделитель
                .append('_') //'_'
                        //YY – две последние цифры порядкового номера года отчетного периода.
                        //MM – порядковый номер месяца отчетного периода
                .append(new SimpleDateFormat("yyMM").format(date))
                .append(String.format("%02d", packetNumber)).toString();
    }

    /**
     * Формируется номер счета следующего формата ГГММ-N/ Ni,
     * Где N = номер пакета
     * Ni = первые 3 цифры кода ЛПУ.
     *
     * @param formDate          дата создания счета (ГГММ)
     * @param packetNumber      номер пакета (N)
     * @param obsoleteInfisCode устаревший инфис ЛПУ (Ni)
     * @return строка с номером счета
     */
    public static String getAccountNumber(
            final Date formDate,
            final int packetNumber,
            final String obsoleteInfisCode) {
        return String.format("%s-%02d/%s", new SimpleDateFormat("yyMM").format(formDate), packetNumber, obsoleteInfisCode.substring(0, 3));
    }

    public static void checkPrimary(final boolean isPrimary, final int packetNumber)
            throws TException {
        if (isPrimary && packetNumber > 1) {
            logger.warn("This account may not be primary");
            throw TFOMSErrors.ACCOUNT_NOT_PRIMARY.getException();
        } else if (!isPrimary && packetNumber == 1) {
            logger.warn("This is primary Account, but requested as secondary");
            throw TFOMSErrors.ACCOUNT_NOT_SECONDARY.getException();
        }
    }

    public static void checkOrgStructureList(List<Integer> orgStructureIdList) throws TException {
        if (orgStructureIdList.isEmpty()) {
            logger.info("OrgStructure list is empty = > From all LPU.");
        } else {
            logger.info("OrgStructureIdList = {}", orgStructureIdList);
            boolean atleastOneOrgStructureIsCorrect = false;
            for (int currentOrgStructureId : orgStructureIdList) {
                try {
                    logger.debug("Search in OrgStructure[{}] : {}",
                            currentOrgStructureId, TFOMSServer.getOrgStructureBean().getOrgStructureById(currentOrgStructureId).getName());
                    atleastOneOrgStructureIsCorrect = true;
                } catch (CoreException e) {
                    logger.error("OrgStructure[{}] not exists", currentOrgStructureId);
                }
            }
            if (!atleastOneOrgStructureIsCorrect) {
                throw TFOMSErrors.ORGSTRUCTURE_ID_LIST_INCORRECT.getException();
            }
        }
    }

    /**
     * Вычисление значения тега PR_NOV
     *
     * @param packetNumber номер пакета
     * @return значение тега PR_NOV
     */
    public static short getPRNOV(final int packetNumber) {
        switch (packetNumber) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            default: {
                return 2;
            }
        }
    }

    public static Map<String, String> getStartedParamMap(
            final Contract contract,
            final Date begDate,
            final Date endDate,
            final Organisation organisation,
            final List<Integer> orgStructureIdList,
            final String obsoleteInfisCode,
            final String levelMO,
            final String smoNumber) {
        //Окргуление до начала дня (HH:mm:ss 00:00:00)
        final LocalDate beginInterval = new LocalDate(begDate);
        //Окргуление до конца дня (HH:mm:ss 23:59:59)
        final LocalDateTime endInterval = new LocalDateTime(endDate)
                .withField(DateTimeFieldType.hourOfDay(), 23)
                .withField(DateTimeFieldType.minuteOfHour(), 59)
                .withField(DateTimeFieldType.secondOfMinute(), 59);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
        final Map<String, String> result = new HashMap<String, String>();
        result.put(Constants.PARAM_NAME_CONTRACT_ID, String.valueOf(contract.getId()));
        result.put(Constants.PARAM_NAME_BEGIN_DATE, simpleDateFormat.format(beginInterval.toDate()));
        result.put(Constants.PARAM_NAME_ORGANISATION_ID, String.valueOf(organisation.getId()));
        result.put(Constants.PARAM_NAME_END_DATE, simpleDateFormat.format(endInterval.toDate()));
        result.put(Constants.PARAM_NAME_ORG_STRUCTURE_ID_LIST, XMLHelper.getOrgStructureListAsString(orgStructureIdList));
        result.put(Constants.PARAM_NAME_OBSOLETE_INFIS_CODE, '\'' + obsoleteInfisCode + '\'');
        result.put(Constants.PARAM_NAME_LEVEL_MO, '\'' + levelMO + '\'');
        result.put(Constants.PARAM_NAME_SMO_AREA, '\'' + smoNumber + '\'');
        return result;
    }

    public static boolean isSpokesmanNeeded(final Set<PersonOptionalFields> personOptionalFields) {
        for (PersonOptionalFields current : personOptionalFields) {
            switch (current) {
                case FAM_P:
                case IM_P:
                case OT_P:
                case W_P:
                case DR_P: {
                    logger.debug("Spokesman is needed!");
                    return true;
                }
            }
        }
        logger.debug("Spokesman is NOT needed!");
        return false;
    }

    /**
     * Вывод в лог информации о сущности
     *
     * @param item сущность
     */
    public static <T extends Informationable> void toLog(final T item) {
        if (logger.isDebugEnabled()) {
            if (item != null) {
                logger.debug(item.getInfo());
            } else {
                logger.warn("Where is my object, Johnny? Are you kidding me?");
            }
        }
    }

    /**
     * Вывод в лог информации о списке сущностей
     *
     * @param itemList сущности
     */
    public static <T extends Informationable> void toLog(final List<T> itemList) {
        if (logger.isDebugEnabled()) {
            logger.debug("ResultList.size()={}. DATA:", itemList.size());
            for (Informationable item : itemList) {
                toLog(item);
            }
        }
    }

    public static void preSelectParameters(final Map<String, String> resultParamMap) {
        logger.debug("Start of preSelecting some parameters");
        preSelectIntegerParameter(Constants.QUERY_NAME_MULTIPLE_BIRTH_CONTACT_TYPE, Constants.PARAM_NAME_MULTIPLE_BIRTH_CONTACT_TYPE, resultParamMap, null);

        preSelectIntegerParameter(Constants.QUERY_NAME_MOVING_ACTION_TYPE, Constants.PARAM_NAME_MOVING_ACTION_TYPE, resultParamMap, null);
        final Map<String, String> queryParameters = new HashMap<String, String>(1);
        queryParameters.put(Constants.PARAM_NAME_MOVING_ACTION_TYPE, resultParamMap.get(Constants.PARAM_NAME_MOVING_ACTION_TYPE));

        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_MAIN_DIAGNOSIS, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_MAIN_DIAGNOSIS, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_SECONDARY_DIAGNOSIS, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_SECONDARY_DIAGNOSIS, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_ISHOD, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_ISHOD, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_RESULT, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_RESULT, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_CSG, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_CSG, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_STAGE, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_STAGE, resultParamMap, queryParameters);
        preSelectIntegerParameter(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_HOSPITAL_BED_PROFILE, Constants.PARAM_NAME_STATIONARY_PROPERTY_TYPE_HOSPITAL_BED_PROFILE, resultParamMap, queryParameters);
        logger.debug("End of preSelecting some parameters");
    }

    private static boolean preSelectIntegerParameter(
            final String queryName,
            final String paramName,
            final Map<String, String> resultParamMap,
            final Map<String, String> queryParamMap) {
        final Integer result = TFOMSServer.getQueryChache().executeNamedQueryForIntegerResult(queryName, queryParamMap);
        if (result == null) {
            return false;
        }
        resultParamMap.put(paramName, String.valueOf(result));
        logger.debug("PreSelect: {} = {}", paramName, result);
        return true;
    }
}
