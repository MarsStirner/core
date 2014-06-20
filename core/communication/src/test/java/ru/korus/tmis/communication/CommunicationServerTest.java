package ru.korus.tmis.communication;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.TTCCLayout;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Queue;
import ru.korus.tmis.core.database.common.DbPatientBean;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.schedule.DateConvertions;
import ru.korus.tmis.util.TestUtilCommon;
import ru.korus.tmis.util.TestUtilDatabase;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Author: Upatov Egor <br>
 * Date: 18.06.2014, 17:46 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Test(groups = "communication")
public class CommunicationServerTest extends Arquillian {
    private final static Logger logger = LoggerFactory.getLogger(CommunicationServerTest.class);
    private final static Marker testMarker = MarkerFactory.getMarker("TEST");
    private final static int PORT = 7914;
    private final static String HOST = "127.0.0.1";
    private final int TIMEOUT = 100000; // 100 seconds
    private Communications.Client client;

    @Deployment
    public static Archive createTestArchive() {
        System.out.println("Start of creating Archive");
        final WebArchive archive = ShrinkWrap.create(WebArchive.class, "communication_test.war");
        archive.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");
        archive.addPackage(CommunicationServer.class.getPackage());
        archive.addPackage(ru.korus.tmis.core.database.DbJobBean.class.getPackage());
        archive.addPackage(ru.korus.tmis.core.auth.AuthStorageBeanLocal.class.getPackage());
        archive.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        archive.addPackages(false, (new TestUtilDatabase()).getPackagesForTest());
        logger.info(archive.toString());
        return archive;
    }

    @BeforeClass
    public void initConnection() {
        final TTransport transport = new TSocket(HOST, PORT, TIMEOUT);
        logger.info("Transport success");
        final TProtocol protocol = new TBinaryProtocol(transport);
        logger.info("Protocol success");
        client = new Communications.Client(protocol);
        logger.info("Client created");
        try {
            transport.open();
        } catch (TTransportException e) {
            logger.error("Cannot open transport: " + e.getMessage());
            e.printStackTrace();
            fail("Cannot open transport: " + e.getMessage());
        }
        logger.info("Transport opened successfully");
    }

    @AfterClass
    public void closeConnection() throws InterruptedException {
        logger.warn("End of all communication component test suite. Closing transport... ");
        client.getInputProtocol().getTransport().close();
        client.getOutputProtocol().getTransport().close();
        logger.info("Transport closed");
    }

    @Test
    public void getVersion() throws TException {
        logger.info("Start of Test: getVersion");
        final int result = client.getVersion();
        logger.info("Result is: {}", result);
        assertNotEquals(result, 0, "Get 0 as version");
        logger.info("End of Test: getVersion. SUCCESS");
    }

    @Test
    public void checkOrganisationInfo() {
        logger.info("Start of Test: getOrganisationInfo");
        String param = "500";
        Organization result;
        try {
            logger.info("Call with {}", param);
            result = client.getOrganisationInfo(param);
            logger.info("Result is: {}", result);
        } catch (NotFoundException e) {
            fail("NotFoundException by infisCode=" + param);
        } catch (TException e) {
            e.printStackTrace();
            fail();
        }
        param = new BigInteger(130, new SecureRandom()).toString(32);
        try {
            logger.info("Call with {}", param);
            result = client.getOrganisationInfo(param);
            logger.info("Result is: {}", result);
            fail("Get Answer on a random string=" + param);
        } catch (NotFoundException e) {
            logger.info("Not Found as expected.");
        } catch (TException e) {
            e.printStackTrace();
            fail();
        }
        logger.info("End of Test: getOrganisationInfo. SUCCESS");
    }

