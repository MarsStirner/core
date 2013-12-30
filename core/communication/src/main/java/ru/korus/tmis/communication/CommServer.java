package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Address;
import ru.korus.tmis.communication.thriftgen.OrgStructure;
import ru.korus.tmis.communication.thriftgen.Queue;
import ru.korus.tmis.communication.thriftgen.Speciality;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.epgu.EPGUTicketBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.communication.QueueTicket;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.NoSuchPatientException;

import javax.ejb.EJBException;
import java.util.*;

/**
 * Author:      Upatov Egor
 * Date:        17.12.12 at 14:55
 * Company:     Korus Consulting IT
 * Description: Класс содержит в себе ссылки на EJB с помощью которых тянет инфу из базы и реализует методы, сгенеренные thrift.
 * Также методом startService запускается сервер, который слушает с заданного порта в отдельном потоке.
 */

public class CommServer implements Communications.Iface {
    //Logger
    private static final Logger logger = LoggerFactory.getLogger(CommServer.class);
    //Beans
    private static DbOrgStructureBeanLocal orgStructureBean = null;
    private static DbPatientBeanLocal patientBean = null;
    private static DbStaffBeanLocal staffBean = null;
    private static DbQuotingBySpecialityBeanLocal quotingBySpecialityBean = null;
    private static DbOrganizationBeanLocal organisationBean = null;
    private static DbActionPropertyBeanLocal actionPropertyBean = null;
    private static DbQuotingByTimeBeanLocal quotingByTimeBean = null;
    private static DbActionBeanLocal actionBean = null;
    private static DbManagerBeanLocal managerBean = null;
    private static DbEventBeanLocal eventBean = null;
    //////////////////////////////////////////////////////////
    private static DbClientDocumentBeanLocal documentBean = null;
    private static DbRbDocumentTypeBeanLocal documentTypeBean = null;
    private static DbClientPolicyBeanLocal policyBean = null;
    private static DbRbPolicyTypeBeanLocal policyTypeBean = null;
    //////////////////////////////////////////////////////////
    private static EPGUTicketBeanLocal queueTicketBean = null;
    //////////////////////////////////////////////////////////
    private static PatientQueueBeanLocal patientQueueBean = null;

    //Singleton instance
    private static CommServer instance;
    private static TServer server;
    private static TServerSocket serverTransport;
    //THREAD properties
    //Listener thread
    private Thread communicationListener = null;
    //Listener port
    private static final int PORT_NUMBER = 7911;
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.MIN_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;
    //MAXimum work threads in threadpool
    private static final int MAX_WORKER_THREADS = 255;
    //Number of request
    private static int requestNum = 0;


