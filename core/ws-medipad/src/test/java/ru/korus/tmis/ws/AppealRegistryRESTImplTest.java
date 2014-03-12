/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.ws;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.auth.*;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.common.DbActionBean;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.AppealBeanLocal;
import ru.korus.tmis.util.TestUtilBusiness;
import ru.korus.tmis.util.TestUtilCommon;
import ru.korus.tmis.util.TestUtilWsMedipad;
import scala.actors.threadpool.Arrays;

import javax.ejb.EJB;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;


/**
 * @author SZagrebelny
 */
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
//@Transactional(value = TransactionMode.ROLLBACK)
public class AppealRegistryRESTImplTest extends Arquillian {

    final String BASE_URL = "http://localhost:7713/test/rest";

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

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");

        // common -------------------------------------------------------------------
        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());
        // --------------------------------------------------------------------------

        wa.addPackages(true, (new TestUtilWsMedipad()).getPackagesForTest());
        wa.addClass(AuthStorageBeanLocal.class);
        wa.addClass(AuthStorageBean.class);

        wa.addClass(CommonDataProcessorBeanLocal.class);
        wa.addClass(CommonDataProcessorBean.class);

     /*   wa.addPackage(ru.korus.tmis.ws.webmis.rest.servlet.RestServlet.class.getPackage());
        wa.addPackage(ru.korus.tmis.ws.webmis.rest.WebMisREST.class.getPackage());
        wa.addPackage(ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage.class.getPackage());*/

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        wa.addAsWebInfResource(new File("./src/main/webapp/WEB-INF/web.xml"), "web.xml");
        System.out.println("**************************** createTestArchive medipad");
        return wa;
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
        try {
            AppealData appealData =initAppealData(); // инициализация параметров госпитализации
            int countEvent = eventBeanLocal.getEventsForPatient(TEST_PATIENT_ID).size();  // количетово обращений пациента ДО
            createTestUser(); // создание тестового пользователя с ролью "медсестра приемного отделения”. Login: 'test'
            AuthData authData = auth();
            appealBean.insertAppealForPatient(appealData, TEST_PATIENT_ID, authData); // создание обращения на госпитализацию.
            int countEventNew = eventBeanLocal.getEventsForPatient(TEST_PATIENT_ID).size(); // количетово обращений пациента ПОСЛЕ
            Assert.assertEquals(countEventNew, countEvent + 1); // количетово обращений пациента ПОСЛЕ должно быть на один больше
            //TODO  add more assertion!
        } catch (CoreException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private AuthData auth() throws CoreException {
        // авторизация пользователм  'test' с ролью "медсестра приемного отделения”
        return authStorageBeanLocal.createToken("test", "098f6bcd4621d373cade4e832627b4f6" /*MD5 for 'test'*/, 29);
    }

    @Test
    public void testAuth() {
        try {
            createTestUser();
            final URL url = new URL(BASE_URL + "/tms-auth/roles/");
            System.out.println("Send POST to..." + url.toString());
            //TODO move to resource file!
            final String input = "{\"login\":\"test\",\"password\":\"098f6bcd4621d373cade4e832627b4f6\",\"roleId\":0}";
            HttpURLConnection conn = openConnectionPost(url);
            toPostStream(input, conn);
            int code = getResponseCode(conn);
            String res = getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            //TODO move to resource file!
            //final String goodRes = "callback({\"version\":\"2.5.24-SNAPSHOT\",\"roles\":[{\"id\":29,\"name\":\"Медсестра приемного отделения\",\"withDep\":0,\"right\":[{\"permitted\":false,\"code\":\"clientTreatmentDelete\",\"isPermitted\":false},{\"permitted\":false,\"code\":\"clientDiagnosticCreate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientAssessmentRead\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticUpdate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientTreatmentRead\",\"isPermitted\":true},{\"permitted\":true,\"code\":\"clientTreatmentCreate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticRead\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientTreatmentUpdate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientAssessmentDelete\",\"isPermitted\":false},{\"permitted\":false,\"code\":\"clientAssessmentCreate\",\"isPermitted\":false},{\"permitted\":true,\"code\":\"clientAssessmentUpdate\",\"isPermitted\":true},{\"permitted\":false,\"code\":\"clientDiagnosticDelete\",\"isPermitted\":false}],\"code\":\"admNurse\"}],\"doctor\":{\"id\":34,\"name\":{\"last\":\"test\",\"first\":\"test\",\"middle\":\"test\",\"raw\":\"test test test\"},\"department\":null,\"specs\":{\"id\":1,\"name\":\"специальность\"}}})";
            Assert.assertTrue(res.contains("\"raw\":\"test test test\""));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    private void toPostStream(String input, HttpURLConnection conn) throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
    }

    private HttpURLConnection openConnectionPost(URL url, AuthData authData) throws IOException {
        HttpURLConnection conn = openConnectionPost(url);
        conn.setRequestProperty("Cookie", "authToken=" + authData.getAuthToken().getId());
        return conn;
    }


    private HttpURLConnection openConnectionPost(URL url) throws IOException {
        HttpURLConnection conn = openConnection(url);
        conn.setRequestMethod("POST");
        return conn;
    }

    private HttpURLConnection openConnectionGet(URL url, AuthData authData) throws IOException {
        HttpURLConnection conn = openConnection(url);
        conn.setRequestProperty("Cookie", "authToken=" + authData.getAuthToken().getId());
        conn.setRequestMethod("GET");
        return conn;
    }

    @Test
    public void testGetActionTypeInfo() {
        try {
            AuthData authData = auth();
            //http://webmis/data/dir/actionTypes/3911?eventId=325&callback=jQuery18202118265349417925_1394181799283&_=1394182983004
            final String transfusionTherapyActionId = "3911";
            final Integer eventId = appealBean.insertAppealForPatient(initAppealData(), TEST_PATIENT_ID, authData); // создание обращения на госпитализацию.
            URL url = new URL(BASE_URL + "/tms-registry/dir/actionTypes/" + transfusionTherapyActionId);
            url = addGetParam(url, "eventId", String.valueOf(eventId));
            final String tstCallback = "tstCallback";
            url = addGetParam(url, "callback", tstCallback);
            url = addGetParam(url, "_" , authData.getAuthToken().getId());
            System.out.println("Send GET to..." + url.toString());
            HttpURLConnection conn = openConnectionGet(url, authData);
            int code = getResponseCode(conn);
            String res = getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            res = removePatting(res, tstCallback);
            /*JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/getActionTypeInfoResp.json"))));
            Assert.assertEquals(resJson, expected);*/

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testCreateAction() {
        try {
            AuthData authData = auth();
            //http://webmis/data/appeals/325/documents/?callback=jQuery18205675772596150637_1394525601248
            final Integer transfusionTherapyActionId = 3911;
            final Integer eventId = appealBean.insertAppealForPatient(initAppealData(), TEST_PATIENT_ID, authData); // создание обращения на госпитализацию.
            URL url = new URL(BASE_URL + String.format("/tms-registry/appeals/%s/documents/", eventId));
            final int countAction = dbActionBean.getLastActionByActionTypeIdAndEventId(eventId, new HashSet<Integer>(){{add(transfusionTherapyActionId);}});
            final String tstCallback = "tstCallback";
            url = addGetParam(url, "callback", tstCallback);
            url = addGetParam(url, "_" , authData.getAuthToken().getId());
            System.out.println("Send POST to..." + url.toString());
            HttpURLConnection conn = openConnectionPost(url, authData);
            toPostStream( new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createActionReq.json"))), conn);
            int code = getResponseCode(conn);
            String res = getResponseData(conn, code);
            Assert.assertTrue(code == 200);
            final int countActionNew = dbActionBean.getLastActionByActionTypeIdAndEventId(eventId, new HashSet<Integer>() {{
                add(transfusionTherapyActionId);
            }});
            Assert.assertEquals(countActionNew, countAction + 1);
            res = removePatting(res, tstCallback);
            JsonParser parser = new JsonParser();
            JsonElement resJson = parser.parse(res);
            JsonElement expected = parser.parse(new String(Files.readAllBytes(Paths.get("./src/test/resources/json/createActionResp.json"))));
            Assert.assertEquals(resJson, expected);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }


    private String removePatting(String res, String tstCallback) {
        final String prefix = tstCallback + "(";
        Assert.assertTrue(res.startsWith(prefix));
        final String suffix = ")";
        Assert.assertTrue(res.endsWith(suffix));
        return res.substring(prefix.length()).substring(0, res.length() - suffix.length() - prefix.length());
    }

    private URL addGetParam(final URL url, final String name, String value) throws MalformedURLException {
        final String beg = url.toString().indexOf('?') < 0 ? "?" : "&";
        return new URL(url + beg + name + "=" + value);
    }


    private HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    private int getResponseCode(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        System.out.println("Response code: " + code);
        String msg = conn.getResponseMessage();
        System.out.println("Response message: " + msg);
        return code;
    }

    /**
     * @param conn
     * @param code
     * @return
     * @throws IOException
     */
    private String getResponseData(HttpURLConnection conn, int code) throws IOException {
        final InputStream inputStream = code == 200 ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                inputStream));

        String output;
        System.out.println("Output from Server .... \n");
        String res = "";
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            res += output;
        }
        br.close();
        return res;
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

    private void createTestUser() {
        JsonPerson personInfo = new JsonPerson();
        personInfo.setFname("test");
        personInfo.setLname("test");
        personInfo.setPname("test");
        personInfo.setLogin("test");
        personInfo.setPassword("test");
        personInfo.setRoles(Arrays.asList(new String[]{"admNurse"}));
        final String res = usersMgr.create(personInfo);
        System.out.println("AppealRegistryRESTImplTest.createTestUser: " + res);
    }

}