    @Test
    public void checkOrgStructures() {
        logger.info("Start of Test: getOrgStructures");
        String infisCode = "500";
        boolean recursive = false;
        int parentId = 0;
        logger.info(testMarker, "Call with infis={}, parent={}, recursive={}", new Object[]{infisCode, parentId, recursive});
        try {
            final List<OrgStructure> result = client.getOrgStructures(parentId, recursive, infisCode);
            for (OrgStructure current : result) {
                logger.info(current.toString());
            }
        } catch (TException e) {
            fail();
        }
        //set recursive = true
        recursive = true;
        logger.info(testMarker, "Call with infis={}, parent={}, recursive={}", new Object[]{infisCode, parentId, recursive});
        try {
            final List<OrgStructure> result = client.getOrgStructures(parentId, recursive, infisCode);
            for (OrgStructure current : result) {
                logger.info(current.toString());
            }
        } catch (TException e) {
            fail();
        }
        logger.info("End of Test: getOrgStructures. SUCCESS");
    }

    @Test
    public void checkAllOrgStructures() {
        logger.info("Start of Test: getAllOrgStructures");
        String infisCode = "500";
        boolean recursive = false;
        int parentId = 0;
        logger.info(testMarker, "Call with infis={}, parent={}, recursive={}", new Object[]{infisCode, parentId, recursive});
        try {
            final List<OrgStructure> result = client.getOrgStructures(parentId, recursive, infisCode);
            for (OrgStructure current : result) {
                logger.info(current.toString());
            }
        } catch (TException e) {
            fail();
        }
        //set recursive = true
        recursive = true;
        logger.info(testMarker, "Call with infis={}, parent={}, recursive={}", new Object[]{infisCode, parentId, recursive});
        try {
            final List<OrgStructure> result = client.getOrgStructures(parentId, recursive, infisCode);
            for (OrgStructure current : result) {
                logger.info(current.toString());
            }
        } catch (TException e) {
            fail();
        }
        logger.info("End of Test: getAllOrgStructures. SUCCESS");
    }