    /**
     * Получение оргструктур, которые входят в заданное подразделение.
     * При установленном флаге рекурсии выводит все подразделения которые принадлежат запрошенному.
     *
     * @param parentId  Подразделение для которого необходимо найти, входящие в него, подразделения.
     * @param recursive Флаг рекурсии.
     * @return Список подразделений
     * @throws TException
     */
    @Override
    public List<OrgStructure> getOrgStructures(final int parentId, final boolean recursive, final String infisCode)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getOrgStructures(id={}, recursive={}, infisCode={})",
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
            throw new TException("Error while getRecursive from bean (Unknown exception)", e);
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
     * Получение ID подразделений, находящихся по заданному адресу.
     *
     * @param params Адрес ЛПУ
     * @return Список ID подразделений
     * @throws NotFoundException
     * @throws SQLException
     * @throws TException
     */
    @Override
    public List<Integer> findOrgStructureByAddress(final FindOrgStructureByAddressParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.findOrgStructureByAddress(streetKLADR={}, pointKLADR={}, number={}/{} flat={})",
                currentRequestNum, params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        final List<Integer> resultList;
        try {
            resultList = orgStructureBean.getOrgStructureByAddress(
                    params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " CoreException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("No one OrgStructure found.");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
        }
        logger.info("End of #{} findOrgStructureByAddress. Return (size={} DATA=({})) as result.",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение списка работников заданной оргструктуры
     *
     * @param orgStructureId Оргструктура, в которой ищем работников
     * @param recursive      флаг рекурсии (при true- выборка работников еще и из подчиненных оргструктур)
     * @param infisCode      инфис-код организации
     * @return Список работников
     * @throws TException
     */
    @Override
    public List<Person> getPersonnel(final int orgStructureId, final boolean recursive, final String infisCode)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPersonnel(OrgStructureId={}, recursive={}, infisCode={})",
                currentRequestNum, orgStructureId, recursive, infisCode);
        final List<Staff> personnelList;
        try {
            personnelList = orgStructureBean.getPersonnel(orgStructureId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " CoreException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("No one Person related with this orgStructures (COREException)");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
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

    //TODO Не реализовано
    @Override
    public TicketsAvailability getTotalTicketsAvailability(final GetTicketsAvailabilityParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getTotalTicketsAvailability({})", currentRequestNum, params);

        final TicketsAvailability result = null;
        logger.info("End of #{} getTotalTicketsAvailability. Return \"({})\" as result.", currentRequestNum, result);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    //TODO Не реализовано
    @Override
    public List<ExtendedTicketsAvailability> getTicketsAvailability(final GetTicketsAvailabilityParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getTicketsAvailability({})", currentRequestNum, params);

        final List<ExtendedTicketsAvailability> result = new ArrayList<ExtendedTicketsAvailability>(0);
        logger.info("End of #{} getTicketsAvailability. Return (Size={}), DATA={})", currentRequestNum, result.size(), result);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    /**
     * Получение расписания врача и информации о талончиках
     *
     * @param params Структура, содержащая:{id врача, дата на которую берется расписание, id ЛПУ}
     * @return Расписание врача
     * @throws TException
     */
    @Override
    @Deprecated
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getWorkTimeAndStatus({})", currentRequestNum, params);
        if (!params.isSetDate()) {
            params.setDate(new DateMidnight(DateTimeZone.UTC).getMillis());
        }
        final Date paramsDate = DateConvertions.convertUTCMillisecondsToLocalDate(params.getDate());
        logger.debug("Readable date: {}", paramsDate);
        Action personAction = null;
        //Доктор для которого получаем расписание
        Staff doctor = null;
        try {
            doctor = staffBean.getStaffById(params.getPersonId());
            //1. Получаем actionId по id врача (personId) и дате(date)
            personAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), paramsDate, "amb");
        } catch (CoreException e) {
            if (doctor == null) {
                logger.error("#" + currentRequestNum + " Doctor not found by ID=" + params.getPersonId(), e);
                throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
            }
            logger.error("End of #" + currentRequestNum + " Exception while getting actions for PersonID=" + params.getPersonId());
            throw new NotFoundException()
                    .setError_msg("Error during the preparation of action associated with inspection by the doctor. Doctor ID ="
                            + params.getPersonId());
        } catch (EJBException e) {
            logger.error("End of #" + currentRequestNum + " Doctor not found by ID=" + params.getPersonId(), e);
            throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
        }

        final PersonSchedule currentSchedule = new PersonSchedule(doctor, personAction);
        final RbReasonOfAbsence reasonOfAbsence = currentSchedule.checkReasonOfAbscence();
        if (reasonOfAbsence != null) {
            logger.info("End of #{}. Doctor has ReasonOfAbsence.", currentRequestNum);
            throw new ReasonOfAbsenceException().setCode(reasonOfAbsence.getCode()).setName(reasonOfAbsence.getName());
        }
        try {
            currentSchedule.formTickets();
        } catch (CoreException e) {
            logger.error("End of #{}. Exception while forming tickets: {}", currentRequestNum, e);
            throw new NotFoundException().setError_msg("Doctor Schedule is broken");
        }
        //TODO uncomment to NTK version after 3.3.23 inlusive
//        if(params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty()){
//            currentSchedule.checkQuotingBySpeciality(params.hospitalUidFrom);
//        }
        currentSchedule.takeConstraintsOnTickets(CommunicationHelper.getQuotingType(params));
        final Amb result = ParserToThriftStruct.parsePersonScheduleToAmb(currentSchedule);
        logger.info("End of #{} TimeAndStatus. Return \"{}\" as result.",
                currentRequestNum, result);
        return result;
    }


    /**
     * Добавление указанного пациента
     *
     * @param params ФИО, Дата рождения, Пол
     * @return Статус попытки добавления пациента
     * @throws TException
     */
    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.addPatient( {} )", currentRequestNum, params );
        final PatientStatus result = new PatientStatus();
        //CHECK PARAMS
        if (!CommunicationHelper.checkAddPatientParams(params, result)) {
            logger.warn("End of #{} addPatient. Error message=\"{}\"", currentRequestNum, result.getMessage());
            return result.setSuccess(false).setPatientId(0);
        }
        final ru.korus.tmis.core.entity.model.Patient patient;
        try {
            patient = patientBean.insertOrUpdatePatient(0, params.firstName, params.patrName, params.lastName,
                    DateConvertions.convertUTCMillisecondsToLocalDate(params.getBirthDate()), "",
                    CommunicationHelper.getSexAsString(params.getSex()), "0", "0", "", null, 0, "", "", null, 0);
            patientBean.savePatientToDataBase(patient);
            logger.debug("Patient ={}", patient);
            if (patient.getId() == 0 || patient.getId() == null) {
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
     * Поиск одного пациента по заданным параметрам
     *
     * @param params Параметры поиска
     * @return Статус нахождения пациента
     * @throws TException
     */
    @Override
    public PatientStatus findPatient(final FindPatientParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.findPatient({})", currentRequestNum, params);
        //Convert to patterns && MAP
        //TODO передавать сразу map
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
            if (!params.isSetDocument() || params.getDocumentSize() == 0) {
                logger.warn("Document map is not set or empty. Map = {}", params.getDocument().toString());
                patientsList = patientBean.findPatientWithoutDocuments(parameters);
            } else {
                final Map<String, String> document = params.getDocument();
                if (document.containsKey(DocumentMapFields.CLIENT_ID.getFieldName())) {
                    patientsList = patientBean.findPatient(parameters,
                            Integer.parseInt(document.get(DocumentMapFields.CLIENT_ID.getFieldName())));
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
     * Поиск всех пациентов, удовлетворяющих условиям
     * @param params Параметры для поиска пациентов
     * @return Список пациентов
     * @throws TException
     */
    @Override
    public List<ru.korus.tmis.communication.thriftgen.Patient> findPatients(
            final FindMultiplePatientsParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.findPatients({})", currentRequestNum, params);
        //Convert to patterns && MAP
        //TODO передавать сразу map
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
        logger.debug("RECIEVED PARAMS IS {}", params);
        logger.debug("DOCUMENTS IS {}", params.getDocument());
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
     * @param params Параметры поиска
     * @return Статус нахождения пациента
     * @throws NotFoundException            когда не найдено ни одного пациента по заданным параметрам
     * @throws InvalidPersonalInfoException когда по полису или документу найдены пациент(ы) в БД ЛПУ, но (ФИО/пол/др) отличаются от переданных
     * @throws InvalidDocumentException     когда не найдено совпадений по полису и документу, но пациент с таким (ФИО/пол/др) уже есть в БД ЛПУ
     * @throws AnotherPolicyException       когда пациент найден и документы совпали, но его полис отличается от запрошенного
     * @throws NotUniqueException           когда по запрошенным параметрам невозможно выделить единственного пациента
     */
    @Override
    public PatientStatus findPatientByPolicyAndDocument(FindPatientByPolicyAndDocumentParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.findPatientByPolicyAndDocument({})", currentRequestNum, params);
        final PatientStatus result = new PatientStatus();
        //Поиск пациентов по ФИО, полу и ДР
        logger.debug("birthDate = {}", DateConvertions.convertUTCMillisecondsToLocalDate(params.getBirthDate()));
        final List<Patient> patientList = patientBean.findPatientsByPersonalInfo(
                params.getLastName(),
                params.getFirstName(),
                params.getPatrName(),
                params.getSex(),
                DateConvertions.convertUTCMillisecondsToLocalDate(params.getBirthDate())
        );
        if (!patientList.isEmpty()) {
            //Вывод в лог
            if (logger.isDebugEnabled()) {
                logger.debug("Patient founded by personal info (count={}):", patientList.size());
                for (Patient currentPatient : patientList) {
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
            final List<Patient> resultList = new ArrayList<Patient>(patientList.size());
            for (Patient currentPatient : patientList) {
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
                    for (Patient currentPatient : patientList) {
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
                    for (Patient currentPatient : resultList) {
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
     * @throws PolicyTypeNotFoundException когда нету типа полиса с переданным кодом
     * @throws NotFoundException           когда нету пациента с переданным идентификатором
     */
    @Override
    public boolean changePatientPolicy(final ChangePolicyParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.changePatientPolicy({})", currentRequestNum, params);
        try {
            final Patient patient = patientBean.getPatientById(params.getPatientId());
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
            logger.error("End of #{}. No such Patient[{}]", currentRequestNum, params.getPatientId());
            throw new NotFoundException().setError_msg(CommunicationErrors.msgNoSuchPatient.getMessage());
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
        logger.info("#{} Call method -> CommServer.checkForNewQueueCoupons()", currentRequestNum);
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
     * @param params Параметры для поиска первого свободого талончика
     * @return Структура с данными первого доступного для записи талончика
     * @throws NotFoundException когда у выьранного врача с этой даты нету свободных талончиков
     */
    @Override
    public TTicket getFirstFreeTicket(final ScheduleParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getFirstFreeTicket({})", currentRequestNum, params);
        final Staff doctor;
        try {
            doctor = staffBean.getStaffById(params.getPersonId());
        } catch (CoreException e) {
            logger.error("End of #{}.Doctor not found by ID={}", currentRequestNum, params.getPersonId());
            throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
        } catch (EJBException e) {
            logger.error("End of #{}.Doctor not found by ID={}", currentRequestNum, params.getPersonId());
            throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
        }
        final Date begDate = DateConvertions.convertUTCMillisecondsToLocalDate(params.getBeginDateTime());
        final Date endDate = (params.isSetEndDateTime() && params.getBeginDateTime() < params.getEndDateTime()) ?
                DateConvertions.convertUTCMillisecondsToLocalDate(params.getEndDateTime()) : new DateMidnight(begDate).plusMonths(1).toDate();
        logger.debug("From {} to {}.", begDate, endDate);
        List<Action> doctorActions = staffBean.getPersonShedule(doctor.getId(), begDate, endDate);
        logger.debug("Ambulatory Actions count = {}. DATA:", doctorActions.size());
        for (Action currentAction : doctorActions) {
            try {
                final PersonSchedule currentSchedule = new PersonSchedule(doctor, currentAction);
                final RbReasonOfAbsence reasonOfAbsence = currentSchedule.checkReasonOfAbscence();
                if (reasonOfAbsence != null) {
                    logger.info("End of #{}. Doctor has ReasonOfAbsence.", currentRequestNum);
                    continue;
                }
                currentSchedule.formTickets();
                //TODO uncomment to NTK version after 3.3.23 inlusive
//                if(params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty()){
//                    currentSchedule.checkQuotingBySpeciality(params.hospitalUidFrom);
//                }
                currentSchedule.takeConstraintsOnTickets(CommunicationHelper.getQuotingType(params));
                final ru.korus.tmis.communication.Ticket ticket = currentSchedule.getFirstFreeTicketAfterDateTime(params.beginDateTime);
                if (ticket != null) {
                    final TTicket result = ParserToThriftStruct.parseTTicket(currentSchedule, ticket);
                    logger.info("End of #{}. Return: {}", currentRequestNum, result);
                    return result;
                }
            } catch (CoreException e) {
                logger.debug("Skip this action.");
            }
        }
        logger.info("End of #{} getFirstFreeTicket. No one is founded", currentRequestNum);
        throw new NotFoundException().setError_msg("No free ticket founded.");
    }

    /**
     * Метод для получения расписания врача пачкой
     *
     * @param params Параметры для получения расписания
     * @return map[timestamp, Amb] - карта вида <[Дата приема], [Расписание на эту дату]>,
     *         в случае отсутствия расписания на указанную дату набор ключ-значение опускается
     * @throws NotFoundException когда нету такого идентификатора врача
     */
    @Override
    public ru.korus.tmis.communication.thriftgen.PersonSchedule getPersonSchedule(final ScheduleParameters params)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPersonSchedule({})", currentRequestNum, params);
        final Staff doctor;
        try {
            doctor = staffBean.getStaffById(params.getPersonId());
            if (logger.isDebugEnabled()) {
                logger.debug("Requested doctor: {}", doctor.getInfoString());
            }
        } catch (CoreException e) {
            logger.error("End of #{}. Doctor not found by ID={}", currentRequestNum, params.getPersonId());
            throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
        } catch (EJBException e) {
            logger.error("End of #{}. Doctor not found by ID={}", currentRequestNum, params.getPersonId());
            throw new NotFoundException().setError_msg("Doctor not found by ID=" + params.getPersonId());
        }
        final Date begInterval = DateConvertions.convertUTCMillisecondsToLocalDate(params.getBeginDateTime());
        final Date endInterval = (params.isSetEndDateTime() && params.getBeginDateTime() < params.getEndDateTime()) ?
                DateConvertions.convertUTCMillisecondsToLocalDate(params.getEndDateTime()) : new DateMidnight(begInterval).plusMonths(1).toDate();
        logger.debug("From [{}] to [{}]", begInterval, endInterval);
        final ru.korus.tmis.communication.thriftgen.PersonSchedule result = new ru.korus.tmis.communication.thriftgen.PersonSchedule();
        final List<Action> schedule = staffBean.getPersonShedule(doctor.getId(), begInterval, endInterval);
        result.setSchedules(new HashMap<Long, Schedule>(schedule.size()));
        if (schedule.isEmpty()) {
            logger.info("End of #{}. Person[{}] has no one ambulatoryAction in this interval", currentRequestNum, doctor.getId());
            return result;
        }
        for (Action currentAction : schedule) {
            final PersonSchedule currentSchedule = new PersonSchedule(doctor, currentAction);
            final RbReasonOfAbsence reasonOfAbsence = currentSchedule.checkReasonOfAbscence();
            if (reasonOfAbsence != null) {
                logger.info("Doctor has ReasonOfAbsence.", currentRequestNum);
                result.putToPersonAbsences(DateConvertions.convertDateToUTCMilliseconds(currentSchedule.getAmbulatoryDate()),
                        new ReasonOfAbsenceException(reasonOfAbsence.getName(), reasonOfAbsence.getCode()));
                continue;
            }
            try {
                currentSchedule.formTickets();
            } catch (CoreException e) {
                logger.error("Exception while forming tickets:", e);
                continue;
            }
            //TODO uncomment to NTK version after 3.3.23 inlusive
//            if(params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty()){
//                currentSchedule.checkQuotingBySpeciality(params.hospitalUidFrom);
//            }
            currentSchedule.takeConstraintsOnTickets(CommunicationHelper.getQuotingType(params));
            result.putToSchedules(
                    DateConvertions.convertDateToUTCMilliseconds(currentSchedule.getAmbulatoryDate()),
                    ParserToThriftStruct.parsePersonSchedule(currentSchedule)
            );
        }
        logger.debug("End of #{} getPersonSchedule. Return: {}", currentRequestNum, result);
        return result;
    }

    /**
     * Получение информации о пациентах по их идентификаторам
     *
     * @param patientIds Список идентификаторов пациентов
     * @return Список, содержащий информацию по каждому пациенту (Ключ = переданный ID \ значение = Информация о пациенте)
     * @throws NotFoundException
     * @throws SQLException
     * @throws TException
     */
    @Override
    public Map<Integer, ru.korus.tmis.communication.thriftgen.Patient> getPatientInfo(final List<Integer> patientIds) throws TException {
        //Логика работы: по всему полученному массиву вызвать getByID у бина,
        // если нет одного из пациентов, то вернуть всех кроме него.
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPatientInfo({}) total size={}",
                currentRequestNum, patientIds, patientIds.size());
        if (patientIds.size() == 0) return new HashMap<Integer, ru.korus.tmis.communication.thriftgen.Patient>();
        final Map<Integer, ru.korus.tmis.communication.thriftgen.Patient> resultMap = new HashMap<Integer, ru.korus.tmis.communication.thriftgen.Patient>(patientIds.size());
        for (Integer current : patientIds) {
            if (current != null) {
                try {
                    final Patient requested = patientBean.getPatientById(current);
                    if (requested != null) {
                        resultMap.put(current, ParserToThriftStruct.parsePatient(requested));
                        logger.debug("Add patient ID={},NAME={} {}",
                                requested.getId(), requested.getFirstName(), requested.getLastName());
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
     * Запись пациента на прием к врачу
     *
     * @param params ID пациента, ID врача, Дата записи
     * @return Статус успешности записи
     * @throws TException
     */
    @Override
    public EnqueuePatientStatus enqueuePatient(final EnqueuePatientParameters params) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.enqueuePatient({})", currentRequestNum, params);
        final Date paramsDateTime = DateConvertions.convertUTCMillisecondsToLocalDate(params.getDateTime());
        logger.debug("Date: {}", paramsDateTime);
        //Выбранный пациент
        final Patient patient;
        //выбранный врач
        final Staff doctor;
        // прием врача
        final Action personAction;
        try {
            //Проверяем существование пациента по ID:
            patient = patientBean.getPatientById(params.getPatientId());
            //проверить жив ли пациент
            if (!patientBean.isAlive(patient)) {
                logger.warn("Unfortunately this patient is dead.");
                return new EnqueuePatientStatus().setSuccess(false)
                        .setMessage(CommunicationErrors.msgPatientMarkedAsDead.getMessage());
            }
            //Проверяем существование врача по ID:
            doctor = staffBean.getStaffById(params.getPersonId());
            if (!CommunicationHelper.checkApplicable(patient, doctor.getSpeciality())) {
                logger.warn("Doctor speciality is not applicable to patient.");
                return new EnqueuePatientStatus().setSuccess(false)
                        .setMessage(CommunicationErrors.msgEnqueueNotApplicable.getMessage());
            }

            personAction = staffBean.getPersonActionsByDateAndType(
                    params.getPersonId(),
                    DateConvertions.convertUTCMillisecondsToLocalDate(params.getDateTime()),
                    "amb");
        } catch (NoSuchPatientException e) {
            logger.error("Error while get patient by ID=" + params.getPatientId(), e);
            return new EnqueuePatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgWrongPatientId.getMessage());
        } catch (CoreException e) {
            logger.error("DOCTOR NOT WORK AT THIS DATE", e);
            return new EnqueuePatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgQueueNotFound.getMessage());
        } catch (Exception e) {
            logger.error("Error while get Staff by ID=" + params.getPersonId(), e);
            return new EnqueuePatientStatus().setSuccess(false)
                    .setMessage(CommunicationErrors.msgWrongDoctorId.getMessage());

        }

        final PersonSchedule currentSchedule = new PersonSchedule(doctor, personAction);
        final RbReasonOfAbsence reasonOfAbsence = currentSchedule.checkReasonOfAbscence();
        if (reasonOfAbsence != null) {
            logger.info("End of #{}. Doctor has ReasonOfAbsence.", currentRequestNum);
            throw new ReasonOfAbsenceException().setCode(reasonOfAbsence.getCode()).setName(reasonOfAbsence.getName());
        }
        try {
            currentSchedule.formTickets();
        } catch (CoreException e) {
            logger.error("Exception while forming tickets:", e);
        }
        // currentSchedule.takeConstraintsOnTickets(CommunicationHelper.getQuotingType(params));
        final EnqueuePatientStatus result = currentSchedule.enqueuePatientToTime(
                paramsDateTime,
                patient,
                params.getHospitalUidFrom(),
                params.getNote()
        );
        logger.info("End of #{} enqueuePatient. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Получение талончиков пациента
     *
     * @param patientId ид пациента
     * @return список талончиков
     * @throws TException
     */
    @Override
    public List<Queue> getPatientQueue(final int patientId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPatientQueue(PATIENT_ID={}", currentRequestNum, patientId);
        final List<Queue> result = new ArrayList<Queue>(3);
        Patient patient = null;
        try {
            patient = patientBean.getPatientById(patientId);
            logger.debug("Patient = {}", patient);
            final EventType queueEventType = eventBean.getEventTypeByCode("queue");
            logger.debug("EventType = {}", queueEventType);
            final ActionType queueActionType = actionBean.getActionTypeByCode("queue");
            logger.debug("ActionType = {}", queueActionType);
            for (Event currentEvent : patient.getEvents()) {
                if (currentEvent.getEventType().getId().equals(queueEventType.getId()) && !currentEvent.getDeleted()) {
                    logger.debug("EVENT={}", currentEvent);
                    final Queue ticket = new Queue();
                    ticket.setDateTime(DateConvertions.convertDateToUTCMilliseconds(currentEvent.getSetDate()));
                    final Action queueAction = actionBean.getAppealActionByEventId(currentEvent.getId(), queueActionType.getId());
                    logger.debug("ACTION={}", queueAction);
                    if (queueAction != null) {
                        ticket.setNote(queueAction.getNote())
                                .setEnqueueDateTime(queueAction.getCreateDatetime().getTime())
                                .setQueueId(queueAction.getId())
                                .setPersonId(queueAction.getExecutor().getId());
                        if (queueAction.getCreatePerson() != null) {
                            ticket.setEnqueuePersonId(queueAction.getCreatePerson().getId());
                        } else {
                            ticket.setEnqueuePersonId(0);
                        }
                        try {
                            ticket.setIndex(actionPropertyBean.getActionProperty_ActionByValue(queueAction).getId().getIndex());
                        } catch (Exception e) {
                            logger.warn("Record most likely deleted.");
                            continue;
                        }
                        logger.debug("TICKET={}", ticket);
                        result.add(ticket);
                    }
                }
            }  //patientQueueEvents contains all patient queues events only

        } catch (Exception e) {
            if (patient == null) {
                logger.error("Cannot find this patient", e);
                throw new SQLException().setError_msg(CommunicationErrors.msgWrongPatientId.getMessage())
                        .setError_code(CommunicationErrors.msgWrongPatientId.getId());
            }
            logger.error("Error message", e);
        }
        logger.info("End of #{} getPatientQueue. (Size={}) Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Отмена записи пациента к врачу
     *
     * @param patientId Ид пациента
     * @param queueId   Ид записи
     * @return Статус отмены записи
     * @throws TException
     */
    @Override
    public DequeuePatientStatus dequeuePatient(final int patientId, final int queueId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.dequeuePatient(PatientID={}, QueueID={})",
                currentRequestNum, patientId, queueId);
        Action queueAction = null;
        final DequeuePatientStatus result = new DequeuePatientStatus();
        APValueAction ambActionPropertyAction = null;
        try {
            queueAction = actionBean.getActionById(queueId);
//Проверка тот ли пациент имеет данный талончик
            final Event queueEvent = queueAction.getEvent();
            if (queueEvent == null) {
                logger.warn("Action {} has null event.", queueAction);
                result.setSuccess(false).setMessage(CommunicationErrors.msgTicketIsNotBelongToPatient.getMessage());
            } else {
                final Patient queuePatient = queueEvent.getPatient();
                if (queuePatient == null) {
                    logger.warn("Event {} has null patient", queueEvent);
                    result.setSuccess(false).setMessage(CommunicationErrors.msgTicketIsNotBelongToPatient.getMessage());
                } else {
                    if (queuePatient.getId() != patientId) {
                        logger.error("A given patient is not the owner of the ticket");
                        result.setSuccess(false)
                                .setMessage(CommunicationErrors.msgTicketIsNotBelongToPatient.getMessage());
                        return result;
                    }
                    String hospitalUidFrom = queueAction.getHospitalUidFrom();
                    if (!"0".equals(queueAction.getHospitalUidFrom()) || (queueAction.getHospitalUidFrom() != null)
                            || !queueAction.getHospitalUidFrom().isEmpty()) {
                        updateQuotingBySpeciality(queueAction, hospitalUidFrom);
                    }
                    //Получение ActionProperty_Action соответствующего записи пациента к врачу (queue)
                    ambActionPropertyAction = actionPropertyBean.getActionProperty_ActionByValue(queueAction);
                    //Обнуление поля = отмена очереди
                    ambActionPropertyAction.setValue(null);
                    managerBean.merge(ambActionPropertyAction);
                    //Выставляем флаг удаления у соответствующего действия пользователя
                    queueAction.setDeleted(true);
                    queueAction.setModifyDatetime(new Date());
                    managerBean.merge(queueAction);
                    //Выставляем флаг удаления у соответствующего события пользователя
                    queueEvent.setDeleted(true);
                    queueEvent.setModifyDatetime(new Date());
                    managerBean.merge(queueEvent);
                }
            }
        } catch (CoreException e) {
            if (queueAction == null) {
                logger.error("Cannot get queueAction for this ID=" + queueId, e);
                result.setMessage(CommunicationErrors.msgPatientQueueNotFound.getMessage()).setSuccess(false);
            }
            if (ambActionPropertyAction == null) {
                logger.error("Cannot get queueActionProperty for this ID=" + queueId, e);
                result.setMessage(CommunicationErrors.msgPatientQueueNotFound.getMessage()).setSuccess(false);
            }
        }

        if (ambActionPropertyAction != null && ambActionPropertyAction.getValue() == null) {
            result.setMessage(CommunicationErrors.msgOk.getMessage()).setSuccess(true);
        }
        logger.info("End of #{} dequeuePatient. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    private void updateQuotingBySpeciality(Action queueAction, String hospitalUidFrom) {
        logger.info("Dequeue Action has hospitalUidFrom \"{}\"", hospitalUidFrom);
        Staff doctor = staffBean.getDoctorByClientAmbulatoryAction(queueAction);
        logger.info("Doctor is {}", doctor);
        if (doctor != null) {
            logger.info("SPECIALITY {}", doctor.getSpeciality());
            List<QuotingBySpeciality> currentQuotingBySpecialityList = quotingBySpecialityBean
                    .getQuotingBySpecialityAndOrganisation(doctor.getSpeciality(), hospitalUidFrom);
            if (currentQuotingBySpecialityList.isEmpty()) {
                logger.warn("No quoting for this speciality");
            } else {
                for (QuotingBySpeciality currentQuotingBySpeciality : currentQuotingBySpecialityList) {
                    currentQuotingBySpeciality.setCouponsRemaining(
                            currentQuotingBySpeciality.getCouponsRemaining() + 1);
                    logger.debug("Remaining coupons incremented to quoting = {}", currentQuotingBySpeciality);
                    try {
                        managerBean.merge(currentQuotingBySpeciality);
                    } catch (CoreException e) {
                        logger.error("Error while changing QuotingBySpeciality({})", currentQuotingBySpeciality, e);
                    }
                }
            }
        } else {
            logger.warn("Doctor for action {} is not found, quoting is not changed", queueAction);
        }
    }

    /**
     * Получение списка специальностей работников ЛПУ
     * @param hospitalUidFrom ИД ЛПУ
     * @return Список специальностей
     * @throws TException
     */
    @Override
    public List<Speciality> getSpecialities(final String hospitalUidFrom) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getSpecialities({})", currentRequestNum, hospitalUidFrom);

        final List<QuotingBySpeciality> quotingBySpecialityList;
        try {
            quotingBySpecialityList = quotingBySpecialityBean.getQuotingByOrganisation(hospitalUidFrom);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg(CommunicationErrors.msgItemNotFound.getMessage());
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException().setError_msg(CommunicationErrors.msgUnknownError.getMessage());
        }
        List<Speciality> resultList = new ArrayList<Speciality>(quotingBySpecialityList.size());
        for (QuotingBySpeciality item : quotingBySpecialityList) {
            resultList.add(ParserToThriftStruct.parseQuotingBySpeciality(item));
        }
        logger.info("End of #{} getSpecialities. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение информации об Организации по её Инфис-коду
     *
     * @param infisCode инфис-код организации
     * @return информация об Организации
     * @throws TException Ели не найдено ни одной(NotFoundException)
     */
    @Override
    public Organization getOrganisationInfo(final String infisCode) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getOrganisationInfo(infisCode={})", currentRequestNum, infisCode);
        final Organization result;
        try {
            result = ParserToThriftStruct.parseOrganisation(organisationBean.getOrganizationByInfisCode(infisCode));
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg(CommunicationErrors.msgItemNotFound.getMessage());
        }
        logger.info("End of #{} getOrganisationInfo. Return ({})", currentRequestNum, result);
        return result;
    }

    /**
     * Получение адресов организаций
     *
     * @param orgStructureId ид организации
     * @param recursive      флаг рекурсивной выборки организаций
     * @return Список (адрес и ид организации, которой этот адрес принадлежит)
     * @throws TException
     */
    @Override
    public List<Address> getAddresses(final int orgStructureId, final boolean recursive, final String infisCode) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getAddresses(orgStructureId={}, recursive={}, infisCode={})",
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
            if (orgStructureList.size() == 0) {
                throw new NotFoundException().setError_msg("None of the OrgStructure contain this infisCode and any such parent_id=" + orgStructureId);
            }
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Error while getRecursive from bean (Unknown exception)", e);
        }
        final List<Address> resultList = new ArrayList<Address>(orgStructureList.size());
        for (final ru.korus.tmis.core.entity.model.OrgStructure currentOrgStructure : orgStructureList) {
            logger.debug("OrgStructure={}", currentOrgStructure);
            for (final OrgStructureAddress currentOrgStructureAddress
                    : orgStructureBean.getOrgStructureAddressByOrgStructure(currentOrgStructure)) {
                logger.debug("OrgStructureAddress ={}", currentOrgStructureAddress);
                if (currentOrgStructureAddress != null) {
                    final AddressHouse adrHouse = currentOrgStructureAddress.getAddressHouseList();
                    logger.debug("AddressHouse={}", adrHouse);
                    if (adrHouse != null) {
                        resultList.add(
                                new Address().setOrgStructureId(currentOrgStructure.getId())
                                        .setPointKLADR(adrHouse.getKLADRCode())
                                        .setStreetKLADR(adrHouse.getKLADRStreetCode())
                                        .setCorpus(adrHouse.getCorpus()).setNumber(adrHouse.getNumber())
                                        .setFirstFlat(currentOrgStructureAddress.getFirstFlat())
                                        .setLastFlat(currentOrgStructureAddress.getLastFlat()));
                    }
                }
            }
        }
        logger.info("End of #{} getAddresses. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * получение контактов пациента
     *
     * @param patientId ИД пациента
     * @return Список контактов
     * @throws TException
     */
    @Override
    public List<Contact> getPatientContacts(final int patientId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPatientContacts(patientId={})", currentRequestNum, patientId);
        final List<Contact> resultList = new ArrayList<Contact>();
        final Patient patient;
        try {
            patient = patientBean.getPatientById(patientId);
        } catch (CoreException e) {
            logger.error("Error message", e);
            throw new NotFoundException().setError_msg(CommunicationErrors.msgWrongPatientId.getMessage());
        }
        for (ClientContact clientContact : patient.getActiveClientContacts()) {
            final Contact current = new Contact();
            current.setType(clientContact.getContactType().getName()).setContact(clientContact.getContact())
                    .setNote(clientContact.getNotes()).setCode(clientContact.getContactType().getCode());
            resultList.add(current);
            logger.debug("CONTACT={}", current);
        }
        logger.info("End of #{} getPatientContacts. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    //TODO не реализованно
    @Override
    public List<OrgStructuresProperties> getPatientOrgStructures(final int parentId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> CommServer.getPatientOrgStructures(parentId={})", currentRequestNum, parentId);

        final List<OrgStructuresProperties> resultList = new ArrayList<OrgStructuresProperties>(0);

        logger.info("End of #{} getPatientOrgStructures. Return (Size={}), DATA={})",
                currentRequestNum, resultList.size(), resultList);
        throw new TException(CommunicationErrors.msgNotImplemented.getMessage());
    }

    public CommServer() {
        logger.info("Starting CommServer initialize.");
        communicationListener = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread
                try {
                    serverTransport = new TServerSocket(PORT_NUMBER);
                    Communications.Processor<CommServer> processor = new Communications.Processor<CommServer>(getInstance());
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .processor(processor).maxWorkerThreads(MAX_WORKER_THREADS));
                    logger.info("Starting server on port {}", PORT_NUMBER);
                    server.serve();
                } catch (Exception e) {
                    logger.error("Failed to start server on port {}. Exception={}", PORT_NUMBER, e.getMessage());
                    if (logger.isDebugEnabled()) e.printStackTrace();
                }
            }
        });
        communicationListener.setName("Thrift-Service-Thread");
        communicationListener.setPriority(SERVER_THREAD_PRIORITY);
        communicationListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }


    public static CommServer getInstance() {
        if (instance == null) instance = new CommServer();
        return instance;
    }

    public void startService() {
        communicationListener.start();
    }

    public static void setPatientBean(final DbPatientBeanLocal patientBean) {
        CommServer.patientBean = patientBean;
    }

    public static void setOrgStructureBean(final DbOrgStructureBeanLocal dbOrgStructureBeanLocal) {
        CommServer.orgStructureBean = dbOrgStructureBeanLocal;
    }

    public static void setStaffBean(final DbStaffBeanLocal staffBean) {
        CommServer.staffBean = staffBean;
    }

    public static void setSpecialityBean(final DbQuotingBySpecialityBeanLocal dbQuotingBySpecialityBeanLocal) {
        CommServer.quotingBySpecialityBean = dbQuotingBySpecialityBeanLocal;
    }

    public static void setOrganisationBean(final DbOrganizationBeanLocal organisationBean) {
        CommServer.organisationBean = organisationBean;
    }

    public static void setActionPropertyBean(final DbActionPropertyBeanLocal actionPropertyBean) {
        CommServer.actionPropertyBean = actionPropertyBean;
    }

    public static void setQuotingByTimeBean(final DbQuotingByTimeBeanLocal quotingByTimeBean) {
        CommServer.quotingByTimeBean = quotingByTimeBean;
    }

    public static void setActionBean(final DbActionBeanLocal actionBean) {
        CommServer.actionBean = actionBean;
    }

    public static void setManagerBean(final DbManagerBeanLocal managerBean) {
        CommServer.managerBean = managerBean;
    }

    public static void setEventBean(final DbEventBeanLocal eventBean) {
        CommServer.eventBean = eventBean;
    }

    public static void setDocumentBean(DbClientDocumentBeanLocal documentBean) {
        CommServer.documentBean = documentBean;
    }

    public static void setDocumentTypeBean(DbRbDocumentTypeBeanLocal documentTypeBean) {
        CommServer.documentTypeBean = documentTypeBean;
    }

    public static void setPolicyBean(DbClientPolicyBeanLocal policyBean) {
        CommServer.policyBean = policyBean;
    }

    public static void setPolicyTypeBean(DbRbPolicyTypeBeanLocal policyTypeBean) {
        CommServer.policyTypeBean = policyTypeBean;
    }

    public static void setQueueTicketBean(EPGUTicketBeanLocal queueTicketBean) {
        CommServer.queueTicketBean = queueTicketBean;
    }

    public static DbOrgStructureBeanLocal getOrgStructureBean() {
        return orgStructureBean;
    }

    public static DbPatientBeanLocal getPatientBean() {
        return patientBean;
    }

    public static DbStaffBeanLocal getStaffBean() {
        return staffBean;
    }

    public static DbQuotingBySpecialityBeanLocal getQuotingBySpecialityBean() {
        return quotingBySpecialityBean;
    }

    public static DbOrganizationBeanLocal getOrganisationBean() {
        return organisationBean;
    }

    public static DbActionPropertyBeanLocal getActionPropertyBean() {
        return actionPropertyBean;
    }

    public static DbQuotingByTimeBeanLocal getQuotingByTimeBean() {
        return quotingByTimeBean;
    }

    public static DbActionBeanLocal getActionBean() {
        return actionBean;
    }

    public static DbManagerBeanLocal getManagerBean() {
        return managerBean;
    }

    public static DbEventBeanLocal getEventBean() {
        return eventBean;
    }

    public static DbClientDocumentBeanLocal getDocumentBean() {
        return documentBean;
    }

    public static DbRbDocumentTypeBeanLocal getDocumentTypeBean() {
        return documentTypeBean;
    }

    public static DbClientPolicyBeanLocal getPolicyBean() {
        return policyBean;
    }

    public static DbRbPolicyTypeBeanLocal getPolicyTypeBean() {
        return policyTypeBean;
    }

    public static EPGUTicketBeanLocal getQueueTicketBean() {
        return queueTicketBean;
    }

    public static void setPatientQueueBean(PatientQueueBeanLocal patientQueueBean) {
        CommServer.patientQueueBean = patientQueueBean;
    }

    public static PatientQueueBeanLocal getPatientQueueBean() {
        return patientQueueBean;
    }

    public void endWork() {
        logger.warn("CommServer start closing");
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
