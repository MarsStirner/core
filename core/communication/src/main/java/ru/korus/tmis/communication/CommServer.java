package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Address;
import ru.korus.tmis.communication.thriftgen.OrgStructure;
import ru.korus.tmis.communication.thriftgen.Queue;
import ru.korus.tmis.communication.thriftgen.Speciality;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.exception.CoreException;

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
    final static Logger logger = LoggerFactory.getLogger(CommServer.class);
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

    //Number of request
    private static int requestNum = 0;

    /**
     * Получение оргструктур, которые входят в заданное подразделение. При установленном флаге рекурсии выводит все подразделения которые принадлежат запрошенному.
     *
     * @param parentId  Подразделение для которого необходимо найти, входящие в него, подразделения.
     * @param recursive Флаг рекурсии.
     * @return Список подразделений
     * @throws TException
     */
    @Override
    public List<OrgStructure> getOrgStructures(final int parentId, final boolean recursive, final String infisCode) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getOrgStructures(id={}, recursive={}, infisCode={})", currentRequestNum, parentId, recursive, infisCode);

        //Список для хранения сущностей из БД
        List<ru.korus.tmis.core.entity.model.OrgStructure> orgStructureList;
        try {
            //Получение нужных сущностей из бина
            orgStructureList = orgStructureBean.getRecursiveOrgStructures(parentId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("Error while getRecursive from bean.", e);
            throw new SQLException().setError_code(e.getId()).setError_msg("Error while getRecursive from bean (CoreException)." + e.getMessage());
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Error while getRecursive from bean (Unknown exception)", e);
        }
        if (orgStructureList.size() == 0) {
            logger.warn("#{} throw new NotFoundException.", currentRequestNum);
            throw new NotFoundException().setError_msg("None of the OrgStructure contain any such parent =" + parentId);
        }
        //Список который будет возвращен
        List<OrgStructure> resultList = new ArrayList<OrgStructure>(orgStructureList.size());
        //Конвертация сущностей в возвращаемые структуры
        for (ru.korus.tmis.core.entity.model.OrgStructure current : orgStructureList) {
            resultList.add(ParserToThriftStruct.parseOrgStructure(current));
        }
        logger.info("End of #{} getOrgStructures. Return (size={} DATA=({})) as result.", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение ID подразделений, отвечающего за заданный адрес клиента.
     *
     * @param params Адрес клиента
     * @return Список ID подразделений
     * @throws NotFoundException
     * @throws SQLException
     * @throws TException
     */
    @Override
    public List<Integer> findOrgStructureByAddress(final FindOrgStructureByAddressParameters params) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.findOrgStructureByAddress(streetKLADR={}, pointKLADR={}, number={}/{} flat={})",
                currentRequestNum, params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        List<Integer> resultList;
        try {
            resultList = orgStructureBean.getOrgStructureByAddress(params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("not found");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
        }
        if (resultList.size() == 0) {
            logger.warn("#{} throw new NotFoundException.", currentRequestNum);
            throw new NotFoundException().setError_msg("None of the OrgStructure service this address. =" + params.toString());
        }
        logger.info("End of #{} findOrgStructureByAddress.Return (size={} DATA=({})) as result.", currentRequestNum, resultList.size(), resultList);
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
    public List<Person> getPersonnel(final int orgStructureId, final boolean recursive, final String infisCode) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getPersonnel(OrgStructureId={}, recursive={}, infisCode={})", currentRequestNum,
                orgStructureId, recursive, infisCode);
        List<Staff> personnelList;
        try {
            personnelList = orgStructureBean.getPersonnel(orgStructureId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("No one Person related with this orgStructures (COREException)");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
        }
        List<Person> resultList = new ArrayList<Person>(personnelList.size());
        //Конвертация сущностей в возвращаемые структуры
        for (Staff person : personnelList) {
            resultList.add(ParserToThriftStruct.parseStaff(person));
        }
        logger.info("End of #{} getPersonnel. Return (size={} DATA=({})) as result.", currentRequestNum, resultList.size(), resultList);
        return resultList;

    }

    //TODO Не реализовано
    @Override
    public TicketsAvailability getTotalTicketsAvailability(final GetTicketsAvailabilityParameters params) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getTotalTicketsAvailability(OrgStructureId={}, PersonId={}, Speciality={} [Notation={}], BeginDate={} EndDate={})",
                currentRequestNum, params.getOrgStructureId(), params.getPersonId(), params.getSpeciality(),
                params.getSpecialityNotation(), new DateTime(params.getBegDate()), new DateTime(params.getEndDate()));

        TicketsAvailability result = null;
        logger.info("End of #{} getTotalTicketsAvailability. Return \"({})\" as result.", currentRequestNum, result);
        return result;
    }

    //TODO Не реализовано
    @Override
    public List<ExtendedTicketsAvailability> getTicketsAvailability(final GetTicketsAvailabilityParameters params) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getTicketsAvailability(OrgStructureId={}, PersonId={}, Speciality={} [Notation={}], BeginDate={} EndDate={})",
                currentRequestNum, params.getOrgStructureId(), params.getPersonId(), params.getSpeciality(),
                params.getSpecialityNotation(), new DateTime(params.getBegDate()), new DateTime(params.getEndDate()));

        List<ExtendedTicketsAvailability> result = null;
        logger.info("End of #{} getTicketsAvailability. Return (Size={}), DATA={})", currentRequestNum, result.size(), result);
        return result;
    }

    /**
     * Получение расписания врача и информации о талончиках
     *
     * @param params Структура, содержащая:{id врача, дата на которую берется расписание, id ЛПУ}
     * @return Расписание врача
     * @throws TException
     */
    @Override
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws TException {
        int currentRequestNum = requestNum++;
        Date paramsDate = new DateMidnight(params.getDate()).toDate();
        logger.info("#{} Call method -> CommServer.getWorkTimeAndStatus(personId={}, HospitalUID={}, DATE={})",
                currentRequestNum, params.getPersonId(), params.getHospitalUidFrom(), paramsDate);
        Action personAction = null;
        Action timelineAction = null;
        //Доктор для которого получаем расписание
        Staff doctor = null;
        try {
            doctor = staffBean.getStaffById(params.getPersonId());
            //1. Получаем actionId по id врача (personId) и дате(date)
            personAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), paramsDate, "amb");
            if (logger.isDebugEnabled()) {
                logger.debug("ACTION [ ID={} DOCTOR={} {} {}, ACT_TYPE={}, EVENT={}, NOTE={}]", personAction.getId(),
                        doctor.getLastName(), doctor.getFirstName(), doctor.getPatrName(),
                        personAction.getActionType().getName(), personAction.getEvent().getId(), personAction.getNote());
            }
            //2. Проверяем есть ли «причина отсутствия» этого врача в указанную дату _getReasonOfAbsence
            timelineAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), paramsDate, "timeline");
            if (timelineAction != null && actionPropertyBean.getActionPropertyValue(timelineAction.getActionProperties().get(0)).get(0) != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TIMELINE ACTION [ ID={}, ACT_TYPE={}, EVENT={}, NOTE={}]", timelineAction.getId(),
                            timelineAction.getActionType().getName(), timelineAction.getEvent().getId(), timelineAction.getNote());
                }
            }
        } catch (Exception e) {
            if (doctor == null) {
                logger.error("#" + currentRequestNum + " Doctor not found by ID=" + params.getPersonId(), e);
                throw new NotFoundException().setError_msg("Заданный врач не найден ID=" + params.getPersonId());
            }
            if (personAction == null) {
                logger.error("#" + currentRequestNum + "Exception while getting actions for PersonID=" + params.getPersonId(), e);
                throw new NotFoundException().setError_msg("Ошибка во время получения действия связанного с осмотром заданного врача. ID врача=" + params.getPersonId());
            }
            if (timelineAction == null) {
                logger.error("Причина отсутвия на указанную дату не найдена." + params.getPersonId());
            }
        }
        //3. Если есть actionId и отсутствует «Причина отсутствия» (т.е. врач на месте), то делаем выборку ограничений $constraints = _getQuotingByTimeConstraints
        List<QuotingByTime> constraints = null;
        if (timelineAction == null && personAction != null) {
            QuotingType quotingType;
            if (params.getHospitalUidFrom() != 0) quotingType = QuotingType.FROM_OTHER_LPU;
            else quotingType = QuotingType.FROM_PORTAL;

            constraints = quotingByTimeBean.getQuotingByTimeConstraints(params.getPersonId(), paramsDate, quotingType.getValue());

            if (constraints.size() > 0 && logger.isDebugEnabled()) {
                for (QuotingByTime qbt : constraints) {
                    logger.debug("QuotingByTime [Id={}, Person={}, DATE={}, START={}, END={}, TYPE={}]", qbt.getId(), qbt.getDoctor().getLastName(),
                            new DateMidnight(qbt.getQuotingDate()), new DateTime(qbt.getQuotingTimeStart()),
                            new DateTime(qbt.getQuotingTimeEnd()), qbt.getQuotingType());
                }
            }
        }

        //4. Выборка талончиков:
        Amb result = new Amb();
        try {
            if (constraints == null || constraints.size() == 0) {
                result = getAmbInfo(personAction, doctor.getExternalQuota());
            } else {
                //4.1. Если обнаружены ограничения, то производим полную выборку $vResult = _getAmbInfo(actionId, -1) и осуществляем преобразование результата, согласно ограничениям:
                result = getAmbInfo(personAction, (short) -1);
                for (Ticket currentTicket : result.getTickets()) {
                    int available = 0;
                    for (QuotingByTime qbt : constraints) {
                        if (qbt.getQuotingTimeStart().getTime() != 0 && qbt.getQuotingTimeEnd().getTime() != 0) {
                            if (currentTicket.getTime() >= qbt.getQuotingTimeStart().getTime() &&
                                    currentTicket.getTime() <= qbt.getQuotingTimeEnd().getTime() &&
                                    currentTicket.available == 1) {
                                available = 1;
                                break;
                            }
                        }
                    }
                    currentTicket.setAvailable(available);
                }
            }
        } catch (Exception e) {
            logger.error("getAmbInfo failed!", e);
        }
        logger.info("End of #{} getWorkTimeAndStatus. Return (TicketsSize={}) \"{}\" as result.", currentRequestNum, result.getTicketsSize(), result);
        return result;
    }

    private Amb getAmbInfo(final Action action, short quota) throws CoreException {

        Amb result = new Amb();
        String fieldName;

        List<APValueTime> times = new ArrayList<APValueTime>();
        List<APValueAction> queue = new ArrayList<APValueAction>();
        List<Ticket> tickets = new ArrayList<Ticket>();
        short externalCount = 0;

        for (ActionProperty currentProperty : action.getActionProperties()) {
            fieldName = currentProperty.getType().getName();
            //Fill AMB params without tickets and fill arrays to compute tickets
            final APValue value = actionPropertyBean.getActionPropertyValue(currentProperty).get(0);
            if (fieldName.equals("begTime")) {
                result.setBegTime(((APValueTime) value).getValue().getTime());
            } else {
                if (fieldName.equals("endTime")) {
                    result.setEndTime(((APValueTime) value).getValue().getTime());
                } else {
                    if (fieldName.equals("office")) {
                        result.setOffice(((APValueString) value).getValue());
                    } else {
                        if (fieldName.equals("plan")) {
                            result.setPlan(((APValueInteger) value).getValue());
                        } else {
                            if (fieldName.equals("times")) {
                                for (APValue timevalue : actionPropertyBean.getActionPropertyValue(currentProperty)) {
                                    times.add((APValueTime) timevalue);
                                }
                            } else {
                                if (fieldName.equals("queue")) {
                                    for (APValue queuevalue : actionPropertyBean.getActionPropertyValue(currentProperty)) {
                                        queue.add((APValueAction) queuevalue);
                                    }
                                }
                            }
                        }
                    }
                    //Вывод всех свойств со значениями в лог
                    if (logger.isDebugEnabled()) {
                        for (APValue apValue : actionPropertyBean.getActionPropertyValue(currentProperty)) {
                            logger.debug("ID={} NAME={} VALUE={}", currentProperty.getId(), currentProperty.getType().getName(), apValue.getValue());
                        }
                    }
                }
            }
        }
        //END OF ###Fill AMB params without tickets and fill arrays to compute tickets

        //COMPUTE TICKETS
        for (int i = 0; i < times.size(); i++) {
            APValueTime currentTime = times.get(i);
            int free;
            if (currentTime != null) {
                if (queue.size() > i && queue.get(i).getValue() != null) {
                    free = 0;
                    if (action.getAssigner() != null) {
                        externalCount++;
                    }
                } else {
                    free = 1;
                }
                Ticket newTicket = new Ticket();
                newTicket.setTime(new DateTime(currentTime.getValue()).getMillis());
                newTicket.setFree(free).setAvailable(free);
                if (free == 0) {
                    //талончик занят, выясняем кем
                    if (logger.isDebugEnabled()) {
                        logger.debug("CLIENT ACTION={}", queue.get(i).getValue());
                        logger.debug("CLIENT ACTIONID={}", queue.get(i).getValue().getId());
                        logger.debug("CLIENT ACTIONEVENT={}", queue.get(i).getValue().getEvent());
                        logger.debug("CLIENT ACTIONEVENTPATIENT={}", queue.get(i).getValue().getEvent().getPatient());
                    }
                    newTicket.setPatientId(queue.get(i).getValue().getEvent().getPatient().getId())
                            .setPatientInfo(queue.get(i).getValue().getEvent().getPatient().getLastName());
                }
                tickets.add(newTicket);
            }
        }
        //??? http://miswiki.ru/   Получение талончиков _getTickets()
        int available = Math.max(0, (int) (quota * tickets.size() * 0.01) - externalCount);
        if (quota != -1 && available < 1) {
            for (Ticket ticket : tickets) {
                ticket.setAvailable(0);
            }
        }
        return result.setAvailable(available).setTickets(tickets);
    }

    /**
     * Добавление указанного пациента
     *
     * @param params ФИО, Дата рождения, Пол
     * @return Статус добавления пациента
     * @throws TException
     */
    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.addPatient( Full name=\"{} {} {}\", BirthDATE={}, SEX={})",
                currentRequestNum, params.getLastName(), params.getFirstName(), params.getPatrName(),
                new DateMidnight(params.getBirthDate()), params.getSex());

        PatientStatus result = new PatientStatus();
        //CHECK PARAMS
        String errorMessage = "";
        boolean allParamsIsSet = true;
        if (!params.isSetLastName() || params.getLastName().length() == 0) {
            allParamsIsSet = false;
            errorMessage += "Фамилия должна быть указана\n";
        }
        if (!params.isSetFirstName() || params.getFirstName().length() == 0) {
            allParamsIsSet = false;
            errorMessage += "Имя должно быть указано\n";
        }
        if (!params.isSetPatrName() || params.getPatrName().length() == 0) {
            params.setPatrName("");
        }
        if (!allParamsIsSet) {
            logger.warn("End of #{} addPatient.Error message=\"{}\"", currentRequestNum, errorMessage);
            return result.setSuccess(false).setMessage(errorMessage).setPatientId(0);
        }
        //END OF CHECK PARAMS
        DateTime birthDate = new DateTime(params.getBirthDate());
        String sexType;
        switch (params.getSex()) {
            case 1:
                sexType = "male";
                break;
            case 2:
                sexType = "female";
                break;
            default:
                sexType = "";
                break;
        }
        final ru.korus.tmis.core.entity.model.Patient patient;
        try {
            patient = patientBean.insertOrUpdatePatient(0, params.firstName, params.patrName, params.lastName, birthDate.toDate(), "", sexType, "0", "0", "", null, 0, "", "", null, 0);
            patientBean.savePatientToDataBase(patient);
            logger.debug("Patient ={}", patient);
            if (patient.getId() == 0 || patient.getId() == null)
                throw new CoreException("Something is wrong while saving");
        } catch (Exception e) {
            logger.error("Error while saving to database", e);
            return result.setMessage("Error while saving to database. Message=" + e.getMessage()).setSuccess(false);
        }
        result = result.setMessage("Successfully added patient to database").setSuccess(true).setPatientId(patient.getId());
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
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.findPatient( Full name=\"{} {} {}\",Sex={}, BirthDATE={}, IDType={},ID={})",
                currentRequestNum, params.getLastName(), params.getFirstName(), params.getPatrName(), params.getSex(),
                new DateTime(params.getBirthDate()), params.getIdentifierType(), params.getIdentifier());
        //Convert to patterns && MAP
        //TODO передавать сразу map
        Map<String, String> parameters = new HashMap<String, String>();
        if (params.isSetLastName()) {
            parameters.put("lastName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.lastName));
        }
        if (params.isSetFirstName()) {
            parameters.put("firstName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.firstName));
        }
        if (params.isSetPatrName()) {
            parameters.put("patrName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.patrName));
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
        if (params.isSetOmiPolicyNumber()) {
            parameters.put("omiNumber", params.getOmiPolicyNumber());
        }
        if (params.isSetOmiPolicySerial()) {
            parameters.put("omiSerial", params.getOmiPolicySerial());
        }
        if (params.isSetSex()) {
            parameters.put("sex", String.valueOf(params.getSex()));
        }
        logger.debug(parameters.toString());
        List<ru.korus.tmis.core.entity.model.Patient> patientsList;
        try {
            patientsList = patientBean.findPatient(parameters);
        } catch (Exception e) {
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
        switch (patientsList.size()) {
            case 0:
                result = new PatientStatus().setSuccess(false).setMessage("msgNoSuchPatient");
                break;
            case 1:
                result = new PatientStatus().setSuccess(true).setMessage("msgOk").setPatientId(patientsList.get(0).getId());
                break;
            default:
                result = new PatientStatus().setSuccess(false).setMessage("msgTooManySuchPatients: " + patientsList.size());
                break;
        }
        logger.info("End of #{} addPatient. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Поиск всех пациентов, удовлетворяющих условиям
     *
     * @param params Параметры для поиска пациентов
     * @return Список пациентов
     * @throws TException
     */
    @Override
    public List<ru.korus.tmis.communication.thriftgen.Patient> findPatients(
            final FindPatientParameters params) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.findPatients( Full name=\"{} {} {}\",Sex={}, BirthDATE={}, IDType={},ID={})",
                currentRequestNum, params.getLastName(), params.getFirstName(), params.getPatrName(), params.getSex(),
                new DateTime(params.getBirthDate()), params.getIdentifierType(), params.getIdentifier());
        //Convert to patterns && MAP
        //TODO передавать сразу map
        Map<String, String> parameters = new HashMap<String, String>();
        if (params.isSetLastName()) {
            parameters.put("lastName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.lastName));
        }
        if (params.isSetFirstName()) {
            parameters.put("firstName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.firstName));
        }
        if (params.isSetPatrName()) {
            parameters.put("patrName", ParserToThriftStruct.convertDotPatternToSQLLikePattern(params.patrName));
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
        if (params.isSetOmiPolicyNumber()) {
            parameters.put("omiNumber", params.getOmiPolicyNumber());
        }
        if (params.isSetOmiPolicySerial()) {
            parameters.put("omiSerial", params.getOmiPolicySerial());
        }
        if (params.isSetSex()) {
            parameters.put("sex", String.valueOf(params.getSex()));
        }
        logger.debug(parameters.toString());
        List<ru.korus.tmis.core.entity.model.Patient> patientsList;
        try {
            patientsList = patientBean.findPatient(parameters);
        } catch (Exception e) {
            logger.error("Failed to get patients because: {}", e.getMessage(), e.getCause());
            throw new TException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            for (ru.korus.tmis.core.entity.model.Patient pat : patientsList) {
                logger.debug("Patent in result: ID={} FULLNAME={} {} {} SEX={}", pat.getId(),
                        pat.getLastName(), pat.getFirstName(), pat.getPatrName(), pat.getSex());
            }
        }
        List<ru.korus.tmis.communication.thriftgen.Patient> resultList = new ArrayList<ru.korus.tmis.communication.thriftgen.Patient>();
        for (ru.korus.tmis.core.entity.model.Patient pat : patientsList) {
            resultList.add(ParserToThriftStruct.parsePatient(pat));
        }
        logger.info("End of #{} findPatients. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
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
    public Map<Integer, PatientInfo> getPatientInfo(final List<Integer> patientIds) throws TException {
        //Логика работы: по всему полученному массиву вызвать getByID у бина, если нет одного из пациентов, то вернуть всех кроме него.
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getPatientInfo({}) total size={}", currentRequestNum, patientIds, patientIds.size());
        if (patientIds.size() == 0) return new HashMap<Integer, PatientInfo>();
        Map<Integer, PatientInfo> resultMap = new HashMap<Integer, PatientInfo>(patientIds.size());
        for (Integer current : patientIds) {
            if (current != null) {
                try {
                    ru.korus.tmis.core.entity.model.Patient requested = patientBean.getPatientById(current);
                    if (requested != null) {
                        resultMap.put(current, ParserToThriftStruct.parsePatientInfo(requested));
                        logger.debug("Add patient ID={},NAME={} {}", requested.getId(), requested.getFirstName(), requested.getLastName());
                    }
                } catch (CoreException e) {
                    logger.warn("Missing patient with ID={}, No such patient in DB.", current);
                    // resultMap.put(current,null); //Если по какому то ID не найдена запись в БД, то ему соответствует NULL в возвращаемом массиве.
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
        int currentRequestNum = requestNum++;
        DateTime paramsDateTime = new DateTime(params.getDateTime());
        logger.info("#{} Call method -> CommServer.enqueuePatient( DOCTOR_ID={} PATIENT_ID={} DATE=[{}] MILLIS={})",
                currentRequestNum, params.getPersonId(), params.getPatientId(), paramsDateTime, params.getDateTime());

        ru.korus.tmis.core.entity.model.Patient patient = null;
        Staff person = null;
        // Получаем ActionId:
        Action doctorAction;
        //Проверяем существование пациента по ID:
        try {
            patient = patientBean.getPatientById(params.getPatientId());
            person = staffBean.getStaffById(params.getPersonId());
            doctorAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), paramsDateTime.toDate(), "amb");
        } catch (Exception e) {
            if (patient == null) {
                logger.error("Error while get patient by ID=" + params.getPatientId(), e);
                return new EnqueuePatientStatus().setSuccess(false).setMessage("Пациент с указанным ID отсутствует в БД. Запись невозможна.");
            } else {
                if (person == null) {
                    logger.error("Error while get Staff by ID=" + params.getPersonId(), e);
                    return new EnqueuePatientStatus().setSuccess(false).setMessage("Врач с указанным ID отсутствует в БД. Запись невозможна.");
                } else {
                    logger.error("DOCTOR NOT WORK AT THIS DATE", e);
                    return new EnqueuePatientStatus().setSuccess(false).setMessage("Врач не работает в указанную дату " + paramsDateTime.toDateMidnight().toString());
                }
            }
        }
        logger.info("AMB ACTION={} TYPEID={} TYPENAME={} OFFICE={}", doctorAction.getId(),
                doctorAction.getActionType().getId(), doctorAction.getActionType().getName(), doctorAction.getOffice());
        final List<APValueTime> timesAMB = new ArrayList<APValueTime>();
        final List<APValueAction> queueAMB = new ArrayList<APValueAction>();
        ActionProperty queueAP = null;
        final Event queueEvent;
        final EventType queueEventType;
        final Action queueAction;
        final ActionType queueActionType;
        try {
            for (ActionProperty currentProperty : doctorAction.getActionProperties()) {
                String fieldName = currentProperty.getType().getName();
                if (fieldName.equals("times")) {
                    for (APValue timeValue : actionPropertyBean.getActionPropertyValue(currentProperty)) {
                        timesAMB.add((APValueTime) timeValue);
                    }
                } else {
                    if (fieldName.equals("queue")) {
                        queueAP = currentProperty;
                        for (APValue queueValue : actionPropertyBean.getActionPropertyValue(currentProperty)) {
                            queueAMB.add((APValueAction) queueValue);
                        }
                    }
                }
                if (logger.isDebugEnabled()) {  //ALL ACTION PROPERTIES TO LOG
                    List<APValue> values = actionPropertyBean.getActionPropertyValue(currentProperty);
                    for (APValue apValue : values) {
                        logger.debug("NAME={} VALUE={}", currentProperty.getType().getName(), apValue.getValue());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("PARSE ERROR", e);
        }
        //счетчик индекса для queue & times
        int i = 0;
        //Индикатор совпадения одного из времён приема врача и запрошенного времени
        boolean timeHit = false;
        for (APValueTime currentTimeAMB : timesAMB) {
            if (new DateTime(currentTimeAMB.getValue()).getMillisOfDay() == paramsDateTime.getMillisOfDay()) {
                //Совпадение времени с запрошенным
                timeHit = true;
                //Проверка свободности найденной ячейки времени
                if (queueAMB.size() > i && queueAMB.get(i).getValue() != null) {
                    return new EnqueuePatientStatus().setMessage(
                            "Выбранное время:[" + paramsDateTime.toString() + "] к сожалению уже занято другим пациентом.")
                            .setSuccess(false);
                } else {
                    //Если ячейка времени свободна, то создаём записи в таблицах Event, Action, ActionProperty_Action:
                    logger.info("Ячейка времени:[{}] свободна (запрошеное время=[{}]). Начинаем запись пациента.",
                            new DateTime(currentTimeAMB.getValue()),
                            paramsDateTime);
                    try {
                        //1) Создаем событие  (Event)
                        //1.a)Получаем тип события (EventType)
                        queueEventType = eventBean.getEventTypeByCode("queue");
                        logger.debug("EventType is {} typeID={} typeName={}",
                                queueEventType, queueEventType.getId(), queueEventType.getName());
                        //1.b)Сохраняем событие  (Event)
                        queueEvent = eventBean.createEvent(patient, queueEventType, person, paramsDateTime.toDate(), paramsDateTime.plusWeeks(1).toDate());
                        logger.debug("Event is {} ID={} UUID={}", queueEvent, queueEvent.getId(), queueEvent.getUuid().getUuid());
                        //2) Создаем действие (Action)
                        //2.a)Получаем тип    (ActionType)
                        queueActionType = actionBean.getActionTypeByCode("queue");
                        logger.debug("ActionType is {} typeID={} typeName={}", queueActionType, queueActionType.getId(), queueActionType.getName());
                        //2.b)Сохраняем действие  (Action)
                        queueAction = actionBean.createAction(queueActionType, queueEvent, person, paramsDateTime.toDate(), String.valueOf(params.hospitalUidFrom));
                        logger.debug("Action is {} ID={} UUID={}", queueAction, queueAction.getId(), queueAction.getUuid().getUuid());
                        // Заполняем ActionProperty_Action для 'queue' из Action='amb'
                        // Для каждого времени(times) из Action[приема врача]
                        // заполняем очередь(queue) null'ами если она не ссылается на другой Action, и добавлем наш запрос в эту очередь
                        // с нужным значением index, по которому будет происходить соответствие с ячейкой времени.
                        if (timesAMB.size() != queueAMB.size()) {
                            //Наша запись к врачу первая, заполняем все null'ами
                            //Создаем ActionProperty
                            if (queueAP == null) {
                                //Выбираем ActionPropertyType
                                ActionPropertyType queueAPType = null;
                                for (ActionPropertyType apt : doctorAction.getActionType().getActionPropertyTypes()) {
                                    if (apt.getName().equals("queue")) {
                                        queueAPType = apt;
                                    }
                                }
                                if (queueAPType != null) {
                                    queueAP = actionPropertyBean.createActionProperty(doctorAction, queueAPType);
                                } else {
                                    queueAP = actionPropertyBean.createActionProperty(doctorAction, 14, null);
                                }
                            }
                            for (int j = 0; j < timesAMB.size(); j++) {
                                APValueAction newActionProperty_Action = new APValueAction(queueAP.getId(), j);
                                if (i != j) newActionProperty_Action.setValue(null);
                                else newActionProperty_Action.setValue(queueAction);
                                managerBean.persist(newActionProperty_Action);
                            }
                        } else {
                            APValueAction newActionProperty_Action = new APValueAction(queueAP.getId(), i);
                            newActionProperty_Action.setValue(queueAction);
                            managerBean.merge(newActionProperty_Action);
                        }
                    } catch (Exception e) {
                        logger.error("Error while create new EVENT", e);
                        return new EnqueuePatientStatus().setMessage("К сожалению создание обращения невозможно по неизвестным причинам.").setSuccess(false);
                    }
                    break;
                }
            }
            i++;
        }
        //У врача нету талончиков на запрошенную дату.
        if (!timeHit) {
            logger.warn("У врача нету талончиков на запрошенную дату:[{}]", paramsDateTime);
            return new EnqueuePatientStatus().setMessage("У врача нету талончиков на запрошенную дату:[" + paramsDateTime.toString() + "]").setSuccess(false);
        }
        EnqueuePatientStatus result = new EnqueuePatientStatus().setSuccess(true).setIndex(i).setMessage("Успешное внесение в очередь").setQueueId(queueAction.getId());
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
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getPatientQueue(PATIENT_ID={}", currentRequestNum, patientId);
        List<Queue> result = new ArrayList<Queue>(3);
        Patient patient = null;
        try {
            patient = patientBean.getPatientById(patientId);
            logger.debug("Patient = {}", patient);
            EventType queueEventType = eventBean.getEventTypeByCode("queue");
            logger.debug("EventType = {}", queueEventType);
            ActionType queueActionType = actionBean.getActionTypeByCode("queue");
            logger.debug("ActionType = {}", queueActionType);
            for (Event currentEvent : patient.getEvents()) {
                if (currentEvent.getEventType().getId().equals(queueEventType.getId()) && !currentEvent.getDeleted()) {
                    logger.debug("EVENT={}", currentEvent);
                    Queue ticket = new Queue();
                    ticket.setDateTime(currentEvent.getSetDate().getTime());
                    Action queueAction = actionBean.getAppealActionByEventId(currentEvent.getId(), queueActionType.getId());
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
                            logger.warn("Запись скорее всего удалена");
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
                throw new SQLException().setError_msg("No such patient ID=" + patientId).setError_code(patientId);
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
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.dequeuePatient(PatientID={}, QueueID={})", currentRequestNum, patientId, queueId);
        Action queueAction = null;
        DequeuePatientStatus result = new DequeuePatientStatus();
        APValueAction AMBActionProperty_Action = null;
        try {
            queueAction = actionBean.getActionById(queueId);
            //Проверка тот ли пациент имеет данный талончик
            if (queueAction.getEvent().getPatient().getId() != patientId) {
                logger.error("Заданный пациент не является владельцем этого талончика");
                result.setMessage("Заданный пациент не является владельцем этого талончика").setSuccess(false);
                return result;
            }
            //Получение ActionProperty_Action соответствующего записи пациента к врачу (queue)
            AMBActionProperty_Action = actionPropertyBean.getActionProperty_ActionByValue(queueAction);
            //Обнуление поля = отмена очереди
            AMBActionProperty_Action.setValue(null);
            managerBean.merge(AMBActionProperty_Action);
        } catch (Exception e) {
            if (queueAction == null) {
                logger.error("Cannot get queueAction for this ID=" + queueId, e);
                result.setMessage("Нету записи к врачу с данным ID").setSuccess(false);
            }
            if (AMBActionProperty_Action == null) {
                logger.error("Cannot get queueActionProperty for this ID=" + queueId, e);
                result.setMessage("Нету записи к врачу с данным ID").setSuccess(false);
            }

        }
        if (AMBActionProperty_Action != null && AMBActionProperty_Action.getValue() == null)
            result.setMessage("Запись отменена").setSuccess(true);

        logger.info("End of #{} dequeuePatient. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }

    /**
     * Получение списка специальностей работников ЛПУ
     *
     * @param hospitalUidFrom ИД ЛПУ
     * @return Список специальностей
     * @throws TException
     */
    @Override
    public List<Speciality> getSpecialities(final String hospitalUidFrom) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getSpecialities({})", currentRequestNum, hospitalUidFrom);

        List<QuotingBySpeciality> quotingBySpecialityList;
        try {
            quotingBySpecialityList = quotingBySpecialityBean.getQuotingByOrganisation(hospitalUidFrom);
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("not found");
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException(currentRequestNum, "Not found");
        }
        List<Speciality> resultList = new ArrayList<Speciality>(quotingBySpecialityList.size());
        for (QuotingBySpeciality item : quotingBySpecialityList) {
            resultList.add(ParserToThriftStruct.parseQuotingBySpeciality(item));
        }
        logger.info("End of #{} getSpecialities. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение информации об Организации по её Инфис-коду
     *
     * @param infisCode
     * @return информация об Организации
     * @throws TException
     */
    @Override
    public Organization getOrganisationInfo(final String infisCode) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getOrganisationInfo(infisCode={})", currentRequestNum, infisCode);

        Organization result;
        try {
            result = ParserToThriftStruct.parseOrganisation(organisationBean.getOrganizationByInfisCode(infisCode));
        } catch (CoreException e) {
            logger.error("#" + currentRequestNum + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException().setError_msg("not found");
        }
        if (result == null) throw new NotFoundException().setError_msg("Organisation isn't founded in Database");

        logger.info("End of #{} getOrganisationInfo. Return ({})", currentRequestNum, result);
        return result;
    }

    //TODO не реализованно
    @Override
    public List<Address> getAddresses(final int orgStructureId, final boolean recursive) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getAddresses(orgStructureId={},recursive={})", currentRequestNum, orgStructureId, recursive);
        //Список для хранения сущностей из БД
        List<ru.korus.tmis.core.entity.model.OrgStructure> orgStructureList;
        try {
            //Получение нужных сущностей из бина
            orgStructureList = orgStructureBean.getRecursiveOrgStructures(orgStructureId, recursive, "");
        } catch (CoreException e) {
            logger.error("Error while getRecursive from bean.", e);
            throw new SQLException().setError_code(e.getId()).setError_msg("Error while getRecursive from bean (CoreException)." + e.getMessage());
        } catch (Exception e) {
            logger.error("#" + currentRequestNum + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Error while getRecursive from bean (Unknown exception)", e);
        }
        if (orgStructureList.size() == 0) {
            logger.warn("#{} throw new NotFoundException.", currentRequestNum);
            throw new NotFoundException().setError_msg("None of the OrgStructure contain any such parent =" + orgStructureId);
        }
        List<Address> resultList = new ArrayList<Address>(orgStructureList.size());
        for (ru.korus.tmis.core.entity.model.OrgStructure currentOrgStructure : orgStructureList) {
            // resultList.add(new Address().setCorpus(currentOrgStructure.getOrganization().get))
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
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getPatientContacts(patientId={})", currentRequestNum, patientId);
        List<Contact> resultList = new ArrayList<Contact>();
        Patient patient;
        try {
            patient = patientBean.getPatientById(patientId);
        } catch (CoreException e) {
            logger.error("Error message", e);
            throw new NotFoundException().setError_msg("Patient not found");
        }
        for (ClientContact clientContact : patient.getActiveClientContacts()) {
            Contact current = new Contact();
            current.setType(clientContact.getContactType().getName()).setContact(clientContact.getContact())
                    .setNote(clientContact.getNotes()).setCode(clientContact.getContactType().getCode());
            resultList.add(current);
            logger.debug("CONTACT={}", current);
        }
        logger.info("End of #{} getPatientContacts. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
    }

    //TODO не реализованно
    @Override
    public List<OrgStructuresProperties> getPatientOrgStructures(final int parentId) throws TException {
        int currentRequestNum = requestNum++;
        logger.info("#{} Call method -> CommServer.getPatientOrgStructures(parentId={})", currentRequestNum, parentId);

        List<OrgStructuresProperties> resultList = null;

        logger.info("End of #{} getPatientOrgStructures. Return (Size={}), DATA={})", currentRequestNum, resultList.size(), resultList);
        return resultList;
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
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
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

    public static void setPatientBean(DbPatientBeanLocal patientBean) {
        CommServer.patientBean = patientBean;
        logger.debug("Patient Bean Link is {}", patientBean);
    }

    public static void setOrgStructureBean(DbOrgStructureBeanLocal dbOrgStructureBeanLocal) {
        CommServer.orgStructureBean = dbOrgStructureBeanLocal;
        logger.debug("OrgStructure Bean Link is {}", dbOrgStructureBeanLocal);
    }

    public static void setStaffBean(DbStaffBeanLocal staffBean) {
        CommServer.staffBean = staffBean;
        logger.debug("Staff (Personnel) Bean Link is {}", staffBean);
    }

    public static void setSpecialityBean(DbQuotingBySpecialityBeanLocal dbQuotingBySpecialityBeanLocal) {
        CommServer.quotingBySpecialityBean = dbQuotingBySpecialityBeanLocal;
        logger.debug("Speciality (DbRbSpecialityBean) Bean Link is {}", dbQuotingBySpecialityBeanLocal);
    }

    public static void setOrganisationBean(DbOrganizationBeanLocal organisationBean) {
        CommServer.organisationBean = organisationBean;
        logger.debug("Organisation Bean Link is {}", organisationBean);
    }

    public static void setActionPropertyBean(DbActionPropertyBeanLocal actionPropertyBean) {
        CommServer.actionPropertyBean = actionPropertyBean;
        logger.debug("ActionProperty Bean Link is {}", actionPropertyBean);
    }

    public static void setQuotingByTimeBean(DbQuotingByTimeBeanLocal quotingByTimeBean) {
        CommServer.quotingByTimeBean = quotingByTimeBean;
        logger.debug("QuotingByTime Bean Link is {}", quotingByTimeBean);
    }

    public static void setActionBean(DbActionBeanLocal actionBean) {
        CommServer.actionBean = actionBean;
        logger.debug("ActionBean Bean Link is {}", actionBean);
    }

    public static void setManagerBean(DbManagerBeanLocal managerBean) {
        CommServer.managerBean = managerBean;
        logger.debug("ManagerBean Bean Link is {}", managerBean);
    }

    public static void setEventBean(DbEventBeanLocal eventBean) {
        CommServer.eventBean = eventBean;
        logger.debug("EventBean Bean Link is {}", eventBean);
    }

    public void endWork() {
        logger.warn("CommServer start closing");
        logger.info("Total request served={}", requestNum);
        server.stop();
        logger.warn("Server stopped.");
        serverTransport.close();
        logger.warn("Transport closed.");
        communicationListener.interrupt();
        if (communicationListener.isInterrupted()) logger.warn("ServerThread is interrupted successfully");
        if (communicationListener.isAlive()) {
            logger.error("ServerThread is ALIVE?!?!?!");
            //noinspection deprecation
            communicationListener.stop();
        }
        logger.info("All fully stopped.");
    }
}
