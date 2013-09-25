package ru.korus.tmis.core.prescription;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.korus.tmis.prescription.thservice.*;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Author: Upatov Egor <br>
 * Date: 28.08.13, 18:00 <br>
 * Company: Korus Consulting IT <br>
 * Description: Интеграционный тест для работы с Листами Назначений<br>
 */
@Test(enabled = true, groups = {"prescription"})
public class PrescriptionServiceTest {
    final static Logger logger = LoggerFactory.getLogger(PrescriptionServiceTest.class);
    private PrescriptionExchange.Client client;
    private String[] hosts = {"10.2.1.58", "localhost", "192.168.1.100"};
    private static int port = 8383;
    private static int timeout = 35000;
    private TTransport transport;

    @BeforeClass
    public void initConnection(){
        transport = new TSocket(hosts[2], PrescriptionServiceTest.port, PrescriptionServiceTest.timeout);
        logger.debug("Transport success");
        TProtocol protocol = new TBinaryProtocol(transport);
        logger.debug("Protocol success");
        client = new PrescriptionExchange.Client(protocol);
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
        logger.warn("End of all prescription component test suite. Closing transport... ");
    }

    @Test(enabled =true, groups = {"prescription"})
    public void getPrescriptionList(){
        logger.warn("Start of GetPrescriptionList test:");
        int eventId = 255;
        PrescriptionList result;
        try{
            result = client.getPrescriptionList(eventId);
            assertTrue(result != null, "GetPrescriptionList returns NULL");
            logger.info("Result is: {}", result);
        } catch (TException e) {
            logger.error("GetPrescriptionList test failed", e);
            fail();
        }
    }

    @Test(enabled =true, groups = {"prescription"})
    public void savePrescriptionList(){
        logger.warn("Start of SavePrescriptionList test:");
        PrescriptionList listToSave= new PrescriptionList();
        listToSave.setPrescriptionList(new ArrayList<Prescription>());
        try{
            client.save(listToSave);
//            assertTrue(result != null, "GetPrescriptionList returns NULL");
//            logger.info("Result is: {}", result);
        } catch (SavePrescrListException e) {
            logger.error("Exception while saving prescriptionList=[{}]. Error[{}]", listToSave, e);
        } catch (TException e) {
            logger.error("SavePrescriptionList test failed", e);
            fail();
        }
    }
}
