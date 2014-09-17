package ru.korus.tmis.pix.sda;

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
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.transmit.Transmitter;
import ru.korus.tmis.hs.HsPixPullTimerBeanLocal;
import ru.korus.tmis.pix.sda.ws.Container;
import ru.korus.tmis.pix.sda.ws.EMRReceiverServiceSoap;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.TestUtilBusiness;
import ru.korus.tmis.util.TestUtilCommon;
import ru.korus.tmis.util.TestUtilDatabase;
import ru.korus.tmis.util.TestUtilLaboratory;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
public class HsPixPullBeanTest extends Arquillian {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    HsPixPullTimerBeanLocal hsPixPullBean;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");

        // common -------------------------------------------------------------------
        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilDatabase()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilLaboratory()).getPackagesForTest());
        // --------------------------------------------------------------------------
        wa.addPackage(Transmitter.class.getPackage());

        wa.addClass(DbQueryBeanLocal.class);
        wa.addClass(DbQueryBean.class);

        wa.addClass(RbMedicalAidTypeBeanLocal.class);
        wa.addClass(RbMedicalAidTypeBean.class);

        wa.addClass(RbMedicalAidProfileBeanLocal.class);
        wa.addClass(RbMedicalAidProfileBean.class);

        wa.addClass(DbSchemeKladrBeanLocal.class);
        wa.addClass(DbSchemeKladrBean.class);

        wa.addClass(HsPixPullBean.class);

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive for HsPixPullBeanTest");
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
        System.out.println("**************************** hello referencebook test test");
        Assert.assertNotNull(em);
        Assert.assertNotNull(this.hsPixPullBean);
    }

    @Mock
    EMRReceiverServiceSoap mockPort;

    @Test
    public void pullDb() throws Exception {
        ConfigManager.RbManagerSetting().DebugDemoMode_$eq("on");
        ConfigManager.Common().OrgId_$eq(3479);
        System.out.println("**************************** pullDb from HsPixPullBeanTest");
        initDb();
        Assert.assertNotNull(hsPixPullBean);
        hsPixPullBean.setPort(mockPort);
        ArgumentCaptor<EMRReceiverServiceSoap> captor = ArgumentCaptor.forClass(EMRReceiverServiceSoap.class);
        hsPixPullBean.pullDb(true);
        ArgumentCaptor<Container> argument = ArgumentCaptor.forClass(Container.class);
        verify(mockPort, times(2)).container(argument.capture());
        final String contextPath = "ru.korus.tmis.pix.sda.ws";
        Assert.assertTrue(TestUtilCommon.checkArgument(argument.getAllValues().get(0), "./src/test/resources/xml/event.xml", contextPath));
        Assert.assertTrue(TestUtilCommon.checkArgument(argument.getAllValues().get(1), "./src/test/resources/xml/patient.xml", contextPath));
    }

    private void initDb() throws InterruptedException {
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/rbDocumentType.sql");
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/rbEventTypePurpose.sql");
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/rbAcheResult.sql");
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/rbSocStatusClass.sql");
        TestUtilCommon.executeQuery(em, "./src/test/resources/sql/init.sql");
        em.flush();
        Thread.sleep(1000);// чтобы успел отработать триггер в БД ??
    }





}
