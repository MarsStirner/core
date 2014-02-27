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
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.data.AppealData;
import ru.korus.tmis.core.database.DbRbResultBeanLocal;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.core.patient.AppealBeanLocal;
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;
import ru.korus.tmis.util.TestUtilBusiness;
import ru.korus.tmis.util.TestUtilCommon;

import javax.ejb.EJB;
import java.io.File;


/**
 * @author SZagrebelny
 */
//@RunWith(Arquillian.class)
@PersistenceTest
//@Transactional(value = TransactionMode.DISABLED)
@Transactional(value = TransactionMode.ROLLBACK)
//@DataSource(value = "s11r64")
public class AppealRegistryRESTImplTest extends Arquillian {

    @EJB
    private AppealBeanLocal appealBean = null;

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");

        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive medipad");
        return wa;
    }

    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello medipad test test");
        Assert.assertNotNull(appealBean);
    }

    @Test
    public void testInsertAppealForPatient() {
        AppealData appealData = new AppealData();
        int patientId = 3;
        try {
            appealBean.insertAppealForPatient(appealData, patientId, null);
        } catch (CoreException e) {
            e.printStackTrace();
            //TODO
        }
        //TODO
    }

}