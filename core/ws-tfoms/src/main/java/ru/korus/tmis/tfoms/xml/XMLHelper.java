package ru.korus.tmis.tfoms.xml;

import com.google.common.collect.ImmutableMap;
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
     * Проверка на необходимость выгрузки этого случая (не был ли этот случай уже выставлен в каком-либо счете)
     *
     * @param primaryAccount флаг типа выгрузки (первичная \ повторная)
     * @param actionId       идентификатор действия с которым связана услуга
     * @return true - выгрузка еще не проводилась, false - случай уже был выставлен в одном из счетов
     */
    private static boolean checkItemIsAlreadyInAccountItems(final boolean primaryAccount, final int actionId) {
        final ImmutableMap<String, String> variables = ImmutableMap.of(Constants.ACTION_ID_PARAM, String.valueOf(actionId));
        try {
            final String queryName = primaryAccount ? Constants.ACCOUNTITEM_CHECK_PRIMARY : Constants.ACCOUNTITEM_CHECK_ADDITIONAL;
            final String query = TFOMSServer.getQueryChache().getQueryWithSettedParams(queryName, variables);
            final List resultList = TFOMSServer.getSpecialPreferencesBean().executeNamedQuery(query);
            if (((Number) resultList.get(0)).intValue() > 0) {
                logger.debug("Action[{}] is in AccountItem.", actionId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception while checking AccountItem table for Action[" + actionId + "].", e);
            return false;
        }
        return true;
    }

    /**
     * Выгрузка всех поликлинических услуг
     *  @param registry              реестр куда попадет результат
     *
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
        final String mainPoliclinicQuery = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                Constants.POLICLINIC_SELECT_STATEMENT,
                null
        );
        //Skip processing
        if (mainPoliclinicQuery.isEmpty()) {
            logger.error("Main Policlinic query with name = \'{}\' not exists.", Constants.POLICLINIC_SELECT_STATEMENT);
            return;
        }
        logger.info("Main Policlinic query:\n{}", mainPoliclinicQuery);
        long startTime = System.currentTimeMillis();
        List<UploadItem> itemList;
        try {
            itemList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForUploadItem(mainPoliclinicQuery);
            logger.info("Query executionTime is {} seconds. Returned {} rows.", (float) (System.currentTimeMillis() - startTime) / 1000, itemList.size());
            for(Object item : itemList){
                logger.debug(item.toString());
            }
        } catch (Exception e) {
            logger.error("Exception while executing main policlinic query. Skip processing policlinic rows", e);
            return;
        }
        //Each row processing
        for (UploadItem item : itemList) {
            logger.debug("##### Start of Item[{}] #####\n{}", item.getId(), item.getInfo());
            if (checkItemIsAlreadyInAccountItems(primaryAccount, item.getAction())) {
                XMLSluch sluch = new XMLSluch(item);
                if (!sluch.fillPatientProperties(Constants.PATIENT_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because patientProperties is not found.", item.getId());
                    //Нету пола и возраста (даты рождения)..... пропускаем такой случай
                    continue;
                }
                if (!sluch.fillPatientPolicy(Constants.PATIENT_POLICY)) {
                    logger.debug("Item[{}] policy is not found.", item.getId());
                }
                if (!sluch.fillPatientDocument(Constants.PATIENT_DOCUMENT)) {
                    logger.debug("Item[{}] document is not found.", item.getId());
                }
                if (!sluch.fillUploadItemProperties(Constants.POLICLINIC_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadItemProperties is not found.", item.getId());
                    //Нет свойств случая (DS012, RSLT, ISHOD) пропускаем этот случай
                    continue;
                }
                if (!sluch.fillSluchProperties(Constants.SLUCH_PROPERTIES)) {
                    logger.debug("Item[{}] sluchProperties is not found.", item.getId());
                }
                if (!sluch.fillUploadUslProperties(Constants.POLICLINIC_USL_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadUslProperties is not found.", item.getId());
                    //Нет единицы учета и прочего
                    continue;
                }
                //TODO TESTING
                logger.debug(sluch.getParameters().toString());
                if (!sluch.fillUsl(Constants.POLICLINIC_TARIFF, false)) {
                    logger.debug("Item[{}] processing skipped because no one USL is founded (Contract_Tariff, rbServiceFinance, MedicalKindUnit)", item.getId());
                    //Не найдено подходящего тарифа, пропускаем
                    continue;
                }
                //Проверка не является ли этот случаем исключением из выгрузки  (true - исключение и его нужно пропустить)
                //WMIS-22-40
                if (sluch.isException(Constants.POLICLINIC_EXCEPTION)) {
                    continue;
                }
                addSluchToRegistry(
                        registry,
                        account,
                        sluch,
                        infisCode,
                        obsoleteInfisCode,
                        true,
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
                            primaryAccount,
                            account,
                            infisCode,
                            obsoleteInfisCode,
                            patientOptionalFields,
                            personOptionalFields,
                            sluchOptionalFields,
                            needForSpokesman
                    );
                } else if (sluch.needAdditionalDispanserizationSluch()) {
                    //WMIS-40
                    logger.debug("Need Additional dispanserization Sluch.");
                    processAdditionalDispanserizationSluch(
                            sluch,
                            registry,
                            primaryAccount,
                            account,
                            infisCode,
                            obsoleteInfisCode,
                            patientOptionalFields,
                            personOptionalFields,
                            sluchOptionalFields,
                            needForSpokesman
                    );
                }
            }
            logger.debug("End of Item[{}]", item.getId());
        }
    }

    private static void processAdditionalDispanserizationSluch(final XMLSluch primarySluch,
                                                               final Map<Person, List<Sluch>> registry,
                                                               final boolean primaryAccount,
                                                               final Account account,
                                                               final String infisCode,
                                                               final String obsoleteInfisCode,
                                                               final Set<PatientOptionalFields> patientOptionalFields,
                                                               final Set<PersonOptionalFields> personOptionalFields,
                                                               final Set<SluchOptionalFields> sluchOptionalFields,
                                                               final boolean needForSpokesman) {
        final String additionalPoliclinicQuery = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                Constants.ADDITIONAL_DISPANSERIZATION_SELECT_STATEMENT,
                primarySluch.getParameters()
        );
        //Skip processing
        if (additionalPoliclinicQuery.isEmpty()) {
            logger.error("Additional query with name = \'{}\' not exists.", Constants.ADDITIONAL_DISPANSERIZATION_SELECT_STATEMENT);
            return;
        }
        logger.info("Additional dispanserization query:\n{}", additionalPoliclinicQuery);
        long startTime = System.currentTimeMillis();
        List<UploadItem> itemList;
        try {
            itemList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForUploadItem(additionalPoliclinicQuery);
        } catch (Exception e) {
            logger.error("Exception while executing additional dispanserization query. Skip processing rows", e);
            return;
        }
        logger.info("Query executionTime is {} seconds. Returned {} rows.", (float) (System.currentTimeMillis() - startTime) / 1000, itemList.size());
        toLog(itemList);
        //Each row processing
        for (UploadItem item : itemList) {
            logger.debug("##### ADDITIONAL # Start of Item[{}] #####\n{}", item.getId(), item.getInfo());
                XMLSluch sluch = new XMLSluch(item, primarySluch);
                sluch.setPatientProperties(primarySluch.getPatientProperties());
                if (!sluch.fillPatientPolicy(Constants.PATIENT_POLICY)) {
                    logger.debug("Item[{}] policy is not found.", item.getId());
                }
                if (!sluch.fillPatientDocument(Constants.PATIENT_DOCUMENT)) {
                    logger.debug("Item[{}] document is not found.", item.getId());
                }
                if (!sluch.fillUploadItemProperties(Constants.POLICLINIC_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadItemProperties is not found.", item.getId());
                    continue;
                }
                if (!sluch.fillSluchProperties(Constants.SLUCH_PROPERTIES)) {
                    logger.debug("Item[{}] sluchProperties is not found.", item.getId());
                }
                //TODO TESTING
                logger.debug(sluch.getParameters().toString());
                if (!sluch.fillUploadUslProperties(Constants.POLICLINIC_USL_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadUslProperties is not found.", item.getId());
                    continue;
                }
                if (Constants.DISPANSERIZATION_IDSP_LIST.contains(sluch.getUslProperties().getIDSP())
                        && (
                        Constants.DISPANSERIZATION_SERVICE_INFIS_PART.contains(sluch.getItem().getServiceInfis().substring(2, 5))
                                && !"P".equals(sluch.getItem().getMedicalKindCode())
                ) || (
                        "2".equals(sluch.getItemProperties().getRegionalCode())
                                && "P".equals(sluch.getItem().getMedicalKindCode())
                )
                        ) {
                    logger.info("SKIP OTHER DISPANSERIZATION SLUCH");
                    break;
                } else {
                    String uslQueryName = Constants.POLICLINIC_TARIFF;
                    if (primarySluch.isChildDispanserizationUnder3Years()) {
                        logger.debug("it is sluch with dispanserization under 3 years old patient");
                        uslQueryName = Constants.ADDITIONAL_DISPANSERIZATION_TARIFF;
                    }
                    if (!sluch.fillUsl(uslQueryName, false)) {
                        logger.debug("Item[{}] processing skipped because no one USL is founded (Contract_Tariff, rbServiceFinance, MedicalKindUnit)", item.getId());
                        continue;
                    }
                    addSluchToRegistry(
                            registry,
                            account,
                            sluch,
                            infisCode,
                            obsoleteInfisCode,
                            true,
                            patientOptionalFields,
                            personOptionalFields,
                            sluchOptionalFields,
                            needForSpokesman
                    );
                }
            logger.debug(" ADDITIONAL # End of Item[{}]", item.getId());
        }
    }

    private static void processAdditionalPoliclinicSluch(final XMLSluch primarySluch,
                                                         final Map<Person, List<Sluch>> registry,
                                                         final boolean primaryAccount,
                                                         final Account account,
                                                         final String infisCode,
                                                         final String obsoleteInfisCode,
                                                         final Set<PatientOptionalFields> patientOptionalFields,
                                                         final Set<PersonOptionalFields> personOptionalFields,
                                                         final Set<SluchOptionalFields> sluchOptionalFields,
                                                         final boolean needForSpokesman) {
        final String additionalPoliclinicQuery = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                Constants.ADDITIONAL_POLICLINIC_SELECT_STATEMENT,
                primarySluch.getParameters()
        );
        //Skip processing
        if (additionalPoliclinicQuery.isEmpty()) {
            logger.error("Additional query with name = \'{}\' not exists.", Constants.ADDITIONAL_POLICLINIC_SELECT_STATEMENT);
            return;
        }
        logger.info("Additional policlinic query:\n{}", additionalPoliclinicQuery);
        long startTime = System.currentTimeMillis();
        List<UploadItem> itemList;
        try {
            itemList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForUploadItem(additionalPoliclinicQuery);
        } catch (Exception e) {
            logger.error("Exception while executing additional policlinic query. Skip processing rows", e);
            return;
        }
        logger.info("Query executionTime is {} seconds. Returned {} rows.", (float) (System.currentTimeMillis() - startTime) / 1000, itemList.size());
        toLog(itemList);
        //Each row processing
        for (UploadItem item : itemList) {
            logger.debug("##### ADDITIONAL # Start of Item[{}] #####\n{}", item.getId(), item.getInfo());

                XMLSluch sluch = new XMLSluch(item, primarySluch);
                if (logger.isDebugEnabled()) {
                    logger.debug("##### ADDITIONAL # {}", item.getInfo());
                }
                sluch.setPatientProperties(primarySluch.getPatientProperties());
                if (!sluch.fillPatientPolicy(Constants.PATIENT_POLICY)) {
                    logger.debug("Item[{}] policy is not found.", item.getId());
                }
                if (!sluch.fillPatientDocument(Constants.PATIENT_DOCUMENT)) {
                    logger.debug("Item[{}] document is not found.", item.getId());
                }
                if (!sluch.fillUploadItemProperties(Constants.POLICLINIC_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadItemProperties is not found.", item.getId());
                    continue;
                }
                if ("304".equals(sluch.getItemProperties().getRSLT())) {
                    if (!sluch.fillSluchProperties(Constants.SLUCH_PROPERTIES)) {
                        logger.debug("Item[{}] sluchProperties is not found.", item.getId());
                    }
                    if (!sluch.fillUploadUslProperties(Constants.POLICLINIC_USL_PROPERTIES)) {
                        logger.debug("Item[{}] processing skipped because UploadUslProperties is not found.", item.getId());
                        continue;
                    }
                    //TODO TESTING
                    logger.debug(sluch.getParameters().toString());
                    if (!sluch.fillUsl(Constants.POLICLINIC_TARIFF, true)) {
                        logger.debug("Item[{}] processing skipped because no one USL is founded (Contract_Tariff, rbServiceFinance, MedicalKindUnit)", item.getId());
                        continue;
                    }
                    addSluchToRegistry(
                            registry,
                            account,
                            sluch,
                            infisCode,
                            obsoleteInfisCode,
                            true,
                            patientOptionalFields,
                            personOptionalFields,
                            sluchOptionalFields,
                            needForSpokesman
                    );
                } else {
                    logger.info("SKIP SLUCH WITH RSLT<>304");
                    break;
                }
            logger.debug(" ADDITIONAL # End of Item[{}]", item.getId());
        }
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
        final String mainStationaryQuery = TFOMSServer.getQueryChache().getQueryWithSettedParams(
                Constants.STATIONARY_SELECT_STATEMENT,
                null
        );
        //Skip processing
        if (mainStationaryQuery.isEmpty()) {
            logger.error("Main Stationary query with name = \'{}\' not exists.", Constants.STATIONARY_SELECT_STATEMENT);
            return;
        }
        logger.info("Main Stationary query:\n{}", mainStationaryQuery);
        long startTime = System.currentTimeMillis();
        List<UploadItem> itemList;
        try {
            itemList = TFOMSServer.getSpecialPreferencesBean().executeNamedQueryForUploadItem(mainStationaryQuery);
        } catch (Exception e) {
            logger.error("Exception while executing main stationary query. Skip processing stationary rows", e);
            return;
        }
        logger.info("Query executionTime is {} seconds. Returned {} rows.", (float) (System.currentTimeMillis() - startTime) / 1000, itemList.size());
        toLog(itemList);

        //Each row processing
        for (UploadItem item : itemList) {
            logger.debug("##### Start of Item[{}] #####\n{}", item.getId(), item.getInfo());
            if (checkItemIsAlreadyInAccountItems(primaryAccount, item.getAction())) {
                XMLSluch sluch = new XMLSluch(item);
                if (!sluch.fillPatientProperties(Constants.PATIENT_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because patientProperties is not found.", item.getId());
                    //Нету пола и возраста (даты рождения)..... пропускаем такой случай
                    continue;
                }
                if (!sluch.fillPatientPolicy(Constants.PATIENT_POLICY)) {
                    logger.debug("Item[{}] policy is not found.", item.getId());
                }
                if (!sluch.fillPatientDocument(Constants.PATIENT_DOCUMENT)) {
                    logger.debug("Item[{}] document is not found.", item.getId());
                }
                if (!sluch.fillUploadItemProperties(Constants.STATIONARY_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadItemProperties is not found.", item.getId());
                    continue;
                }
                if (!sluch.fillSluchProperties(Constants.SLUCH_PROPERTIES)) {
                    logger.debug("Item[{}] sluchProperties is not found.", item.getId());
                }
                if (!sluch.fillUploadUslProperties(Constants.STATIONARY_USL_PROPERTIES)) {
                    logger.debug("Item[{}] processing skipped because UploadUslProperties is not found.", item.getId());
                    continue;
                }
                if (!sluch.fillUsl(Constants.STATIONARY_TARIFF, false)) {
                    logger.debug("Item[{}] processing skipped because no one USL is founded (Contract_Tariff, rbServiceFinance, MedicalKindUnit)", item.getId());
                    continue;
                }
                //Проверка не является ли этот случаем исключением из выгрузки  (true - исключение и его нужно пропустить)
                if (sluch.isException(Constants.STATIONARY_EXCEPTION)) {
                    continue;
                }
                //Дополнительных запросов проводить не требуется
                addSluchToRegistry(
                        registry,
                        account,
                        sluch,
                        infisCode,
                        obsoleteInfisCode,
                        false,
                        patientOptionalFields,
                        personOptionalFields,
                        sluchOptionalFields,
                        needForSpokesman
                );
            }
            logger.debug("End of Item[{}]", item.getId());
        }
    }

    /**
     * Добавление случая в реестр
     *
     * @param registry              реестр куда попадет результат
     * @param sluch                 случай для добавления
     */
    private static void addSluchToRegistry(
            final Map<Person, List<Sluch>> registry,
            final Account account,
            final XMLSluch sluch,
            final String infisCode,
            final String obsoleteInfisCode,
            final boolean isPoliclinic,
            final Set<PatientOptionalFields> patientOptionalFields,
            final Set<PersonOptionalFields> personOptionalFields,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final boolean needForSpokesman) {
        //Идшник пациента из формируемого случая
        final int sluchPatient = sluch.getItem().getPatient();
        for (Map.Entry<Person, List<Sluch>> entry : registry.entrySet()) {
            if (entry.getKey().getPatientId() == sluchPatient) {
                //Patient exists. adding sluch to patient
                logger.debug("Patient[{}] exists in registry. Adding new Sluch to this patient", sluchPatient);
                entry.getValue().addAll(sluch.formSluchStructure(account, sluchOptionalFields, patientOptionalFields, infisCode, obsoleteInfisCode, isPoliclinic));
                return;
            }
        }
        //Если внутри цикла не нашли пациента - то это новый пациент, формируем еще одну запись в реестре
        logger.debug("This Patient[{}] is new in registry.", sluchPatient);
        final ArrayList<Sluch> sluchList = new ArrayList<Sluch>(1);
        sluchList.addAll(sluch.formSluchStructure(account, sluchOptionalFields, patientOptionalFields, infisCode, obsoleteInfisCode, isPoliclinic));
        registry.put(sluch.formPersonStructure(personOptionalFields, needForSpokesman), sluchList);
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
            final String levelMO
    ) {
        //Окргуление до начала дня (HH:mm:ss 00:00:00)
        LocalDate beginInterval = new LocalDate(begDate);
        //Окргуление до конца дня (HH:mm:ss 23:59:59)
        LocalDateTime endInterval = new LocalDateTime(endDate)
                .withField(DateTimeFieldType.hourOfDay(), 23)
                .withField(DateTimeFieldType.minuteOfHour(), 59)
                .withField(DateTimeFieldType.secondOfMinute(), 59);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
        final Map<String, String> result = new HashMap<String, String>();
        result.put(Constants.CONTRACT_ID_PARAM, String.valueOf(contract.getId()));
        result.put(Constants.BEGIN_DATE_PARAM, simpleDateFormat.format(beginInterval.toDate()));
        result.put(Constants.ORGANISATION_ID_PARAM, String.valueOf(organisation.getId()));
        result.put(Constants.END_DATE_PARAM, simpleDateFormat.format(endInterval.toDate()));
        result.put(Constants.ORG_STRUCTURE_ID_LIST_PARAM, XMLHelper.getOrgStructureListAsString(orgStructureIdList));
        result.put(Constants.OBSOLETE_INFIS_CODE_PARAM, obsoleteInfisCode);
        result.put(Constants.LEVEL_MO_PARAM, '\''+levelMO+'\'');
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
}