    @Test(enabled = true)
    public void getPatientQueue() {
        logger.info("Start getPatientQueue test:");
        List<Queue> result;
        Integer patientId = 2;
        try {
            result = client.getPatientQueue(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of getpatientQueue test.");
        } catch (TException e) {
            logger.error("Fail of getPatientQueue test. Exception stacktrace:", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void getPatientContacts() {
        logger.info("Start getPatientContacts test:");
        List<Contact> result;
        Integer patientId = 2;
        try {
            result = client.getPatientContacts(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of getPatientContacts test.");
        } catch (TException e) {
            logger.error("getPatientContacts Failed.", e);
            fail();
        }
    }

    @Test
    public void checkFindPatient() {
        final FindPatientParameters params = new FindPatientParameters();
        params.setLastName("Милонов");
        params.setFirstName("Сергей");
        params.setPatrName("Игоревич");
        params.setBirthDate(DateConvertions.convertLocalDateToUTCMilliseconds(new LocalDate(1990, 8, 1)));
        params.setSex(1);
        params.putToDocument("client_id", "2");
        PatientStatus patientStatus = null;
        try {
            logger.info("Call findPatient({})", params);
            patientStatus = client.findPatient(params);
            logger.info("Result: {}", patientStatus);
            assertEquals(patientStatus.getPatientId(), 2, "Не тот пациент (ИД=2)");
            assertTrue(patientStatus.isSuccess(), "Поиск был неуспешным");
            //Меняем пол, теперь не должно быть успешного результата
            params.setSex(2);
            logger.info("Call findPatient({})", params);
            patientStatus = client.findPatient(params);
            logger.info("Result: {}", patientStatus);
        } catch (TException e) {
            e.printStackTrace();
            fail("FindPatientTest throw EXCEPTION");
        }
        logger.info("FindPatientTest passed. Result = {}", patientStatus);
    }

    @Test(enabled = true)
    public void getPersonnel() {
        logger.info("Start of Test: getPersonnel:");
        List<Person> result;
        Integer orgStructureId = 0;
        boolean recursive = true;
        String infisCode = "";
        try {
            result = client.getPersonnel(orgStructureId, recursive, infisCode);
            logger.info("Send and receive is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());
            for (Person person : result) {
                logger.info(person.toString());
            }
            logger.info("Successful end of getPersonnel test.");
        } catch (TException e) {
            logger.error("getPersonnel Failed.", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void getPatientInfo() {
        logger.info("Start of getPatientInfo test:");
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(414);
        ids.add(212);
        ids.add(1000);
        ids.add(2);
        ids.add(1);
        HashMap<Integer, Patient> result;
        try {
            result = (HashMap<Integer, Patient>) client.getPatientInfo(ids);
            logger.info("Send and receive is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());
            for (Integer key : result.keySet()) {
                logger.info("KEY:" + key + " VALUE:" + result.get(key));
            }
            logger.info("Successful end of getPatientInfo test.");
        } catch (TException e) {
            logger.error("getPatientInfo test failed", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void checkForNewQueueCoupons() {
        try {
            final List<QueueCoupon> result = client.checkForNewQueueCoupons();
            for (QueueCoupon current : result) {
                logger.info(current.toString());
            }
        } catch (TException e) {
            logger.error("Failed to checkForNewQueueCoupons: ", e);
            fail("Not working");
        }
    }


    @Test(enabled = true)
    public void linkedTest() {
        logger.info(
                "Start of linked test. Try to add patient, get patientInfo on him and check enqueue/dequeue to doctor");
        final String lastName = "Фамилия";
        final String firstName = "Имя";
        final String patrName = "Отчество";
        final long birthDate = DateConvertions.convertLocalDateToUTCMilliseconds(new LocalDate(1999, 9, 19));
        Integer id;
        try {
            final AddPatientParameters patient = new AddPatientParameters()
                    .setLastName(lastName)
                    .setFirstName(firstName)
                    .setPatrName(patrName)
                    .setBirthDate(birthDate);
            logger.info("First step is try to addPatient: {}", patient);
            final PatientStatus addStatus = client.addPatient(patient);
            logger.info("AddStatus for patient is {}", addStatus);
            assertTrue(addStatus != null, "Добавление пациента не удалось");
            assertTrue(addStatus.isSuccess(), "Пациент не добавлен");

            id = addStatus.getPatientId();
            logger.info("Patient successfully added, ID={}", id);

            logger.info("Second step is to call getPatientInfo(id={})", id);
            getPatientInfoFromLinkedTest(lastName, firstName, patrName, birthDate, id);

            logger.info("Third step is try to find Patient [{}] by multiple ways:", patient);

            logger.info("1) Call findPatient method with document, contains \"client_id\"  param.");
            findPatientByDocument(lastName, firstName, patrName, birthDate, id);

            logger.info("2) Call findPatients method with \"lastName\" param.");
            findPatientsByLastNameOnly(lastName, id);

            logger.info("3) Call findPatients method with \"FirstName\" param.");
            findPatientsByFirstNameOnly(firstName, id);

            logger.info("4) Call findPatients method with \"birthDate\" param.");
            findPatientsByBirthDateOnly(birthDate, id);

            logger.info("5) Call findPatients method with same param as findPatient.");
            findPatient(lastName, firstName, patrName, birthDate, id);

            logger.info("6) Send another parameters to findPatient(-s) method to avoid receive our patient.");
            incorrectParamsTest(lastName, firstName, patrName, birthDate, id);

            logger.info("Fourth step is to find free ticket to a doctor and find a doctor too.");
            Map<Integer, List<TTicket>> enqueueParam = checkAvailableDoctors();
            if (enqueueParam.isEmpty()) {
                logger.warn("No one doctor has Free tickets");
                fail();
            }
            logger.info("1) Available tickets is {}", enqueueParam);
            Iterator<Map.Entry<Integer, List<TTicket>>> iterator = enqueueParam.entrySet().iterator();
            assertTrue(iterator.hasNext(), "Итератор не должен быть пустым");
            logger.info("2)Randomly select available doctor");
            //Рандомный индекс врача в списке (сначала врача, а затем и талончика)
            int selectedIndex = new Random().nextInt(enqueueParam.keySet().size());
            int selectedDoctorId = 0;
            int currentIndex = 0;
            for (Integer doctorId : enqueueParam.keySet()) {
                if (selectedIndex == currentIndex) {
                    selectedDoctorId = doctorId;
                    break;
                }
            }
            //Выбранный врач и его список талонов
            final List<TTicket> selectedDoctorTicketList = enqueueParam.get(selectedDoctorId);
            //Рандомный индекс талона в списке
            selectedIndex = new Random().nextInt(selectedDoctorTicketList.size());
            final TTicket ticketToQueueTest = selectedDoctorTicketList.get(selectedIndex);
            logger.info("Person[{}] and [{}]", selectedDoctorId, ticketToQueueTest);
            logger.info("Fifth step is to check empty queue (for patient and for doctor timeline).");
            checkEmptyQueue(id, selectedDoctorId, ticketToQueueTest.getDate());
            logger.info("Sixth step is to enqueuePatient and check this(for patient and for doctor timeline).");
            final int queueId = checkEnqueue(id, selectedDoctorId, ticketToQueueTest);

            logger.info("Seventh step is to dequeuePatient and check this(for patient and for doctor timeline)");
            checkDequeuePatient(id, selectedDoctorId, queueId, ticketToQueueTest);


        } catch (TException e) {
            logger.error("Cannot add Patient", e);
        }


    }

    private Map<Integer, List<TTicket>> checkAvailableDoctors() {
        //Id доктора-> Список свободных талоничков
        Map<Integer, List<TTicket>> result = new HashMap<Integer, List<TTicket>>();

        try {
            logger.info("1) Get all doctors from top-level LPU recursively");
            List<Person> allDoctorsList = client.getPersonnel(0, true, "");
            logger.info("1)Result is {}", allDoctorsList);
            final LocalDateTime startInterval = new LocalDateTime(2014, 6, 21, 9, 0, 0);
            final LocalDateTime endInterval = new LocalDateTime(2014, 6, 30, 0, 0, 0);
            // Параметры без ид врача
            final ScheduleParameters params = new ScheduleParameters();
            params.setBeginDateTime(DateConvertions.convertLocalDateTimeToUTCMilliseconds(startInterval));
            params.setEndDateTime(DateConvertions.convertLocalDateTimeToUTCMilliseconds(endInterval));
            params.setQuotingType(QuotingType.FROM_PORTAL);
            params.setHospitalUidFrom("");

            logger.info("2) Start search for available tickets from [{}] to [{}]", startInterval, endInterval);
            logger.info(params.toString());

            for (Person currentPerson : allDoctorsList) {
                logger.info("Start searching for Person[{}]", currentPerson.getId());
                final PersonSchedule currentResult = client.getPersonSchedule(params.setPersonId(currentPerson.getId()));

                logger.info(currentResult.toString());

                final List<TTicket> currentPersonFreeTicketList = new ArrayList<TTicket>();
                for (Map.Entry<Long, Schedule> currentSchedule : currentResult.getSchedules().entrySet()) {
                    for (TTicket currentTicket : currentSchedule.getValue().getTickets()) {
                        if (currentTicket.isAvailable() && currentTicket.isFree()) {
                            // Добавление всех свободных талонов
                            currentPersonFreeTicketList.add(currentTicket);
                        }
                    }
                }
                logger.info("Person[{}] has {} free tickets", currentPerson.getId(), currentPersonFreeTicketList.size());
                if (!currentPersonFreeTicketList.isEmpty()) {
                    result.put(currentPerson.getId(), currentPersonFreeTicketList);
                }
            }
            if (result.isEmpty()) {
                logger.warn("No one doctor has available tickets in next week");
            }
            return result;
        } catch (TException e) {
            logger.error("Невозможно получить врачей рекурсивно начиная с топ-уровня", e);
            fail("Error message", e);
        }
        fail("Unreachable point");
        return null;
    }

    private void checkDequeuePatient(int patientId, int doctorId, int queueId, TTicket ticket) throws TException {
        logger.info("1) Call dequeuePatient method");
        final DequeuePatientStatus dequeuePatientStatus = client.dequeuePatient(patientId, queueId);
        logger.info("1)Result is {}", dequeuePatientStatus);
        assertTrue(dequeuePatientStatus.isSuccess(), "1) Статус отзыва записи - провал отзыва записи к врачу");

        logger.info("2)  Call dequeuePatient method second time to get access denied");
        final DequeuePatientStatus incorrectDequeuePatientStatus = client.dequeuePatient(patientId, queueId);
        logger.info("2)Result is {}", incorrectDequeuePatientStatus);
        assertFalse(incorrectDequeuePatientStatus.isSuccess(), "2) повторная отзыв записи на то-же время прошел успешно");

        logger.info("After dequeue patient must have empty queue");
        checkEmptyQueue(patientId, doctorId, ticket.getDate() + ticket.getBegTime());
    }

    private int checkEnqueue(int patientId, int doctorId, TTicket ticket) {
        int queueId = 0;
        logger.info("1) Call enqueuePatient method");
        try {
            final EnqueuePatientStatus enqueuePatientStatus = client.enqueuePatient(new EnqueuePatientParameters()
                            .setHospitalUidFrom("REALLY NOT OUR LPU")
                            .setNote("Абракадабра")
                            .setPatientId(patientId)
                            .setPersonId(doctorId)
                            .setDateTime(ticket.getDate() + ticket.getBegTime())
            );
            logger.info("1)Result is {}", enqueuePatientStatus);
            queueId = enqueuePatientStatus.getQueueId();
            assertTrue(enqueuePatientStatus.isSuccess(), "1) Статус записи - провал записи к врачу");
        } catch (TException e) {
            fail("Cannot enqueuePatient", e);
        }

        logger.info("2)  Call enqueuePatient method second time to get access denied");
        try {
            final EnqueuePatientStatus incorrectEnqueuePatientStatus = client.enqueuePatient(new EnqueuePatientParameters()
                            .setHospitalUidFrom("")
                            .setNote("Алагамуса")
                            .setPatientId(patientId)
                            .setPersonId(doctorId)
                            .setDateTime(ticket.getDate() + ticket.getBegTime())
            );
            logger.info("2)Result is {}", incorrectEnqueuePatientStatus);
            assertFalse(incorrectEnqueuePatientStatus.isSuccess(), "2) повторная запись на то-же время прошла успешно");
        } catch (TException e) {
            fail("SecondTime enqueue fail with exception", e);
        }
        logger.info("4) Call getPatientQueue.");
        try {
            final List<Queue> patientQueue = client.getPatientQueue(patientId);
            logger.info("4)Result is {}", patientQueue);
            assertFalse(patientQueue.isEmpty(), "4) Никуда не записан, хотя должен быть");
            for (Queue currentQueue : patientQueue) {
                if (currentQueue.getQueueId() == queueId) {
                    logger.info("OK");
                    assertEquals(currentQueue.getDateTime(), ticket.getDate() + ticket.getBegTime(), "Запись не на то время");
                }
            }
        } catch (TException e) {
            fail("Get Patient queue after enqueue", e);
        }
        assertNotEquals(queueId, 0);
        return queueId;
    }

    private void checkEmptyQueue(Integer patientId, Integer doctorId, long dateTime) throws TException {
        logger.info("1) Call getPatientQueue.");
        final List<Queue> patientQueue = client.getPatientQueue(patientId);
        logger.info("1)Result is {}", patientQueue);
        assertTrue(patientQueue.isEmpty(), "1) Вновь созданный пациент не должен быть никуда записан");
    }

    private void getPatientInfoFromLinkedTest(String lastName, String firstName, String patrName, long birthDate, Integer id) throws TException {
        final Map<Integer, Patient> patientInfoMap = client.getPatientInfo(ImmutableList.of(id));
        logger.info("Received result from method \"getPatientInfo\" call = {}", patientInfoMap);
        assertEquals(patientInfoMap.size(), 1, "В возвращаемой коллекциии не ровно один пациент");
        assertTrue(patientInfoMap.containsKey(id), "Возвращаемая коллекция не имеет пациента с заданным ID");
        Patient patientInfo = patientInfoMap.get(id);
        logger.info("Result is {}", patientInfo);
        assertEquals(patientInfo.getFirstName(), firstName, "Имя несовпало");
        assertEquals(patientInfo.getLastName(), lastName, "Фамилия несовпала");
        assertEquals(patientInfo.getPatrName(), patrName, "Отчество несовпало");
        assertEquals(patientInfo.getBirthDate(), birthDate, "Дата рождения несовпала");
    }

    private void incorrectParamsTest(String lastName, String firstName, String patrName, long birthDate, Integer id) throws TException {
        logger.info("6.1) Send another parameters to findPatient method.");
        Map<String, String> documents = new HashMap<String, String>();
        documents.put("client_id", String.valueOf(id - 1));
        PatientStatus findStatus = client.findPatient(new FindPatientParameters()
                        .setLastName(lastName)
                        .setFirstName(firstName)
                        .setPatrName(patrName)
                        .setBirthDate(birthDate)
                        .setDocument(documents)
        );
        logger.info("6.1) Find patient by id result is {}", findStatus);
        assertFalse(findStatus.getPatientId() == id, "В заведомо неправильной выборке наш пациент!!!");
    }

    private void findPatientsByLastNameOnly(String lastName, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                        .setLastName(lastName)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.info("2) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "2) In recieved list there are no requested patient. Method findPatients failed");
        logger.info("2) Find patient by id result is {}", patientList);
    }

    private void findPatientsByFirstNameOnly(String firstName, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                        .setFirstName(firstName)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.info("3) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "3) In recieved list there are no requested patient. Method findPatients failed");
        logger.info("3) Find patient by id result is {}", patientList);
    }

    private void findPatientByDocument(String lastName, String firstName, String patrName, long birthDate, Integer id) throws TException {
        HashMap<String, String> documents = new HashMap<String, String>();
        documents.put("client_id", id.toString());
        PatientStatus findStatus = client.findPatient(new FindPatientParameters()
                        .setLastName(lastName)
                        .setFirstName(firstName)
                        .setPatrName(patrName)
                        .setBirthDate(birthDate)
                        .setDocument(documents)
        );
        logger.info("1) Find patient by id result is {}", findStatus);
        assertTrue(findStatus.isSuccess());
        assertTrue(findStatus.getPatientId() == id, "Не тот пациент!!!");
    }

    private void findPatient(String lastName, String firstName, String patrName, long birthDate, Integer id) throws TException {
        HashMap<String, String> documents = new HashMap<String, String>();
        documents.put("client_id", id.toString());
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                        .setLastName(lastName)
                        .setFirstName(firstName)
                        .setPatrName(patrName)
                        .setBirthDate(birthDate)
                        .setDocument(documents)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.info("6) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "6) In recieved list there are no requested patient. Method findPatients failed");
        logger.info("6) Find patient by id result is {}", patientList);

    }

    private void findPatientsByBirthDateOnly(long birthDate, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                        .setBirthDate(birthDate)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.info("4) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "4) In recieved list there are no requested patient. Method findPatients failed");
        logger.info("4) Find patient by id result is {}", patientList);
    }


}
