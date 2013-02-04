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
import ru.korus.tmis.core.entity.model.Patient;
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
        List<ru.korus.tmis.core.entity.model.OrgStructure> allStructuresList = null;
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
            resultList.add(parseOrgStructureToThriftStruct(current));
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
        logger.info("Call method -> CommServer.getPersonell(OrgStructureId={}, recursive={})", orgStructureId, recursive);
        List<Person> personell = new ArrayList<Person>(10);
        //TODO for orgstructure id=33 recursive false available for external=ignore
        personell.add(new Person().setId(379).setOrgStructureId(33).setCode("793").setLastName("Григорьев").setFirstName("Алексей").setPatrName("Юрьевич").setSpeciality("сестринское дело").setSpecialityOKSOCode("040815").setSpecialityRegionalCode("63").setSexFilter("0").setPost("Медицинская сестра"));
        personell.add(new Person().setId(539).setOrgStructureId(33).setCode("-").setLastName("Завистовский").setFirstName("Андрей").setPatrName("Валериевич").setSpeciality("радиолог").setSpecialityOKSOCode("040117").setSpecialityRegionalCode("0").setSexFilter("0").setPost("Врач-радиолог"));
        personell.add(new Person().setId(196).setOrgStructureId(33).setCode("591").setLastName("Курганова").setFirstName("Ирина").setPatrName("Николаевна").setSpeciality("радиолог").setSpecialityOKSOCode("040117").setSpecialityRegionalCode("16").setSexFilter("0").setPost("Врач-радиолог"));
        personell.add(new Person().setId(197).setOrgStructureId(33).setCode("540").setLastName("Нечеснюк").setFirstName("Алексей").setPatrName("Владимирович").setSpeciality("радиолог").setSpecialityOKSOCode("040117").setSpecialityRegionalCode("16").setSexFilter("0").setPost("Заведующий отделением"));
        personell.add(new Person().setId(380).setOrgStructureId(33).setCode("791").setLastName("Никитина").setFirstName("Мария").setPatrName("Михайловна").setSpeciality("сестринское дело").setSpecialityOKSOCode("040815").setSpecialityRegionalCode("63").setSexFilter("0").setPost("Медицинская сестра"));
        personell.add(new Person().setId(381).setOrgStructureId(33).setCode("216").setLastName("Никишова").setFirstName("Тамара").setPatrName("Геннадьевна").setSpeciality("лабораторное дело").setSpecialityOKSOCode("040813").setSpecialityRegionalCode("0").setSexFilter("0").setPost("Лаборант"));
        personell.add(new Person().setId(382).setOrgStructureId(33).setCode("208").setLastName("Огурцова").setFirstName("Любовь").setPatrName("Денисовна").setSpeciality("лабораторное дело").setSpecialityOKSOCode("040813").setSpecialityRegionalCode("0").setSexFilter("0").setPost("Лаборант"));
        personell.add(new Person().setId(383).setOrgStructureId(33).setCode("990").setLastName("Сахарова").setFirstName("Оксана").setPatrName("Михайловна").setSpeciality("сестринское дело").setSpecialityOKSOCode("040815").setSpecialityRegionalCode("63").setSexFilter("0").setPost("Медицинская сестра"));
        personell.add(new Person().setId(199).setOrgStructureId(33).setCode("686").setLastName("Степанова").setFirstName("Ольга").setPatrName("Викторовна").setSpeciality("сестринское дело").setSpecialityOKSOCode("040815").setSpecialityRegionalCode("63").setSexFilter("0").setPost("Старшая медицинская сестра"));
        personell.add(new Person().setId(384).setOrgStructureId(33).setCode("1048").setLastName("Тедеева").setFirstName("Анна").setPatrName("Хасановна").setSpeciality("радиолог").setSpecialityOKSOCode("040117").setSpecialityRegionalCode("16").setSexFilter("0").setPost("Врач-радиолог"));
        return personell;

    }

    @Override
    public Amb getWorkTimeAndStatus(final GetTimeWorkAndStatusParameters params) throws NotFoundException, SQLException, TException {

        return new Amb().setPlan("");  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PatientStatus addPatient(final AddPatientParameters params) throws SQLException, TException {
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
                        resultMap.put(current, parsePatientToThriftStruct(requested));
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

    private PatientInfo parsePatientToThriftStruct(final Patient item) {
        if (item == null) return null;
        PatientInfo result = new PatientInfo().setFirstName(item.getFirstName()).setLastName(item.getLastName()).setPatrName(item.getPatrName());
        result.setSex(item.getSex()).setBirthDate(item.getBirthDate().getTime());
        return result;
    }

    private OrgStructure parseOrgStructureToThriftStruct(final ru.korus.tmis.core.entity.model.OrgStructure item) {

        OrgStructure result = new OrgStructure().setId(item.getId()).setCode(item.getCode());
        if (item.getAddress() != null) result.setAdress(item.getAddress());
        if (item.getName() != null) result.setName(item.getName());
        if (item.getParentId() != null) result.setParent_id(item.getParentId());
        return result;
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
}
