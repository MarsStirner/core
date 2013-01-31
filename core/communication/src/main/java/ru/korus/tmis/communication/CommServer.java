package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
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
import ru.korus.tmis.core.exception.CoreException;

import java.util.*;

/**
 * User: Upatov Egor
 * Date: 17.12.12 at 14:55
 * Company:     Korus Consulting IT
 * Description:  Класс содержит в себе ссылки на EJB с помощью которых тянет инфу из базы и реализует методы, сгенеренные thrift.
 * Также методом startService запускает сервер, который слушает с порта в отдельном потоке.
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
    private static int request_num = 0;

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
        request_num++;
        logger.info("#{} Call method -> CommServer.getOrgStructures(id={}, recursive={}, infisCode={})", request_num, parentId, recursive, infisCode);
        //Список который будет возвращен
        List<OrgStructure> resultList = new ArrayList<OrgStructure>();
        //Список для хранения сущностей из БД
        List<ru.korus.tmis.core.entity.model.OrgStructure> allStructuresList;
        try {
            //Получение нужных сущностей из бина
            allStructuresList = orgStructureBean.getRecursiveOrgStructures(parentId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("Error while getRecursive from bean.", e);
            throw new SQLException().setError_code(e.getId()).setError_msg("Error while getRecursive from bean (CoreException)." + e.getMessage());
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Error while getRecursive from bean (Unknown exception)", e);
        }
        if (allStructuresList.size() == 0) {
            logger.warn("#{} throw new NotFoundException.", request_num);
            throw new NotFoundException("None of the OrgStructure contain any such parent =" + parentId);
        }

        //Конвертация сущностей в возвращаемые структуры
        for (ru.korus.tmis.core.entity.model.OrgStructure current : allStructuresList) {
            resultList.add(ParserToThriftStruct.parseOrgStructure(current));
        }
        logger.info("End of #{} getOrgStructures. Return (size={} DATA=({})) as result.", request_num, resultList.size(), resultList);
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
        request_num++;
        logger.info("#{} Call method -> CommServer.findOrgStructureByAddress(streetKLADR={}, pointKLADR={}, number={}/{} flat={})",
                request_num, params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        List<Integer> resultList;
        try {
            resultList = orgStructureBean.getOrgStructureByAddress(params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("not found");
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
        }
        if (resultList.size() == 0) {
            logger.warn("#{} throw new NotFoundException.", request_num);
            throw new NotFoundException("None of the OrgStructure service this address. =" + params.toString());
        }
        logger.info("End of #{} findOrgStructureByAddress.Return (size={} DATA=({})) as result.", request_num, resultList.size(), resultList);
        return resultList;
    }

    @Override
    public List<Person> getPersonnel(final int orgStructureId, final boolean recursive, final String infisCode) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getPersonnel(OrgStructureId={}, recursive={}, infisCode={})", request_num,
                orgStructureId, recursive, infisCode);
        List<Staff> personnelList;
        try {
            personnelList = orgStructureBean.getPersonnel(orgStructureId, recursive, infisCode);
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("No one Person related with this orgStructures (COREException)");
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new TException("Unknown Error", e);
        }
        List<Person> resultList = new ArrayList<Person>(personnelList.size());
        for (Staff person : personnelList) {
            resultList.add(ParserToThriftStruct.parseStaff(person));
        }
        logger.info("End of #{} getPersonnel. Return (size={} DATA=({})) as result.", request_num, resultList.size(), resultList);
        return resultList;

    }

    @Override
    public TicketsAvailability getTotalTicketsAvailability(final GetTicketsAvailabilityParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getTotalTicketsAvailability(OrgStructureId={}, PersonId={}, Speciality={} [Notation={}], BeginDate={} EndDate={})", request_num,
                params.getOrgStructureId(), params.getPersonId(), params.getSpeciality(), params.getSpecialityNotation(),
                new Date(params.getBegDate()), new Date(params.getEndDate()));

        TicketsAvailability result = null;
        logger.info("End of #{} getTotalTicketsAvailability. Return \"({})\" as result.", request_num, result);
        return result;
    }

    @Override
    public List<ExtendedTicketsAvailability> getTicketsAvailability(final GetTicketsAvailabilityParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getTicketsAvailability(OrgStructureId={}, PersonId={}, Speciality={} [Notation={}], BeginDate={} EndDate={})", request_num,
                params.getOrgStructureId(), params.getPersonId(), params.getSpeciality(), params.getSpecialityNotation(),
                new Date(params.getBegDate()), new Date(params.getEndDate()));

        List<ExtendedTicketsAvailability> result = null;
        logger.info("End of #{} getTicketsAvailability. Return (Size={}), DATA={})", request_num, result.size(), result);
        return result;
    }

    @Override
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getWorkTimeAndStatus(personId={}, HospitalUID={}, DATE={})",
                request_num, params.getPersonId(), params.getHospitalUidFrom(), params.getDate());

        Staff doctor;
        try {
            doctor = staffBean.getStaffById(params.getPersonId());
        } catch (Exception e) {
            logger.error("Doctor with ID=" + params.getPersonId() + ". Not found", e);
            throw new NotFoundException().setError_msg("Doctor  with ID=" + params.getPersonId() + ". Not found");
        }

        //1. Получаем actionId по id врача (personId) и дате(date)
        boolean doctor_is_available = true;
        Action personAction;
        try {
            personAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), new Date(params.getDate()), "amb");
        } catch (Exception e) {
            logger.error("Exception while getting actions for PersonID=" + params.getPersonId(), e);
            throw new NotFoundException().setError_msg("CoreException while getting actions for PersonID=" + params.getPersonId());
        }


        if (personAction == null) {
            logger.warn("No actions to personId={} at date={}", params.getPersonId(), params.getDate());
            throw new NotFoundException().setError_msg("There are no actions");
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("ACTION [ ID={} DOCTOR={} {} {}, ACT_TYPE={}, EVENT={}, NOTE={}]", personAction.getId(),
                        doctor.getLastName(), doctor.getFirstName(), doctor.getPatrName(),
                        personAction.getActionType().getName(), personAction.getEvent().getId(), personAction.getNote());
            }
        }

        //2. Проверяем есть ли «причина отсутствия» этого врача в указанную дату _getReasonOfAbsence
        Action timelineAction = null;
        try {
            timelineAction = staffBean.getPersonActionsByDateAndType(params.getPersonId(), new Date(params.getDate()), "timeline");
            if (timelineAction != null && actionPropertyBean.getActionPropertyValue(timelineAction.getActionProperties().get(0)).get(0) != null) {
                doctor_is_available = false;
            }
        } catch (Exception e) {
            logger.error("Exception while getting timeline actions for PersonID=" + params.getPersonId());
            doctor_is_available = true; //На всякий случай
        }
        if (logger.isDebugEnabled() && timelineAction != null) {
            logger.debug("TIMELINE ACTION [ ID={}, ACT_TYPE={}, EVENT={}, NOTE={}]", timelineAction.getId(), timelineAction.getActionType().getName(),
                    timelineAction.getEvent().getId(), timelineAction.getNote());
        }

        //3. Если есть actionId и отсутствует «Причина отсутствия» (т.е. врач на месте), то делаем выборку ограничений $constraints = _getQuotimgByTimeConstraints
        List<QuotingByTime> constraints = null;
        if (doctor_is_available && personAction != null) {
            QuotingType quotingType;
            if (params.getHospitalUidFrom() != 0) quotingType = QuotingType.FROM_OTHER_LPU;
            else quotingType = QuotingType.FROM_PORTAL;
            constraints = quotingByTimeBean.getQuotingByTimeConstraints(params.getPersonId(), new Date(params.getDate()), quotingType.getValue());
            if (constraints.size() > 0 && logger.isDebugEnabled()) {
                for (QuotingByTime qbt : constraints) {
                    logger.debug("QuotingByTime [Id={}, Person={}, DATE={}, START={}, END={}, TYPE={}]", qbt.getId(), qbt.getDoctor().getLastName(),
                            qbt.getQuotingDate(), qbt.getQuotingTimeStart(), qbt.getQuotingTimeEnd(), qbt.getQuotingType());
                }
            }
        }

        //4. Выборка талончиков:
        Amb result = new Amb();
        try {
            if (constraints == null || constraints.size() > 0) {
                result = getAmbInfo(personAction, doctor.getExternalQuota());
            } else {
                //4.1. Если обнаружены ограничения, то производим полную выборку $vResult = _getAmbInfo(actionId, -1) и осуществляем преобразование результата, согласно ограничениям:
                result = getAmbInfo(personAction, (short) -1);
                List<Ticket> modified = result.getTickets();
                for (Ticket current_ticket : modified) {
                    int available = 0;
                    for (QuotingByTime qbt : constraints) {
                        if (qbt.getQuotingTimeStart().getTime() != 0 && qbt.getQuotingTimeEnd().getTime() != 0) {
                            if (current_ticket.getTime() >= qbt.getQuotingTimeStart().getTime() && current_ticket.getTime() <= qbt.getQuotingTimeEnd().getTime() && current_ticket.available == 1) {
                                available = 9999;
                                break;
                            }
                        }
                    }
                    current_ticket.setAvailable(available);
                }
                result.setTickets(modified);
            }
        } catch (Exception e) {
            logger.error("getAmbInfo failed!", e);
        }
        logger.info("End of #{} getWorkTimeAndStatus. Return (TicketsSize={}) \"{}\" as result.", request_num, result.getTicketsSize(), result);
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
            if (fieldName.equals("begTime")) {
                result.setBegTime(((APValueTime) (actionPropertyBean.getActionPropertyValue(currentProperty).get(0))).getValue().getTime());
            } else {
                if (fieldName.equals("endTime")) {
                    result.setEndTime(((APValueTime) (actionPropertyBean.getActionPropertyValue(currentProperty).get(0))).getValue().getTime());
                } else {
                    if (fieldName.equals("office")) {
                        result.setOffice(((APValueString) (actionPropertyBean.getActionPropertyValue(currentProperty).get(0))).getValue());
                    } else {
                        if (fieldName.equals("plan")) {
                            result.setPlan(((APValueInteger) (actionPropertyBean.getActionPropertyValue(currentProperty).get(0))).getValue());
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
                    List<APValue> values = actionPropertyBean.getActionPropertyValue(currentProperty);
                    for (APValue apValue : values) {
                        logger.debug("NAME={} VALUE={}", currentProperty.getType().getName(), apValue.getValue());
                    }
                }
            }
        }
        //END OF ###Fill AMB params without tickets and fill arrays to compute tickets

        //COMPUTE TICKETS
        for (int i = 0; i < times.size(); i++) {
            APValueTime current_time = times.get(i);
            int free;
            if (current_time != null) {
                if (queue.size() > i && queue.get(i).getValue() != null) {
                    free = 0;
                    if (action.getAssigner() != null) {
                        externalCount++;
                    }
                } else {
                    free = 1;
                }
                Ticket newTicket = new Ticket();
                newTicket.setTime(current_time.getValue().getTime());
                newTicket.setFree(free).setAvailable(free);

                if (free == 0) {
                    logger.debug("CLIENT ACTION={}", queue.get(i).getValue());
                    logger.debug("CLIENT ACTIONID={}", queue.get(i).getValue().getId());
                    logger.debug("CLIENT ACTIONEVENT={}", queue.get(i).getValue().getEvent());
                    logger.debug("CLIENT ACTIONEVENTPATIENT={}", queue.get(i).getValue().getEvent().getPatient());
                    newTicket.setPatientId(queue.get(i).getValue().getEvent().getPatient().getId()).setPatientInfo(queue.get(i).getValue().getEvent().getPatient().getLastName());
                }
                tickets.add(newTicket);
            }
        }
        int available = Math.max(0, (int) (quota * tickets.size() * 0.01) - externalCount);
        if (quota != -1 && available < 1) {
            for (Ticket ticket : tickets) {
                ticket.setAvailable(0);
            }
        }
        return result.setAvailable(available).setTickets(tickets);
    }


    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.addPatient( Full name=\"{} {} {}\", BirthDATE={}, SEX={})",
                request_num, params.getLastName(), params.getFirstName(), params.getPatrName(), new Date(params.getBirthDate()), params.getSex());
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
            logger.error(errorMessage);
            return result.setSuccess(false).setMessage(errorMessage).setPatientId(0);
        }
        //END OF CHECK PARAMS
        Date birthDate = new Date();
        birthDate.setTime(params.getBirthDate());
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
        ru.korus.tmis.core.entity.model.Patient patient;
        try {
            patient = patientBean.insertOrUpdatePatient(0, params.firstName, params.patrName, params.lastName, birthDate, "", sexType, "0", "0", "", null, 0, "", "", null, 0);
        } catch (Exception e) {
            logger.error("Error while called insertOrUpdatePatient", e);
            return result.setMessage(e.getMessage()).setSuccess(false);
        }
        logger.debug("Patient ={}", patient);
        if (patient == null) {
            logger.error("Error NULLPOINTER!!!");
            return result.setMessage("Null pointer").setSuccess(false);
        }
        try {
            patientBean.savePatientToDataBase(patient);
            logger.debug("Patient ={}", patient);
            if (patient.getId() == 0 || patient.getId() == null)
                throw new CoreException("Something is wrong while saving");
        } catch (Exception e) {
            logger.error("Error while saving to database", e);
            return result.setMessage("Error while saving to database. Message=" + e.getMessage()).setSuccess(false);
        }
        result = result.setMessage("Successfully added patient to database").setSuccess(true).setPatientId(patient.getId());
        logger.info("End of #{} addPatient. Return \"{}\"", request_num, result);
        return result;
    }

    @Override
    public PatientStatus findPatient(final FindPatientParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.findPatient( Full name=\"{} {} {}\",Sex={}, BirthDATE={}, IDType={},ID={})",
                request_num, params.getLastName(), params.getFirstName(), params.getPatrName(), params.getSex(),
                new Date(params.getBirthDate()), params.getIdentifierType(), params.getIdentifier());
        //Convert to patterns && MAP
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
        logger.info(parameters.toString());
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
        PatientStatus result;
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
        logger.info("End of #{} addPatient. Return \"{}\" as result.", request_num, result);
        return result;
    }

    @Override
    public List<ru.korus.tmis.communication.thriftgen.Patient> findPatients(
            final FindPatientParameters params) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.findPatients( Full name=\"{} {} {}\",Sex={}, BirthDATE={}, IDType={},ID={})",
                request_num, params.getLastName(), params.getFirstName(), params.getPatrName(), params.getSex(),
                new Date(params.getBirthDate()), params.getIdentifierType(), params.getIdentifier());
        //Convert to patterns && MAP
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
        logger.info(parameters.toString());
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
        logger.info("End of #{} findPatients. Return (Size={}), DATA={})", request_num, resultList.size(), resultList);
        return resultList;
    }

    /**
     * Получение информации о пациентах по их идентификаторам
     *
     * @param patientIds Список идентификаторов пациентов
     * @return Список, содержащий информацию по каждому пациенту
     * @throws NotFoundException
     * @throws SQLException
     * @throws TException
     */
    @Override
    public Map<Integer, PatientInfo> getPatientInfo(final List<Integer> patientIds) throws TException {
        //Логика работы: по всему полученному массиву вызвать getByID у бина, если нет одного из пациентов, то вернуть всех кроме него.
        request_num++;
        logger.info("#{} Call method -> CommServer.getPatientInfo({}) total size={}", request_num, patientIds, patientIds.size());
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
        logger.info("End of #{} getPatientInfo. Return (Size={}), DATA={})", request_num, resultMap.size(), resultMap);
        return resultMap;
    }

    @Override
    public EnqueuePatientStatus enqueuePatient(final EnqueuePatientParameters params) throws TException {
        request_num++;
        int currentRequestNumber = request_num;
        DateTime paramsDateTime = new DateTime(params.getDateTime());
        logger.info("#{} Call method -> CommServer.enqueuePatient( DOCTOR_ID={} PATIENT_ID={} DATE=[{}] MILLIS={})",
                currentRequestNumber, params.getPersonId(), params.getPatientId(), paramsDateTime, params.getDateTime());

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
        List<APValueTime> timesAMB = new ArrayList<APValueTime>();
        List<APValueAction> queueAMB = new ArrayList<APValueAction>();
        ActionProperty queueAP = null;
        Event queueEvent;
        EventType queueEventType;
        Action queueAction = null;
        ActionType queueActionType;
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
                    return new EnqueuePatientStatus().setMessage("Выбранное время:[" + paramsDateTime.toString() + "] к сожалению уже занято другим пациентом.").setSuccess(false);
                } else {
                    //Если ячейка времени свободна, то создаём записи в таблицах Event, Action, ActionProperty_Action:
                    logger.info("Ячейка времени:[{}] свободна (запрошеное время=[{}]). Начинаем запись пациента.", new DateTime(currentTimeAMB.getValue()), paramsDateTime);
                    try {
                        //1) Создаем событие  (Event)
                        //1.a)Получаем тип события (EventType)
                        queueEventType = eventBean.getEventTypeByCode("queue");
                        logger.debug("EventType is {} typeID={} typeName={}", queueEventType, queueEventType.getId(), queueEventType.getName());
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
        logger.info("End of #{} enqueuePatient. Return \"{}\" as result.", request_num, result);
        return result;
    }

    @Override
    public List<Queue> getPatientQueue(final int parentId) throws TException {
        List<Queue> resultList = new ArrayList<Queue>(3);
        resultList.add(new Queue().setIndex(1).setDateTime(System.currentTimeMillis()).setEnqueuePersonId(parentId).setQueueId(1).setPersonId(parentId).setEnqueueDateTime(System.currentTimeMillis() - 60000));
        resultList.add(new Queue().setIndex(2).setDateTime(System.currentTimeMillis() + 360000).setEnqueuePersonId(parentId + 100).setQueueId(2).setPersonId(parentId + 100).setEnqueueDateTime(System.currentTimeMillis() - 360000));
        return resultList;
    }

    @Override
    public DequeuePatientStatus dequeuePatient(final int patientId, final int queueId) throws TException {
        return new DequeuePatientStatus().setSuccess(false).setMessage("ЗАГЛУШКА, переданные параметры patientId=" + patientId + " queueId=" + queueId);
    }

    @Override
    public List<Speciality> getSpecialities(final String hospitalUidFrom) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getSpecialities({})", request_num, hospitalUidFrom);


        List<QuotingBySpeciality> quotingBySpecialityList;
        try {
            quotingBySpecialityList = quotingBySpecialityBean.getQuotingByOrganisation(hospitalUidFrom);
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("not found");
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException(request_num, "Not found");
        }
        List<Speciality> resultList = new ArrayList<Speciality>(quotingBySpecialityList.size());
        for (QuotingBySpeciality item : quotingBySpecialityList) {
            resultList.add(ParserToThriftStruct.parseQuotingBySpeciality(item));
        }
        logger.info("End of #{} getSpecialities. Return (Size={}), DATA={})", request_num, resultList.size(), resultList);
        return resultList;
    }

    @Override
    public Organization getOrganisationInfo(final String infisCode) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getOrganisationInfo(infisCode={})", request_num, infisCode);

        Organization result;
        try {
            result = ParserToThriftStruct.parseOrganisation(organisationBean.getOrganizationByInfisCode(infisCode));
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("not found");
        }
        if (result == null) throw new NotFoundException("Organisation isn't founded in Database");

        logger.info("End of #{} getOrganisationInfo. Return ({})", request_num, result);
        return result;
    }


    @Override
    public List<Address> getAddresses(final int orgStructureId, final boolean recursive) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getAddresses(orgStructureId={},recursive={})", request_num, orgStructureId, recursive);

        List<Address> resultList = null;

        logger.info("End of #{} getAddresses. Return (Size={}), DATA={})", request_num, resultList.size(), resultList);
        return resultList;
    }

    @Override
    public List<Contact> getPatientContacts(final int patientId) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getPatientContacts(patientId={})", request_num, patientId);

        List<Contact> resultList = null;

        logger.info("End of #{} getPatientContacts. Return (Size={}), DATA={})", request_num, resultList.size(), resultList);
        return resultList;
    }

    @Override
    public List<OrgStructuresProperties> getPatientOrgStructures(final int parentId) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getPatientOrgStructures(parentId={})", request_num, parentId);

        List<OrgStructuresProperties> resultList = null;

        logger.info("End of #{} getPatientOrgStructures. Return (Size={}), DATA={})", request_num, resultList.size(), resultList);
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
        logger.info("Total request served={}", request_num);
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
