package ru.korus.tmis.ws.finance;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.finance.*;
import ru.korus.tmis.core.entity.model.EventPayment;
import ru.korus.tmis.core.transmit.Transmitter;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.TestUtilCommon;
import ru.korus.tmis.util.TestUtilDatabase;
import ru.korus.tmis.ws.finance.odvd.Table;
import ru.korus.tmis.ws.finance.odvd.WsPoliclinicPortType;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.02.14, 10:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

//@RunWith(Arquillian.class)
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
@Transactional(value = TransactionMode.ROLLBACK)
public class FinancePullBeanTest extends Arquillian {

    private static final String TEST_SERVICE_CODE = "В02.018.01";
    private static final String TEST_SERVICE_NAME = "Процедуры сестринского ухода при подготовке больного к колопроктологической операции";
    private static final double TEST_SERVICE_AMOUNT = 2.0;
    private static final double TEST_SERVICE_SUM = 10000.0;
    private static final double TEST_SERVICE_SUM_DISC = 1000.0;
    private static final int TEST_SERVICE_PATIENT_ID = 347610;
    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    PaymentBeanLocal paymentBeanLocal;

    @EJB
    FinancePullBeanLocal financePullBean;

    @EJB
    DbActionBeanLocal dbActionBeanLocal;

    private static final Integer TEST_SERVICE_ACTION_ID = 793630;

    private final Integer TEST_EVENT_ID = 841672;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");

