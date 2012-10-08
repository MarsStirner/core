/*package ru.korus.test.appeal;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
//import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.korus.tmis.core.auth.AuthData;
//import ru.korus.tmis.core.auth.AuthStorageBean;
//import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.*;
//import ru.korus.tmis.core.exception.CoreException;
//import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.core.patient.AppealBeanLocal;
import ru.korus.tmis.core.auth.AuthToken;
//import ru.korus.tmis.core.database.DbManagerBean;
import ru.korus.tmis.core.database.DbManagerBeanLocal;
import ru.korus.tmis.core.database.DbStaffBean;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
//import ru.korus.tmis.ws.impl.AuthenticationWSImpl;
//import ru.korus.tmis.ws.webmis.rest.AuthenticationRESTImpl;
//import ru.korus.tmis.ws.webmis.rest.test.OnDeamandData;

/*import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;*/

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
/*
@RunWith(org.jboss.arquillian.junit.Arquillian.class)
public class AppealTestCase {

//Bean, Inject
@EJB
private DbPatientBeanLocal dbPatient;

@EJB
private DbManagerBeanLocal dbManager;

@EJB
private DbMkbBeanLocal dbMkb;

@EJB
private AppealBeanLocal appealBean;

@EJB
private DbFDRecordBeanLocal dbFDRecordBean;

//@EJB
//private AuthStorageBeanLocal authStorage;

@EJB
private DbStaffBeanLocal dbStaff;

//@Inject
//private AuthenticationWSImpl auth;
//коллекция объектов, созданных в процессе тестирования и подлежащих удалению по окончании теста
private Set<Object> toDrop = new HashSet<Object>();

private AuthData authData;

//Создание архива для деплоя тестового приложения
@Deployment(name="glassfish",testable = true)
public static Archive createTestArchive() {

//Тестируемые классы и зависимости
Class<?>[] classes = new Class<?>[] {
//DbPatientBeanLocal.class,
DbPatientBean.class,
//DbMkbBeanLocal.class,
DbMkbBean.class,
//AppealBeanLocal.class,
AppealBean.class,
//DbFDRecordBeanLocal.class,
DbFDRecordBean.class,
//DbRbCoreActionPropertyBeanLocal.class,
DbRbCoreActionPropertyBean.class,
//DbStaffBeanLocal.class,
DbStaffBean.class,
//DbCustomQueryLocal.class,
DbCustomQueryBean.class,
//DbEventBeanLocal.class,
DbEventBean.class,
//DbRbBloodTypeBeanLocal.class,
DbRbBloodTypeBean.class,
//DbActionTypeBeanLocal.class,
DbActionTypeBean.class,
//DbRbCounterBeanLocal.class,
DbRbCounterBean.class,
//DbActionPropertyTypeBeanLocal.class,
DbActionPropertyTypeBean.class,
//DbActionPropertyBeanLocal.class,
DbActionPropertyBean.class,
//DbActionBeanLocal.class,
DbActionBean.class,
//AppLockBeanLocal.class,
AppLockBean.class,
RbBloodType.class,
//AuthStorageBeanLocal.class, AuthStorageBean.class,
/*DbManagerBeanLocal.class, */DbManagerBean.

class,
        //AuthenticationWSImpl.class, AuthData.class,
        ru.korus.tmis.core.logging.LoggingInterceptor.class,
        ru.korus.tmis.core.database.InternalLoggerBeanLocal.class,
        ru.korus.tmis.core.database.InternalLoggerBean.class
//OnDeamandData.class

};

//EnterpriseArchive - ear, WebArchive - war, JavaArchive - jar (используется апи ShrinkWrap)
WebArchive wa=ShrinkWrap.create(WebArchive.class,"test.war");
wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
wa.addClasses(classes);
wa.addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));
//wa.setWebXML(new File("./src/test/resources/web.xml"));

System.out.println(wa.toString());
return wa;
}

