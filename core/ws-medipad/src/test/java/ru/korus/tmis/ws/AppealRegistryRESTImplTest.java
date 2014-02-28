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
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.data.*;
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
import scala.actors.threadpool.Arrays;

import javax.ejb.EJB;
import java.io.File;
import java.util.Date;


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

        wa.addClass(AuthStorageBeanLocal.class);
        wa.addClass(AuthStorageBean.class);
        //wa.add(UsersMgr.class);

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive medipad");
        return wa;
    }

    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello medipad test test");
        Assert.assertNotNull(appealBean);
        Assert.assertNotNull(authStorageBeanLocal);
        Assert.assertNotNull(usersMgr);
        Assert.assertNotNull(eventBeanLocal);
    }

    @Test
    public void testInsertAppealForPatient() {
        AppealData appealData = new AppealData();
        int patientId = 2;
        try {
            int countEvent = eventBeanLocal.getEventsForPatient(patientId).size();
            createTestUser();
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

            AuthData authData =  authStorageBeanLocal.createToken("test", "098f6bcd4621d373cade4e832627b4f6" /*MD5 for 'test'*/, 29);
            appealBean.insertAppealForPatient(appealData, patientId, authData);
            int countEventNew = eventBeanLocal.getEventsForPatient(patientId).size();
            Assert.assertEquals(countEventNew, countEvent + 1);
            //TODO  add more assertion!
        } catch (CoreException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private void createTestUser() {
        JsonPerson personInfo = new JsonPerson();
        personInfo.setFname("test");
        personInfo.setLname("test");
        personInfo.setPname("test");
        personInfo.setLogin("test");
        personInfo.setPassword("test");
        personInfo.setRoles(Arrays.asList(new String[]{"admNurse"}));
        usersMgr.create(personInfo);
    }

}