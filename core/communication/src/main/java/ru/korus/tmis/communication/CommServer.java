package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Queue;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Staff;
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
        List<Integer> resultList = null;
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
    public List<ExtendedTicketsAvailability> getTicketsAvailability(final GetTicketsAvailabilityParameters params) throws NotFoundException, SQLException, TException {
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


        Amb result = new Amb().setPlan("");
        logger.info("End of #{} getWorkTimeAndStatus. Return (TicketsSize={}) \"{}\" as result.", request_num, result.getTicketsSize(), result);
        return result;
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

        Patient patient = null;
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
        List<Patient> patientsList = null;
        try {
            patientsList = patientBean.findPatient(parameters);
        } catch (Exception e) {
            logger.error("Failed to get patients because: {}", e.getMessage(), e.getCause());
            throw new TException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            for (Patient pat : patientsList) {
                logger.debug("Patent in result: ID={} FULLNAME={} {} {} SEX={}", pat.getId(),
                        pat.getLastName(), pat.getFirstName(), pat.getPatrName(), pat.getSex());
            }
        }
        PatientStatus result = null;
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
    public List<ru.korus.tmis.communication.thriftgen.Patient> findPatients(final FindPatientParameters params) throws TException {
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
        List<Patient> patientsList = null;
        try {
            patientsList = patientBean.findPatient(parameters);
        } catch (Exception e) {
            logger.error("Failed to get patients because: {}", e.getMessage(), e.getCause());
            throw new TException(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            for (Patient pat : patientsList) {
                logger.debug("Patent in result: ID={} FULLNAME={} {} {} SEX={}", pat.getId(),
                        pat.getLastName(), pat.getFirstName(), pat.getPatrName(), pat.getSex());
            }
        }
        List<ru.korus.tmis.communication.thriftgen.Patient> resultList = new ArrayList<ru.korus.tmis.communication.thriftgen.Patient>();
        for (Patient pat : patientsList) {
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
                    Patient requested = patientBean.getPatientById(current);
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
        return new EnqueuePatientStatus().setSuccess(false).setIndex(params.getPersonId()).setMessage("ЗАГЛУШКА" + params.getNote()).setQueueId(0);
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


        List<QuotingBySpeciality> quotingBySpecialityList = null;
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

        Organization result = null;
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
            communicationListener.stop();
        }
        logger.info("All fully stopped.");
    }
}
