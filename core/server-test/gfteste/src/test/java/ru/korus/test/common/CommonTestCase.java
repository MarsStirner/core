/*package ru.korus.test.common;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateful;
//import javax.ejb.TimerService;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import javax.inject.Inject;

import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.*;

import ru.korus.tmis.core.assessment.AssessmentBean;
import ru.korus.tmis.core.assessment.AssessmentBeanLocal;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBean;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal;
import ru.korus.tmis.core.common.TypeFilterBean;
import ru.korus.tmis.core.common.TypeFilterBeanLocal;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.*;

import ru.korus.tmis.core.diagnostic.DiagnosticBean;
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.fd.*;
import ru.korus.tmis.core.patient.*;
import ru.korus.tmis.core.thesaurus.ThesaurusBean;
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal;
import ru.korus.tmis.core.treatment.TreatmentBean;
import ru.korus.tmis.core.treatment.TreatmentBeanLocal;
//import ru.korus.tmis.ws.impl.AuthenticationWSImpl;
//import ru.korus.tmis.ws.impl.MedipadWSImpl;

@RunWith(org.jboss.arquillian.junit.Arquillian.class)
public class CommonTestCase {

	/*
	 * 
	 * После деплоя Аркуилиан может устанавливать сюда переменные из контекста приложения
	 * при помощи аннотаций EJB и Inject
	 */
/*	@EJB
	private DbClientAllergyBeanLocal clientAllergy;

    @EJB
    private DbClientDocumentBeanLocal clientDocument;

    @EJB
    private DbClientPolicyBeanLocal dbClientPolicyBean;

    @EJB
    private DbClientContactBeanLocal dbClientContactBean;

    @EJB
    private DbClientIntoleranceMedicamentBeanLocal dbClientIntoleranceMedicament;

    @EJB
    private DbClientRelationBeanLocal dbClientRelationBeanLocal;

    @EJB
    private DbClientSocStatusBeanLocal dbClientSocStatusBean;

	@EJB
	private DbPatientBeanLocal dbPatient;
	
	@EJB
	private DbManagerBeanLocal dbManager;

    @EJB
    private DbStaffBeanLocal dbStaff;

    //@EJB
    //private DbMkbBeanLocal dbMkb;

    //@Inject
    //MedipadWSImpl wsImpl;
    //@EJB
    //private AppealBeanLocal appealBean;

    //@EJB
    //private DbFDRecordBeanLocal dbFDRecordBean;

    //@EJB
    //private AuthStorageBeanLocal authStorage;

    //коллекция объектов, созданных в процессе тестирования и подлежащих удалению по окончании теста
    private  Set<Object> toDrop = new HashSet<Object>();

    //@Inject
    //AuthenticationWSImpl wsAuth;

    //private AuthData authData;
	/*
	 * Создание архива для деплоя тестового приложения
	 */
/*	@Deployment(name="glassfish",testable = true)
	public static Archive createTestArchive() {

		/*
		 * Тестируемые классы и зависимости
		 * 
		 */
