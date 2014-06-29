/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.pharmacy;

import misexchange.MISExchangePortType;
import misexchange.RCMRIN000002UV02;
import misexchange.Request;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.*;
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
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.pharmacy.*;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.TestUtilBusiness;
import ru.korus.tmis.util.TestUtilCommon;
import ru.korus.tmis.util.TestUtilDatabase;
import ru.korus.tmis.util.TestUtilLaboratory;

import javax.ejb.EJB;
import javax.mail.internet.MimeUtility;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * @author SZagrebelny
 */
@PersistenceTest
@Transactional(value = TransactionMode.ROLLBACK)
public class PrescriptionTest extends Arquillian {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    JpaExampleBean jpaExample;

    @EJB
    DbEventBeanLocal dbEventBeanLocal;

    @EJB
    DbActionBeanLocal dbActionBean;

    @EJB
    DbActionTypeBeanLocal dbActionTypeBeanLocal;

    @EJB
    PharmacyBeanLocal pharmacyBean;

    @EJB
    DbUUIDBeanLocal dbUUIDBeanLocal;

    final private Integer TEST_PATIENT_ID = 1;

    @Mock
    MISExchangePortType mockPort;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");
//--- common --------------------------------------------------------
        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilDatabase()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilLaboratory()).getPackagesForTest());
//-------------------------------------------------------------------

//--- prescription --------------------------------------------------
        wa.addClass(JpaExample.class);
        wa.addClass(JpaExampleBean.class);
        wa.addClass(DrugChart.class);
        wa.addClass(DrugComponent.class);
        wa.addPackage(PharmacyBeanLocal.class.getPackage());
        wa.addPackage(DbDrugChartBean.class.getPackage());
        wa.addPackage(PrescriptionsTo1C.class.getPackage());
//-------------------------------------------------------------------
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive");
        return wa;
    }

    @BeforeTest
    protected void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void hello() {
        System.out.println("**************************** hello");
        Assert.assertNotNull(jpaExample);
        Assert.assertEquals(jpaExample.getInfo(), JpaExample.INFO_MSG );
        final List<RbFinance> res = em.createNamedQuery("rbFinance.findAll", RbFinance.class).getResultList();
        Assert.assertTrue(!res.isEmpty());
    }

    @Test
    public void testPrescriptions() {
        System.out.println("**************************** testPrescriptions() started...");
        try {
            createPrescriptions();
        } catch (CoreException ex) {
            Assert.fail("Exception in testPrescriptions", ex);
        }
    }

    @Test
    public void pullDb() throws Exception {
        ConfigManager.RbManagerSetting().DebugDemoMode_$eq("on");
        ConfigManager.Common().OrgId_$eq(3479);
        ConfigManager.Drugstore().Active_$eq("on");
        System.out.println("**************************** PrescriptionTest.pullDb");
        initDb();
        Assert.assertNotNull(pharmacyBean);

        pharmacyBean.setMisExchangeSoap(mockPort);
        ArgumentCaptor<MISExchangePortType> captor = ArgumentCaptor.forClass(MISExchangePortType.class);
        pharmacyBean.pooling();
        ArgumentCaptor<Request> argument = ArgumentCaptor.forClass(Request.class);
        verify(mockPort, times(7)).processHL7V3Message(argument.capture());
        final String contextPath = "misexchange";
        final Request msg = argument.getAllValues().get(6);
        String docText = null;
        if (msg instanceof  RCMRIN000002UV02) {
            ((RCMRIN000002UV02)msg).getMessage().getId().setRoot("TEST");
            final List<Object> content = ((RCMRIN000002UV02) msg).getMessage().getControlActProcess().getText().getContent();
            Object docBase64 = content.size() > 5 ? content.get(5) : null;
            if(docBase64 instanceof String) {
                docText = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) docBase64)), "UTF-8");
                docText = docText.replaceAll("root=\".{8}-.{4}-.{4}-.{4}-.{12}\"", "root=\"TEST\"");
                docText = docText.replaceAll("time value=\".{8}\"", "time value=\"20140623\"");
            }
            ((RCMRIN000002UV02)msg).getMessage().getControlActProcess().setText(null);
        }
        Assert.assertTrue(TestUtilCommon.checkArgument(msg, "./src/test/resources/xml/sendPrescription.xml", contextPath));
        Assert.assertTrue(TestUtilCommon.checkArgument(docText, "./src/test/resources/xml/sendPrescriptionDoc.xml"));
    }

    private void initDb() throws InterruptedException {
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/init.sql");
        em.flush();
        Thread.sleep(1000);// чтобы успел отработать триггер в БД ??
    }


    private void createPrescriptions() throws CoreException {
        List<Event> eventList = dbEventBeanLocal.getEventsForPatient(TEST_PATIENT_ID);
        Assert.assertTrue(!eventList.isEmpty());
        Event event = eventList.iterator().next();
        final int actionTypeIdForPrescription = 123; //TODO: must be cretae by flatCode 'prescription'
        Action actionPrescription = dbActionBean.createAction(event.getId(), actionTypeIdForPrescription, null);
        actionPrescription.setUuid(dbUUIDBeanLocal.createUUID());
        final int executorId = 1;
        actionPrescription.setExecutor(em.find(Staff.class, executorId));
        em.persist(actionPrescription);
        em.merge(actionPrescription);
        em.flush();
        createInterval(actionPrescription);
        createDrugComponent(actionPrescription);
        em.flush();
    }

    private void createDrugComponent(Action actionPrescription) {
        DrugComponent comp = new DrugComponent();
        comp.setAction(actionPrescription);
        //TODO add rls tables to createTestArchive!
        comp.setNomen(em.find(RlsNomen.class, 148048));
        comp.setName("Амоксиклав");
        comp.setCreateDateTime(new Date());
        comp.setUnit(comp.getNomen().getUnit());
        em.persist(comp);
    }

    private void createInterval(Action actionPrescription) {
        DrugChart intreval = new DrugChart();
        intreval.setAction(actionPrescription);
        intreval.setBegDateTime(new Date());
        intreval.setStatus((short)0);
        em.persist(intreval);
    }

}