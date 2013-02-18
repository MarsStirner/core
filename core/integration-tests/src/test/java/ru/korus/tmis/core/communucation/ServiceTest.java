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

/**
 * User: eupatov
 * Date: 17.12.12 at 14:53
 */
public class ServiceTest {
    final static Logger logger = LoggerFactory.getLogger(ServiceTest.class);

    private Communications.Client client;
    private String[] hosts = {"10.2.1.58", "localhost"};
    private static int port = 7911;
    private static int timeout = 15000;
    private TTransport transport;

    @BeforeClass
    public void initConnection() {
        transport = new TSocket(hosts[1], ServiceTest.port, ServiceTest.timeout);
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
    private void getOrganisationInfo() {
        logger.warn("GetOrganisationInfo");
        Organization result = null;
        try {
            result = client.getOrganisationInfo("500");
        } catch (TException e) {
            logger.error("getOrganisationInfo Failed.");
        }
        logger.debug(result.toString());
        logger.warn("End");
    }

    @Test(enabled = true)
    private void getSpecialities() {
        logger.warn("GetSpecialitiesTest");
        List<Speciality> result = null;
        try {
            result = client.getSpecialities("580033");
        } catch (TException e) {
            logger.error("getSpecialities Failed.");
            e.printStackTrace(System.out);
            return;
        } catch (Exception e) {
            logger.error("EXCEPTION E!!!", e);
        }
        //assert(result.size()>0);
        logger.info("Received list size=" + result.size());
        for (Speciality speciality : result) {
            logger.debug(speciality.toString());
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    private void dequeuePatient() {
        logger.warn("dequeuePatientTest");
        DequeuePatientStatus status = null;
        try {
            status = client.dequeuePatient(6227, 244074);
        } catch (TException e) {
            logger.error("dequeuePatient test FAILED", e);
        }
        logger.debug(status.toString());
        logger.warn("End");
    }

    @Test(enabled = true)
    private void addPatient() {
        logger.warn("addPatientTest");
        PatientStatus status = null;
        try {
            status = client.addPatient(new AddPatientParameters().setLastName("ТЕСТОВ3").setFirstName("ТЕСТ1").setBirthDate(new Date().getTime()).setSex(1));
        } catch (TException e) {
            logger.error("addPatient test FAILED", e);
        }
        logger.debug(status.toString());
        logger.warn("End");
    }

    @Test(enabled = true)
    private void findPatient() {
        logger.warn("findPatientTest");
        PatientStatus status = null;
        try {
            status = client.findPatient(new FindPatientParameters().setLastName("Ив...").setFirstName("Арина").setSex(2));
        } catch (TException e) {
            logger.error("findPatient test FAILED", e);
        }
        logger.debug(status.toString());
        logger.warn("End");
    }

    @Test(enabled = true)
    private void findPatients() {
        logger.warn("findPatientSTest");
        List<Patient> status = null;
        try {
            status = client.findPatients(new FindPatientParameters().setLastName("Ив...").setSex(2).setBirthDate(new Date().getTime()));
        } catch (TException e) {
            logger.error("findPatientS test FAILED", e);
        }
        for (Patient patient : status) {
            logger.debug(patient.toString());
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    private void findOSbyADDRESS() {
        logger.warn("findOS BY ADDRESS test");
        List<Integer> result = null;
        try {
            result = client.findOrgStructureByAddress(new FindOrgStructureByAddressParameters().setFlat(10).setPointKLADR("7800000500000").setStreetKLADR("78000005000003600").setCorpus("").setNumber("1"));
        } catch (TException e) {
            logger.error("find OS by aDREss", e);
        }
        assert (result != null);
        assert (result.size() > 0);
        System.out.println("Received list size=" + result.size());
        for (Integer id : result) {
            logger.debug("ID:" + id);
        }
        logger.warn("End");
    }


    @Test(enabled = true)
    private void getPatientInfo() {
        logger.warn("getPatientINFO test");
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(414);
        ids.add(212);
        ids.add(1000);
        ids.add(152);
        ids.add(1);
        HashMap<Integer, PatientInfo> result = null;
        try {
            result = (HashMap<Integer, PatientInfo>) client.getPatientInfo(ids);
        } catch (TException e) {
            logger.error("getPatientInfo FAILED", e);
        }
        logger.info("Received list size=" + result.size());
        for (Integer key : result.keySet()) {
            logger.debug("KEY:" + key + " VALUE:" + result.get(key));
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    private void getOrgStructures() {
        logger.warn("GetOrgStructuresTest");
        List<OrgStructure> result = null;
        try {
            result = client.getOrgStructures(3, true, "");
        } catch (TException e) {
            logger.error("getOrgStructure Failed.", e);
        }

        logger.info("Received list size=" + result.size());
        for (OrgStructure structure : result) {
            logger.debug(structure.toString());
        }
        logger.warn("End");
    }

    @Test(enabled = true)
    private void getPersonnel() {
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
    private void getWorkTimeAndStatus() {
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
    private void enqueuePatient() {
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
    private void getPatientQueue() {
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
    private void getPatientContacts() {
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

    private long getMillisFromDate(int year, int month, int day) {
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
