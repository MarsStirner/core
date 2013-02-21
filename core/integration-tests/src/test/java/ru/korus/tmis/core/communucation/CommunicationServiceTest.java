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
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Queue;

import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * User: eupatov
 * Date: 17.12.12 at 14:53
 */
public class CommunicationServiceTest {
    final static Logger logger = LoggerFactory.getLogger(CommunicationServiceTest.class);

    private Communications.Client client;
    private String[] hosts = {"10.2.1.58", "localhost"};
    private static int port = 7911;
    private static int timeout = 15000;
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
        }
        logger.info("Transport opened successfully");
    }

    @AfterClass
    public void closeConnection() {
        transport.close();
        logger.warn("End of all communication component test suite. Closing transport... ");
    }


    @Test(enabled = true)
    public void getOrganisationInfo() {
        logger.warn("Start of GetOrganisationInfo test:");
        String infisCodeParam = "500";
        Organization result;
        try {
            result = client.getOrganisationInfo(infisCodeParam);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.warn("Successful end of getOrganisationInfoTest");
        } catch (NotFoundException e) {
            logger.error("None of organisation found by this infisCode =" + infisCodeParam, e);
        } catch (TException e) {
            logger.error("Get organisationInfo test failed");
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void getSpecialities() {
        logger.warn("Start of GetSpecialities test:");
        String hospitalUIDParam = "580033";
        List<Speciality> result;
        try {
            result = client.getSpecialities(hospitalUIDParam);
            logger.info("Send and recieve is successfully done.");
            logger.info("Received list size=" + result.size());
            for (Speciality speciality : result) {
                logger.debug(speciality.toString());
            }
            logger.warn("Successful end of getSpecialities test.");
        } catch (NotFoundException e) {
            logger.error("No one speciality found or LPU is incorrect.", e);
        } catch (TException e) {
            logger.error("Exception on server side", e);
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void dequeuePatient() {
        logger.warn("Start of dequeuePatient test:");
        Integer dequeuePatientIdParam = 6227;
        Integer dequeuePatientQueueActionIdParam = 244074;
        DequeuePatientStatus status;
        try {
            status = client.dequeuePatient(dequeuePatientIdParam, dequeuePatientQueueActionIdParam);
            logger.info("Send and recieve is successfully done. Result is {}", status.toString());
            logger.warn("Successful end of dequeuePatient test.");
        } catch (NotFoundException e) {
            logger.error("Something not found", e);
        } catch (SQLException e) {
            logger.error("SQLEXCEPTION server side error", e);
            assertTrue(false);
        } catch (TException e) {
            logger.error("dequeuePatient test failed", e);
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void addPatient() {
        logger.warn("Start of addPatient test:");
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
            logger.warn("Successful end of addPatient test.");
        } catch (TException e) {
            logger.error("addPatient test FAILED", e);
        }
    }

    @Test(enabled = true)
    public void findPatient() {
        logger.warn("Start of findPatient test:");

        HashMap<String, String> documents = new HashMap<String, String>();
        documents.put("policy_type", "1");
        documents.put("serial", "57Р");
        documents.put("number", "055001");

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
            logger.warn("Successful end of findPatient test.");
        } catch (TException e) {
            logger.error("findPatient test FAILED", e);
        }
    }

    @Test(enabled = true)
    public void findPatients() {
        logger.warn("Start of findPatientS test:");
        FindMultiplePatientsParameters parameters = new FindMultiplePatientsParameters()
                .setLastName("Ив...")
                .setFirstName("*")
                .setPatrName("*")
                .setSex(2);
        List<Patient> result;
        try {
            result = client.findPatients(parameters);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info("Received list size=" + result.size());

            for (Patient patient : result) {
                logger.debug(patient.toString());
            }
            logger.warn("Successful end of findPatientS test.");
        } catch (TException e) {
            logger.error("findPatientS test FAILED", e);
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void findOrgStructureByAddress() {
        logger.warn("Start of findOrgStructureByAddress test");
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
            logger.warn("Successful end of findOrgStructureByAddress test.");
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found", e);
        } catch (TException e) {
            logger.error("Fail findOrgStructureByAddress test. Exception stacktrace:", e);
            assertTrue(false);
        }
    }


    @Test(enabled = true)
    public void getPatientInfo() {
        logger.warn("Start of getPatientInfo test:");
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
            logger.warn("Successful end of getPatientInfo test.");
        } catch (TException e) {
            logger.error("getPatientInfo test failed", e);
        }
    }

    @Test(enabled = true)
    public void getOrgStructures() {
        logger.warn("Start of getOrgStructures test:");
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
            logger.warn("Successful end of getOrgStructures test.");
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found", e);
        } catch (TException e) {
            logger.error("get OrgStructures test failed.", e);
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void getPersonnel() {
        logger.warn("Start of getPersonnel test:");
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
            logger.warn("Successful end of getPersonnel test.");
        } catch (TException e) {
            logger.error("getPersonnel Failed.", e);
        }
    }


    @Test(enabled = true)
    public void getWorkTimeAndStatus() {
        logger.warn("Start of getWorkTimeAndStatus test:");
        Amb result;
        GetTimeWorkAndStatusParameters parameters = new GetTimeWorkAndStatusParameters()
                .setPersonId(242)
                .setDate(new DateMidnight().getMillis())
                .setHospitalUidFrom("");
        try {
            result = client.getWorkTimeAndStatus(parameters);
            logger.info("Send and recieve is successfully done.");
            assertTrue(result != null);
            logger.info(result.toString());
            logger.warn("Successful end of getWorkTimeAndStatus test.");
        } catch (TException e) {
            logger.error("getWorkTimeAndStatus test failed.", e);
        }
    }

    @Test(enabled = true)
    public void enqueuePatient() {
        logger.warn("Start of enqueuePatient test:");
        EnqueuePatientStatus result;
        EnqueuePatientParameters parameters = new EnqueuePatientParameters()
                .setDateTime(new DateTime(2013, 1, 31, 15, 30, 0, 0).getMillis())
                .setPatientId(6226)
                .setPersonId(242);
        try {
            result = client.enqueuePatient(parameters);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.warn("Successful end of enqueuePatient test.");
        } catch (TException e) {
            logger.error("Fail of enqueuePatient test. Exception stacktrace:", e);
        }
    }

    @Test(enabled = true)
    public void getPatientQueue() {
        logger.warn("Start getPatientQueue test:");
        List<Queue> result;
        Integer patientId = 6226;
        try {
            result = client.getPatientQueue(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.warn("Successful end of getpatientQueue test.");
        } catch (TException e) {
            logger.error("Fail of getPatientQueue test. Exception stacktrace:", e);
        }
    }

    @Test(enabled = true)
    public void getPatientContacts() {
        logger.warn("Start getPatientContacts test:");
        List<Contact> result;
        Integer patientId = 6226;
        try {
            result = client.getPatientContacts(patientId);
            logger.info("Send and recieve is successfully done. Result is {}", result.toString());
            logger.warn("Successful end of getPatientContacts test.");
        } catch (TException e) {
            logger.error("getPatientContacts Failed.", e);
        }
    }

    @Test(enabled = true)
    public void linkedTest() {
        logger.warn(
                "Start of linked test. Try to add patient, get patientInfo on him and check enqueue/dequeue to doctor");
        final String lastName = "Фамилия";
        final String firstName = "Имя";
        final String patrName = "Отчество";
        final long birthDate = new DateMidnight(1991, 9, 19).getMillis();
        Integer id = 0;
        try {
            final AddPatientParameters patient = new AddPatientParameters()
                    .setLastName(lastName)
                    .setLastName(firstName)
                    .setPatrName(patrName)
                    .setBirthDate(birthDate);
            logger.info("First step is try to addPatient: {}", patient);
            final PatientStatus addStatus;

            addStatus = client.addPatient(patient);

            assertTrue(addStatus != null, "Добавление пациента не удалось");
            assertTrue(addStatus.isSuccess(), "Пациент не добавлен");

            id = addStatus.getPatientId();
            logger.debug("Patient successfully added, ID={}", id);

            logger.info("Second step is try to find Patient [{}] by multiple ways:", patient);
            HashMap<String, String> documents = new HashMap<String, String>();
            documents.put("client_id", id.toString());

            client.findPatient(new FindPatientParameters()
                    .setLastName(lastName)
                    .setFirstName(firstName)
                    .setPatrName(patrName)
                    .setBirthDate(birthDate)
                    .setDocument(documents)
            );

        } catch (TException e) {
            logger.error("Cannot add Patient", e);
        }


    }
}
