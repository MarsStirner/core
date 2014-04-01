package ru.korus.tmis.pix.sda;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.DataSource;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.common.InternalLoggerBean;
import ru.korus.tmis.core.database.common.InternalLoggerBeanLocal;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.APValueHospitalBed;
import ru.korus.tmis.core.entity.model.RbFinance;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.hs.HsPixPullTimerBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.TestUtil;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.02.14, 10:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

//@RunWith(Arquillian.class)
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
//@Transactional(value = TransactionMode.ROLLBACK)
@DataSource(value = "s11r64")
public class HsPixPullBeanTest extends Arquillian {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    HsPixPullTimerBeanLocal hsPixPullBean;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");
//--- common --------------------------------------------------------
//        wa.addClass(RbFinance.class);

        wa.addPackages(false, TestUtil.addBaseEntities());
        wa.addPackages(false, Kladr.class.getPackage());
        wa.addPackage(DbEventBeanLocal.class.getPackage());
        wa.addClass(InternalLoggerBeanLocal.class);
        wa.addClass(InternalLoggerBean.class);
//-------------------------------------------------------------------
        wa.addClass(DbQueryBeanLocal.class);
        wa.addClass(DbQueryBean.class);

        wa.addClass(RbMedicalAidTypeBeanLocal.class);
        wa.addClass(RbMedicalAidTypeBean.class);

        wa.addClass(RbMedicalAidProfileBeanLocal.class);
        wa.addClass(RbMedicalAidProfileBean.class);

        wa.addClass(DbSchemeKladrBeanLocal.class);
        wa.addClass(DbSchemeKladrBean.class);

        wa.addClass(HsPixPullBean.class);

        wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));
        //wa.addAsWebInfResource(new File("./src/test/resources/init.sql"), "classes/init.sql");
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive for HsPixPullBeanTest");
        return wa;
    }

    //Чтобы тест работал при запуске через IDEA необходимо поставить в зависимость Maven: org.glassfish.extras:glassfish-embedded-all:3.2-b06 выше других библиотек
    //File->Project Structure...->Modules - <cur module> - dependencies - поставить org.glassfish.extras:glassfish-embedded выше всех других либ с slf4j
    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello from HsPixPullBeanTest");
        final List<RbFinance> res = em.createNamedQuery("rbFinance.findAll", RbFinance.class).getResultList();
        final List<APValueHospitalBed> props = em.createNamedQuery( "APValueHospitalBed.findAll", APValueHospitalBed.class).setHint("eclipselink.PARAMETER_DELIMITER", "`").getResultList();
        Assert.assertTrue(!res.isEmpty());
    }

    @Test
    public void pullDb() {
        System.out.println("**************************** pullDb from HsPixPullBeanTest");
        initDb();
        Assert.assertNotNull(hsPixPullBean);
        hsPixPullBean.pullDb(true);
    }

    private void initDb() {
        executeQuery(getSqlFromFile("./src/test/resources/rbEventTypePurpose.sql"));
        executeQuery(getSqlFromFile("./src/test/resources/rbAcheResult.sql"));
        executeQuery(getSqlFromFile("./src/test/resources/init.sql"));
    }

    private void executeQuery(String[] sqlFromFile) {
        for(String sql : sqlFromFile) {
            em.createNativeQuery(sql).executeUpdate();
        }
    }

    private String[] getSqlFromFile(String sqlFileNAme) {
        try {
            return (new String(Files.readAllBytes(Paths.get(sqlFileNAme)), "UTF-8")).split(";");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.fail("cannot load initial SQL request");
        }
        return null;
    }

}
