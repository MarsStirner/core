package ru.korus.tmis.core.communucation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.joda.time.DateTime;
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
    }


    @Test(enabled = true)
    public void getOrganisationInfo() {
        logger.warn("GetOrganisationInfo");

        String infisCodeParam = "500";
        Organization result;
        try {
            result = client.getOrganisationInfo(infisCodeParam);
            logger.debug(result.toString());
            logger.warn("End");
        } catch (NotFoundException e) {
            logger.error("None of organisation found by this infisCode =" + infisCodeParam, e);
        } catch (TException e) {
            logger.error("Get organisationInfo test failed");
            assertTrue(false);
        }
    }

    @Test(enabled = true)
    public void getSpecialities() {
        logger.warn("GetSpecialitiesTest");
        String hospitalUIDParam = "580033";
        List<Speciality> result;
        try {
            result = client.getSpecialities(hospitalUIDParam);

            logger.info("Received list size=" + result.size());

            for (Speciality speciality : result) {
                logger.debug(speciality.toString());
            }
        } catch (NotFoundException e) {
            logger.error("No one speciality found or LPU is incorrect.", e);
        } catch (TException e) {
            logger.error("Exception on server side", e);
            assertTrue(false);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void dequeuePatient() {
        logger.warn("dequeuePatientTest");
        Integer dequeuePatientIdParam = 6227;
        Integer dequeuePatientQueueActionIdParam = 244074;
        DequeuePatientStatus status;
        try {
            status = client.dequeuePatient(dequeuePatientIdParam, dequeuePatientQueueActionIdParam);
            logger.debug(status.toString());
        } catch (NotFoundException e) {
            logger.error("Something not found", e);
        } catch (SQLException e) {
            logger.error("SQLEXCEPTION server side error", e);
            assertTrue(false);
        } catch (TException e) {
            logger.error("dequeuePatient test failed", e);
            assertTrue(false);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void addPatient() {
        logger.warn("addPatientTest");
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
            logger.debug(status.toString());
        } catch (TException e) {
            logger.error("addPatient test FAILED", e);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void findPatient() {
        logger.warn("findPatientTest");
        FindPatientParameters parameters = new FindPatientParameters()
                .setLastName("Ив...")
                .setFirstName("Арина")
                .setSex(2);
        PatientStatus status;
        try {
            status = client.findPatient(parameters);
            logger.debug(status.toString());
        } catch (TException e) {
            logger.error("findPatient test FAILED", e);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void findPatients() {
        logger.warn("findPatientSTest");
        FindPatientParameters parameters = new FindPatientParameters()
                .setLastName("Ив...")
                .setFirstName("*")
                .setBirthDate(473040000000l)
                .setSex(2);
        List<Patient> status;
        try {
            status = client.findPatients(parameters);
            assertTrue(status != null);
            for (Patient patient : status) {
                logger.debug(patient.toString());
            }
        } catch (TException e) {
            logger.error("findPatientS test FAILED", e);
            assertTrue(false);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void findOSbyADDRESS() {
        logger.warn("findOS BY ADDRESS test");
        List<Integer> result;
        FindOrgStructureByAddressParameters parameters = new FindOrgStructureByAddressParameters()
                .setFlat(10)
                .setPointKLADR("7800000500000")
                .setStreetKLADR("78000005000003600")
                .setCorpus("")
                .setNumber("1");
        try {
            result = client.findOrgStructureByAddress(parameters);
            assert (result != null);
            assert (result.size() > 0);
            System.out.println("Received list size=" + result.size());
            for (Integer id : result) {
                logger.debug("ID:" + id);
            }
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found", e);
        } catch (TException e) {
            logger.error("findOsbyAddress test failed.", e);
            assertTrue(false);
        }
        logger.warn("End");
    }


    @Test(enabled = true)
    public void getPatientInfo() {
        logger.warn("getPatientINFO test");
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(414);
        ids.add(212);
        ids.add(1000);
        ids.add(152);
        ids.add(1);
        HashMap<Integer, PatientInfo> result;
        try {
            result = (HashMap<Integer, PatientInfo>) client.getPatientInfo(ids);
            logger.info("Received list size=" + result.size());
            for (Integer key : result.keySet()) {
                logger.debug("KEY:" + key + " VALUE:" + result.get(key));
            }
        } catch (TException e) {
            logger.error("getPatientInfo test failed", e);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void getOrgStructures() {
        logger.warn("GetOrgStructuresTest");
        List<OrgStructure> result;
        try {
            result = client.getOrgStructures(3, true, "");
            logger.info("Received list size=" + result.size());
            for (OrgStructure structure : result) {
                logger.debug(structure.toString());
            }
        } catch (NotFoundException e) {
            logger.error("OrgStructure not found", e);
        } catch (TException e) {
            logger.error("get OrgStructures test failed.", e);
            assertTrue(false);
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    public void getPersonnel() {
        logger.warn("GetPersonnelTest");
        List<Person> result = null;
        try {
            result = client.getPersonnel(0, true, "");
        } catch (TException e) {
            logger.error("getPersonnel Failed.", e);
        }

        logger.info("Received list size=" + result.size());
        for (Person person : result) {
            logger.debug(person.toString());
        }
        logger.warn("End");
    }


    @Test(enabled = true)
    public void getWorkTimeAndStatus() {
        logger.warn("getWorkTimeAndStatusTest");
        Amb result = null;
        try {
            result = client.getWorkTimeAndStatus(new GetTimeWorkAndStatusParameters().setPersonId(242)
                    .setDate(new DateTime(2013, 1, 31, 0, 0, 0, 0).getMillis()).setHospitalUidFrom(0));
        } catch (TException e) {
            logger.error("getWorkTimeAndStatus Failed.", e);
        }

        logger.info(result.toString());
        logger.warn("End");

    }

    @Test(enabled = true)
    public void enqueuePatient() {
        logger.warn("getWorkTimeAndStatusTest");

        EnqueuePatientStatus result = null;
        try {
            result = client.enqueuePatient(new EnqueuePatientParameters().setDateTime(new DateTime(2013, 1, 31, 15, 30, 0, 0).getMillis()).setPatientId(6226).setPersonId(242));

        } catch (TException e) {
            logger.error("getWorkTimeAndStatus Failed.", e);
        }

        logger.info(result.toString());
        logger.warn("End");

    }

    @Test(enabled = true)
    public void getPatientQueue() {
        logger.warn("getPatientQueue");

        List<Queue> result = null;
        try {
            result = client.getPatientQueue(6226);
        } catch (TException e) {
            logger.error("getPatientQueue Failed.", e);
        }

        logger.info(result.toString());
        logger.warn("End");

    }

    @Test(enabled = true)
    public void getPatientContacts() {
        logger.warn("getPatientContacts");

        List<Contact> result = null;
        try {
            result = client.getPatientContacts(6226);
        } catch (TException e) {
            logger.error("getPatientContacts Failed.", e);
        }

        logger.info(result.toString());
        logger.warn("End");

    }

    public long getMillisFromDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        logger.debug(calendar.toString());
        return calendar.getTime().getTime();
    }
}
