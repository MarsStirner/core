package ru.korus.test.common;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.*;

import ru.korus.tmis.core.database.*;

import ru.korus.tmis.core.entity.model.ClientAllergy;
import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.RbSocStatusType;
import ru.korus.tmis.core.entity.model.fd.*;

@RunWith(org.jboss.arquillian.junit.Arquillian.class)
public class CommonTestCase {

    /*
      *
      * После деплоя Аркуилиан может устанавливать сюда переменные из контекста приложения
      * при помощи аннотаций EJB и Inject
      */
    @EJB
    private DbClientAllergyBeanLocal clientAllergy;

    @EJB
    private DbClientDocumentBeanLocal clientDocument;

    @EJB
    private DbPatientBeanLocal dbPatient;

    @EJB
    private DbManagerBeanLocal dbManager;

    //коллекция объектов, созданных в процессе тестирования и подлежащих удалению по окончании теста
    private  Set<Object> toDrop = new HashSet<Object>();

    /*
      * Создание архива для деплоя тестового приложения
      */
    @Deployment(name="glassfish",testable = true)
    public static Archive createTestArchive() {

        /*
           * Тестируемые классы и зависимости
           *
           */
        Class<?>[] classes = new Class<?>[] {
                DbRbSocTypeBeanLocal.class, DbRbSocTypeBean.class,
                DbRbDocumentTypeBeanLocal.class, DbRbDocumentTypeBean.class,
                DbRbBloodTypeBeanLocal.class, DbRbBloodTypeBean.class,
                DbPatientBeanLocal.class, DbPatientBean.class,
                DbClientAllergyBean.class,
                DbClientAllergyBeanLocal.class,
                DbClientDocumentBean.class,
                DbClientDocumentBeanLocal.class,
                DbManagerBean.class, DbManagerBeanLocal.class,
                DbCustomQueryLocal.class, DbCustomQueryBean.class,
                DbEventBeanLocal.class, DbEventBean.class,
                DbActionTypeBeanLocal.class, DbActionTypeBean.class,
                DbRbCounterBeanLocal.class, DbRbCounterBean.class,
                DbFlatDirectoryBeanLocal.class, DbFlatDirectoryBean.class,
                DbFDFieldValueBeanLocal.class,   DbFDFieldValueBean.class,
                DbFDRecordBeanLocal.class, DbFDRecordBean.class,
                DbFDFieldBeanLocal.class, DbFDFieldBean.class,
                FDFieldValueId.class, FDRecord.class, FlatDirectory.class,
                FDFieldValue.class, FDField.class, FDFieldType.class,
                ClientFlatDirectory.class, ClientFDProperty.class,

                ru.korus.tmis.core.logging.LoggingInterceptor.class,
                ru.korus.tmis.core.database.InternalLoggerBeanLocal.class,
                ru.korus.tmis.core.database.InternalLoggerBean.class
        };

        /*
           * EnterpriseArchive - ear
           * WebArchive - war
           * JavaArchive - jar
           * используется апи ShrinkWrap
           *
           */
        WebArchive wa = ShrinkWrap.create(WebArchive.class,"test.war");
        wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
        wa.addClasses(classes);
        wa.addPackage(RbSocStatusType.class.getPackage()); //добавляем все ентити
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));

        System.out.println(wa.toString());
        return wa;
    }



    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        //зачистка (но вообще-то и так всё откатывается)
        dbManager.removeAll(this.toDrop);
    }


    /*
      *
      * Тестирование по протоколу EJB (есть еще Servlet 2.5 и Servlet 3.0)
      */
   /* @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientAllergies() throws Exception{
        Date now  = new Date();

        Patient pat  = dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "2222", now, 0, "bloodNotes", "notes", null, 0);

        dbManager.persist(pat);

        toDrop.add(dbPatient.getPatientById(pat.getId()));

        ClientAllergy test = new ClientAllergy();
        test.setId(-1);
        test.setCreateDate(now);
        test.setModifyDatetime(now);
        test.setPower(Integer.MIN_VALUE);
        test.setNameSubstance("TEST");
        test.setCreateDatetime(now);
        test.setNotes("TEST");
        test.setPatient(pat);

        ClientAllergy _test = null;

        dbManager.persist(test);

        _test = clientAllergy.getClientAllergyById(test.getId());

        toDrop.add(_test);

        assertTrue("persisted Id", test.getId().equals(_test.getId()));
        assertTrue("persisted Power",test.getPower() == _test.getPower());
        assertTrue("persisted NameSubstance",test.getNameSubstance().equals(_test.getNameSubstance()));
        assertTrue("persisted Notes",test.getNotes().equals(_test.getNotes()));

        test.setPower(Integer.MAX_VALUE);
        dbManager.merge(test);

        _test = clientAllergy.getClientAllergyById(test.getId());
        assertTrue("merged Id", test.getId().equals(_test.getId()));
        assertTrue("merged Power", test.getPower() == _test.getPower());
        assertTrue("merged NameSubstance", test.getNameSubstance().equals(_test.getNameSubstance()));
        assertTrue("merged Notes", test.getNotes().equals(_test.getNotes()));
    }*/

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void testClientIdCards() throws Exception{

        try {
            Date now  = new Date();

            Patient pat  = dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                    now, "1", "12", "11", "111", "2222", now, 0, "bloodNotes", "notes", null, 0);

            dbManager.persist(pat);

            toDrop.add(dbPatient.getPatientById(pat.getId()));

            ClientDocument test =  clientDocument.insertOrUpdateClientDocument(-1,//id: Int
                    3,//rbDocumentTypeId: Int - Свид. о рожд
                    "TEST",//issued: String
                    "TEST",//number: String
                    "TEST",//serial: String
                    now,//date: Date
                    now,
                    pat,//patient: Patient
                    null //sessionUser: Staff
            );

            dbManager.persist(test);

            ClientDocument _test = clientDocument.getClientDocumentById(test.getId());

            toDrop.add(_test);

            assertTrue("persisted Id", test.getId().equals(_test.getId()));
            assertTrue("persisted rbDocumentTypeId",test.getDocumentType().getId() == _test.getDocumentType().getId());
            assertTrue("persisted issued",test.getIssued().equals(_test.getIssued()));
            assertTrue("persisted number",test.getNumber().equals(_test.getNumber()));
            assertTrue("persisted serial",test.getSerial().equals(_test.getSerial()));

            ClientDocument test1 =  clientDocument.insertOrUpdateClientDocument(test.getId(),//id: Int
                                                                                3,//rbDocumentTypeId: Int - Свид. о рожд
                                                                                "TEST1",//issued: String
                                                                                "TEST1",//number: String
                                                                                "TEST1",//serial: String
                                                                                now,//date: Date
                                                                                now,
                                                                                pat,//patient: Patient
                                                                                null //sessionUser: Staff
            );

            dbManager.merge(test1);

            _test = clientDocument.getClientDocumentById(test1.getId());

            if(test1.getId() != test.getId()) {//добавился лишний объект
                toDrop.add(_test);
            }
            assertTrue("merged id", _test.getId().equals(test.getId()));

            assertTrue("merged issued :" + _test.getIssued(), _test.getIssued().equals("TEST1"));
        }
        catch (Exception e ){
          assertTrue("Exception :" + e.getMessage(), false);
          System.out.println(e.getMessage());
        }
    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientPolicies() throws Exception{

    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientContacts() throws Exception{

    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientDrugIntolerances() throws Exception{

    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientRelatives() throws Exception{

    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientSocStatuses() throws Exception{    //SocStatuses, Disabilities, Occupations

    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientEvents() throws Exception{    //Appeal


    }
}
