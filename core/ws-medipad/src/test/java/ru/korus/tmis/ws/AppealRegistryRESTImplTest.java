/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.ws;

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
import ru.korus.tmis.core.database.DbStaffBean;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.core.pharmacy.DbDrugChartBean;
import ru.korus.tmis.util.TestUtil;

import javax.ejb.EJB;
import java.io.File;
import java.util.Date;
import java.util.List;


/**
 * @author SZagrebelny
 */
//@RunWith(Arquillian.class)
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
//@Transactional(value = TransactionMode.ROLLBACK)
//@DataSource(value = "s11r64")
public class AppealRegistryRESTImplTest extends Arquillian {

    @EJB
    private AppealBean appealBean = null;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../drugstore-tier/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");
//--- common --------------------------------------------------------
        wa.addPackages(false, TestUtil.addBaseEntities());
        wa.addPackage(DbEventBeanLocal.class.getPackage());
        wa.addClass(InternalLoggerBeanLocal.class);
        wa.addClass(InternalLoggerBean.class);
        wa.addClass(LoggingInterceptor.class);
        wa.addClass(DbStaffBean.class);
        wa.addClass(DbStaffBeanLocal.class);
//-------------------------------------------------------------------
        wa.addClass(AppealBean.class);

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive");
        return wa;
    }

    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello medipad test test");
        Assert.assertNotNull(appealBean);
    }


}