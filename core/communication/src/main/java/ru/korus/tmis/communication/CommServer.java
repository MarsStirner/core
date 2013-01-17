package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.database.DbPatientBeanLocal;
import ru.korus.tmis.core.database.DbQuotingBySpecialityBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //Singleton instance
    private static CommServer instance;
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
    public List<OrgStructure> getOrgStructures(final int parentId, final boolean recursive) throws TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getOrgStructures(id={}, recursive={})", request_num, parentId, recursive);
        //Список который будет возвращен
        List<OrgStructure> resultList = new ArrayList<OrgStructure>();
        //Список для хранения сущностей из БД
        List<ru.korus.tmis.core.entity.model.OrgStructure> allStructuresList;
        try {
            //Получение нужных сущностей из бина
            allStructuresList = orgStructureBean.getRecursiveOrgStructures(parentId, recursive);
        } catch (CoreException e) {
            logger.error("Error while getRecursive from bean.", e);
            throw new TException("Error while getRecursive from bean.", e);
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException(request_num, "Not found");
        }
        //Конвертация сущностей в возвращаемые структуры
        for (ru.korus.tmis.core.entity.model.OrgStructure current : allStructuresList) {
            resultList.add(ParserToThriftStruct.parseOrgStructure(current));
        }
        logger.info("End of #{} getOrgStructres (id={}, recursive={}). Return {} structures.", request_num, parentId, recursive, resultList.size());
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
    public List<Integer> findOrgStructureByAddress(final FindOrgStructureByAdressParameters params) throws NotFoundException, SQLException, TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.findOrgStructureByAddress(streetKLADR={}, pointKLADR={}, number={}/{} flat={})",
                request_num, params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        //TODO OrgStructure_Adress TABLE IS EMPTY for v71  used pnz_gor_det shema
        List<Integer> resultList = null;
        try {
            resultList = orgStructureBean.getOrgStructureByAdress(params.getPointKLADR(), params.getStreetKLADR(), params.getNumber(), params.getCorpus(), params.getFlat());
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("not found");
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException(request_num, "Not found");
        }
        logger.info("End of #{} findOrgStructureByAdress. Return \"{}\" as result.", request_num, resultList);
        return resultList;
    }

    @Override
    public List<Person> getPersonnel(final int orgStructureId, final boolean recursive) throws NotFoundException, SQLException, TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getPersonnel(OrgStructureId={}, recursive={})", request_num, orgStructureId, recursive);
        List<Staff> personnelList;
        try {
            personnelList = orgStructureBean.getPersonnel(orgStructureId, recursive);
        } catch (CoreException e) {
            logger.error("#" + request_num + " COREException. Message=" + e.getMessage(), e);
            throw new NotFoundException("not found");
        } catch (Exception e) {
            logger.error("#" + request_num + " Exception. Message=" + e.getMessage(), e);
            throw new SQLException(request_num, "Not found");
        }
        List<Person> resultList = new ArrayList<Person>(personnelList.size());
        for (Staff person : personnelList) {
            resultList.add(ParserToThriftStruct.parseStaff(person));
        }
        logger.info("End of #{} getPersonnel. Return \"{}\" as result.", request_num, resultList);
        return resultList;

    }

    @Override
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws NotFoundException, SQLException, TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.getWorkTimeAndStatus(personId={}, HospitalUID={}, DATE={})",
                request_num, params.getPersonId(), params.getHospitalUidFrom(), params.getDate());

        logger.info("End of #{} getWorkTimeAndStatus. Return \"{}\" as result.", request_num, null);
        return new Amb().setPlan("");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws SQLException, TException {
        request_num++;
        logger.info("#{} Call method -> CommServer.addPatient( Full name=\"{} {} {}\", HospitalUID={}, BirthDATE={})",
                request_num, params.getLastName(), params.getFirstName(), params.getPatrName(), params.getBirthDate());
        logger.info("End of #{} addPatient. Return \"{}\" as result.", request_num, null);
        return new PatientStatus().setMessage("На данный момент это заглушка[return false always] Was called with lastname=" + params.getLastName()).setSuccess(false);
    }

    @Override
    public PatientStatus findPatient(final FindPatientParameters params) throws NotFoundException, SQLException, TException {
        return new PatientStatus().setMessage("На данный момент это заглушка[return false always].Was called with identifier " + params.getIdentifier()).setSuccess(false);
    }

    @Override
    public List<Integer> findPatients(final FindPatientParameters params) throws NotFoundException, SQLException, TException {
        List<Integer> resultList = new ArrayList<Integer>(5);
        resultList.add(3280);
        resultList.add(3882);
        resultList.add(4594);
        resultList.add(366);
        resultList.add(2144);
        resultList.add(799);
        resultList.add(1173);
        resultList.add(4844);
        resultList.add(120);
        resultList.add(2476);
        resultList.add(237);
        resultList.add(4379);
        resultList.add(1508);
        resultList.add(4666);
        resultList.add(3);
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
    public Map<Integer, PatientInfo> getPatientInfo(final List<Integer> patientIds) throws NotFoundException, SQLException, TException {
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
                        resultMap.put(current, ParserToThriftStruct.parsePatient(requested));
                        logger.debug("Add patient ID={},NAME={} {}", requested.getId(), requested.getFirstName(), requested.getLastName());
                    }
                } catch (CoreException e) {
                    logger.warn("Missing patient with ID={}, No such patient in DB.", current);
                    // resultMap.put(current,null); //Если по какому то ID не найдена запись в БД, то ему соответствует NULL в возвращаемом массиве.
                }
            }
        }
        logger.info("End of #{} getPatientInfo.Return {} patient info's.", request_num, resultMap);
        return resultMap;
    }

    @Override
    public EnqueuePatientStatus enqueuePatient(final EnqueuePatientParameters params) throws NotFoundException, SQLException, TException {
        return new EnqueuePatientStatus().setSuccess(false).setIndex(params.getPersonId()).setMessage("ЗАГЛУШКА" + params.getNote()).setQueueId(0);
    }

    @Override
    public List<Queue> getPatientQueue(final int parentId) throws NotFoundException, SQLException, TException {
        List<Queue> resultList = new ArrayList<Queue>(3);
        resultList.add(new Queue().setIndex(1).setDateTime(System.currentTimeMillis()).setEnqueuePersonId(parentId).setQueueId(1).setPersonId(parentId).setEnqueueDateTime(System.currentTimeMillis() - 60000));
        resultList.add(new Queue().setIndex(2).setDateTime(System.currentTimeMillis() + 360000).setEnqueuePersonId(parentId + 100).setQueueId(2).setPersonId(parentId + 100).setEnqueueDateTime(System.currentTimeMillis() - 360000));
        return resultList;
    }

    @Override
    public DequeuePatientStatus dequeuePatient(final int patientId, final int queueId) throws NotFoundException, SQLException, TException {
        return new DequeuePatientStatus().setSuccess(false).setMessage("ЗАГЛУШКА, переданные параметры patientId=" + patientId + " queueId=" + queueId);
    }

    @Override
    public List<Speciality> getSpecialities(final String hospitalUidFrom) throws NotFoundException, SQLException, TException {
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
        logger.info("End of #{} getSpecialities. Return {} specialities.", request_num, resultList.size());
        return resultList;
    }

    @Override
    public List<Address> getAddresses(int orgStructureId, boolean recursive) throws SQLException, NotFoundException, TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Contact> getPatientContacts(int patientId) throws NotFoundException, TException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CommServer() {
        logger.info("Starting CommServer initialize.");
        communicationListener = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread
                try {
                    TServerSocket serverTransport = new TServerSocket(PORT_NUMBER);
                    Communications.Processor<CommServer> processor = new Communications.Processor<CommServer>(getInstance());
                    TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
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

}