        // common -------------------------------------------------------------------
        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilDatabase()).getPackagesForTest());
        /// wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());
        // --------------------------------------------------------------------------
        wa.addPackage(Transmitter.class.getPackage());

        wa.addClass(DbEventLocalContractLocal.class);
        wa.addClass(DbEventLocalContract.class);

        wa.addClass(DbEventPaymentLocal.class);
        wa.addClass(DbEventPayment.class);

        wa.addClass(FinancePullBeanLocal.class);
        wa.addClass(FinancePullBean.class);

        wa.addClass(PaymentBeanLocal.class);
        wa.addClass(PaymentBean.class);

        //wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));
        //wa.addAsWebInfResource(new File("./src/test/resources/init.sql"), "classes/init.sql");
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive for FinancePullBeanTest");
        return wa;
    }

    @BeforeTest
    protected void setUp() throws Exception {
        initMocks(this);
    }


    //Чтобы тест работал при запуске через IDEA необходимо поставить в зависимость Maven: org.glassfish.extras:glassfish-embedded-all:3.2-b06 выше других библиотек
    //File->Project Structure...->Modules - <cur module> - dependencies - поставить org.glassfish.extras:glassfish-embedded выше всех других либ с slf4j
    @Test
    public void hello() {
        System.out.println("**************************** hello FinancePullBeanTest test test");
        Assert.assertNotNull(em);
        Assert.assertNotNull(this.financePullBean);
        Assert.assertNotNull(this.dbActionBeanLocal);
    }

    @Mock
    WsPoliclinicPortType mockPort;

    @Test
    public void pullDb() throws Exception {
        ConfigManager.Finance().FinanceActive_$eq("on");
        System.out.println("**************************** pullDb from FinancePullBeanTest");
        initDb();
        Assert.assertNotNull(financePullBean);
        financePullBean.setPort(mockPort);
        ArgumentCaptor<WsPoliclinicPortType> captor = ArgumentCaptor.forClass(WsPoliclinicPortType.class);
        financePullBean.pullDb();
        checkPutTreatment();
        checkPutService();
    }

    @Test
    public void getServiceList() throws Exception {
        ConfigManager.Finance().FinanceActive_$eq("on");
        System.out.println("**************************** pullDb from FinancePullBeanTest");
        Assert.assertNotNull(paymentBeanLocal);
        initDb();
        ServiceListResult res = paymentBeanLocal.getServiceList(TEST_EVENT_ID);
        Assert.assertTrue(res.getIdTreatment().equals(TEST_EVENT_ID));
        final int countOfService = 1;
        Assert.assertEquals(res.getListService().size(), countOfService);
        Integer[] actionsId = {793630};
        for(int i = 0; i < countOfService; ++i) {
            ServiceInfo serviceInforFromDb = new ServiceInfo(dbActionBeanLocal.getActionById(actionsId[i]));
            Assert.assertEquals(res.getListService().get(i), serviceInforFromDb);
        }
    }

    @Test
    public void setPaymentInfo() throws Exception {
        ConfigManager.Finance().FinanceActive_$eq("on");
        System.out.println("**************************** pullDb from FinancePullBeanTest");
        Assert.assertNotNull(paymentBeanLocal);
        initDb();
        List<ServicePaidInfo> servicePaidInfoList = new LinkedList<ServicePaidInfo>();
        PersonName personName = new PersonName("Тестов", "Тест", "Тестович");
        servicePaidInfoList.add(getTestServicePaidInfo());
        Integer res = paymentBeanLocal.setPaymentInfo(new Date(), "test", TEST_EVENT_ID, personName, servicePaidInfoList);
        Assert.assertTrue(res.equals(TEST_EVENT_ID));
        Object lastId = em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
        Assert.assertTrue(lastId instanceof Long);
        EventPayment eventPayment = em.find(EventPayment.class, ((Long)lastId).intValue());
        Assert.assertNotNull(eventPayment);
        checkTestServicePaidInfo(eventPayment);
    }

    private void checkTestServicePaidInfo(EventPayment eventPayment) {
        Assert.assertNotNull(eventPayment.getAction());
        Assert.assertEquals(eventPayment.getAction().getId(), TEST_SERVICE_ACTION_ID);
        Assert.assertNotNull(eventPayment.getService());
        Assert.assertEquals(eventPayment.getService().getCode(), TEST_SERVICE_CODE);
        Assert.assertEquals(eventPayment.getService().getName(), TEST_SERVICE_NAME);
        Assert.assertTrue(eventPayment.getAction().getAmount() == TEST_SERVICE_AMOUNT);
        Assert.assertTrue(eventPayment.getAction().getEvent().getPatient().getId() == TEST_SERVICE_PATIENT_ID);
       //Assert.assertEquals(eventPayment.get);
        //TODO add check TypePayment

    }

    private ServicePaidInfo getTestServicePaidInfo() {
        ServicePaidInfo res = new ServicePaidInfo();
        res.setIdAction(TEST_SERVICE_ACTION_ID);
        res.setCodeService(TEST_SERVICE_CODE);
        res.setNameService(TEST_SERVICE_NAME);
        res.setAmount(TEST_SERVICE_AMOUNT);
        res.setSum(TEST_SERVICE_SUM);
        res.setSumDisc(TEST_SERVICE_SUM_DISC);
        res.setCodePatient("" + TEST_SERVICE_PATIENT_ID);
        res.setTypePayment(true);
        return res;
    }


    private void checkPutTreatment() {
        ArgumentCaptor<BigInteger> idTreatmentCapture = ArgumentCaptor.forClass(BigInteger.class);
        ArgumentCaptor<String> numTreatmentCapator = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<XMLGregorianCalendar> dateTreatmentCapator = ArgumentCaptor.forClass(XMLGregorianCalendar.class);
        ArgumentCaptor<String> codeContractCapator = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> codePatientCapator = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> patientNameCapator = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> paidNameCapator = ArgumentCaptor.forClass(String.class);
        verify(mockPort, times(1)).putTreatment(idTreatmentCapture.capture(),
                numTreatmentCapator.capture(),
                dateTreatmentCapator.capture(),
                codeContractCapator.capture(),
                codePatientCapator.capture(),
                patientNameCapator.capture(),
                paidNameCapator.capture());
        Assert.assertEquals(idTreatmentCapture.getValue(), BigInteger.valueOf(TEST_EVENT_ID));
        Assert.assertEquals(numTreatmentCapator.getValue(), "1111/22");
        try {
            Assert.assertEquals(dateTreatmentCapator.getValue(),
                    DatatypeFactory.newInstance().newXMLGregorianCalendar("2014-01-22T10:39:23.000+04:00"));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Assert.assertEquals(codeContractCapator.getValue(), "2201-п");
        Assert.assertEquals(codePatientCapator.getValue(), "347610");
        Assert.assertEquals(patientNameCapator.getValue(), "АРИНА ВЯЧЕСЛАВОВНА КОЗИНА");
        Assert.assertEquals(paidNameCapator.getValue(), "Юлия Владимировна Копытина");
    }

    private void checkPutService() throws Exception {
        ArgumentCaptor<BigInteger> idTreatmentCapture = ArgumentCaptor.forClass(BigInteger.class);
        ArgumentCaptor<Table> listServiceCompleteCapture = ArgumentCaptor.forClass(Table.class);
        verify(mockPort, times(1)).putService(idTreatmentCapture.capture(), listServiceCompleteCapture.capture());
        Assert.assertEquals(idTreatmentCapture.getValue(), BigInteger.valueOf(TEST_EVENT_ID));
        final String contextPath = "ru.korus.tmis.ws.finance.odvd";
        Assert.assertTrue(TestUtilCommon.checkArgument(listServiceCompleteCapture.getAllValues().get(0), "./src/test/resources/xml/services.xml", contextPath));
    }


    private void initDb() throws InterruptedException {
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/init.sql");
        em.flush();
        Thread.sleep(1000);// чтобы успел отработать триггер в БД ??
    }

}