/*		Class<?>[] classes = new Class<?>[] {
			/*	DbRbSocTypeBeanLocal.class, DbRbSocTypeBean.class,
                DbRbDocumentTypeBeanLocal.class, DbRbDocumentTypeBean.class,
                DbRbBloodTypeBeanLocal.class, DbRbBloodTypeBean.class,
                DbPatientBeanLocal.class, DbPatientBean.class,
				DbClientAllergyBean.class,
				DbClientAllergyBeanLocal.class,
                DbClientDocumentBean.class,
                DbClientDocumentBeanLocal.class,
                DbClientPolicyBeanLocal.class, DbClientPolicyBean.class,
                DbRbPolicyTypeBeanLocal.class, DbRbPolicyTypeBean.class,
                DbOrganizationBean.class, DbOrganizationBeanLocal.class,
                DbClientContactBeanLocal.class, DbClientContactBean.class,
                DbRbContactTypeBeanLocal.class, DbRbContactTypeBean.class,
                DbClientIntoleranceMedicamentBeanLocal.class, DbClientIntoleranceMedicamentBean.class,
                DbClientRelationBeanLocal.class, DbClientRelationBean.class,
                DbRbRelationTypeBeanLocal.class, DbRbRelationTypeBean.class,
                DbClientSocStatusBeanLocal.class, DbClientSocStatusBean.class,

                /*AppealBeanLocal.class, AppealBean.class,
                DbActionPropertyBeanLocal.class, DbActionPropertyBean.class,
                DbActionPropertyTypeBeanLocal.class, DbActionPropertyTypeBean.class,
                DbActionBeanLocal.class, DbActionBean.class,
                DbActionTypeBeanLocal.class, DbActionTypeBean.class,
                DbEventBeanLocal.class, DbEventBean.class,
                DbRbCounterBeanLocal.class, DbRbCounterBean.class,
                DbStaffBeanLocal.class, DbStaffBean.class,
                DbRbCoreActionPropertyBeanLocal.class, DbRbCoreActionPropertyBean.class,
                DbCustomQueryLocal.class, DbCustomQueryBean.class,
                AppLockBeanLocal.class, AppLockBean.class,
                DbRbSocStatusClassBeanLocal.class, DbRbSocStatusClassBean.class,
                DbFlatDirectoryBeanLocal.class, DbFlatDirectoryBean.class,
                DbFDFieldValueBeanLocal.class,   DbFDFieldValueBean.class,
                DbFDRecordBeanLocal.class, DbFDRecordBean.class,
                DbFDFieldBeanLocal.class, DbFDFieldBean.class,
                FDFieldValueId.class, FDRecord.class, FlatDirectory.class,
                FDFieldValue.class, FDField.class, FDFieldType.class,
                ClientFlatDirectory.class, ClientFDProperty.class,
                DbMkbBeanLocal.class, DbMkbBean.class,
                DbVersionBeanLocal.class, DbVersionBean.class,
                PatientBeanLocal.class, PatientBean.class,
                DbSchemeKladrBeanLocal.class,DbSchemeKladrBean.class,
                DbClientWorkBeanLocal.class,DbClientWorkBean.class,
                DbTempInvalidBeanLocal.class, DbTempInvalidBean.class,
                DbRbTempInvalidReasonBeanLocal.class, DbRbTempInvalidReasonBean.class,
                DbClientAddressBeanLocal.class, DbClientAddressBean.class,
                DbRbTempInvalidDocumentBeanLocal.class, DbRbTempInvalidDocumentBean.class,
                AssessmentBeanLocal.class, AssessmentBean.class,
                TypeFilterBeanLocal.class, TypeFilterBean.class,
                CommonDataProcessorBeanLocal.class, CommonDataProcessorBean.class,
                DbOrgStructureBeanLocal.class, DbOrgStructureBean.class,
                DiagnosticBeanLocal.class, DiagnosticBean.class,
                ThesaurusBeanLocal.class, ThesaurusBean.class,
                DbThesaurusBeanLocal.class, DbThesaurusBean.class,
                TreatmentBeanLocal.class, TreatmentBean.class,
                DbRlsBeanLocal.class, DbRlsBean.class,
                PrimaryAssessmentBeanLocal.class, PrimaryAssessmentBean.class,
                SeventhFormBeanLocal.class, SeventhFormBean.class,
                HospitalBedBeanLocal.class, HospitalBedBean.class,
                AssignmentBeanLocal.class, AssignmentBean.class,*/

//AuthenticationWSImpl.class,
//AuthStorageBeanLocal.class, AuthStorageBean.class,
/*                DbManagerBean.class, DbManagerBeanLocal.class,
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
/*		WebArchive wa = ShrinkWrap.create(WebArchive.class,"test.war");
		wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
		wa.addClasses(classes);
		wa.addPackage(RbSocStatusType.class.getPackage()); //добавляем все ентити
        //wa.addPackage(AuthenticationWSImpl.class.getPackage());
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
        //dbManager.removeAll(this.toDrop);
    }
	

	/*
	 * 
	 * Тестирование по протоколу EJB (есть еще Servlet 2.5 и Servlet 3.0)
	 */
