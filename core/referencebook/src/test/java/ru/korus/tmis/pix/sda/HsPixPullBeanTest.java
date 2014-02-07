package ru.korus.tmis.pix.sda;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.*;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.hs.HsPixPullTimerBeanLocal;
import ru.korus.tmis.util.TestUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJB;


import java.io.File;
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
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive for HsPixPullBeanTest");
        return wa;
    }

    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello from HsPixPullBeanTest");
        final List<RbFinance> res = em.createNamedQuery("rbFinance.findAll", RbFinance.class).getResultList();
        Assert.assertTrue(!res.isEmpty());
    }

    @Test
    public void pullDb() {
        System.out.println("**************************** pullDb from HsPixPullBeanTest");
        Assert.assertNotNull(hsPixPullBean);
        hsPixPullBean.pullDb();
    }

}