@Before
public void setUp()throws Exception{
        Date now=new Date();
String tokenStr="2"+"24"+"Тестов Тест Тестович"+"Тестировщик"+String.format("%d",now.getTime());
this.authData=new AuthData(new AuthToken(tokenStr),
        new Staff(2),
        2,
        new Role(24),
        "Тест",
        "Тестов",
        "Тестович",
        "Тестировщик");
assertTrue("<<<<<<<<<<",authData!=null);
/*    }

    @After
    public void tearDown() throws Exception {
        //зачистка (но вообще-то и так всё откатывается)
        //dbManager.removeAll(this.toDrop);
    }

    /*@ArquillianResource
    URL deploymentUrl;

    final String AUTH_PATH = "/tmis-ws-medipad/rest/tms-auth/auth";///?login=ДБалашов&passwd=c4ca4238a0b923820dcc509a6f75849b&role=24";

    @Test
    @RunAsClient
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testAuthData() throws Exception{
        ClientConfig config = new DefaultClientConfig();
        //config.getClasses().add(JSONRootElementProvider.class);
        Client client = Client.create(config);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("login", "ДБалашов");
        queryParams.add("passwd", "c4ca4238a0b923820dcc509a6f75849b");
        queryParams.add("role", "24");

        WebResource wr = client.resource(new URL(deploymentUrl,AUTH_PATH).toExternalForm()).queryParams(queryParams);
        wr.accept(MediaType.APPLICATION_JSON);
        String json = wr.get(String.class);
        //System.out.println(json);
        assertTrue("<<<<<<<<<<", false);
        //this.authData = new AuthData(json);
    } */

/*@Test
@OperateOnDeployment("glassfish")
@OverProtocol("EJB")
public void testAuthData() throws Exception{


// Пытаемся получить сотрудника по логину
//Staff staff = dbStaff.getStaffById(2);

// Получаем роли сотрудника
/*val roles = getRoles(login, password)

// Проверяем, обладает ли сотрудник запрашиваемой ролью
val staffRole = roles.find(_.getId == roleId)

val userId: Int = staff.getId.intValue
val userFirstName = staff.getFirstName
val userLastName = staff.getLastName
val userPatrName = staff.getPatrName
val userSpeciality = staff.getSpeciality match {
    case null => ""
    case specs => specs.getName
}
*/
/*       Date now = new Date();
    String tokenStr = "2" + "24" + "Тестов Тест Тестович" + "Тестировщик" + String.format("%d",now.getTime());
    authData = new AuthData(new AuthToken(tokenStr),
                            new Staff(2),
                            2,
                            new Role(24),
                            "Тест",
                            "Тестов",
                            "Тестович",
                            "Тестировщик");
    assertTrue("<<<<<<<<<<", true);
}*/

//Теcтирование сервисов госпитализации
@Test
@OperateOnDeployment("glassfish")
@OverProtocol("EJB")
public void testAppeals()throws Exception{    //Appeal

        Date now=new Date();
assertTrue(">>>>>>>authData = null",authData!=null);
assertTrue(">>>>>>>authData.getUser() = null",authData.getUser()!=null);

Patient pat=dbPatient.insertOrUpdatePatient(0,
        "TEST",
        "TEST",
        "TEST",
        now,
        "село Проверкино",
        "male",
        "76.0",
        "172.0",
        "ТЕСТ-12М",
        now,
        1,
        "bloodNotes",
        "notes",
        authData.getUser(),
        0);
assertTrue(">>>>>>>pat = null",pat!=null);
dbManager.persist(pat);
toDrop.add(dbPatient.getPatientById(pat.getId()));
assertTrue(">>>>>>>pat = not null",pat==null);
//

//appeal data
AppealData appData=new AppealData();

appData.setData(new AppealEntry());

appData.getData().setNumber("");
appData.getData().setUrgent(false);
appData.getData().setRangeAppealDateTime(new DatePeriodContainer(now,now));
appData.getData().setAmbulanceNumber("test_ambulanceNumber");
appData.getData().setAppealType(new IdNameContainer(168,""));
appData.getData().setAgreedType(new IdNameContainer(-1,""));
appData.getData().setAgreedDoctor("Цуккерман");
appData.getData().setAssignment(new AppealAssignmentContainer(new Date(1333310400000L),"12-677","test","Рабинович"));
appData.getData().setHospitalizationType(new IdNameContainer(3,""));
appData.getData().setHospitalizationPointType(null);
appData.getData().setHospitalizationChannelType(new IdNameContainer(2,""));
appData.getData().setDeliveredType("test_deliveredType");
appData.getData().setDeliveredAfterType("test_afterType");
appData.getData().setStateType("test_stateType");
appData.getData().setPhysicalParameters(new PhysicalParametersContainer(162.0,54.0,37.2,"80","85"));
appData.getData().setInjury("test_injury");

appData.getData().getDiagnoses().add(new DiagnosisContainer("assignment","test_description_1","",dbMkb.getMkbByCode("A04.3")));
appData.getData().getDiagnoses().add(new DiagnosisContainer("aftereffect","test_description_2","",dbMkb.getMkbByCode("A83.0")));
appData.getData().getDiagnoses().add(new DiagnosisContainer("attendant","test_description_3","",dbMkb.getMkbByCode("D55.1")));

//insert test
int testEventId=appealBean.insertAppealForPatient(appData,pat.getId().intValue(),null);//authData);
assertTrue("testAppeals 1>> true",true);
assertTrue("testAppeals 1>> false",false);
if(testEventId>0){
        java.util.HashMap<Event, java.util.Map<Action, java.util.Map<String, java.util.List<Object>>>>result=appealBean.getAppealById(testEventId);
Event event=result.keySet().iterator().next();
Action action=result.get(event).keySet().iterator().next();
Object appType=dbFDRecordBean.getIdValueFDRecordByEventTypeId(25,event.getEventType().getId().intValue());

java.util.Map<String, java.util.List<Object>>values=result.get(event).get(action);
AppealData test=new AppealData(event,action,appType,values,"standart",null,null,null);

}

        //AppealData _test = appealBean.getAppealById(835, null);
        //AppealSimplifiedDataList _test =appealBean.getAllAppealsByPatient(pat.getId().intValue(), null);

        //AppealAssignmentContainer x = new AppealAssignmentContainer();
        assertTrue("testAppeals>> true",true);
assertTrue("testAppeals>> false",false);
}
//}