/*@Test
@OperateOnDeployment("glassfish")
@OverProtocol("EJB")
public void testClientAllergies() throws Exception{
   Date now  = new Date();

   Staff testUser = dbStaff.getStaffByLogin("ДБалашов");

   Patient pat  = dbPatient.insertOrUpdatePatient( 0,
                                                   "TEST",
                                                   "TEST",
                                                   "TEST",
                                                   now,
                                                   "село Проверкино",
                                                   "male",
                                                   "76.0",
                                                   "172.0",
                                                   "ТЕСТ-12М",
                                                   now,
                                                   1,
                                                   "bloodNotes",
                                                   "notes",
                                                   testUser);

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
} */
/*
    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void testClientIdCards() throws Exception{
        Date now  = new Date();

        Patient pat  = dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "VF12R", now, 0, "bloodNotes", "notes", null);

        dbManager.persistOrMerge(pat);

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

        dbManager.persistOrMerge(test);

        ClientDocument _test = clientDocument.getClientDocumentById(test.getId());

        toDrop.add(_test);

        assertTrue("persisted Id", test.getId().equals(_test.getId()));
        assertTrue("persisted rbDocumentTypeId", test.getDocumentType().getId() == _test.getDocumentType().getId());
        assertTrue("persisted issued", test.getIssued().equals(_test.getIssued()));
        assertTrue("persisted number", test.getNumber().equals(_test.getNumber()));
        assertTrue("persisted serial", test.getSerial().equals(_test.getSerial()));

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

        dbManager.persistOrMerge(test1);

        _test = clientDocument.getClientDocumentById(test1.getId());

        if(test1.getId() != test.getId()) {//добавился лишний объект
            toDrop.add(_test);
        }
        assertTrue("merged id", _test.getId().equals(test.getId()));

        assertTrue("merged issued", _test.getIssued().equals("TEST1"));
    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientPolicies() throws Exception{

        Date now  = new Date();

        Patient pat  = dbManager.persistOrMerge(
                dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "VF12R", now, 0, "bloodNotes", "notes", null));

        toDrop.add(dbPatient.getPatientById(pat.getId()));

        //insert test
        ClientPolicy test = dbManager.persistOrMerge(
                dbClientPolicyBean.insertOrUpdateClientPolicy(-1,              //int id,
                        2,              //int rbPolicyTypeId, //омс произв
                        0,              //int insurerId,   //id на какую таблицу в дб?
                        "23670023",     //String number,
                        "TEST",       //String serial,
                        now,            //Date startDate,
                        now,            //Date endDate,
                        "INSURE",       //String name,
                        "T",            //String note,
                        pat,            //Patient patient,
                        null));          //Staff sessionUser)

        ClientPolicy _test = dbClientPolicyBean.getClientPolicyById(test.getId().intValue());

        toDrop.add(_test);

        assertTrue("testClientPolicies>> persisted id", test.getId().equals(_test.getId()));
        assertTrue("testClientPolicies>> persisted rbPolicyTypeId",test.getPolicyType().equals(_test.getPolicyType()));
        //assertTrue("testClientPolicies>> persisted insurerId", test.getInsurer().equals(_test.getInsurer()));
        assertTrue("testClientPolicies>> persisted number", test.getNumber().compareTo(_test.getNumber())==0);
        assertTrue("testClientPolicies>> persisted serial", test.getSerial().compareTo(_test.getSerial())==0);
        assertTrue("testClientPolicies>> persisted name", test.getName().compareTo(_test.getName())==0);
        assertTrue("testClientPolicies>> persisted note", test.getNote().compareTo(_test.getNote())==0);

        //update test
        ClientPolicy test1 = dbManager.persistOrMerge(
                dbClientPolicyBean.insertOrUpdateClientPolicy( test.getId().intValue(),
                        2, 0, "11110023", "TEST-2", now, now, "INSURE-MODiFY", "T", pat, null));

        _test = dbClientPolicyBean.getClientPolicyById(test.getId().intValue());

        assertTrue("testClientPolicies>> merged id", _test.getId().equals(test1.getId()));   //id const
        assertTrue("testClientPolicies>> merged rbPolicyTypeId",_test.getPolicyType().getId().equals(2));
        //assertTrue("testClientPolicies>> merged insurerId", _test.getInsurer().getId().equals(0));
        assertTrue("testClientPolicies>> merged number", _test.getNumber().compareTo("11110023")==0);
        assertTrue("testClientPolicies>> merged serial", _test.getSerial().compareTo("TEST-2")==0);
        assertTrue("testClientPolicies>> merged name", _test.getName().compareTo("INSURE-MODiFY")==0);
        assertTrue("testClientPolicies>> merged note", _test.getNote().compareTo("T")==0);
    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientContacts() throws Exception{

        Date now  = new Date();
        Patient pat  = dbManager.persistOrMerge(
                dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "VF12R", now, 0, "bloodNotes", "notes", null));

        toDrop.add(dbPatient.getPatientById(pat.getId().intValue()));

        //insert test
        ClientContact test = dbManager.persistOrMerge(
                             dbClientContactBean.insertOrUpdateClientContact(-1,
                                     3,
                                     "+7(925)111-11-11",
                                     "test",
                                     pat,
                                     null));

        ClientContact _test = dbClientContactBean.getClientContactById(test.getId().intValue());

        toDrop.add(_test);

        assertTrue("testClientContacts>> persisted id", test.getId().equals(_test.getId()));
        assertTrue("testClientContacts>> persisted rbContactTypeId",test.getContactType().getId().equals(_test.getContactType().getId()));
        assertTrue("testClientContacts>> persisted contact",test.getContact().compareTo(_test.getContact())==0);
        assertTrue("testClientContacts>> persisted notes", test.getNotes().compareTo(_test.getNotes())==0);
        assertTrue("testClientContacts>> persisted patient",test.getPatient().equals(_test.getPatient()));

        //update test
        ClientContact test1 = dbManager.persistOrMerge(
                dbClientContactBean.insertOrUpdateClientContact(
                        test.getId().intValue(), 2, "+7(812)222-22-22", "test1", pat, null));

        _test = dbClientContactBean.getClientContactById(test1.getId().intValue());

        assertTrue("testClientContacts>> merged id", _test.getId().equals(test.getId()));
        assertTrue("testClientContacts>> merged rbContactTypeId", _test.getContactType().getId().equals(2));
        assertTrue("testClientContacts>> merged contact", _test.getContact().compareTo("+7(812)222-22-22")==0);
        assertTrue("testClientContacts>> merged notes", _test.getNotes().compareTo("test1")==0);
        assertTrue("testClientContacts>> merged patient", _test.getPatient().equals(test.getPatient()));
    }

    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientDrugIntolerances() throws Exception {

        Date now  = new Date();
        Patient pat  = dbManager.persistOrMerge(
                dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "VF12R", now, 0, "bloodNotes", "notes", null));

        toDrop.add(dbPatient.getPatientById(pat.getId().intValue()));

        //insert test
        ClientIntoleranceMedicament test =  dbManager.persistOrMerge(
                dbClientIntoleranceMedicament.insertOrUpdateClientIntoleranceMedicament(-1,
                        0,
                        "6,8-о-Бензоло-5,6,7-тригидро-8-оксихинолин",
                        now,
                        "test",
                        pat,
                        null));

        ClientIntoleranceMedicament _test = dbClientIntoleranceMedicament.getClientIntoleranceMedicamentById(test.getId().intValue());

        toDrop.add(_test);

        assertTrue("testClientDrugIntolerances>> persisted id", test.getId().equals(_test.getId()));
        assertTrue("testClientDrugIntolerances>> persisted power", test.getPower()==_test.getPower());
        assertTrue("testClientDrugIntolerances>> persisted nameMedicament", test.getNameMedicament().compareTo(_test.getNameMedicament())==0);
        assertTrue("testClientDrugIntolerances>> persisted notes", test.getNotes().compareTo(_test.getNotes())==0);
        assertTrue("testClientDrugIntolerances>> persisted patient", test.getPatient().equals(_test.getPatient()));

        //update test
        ClientIntoleranceMedicament test1 =  dbManager.persistOrMerge(
                dbClientIntoleranceMedicament.insertOrUpdateClientIntoleranceMedicament(test.getId().intValue(),
                        3, "7-бром-5-(ортохлорфенил)-1,2-дигидро-3Н-1,4-бензодиазепин-2-ОН", now, "test1", pat, null));

        _test = dbClientIntoleranceMedicament.getClientIntoleranceMedicamentById(test1.getId().intValue());

        assertTrue("testClientDrugIntolerances>> merged id", _test.getId().equals(test.getId()));
        assertTrue("testClientDrugIntolerances>> merged power", _test.getPower()==3);
        assertTrue("testClientDrugIntolerances>> merged nameMedicament", _test.getNameMedicament()
                                      .compareTo("7-бром-5-(ортохлорфенил)-1,2-дигидро-3Н-1,4-бензодиазепин-2-ОН")==0);
        assertTrue("testClientDrugIntolerances>> merged notes", _test.getNotes().compareTo("test1")==0);
        assertTrue("testClientDrugIntolerances>> merged patient", _test.getPatient().equals(test.getPatient()));
    }
*/
/*    @Test
    @OperateOnDeployment("glassfish")
    @OverProtocol("EJB")
    public void testClientRelatives() throws Exception{
        //часть теста закоменчена неясно что с релэйшнами

        Date now  = new Date();
        Patient pat  = dbManager.persistOrMerge(
                dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                        now, "1", "12", "11", "111", now, 0, "bloodNotes", "notes", null));

        toDrop.add(dbPatient.getPatientById(pat.getId().intValue()));

        //client contact containers
        List<ClientContactContainer> contacts = new LinkedList<ClientContactContainer>();

        ClientContact contact0 = dbManager.persistOrMerge(
                dbClientContactBean.insertOrUpdateClientContact(-1, 3, "+7(925)111-11-11", "contact 0", pat, null));
        ClientContact contact1 = dbManager.persistOrMerge(
                dbClientContactBean.insertOrUpdateClientContact(-1, 2, "+7(812)222-22-22", "contact 1", pat, null));


        ClientContactContainer clContactContainer = new  ClientContactContainer(contact0);
        contacts.add(clContactContainer);
        clContactContainer = new  ClientContactContainer(contact1);
        contacts.add(clContactContainer);

        //insert test
        //TODO: insertOrUpdateClientRelation метод недоопределен!!!
        ClientRelation test =  dbManager.persistOrMerge(
        dbClientRelationBeanLocal.insertOrUpdateClientRelation( -1,
                                                                 1, //мать-сын
                                                                 "Василиса",
                                                                 "Петухова",
                                                                 "Матвеевна",
                                                                 contacts,
                                                                 pat,
                                                                 null));


    }   */

