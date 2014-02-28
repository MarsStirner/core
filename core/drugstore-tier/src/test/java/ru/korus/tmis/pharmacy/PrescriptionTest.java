/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.pharmacy;

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
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.pharmacy.*;
import ru.korus.tmis.util.TestUtilCommon;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@DataSource(value = "s11r64")
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

    @Deployment
    public static Archive createTestArchive() {
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");
//--- common --------------------------------------------------------
        wa.addClass(JpaExample.class);
        wa.addClass(JpaExampleBean.class);
//        wa.addClass(RbFinance.class);

        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
         wa.addPackage(DbEventBeanLocal.class.getPackage());
        wa.addClass(LoggingInterceptor.class);
        wa.addClass(DbStaffBean.class);
        wa.addClass(DbStaffBeanLocal.class);
//-------------------------------------------------------------------

//--- prescription --------------------------------------------------
        wa.addClass(DrugChart.class);
        wa.addClass(DrugComponent.class);
        wa.addPackage(PharmacyBeanLocal.class.getPackage());
        /*wa.addClass(PharmacyBeanLocal.class);
        wa.addClass(PharmacyBean.class);
        wa.addClass(DbDrugChartBeanLocal.class);
        wa.addClass(DbDrugChartBean.class);
        wa.addClass(DbPharmacyBeanLocal.class);
        wa.addClass(DbPharmacyBean.class);
        wa.addClass(DbDrugChartBean.class);
        wa.addClass(DbRbFinance1CBean.class);
        wa.addClass(DbRbFinance1CBeanLocal.class);
        wa.addClass(DbPrescriptionsTo1CBeanLocal.class);
        wa.addClass(DbPrescriptionsTo1CBean.class);
        wa.addClass(DbRbMethodOfAdministrationLocal.class);
        wa.addClass(DbRbMethodOfAdministration.class);
        wa.addClass(DbPrescriptionSendingResBeanLocal.class);
        wa.addClass(DbPrescriptionSendingResBean.class);*/
        wa.addPackage(DbDrugChartBean.class.getPackage());
        wa.addPackage(PrescriptionsTo1C.class.getPackage());
//-------------------------------------------------------------------


        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        System.out.println("**************************** createTestArchive");
        return wa;
    }

    @Test
    //@Cleanup(phase = TestExecutionPhase.NONE)
    public void hello() {
        System.out.println("**************************** hello");
        Assert.assertNotNull(jpaExample);
        Assert.assertEquals(jpaExample.getInfo(), JpaExample.INFO_MSG );
        final List<RbFinance> res = em.createNamedQuery("rbFinance.findAll", RbFinance.class).getResultList();
        Assert.assertTrue(!res.isEmpty());
    }

    @Test
    public void testPrescriptions() {
        try {
            createPrescriptions();
        } catch (CoreException ex) {
            Assert.fail("Exception in testPrescriptions", ex);
        }
    }

    private void createPrescriptions() throws CoreException {
        HL7PacketBuilder.setTestMode(true);
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
        pharmacyBean.sendPrescriptionTo1C();
        //TODO: Добавить проверку выходного пакета!
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