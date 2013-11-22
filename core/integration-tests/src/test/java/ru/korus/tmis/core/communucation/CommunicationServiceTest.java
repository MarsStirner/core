package ru.korus.tmis.core.communucation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.korus.tmis.communication.DateConvertions;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Queue;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

/**
 * User: eupatov
 * Date: 17.12.12 at 14:53
 */
@Test(enabled = true, groups = {"communication"})
public class CommunicationServiceTest {
    final static Logger logger = LoggerFactory.getLogger(CommunicationServiceTest.class);

    private Communications.Client client;
    private String[] hosts = {"10.2.1.58", "localhost", "192.168.1.100"};
    private static int port = 7911;
    private static int timeout = 3500000;
    private TTransport transport;

    @BeforeClass
    public void initConnection() {
        transport = new TSocket(hosts[1], CommunicationServiceTest.port, CommunicationServiceTest.timeout);
        logger.debug("Transport success");
        TProtocol protocol = new TBinaryProtocol(transport);
        logger.debug("Protocol success");
        client = new Communications.Client(protocol);
        logger.debug("Client created");
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
    public void closeConnection() {
        transport.close();
        logger.warn("End of all communication component test suite. Closing transport... ");
    }

    @Test(enabled = true)
    public void findPatientByPolicyAndDocument() {
        logger.info("Start of findPatientByPolicyAndDocument test.");
        List<PatientStatus> status = new ArrayList<PatientStatus>(28);
        List<FindPatientByPolicyAndDocumentParameters> params = new ArrayList<FindPatientByPolicyAndDocumentParameters>(status.size());
        final File paramsFile = new File(
                this.getClass().getResource("").getPath()
                        .concat("/../../../../../communication/findPatientsByPolicyAndDocuments.csv"));
        logger.info("Path to params file: {}", paramsFile.getAbsolutePath());
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramsFile)));
            String line;
            short i = -1;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            fileReader.readLine(); //skip first line with headers
            while ((line = fileReader.readLine()) != null) {
                i++;
                String[] partsToParse = line.split(";");
                try {
                    FindPatientByPolicyAndDocumentParameters newParam = new FindPatientByPolicyAndDocumentParameters();
                    newParam.setLastName(partsToParse[0]); //lastName
                    newParam.setFirstName(partsToParse[1]); //firstName
                    newParam.setPatrName(partsToParse[2]); //patrName
                    newParam.setSex(Short.parseShort(partsToParse[3])); //sex
                    newParam.setBirthDate(DateConvertions.convertDateToUTCMilliseconds(formatter.parse(partsToParse[4]))); //birthDate
                    newParam.setDocumentSerial(partsToParse[9]); //doc serial
                    newParam.setDocumentNumber(partsToParse[10]); //doc number
                    newParam.setDocumentTypeCode(partsToParse[11]); //doc typeCode
                    newParam.setPolicySerial(partsToParse[5]); //pol serial
                    newParam.setPolicyNumber(partsToParse[6]); //pol number
                    newParam.setPolicyTypeCode(partsToParse[7]); //pol typeCode
                    newParam.setPolicyInsurerInfisCode(partsToParse[8]);
                    params.add(newParam);
                    logger.info("Test case #{}::{}", i, newParam);
                } catch (ParseException e) {
                    logger.error("#{}: Cannot parse (PE) {}", i, Arrays.toString(partsToParse));
                } catch (NumberFormatException e) {
                    logger.error("#{}: Cannot parse (NFE) {}", i, Arrays.toString(partsToParse));
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            logger.error("File {} not found", paramsFile.getAbsolutePath());
            fail("No params file founded");
        } catch (IOException e) {
            fail("ERROR: I/O exception while reading file.");
        }
        Short i = -1;
        for (FindPatientByPolicyAndDocumentParameters currentParams : params) {
            i++;
            try {
                if (currentParams != null) {
                    PatientStatus currentStatus = client.findPatientByPolicyAndDocument(currentParams);
                    logger.info("{}\t|\t{}", i, currentStatus);
                } else {
                    logger.info("{}\t|\t{}", i, "skip");
                }
            } catch (NotFoundException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getError_msg());
            } catch (InvalidPersonalInfoException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getMessage());
            } catch (InvalidDocumentException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getMessage());
            } catch (AnotherPolicyException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getMessage());
            } catch (NotUniqueException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getMessage());
            } catch (TException e) {
                logger.info(i+"\t|\t{}:{}", e.getClass().getName(), e.getMessage());
            }
        }
    }


    @Test(enabled = true)
    public void getOrganisationInfo() {
        logger.info("Start of GetOrganisationInfo test:");
        String infisCodeParam = "500";
        Organization result;
        try {
            result = client.getOrganisationInfo(infisCodeParam);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of getOrganisationInfoTest");
        } catch (NotFoundException e) {
            logger.error("None of organisation found by this infisCode[{}]: {}",
                    infisCodeParam, e.getError_msg());
        } catch (TException e) {
            logger.error("Get organisationInfo test failed");
            fail();
        }
    }

    @Test(enabled = true)
    public void getSpecialities() {
        logger.info("Start of GetSpecialities test:");
        String hospitalUIDParam = "500";
        List<Speciality> result;
        try {
            result = client.getSpecialities(hospitalUIDParam);
            logger.info("Send and recieve is successfully done.");
            logger.info("Received list size=" + result.size());
            for (Speciality speciality : result) {
                logger.debug(speciality.toString());
            }
            logger.info("Successful end of getSpecialities test.");
        } catch (NotFoundException e) {
            logger.error("No one speciality found or LPU is incorrect.", e);
        } catch (TException e) {
            logger.error("Exception on server side", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void dequeuePatient() {
        logger.info("Start of dequeuePatient test:");
        Integer dequeuePatientIdParam = 6227;
        Integer dequeuePatientQueueActionIdParam = 244074;
        DequeuePatientStatus status;
        try {
            status = client.dequeuePatient(dequeuePatientIdParam, dequeuePatientQueueActionIdParam);
            logger.info("Send and recieve is successfully done. Result is {}", status.toString());
            logger.info("Successful end of dequeuePatient test.");
        } catch (NotFoundException e) {
            logger.error("Something not found", e);
        } catch (SQLException e) {
            logger.error("SQLEXCEPTION server side error", e);
            fail();
        } catch (TException e) {
            logger.error("dequeuePatient test failed", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void addPatient() {
        logger.info("Start of addPatient test:");
        PatientStatus status;
        AddPatientParameters parameters = new AddPatientParameters()
                .setLastName("ТЕСТОВ3")
                .setFirstName("ТЕСТ1")
                .setBirthDate(new Date().getTime())
                .setSex(1);
        try {
            status = client.addPatient(parameters);
            assertTrue(status.isSuccess(), "Статус удачный");
            assertTrue(status.getPatientId() > 0);
            logger.info("Send and recieve is successfully done. Result is {}", status.toString());
            logger.info("Successful end of addPatient test.");
        } catch (TException e) {
            logger.error("addPatient test FAILED", e);
        }
    }

    @Test(enabled = true)
    public void findPatient() {
        logger.info("Start of findPatient test:");

        HashMap<String, String> documents = new HashMap<String, String>();
//        documents.put("policy_type", "1");
//        documents.put("serial", "57Р");
//        documents.put("number", "055001");
        documents.put("client_id", "3555");

        final long birthDate = new DateMidnight(2005, 5, 6, DateTimeZone.UTC).getMillis();


        FindPatientParameters parameters = new FindPatientParameters()
                .setLastName("Абдикаримова")
                .setFirstName("Елена")
                .setPatrName("Игоревна")
                .setSex(2)
                .setDocument(documents)
                .setBirthDate(birthDate);
        PatientStatus status;
        try {
            status = client.findPatient(parameters);
            logger.info("Send and recieve is successfully done. Result is {}", status.toString());
            logger.info("Successful end of findPatient test.");
        } catch (TException e) {
            logger.error("findPatient test FAILED", e);
        }
    }

    @Test(enabled = true)
    public void findPatients() {
        logger.info("Start of findPatientS test:");
        FindMultiplePatientsParameters parameters = new FindMultiplePatientsParameters()
                .setLastName("Иванов")
                .setFirstName("Иван")
                .setPatrName("*")
                .setSex(1);
        List<Patient> result;
        try {
            result = client.findPatients(parameters);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());

            for (Patient patient : result) {
                logger.debug(patient.toString());
            }
            logger.info("Successful end of findPatientS test.");
        } catch (TException e) {
            logger.error("findPatientS test FAILED", e);
            fail();
        }
    }

    @Test(enabled = true)
    public void findOrgStructureByAddress() {
        logger.info("Start of findOrgStructureByAddress test");
        List<Integer> result;
        FindOrgStructureByAddressParameters parameters = new FindOrgStructureByAddressParameters()
                .setFlat(10)
                .setPointKLADR("7800000500000")
                .setStreetKLADR("78000005000003600")
                .setCorpus("")
                .setNumber("1");
        try {
            result = client.findOrgStructureByAddress(parameters);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());
            for (Integer orgStructureId : result) {
                logger.debug(orgStructureId.toString());
            }
            logger.info("Successful end of findOrgStructureByAddress test.");
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found: {}", e.getError_msg());
        } catch (TException e) {
            logger.error("Fail findOrgStructureByAddress test. Exception stacktrace:", e);
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
        ids.add(152);
        ids.add(1);
        HashMap<Integer, PatientInfo> result;
        try {
            result = (HashMap<Integer, PatientInfo>) client.getPatientInfo(ids);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());
            for (Integer key : result.keySet()) {
                logger.debug("KEY:" + key + " VALUE:" + result.get(key));
            }
            logger.info("Successful end of getPatientInfo test.");
        } catch (TException e) {
            logger.error("getPatientInfo test failed", e);
        }
    }

    @Test(enabled = true)
    public void getOrgStructures() {
        logger.info("Start of getOrgStructures test:");
        List<OrgStructure> result;
        Integer parentOrgStructureId = 3;
        boolean recursive = true;
        String infisCode = "";
        try {
            result = client.getOrgStructures(parentOrgStructureId, recursive, infisCode);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());

            for (OrgStructure orgStructure : result) {
                logger.debug(orgStructure.toString());
            }
            logger.info("Successful end of getOrgStructures test.");
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found: {}", e.getError_msg());
        } catch (TException e) {
            logger.error("get OrgStructures test failed.", e);
            fail("Get OrgStructures failed");
        }
    }

    @Test(enabled = true)
    public void getPersonnel() {
        logger.info("Start of getPersonnel test:");
        List<Person> result;
        Integer orgStructureId = 0;
        boolean recursive = true;
        String infisCode = "";
        try {
            result = client.getPersonnel(orgStructureId, recursive, infisCode);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());

            for (Person person : result) {
                logger.debug(person.toString());
            }
            logger.info("Successful end of getPersonnel test.");
        } catch (TException e) {
            logger.error("getPersonnel Failed.", e);
        }
    }


    @Test(enabled = true)
    public void getWorkTimeAndStatus() {
        logger.info("Start of getWorkTimeAndStatus test:");
        Amb result;
        GetTimeWorkAndStatusParameters parameters = new GetTimeWorkAndStatusParameters()
                .setPersonId(242)
                .setDate(new DateMidnight().getMillis());
        // .setHospitalUidFrom("");
        try {
            result = client.getWorkTimeAndStatus(parameters);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info(result.toString());
            logger.info("Successful end of getWorkTimeAndStatus test.");
        } catch (NotFoundException e) {
            logger.error("getWorkTimeAndStatus test failed. #{}#", e.getError_msg());
        } catch (TException e) {
            logger.error("getWorkTimeAndStatus test failed. {} ", e.getMessage(), e);
        }
    }

    @Test(enabled = true)
    public void enqueuePatient() {
        logger.info("Start of enqueuePatient test:");
        EnqueuePatientStatus result;
        EnqueuePatientParameters parameters = new EnqueuePatientParameters()
                .setDateTime(new DateTime(2013, 1, 31, 15, 30, 0, 0).getMillis())
                .setPatientId(6226)
                .setPersonId(242);
        try {
            result = client.enqueuePatient(parameters);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of enqueuePatient test.");
        } catch (TException e) {
            logger.error("Fail of enqueuePatient test. Exception stacktrace:", e);
        }
    }

    @Test(enabled = true)
    public void getPatientQueue() {
        logger.info("Start getPatientQueue test:");
        List<Queue> result;
        Integer patientId = 6226;
        try {
            result = client.getPatientQueue(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of getpatientQueue test.");
        } catch (TException e) {
            logger.error("Fail of getPatientQueue test. Exception stacktrace:", e);
        }
    }

    @Test(enabled = true)
    public void getPatientContacts() {
        logger.info("Start getPatientContacts test:");
        List<Contact> result;
        Integer patientId = 6226;
        try {
            result = client.getPatientContacts(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.info("Successful end of getPatientContacts test.");
        } catch (TException e) {
            logger.error("getPatientContacts Failed.", e);
        }
    }

    @Test(enabled = true)
    public void linkedTest() {
        logger.info(
                "Start of linked test. Try to add patient, get patientInfo on him and check enqueue/dequeue to doctor");
        final String lastName = "Фамилия";
        final String firstName = "Имя";
        final String patrName = "Отчество";
        final long birthDate = new DateMidnight(1999, 9, 19, DateTimeZone.UTC).getMillis();

        Integer id;
        try {
            final AddPatientParameters patient = new AddPatientParameters()
                    .setLastName(lastName)
                    .setFirstName(firstName)
                    .setPatrName(patrName)
                    .setBirthDate(birthDate);
            logger.info("First step is try to addPatient: {}", patient);
            final PatientStatus addStatus;

            addStatus = client.addPatient(patient);
            logger.info("AddStatus for patient is {}", addStatus);
            assertTrue(addStatus != null, "Добавление пациента не удалось");
            assertTrue(addStatus.isSuccess(), "Пациент не добавлен");

            id = addStatus.getPatientId();
            logger.debug("Patient successfully added, ID={}", id);

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

            logger.info("6) Send another parameters to findPatient(-s) method to avoid recieve our patient.");
            incorrectParamsTest(lastName, firstName, patrName, birthDate, id);

            logger.info("Fourth step is to find free ticket to a doctor and find a doctor too.");
            Map<Integer, List<DateTime>> enqueueParam = checkAvailableDoctors();
            if (enqueueParam.isEmpty()) {
                logger.warn("No one pair doctorId, dateTime");
                return;
            }
            logger.info("1) Available tickets is {}", enqueueParam);
            Iterator<Map.Entry<Integer, List<DateTime>>> iterator = enqueueParam.entrySet().iterator();
            assertTrue(iterator.hasNext(), "Итератор не должен быть пустым");
            logger.info("2)Randomly select available doctor");
            //Рандомный индекс в списке (сначала врача, а затем и талончика)
            int selectedIndex = new Random().nextInt(enqueueParam.keySet().size());
            //Выбранный врач
            Map.Entry<Integer, List<DateTime>> selectedDoctorWithFreeTickets = null;
            int currentIndex = 0;
            while (iterator.hasNext()) {
                if (selectedIndex == currentIndex) {
                    selectedDoctorWithFreeTickets = iterator.next();
                    break;
                }
                currentIndex++;
                iterator.next();
            }
            logger.info("Selected doctor with free tickets is {}", selectedDoctorWithFreeTickets);
            final int doctorId = selectedDoctorWithFreeTickets.getKey();
            final long queueDate =
                    new DateMidnight(selectedDoctorWithFreeTickets.getValue().get(0).getMillis(), DateTimeZone.UTC)
                            .getMillis();

            logger.info("Fifth step is to check empty queue (for patient and for doctor timeline).");
            assertTrue(checkEmptyQueue(id, selectedDoctorWithFreeTickets.getKey(), queueDate),
                    "Вновь созданный пациент уже содержит запись к врачу");


            logger.info("Sixth step is to enqueuePatient and check this(for patient and for doctor timeline).");
            final int queueId = checkEnqueue(id, doctorId, selectedDoctorWithFreeTickets.getValue());

            logger.info("Seventh step is to dequeuePatient and check this(for patient and for doctor timeline)");
            checkDequeuePatient(id, doctorId, queueId, queueDate);


        } catch (TException e) {
            logger.error("Cannot add Patient", e);
        }


    }

    private Map<Integer, List<DateTime>> checkAvailableDoctors() {
        //Id доктора-> Список свободных талоничков
        Map<Integer, List<DateTime>> result = new HashMap<Integer, List<DateTime>>(1);

        try {
            logger.info("1) Get all doctors from top-level LPU recursively");
            List<Person> allDoctorsList = client.getPersonnel(0, true, "");
            logger.info("1)Result is {}", allDoctorsList);
            logger.info("2) Start search for available tickets from today");
            //Поисковый интервал = неделя
            final int searchIntervalInDays = 7;
            //Дата с которой мы ищем талончики
            DateMidnight requestDate = new DateMidnight(DateTimeZone.UTC);

            int currentDay = 0;
            while (result.isEmpty() && currentDay < searchIntervalInDays) {
                //Текущая поисковая дата
                DateMidnight searchTime = requestDate.plusDays(currentDay);
                logger.info("Search available tickets for {}", searchTime);
                for (Person currentDoctor : allDoctorsList) {
                    Amb currentAmb;
                    try {
                        currentAmb = client.getWorkTimeAndStatus(new GetTimeWorkAndStatusParameters()
                                .setDate(searchTime.getMillis())
                                .setPersonId(currentDoctor.getId())
                                .setHospitalUidFrom("")
                        );
                    } catch (NotFoundException exc) {
                        continue;
                    }
                    if (!currentAmb.getTickets().isEmpty()) {
                        //Список свободных времен для записи
                        List<DateTime> freeTicketsTime = new ArrayList<DateTime>(currentAmb.getTicketsSize());

                        for (Ticket currentTicket : currentAmb.getTickets()) {
                            if (currentTicket.getFree() == 1 && currentTicket.getAvailable() == 1) {
                                freeTicketsTime.add(
                                        new DateTime(searchTime.getMillis() + currentTicket.getTime(), DateTimeZone.UTC));
                            }
                        }
                        //добавление записи вида {Id врача-> Список свободных талончиков}
                        if (!freeTicketsTime.isEmpty()) {
                            result.put(currentDoctor.getId(), freeTicketsTime);
                        }

                    }
                }
                currentDay++;
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

    private void checkDequeuePatient(Integer id, int doctorId, int queueId, long queueDate) throws TException {
        logger.info("1) Call dequeuePatient method");
        final DequeuePatientStatus dequeuePatientStatus = client.dequeuePatient(id, queueId);
        logger.info("1)Result is {}", dequeuePatientStatus);
        assertTrue(dequeuePatientStatus.isSuccess(), "1) Статус отзыва записи - провал отзыва записи к врачу");

        logger.info("2)  Call dequeuePatient method second time to get access denied");
        final DequeuePatientStatus incorrectDequeuePatientStatus = client.dequeuePatient(id, queueId);
        logger.info("2)Result is {}", incorrectDequeuePatientStatus);
        assertFalse(incorrectDequeuePatientStatus.isSuccess(), "2) повторная отзыв записи на то-же время прошел успешно");

        logger.info("After dequeue patient must have empty queue");
        checkEmptyQueue(id, doctorId, queueDate);
    }

    private int checkEnqueue(Integer id, int doctorId, List<DateTime> freeTimes) throws TException {
        //Случайным образом выбранное свободное время (selectedDateTime и следующее время nextSelectedDateTime)
        Random random = new Random();
        int randomIndex = random.nextInt(freeTimes.size());
        int nextRandomIndex = random.nextInt(freeTimes.size());
        while (randomIndex == nextRandomIndex) {
            nextRandomIndex = random.nextInt(freeTimes.size());
        }
        final DateTime selectedDateTime = freeTimes.get(randomIndex);
        final DateTime nextSelectedDateTime = freeTimes.get(nextRandomIndex);
        assertNotEquals(selectedDateTime.getMillis(), nextSelectedDateTime.getMillis(), "Времена совпали");
        logger.info("Selected {}, next {}", selectedDateTime, nextSelectedDateTime);

        final int queueId;
        final int index;
        logger.info("1) Call enqueuePatient method");
        final EnqueuePatientStatus enqueuePatientStatus = client.enqueuePatient(new EnqueuePatientParameters()
                .setHospitalUidFrom("REALLY NOT OUR LPU")
                .setNote("Абракадабра")
                .setPatientId(id)
                .setPersonId(doctorId)
                .setDateTime(selectedDateTime.getMillis())
        );
        logger.info("1)Result is {}", enqueuePatientStatus);
        queueId = enqueuePatientStatus.getQueueId();
        index = enqueuePatientStatus.getIndex();
        assertTrue(enqueuePatientStatus.isSuccess(), "1) Статус записи - провал записи к врачу");

        logger.info("2)  Call enqueuePatient method second time to get access denied");
        final EnqueuePatientStatus incorrectEnqueuePatientStatus = client.enqueuePatient(new EnqueuePatientParameters()
                .setHospitalUidFrom("")
                .setNote("Алагамуса")
                .setPatientId(id)
                .setPersonId(doctorId)
                .setDateTime(selectedDateTime.getMillis())
        );
        logger.info("2)Result is {}", incorrectEnqueuePatientStatus);
        assertFalse(incorrectEnqueuePatientStatus.isSuccess(), "2) повторная запись на то-же время прошла успешно");

        logger.info("3) Call enqueuePatient method to next time at this day, to get access denied (Check repetition)");
        final EnqueuePatientStatus repetitionEnqueuePatientStatus = client.enqueuePatient(new EnqueuePatientParameters()
                .setHospitalUidFrom("")
                .setNote("Запись на тот-же день не должна была пройти")
                .setPatientId(id)
                .setPersonId(doctorId)
                .setDateTime(nextSelectedDateTime.getMillis())
        );
        logger.info("3)Result is {}", repetitionEnqueuePatientStatus);
        assertFalse(repetitionEnqueuePatientStatus.isSuccess(), "3) Запись на тот-же день не должна была пройти");

        logger.info("4) Call getWorkTimeAndStatus");
        final Amb amb = client.getWorkTimeAndStatus(new GetTimeWorkAndStatusParameters()
                .setPersonId(doctorId)
                .setDate((selectedDateTime.toDateMidnight()).getMillis())
                .setHospitalUidFrom("")
        );
        logger.info("4)Result is {}", amb);
        for (Ticket currentTicket : amb.getTickets()) {
            if (currentTicket.getTime() == selectedDateTime.getMillis()) {
                assertTrue(currentTicket.free == 0 && currentTicket.getAvailable() == 0,
                        "Талончик помечен как свободный даже после записи");
                break;
            }
        }

        logger.info("4) Call getPatientQueue.");
        final List<Queue> patientQueue = client.getPatientQueue(id);
        logger.info("4)Result is {}", patientQueue);
        assertFalse(patientQueue.isEmpty(), "4) Никуда записан, хотя должен быть");
        for (Queue currentQueue : patientQueue) {
            if (currentQueue.getQueueId() == queueId) {
                logger.info("OK");
                assertEquals(currentQueue.getDateTime(), selectedDateTime.getMillis(), "Запись не на то время");
                assertEquals(index, currentQueue.getIndex(), "Индекс записи не совпадает");
            }
        }
        return queueId;
    }

    private boolean checkEmptyQueue(Integer id, Integer doctorId, long dateTime) throws TException {
        logger.info("1) Call getPatientQueue.");
        final List<Queue> patientQueue = client.getPatientQueue(id);
        logger.info("1)Result is {}", patientQueue);
        if (!patientQueue.isEmpty()) {
            logger.error("1) Вновь созданный пациент не должен быть никуда записан");
            return false;
        }
        logger.info("2) Call getWorkTimeAndStatus");
        final Amb amb = client.getWorkTimeAndStatus(new GetTimeWorkAndStatusParameters()
                .setPersonId(doctorId)
                .setDate(dateTime)
                .setHospitalUidFrom("")
        );
        logger.info("2)Result is {}", amb);
        for (Ticket currentTicket : amb.getTickets()) {
            if (currentTicket.getPatientId() == id) {
                //Если доктор содержит тикет на этого пациента, то это ошибка (return false)
                return false;
            }
        }
        return true;
    }

    private void getPatientInfoFromLinkedTest(String lastName, String firstName, String patrName, long birthDate, Integer id) throws TException {
        List<Integer> idPseudoList = new ArrayList<Integer>(1);
        idPseudoList.add(id);
        final Map<Integer, PatientInfo> patientInfoMap = client.getPatientInfo(idPseudoList);
        logger.info("Recieved result from method \"getPatientInfo\" call = {}", patientInfoMap);
        assertEquals(patientInfoMap.size(), 1, "В возвращаемой коллекциии не ровно один пациент");
        assertTrue(patientInfoMap.containsKey(id), "Возвращаемая коллекция не имеет пациента с заданным ID");
        PatientInfo patientInfo = patientInfoMap.get(id);
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
        logger.debug("6.1) Find patient by id result is {}", findStatus);
        assertFalse(findStatus.getPatientId() == id, "В заведомо неправильной выборке наш пациент!!!");
    }

    private void findPatientsByLastNameOnly(String lastName, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                .setLastName(lastName)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.debug("2) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "2) In recieved list there are no requested patient. Method findPatients failed");
        logger.debug("2) Find patient by id result is {}", patientList);
    }

    private void findPatientsByFirstNameOnly(String firstName, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                .setFirstName(firstName)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.debug("3) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "3) In recieved list there are no requested patient. Method findPatients failed");
        logger.debug("3) Find patient by id result is {}", patientList);
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
        logger.debug("1) Find patient by id result is {}", findStatus);
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
                logger.debug("6) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "6) In recieved list there are no requested patient. Method findPatients failed");
        logger.debug("6) Find patient by id result is {}", patientList);

    }

    private void findPatientsByBirthDateOnly(long birthDate, Integer id) throws TException {
        List<Patient> patientList = client.findPatients(new FindMultiplePatientsParameters()
                .setBirthDate(birthDate)
        );
        boolean findIsSuccessfull = false;
        for (Patient currentPatient : patientList) {
            if (currentPatient.getId() == id) {
                logger.debug("4) Result contains requested patient: It is \"{}\"", currentPatient);
                findIsSuccessfull = true;
            }
        }
        assertTrue(findIsSuccessfull,
                "4) In recieved list there are no requested patient. Method findPatients failed");
        logger.debug("4) Find patient by id result is {}", patientList);
    }



    @Test(enabled = true)
    public void getFirstFreeTicket() {
        logger.info("Start of getFirstFreeTicket test:");
       try {
           FreeTicket result = client.getFirstFreeTicket(new ScheduleParameters().setPersonId(303).setBeginDateTime(0).setHospitalUidFrom("").setQuotingType(QuotingType.FROM_PORTAL));
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received = {}", result.toString());
            logger.info("Successful end of getFirstFreeTicket test.");
        } catch (TException e) {
            logger.error("getFirstFreeTicket test failed", e);
        }
    }


    @Test(enabled = true)
    public void checkForNewQueueCoupons(){
        try {
            final List<QueueCoupon> result = client.checkForNewQueueCoupons();
            for(QueueCoupon current : result){
                logger.info(current.toString());
            }
        } catch (TException e) {
            logger.error("Failed to checkForNewQueueCoupons: ", e);
            fail("Not working");
        }
    }
}
