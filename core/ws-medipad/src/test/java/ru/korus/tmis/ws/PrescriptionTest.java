package ru.korus.tmis.ws;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.testutil.WebMisBase;

import javax.ejb.EJB;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.06.14, 17:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@PersistenceTest
public class PrescriptionTest extends Arquillian {

    @EJB
    private AuthStorageBeanLocal authStorageBeanLocal = null;

    @Deployment
    public static Archive createTestArchive() {
        return WebMisBase.createArchive("PrescriptionTest");
    }

   @Test
    public void testCreatePrescription() {
        System.out.println("**************************** testCreatePrescription() started...");
        try {
            AuthData authData = WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/data/appeals/325/documents/?callback=jQuery18205675772596150637_1394525601248
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/");
            final String tstCallback = "tstCallback";
            url = WebMisBase.addGetParam(url, "callback", tstCallback);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send POST to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionPost(url, authData);
            WebMisBase.toPostStream(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createPrescriptionReq.json"))), conn);
            //toPostStream( new String(Files.readAllBytes(Paths.get("./src/test/resources/json/tmp.json"))), conn);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, tstCallback);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createPrescriptionResp.json"))));
            //TODO перед тестом почистить БД!
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"valueDomain\":\"rbMethodOfAdministration; IV, PO, IM, SC, AP, IN, IT, IO, B, ID, IH, IA, IP, IS, NG, GU, TP, PR, OTHER\""));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test(dependsOnMethods = "testCreatePrescription")
    public void testUpdatePrescription() {
        System.out.println("**************************** testUpdatePrescription() started...");
        final Integer actionId = 259;
        try {
            AuthData authData = WebMisBase.auth(authStorageBeanLocal);
            //http://10.128.51.85/api/v1/prescriptions/983378?callback=jQuery182040639712987467647_1400594935328
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/" + actionId);
            final String tstCallback = "tstCallback";
            url = WebMisBase.addGetParam(url, "callback", tstCallback);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send PUT to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionPut(url, authData);
            WebMisBase.toPostStream(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/updatePrescriptionReq.json"))), conn);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, tstCallback);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/updatePrescriptionReq.json"))));
            //Assert.assertEquals(resJson, expected);
            //TODO перед тестом почистить БД!
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"valueDomain\":\"rbMethodOfAdministration; IV, PO, IM, SC, AP, IN, IT, IO, B, ID, IH, IA, IP, IS, NG, GU, TP, PR, OTHER\""));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetPrescriptionByEvent() {
        System.out.println("**************************** testGetPrscriptionByEvent() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/api/v1/prescriptions/?callback=jQuery18209323157030157745_1400232225690&eventId=189&_=1400232242804
            final Integer eventId = WebMisBase.TEST_EVENT_ID;
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/");
            url = WebMisBase.addGetParam(url, "eventId", String.valueOf(eventId));
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getPrescriptionByEventResp.json"))));
            //TODO перед тестом почистить БД!
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"valueDomain\":\"rbMethodOfAdministration; IV, PO, IM, SC, AP, IN, IT, IO, B, ID, IH, IA, IP, IS, NG, GU, TP, PR, OTHER\""));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetPrescriptionByTime() {
        System.out.println("**************************** testGetPrescriptionByTime() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/api/v1/prescriptions/?callback=jQuery18205942272304091603_1400733998545&
            // groupBy=interval&
            // dateRangeMin=1400745600&
            // dateRangeMax=1400832000&
            // administrationId=2&
            // drugName=a&
            // pacientName=P&
            // setPersonName=V&
            // departmentId=26&_=1400734043649
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/");
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            url = WebMisBase.addGetParam(url, "dateRangeMin", "1379524000");
            url = WebMisBase.addGetParam(url, "dateRangeMax", "1379530400");
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getPrescriptionByEventResp.json"))));
            //TODO перед тестом почистить БД!
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"valueDomain\":\"rbMethodOfAdministration; IV, PO, IM, SC, AP, IN, IT, IO, B, ID, IH, IA, IP, IS, NG, GU, TP, PR, OTHER\""));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testPrescriptionTypeList() {
        System.out.println("**************************** testPrscriptionTypeList() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/api/v1/prescriptions/?callback=jQuery18209323157030157745_1400232225690&eventId=189&_=1400232242804
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/types/");
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getPrescriptionTypeListResp.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetPrescriptionTemplate() {
        System.out.println("**************************** testGetPrescriptionTemplate() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/api/v1/prescriptions/?callback=jQuery18209323157030157745_1400232225690&eventId=189&_=1400232242804
            Integer actionTypeId = 123;
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/prescriptions/template/" + actionTypeId);
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getPrescriptionTemplateResp.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

}
