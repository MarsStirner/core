package ru.korus.tmis.ws;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.testutil.WebMisBase;
import scala.actors.threadpool.Arrays;

import javax.ejb.EJB;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.06.14, 18:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@PersistenceTest
public class WebMisCommonTest extends Arquillian  {

    static private int createdActionId;

    @EJB
    private AuthStorageBeanLocal authStorageBeanLocal = null;

    @Deployment
    public static Archive createTestArchive() {
        return WebMisBase.createArchive();
    }

    @Test
    public void testCreateAction() {
        System.out.println("**************************** testCreateAction() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/data/appeals/325/documents/?callback=jQuery18205675772596150637_1394525601248
            final Integer eventId = 841695;
            URL url = new URL(WebMisBase.BASE_URL_REST + String.format("/tms-registry/appeals/%s/documents/", eventId));
            final String tstCallback = "tstCallback";
            url = WebMisBase.addGetParam(url, "callback", tstCallback);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send POST to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionPost(url, authData);
            WebMisBase.toPostStream(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createActionReq.json"))), conn);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, tstCallback);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createActionResp.json"))));
            //TODO remove id  from json or clear DB
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"typeId\":3911"));
            final JsonElement jsonData = resJson.getAsJsonObject().get("data");
            Assert.assertNotNull(jsonData);
            final JsonArray jsonActionInfoArray = jsonData.getAsJsonArray();
            Assert.assertNotNull(jsonActionInfoArray);
            Assert.assertTrue(jsonActionInfoArray.size() > 0);
            final JsonPrimitive jsonActionId = jsonActionInfoArray.get(0).getAsJsonObject().getAsJsonPrimitive("id");
            Assert.assertNotNull(jsonActionId);
            createdActionId = jsonActionId.getAsInt();

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test(dependsOnMethods = "testCreateAction")
    public void testDeleteAction() {
        final int actionId = createdActionId;
        System.out.println("**************************** deleteAction(actionId) started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/data/appeals/325/documents/?callback=jQuery18205675772596150637_1394525601248
            final Integer transfusionTherapyActionId = 3911;
            final Integer eventId = 841695;
            URL url = new URL(WebMisBase.BASE_URL_REST + String.format("/tms-registry/appeals/%s/documents/%s", eventId, actionId));
            final String tstCallback = "tstCallback";
            url = WebMisBase.addGetParam(url, "callback", tstCallback);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send DELETE to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionDel(url, authData);
            //toPostStream( new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createActionReq.json"))), conn);
            int code = WebMisBase.getResponseCode(conn);
            //  String res = getResponseData(conn, code);
            Assert.assertTrue(code == 204);

            // res = removePadding(res, tstCallback);
            //Assert.assertEquals(resJson, expected);
            // Assert.assertEquals(res, "true");
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testCreateEvent() {
        System.out.println("**************************** testCreateEvent() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/data/patients/2/appeals/?callback=jQuery1820959072473924607_1402042407315
            final Integer eventId = 841695;
            URL url = new URL(WebMisBase.BASE_URL_REST + String.format("/tms-registry/patients/%s/appeals/",  WebMisBase.TEST_CLIENT_ID));
            final String tstCallback = "tstCallback";
            url = WebMisBase.addGetParam(url, "callback", tstCallback);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            System.out.println("Send POST to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionPost(url, authData);
            WebMisBase.toPostStream(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createAppealsReq.json"))), conn);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, tstCallback);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createAppealsResp.json"))));
            //TODO remove id  from json or clear DB
            //Assert.assertEquals(resJson, expected);
            Assert.assertTrue(res.contains("\"number\":\"NUMBER__\""));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetActionTypeInfo() {
        System.out.println("**************************** testGetActionTypeInfo() started...");
        try {
            AuthData authData = WebMisBase.auth(authStorageBeanLocal);
            //http://webmis/data/dir/actionTypes/3911?eventId=325&callback=jQuery18202118265349417925_1394181799283&_=1394182983004
            final String transfusionTherapyActionId = "3911";
            final Integer eventId = 841695;
            //final Integer eventId = appealBean.insertAppealForPatient(initAppealData(), TEST_PATIENT_ID, authData); // создание обращения на госпитализацию.
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/dir/actionTypes/" + transfusionTherapyActionId);
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
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getActionTypeInfoResp.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    public void testGetActionTypes(){
        System.out.println("**************************** testGetActionTypes() started...");
        try {
            ConfigManager.Common().DebugTestMode_$eq("on");
            //http://webmis/data/dir/actionTypes/?filter[view]=tree&
            // filter[mnem]=EXAM&filter[mnem]=EPI&filter[mnem]=JOUR&filter[mnem]=ORD&filter[mnem]=NOT&filter[mnem]=OTH&
            // callback=jQuery182004028293350711465_1399548976777&
            // sortingField=id&sortingMethod=asc&limit=10&page=1&recordsCount=0&_=1399556929592
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            URL url = new URL(WebMisBase.BASE_URL_REST + "/tms-registry/dir/actionTypes/");
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            final String mnems[] = {"EXAM","EPI","JOUR","ORD","NOT","OTH"};
            for(String mnem : mnems) {
                url = WebMisBase.addGetParam(url, "filter[mnem]", mnem);
            }
            url = WebMisBase.addGetParams(url, "filter[view]=tree&sortingField=id&sortingMethod=asc&limit=10&page=1&recordsCount=0&filter[orgStruct]=1");
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getListActionTypesResp.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetDocumentsList() {
        System.out.println("**************************** testGetDocumentsList() started...");
        try {
            AuthData authData =WebMisBase.auth(authStorageBeanLocal);
            String res = getDocumentsList(authData);

            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getDocumentsList.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testLockDocument() {
        System.out.println("**************************** testLockDocument() started...");
        try {
            //lock Action
            AuthData authData = WebMisBase.auth(authStorageBeanLocal);
            URL url = new URL(WebMisBase.BASE_URL_REST + String.format("/tms-registry/appeals/%s/documents/%s/lock", WebMisBase.TEST_EVENT_ID, 259));
            url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
            url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
            HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
            int code = WebMisBase.getResponseCode(conn);
            String lockRes = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            lockRes = WebMisBase.removePadding(lockRes, WebMisBase.TST_CALLBACK);
            Assert.assertTrue(lockRes.contains("\"id\""));
            //prolong lock Action
            HttpURLConnection connPut = WebMisBase.openConnectionPut(url, authData);
            code = WebMisBase.getResponseCode(connPut);
            lockRes = WebMisBase.getResponseData(connPut, code);
            Assert.assertTrue(code == 200);
            lockRes = WebMisBase.removePadding(lockRes, WebMisBase.TST_CALLBACK);
            Assert.assertTrue(lockRes.contains("\"id\""));

            String res = getDocumentsList(authData);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getDocumentsListLock.json"))));
            Assert.assertEquals(resJson, expected);

            //unlock Action
            HttpURLConnection connDel = WebMisBase.openConnectionDel(url, authData);
            code = WebMisBase.getResponseCode(connDel);
            Assert.assertTrue(code == 204);

            String resUnlock = getDocumentsList(authData);
            JsonElement resJsonUnlock = parser.parse(resUnlock);
            JsonElement expectedUnlock = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getDocumentsList.json"))));
            Assert.assertEquals(resJsonUnlock, expectedUnlock);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    private String getDocumentsList(AuthData authData) throws IOException {
        //http://webmis/data/appeals/189/documents?
        // filter[mnem]=EXAM&filter[mnem]=EPI&filter[mnem]=JOUR&filter[mnem]=ORD&filter[mnem]=NOT&filter[mnem]=OTH&filter[mnem]=EXAM_OLD&filter[mnem]=JOUR_OLD
        // &sortingField=assesmentDate
        // &sortingMethod=desc
        // &limit=10
        // &page=1
        // &recordsCount=0
        // &_=1401344705743
        URL url = new URL(WebMisBase.BASE_URL_REST + String.format("/tms-registry/appeals/%s/documents/", WebMisBase.TEST_EVENT_ID));
        url = WebMisBase.addGetParam(url, "callback", WebMisBase.TST_CALLBACK);
        url = WebMisBase.addGetParam(url, "_", authData.getAuthToken().getId());
        url = WebMisBase.addGetParam(url, "filter[mnem]", Arrays.asList(new String[]{"THER", "EXAM", "EPI", "JOUR", "ORD", "NOT", "OTH", "EXAM_OLD", "JOUR_OLD"}));
        url = WebMisBase.addGetParam(url, "sortingField", "assesmentDate");
        url = WebMisBase.addGetParam(url, "sortingMethod", "desc");
        url = WebMisBase.addGetParams(url, "&limit=10&page=1&recordsCount=0");
        System.out.println("Send GET to..." + url.toString());
        HttpURLConnection conn = WebMisBase.openConnectionGet(url, authData);
        int code = WebMisBase.getResponseCode(conn);
        String res = WebMisBase.getResponseData(conn, code);
        Assert.assertTrue(code == 200);
        res = WebMisBase.removePadding(res, WebMisBase.TST_CALLBACK);
        return res;
    }




}
