/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.ws;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.auth.*;
import ru.korus.tmis.core.data.*;
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
import java.net.URL;
import java.util.Date;


/**
 * @author SZagrebelny
 */
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
@Transactional(value = TransactionMode.ROLLBACK)
public class AppealRegistryRESTImplTest extends Arquillian {

    @EJB
    private AppealBeanLocal appealBean = null;

    @EJB
    private AuthStorageBeanLocal authStorageBeanLocal = null;

    @EJB
    private UsersMgrLocal usersMgr = null;

    @EJB
    private DbEventBeanLocal eventBeanLocal = null;

    private boolean isNeededCreateUser = true;

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
            AppealData appealData = new AppealData();
            final int patientId = 2; // id пациента, для которого создается госпитализация
            int countEvent = eventBeanLocal.getEventsForPatient(patientId).size();  // количетово обращений пациента ДО
            createTestUser(); // создание тестового пользователя с ролью "медсестра приемного отделения”. Login: 'test'
            initAppealData(appealData); // инициализация параметров госпитализации
            // авторизация пользователм  'test' с ролью "медсестра приемного отделения”
            AuthData authData = authStorageBeanLocal.createToken("test", "098f6bcd4621d373cade4e832627b4f6" /*MD5 for 'test'*/, 29);
            appealBean.insertAppealForPatient(appealData, patientId, authData); // создание обращения на госпитализацию.
            int countEventNew = eventBeanLocal.getEventsForPatient(patientId).size(); // количетово обращений пациента ПОСЛЕ
            Assert.assertEquals(countEventNew, countEvent + 1); // количетово обращений пациента ПОСЛЕ должно быть на один больше
            //TODO  add more assertion!
        } catch (CoreException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testAuth() {
        try {
            //final String BASE_URL = "http://localhost:7713/tmis-ws-medipad/rest/tms-auth";
            final String BASE_URL = "http://localhost:7713/test/rest/tms-auth";

            URL url;
            url = new URL(BASE_URL + "/roles/");
            System.out.println("Send POST to..." + url.toString());
            HttpURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            //TODO move to resource file!
            final String input = "{\"login\":\"test\",\"password\":\"098f6bcd4621d373cade4e832627b4f6\",\"roleId\":0}";
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
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


    private HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
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


    private void initAppealData(AppealData appealData) {
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
    }

    private void createTestUser() {
        if (isNeededCreateUser) {
            JsonPerson personInfo = new JsonPerson();
            personInfo.setFname("test");
            personInfo.setLname("test");
            personInfo.setPname("test");
            personInfo.setLogin("test");
            personInfo.setPassword("test");
            personInfo.setRoles(Arrays.asList(new String[]{"admNurse"}));
            usersMgr.create(personInfo);
            isNeededCreateUser = false;
        }
    }

}