/*  @Test
  @OperateOnDeployment("glassfish")
  @OverProtocol("EJB")
    public void testClientSocStatuses() throws Exception{    //SocStatuses, Disabilities, Occupations

        Date now  = new Date();
        Patient pat  = dbManager.persistOrMerge(
                dbPatient.insertOrUpdatePatient(0, "TEST", "TEST", "TEST",
                now, "1", "12", "11", "111", "VF12R", now, 0, "bloodNotes", "notes", null, 0));

        toDrop.add(dbPatient.getPatientById(pat.getId().intValue()));

        //idcards
        ClientDocument card =  dbManager.persistOrMerge(
                clientDocument.insertOrUpdateClientDocument(-1, 3, "TEST", "TEST", "TEST", now, now, pat, null));

        //insert test
        ClientSocStatus test = dbManager.persistOrMerge(
                               dbClientSocStatusBean.insertOrUpdateClientSocStatus(-1,
                                       32,  //инвалидность      //!!! Сейчас не используется
                                       9, //дети-инвалиды
                                       card.getId().intValue(),
                                       now,
                                       now,
                                       pat,
                                       0,
                                       "",
                                       null));

        ClientSocStatus _test = dbClientSocStatusBean.getClientSocStatusById(test.getId().intValue());

        toDrop.add(_test);

        assertTrue("testClientSocStatuses>> BAH9l - ne4eJlagka!", "Ваня".equals("Тимофеич"));
        assertTrue("testClientSocStatuses>> persisted id", test.getId().equals(_test.getId()));
        assertTrue("testClientSocStatuses>> persisted socStatusTypeId", test.getSocStatusType().getId()
                                                                            .equals(_test.getSocStatusType().getId()));
        assertTrue("testClientSocStatuses>> persisted documentId", test.getDocument().equals(card));
        assertTrue("testClientSocStatuses>> persisted patient", test.getPatient().equals(_test.getPatient()));

        //update test
        ClientSocStatus test1 = dbManager.persistOrMerge(
                dbClientSocStatusBean.insertOrUpdateClientSocStatus(test.getId().intValue(),
                        32, 9, card.getId().intValue(), now, now, pat, 0, "", null));

        _test = dbClientSocStatusBean.getClientSocStatusById(test1.getId().intValue());

        assertTrue("testClientSocStatuses>> merged id", _test.getId().equals(test.getId()));
        assertTrue("testClientSocStatuses>> merged socStatusTypeId", _test.getSocStatusType().getId().equals(9));
        assertTrue("testClientSocStatuses>> merged documentId", _test.getDocument().equals(card));
        assertTrue("testClientSocStatuses>> merged patient", _test.getPatient().equals(_test.getPatient()));
    } */

//}
