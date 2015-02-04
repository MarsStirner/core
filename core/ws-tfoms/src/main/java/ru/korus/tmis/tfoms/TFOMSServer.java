package ru.korus.tmis.tfoms;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.joda.time.DateMidnight;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbContractBeanLocal;
import ru.korus.tmis.core.database.common.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.database.common.DbPatientBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.NoSuchPatientException;
import ru.korus.tmis.core.exception.NoSuchRbPolicyTypeException;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.tfoms.thriftgen.*;
import ru.korus.tmis.tfoms.thriftgen.Account;
import ru.korus.tmis.tfoms.thriftgen.AccountItem;
import ru.korus.tmis.tfoms.thriftgen.OrgStructure;
import ru.korus.tmis.tfoms.xml.*;


import java.util.*;

/**
 * User: EUpatov<br>
 * Date: 24.05.13 at 14:06<br>
 * Company Korus Consulting IT<br>
 */
public class TFOMSServer implements TFOMSService.Iface {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(TFOMSServer.class);
    //Beans
    private static DbRbSpecialVariablesPreferencesBeanLocal specialPreferencesBean = null;
    private static SpecialVariablesPreferencesQueryCache queryChache = null;

    private static DbClientPolicyBeanLocal clientPolicyBean = null;
    private static DbPatientBeanLocal dbPatientBean = null;
    private static DbRbPolicyTypeBeanLocal policyTypeBean = null;
    private static DbOrganizationBeanLocal organisationBean = null;
    private static DbAccountBeanLocal accountBean = null;
    private static DbRbPayRefuseTypeBeanLocal refuseTypeBean = null;
    private static DbAccountItemBeanLocal accountItemBean = null;
    private static DbOrgStructureBeanLocal orgStructureBean = null;
    private static DbContractBeanLocal contractBean = null;
    //Singleton instance
    private static TFOMSServer instance;
    //Server
    private static TServer server;
    //Transport
    private static TServerSocket serverTransport;
    //Number of request
    private static int requestNum = 0;

    //THREAD properties
    //Listener thread
    private Thread requestListener = null;
    //Listener port [DEFAULT = 7912]
    private static final int PORT_NUMBER = ConfigManager.TfomsUpload().Port();
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.MAX_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;
    //MAXimum work threads in thread pool
    private static final int MAX_WORKER_THREADS = ConfigManager.TfomsUpload().MaxThreads();
    private static final int MIN_WORKER_THREADS = 1;


