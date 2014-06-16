/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.ws;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.auth.JsonPerson;
import ru.korus.tmis.core.auth.UsersMgrLocal;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.AppealBeanLocal;
import ru.korus.tmis.testutil.DbUtil;
import ru.korus.tmis.testutil.WebMisBase;
import scala.actors.threadpool.Arrays;

import javax.ejb.EJB;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author SZagrebelny
 */
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
//@Transactional(value = TransactionMode.ROLLBACK)
public class AppealRegistryRESTImplTest extends Arquillian {

    final static String WAR_NAME = "AppealRegistryRESTImplTest";
    
    final static String BASE_URL_SOAP = String.format("http://localhost:7713/%s/", WAR_NAME);

    final int TEST_PATIENT_ID = 2; // id пациента, для которого создается госпитализация

    @EJB
    private AppealBeanLocal appealBean = null;

    @EJB
    private DbActionBeanLocal dbActionBean = null;

    @EJB
    private AuthStorageBeanLocal authStorageBeanLocal = null;

    @EJB
    private UsersMgrLocal usersMgr = null;

    @EJB
    private DbEventBeanLocal eventBeanLocal = null;

    private DbUtil dbUtil;


    @Deployment
    public static Archive createTestArchive() {
        return WebMisBase.createArchive(WAR_NAME);
    }

    @Test
    public void hello() {
        System.out.println("**************************** hello medipad test test");
        Assert.assertNotNull(appealBean);
        Assert.assertNotNull(authStorageBeanLocal);
        Assert.assertNotNull(usersMgr);
        Assert.assertNotNull(eventBeanLocal);
    }

