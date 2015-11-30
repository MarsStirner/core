package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Address;
import ru.korus.tmis.communication.thriftgen.OrgStructure;
import ru.korus.tmis.communication.thriftgen.Patient;
import ru.korus.tmis.communication.thriftgen.PersonSchedule;
import ru.korus.tmis.communication.thriftgen.Queue;
import ru.korus.tmis.communication.thriftgen.Speciality;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.database.epgu.EPGUTicketBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Schedule;
import ru.korus.tmis.core.entity.model.communication.QueueTicket;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.schedule.*;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 03.06.2014, 17:47 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class CommunicationServer implements Communications.Iface {
    //Logger
    private static final Logger logger = LoggerFactory.getLogger(CommunicationServer.class);
    private static final int PROTOCOL_VERSION = 2;
    private static final Marker LOGGING_SUBSYSTEM_MARKER = MarkerFactory.getMarker("LOGGING_SUBSYSTEM_MARKER");
    final DateTimeFormatter dateformatter = DateTimeFormat.forPattern("YYYY-MM-dd");
    final DateTimeFormatter dateTimeformatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");

    //Singleton instance
    private static CommunicationServer instance;
    private static TServer server;
    private static TServerSocket serverTransport;
    //THREAD properties
    //Listener thread
    private Thread communicationListener = null;
    //Listener port
    private static final int PORT_NUMBER = 7914;
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.NORM_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;
    //work threads in threadpool
    private static final int MAX_WORKER_THREADS = 25;
    private static final int MIN_WORKER_THREADS = 3;
    //Number of request
    private static int requestNum = 0;

    private static DbOrgStructureBeanLocal orgStructureBean = null;
    private static DbPatientBeanLocal patientBean = null;
    private static DbStaffBeanLocal staffBean = null;
    private static DbQuotingBySpecialityBeanLocal quotingBySpecialityBean = null;
    private static DbOrganizationBeanLocal organisationBean = null;
    //////////////////////////////////////////////////////////
    private static DbClientDocumentBeanLocal documentBean = null;
    private static DbRbDocumentTypeBeanLocal documentTypeBean = null;
    private static DbClientPolicyBeanLocal policyBean = null;
    private static DbRbPolicyTypeBeanLocal policyTypeBean = null;
    //////////////////////////////////////////////////////////
    private static EPGUTicketBeanLocal queueTicketBean = null;
    //////////////////////////////////////////////////////////
    private static PatientQueueBeanLocal patientQueueBean = null;
    private static ScheduleBeanLocal scheduleBean = null;


    /**
     * Версия сервиса
     *
     * @return номер версии
     */
    @Override
    public int getVersion() throws TException {

        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getVersion() -> {}", currentRequestNum, PROTOCOL_VERSION);
        return PROTOCOL_VERSION;
    }

    /**
     * получение информации об организации(ЛПУ) по ее инфис-коду
     *
     * @param infisCode 1)Инфис-код организации
     * @return Структуа с информацией об организации
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда в БД ЛПУ нету организации с таким инфис-кодом
     */
    @Override
    public Organization getOrganisationInfo(final String infisCode) throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getOrganisationInfo(infisCode={})", currentRequestNum, infisCode);
        final Organization result;
        try {
            result = ParserToThriftStruct.parseOrganisation(organisationBean.getOrganizationByInfisCode(infisCode));
        } catch (CoreException e) {
            logger.error("#{} COREException. Message=", currentRequestNum, e.getMessage());
            throw new NotFoundException().setError_msg(CommunicationErrors.msgItemNotFound.getMessage());
        }
        logger.info("End of #{} getOrganisationInfo. Return ({})", currentRequestNum, result);
        return result;
    }

    /**
     * Получение списка подразделений, входящих в заданное подразделение
     *
     * @param parentId  1) идентификатор подразделения, для которого нужно найти дочернии подразделения
     * @param recursive 2) Флаг рекурсии (выбрать также подразделения, входяшие во все дочерние подразделения)
     * @param infisCode 3) Инфис-код
     * @return Список структур, содержащих информацию о дочерних подразделениях
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было найдено ни одного подразделения, удовлетворяющего заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<OrgStructure> getOrgStructures(final int parentId, final boolean recursive, final String infisCode)
            throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getOrgStructures(id={}, recursive={}, infisCode={})",
                currentRequestNum, parentId, recursive, infisCode);
        //Список для хранения сущностей из БД
        final List<ru.korus.tmis.core.entity.model.OrgStructure> orgStructureList;
        try {
            //Получение нужных сущностей из бина
            orgStructureList = orgStructureBean.getRecursiveOrgStructures(parentId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " CoreException while getRecursive from bean.", e);
            throw new NotFoundException().setError_msg("None of the OrgStructure contain any such parent =" + parentId);
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException(CommunicationErrors.msgUnknownError.getMessage(), e);
        }
        //Список который будет возвращен
        final List<OrgStructure> resultList = new ArrayList<OrgStructure>(orgStructureList.size());
        //Конвертация сущностей в возвращаемые структуры
        for (final ru.korus.tmis.core.entity.model.OrgStructure current : orgStructureList) {
            resultList.add(ParserToThriftStruct.parseOrgStructure(current));
        }
        logger.info("End of #{} getOrgStructures. Return (size={} DATA=({})) as result.",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение списка всех подразделений, относящихся к запрошенному ЛПУ
     *
     * @param parentId  1) идентификатор подразделения, для которого нужно найти дочернии подразделения
     * @param recursive 2) Флаг рекурсии (выбрать также подразделения, входяшие во все дочерние подразделения)
     * @param infisCode 3) Инфис-код
     * @return Список структур, содержащих информацию о дочерних подразделениях
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было найдено ни одного подразделения, удовлетворяющего заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<OrgStructure> getAllOrgStructures(final int parentId, final boolean recursive, final String infisCode)
            throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getAllOrgStructures(id={}, recursive={}, infisCode={})",
                currentRequestNum, parentId, recursive, infisCode);
        //Список для хранения сущностей из БД
        final List<ru.korus.tmis.core.entity.model.OrgStructure> orgStructureList;
        try {
            //Получение нужных сущностей из бина
            orgStructureList = orgStructureBean.getRecursiveOrgStructuresWithoutAvailableForExternal(parentId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " CoreException while getRecursive from bean.", e);
            throw new NotFoundException().setError_msg("None of the OrgStructure contain any such parent =" + parentId);
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException(CommunicationErrors.msgUnknownError.getMessage(), e);
        }
        //Список который будет возвращен
        final List<OrgStructure> resultList = new ArrayList<OrgStructure>(orgStructureList.size());
        //Конвертация сущностей в возвращаемые структуры
        for (final ru.korus.tmis.core.entity.model.OrgStructure current : orgStructureList) {
            resultList.add(ParserToThriftStruct.parseOrgStructure(current));
        }
        logger.info("End of #{} getAllOrgStructures. Return (size={} DATA=({})) as result.",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение обслуживаемых адресов запрошенного подразделения
     *
     * @param orgStructureId 1) идетификатор подразделения, для которого требуется найти обслуживаемые им адреса
     * @param recursive      2) Флаг рекурсии (выбрать также подразделения, входяшие во все дочерние подразделения)
     * @param infisCode      3) Инфис-код
     * @return Список структур, содержащих информацию об адресах запрошенных подразделений
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было найдено ни одного адреса подразделения, удовлетворяющего заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Address> getAddresses(final int orgStructureId, final boolean recursive, final String infisCode)
            throws SQLException, NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getAddresses(orgStructureId={}, recursive={}, infisCode={})",
                currentRequestNum, orgStructureId, recursive, infisCode);
        //Список для хранения сущностей из БД
        final List<ru.korus.tmis.core.entity.model.OrgStructure> orgStructureList = new ArrayList<ru.korus.tmis.core.entity.model.OrgStructure>();
        try {
            if (orgStructureId != 0) {
                //Получение запрашиваемой ОргСтруктуры (если такой нету, то выход с ошибкой)
                orgStructureList.add(orgStructureBean.getOrgStructureById(orgStructureId));
            }
            //Получение нужных сущностей из бина
            orgStructureList.addAll(orgStructureBean.getRecursiveOrgStructures(orgStructureId, recursive, infisCode));
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " Error while getRecursive from bean.", e);
            if (orgStructureList.isEmpty()) {
                throw new NotFoundException().setError_msg("None of the OrgStructure contain this infisCode and any such parent_id=" + orgStructureId);
            }
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException(CommunicationErrors.msgUnknownError.getMessage(), e);
        }
        final List<Address> resultList = new ArrayList<Address>(orgStructureList.size());
        for (final ru.korus.tmis.core.entity.model.OrgStructure currentOrgStructure : orgStructureList) {
            logger.debug("OrgStructure={}", currentOrgStructure);
            for (final OrgStructureAddress currentOrgStructureAddress
                    : orgStructureBean.getOrgStructureAddressByOrgStructure(currentOrgStructure)) {
                logger.debug("OrgStructureAddress ={}", currentOrgStructureAddress);
                if (currentOrgStructureAddress != null) {
                    if (currentOrgStructureAddress.getAddressHouseList() != null) {
                        resultList.add(ParserToThriftStruct.parseAddress(currentOrgStructure, currentOrgStructureAddress));
                    } else {
                        logger.debug("AddressHouse=NULL");
                    }
                }
            }
        }
        logger.info("End of #{} getAddresses. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение списка идентификаторов подразделений, расположенных по указанному адресу
     *
     * @param params 1) Структура с параметрами поиска подразделений по адресу
     * @return Список идентификаторов подразделений, приписанных к запрошенному адресу
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было найдено ни одного подразделения, удовлетворяющего заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Integer> findOrgStructureByAddress(final FindOrgStructureByAddressParameters params)
            throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.findOrgStructureByAddress(streetKLADR={}, pointKLADR={}, number={}/{} flat={})",
                currentRequestNum, params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        final List<Integer> resultList = orgStructureBean.getOrgStructureIdListByAddress(
                params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        if (resultList.isEmpty()) {
            logger.error("End of #{}. No one orgStructureFound", currentRequestNum);
            throw new NotFoundException().setError_msg("No one OrgStructure found.");
        }
        logger.info("End of #{} findOrgStructureByAddress. Return (size={} DATA=({})) as result.",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение списка персонала, работающего в запрошенном подразделении
     *
     * @param orgStructureId 1) идентификатор подразделения
     * @param recursive      2) флаг рекусрии
     * @param infisCode      3) инфис-код
     * @return Список идентификаторов подразделений, приписанных к запрошенному адресу
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было найдено ни одного работника, удовлетворяющего заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Person> getPersonnel(final int orgStructureId, final boolean recursive, final String infisCode)
            throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPersonnel(OrgStructureId={}, recursive={}, infisCode={})",
                currentRequestNum, orgStructureId, recursive, infisCode);
        final List<Staff> personnelList;
        try {
            personnelList = orgStructureBean.getPersonnel(orgStructureId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " CoreException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("No one Person related with this orgStructures (COREException)");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException(CommunicationErrors.msgUnknownError.getMessage(), e);
        }
        final List<Person> resultList = new ArrayList<Person>(personnelList.size());
        //Конвертация сущностей в возвращаемые структуры
        for (final Staff person : personnelList) {
            resultList.add(ParserToThriftStruct.parseStaff(person));
        }
        logger.info("End of #{} getPersonnel. Return (size={} DATA=({})) as result.",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * НЕ РЕАЛИЗОВАНО
     *
     * @param params
     */
    @Override
    public TicketsAvailability getTotalTicketsAvailability(final GetTicketsAvailabilityParameters params) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getTotalTicketsAvailability({})", currentRequestNum, params);
        final TicketsAvailability result = null;
        logger.info("End of #{} getTotalTicketsAvailability. Return \"({})\" as result.", currentRequestNum, result);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    /**
     * НЕ РЕАЛИЗОВАНО
     *
     * @param params
     */
    @Override
    public List<ExtendedTicketsAvailability> getTicketsAvailability(final GetTicketsAvailabilityParameters params) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getTicketsAvailability({})", currentRequestNum, params);

        final List<ExtendedTicketsAvailability> result = new ArrayList<ExtendedTicketsAvailability>(0);
        logger.info("End of #{} getTicketsAvailability. Return (Size={}), DATA={})", currentRequestNum, result.size(), result);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    @Deprecated
    @Override
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getWorkTimeAndStatus({})", currentRequestNum, params);
        logger.info("End of #{} getWorkTimeAndStatus. return NOT IMPLEMENTED. ", currentRequestNum);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    /**
     * добавление нового пациента в БД ЛПУ
     *
     * @param params 1) Структура с данными для нового пациента
     * @return Структура со сведениями о статусе добавления пациента
     * @throws ru.korus.tmis.communication.thriftgen.SQLException когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method ->CommunicationServer.addPatient( {} )", currentRequestNum, params);
        final PatientStatus result = new PatientStatus();
        //CHECK PARAMS
        if (!CommunicationHelper.checkAddPatientParams(params, result)) {
            logger.warn("End of #{} addPatient. Error message=\"{}\"", currentRequestNum, result.getMessage());
            return result.setSuccess(false).setPatientId(0);
        }
        final ru.korus.tmis.core.entity.model.Patient patient;
        try {
            patient = patientBean.insertOrUpdatePatient(
                    0,
                    params.firstName,
                    params.patrName,
                    params.lastName,
                    DateConvertions.convertUTCMillisecondsToDate(params.getBirthDate()),
                    "",
                    CommunicationHelper.getSexAsString(params.getSex()),
                    "0",
                    "0",
                    "",
                    null,
                    0,
                    0,
                    null,
                    "",
                    "",
                    null,
                    0
            );
            patientBean.savePatientToDataBase(patient);
            logger.debug("Patient ={}", patient);
            if (patient.getId() == null || patient.getId() == 0) {
                throw new CoreException("Something is wrong while saving");
            } else {
                logger.debug("Patient saved in DB, and now has id={}", patient.getId());
            }
            //Сохранение документов (если заполнены)
            if (params.isSetDocumentNumber()
                    && params.isSetDocumentSerial()
                    && params.isSetDocumentTypeCode()
                    && !params.getDocumentTypeCode().isEmpty()) {
                final RbDocumentType documentType = documentTypeBean.findByCode(params.getDocumentTypeCode());
                if (documentType != null) {
                    final ClientDocument document = documentBean.insertOrUpdateClientDocument(
                            0,
                            documentType.getId(),
                            "",
                            params.getDocumentNumber(),
                            params.getDocumentSerial(),
                            new Date(),
                            new Date(),
                            patient,
                            null);
                    final ClientDocument persistedDocument = documentBean.persistNewDocument(document);
                    logger.debug("Persisted Document[{}]", persistedDocument.getId());
                } else {
                    logger.warn("With code[{}] no one rbDocumentType founded", params.getDocumentTypeCode());
                }
            }
            //Сохранение полисов (если заполнены)
            if (params.isSetPolicyNumber()
                    && params.isSetPolicyTypeCode()
                    && !params.getPolicyTypeCode().isEmpty()) {
                final RbPolicyType policyType = policyTypeBean.findByCode(params.getPolicyTypeCode());
                if (policyType != null) {
                    //страховщик
                    Organisation insurer = null;
                    if (params.isSetPolicyInsurerInfisCode() && !params.getPolicyInsurerInfisCode().isEmpty()) {
                        try {
                            insurer = organisationBean.getOrganizationByInfisCode(params.getPolicyInsurerInfisCode());
                        } catch (CoreException e) {
                            logger.warn("Couldn't find organisation with InfisCode=\"{}\"", params.getPolicyInsurerInfisCode());
                        }
                    }
                    final ClientPolicy policy = policyBean.insertOrUpdateClientPolicy(
                            0,
                            policyType.getId(),
                            insurer != null ? insurer.getId() : 0,
                            params.getPolicyNumber(),
                            params.isSetPolicySerial() ? params.getPolicySerial() : "",
                            new Date(),
                            null,
                            "",
                            "Данные из ТФОМС",
                            patient,
                            null);
                    final ClientPolicy persistedPolicy = policyBean.persistNewPolicy(policy);
                    logger.debug("Persisted policy[{}]", persistedPolicy.getId());
                } else {
                    logger.warn("With code[{}] no one rbPolicyType founded", params.getPolicyTypeCode());
                }
            }
        } catch (CoreException e) {
            logger.error("Error while saving to database", e);
            return result.setMessage("Error while saving to database. Message=" + e.getMessage()).setSuccess(false);
        }
        result.setMessage("Successfully added patient to database").setSuccess(true).setPatientId(patient.getId());
        logger.info("End of #{} addPatient. Return \"{}\"", currentRequestNum, result);
        return result;
    }

    /**
     * Поиск пациента в БД ЛПУ по заданным параметрам
     *
     * @param params 1) Структура с данными для поиска единственного пациента
     * @return Структура с данными о результатах посика пациента
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда не было переданно ни одного документа
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public PatientStatus findPatient(final FindPatientParameters params) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method ->CommunicationServer.findPatient({})", currentRequestNum, params);
        //Convert to patterns && MAP
        final Map<String, String> parameters = new HashMap<String, String>();
        if (params.isSetLastName()) {
            parameters.put("lastName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.lastName));
        }
        if (params.isSetFirstName()) {
            parameters.put("firstName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.firstName));
        }
        if (params.isSetPatrName()) {
            parameters.put("patrName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.patrName));
        }
        if (params.isSetBirthDate()) {
            parameters.put("birthDate", String.valueOf(params.getBirthDate()));
        }
        if (params.isSetIdentifier()) {
            parameters.put("identifier", params.getIdentifier());
        }
        if (params.isSetIdentifierType()) {
            parameters.put("identifierType", params.getIdentifierType());
        }
        if (params.isSetSex()) {
            parameters.put("sex", String.valueOf(params.getSex()));
        }
        logger.debug("DOCUMENTS IS {}", params.getDocument());
        logger.debug("MAPPED PARAMS IS {}", parameters);
        final List<ru.korus.tmis.core.entity.model.Patient> patientsList;
        try {
            if (!params.isSetDocument() || params.getDocument().isEmpty()) {
                logger.warn("Document map is not set or empty");
                patientsList = patientBean.findPatientWithoutDocuments(parameters);
            } else {
                final Map<String, String> document = params.getDocument();
                if (document.containsKey(DocumentMapFields.CLIENT_ID.getFieldName())) {
                    try {
                        patientsList = patientBean.findPatient(parameters,
                                Integer.parseInt(document.get(DocumentMapFields.CLIENT_ID.getFieldName())));
                    } catch (NumberFormatException nfe) {
                        logger.error("Exception in findPatient by {}. Cannot parse \'{}\' to Integer", DocumentMapFields.CLIENT_ID.getFieldName(), document.get(DocumentMapFields.CLIENT_ID.getFieldName()));
                        return new PatientStatus().setSuccess(false).setMessage(CommunicationErrors.msgInvalidClientId.getMessage());
                    }
                } else {
                    final String number;
                    final String serial;
                    //Проверка существования и инициализация номера и серии
                    //Если нету таких полей - провал поиска с сообщением что документы не прикреплены
                    if (document.containsKey(DocumentMapFields.NUMBER.getFieldName())) {
                        number = document.get(DocumentMapFields.NUMBER.getFieldName());
                        if (document.containsKey(DocumentMapFields.SERIAL.getFieldName())) {
                            serial = document.get(DocumentMapFields.SERIAL.getFieldName());
                        } else {
                            logger.error("#{} Document map not contain {} field. Map value ={}",
                                    currentRequestNum, DocumentMapFields.SERIAL.getFieldName(), document.toString());
                            return new PatientStatus().setSuccess(false)
                                    .setMessage(CommunicationErrors.msgNoDocumentsAttached.getMessage());
                        }
                    } else {
                        logger.error("#{} Document map not contain {} field. Map value ={}",
                                currentRequestNum, DocumentMapFields.NUMBER.getFieldName(), document.toString());
                        return new PatientStatus().setSuccess(false)
                                .setMessage(CommunicationErrors.msgNoDocumentsAttached.getMessage());
                    } // Конец проверки и инициализации серии и номера

                    if (document.containsKey(DocumentMapFields.DOCUMENT_CODE.getFieldName())) {
                        patientsList = patientBean.findPatientByDocument(
                                parameters,
                                serial,
                                number,
                                document.get(DocumentMapFields.DOCUMENT_CODE.getFieldName())
                        );
                    } else if (document.containsKey(DocumentMapFields.POLICY_TYPE.getFieldName())) {
                        patientsList = patientBean.findPatientByPolicy(
                                parameters,
                                serial,
                                number,
                                document.get(DocumentMapFields.POLICY_TYPE.getFieldName())
                        );
                    } else {
                        logger.error("In document map there no \"{}\", or \"{}\", or \"{}\" But map has keys {}",
                                DocumentMapFields.CLIENT_ID.getFieldName(),
                                DocumentMapFields.DOCUMENT_CODE.getFieldName(),
                                DocumentMapFields.POLICY_TYPE.getFieldName(),
                                document.keySet());
                        throw new NotFoundException(CommunicationErrors.msgNoDocumentsAttached.getMessage());
                    }
                }
            }
        } catch (CoreException e) {
            logger.error("Failed to get patients because: {} CAUSE={}", e.getMessage(), e.getCause());
            throw new TException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            for (ru.korus.tmis.core.entity.model.Patient pat : patientsList) {
                logger.debug("Patent in result: ID={} FULLNAME={} {} {} SEX={}", pat.getId(),
                        pat.getLastName(), pat.getFirstName(), pat.getPatrName(), pat.getSex());
            }
        }
        final PatientStatus result;
        if (patientsList.isEmpty()) {
            result = new PatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgNoSuchPatient.getMessage());

        } else if (patientsList.size() == 1) {
            result = new PatientStatus().setSuccess(true)
                    .setMessage(CommunicationErrors.msgOk.getMessage())
                    .setPatientId(patientsList.get(0).getId());
        } else {
            result = new PatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgTooManySuchPatients.getMessage());
        }

        logger.info("End of #{} findPatient. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Поиск пациентов в БД ЛПУ по заданным параметрам
     *
     * @param params 1) Структура с данными для поиска нескольких пациентов
     * @return Список структур с данными для найденных пациентов
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException НЕ возвращает этой ошибки
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Patient> findPatients(final FindMultiplePatientsParameters params) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method ->CommunicationServer.findPatients({})", currentRequestNum, params);
        //Convert to patterns && MAP
        final Map<String, String> parameters = new HashMap<String, String>();
        if (params.isSetLastName()) {
            parameters.put("lastName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.lastName));
        }
        if (params.isSetFirstName()) {
            parameters.put("firstName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.firstName));
        }
        if (params.isSetPatrName()) {
            parameters.put("patrName", CommunicationHelper.convertDotPatternToSQLLikePattern(params.patrName));
        }
        if (params.isSetBirthDate()) {
            parameters.put("birthDate", String.valueOf(params.getBirthDate()));
        }
        if (params.isSetIdentifier()) {
            parameters.put("identifier", params.getIdentifier());
        }
        if (params.isSetIdentifierType()) {
            parameters.put("identifierType", params.getIdentifierType());
        }
        if (params.isSetSex()) {
            parameters.put("sex", String.valueOf(params.getSex()));
        }
        logger.debug("MAPPED PARAMS IS {}", parameters);
        final List<ru.korus.tmis.core.entity.model.Patient> patientsList;
        try {
            patientsList = patientBean.findPatientsByParams(parameters, params.getDocument());
        } catch (Exception e) {
            logger.error("Failed to get patients because: {} {}", e.getMessage(), e.getCause());
            throw new TException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            for (ru.korus.tmis.core.entity.model.Patient pat : patientsList) {
                logger.debug("Patent in result: ID={} FULLNAME={} {} {} SEX={}", pat.getId(),
                        pat.getLastName(), pat.getFirstName(), pat.getPatrName(), pat.getSex());
            }
        }
        final List<ru.korus.tmis.communication.thriftgen.Patient> resultList =
                new ArrayList<ru.korus.tmis.communication.thriftgen.Patient>(patientsList.size());
        for (ru.korus.tmis.core.entity.model.Patient pat : patientsList) {
            resultList.add(ParserToThriftStruct.parsePatient(pat));
        }
        logger.info("End of #{} findPatients. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Поиск пациента по данным из ТФОМС
     *
     * @param params 1) Параметры поиска
     * @return Статус нахождения пациента
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException            когда не найдено ни одного пациента по заданным параметрам
     * @throws ru.korus.tmis.communication.thriftgen.InvalidPersonalInfoException когда по полису или документу найдены пациент(ы) в БД ЛПУ, но (ФИО/пол/др) отличаются от переданных
     * @throws ru.korus.tmis.communication.thriftgen.InvalidDocumentException     когда не найдено совпадений по полису и документу, но пациент с таким (ФИО/пол/др) уже есть в БД ЛПУ
     * @throws ru.korus.tmis.communication.thriftgen.AnotherPolicyException       когда пациент найден и документы совпали, но его полис отличается от запрошенного
     * @throws ru.korus.tmis.communication.thriftgen.NotUniqueException           когда по запрошенным параметрам невозможно выделить единственного пациента
     */
    @Override
    public PatientStatus findPatientByPolicyAndDocument(final FindPatientByPolicyAndDocumentParameters params) throws NotFoundException, InvalidPersonalInfoException, InvalidDocumentException, AnotherPolicyException, NotUniqueException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method ->CommunicationServer.findPatientByPolicyAndDocument({})", currentRequestNum, params);
        final PatientStatus result = new PatientStatus();
        //Поиск пациентов по ФИО, полу и ДР
        logger.debug("birthDate = {}", DateConvertions.convertUTCMillisecondsToDate(params.getBirthDate()));
        final List<ru.korus.tmis.core.entity.model.Patient> patientList = patientBean.findPatientsByPersonalInfo(
                params.getLastName(),
                params.getFirstName(),
                params.getPatrName(),
                params.getSex(),
                DateConvertions.convertUTCMillisecondsToDate(params.getBirthDate())
        );
        if (!patientList.isEmpty()) {
            //Вывод в лог
            if (logger.isDebugEnabled()) {
                logger.debug("Patient founded by personal info (count={}):", patientList.size());
                for (ru.korus.tmis.core.entity.model.Patient currentPatient : patientList) {
                    logger.debug(currentPatient.getInfoString());
                }
            }
        } else {
            logger.info("By personal info founded zero patients.");
        }
        //Поиск полисов по серии, номеру и коду типа полиса
        final List<ClientPolicy> policyList = policyBean.findBySerialAndNumberAndTypeCode(
                params.isSetPolicySerial() ? params.getPolicySerial() : "",
                params.getPolicyNumber(),
                params.getPolicyTypeCode()
        );
        if (!policyList.isEmpty()) {
            //Вывод в лог
            if (logger.isDebugEnabled()) {
                logger.debug("Policies founded by params (count={}):", policyList.size());
                for (ClientPolicy currentPolicy : policyList) {
                    logger.debug(currentPolicy.getInfoString());
                }
            }
            //Проверка страховщика
            if (params.isSetPolicyInsurerInfisCode() && !params.getPolicyInsurerInfisCode().isEmpty()) {
                for (ClientPolicy currentPolicy : policyList) {
                    if (currentPolicy.getInsurer() == null || !params.getPolicyInsurerInfisCode().equals(currentPolicy.getInsurer().getInfisCode())) {
                        logger.warn("Policy[{}] has another insurer that is in parameters", currentPolicy.getId());
                    }
                }
            }
        } else {
            logger.info("By params founded zero policies.");
        }
        //Поиск документов по серии, номеру и коду типа докумнента
        final List<ClientDocument> documentList = documentBean.findBySerialAndNumberAndTypeCode(
                params.getDocumentSerial(), params.getDocumentNumber(), params.getDocumentTypeCode()
        );
        if (!documentList.isEmpty()) {
            //Вывод в лог
            if (logger.isDebugEnabled()) {
                logger.debug("Documents founded by params (count={}):", documentList.size());
                for (ClientDocument currentDocument : documentList) {
                    logger.debug(currentDocument.getInfoString());
                }
            }
        } else {
            logger.info("By params founded zero documents.");
        }

        //Начнем перекрестную проверку
        if (!patientList.isEmpty()) {
            //список пациентов, у которых совпал полис
            final List<ru.korus.tmis.core.entity.model.Patient> resultList = new ArrayList<ru.korus.tmis.core.entity.model.Patient>(patientList.size());
            for (ru.korus.tmis.core.entity.model.Patient currentPatient : patientList) {
                for (ClientPolicy currentPolicy : policyList) {
                    if (currentPatient.getId().equals(currentPolicy.getPatient().getId())) {
                        logger.debug("Patient[{}] has policy[{}]", currentPatient.getId(), currentPolicy.getId());
                        resultList.add(currentPatient);
                    }
                }
            }
            switch (resultList.size()) {
                case 0: {
                    //Совпадений пациентов и полисов не найдено
                    int checkedWithDocuments = 0;
                    for (ru.korus.tmis.core.entity.model.Patient currentPatient : patientList) {
                        for (ClientDocument currentDocument : documentList) {
                            if (currentPatient.getId().equals(currentDocument.getPatient().getId())) {
                                if (checkedWithDocuments == 0 || checkedWithDocuments == currentPatient.getId()) {
                                    logger.debug("Patient[{}] has document[{}]", currentPatient.getId(), currentDocument.getId());
                                    checkedWithDocuments = currentPatient.getId();
                                } else {
                                    // В ходе проверки документов появился второй пациент с одним из отобранных документов
                                    logger.error("End of #{}. On document check there are still more then one patients", currentRequestNum);
                                    throw new NotUniqueException("Cannot select only one patient by params", 4);
                                }
                            }
                        }
                    }
                    if (checkedWithDocuments == 0) {
                        //проверка документов ничего не дала
                        logger.error("End of #{}. No one patient has this documents", currentRequestNum);
                        throw new InvalidDocumentException("No one patient contain this documents or policies", 3);
                    } else {
                        //после проверки по документам найден только один пациент
                        logger.info("Patient[{}] selected by documents. But hasnt requested policy", checkedWithDocuments);
                        throw new AnotherPolicyException("Policy is another or empty.", 7, checkedWithDocuments);
                    }
                }
                case 1: {
                    result.setSuccess(true);
                    result.setPatientId(patientList.get(0).getId());
                    result.setMessage(CommunicationErrors.msgOk.getMessage());
                    break;
                }
                default: {
                    //В случае если сопоставление пациентов и полисов дало больше одного совпадения.
                    int checkedWithDocuments = 0;
                    for (ru.korus.tmis.core.entity.model.Patient currentPatient : resultList) {
                        for (ClientDocument currentDocument : documentList) {
                            if (currentPatient.getId().equals(currentDocument.getPatient().getId())) {
                                logger.debug("Patient[{}] has document[{}]", currentPatient.getId(), currentDocument.getId());
                                if (checkedWithDocuments == 0 || checkedWithDocuments == currentPatient.getId()) {
                                    checkedWithDocuments = currentPatient.getId();
                                } else {
                                    // В ходе проверки документов появился второй пациент с одним из отобранных документов
                                    logger.error("End of #{}. On document check there are still more then one patients", currentRequestNum);
                                    throw new NotUniqueException("Cannot select only one patient by params", 2);
                                }
                            }
                        }
                    }
                    if (checkedWithDocuments == 0) {
                        //проверка документов ничего не дала
                        logger.error("End of #{}. No one patient has this documents", currentRequestNum);
                        throw new NotUniqueException("Cannot select only one patient by params", 3);
                    } else {
                        //после проверки по документам найден только один пациент
                        logger.info("Patient[{}] selected by documents.", checkedWithDocuments);
                        result.setSuccess(true);
                        result.setMessage("Selected by documents");
                        result.setPatientId(checkedWithDocuments);
                    }
                    break;
                }
            }
        } else {
            if (documentList.isEmpty() && policyList.isEmpty()) {
                //Нету ни полисов, ни документов
                logger.error("End of #{}. NotFound.", currentRequestNum);
                throw new NotFoundException(CommunicationErrors.msgItemNotFound.getMessage());
            } else {
                //Полисы или документы есть, но по ФИО не найдено пациентов
                logger.error("End of #{}. InvalidPersonalInfo. {} is founded",
                        currentRequestNum, policyList.isEmpty() ? "Document" : "Policy");
                throw new InvalidPersonalInfoException(
                        CommunicationErrors.msgInvalidPersonalInfo.getMessage(),
                        CommunicationErrors.msgInvalidPersonalInfo.getId());
            }
        }
        logger.info("End of #{} getPatientByPolicyAndDocument. Result is {}", currentRequestNum, result);
        return result;
    }

    /**
     * Добавление/ изменение полиса клиента
     *
     * @param params 1) Параметры для добавления полиса (struct ChangePolicyParameters)
     * @return успешность замены/добавления полиса
     * @throws ru.korus.tmis.communication.thriftgen.PolicyTypeNotFoundException когда нету типа полиса с переданным кодом
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException           когда нету пациента с переданным идентификатором
     */
    @Override
    public boolean changePatientPolicy(final ChangePolicyParameters params) throws PolicyTypeNotFoundException, NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.changePatientPolicy({})", currentRequestNum, params);
        final ru.korus.tmis.core.entity.model.Patient patient = CommunicationHelper.findPatientById(patientBean, params.getPatientId());
        logger.debug("Requested Patient is [{}-{}]", patient.getId(), patient.getFullName());
        if (params.getPolicy().getTypeCode().isEmpty()) {
            logger.error("End of #{}. No such rbPolicyType(policyTypeCode is empty).", currentRequestNum);
            throw new PolicyTypeNotFoundException(
                    "В БД ЛПУ не содержится типов полисов с таким кодом(пустое значение)",
                    17
            );
        }
        final RbPolicyType requestedType = policyTypeBean.findByCode(params.getPolicy().getTypeCode());
        if (requestedType == null) {
            logger.error("End of #{}. No such rbPolicyType(code={}).", currentRequestNum, params.getPolicy().getTypeCode());
            throw new PolicyTypeNotFoundException(
                    "В БД ЛПУ не содержится типов полисов с таким кодом("
                            .concat(params.getPolicy().getTypeCode()).concat(")"),
                    17
            );
        }
        int deletedPolicyCount = policyBean.deleteAllClientPoliciesByType(patient.getId(), requestedType.getCode());
        logger.debug("Delete {} previous policies.", deletedPolicyCount);
        //страховщик
        Organisation insurer = null;
        if (params.getPolicy().isSetInsurerInfisCode() && !params.getPolicy().getInsurerInfisCode().isEmpty()) {
            try {
                insurer = organisationBean.getOrganizationByInfisCode(params.getPolicy().getInsurerInfisCode());
            } catch (CoreException e) {
                logger.warn("Couldn't find organisation with InfisCode=\"{}\"", params.getPolicy().getInsurerInfisCode());
            }
        }
        try {
            final ClientPolicy policy = policyBean.insertOrUpdateClientPolicy(
                    0,
                    requestedType.getId(),
                    insurer != null ? insurer.getId() : 0,
                    params.getPolicy().getNumber(),
                    params.getPolicy().isSetSerial() ? params.getPolicy().getSerial() : "",
                    new Date(),
                    null,
                    "",
                    "Данные из ТФОМС",
                    patient,
                    null);
            final ClientPolicy persistedPolicy = policyBean.persistNewPolicy(policy);
            logger.debug("Persisted policy[{}]", persistedPolicy.getId());
            return true;
        } catch (CoreException e) {
            logger.error("End of #{} CommunicationServer.changePatientPolicy. Exception while forming new policy", e);
            return false;
        }
    }


    /**
     * Запрос на список талончиков, которые появились с момента последнего запроса
     * (для поиска записей на прием к врачу созданных не через КС)
     *
     * @return Список новых талончиков или пустой список, если таких талончиков не найдено то пустой список
     */
    @Override
    public List<QueueCoupon> checkForNewQueueCoupons() throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.checkForNewQueueCoupons()", currentRequestNum);
        final List<QueueCoupon> result = new ArrayList<QueueCoupon>();
        final List<QueueTicket> databaseChangeList = queueTicketBean.pullDatabase();
        //        Если изменений нету
        if (databaseChangeList.isEmpty()) {
            logger.info("End of #{} checkForNewQueueCoupons. Empty changelist", currentRequestNum);
            return result;
        } else if (logger.isDebugEnabled()) {
            logger.debug("Database changeList size={}. DATA:", databaseChangeList.size());
            for (QueueTicket currentTicket : databaseChangeList) {
                logger.debug(currentTicket.getInfoString());
            }
        }
        for (QueueTicket currentQueueCoupon : databaseChangeList) {
            result.add(ParserToThriftStruct.parseQueueCoupon(currentQueueCoupon));
            queueTicketBean.changeStatus(currentQueueCoupon, QueueTicket.Status.SENDED);
        }
        logger.info("End of #{} checkForNewQueueCoupons. Result is {}", currentRequestNum, result);
        return result;
    }

    /**
     * Метод для получения первого свободного талончика врача
     *
     * @param params 1) Параметры для поиска первого свободого талончика
     * @return Структура с данными первого доступного для записи талончика
     * @throws NotFoundException когда у выбранного врача с этой даты нету свободных талончиков, или нету такого врача
     */
    @Override
    public TTicket getFirstFreeTicket(final ScheduleParameters params) throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method ->CommunicationServer.getFirstFreeTicket({})", currentRequestNum, params);
        final Staff person = CommunicationHelper.findPersonById(staffBean, params.getPersonId());
        logger.debug("Requested person is {}", person.getInfoString());
        final LocalDateTime startDateTime = DateConvertions.convertUTCMillisecondsToLocalDateTime(params.getBeginDateTime());
        final LocalDateTime endDateTime = (params.isSetEndDateTime() && params.getBeginDateTime() < params.getEndDateTime()) ? DateConvertions.convertUTCMillisecondsToLocalDateTime(params.getEndDateTime()) : startDateTime.plusMonths(1);
        logger.info("Date interval: FROM [{}] TO [{}]", dateTimeformatter.print(startDateTime), dateTimeformatter.print(endDateTime));
        // Группируем расписания по дате
        long startTime = System.currentTimeMillis();
        final List<ru.korus.tmis.schedule.PersonSchedule> scheduleList = scheduleBean.getPersonScheduleOnDateInterval(person, startDateTime, endDateTime);
        logger.debug("Select & Group Schedules by Date in {} millis", System.currentTimeMillis() - startTime);
        final RbTimeQuotingType quotingType = CommunicationHelper.getQuotingType(params);
        logger.debug(quotingType.toString());
        for (ru.korus.tmis.schedule.PersonSchedule current : scheduleList) {
            logger.debug("Start of [{}]", dateformatter.print(current.getScheduleDate()));
            final List<QuotingByTime> quotingList = scheduleBean.getQuotingByTimeToPersonAndDateAndType(
                    person,
                    current.getScheduleDate(),
                    quotingType
            );
            if (!quotingList.isEmpty()) {
                logger.debug("Quoting By Time");
                current.takeQuotingByTimeConstraintsToTickets(quotingList);
            } else {
                logger.debug("Quoting By Person");
                current.takePersonQuotingConstraintsToTickets(quotingType);
            }
            if (params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty() && person.getSpeciality() != null) {
                logger.debug("Quoting by Speciality[{}]", person.getSpeciality().getId());
                if (person.getSpeciality().getQuotingEnabled() != null && !person.getSpeciality().getQuotingEnabled()) {
                    logger.debug("rbSpeciality[{}] has quotingEnabled=0. Skip QuotingBySpeciality.", person.getSpeciality().getId());
                } else {
                    current.takeQuotingBySpecialityConstraintsToTickets(quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(person.getSpeciality(), params.getHospitalUidFrom()));
                }
            }
            final ScheduleTicket firstFreeTicket = current.getFirstFreeTicketAfterDateTime(startDateTime);
            if (firstFreeTicket != null) {
                logger.info("ScheduleTicket[{}] [{} - {}] is firstFreeTicket.", firstFreeTicket.getId(), firstFreeTicket.getBegTime(), firstFreeTicket.getEndTime());
                final TTicket result = ParserToThriftStruct.parseTTicket(firstFreeTicket);
                logger.info("End of #{} getFirstFreeTicket. Result is {}", currentRequestNum, result);
                return result;
            }
        }
        logger.info("End of #{} getFirstFreeTicket. No one is founded", currentRequestNum);
        throw new NotFoundException().setError_msg(CommunicationErrors.msgNoFreeTicket.getMessage());
    }

    /**
     * Метод для получения расписания врача пачкой за указанный интервал
     *
     * @param params 1) Параметры для получения расписания
     * @return структура данных с информацией о примемах врача
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда нету такого идентификатора врача
     */
    @Override
    public PersonSchedule getPersonSchedule(final ScheduleParameters params) throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPersonSchedule({})", currentRequestNum, params);
        final Staff person = CommunicationHelper.findPersonById(staffBean, params.getPersonId());
        logger.debug("Requested person is {}", person.getInfoString());
        final LocalDateTime startDateTime = DateConvertions.convertUTCMillisecondsToLocalDateTime(params.getBeginDateTime());
        final LocalDateTime endDateTime = (params.isSetEndDateTime() && params.getBeginDateTime() < params.getEndDateTime()) ? DateConvertions.convertUTCMillisecondsToLocalDateTime(params.getEndDateTime()) : startDateTime.plusMonths(1);
        logger.info("Date interval: FROM [{}] TO [{}]", dateTimeformatter.print(startDateTime), dateTimeformatter.print(endDateTime));
        // Группируем расписания по дате
        long startTime = System.currentTimeMillis();
        final List<ru.korus.tmis.schedule.PersonSchedule> personScheduleList = scheduleBean.getPersonScheduleOnDateInterval(person, startDateTime, endDateTime);
        logger.debug("Select & Group Schedules by Date in {} millis", System.currentTimeMillis() - startTime);
        final PersonSchedule result = new PersonSchedule();
        result.setSchedules(new LinkedHashMap<Long, ru.korus.tmis.communication.thriftgen.Schedule>(personScheduleList.size()));
        final RbTimeQuotingType quotingType = CommunicationHelper.getQuotingType(params);
        for (ru.korus.tmis.schedule.PersonSchedule current : personScheduleList) {
            logger.debug("Start of [{}]", dateformatter.print(current.getScheduleDate()));
            final long key = DateConvertions.convertLocalDateToUTCMilliseconds(current.getScheduleDate());
            //Квоты по времени для нужного типа
            final List<QuotingByTime> quotingList = scheduleBean.getQuotingByTimeToPersonAndDateAndType(
                    person,
                    current.getScheduleDate(),
                    quotingType
            );
            if (!quotingList.isEmpty()) {
                logger.debug("Quoting By Time");
                current.takeQuotingByTimeConstraintsToTickets(quotingList);
            } else {
                logger.debug("Quoting By Person");
                current.takePersonQuotingConstraintsToTickets(quotingType);
            }
            if (params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty() && person.getSpeciality() != null) {
                logger.debug("Quoting by Speciality[{}]", person.getSpeciality().getId());
                if (person.getSpeciality().getQuotingEnabled() != null && !person.getSpeciality().getQuotingEnabled()) {
                    logger.debug("rbSpeciality[{}] has quotingEnabled=0. Skip QuotingBySpeciality.", person.getSpeciality().getId());
                } else {
                    current.takeQuotingBySpecialityConstraintsToTickets(quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(person.getSpeciality(), params.getHospitalUidFrom()));
                }
            }
            //Формирование расписания
            result.putToSchedules(key, ParserToThriftStruct.parsePersonSchedule(current));
        }
        logger.info("End of #{} CommunicationServer.getPersonSchedule.", currentRequestNum);
        if (logger.isDebugEnabled()) {
            logger.debug("#{} CommunicationServer.getPersonSchedule result is \n {}", currentRequestNum, result);
        }
        return result;
    }

    /**
     * * Получение детальной информации по пациентам по их идентфикаторам
     * * @param patientIds                    1) Список идентификаторов пациентов
     * * @return                              map<int, PatientInfo> - карта вида <[Идетификатор пациента], [Информация о пациенте]>,
     * в случае отсутвия идентификатора в БД ЛПУ набор ключ-значение опускается
     * * @throws SQLException                 когда произошла внутренняя ошибка при запросах к БД ЛПУ
     *
     * @param patientIds Список идентификаторов пациентов
     */
    @Override
    public Map<Integer, Patient> getPatientInfo(final List<Integer> patientIds) throws SQLException, TException {
        //Логика работы: по всему полученному массиву вызвать getByID у бина,
        // если нет одного из пациентов, то вернуть всех кроме него.
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPatientInfo({}) total size={}",
                currentRequestNum, patientIds, patientIds.size());
        final Map<Integer, ru.korus.tmis.communication.thriftgen.Patient> resultMap = new HashMap<Integer, ru.korus.tmis.communication.thriftgen.Patient>(patientIds.size());
        for (Integer current : patientIds) {
            if (current != null) {
                try {
                    final ru.korus.tmis.core.entity.model.Patient requested = patientBean.getPatientById(current);
                    if (requested != null) {
                        resultMap.put(current, ParserToThriftStruct.parsePatient(requested));
                        logger.debug("Add patient ID={}, FIO={}", requested.getId(), requested.getFullName());
                    }
                } catch (CoreException e) {
                    logger.warn("Missing patient with ID={}, No such patient in DB.", current);
                }
            }
        }
        logger.info("End of #{} getPatientInfo. Return (Size={}), DATA={})", currentRequestNum, resultMap.size(), resultMap);
        return resultMap;
    }

    /**
     * Получение контактной информации для заданного пациента
     *
     * @param patientId@return Список структур с контактной информацией
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда пациент не был найден по идентифкатору
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Contact> getPatientContacts(final int patientId) throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPatientContacts(patientId={})", currentRequestNum, patientId);
        final ru.korus.tmis.core.entity.model.Patient patient = CommunicationHelper.findPatientById(patientBean, patientId);
        logger.debug("Requested Patient is [{}-{}]", patient.getId(), patient.getFullName());
        final List<Contact> resultList = new ArrayList<Contact>();
        for (ClientContact current : patient.getActiveClientContacts()) {
            resultList.add(ParserToThriftStruct.parseContact(current));
        }
        logger.info("End of #{} getPatientContacts. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * НЕ РЕАЛИЗОВАНО
     *
     * @param parentId
     */
    @Override
    public List<OrgStructuresProperties> getPatientOrgStructures(final int parentId)
            throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPatientOrgStructures(parentId={})", currentRequestNum, parentId);
        final List<OrgStructuresProperties> resultList = new ArrayList<OrgStructuresProperties>(0);
        logger.info("End of #{} getPatientOrgStructures. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    /**
     * Запись пациента на прием к врачу
     *
     * @param params 1) Структура с параметрами для  записи на прием к врачу
     * @return Структура с данными о статусе записи пациента на прием к врачу
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException //TODO
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public EnqueuePatientStatus enqueuePatient(final EnqueuePatientParameters params) throws NotFoundException, SQLException, ReasonOfAbsenceException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.enqueuePatient({})", currentRequestNum, params);
        final ru.korus.tmis.core.entity.model.Patient patient = CommunicationHelper.findPatientById(patientBean, params.getPatientId());
        logger.debug("Requested Patient is [{}-{}]", patient.getId(), patient.getFullName());
        final Staff person = CommunicationHelper.findPersonById(staffBean, params.getPersonId());
        logger.debug("Requested person is {}", person.getInfoString());
        final ru.korus.tmis.core.entity.model.Speciality personSpeciality = person.getSpeciality();
        //Проверка ограничений специальности врача
        if (personSpeciality != null && !CommunicationHelper.checkApplicable(patient, personSpeciality)) {
            logger.warn("Person speciality is not applicable to patient.");
            return new EnqueuePatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgEnqueueNotApplicable.getMessage());
        }
        final LocalDateTime dateTime = new LocalDateTime(params.getDateTime(), DateTimeZone.UTC);
        final LocalTime time = dateTime.toLocalTime();
        logger.debug("DateTime is {}", dateTimeformatter.print(dateTime));
        //Получаем все неудаленные расписания врача на дату (время будет откинуто внутри метода)
        final List<Schedule> scheduleList = scheduleBean.getScheduleOnDateAndPerson(dateTime.toDate(), person);
        if (scheduleList.isEmpty()) {
            logger.info("End of #{}. Person[{}] hasn't any schedule at this date", currentRequestNum, person.getId());
            throw new NotFoundException(CommunicationErrors.msgQueueNotFound.getMessage());
        } else if (logger.isDebugEnabled()) {
            logger.debug("Founded {} schedules. Data:", scheduleList.size());
            for (Schedule currentSchedule : scheduleList) {
                logger.debug(currentSchedule.toString());
            }
        }
        //Сливаем все расписания на день в одно
        final ru.korus.tmis.schedule.PersonSchedule schedule = new ru.korus.tmis.schedule.PersonSchedule(dateTime.toLocalDate(), person, scheduleList);
        final RbTimeQuotingType quotingType = CommunicationHelper.getQuotingType(params);
        //Квоты по времени для нужного типа
        final List<QuotingByTime> quotingByTimeList = scheduleBean.getQuotingByTimeToPersonAndDateAndType(
                person,
                schedule.getScheduleDate(),
                quotingType
        );
        if (!quotingByTimeList.isEmpty()) {
            logger.debug("Quoting By Time");
            schedule.takeQuotingByTimeConstraintsToTickets(quotingByTimeList);
        } else {
            logger.debug("Quoting By Person");
            schedule.takePersonQuotingConstraintsToTickets(quotingType);
        }
        //Список квот по специальности
        List<QuotingBySpeciality> quotingBySpecialityList = new ArrayList<QuotingBySpeciality>(1);
        //Организация из которой будет производиться запись
        Organisation organisation = null;
        if (params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty() && person.getSpeciality() != null) {
            logger.debug("Quoting by Speciality[{}]", person.getSpeciality().getId());
            if (person.getSpeciality().getQuotingEnabled() != null && !person.getSpeciality().getQuotingEnabled()) {
                logger.debug("rbSpeciality[{}] has quotingEnabled=0. Skip QuotingBySpeciality.", person.getSpeciality().getId());
            } else {
                try {
                    organisation = organisationBean.getOrganizationByInfisCode(params.getHospitalUidFrom());
                    quotingBySpecialityList = quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(person.getSpeciality(), organisation);
                    schedule.takeQuotingBySpecialityConstraintsToTickets(quotingBySpecialityList);
                } catch (CoreException e) {
                    logger.warn("Organisation not founded by infisCode[{}]", params.getHospitalUidFrom());
                    organisation = null;
                }
            }
        }
        //Ищем талон с нужным временем
        final ScheduleTicketExtended ticketExtended = schedule.getTicketToTime(time);
        if (ticketExtended != null) {
            final ScheduleTicket ticket = ticketExtended.getTicket();
            logger.info("Requested Ticket: {}", ticket);
            if (!ticketExtended.isAvailable()) {
                //Талон не доступен для записи
                logger.info("End of #{}. Ticket is not available", currentRequestNum);
                return new EnqueuePatientStatus(false)
                        .setMessage(CommunicationErrors.msgTicketNotFound.getMessage());
            }
            //Проверка на то, чтобы не было двух записей одного и того-же пацеинта на один прием того-же врача (день)
            if (schedule.checkRepetitionEnqueue(patient)) {
                logger.info("Detected repetition enqueue to this doctor. Cancelling enqueue.");
                return new EnqueuePatientStatus(false).setMessage(CommunicationErrors.msgPatientAlreadyQueued.getMessage());
            }
            //Проверка на то, чтобы не было двух записей одного и того-же пацеинта на одно и то-же время (к разным врачам)
            if (patientQueueBean.checkPatientQueueByDateTime(
                    patient,
                    schedule.getScheduleDate().toDate(),
                    ticket.getBegTime(),
                    ticket.getEndTime()
            )) {
                logger.info("Detected repetition enqueue to another doctor by same time. Cancelling enqueue.");
                return new EnqueuePatientStatus(false).setMessage(CommunicationErrors.msgPatientAlreadyQueuedToTime.getMessage());
            }
            //запрещать другому потоку выполнять проверку до того как блок будет отработан первым потоком
            final ScheduleClientTicket clientTicket;
            synchronized (this) {
                //Талон занят
                if (!ticket.isFree()) {
                    logger.debug("End of #{}. Ticket[{}] is busy", currentRequestNum, ticket.getId());
                    return new EnqueuePatientStatus(false).setMessage(CommunicationErrors.msgTicketIsBusy.getMessage());
                }
                //Уменьшение квот по специальности
                if (organisation != null && !quotingBySpecialityList.isEmpty()) {
                    quotingBySpecialityBean.decrementRemainingCoupons(quotingBySpecialityList.iterator().next());
                }
                clientTicket = scheduleBean.enqueuePatientToScheduleTicket(ticket, patient, params.getNote(), organisation);
            }
            logger.debug("Enqueue is successful: {}", clientTicket);
            logger.info("End of #{}. Return [{}] as result", currentRequestNum, clientTicket.getId());
            return new EnqueuePatientStatus(true).setMessage(CommunicationErrors.msgOk.getMessage())
                    .setQueueId(clientTicket.getId());

        }
        logger.info("End of #{}. Person[{}] hasn't any ticket at this dateTime", currentRequestNum, person.getId());
        return new EnqueuePatientStatus(false)
                .setMessage(CommunicationErrors.msgTicketNotFound.getMessage());
    }

    /**
     * Получение списка записей на приемы к врачам заданного пациента
     *
     * @param patientId 1) Идентификатор пациента
     * @return Список структура с данными о записях пациента на приемы к врачам
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда пациент не был найден
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public List<Queue> getPatientQueue(final int patientId) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getPatientQueue(patientId={})", currentRequestNum, patientId);
        final ru.korus.tmis.core.entity.model.Patient patient = CommunicationHelper.findPatientById(patientBean, patientId);
        logger.debug("Requested Patient is [{}-{}]", patient.getId(), patient.getFullName());
        final List<ScheduleClientTicket> patientTickets = scheduleBean.getScheduleClientTicketsForPatient(patient);
        if (logger.isDebugEnabled()) {
            for (ScheduleClientTicket currentTicket : patientTickets) {
                logger.debug("[{}] to ticket [{}]", currentTicket.getId(), currentTicket.getTicket().getId());
            }
        }
        final List<Queue> resultList = new ArrayList<Queue>(patientTickets.size());
        final Date currentServerDate = new LocalDate().toDate();
        for (ScheduleClientTicket currentTicket : patientTickets) {
            final Date currentTicketDate = currentTicket.getTicket().getSchedule().getDate();
            if (currentTicketDate.equals(currentServerDate) || currentTicketDate.after(currentServerDate)) {
                resultList.add(ParserToThriftStruct.parseQueue(currentTicket));
            } else {
                logger.debug("ScheduleClientTicket[{}] is before CURDATE. skip it.", currentTicket.getId());
            }
        }
        logger.info("End of #{}. Return {} tickets.", currentRequestNum, resultList.size());
        if (logger.isDebugEnabled()) {
            for (Queue current : resultList) {
                logger.debug(current.toString());
            }
        }
        return resultList;
    }

    /**
     * Отмена записи пациента на прием к врачу
     *
     * @param patientId 1) Идентификатор пациента
     * @param queueId   2) Идентификатор записи, которую необходимо отменить
     * @return Структура с данными о статусе отмены записи пациента на прием к врачу
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда нету такого пациента в БД
     * @throws ru.korus.tmis.communication.thriftgen.SQLException      когда произошла внутренняя ошибка при запросах к БД ЛПУ
     */
    @Override
    public DequeuePatientStatus dequeuePatient(final int patientId, final int queueId) throws NotFoundException, SQLException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.dequeuePatient(patientId={}, queueId={})", currentRequestNum, patientId, queueId);
        final ru.korus.tmis.core.entity.model.Patient patient = CommunicationHelper.findPatientById(patientBean, patientId);
        logger.debug("Requested Patient is [{}-{}]", patient.getId(), patient.getFullName());
        final ScheduleClientTicket dequeueTicket = scheduleBean.getScheduleClientTicketById(queueId);
        logger.info("Requested ticket is {}", dequeueTicket);
        if (dequeueTicket.isDeleted()) {
            logger.info("End of #{}. ScheduleClientTicket[{}] is already deleted", currentRequestNum, dequeueTicket.getId());
            return new DequeuePatientStatus(false).setMessage(CommunicationErrors.msgPatientQueueNotFound.getMessage());
        }
        if (patient.equals(dequeueTicket.getClient())) {
            final List<QuotingBySpeciality> toIncrement = quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(
                    dequeueTicket.getTicket().getSchedule().getPerson().getSpeciality(),
                    dequeueTicket.getInfisFrom()
            );
            if (!toIncrement.isEmpty()) {
                logger.info("Increment QuotingBySpeciality");
                quotingBySpecialityBean.incrementRemainingCoupons(toIncrement.iterator().next());
            }
            boolean success = scheduleBean.dequeueScheduleClientTicket(dequeueTicket);
            if (success) {
                logger.info("End of #{}. ScheduleClientTicket[{}] is successfully cancelled", currentRequestNum, dequeueTicket.getId());
                return new DequeuePatientStatus(true).setMessage(CommunicationErrors.msgOk.getMessage());
            } else {
                logger.info("End of #{}. ERROR something going wrong", currentRequestNum);
                return new DequeuePatientStatus(false).setMessage(CommunicationErrors.msgUnknownError.getMessage());
            }
        } else {
            logger.info("End of #{}. ScheduleClientTicket[{}] has another owner[{}-{}]",
                    currentRequestNum,
                    dequeueTicket.getId(),
                    dequeueTicket.getClient().getId(),
                    dequeueTicket.getClient().getFullName()
            );
            return new DequeuePatientStatus(false).setMessage(CommunicationErrors.msgTicketIsNotBelongToPatient.getMessage());
        }
    }

    /**
     * Получение списка  с информацией о специализациях и доступных талончиках
     *
     * @param hospitalUidFrom 1) Инфис-код ЛПУ
     * @return Список структур с данными о специализациях врачей
     * @throws ru.korus.tmis.communication.thriftgen.NotFoundException когда ничего не найдено
     */
    @Override
    public List<Speciality> getSpecialities(final String hospitalUidFrom) throws NotFoundException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommunicationServer.getSpecialities({})", currentRequestNum, hospitalUidFrom);
        //Неквотируемые специальности не передаются
        //final List<ru.korus.tmis.core.entity.model.Speciality> unquotedSpecialityList = quotingBySpecialityBean.getUnquotedSpecialities();
        final List<QuotingBySpeciality> quotingBySpecialityList = quotingBySpecialityBean.getQuotingByOrganisation(hospitalUidFrom);
        List<Speciality> resultList = new ArrayList<Speciality>(quotingBySpecialityList.size());
        for (QuotingBySpeciality item : quotingBySpecialityList) {
            logger.debug("QuotingBySpeciality[{}]", item.getId());
            if (item.getSpeciality().getQuotingEnabled() != null && !item.getSpeciality().getQuotingEnabled()) {
                logger.warn("Speciality[{}] has quotingEnabled=0. Skip it.", item.getSpeciality().getId());
            } else {
                resultList.add(ParserToThriftStruct.parseSpeciality(item));
            }
        }
        //Неквотируемые специальности не передаются
        //for(ru.korus.tmis.core.entity.model.Speciality item : unquotedSpecialityList){
        //logger.debug("Speciality[{}] is unquoted", item.getId());
        //resultList.add(ParserToThriftStruct.parseSpeciality(item));
        //}
        if (resultList.isEmpty()) {
            logger.error("End of #{}. No one speciality found.", currentRequestNum);
            throw new NotFoundException().setError_msg(CommunicationErrors.msgItemNotFound.getMessage());
        }
        logger.info("End of #{} getSpecialities. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    public static CommunicationServer getInstance() {
        if (instance == null) {
            instance = new CommunicationServer();
        }
        return instance;
    }

    public void startService() {
        communicationListener.start();
    }

    public CommunicationServer() {
        logger.info("Starting CommunicationServer initialize.");
        communicationListener = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread
                try {
                    serverTransport = new TServerSocket(PORT_NUMBER);
                    Communications.Processor<CommunicationServer> processor = new Communications.Processor<CommunicationServer>(getInstance());
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .processor(processor)
                            .maxWorkerThreads(MAX_WORKER_THREADS)
                            .minWorkerThreads(MIN_WORKER_THREADS)
                    );
                    logger.info("Starting CommunicationServer on port {}", PORT_NUMBER);
                    server.serve();
                } catch (Exception e) {
                    logger.error("Failed to start CommunicationServer on port {}.", PORT_NUMBER, e);
                }
            }
        });
        communicationListener.setName("Communication-service-thread");
        communicationListener.setPriority(SERVER_THREAD_PRIORITY);
        communicationListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }

    public static void setPatientBean(final DbPatientBeanLocal patientBean) {
        CommunicationServer.patientBean = patientBean;
    }

    public static void setOrgStructureBean(final DbOrgStructureBeanLocal dbOrgStructureBeanLocal) {
        CommunicationServer.orgStructureBean = dbOrgStructureBeanLocal;
    }

    public static void setStaffBean(final DbStaffBeanLocal staffBean) {
        CommunicationServer.staffBean = staffBean;
    }

    public static void setSpecialityBean(final DbQuotingBySpecialityBeanLocal dbQuotingBySpecialityBeanLocal) {
        CommunicationServer.quotingBySpecialityBean = dbQuotingBySpecialityBeanLocal;
    }

    public static void setOrganisationBean(final DbOrganizationBeanLocal organisationBean) {
        CommunicationServer.organisationBean = organisationBean;
    }

    public static void setDocumentBean(DbClientDocumentBeanLocal documentBean) {
        CommunicationServer.documentBean = documentBean;
    }

    public static void setDocumentTypeBean(DbRbDocumentTypeBeanLocal documentTypeBean) {
        CommunicationServer.documentTypeBean = documentTypeBean;
    }

    public static void setPolicyBean(DbClientPolicyBeanLocal policyBean) {
        CommunicationServer.policyBean = policyBean;
    }

    public static void setPolicyTypeBean(DbRbPolicyTypeBeanLocal policyTypeBean) {
        CommunicationServer.policyTypeBean = policyTypeBean;
    }

    public static void setQueueTicketBean(EPGUTicketBeanLocal queueTicketBean) {
        CommunicationServer.queueTicketBean = queueTicketBean;
    }

    public static void setPatientQueueBean(PatientQueueBeanLocal patientQueueBean) {
        CommunicationServer.patientQueueBean = patientQueueBean;
    }

    public static void setScheduleBean(ScheduleBeanLocal scheduleBean) {
        CommunicationServer.scheduleBean = scheduleBean;
    }


    public void endWork() {
        logger.warn("CommunicationServer start closing");
        logger.info("Total request served={}", requestNum);
        server.stop();
        logger.warn("Server stopped.");
        serverTransport.close();
        logger.warn("Transport closed.");
        communicationListener.interrupt();
        if (communicationListener.isInterrupted()) {
            logger.warn("ServerThread is interrupted successfully");
            logger.debug("He is dead! So we know this thread as a very good thread, he process requests well, works all day long from sunrise to sunset. Rest in peace.");
        }
        if (communicationListener.isAlive()) {
            try {
                logger.error("Wait for a second to Thread interrupt");
                communicationListener.join(1000);
            } catch (InterruptedException e) {
                logger.error("He is dead! So we know this thread as a very good thread, he process requests well, works all day long from sunrise to sunset. Rest in peace.");
            }
            if (communicationListener.isAlive()) {
                logger.error("ServerThread is STILL ALIVE?! Setting MinimalPriority to the Thread");
                communicationListener.setPriority(Thread.MIN_PRIORITY);
            }
        }
        logger.info("All fully stopped.");
    }

}