    public TFOMSServer() {
        logger.info("Starting TFOMSServer initialize.");
        requestListener = new Thread(new Runnable() {
            @Override
            public void run() {

                //Thread
                try {
                    serverTransport = new TServerSocket(PORT_NUMBER);
                    TFOMSService.Processor<TFOMSServer> processor = new TFOMSService.Processor<TFOMSServer>(getInstance());
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .protocolFactory(new TBinaryProtocol.Factory())
                            .processor(processor)
                            .minWorkerThreads(MIN_WORKER_THREADS)
                            .maxWorkerThreads(MAX_WORKER_THREADS));
                    logger.info("Starting server on port {}", PORT_NUMBER);
                    server.serve();
                } catch (Exception e) {
                    logger.error("Failed to start server on port {}. Exception={}", PORT_NUMBER, e.getMessage());
                    if (logger.isDebugEnabled()) e.printStackTrace();
                }
            }
        });
        requestListener.setName("Thrift-Service-Thread");
        requestListener.setPriority(SERVER_THREAD_PRIORITY);
        requestListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }

    /**
     * Получение экземпляра (синглтон)
     *
     * @return экземпляр
     */
    public static TFOMSServer getInstance() {
        if (instance == null) {
            instance = new TFOMSServer();
        }
        return instance;
    }


    /**
     * Запуск сервера (в отдельном потоке)
     */
    public void startService() {
        requestListener.start();
    }

    /**
     * Корректное завершение работы
     */
    public void endWork() {
        logger.warn("TFOMSServer start closing");
        logger.info("Total request served={}", requestNum);
        server.stop();
        logger.warn("Server stopped.");
        serverTransport.close();
        logger.warn("Transport closed.");
        requestListener.interrupt();
        if (requestListener.isInterrupted()) {
            logger.warn("ServerThread is interrupted successfully");
        }
        if (requestListener.isAlive()) {
            logger.error("ServerThread is ALIVE?!?!?!");
            requestListener.setPriority(Thread.MIN_PRIORITY);
            requestListener.interrupt();
        }
        logger.info("All fully stopped.");
    }

    //-------------------------------------------------------------------------------------------------
    //Accounts
    //-------------------------------------------------------------------------------------------------

    /**
     * Получение всех неудаленных счетов
     *
     * @return Список счетов, отсортированный по убыванию даты выставления
     * @throws TException
     */
    @Override
    public List<Account> getAvailableAccounts() throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getAvailableAccounts()", currentRequestNum);
        final List<Account> result = new ArrayList<Account>();
        for (ru.korus.tmis.core.entity.model.Account currentDbAccount : accountBean.getUndeletedOnly()) {
            result.add(ThriftStructBuilder.createAccount(currentDbAccount));
        }
        logger.info("End of #{} getAvailableAccounts. Return \"{}\" items in result.", currentRequestNum, result.size());
        if (logger.isDebugEnabled()) {
            for (Account currentAccountToPrint : result) {
                logger.debug(currentAccountToPrint.toString());
            }
        }
        return result;
    }

    /**
     * Получение счета по его идентфикатору
     *
     * @param accountId идентфикатор счета
     * @return счет
     * @throws NotFoundException Если по указанному идентификатору нету счета
     */
    @Override
    public Account getAccount(final int accountId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getAccount(accountId ={})", currentRequestNum, accountId);
        final ru.korus.tmis.core.entity.model.Account account = checkAccount(accountId);
        if (account.isDeleted()) {
            logger.warn("Requested Account is deleted. {}", account.getInfo());
        }
        final Account result = ThriftStructBuilder.createAccount(account);
        logger.info("End of #{} getAccount. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Получение позиций счета
     *
     * @param accountId идентификатор счета
     * @return Список позиций счета \ пустой список если у счета нету позиций
     * @throws NotFoundException не было найдено такого счета
     * @throws TException
     */
    @Override
    public AccountInfo getAccountItems(final int accountId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getAccountItems({})", currentRequestNum, accountId);
        //Check Account
        final ru.korus.tmis.core.entity.model.Account requestedAccount = checkAccount(accountId);
        final List<ru.korus.tmis.core.entity.model.AccountItem> accountItems = accountItemBean.getByAccountId(accountId);
        final AccountInfo result = new AccountInfo().setItems(new ArrayList<AccountItem>());
        result.setAccount(ThriftStructBuilder.createAccount(requestedAccount));
        for (ru.korus.tmis.core.entity.model.AccountItem currentItem : accountItems)
            result.addToItems(ThriftStructBuilder.createAccountItem(currentItem));
        logger.info("End of #{} getAccountItems. Return \"{}\" items in result.", currentRequestNum, result.getItemsSize());
        if (logger.isDebugEnabled()) {
            for (AccountItem currentAccountItemToPrint : result.getItems()) {
                logger.debug(currentAccountItemToPrint.toString());
            }
        }
        return result;
    }

    /**
     * Проставление отметки "не выгружать более" для заданных позиций счета
     *
     * @param items Список структур позиций счета, которых более не требуется выгружать ни в одном счете
     */
    @Override
    public void setDoNotUploadAnymoreMarks(List<AccountItemWithMark> items) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> setDoNotUploadAnymoreMarks(size ={})\n({})", new Object[] { currentRequestNum, items.size(), items });
        for (AccountItemWithMark item : items) {
            logger.debug("Start processing: {}", item);
            ru.korus.tmis.core.entity.model.AccountItem currentAccountItem = accountItemBean.getById(item.getId());
            if (currentAccountItem != null) {
                if (currentAccountItem.isNotUploadAnymore() == item.isStatus()) {
                    logger.debug("AccountItem[{}] has same status with requested...", currentAccountItem.getId());
                } else {
                    currentAccountItem.setNotUploadAnymore(item.isStatus());
                    if (item.isSetNote()) {
                        currentAccountItem.setNote(item.getNote());
                    }
                    accountItemBean.merge(currentAccountItem);
                    logger.debug("Status changed");
                }
            } else {
                logger.debug("AccountItem[{}] not founded in DB", item.getId());
            }
        }
    }

    /**
     * Удаление счета(deleted = 1) по идентификатору
     *
     * @param accountId идентификатор счета
     * @return статус удаления
     * @throws TException Если счета с указанным идентификатором не существует
     */
    @Override
    public boolean deleteAccount(final int accountId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> deleteAccount(accountId ={})", currentRequestNum, accountId);
        ru.korus.tmis.core.entity.model.Account toDelete = checkAccount(accountId);
        toDelete = accountBean.deleteAccount(toDelete);
        boolean result = toDelete.isDeleted();
        logger.info("End of #{} deleteAccount. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Внутренний метод для получений счета по его идентификатору/Или кинуть ошибку NotFoundException
     *
     * @param accountId Идентификатор счета
     * @return счет
     * @throws TException NotFoundException в случае если нету такого счета
     */
    private ru.korus.tmis.core.entity.model.Account checkAccount(final int accountId) throws TException {
        final ru.korus.tmis.core.entity.model.Account result = accountBean.getById(accountId);
        if (result == null || result.isDeleted()) {
            logger.warn("No such Account[{}] exists.", accountId);
            throw TFOMSErrors.ACCOUNT_NOT_EXISTS.getException();
        } else if (logger.isDebugEnabled()) {
            logger.debug("Requested Account: {}", result.getInfo());
        }
        return result;
    }

    //-------------------------------------------------------------------------------------------------
    //XML
    //-------------------------------------------------------------------------------------------------

    /**
     * Получение двух реестров для форимрование xml-отчета
     *
     * @param contractId            идентификатор контракта по которому будут искаться оказаные услуги
     * @param beginDate             дата начала интервала поиска услуг
     * @param endDate               дата окончания интервала поиска услуг
     * @param organisationInfis     инфис код организации для которой выполяется выгрузка
     * @param orgStructureIdList    список подразделения для которых выполняется поиск
     * @param patientOptionalFields перечень запрошенных опциональных полей для реестра пациентов
     * @param sluchOptionalFields   перечень запрошенных опциональных полей для реестра услуг
     * @return Структура с реестрами пациентов и услуг
     * @throws InvalidOrganizationInfisException некорректный инфис код
     * @throws InvalidContractException          некорректный идентификатор контракта
     * @throws InvalidDateIntervalException      некорректный диапозон дат
     * @throws NotFoundException                 Ничего не найдено
     * @throws SQLException                      Ошибка при выполнении запроса к БД
     * @throws TException                        Общий суперкласс всех ошибок
     */
    @Override
    public XMLRegisters getXMLRegisters(final int contractId,
                                        final long beginDate,
                                        final long endDate,
                                        final String organisationInfis,
                                        final String obsoleteInfisCode,
                                        final String smoNumber,
                                        final List<Integer> orgStructureIdList,
                                        final Set<PatientOptionalFields> patientOptionalFields,
                                        final Set<PersonOptionalFields> personOptionalFields,
                                        final Set<SluchOptionalFields> sluchOptionalFields,
                                        final boolean primaryAccount,
                                        final String levelMO)
            throws TException {
        final int currentRequestNum = ++requestNum;
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Logger section
        logger.info("#{} Call method -> getXMLRegisters(contract_id={}, beginDate={}, endDate={}, infisCode=\"{}\","
                        + "obsoleteInfisCode=\"{}\" smoNumber=\"{}\", primaryAccont={}, levelMO={})",
                new Object[] { currentRequestNum, contractId, beginDate, endDate, organisationInfis,
                        obsoleteInfisCode, smoNumber, primaryAccount, levelMO }
        );
        logger.info("PatientOptionalFields={}", patientOptionalFields);
        logger.info("SluchOptionalFields={}", sluchOptionalFields);
        final Date beginInterval = DateConvertions.convertUTCMillisecondsToLocalDate(beginDate);
        final Date endInterval = DateConvertions.convertUTCMillisecondsToLocalDate(endDate);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Check params section
        //Check contract
        final Contract contract = XMLHelper.checkContract(contractId, currentRequestNum);
        //Check dates
        XMLHelper.checkDates(beginInterval, endInterval, currentRequestNum);
        //Check organisation
        final Organisation organisation = XMLHelper.checkOrganisation(organisationInfis, currentRequestNum);
        //Check orgStructure list
        XMLHelper.checkOrgStructureList(orgStructureIdList);
        //Check smoNumber
        if (smoNumber.length() != 2) {
            logger.error("End of #{}. SMO number[{}] has incorrect length", requestNum, smoNumber);
            throw TFOMSErrors.SMOMUMBER_INCORRECT.getException();
        }
        //check primaryAccount
        final int packetNumber = accountBean.getPacketNumber(contractId, beginInterval, endInterval);
        //TODO uncomment down line to check
        //XMLHelper.checkPrimary(primaryAccount, packetNumber);
        //End of check params section
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Some preferences section

        //Method RETURN result
        final XMLRegisters result = new XMLRegisters();
        //Дата формирования реестра
        final Date formDate = new LocalDate().toDate();
        //http://helpdesk.korusconsulting.ru/browse/WMIS-84
        //при выгрузке за прошедший месяц в номере счета и в названиях реестров фигурирует текущий месяц
        logger.debug("Forming date = \"{}\", packetNumber = {}", formDate, packetNumber);
        result.setData(DateConvertions.convertDateToUTCMilliseconds(formDate));
        //Значение тега PR_NOV
        result.setPR_NOV(XMLHelper.getPRNOV(packetNumber));
        logger.debug("PR_NOV set to {}", result.getPR_NOV());
        //Формирование названий реестров
        final String patientFileName = XMLHelper.getPatientFileName(
                organisation.getObsoleteInfisCode(),
                smoNumber,
                beginInterval,
                packetNumber
        );
        final String serviceFileName = XMLHelper.getServiceFileName(
                organisation.getObsoleteInfisCode(),
                smoNumber,
                beginInterval,
                packetNumber
        );
        logger.debug("Patient registry filename = \"{}\"", patientFileName);
        logger.debug("Service registry filename = \"{}\"", serviceFileName);
        result.setPatientRegistryFILENAME(patientFileName);
        result.setServiceRegistryFILENAME(serviceFileName);
        //Вычислем номер счета
        final String newAccountNumber = XMLHelper.getAccountNumber(beginInterval, packetNumber, organisation.getObsoleteInfisCode());
        logger.debug("New Account must have number = \"{}\"", newAccountNumber);

        //Результат работы выгрузки
        final Map<Person, List<Sluch>> registry = new HashMap<Person, List<Sluch>>();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Processing section
        //Выбранные и проверенные параметры
        final Map<String, String> uploadParam = XMLHelper.getStartedParamMap(
                contract,
                beginInterval,
                endInterval,
                organisation,
                orgStructureIdList,
                obsoleteInfisCode,
                levelMO,
                smoNumber
        );
        if (logger.isDebugEnabled()) {
            logger.debug("Parameter map:\n {}", uploadParam.toString());
        }
        //создаем и записываем в БД новый счет
        final ru.korus.tmis.core.entity.model.Account resultAccount = accountBean.createNewAccount(
                contract,
                null,
                contract.getPayerId(),
                formDate,
                newAccountNumber,
                0,       //amount
                0,       //uet
                0,       //sum
                formDate, //Дата отправки в ТФОМС
                beginInterval, //begDate
                endInterval,   //endDate
                null
        );
        logger.debug("Persisted {}", resultAccount.getInfo());

        //Заполняем кэш запросов
        queryChache.clearCache();
        queryChache.cacheQueries();
        //Предвыборка некоторых идентификаторов
        XMLHelper.preSelectParameters(uploadParam);
        queryChache.setStartedParameters(uploadParam);


        final boolean needForSpokesman = XMLHelper.isSpokesmanNeeded(personOptionalFields);
        logger.info("############STATIONARY#######################");
        long startTime = System.currentTimeMillis();
        XMLHelper.processStationarySluch(
                registry,
                primaryAccount,
                resultAccount,
                organisationInfis,
                obsoleteInfisCode,
                patientOptionalFields,
                personOptionalFields,
                sluchOptionalFields,
                needForSpokesman
        );
        logger.info("Processing stationary Sluch finished in {} seconds", (float) (System.currentTimeMillis() - startTime) / 1000);

        logger.info("############POLICLINIC#######################");
        startTime = System.currentTimeMillis();
        XMLHelper.processPoliclinicSluch(
                registry,
                primaryAccount,
                resultAccount,
                organisationInfis,
                obsoleteInfisCode,
                patientOptionalFields,
                personOptionalFields,
                sluchOptionalFields,
                needForSpokesman
        );
        logger.info("Processing policlinic Sluch finished in {} seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
        //Формирование записей в таблице Account_AktInfo
        formAccount_AktInfo(resultAccount, registry, patientFileName, serviceFileName);
        if (registry.isEmpty()) {
            logger.info("End of #{}. Empty registry", currentRequestNum);
            throw TFOMSErrors.EMPTY_REGISTRY.getException();
        }
        //Сортируем услуги
        sortUslInSluch(registry);
        //Устанавливаем сформированный реестр
        result.setRegistry(registry);
        result.setAccount(ThriftStructBuilder.createAccount(accountBean.recalculateAccount(resultAccount)));
        //Данные для тега SCHET
        final Schet resultSchet = ThriftStructBuilder.createSchet(organisation, formDate, newAccountNumber, "58000", resultAccount);
        result.setSchet(resultSchet);
        logger.debug("SHET = {}", resultSchet);
        int sluchCount = printUploadResultToLog(result);
        queryChache.printSummaryUsage();
        logger.info("End of #{} getXMLRegisters. Return \"{}\" patients in result and \"{}\" sluch.",
                new Object[] { currentRequestNum, result.getRegistrySize(), sluchCount });
        return result;
    }

    private void formAccount_AktInfo(ru.korus.tmis.core.entity.model.Account account, final Map<Person, List<Sluch>> registry, final String patientFileName, final String serviceFileName) {
        int patientCount = 0;
        int sluchCount = 0;
        for (Map.Entry<Person, List<Sluch>> current : registry.entrySet()) {
            patientCount++;
            sluchCount += current.getValue().size();
        }
        final AccountAktInfo patientRegistryAktInfo = new AccountAktInfo(account, patientFileName, patientCount);
        final AccountAktInfo sluchRegistryAktInfo = new AccountAktInfo(account, serviceFileName, sluchCount);
        accountBean.persistAccountAktInfo(patientRegistryAktInfo);
        accountBean.persistAccountAktInfo(sluchRegistryAktInfo);
        logger.debug("PATIENT AccountAktInfo[{}] - {}", patientRegistryAktInfo.getId(), patientCount);
        logger.debug("SLUCH AccountAktInfo[{}] -{}", sluchRegistryAktInfo.getId(), sluchCount);
    }

    /**
     * Вывод результата работы выгрузки в лог
     *
     * @param result результат выгрузки
     * @return количество оказанных случаев (в выгружаемом реестре)
     */
    private int printUploadResultToLog(XMLRegisters result) {
        int sluchCount = 0;
        if (logger.isDebugEnabled()) {
            final long startTime = System.currentTimeMillis();
            for (Map.Entry<Person, List<Sluch>> itemToPrint : result.getRegistry().entrySet()) {
                logger.debug("###############################################################");
                logger.debug(itemToPrint.getKey().toString());
                logger.debug("---------------------------------------------------------------");
                for (Sluch sluchToPrint : itemToPrint.getValue()) {
                    logger.debug(sluchToPrint.toString());
                    sluchCount++;
                }
            }
            logger.debug("Print result in {} seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
        }
        return sluchCount;
    }


    /**
     * Сортируем услуги внутри случаев
     *
     * @param registry реестры которые необходимо конвертировать
     */
    private void sortUslInSluch(final Map<Person, List<Sluch>> registry) {
        for (Map.Entry<Person, List<Sluch>> entry : registry.entrySet()) {
            for (Sluch currentSluch : entry.getValue()) {
                //WMIS-16
                Collections.sort(currentSluch.getUSL(), new Comparator<Usl>() {
                    @Override
                    public int compare(Usl x, Usl y) {
                        if (x.getCODE_USL() != null && y.getCODE_USL() != null) {
                            return x.getCODE_USL().charAt(x.getCODE_USL().length() - 1) - y.getCODE_USL().charAt(y.getCODE_USL().length() - 1);
                        } else {
                            return 0;
                        }
                    }
                });
            }
        }
    }


    /**
     * Получение всех подразделений ЛПУ
     *
     * @param organisationInfis инфис-код ЛПУ для которого возвращаем все (даже deleted = true) подразделения
     * @return Список подразделений
     * @throws InvalidOrganizationInfisException В случае, если нету организации с переданным инфис-кодом
     * @throws TException                        В случае внутренней ошибки
     */
    @Override
    public List<OrgStructure> getOrgStructures(final String organisationInfis) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getOrgStructures({})", currentRequestNum, organisationInfis);
        //Check infis
        final Organisation requestedOrganisation;
        try {
            requestedOrganisation = organisationBean.getOrganizationByInfisCode(organisationInfis);
            logger.debug("By infis[{}] founded organisation[{}]", organisationInfis, requestedOrganisation.getId());
        } catch (CoreException e) {
            logger.warn("End of #{}. No such Organisation exists", currentRequestNum);
            throw TFOMSErrors.ORGANISATION_NOT_EXISTS.getException();
        }
        final List<OrgStructure> result = new ArrayList<OrgStructure>();
        //По всем оргструктурам, принадлежащим организации
        for (ru.korus.tmis.core.entity.model.OrgStructure currentDbOrgStructure
                : orgStructureBean.getOrgStructuresByOrganisationId(requestedOrganisation.getId())) {
            result.add(ThriftStructBuilder.createOrgStructure(currentDbOrgStructure));
        }
        logger.info("End of #{} getOrgStructures. Return \"{}\" items in result.", currentRequestNum, result.size());
        if (logger.isDebugEnabled()) {
            for (OrgStructure currentOrgStructureToPrint : result) {
                logger.debug(currentOrgStructureToPrint.toString());
            }
        }
        return result;
    }

    /**
     * Получение всех доступных контрактов для ЛПУ
     *
     * @param organisationInfis инифс-код ЛПУ, для которой возвращается список контрактов
     * @return список контрактов
     * @throws InvalidOrganizationInfisException В случае, если не найдено ЛПУ с переданным инфис-кодом
     * @throws TException                        В случае внутренней ошибки
     */
    @Override
    public List<ru.korus.tmis.tfoms.thriftgen.Contract> getAvailableContracts(final String organisationInfis)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getAvailableContracts({})", currentRequestNum, organisationInfis);
        //Check infis
        final Organisation requestedOrganisation;
        try {
            requestedOrganisation = organisationBean.getOrganizationByInfisCode(organisationInfis);
            logger.debug("By infis[{}] founded organisation[{}]", organisationInfis, requestedOrganisation.getId());
        } catch (CoreException e) {
            logger.warn("End of #{}. No such Organisation exists", currentRequestNum);
            throw TFOMSErrors.ORGANISATION_NOT_EXISTS.getException();
        }
        final List<ru.korus.tmis.tfoms.thriftgen.Contract> result = new ArrayList<ru.korus.tmis.tfoms.thriftgen.Contract>();
        //По всем контрактом, относящимся к организации организации
        for (Contract currentDbContract : contractBean.getContractByOrganisationId(requestedOrganisation.getId())) {
            if (!currentDbContract.isDeleted()) {
                //Не удален, то в результат
                result.add(ThriftStructBuilder.createContract(currentDbContract));
            }
        }
        logger.info("End of #{} getAvailableContracts. Return \"{}\" items in result.", currentRequestNum, result.size());
        if (logger.isDebugEnabled()) {
            for (ru.korus.tmis.tfoms.thriftgen.Contract currentContractToPrint : result) {
                logger.debug(currentContractToPrint.toString());
            }
        }
        return result;
    }

    //-------------------------------------------------------------------------------------------------
    //Загрузка из ТФОМС
    //-------------------------------------------------------------------------------------------------
    @Override
    public int changeClientPolicy(final int patientId, final TClientPolicy newPolicy)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> changeClientPolicy(patientId = {}, newPolicy = {}",
                new Object[] { currentRequestNum,
                        patientId,
                        newPolicy });
        int result;
        ru.korus.tmis.core.entity.model.Patient patient;
        if (newPolicy.getNumber().length() > Constants.POLICY_NUMBER_MAX_LENGTH) {
            logger.warn("Too many symbols in NPOLIS, must be 16");
            newPolicy.setNumber(newPolicy.getNumber().substring(0, Constants.POLICY_NUMBER_MAX_LENGTH));
        }
        if (newPolicy.getSerial().length() > Constants.POLICY_SERIAL_MAX_LENGTH) {
            logger.warn("Too many symbols in SPOLIS, must be 16");
            newPolicy.setSerial(newPolicy.getSerial().substring(0, Constants.POLICY_SERIAL_MAX_LENGTH));
        }
        try {
            patient = dbPatientBean.getPatientById(patientId);
            logger.debug("Patient founded. FIO= {} {} {}",
                    new Object[] { patient.getLastName(),
                            patient.getFirstName(),
                            patient.getPatrName() });
            final RbPolicyType newPolicyType = policyTypeBean.findByCode(String.valueOf(newPolicy.getPolicyTypeCode()));
            List<ClientPolicy> clientPolicies = clientPolicyBean.getActivePoliciesByClientAndType(patientId, newPolicyType);
            //Проверка на совпадение
            for (ClientPolicy currentPolicy : clientPolicies) {
                if (currentPolicy.getNumber().equals(newPolicy.getNumber())
                        && currentPolicy.getSerial().equalsIgnoreCase(newPolicy.getSerial())
                        && newPolicyType.equals(currentPolicy.getPolicyType())
                        &&
                        ((newPolicy.isSetInsurerInfisCode()
                                && currentPolicy.getInsurer() != null
                                && newPolicy.getInsurerInfisCode().equalsIgnoreCase(currentPolicy.getInsurer().getInfisCode())
                        ) || !newPolicy.isSetInsurerInfisCode())
                        ) {
                    logger.error("End of #{}. This combination of serial/number/type is already in database", currentRequestNum);
                    throw TFOMSErrors.POLICY_ALREADY_EXISTS.getException();
                }
            }
            //Удаление всех старых полисов
            clientPolicyBean.deleteAllClientPoliciesByType(patientId, String.valueOf(newPolicy.getPolicyTypeCode()));
            //Получение страховой фирмы
            Organisation insurer = null;
            if (newPolicy.isSetInsurerInfisCode()) {
                try {
                    insurer = organisationBean.getOrganizationByInfisCode(newPolicy.getInsurerInfisCode());
                } catch (CoreException e) {
                    logger.warn("Not found insurer with infisCode = {}", newPolicy.getInsurerInfisCode());
                }
            }
            //Сохранение полиса
            ClientPolicy resultPolicy = clientPolicyBean.insertOrUpdateClientPolicy(0, newPolicyType.getId(), insurer != null ? insurer.getId() : 0, newPolicy.getNumber(), newPolicy.getSerial(), new Date(), null, "", "Изменения из ТФОМС", patient, null);
            clientPolicyBean.persistNewPolicy(resultPolicy);
            result = 1;
        } catch (NoSuchPatientException e) {
            logger.error("End of #{}. No such patient in database", currentRequestNum);
            throw TFOMSErrors.POLICY_NO_SUCH_PATIENT.getException();
        } catch (NoSuchRbPolicyTypeException e) {
            logger.error("End of #{}. No such RbPolicyType in database", currentRequestNum);
            throw TFOMSErrors.POLICY_NO_SUCH_POLICY_TYPE.getException();
        } catch (CoreException e) {
            logger.error("End of #{}.Unknown Exception", currentRequestNum, e);
            throw TFOMSErrors.POLICY_UNKNOWN.getException();
        }
        logger.info("End of #{} changeClientPolicy. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    @Override
    public Map<Integer, String> loadTfomsPayments(
            final String fileName,
            final List<Payment> payments,
            final int refusedAmount,
            final int payedAmount,
            final double payedSum,
            final double refusedSum,
            final String accountNumber,
            final String comment
    ) throws TException {
        final int currentRequestNum = ++requestNum;
        //to log
        logger.info("#{} Call method -> loadTfomsPayments", currentRequestNum);
        logger.info("Parameters:\nfileName = {}\nrefusedAmount = {}\npayedAmount = {}\npayedSum = {}\nrefusedSum = {}\n" +
                        "accountNumber = {}\ncomment = {}",
                new Object[] { fileName, refusedAmount, payedAmount, payedSum, refusedSum, accountNumber, comment }
        );

        final Map<Integer, String> result = new HashMap<Integer, String>(payments.size());
        final Date payDate = new Date(); // Дата оплаты - текущая дата

        if (payments.isEmpty()) {
            return result;
        }
        ru.korus.tmis.core.entity.model.AccountItem checkItem =
                accountItemBean.getById(payments.get(0).getAccountItemId());
        final ru.korus.tmis.core.entity.model.Account account = checkItem.getMaster();


        if (account == null) {
            logger.error("End of #{}. Account[{}] not founded", currentRequestNum, accountNumber);
            throw new NotFoundException().setMessage("Account not founded");
        }
        accountBean.pay(account, payDate, payedSum, payedAmount, refusedSum, refusedAmount, comment);
        for (Payment currentPayment : payments) {
            logger.info("Start processing: {}", currentPayment.toString());
            ru.korus.tmis.core.entity.model.AccountItem currentItem =
                    accountItemBean.getById(currentPayment.getAccountItemId());
            if (currentItem != null) {
                if (!account.getId().equals(currentItem.getMaster().getId())) {
                    //Выбранный случай относится к другому счету
                    result.put(currentPayment.getAccountItemId(), "Requested item is in another account");
                    continue;
                }
                if (currentPayment.isSetRefuseTypeCode() && !"0".equals(currentPayment.getRefuseTypeCode())) {
                    RbPayRefuseType payRefuseType = refuseTypeBean.getByCode(currentPayment.getRefuseTypeCode());
                    if (payRefuseType != null) {
                        accountItemBean.refuse(currentItem, payRefuseType, fileName, payDate, currentPayment.getComment());
                    } else {
                        result.put(currentPayment.getAccountItemId(), "RbPayRefuseType not founded");
                    }
                } else {
                    accountItemBean.pay(currentItem, fileName, payDate, currentPayment.getComment());
                }
            } else {
                result.put(currentPayment.getAccountItemId(), "AccountItem not founded");
            }
        }
        logger.info("End of #{} loadTfomsPayments. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    //-------------------------------------------------------------------------------------------------
    //Beans setters
    //-------------------------------------------------------------------------------------------------

    public static void setDbPatientBean(DbPatientBeanLocal dbPatientBean) {
        TFOMSServer.dbPatientBean = dbPatientBean;
    }

    public static DbOrganizationBeanLocal getOrganisationBean() {
        return organisationBean;
    }

    public static void setOrganisationBean(DbOrganizationBeanLocal organisationBean) {
        TFOMSServer.organisationBean = organisationBean;
    }

    public static DbAccountBeanLocal getAccountBean() {
        return accountBean;
    }

    public static void setAccountBean(DbAccountBeanLocal accountBean) {
        TFOMSServer.accountBean = accountBean;
    }

    public static DbAccountItemBeanLocal getAccountItemBean() {
        return accountItemBean;
    }

    public static void setAccountItemBean(DbAccountItemBeanLocal accountItemBean) {
        TFOMSServer.accountItemBean = accountItemBean;
    }

    public static DbOrgStructureBeanLocal getOrgStructureBean() {
        return orgStructureBean;
    }

    public static void setOrgStructureBean(DbOrgStructureBeanLocal orgStructureBean) {
        TFOMSServer.orgStructureBean = orgStructureBean;
    }

    public static DbContractBeanLocal getContractBean() {
        return contractBean;
    }

    public static void setContractBean(DbContractBeanLocal contractBean) {
        TFOMSServer.contractBean = contractBean;
    }

    public static void setRefuseTypeBean(DbRbPayRefuseTypeBeanLocal refuseTypeBean) {
        TFOMSServer.refuseTypeBean = refuseTypeBean;
    }

    public static void setSpecialPreferencesBean(DbRbSpecialVariablesPreferencesBeanLocal specialPreferencesBean) {
        TFOMSServer.specialPreferencesBean = specialPreferencesBean;
        queryChache = new SpecialVariablesPreferencesQueryCache(specialPreferencesBean);
    }

    public static DbRbSpecialVariablesPreferencesBeanLocal getSpecialPreferencesBean() {
        return specialPreferencesBean;
    }

    public static SpecialVariablesPreferencesQueryCache getQueryChache() {
        return queryChache;
    }

    public static void setQueryChache(SpecialVariablesPreferencesQueryCache queryChache) {
        TFOMSServer.queryChache = queryChache;
    }
}