    /**
     * Проверка создания обращения на госпитализацию
     * REST API: <server>/tmis-ws-medipad/rest/tms-registry/patients/{patientId}/appeals/
     */
    @Test
    public void testInsertAppealForPatient() {
        System.out.println("**************************** testInsertAppealForPatient() started...");
        try {
            AppealData appealData = initAppealData(); // инициализация параметров госпитализации
            int countEvent = eventBeanLocal.getEventsForPatient(TEST_PATIENT_ID).size();  // количетово обращений пациента ДО
            AuthData authData = WebMisBase.auth(authStorageBeanLocal);
            appealBean.insertAppealForPatient(appealData, TEST_PATIENT_ID, authData); // создание обращения на госпитализацию.
            int countEventNew = eventBeanLocal.getEventsForPatient(TEST_PATIENT_ID).size(); // количетово обращений пациента ПОСЛЕ
            Assert.assertEquals(countEventNew, countEvent + 1); // количетово обращений пациента ПОСЛЕ должно быть на один больше
            //TODO  add more assertion!
        } catch (CoreException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private AppealData initAppealData() {
        AppealData appealData = new AppealData();
        AppealEntry data = appealData.getData();
        data.setUrgent(false);
        DatePeriodContainer dateTimeInfo = new DatePeriodContainer();
        dateTimeInfo.setStart(new Date());
        data.setRangeAppealDateTime(dateTimeInfo);
        data.setAppealWithDeseaseThisYear("повторно");
        AppealTypeContainer appealTypeContainer = new AppealTypeContainer();
        IdNameContainer idNameContainerEventType = new IdNameContainer();
        idNameContainerEventType.setId(2);
        appealTypeContainer.setEventType(idNameContainerEventType);
        data.setAppealType(appealTypeContainer);
        IdNameContainer idNameContainerHospType = new IdNameContainer();
        idNameContainerHospType.setId(220);
        data.setHospitalizationType(idNameContainerHospType);
        final IdNameContainer hospitalizationPointType = new IdNameContainer();
        hospitalizationPointType.setId(134);
        data.setHospitalizationPointType(hospitalizationPointType);
        final ContractContainer contractContainer = new ContractContainer();
        contractContainer.setNumber("2009/5");
        contractContainer.setId(61);
        contractContainer.setBegDate(new Date(1262293200000L));
        final IdNameContainer finance = new IdNameContainer();
        finance.setId(1);
        contractContainer.setFinance(finance);
        data.setContract(contractContainer);
        AppealAssignmentContainer assignment = new AppealAssignmentContainer();
        data.setAssignment(assignment);
        final PhysicalParametersContainer physicalParametersContainer = new PhysicalParametersContainer();
        final RangeLeftRightContainer rangeLeftRightContainer = new RangeLeftRightContainer();
        physicalParametersContainer.setBloodPressure(rangeLeftRightContainer);
        final HandPreassureContainer handPreassureContainerRight = new HandPreassureContainer();
        rangeLeftRightContainer.setRight(handPreassureContainerRight);
        HandPreassureContainer handPreassureContainerLeft = new HandPreassureContainer();
        rangeLeftRightContainer.setLeft(handPreassureContainerLeft);
        data.setPhysicalParameters(physicalParametersContainer);
        return appealData;
    }

    @Test
    public void testAuth() {
        System.out.println("**************************** testAuth() started...");
        try {
            final URL url = new URL(WebMisBase.getBaseUrlRest(WAR_NAME) + "/tms-auth/roles/");
            System.out.println("Send POST to..." + url.toString());
            //TODO move to resource file!
            final String input = "{\"login\":\"utest\",\"password\":\"098f6bcd4621d373cade4e832627b4f6\",\"roleId\":0}";
            HttpURLConnection conn = WebMisBase.openConnectionPost(url, null);
            WebMisBase.toPostStream(input, conn);
            int code = WebMisBase.getResponseCode(conn);
            String res = WebMisBase.getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            //TODO move to resource file!
            //final String goodRes = "callback({\"version\":\"2.5.24-SNAPSHOT\",\"roles\":[{\"id\":29,\"name\":\"Медсестра приемного отделения\",\"withDep\":0,\"right\":[{\"permitted\":false,\"code\":\"clientTreatmentDelete\",\"isPermitted\":false},{\"permitted\":false,\"code\":\"clientDiagnosticCreate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientAssessmentRead\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticUpdate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientTreatmentRead\",\"isPermitted\":true},{\"permitted\":true,\"code\":\"clientTreatmentCreate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticRead\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientTreatmentUpdate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientAssessmentDelete\",\"isPermitted\":false},{\"permitted\":false,\"code\":\"clientAssessmentCreate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientAssessmentUpdate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticDelete\",\"isPermitted\":false}],\"code\":\"admNurse\"}],\"doctor\":{\"id\":34,\"name\":{\"last\":\"test\",\"first\":\"test\",\"middle\":\"test\",\"raw\":\"test test test\"},\"department\":null,\"specs\":{\"id\":1,\"name\":\"специальность\"}}})";
            Assert.assertTrue(res.contains("\"raw\":\"test test test\""));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }



    private static int labTestLabResearchId;
    private static int labTestBarcode;
    private static AuthData labTestAuthData;

    @Test
    public void testCreateLabResearch() {
        try {
            //createTestUser();
            labTestAuthData = WebMisBase.auth(authStorageBeanLocal);
            System.out.println(labTestAuthData);
            URL url = new URL(WebMisBase.getBaseUrlRest(WAR_NAME) + "/tms-registry/appeals/189/diagnostics/laboratory");
            HttpURLConnection createLabResearchConnection = WebMisBase.openConnection(url, labTestAuthData, "POST");
            OutputStream createLabResearchOutputStream = createLabResearchConnection.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sdf.format(new Date());
            String data = new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createLabRequestBloodMakro.json")));
            data = data.replace("2014-05-29 18:00:00", nowDate);
            System.out.println("I am going to send create lab research request:");
            System.out.println(data);
            createLabResearchOutputStream.write(data.getBytes());
            createLabResearchOutputStream.flush();
            int code = WebMisBase.getResponseCode(createLabResearchConnection);
            Assert.assertTrue(code == 200);
            String res = WebMisBase.getResponseData(createLabResearchConnection, code);
            res = res.substring(9, res.length() - 1);
            System.out.println(res);
            ObjectMapper mapper = new ObjectMapper();
            JSONCommonData commonData = mapper.readValue(res, JSONCommonData.class);
            System.out.println("Action id is " + commonData.getData().get(0).getId());
            Action a = dbActionBean.getActionById(commonData.getData().get(0).getId());

            labTestLabResearchId = a.getId();
            labTestBarcode = a.getTakenTissue().getBarcode();
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test(dependsOnMethods = "testCreateLabResearch")
    public void testGetLisAcrossResults() {
        try {
            URL url = new URL(BASE_URL_SOAP + "/service-across-results");
            String postData = new String(Files.readAllBytes(Paths.get("./src/test/resources/xml/makroBloodResult.xml")));
            postData = postData.replace("983410", Integer.toString(labTestLabResearchId));
            postData = postData.replace("609520", Integer.toString(labTestBarcode));
            System.out.println("I am going to send lab result request:");
            System.out.println(postData);
            HttpURLConnection connection = WebMisBase.openConnection(url, labTestAuthData, "POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            OutputStream outStream = connection.getOutputStream();
            outStream.write(postData.getBytes());
            outStream.flush();
            int code = WebMisBase.getResponseCode(connection);
            Assert.assertTrue(code == 200);
            WebMisBase.getResponseData(connection, code);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test(dependsOnMethods = "testGetLisAcrossResults")
    public void testGetLabResearchResult() {
        try {
            URL url = new URL(WebMisBase.getBaseUrlRest(WAR_NAME) + "/tms-registry/appeals/189/diagnostics/laboratory/" + labTestLabResearchId);
            HttpURLConnection connection = WebMisBase.openConnection(url, labTestAuthData, "GET");
            connection.setRequestProperty("Accept", "application/x-javascript");
            int code = WebMisBase.getResponseCode(connection);
            Assert.assertTrue(code == 200);
            String res = WebMisBase.getResponseData(connection, code);
            res = res.substring(9, res.length() - 1);
            ObjectMapper mapper = new ObjectMapper();
            JSONCommonData commonData = mapper.readValue(res, JSONCommonData.class);
            List<CommonAttribute> atrs = commonData.getData().get(0).getGroup().get(1).attribute();
            Assert.assertEquals("50", getCommonAttributeValueByName(atrs, "Альбумин"));
            Assert.assertEquals("60", getCommonAttributeValueByName(atrs, "Общий белок"));
            Assert.assertEquals("10", getCommonAttributeValueByName(atrs, "Билирубин общий"));
            Assert.assertEquals("1", getCommonAttributeValueByName(atrs, "Билирубин прямой"));
            Assert.assertEquals("2", getCommonAttributeValueByName(atrs, "Мочевина"));
            Assert.assertEquals("4", getCommonAttributeValueByName(atrs, "Глюкоза"));
            Assert.assertEquals("4", getCommonAttributeValueByName(atrs, "Калий"));
            Assert.assertEquals("153", getCommonAttributeValueByName(atrs, "Натрий"));
            Assert.assertEquals("20", getCommonAttributeValueByName(atrs, "АЛТ"));
            Assert.assertEquals("30", getCommonAttributeValueByName(atrs, "АСТ"));
            Assert.assertEquals("80", getCommonAttributeValueByName(atrs, "ЛДГ"));
            Assert.assertEquals("50", getCommonAttributeValueByName(atrs, "ГГТ"));
            Assert.assertEquals("100", getCommonAttributeValueByName(atrs, "ЩФ"));
            Assert.assertEquals("50", getCommonAttributeValueByName(atrs, "Альфа-амилаза"));
            Assert.assertEquals("20", getCommonAttributeValueByName(atrs, "П-амилаза"));
            Assert.assertEquals("4", getCommonAttributeValueByName(atrs, "Холестерин"));
            Assert.assertEquals("2", getCommonAttributeValueByName(atrs, "Триглицериды"));
            Assert.assertEquals("20", getCommonAttributeValueByName(atrs, "Липаза"));
            Assert.assertEquals("2", getCommonAttributeValueByName(atrs, "ЛПНП"));
            Assert.assertEquals("2", getCommonAttributeValueByName(atrs, "ЛПВП"));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


    private static int labTestBakLabResearchId;
    private static int labTestBakBarcode;
    private static AuthData bakLabTestAuthData;

    @Test
    public void testCreateBakLabResearch() {
        try {
            //createTestUser();
            bakLabTestAuthData = WebMisBase.auth(authStorageBeanLocal);
            System.out.println(bakLabTestAuthData);
            URL url = new URL(WebMisBase.getBaseUrlRest(WAR_NAME) + "/tms-registry/appeals/189/diagnostics/laboratory");
            HttpURLConnection createLabResearchConnection = WebMisBase.openConnection(url, bakLabTestAuthData, "POST");
            OutputStream createLabResearchOutputStream = createLabResearchConnection.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = sdf.format(new Date());
            String data = new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createLabRequestBak.json")));
            data = data.replace("2014-06-11 16:00:00", nowDate);
            data = data.replace("205", "41"); // Current user
            System.out.println("I am going to send create lab research request:");
            System.out.println(data);
            createLabResearchOutputStream.write(data.getBytes());
            createLabResearchOutputStream.flush();
            int code = WebMisBase.getResponseCode(createLabResearchConnection);
            Assert.assertTrue(code == 200);
            String res = WebMisBase.getResponseData(createLabResearchConnection, code);
            res = res.substring(9, res.length() - 1);
            ObjectMapper mapper = new ObjectMapper();
            JSONCommonData commonData = mapper.readValue(res, JSONCommonData.class);
            System.out.println("Action id is " + commonData.getData().get(0).getId());
            Thread.sleep(1000);
            Action a = dbActionBean.getActionById(commonData.getData().get(0).getId());

            labTestBakLabResearchId = a.getId();
            labTestBakBarcode = a.getTakenTissue().getBarcode();
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test(dependsOnMethods = "testCreateBakLabResearch")
    public void testGetLisBakReceived() {
        try {
            URL url = new URL(BASE_URL_SOAP + "/service-bak-results");
            String postData = new String(Files.readAllBytes(Paths.get("./src/test/resources/xml/bak-response-success-receive-data.xml")));
            postData = postData.replace("2014-06-05 11:50:33", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            postData = postData.replace("1000529970", Integer.toString(labTestBakBarcode + 1000 * 1000 * 1000));
            System.out.println("I am going to send lab bak success received:");
            System.out.println(postData);
            HttpURLConnection connection = WebMisBase.openConnection(url, labTestAuthData, "POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            OutputStream outStream = connection.getOutputStream();
            outStream.write(postData.getBytes());
            outStream.flush();
            int code = WebMisBase.getResponseCode(connection);
            Assert.assertTrue(code == 200);
            WebMisBase.getResponseData(connection, code);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test(dependsOnMethods = "testGetLisBakReceived")
    public void testGetLisBakResults() {
        try {
            URL url = new URL(BASE_URL_SOAP + "/service-bak-results");
            String postData = new String(Files.readAllBytes(Paths.get("./src/test/resources/xml/bak-response-full-data.xml")));
            postData = postData.replace("1113481", String.valueOf(labTestBakLabResearchId));
            postData = postData.replace("201406051155", String.valueOf(new Date().getTime()));
            postData = postData.replace("201406051052", String.valueOf(new Date().getTime()));
            postData = postData.replace("239", "41");
            System.out.println("I am going to send lab bak result request:");
            System.out.println(postData);
            HttpURLConnection connection = WebMisBase.openConnection(url, labTestAuthData, "POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            OutputStream outStream = connection.getOutputStream();
            outStream.write(postData.getBytes());
            outStream.flush();
            int code = WebMisBase.getResponseCode(connection);
            Assert.assertTrue(code == 200);
            WebMisBase.getResponseData(connection, code);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


    private String getCommonAttributeValueByName(List<CommonAttribute> attributes, String name) {
        for (CommonAttribute ca : attributes) {
            if (ca.getName().equals(name)) {
                for (PropertyPair pp : ca.getProperties()) {
                    if (pp.getName().equals("value")) {
                        return pp.getValue();
                    }
                }
            }
        }
        return "";
    }


}