package ru.korus.test.patient;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import ru.korus.tmis.core.auth.AuthStorageBean;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;

import static org.junit.Assert.assertTrue;
import javax.ejb.EJB;
import java.io.File;
import java.util.Date;
import java.util.Set;


@RunWith(Arquillian.class)
public class PatientTestCase {

    @EJB
    private DbPatientBeanLocal dbPatient;

    @EJB
    private DbManagerBeanLocal dbManager;

    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private AuthStorageBeanLocal authStorage;

    //Создание архива для деплоя тестового приложения
    @Deployment(name="PatientTestCase-glassfish")
    public static Archive createTestArchive() {

        //Тестируемые классы и зависимости
        Class<?>[] classes = new Class<?>[] {
                ru.korus.tmis.core.logging.LoggingInterceptor.class,
                InternalLoggerBeanLocal.class,
                InternalLoggerBean.class,

                DbPatientBeanLocal.class, DbPatientBean.class,
                DbManagerBeanLocal.class, DbManagerBean.class,

                //AuthStorageBeanLocal.class, AuthStorageBean.class,
                //DbRbSocTypeBeanLocal.class, DbRbSocTypeBean.class,
               // DbRbDocumentTypeBeanLocal.class, DbRbDocumentTypeBean.class,
                //DbClientAllergyBean.class, DbClientAllergyBeanLocal.class,
                //DbClientDocumentBean.class, DbClientDocumentBeanLocal.class,
                //DbClientPolicyBeanLocal.class, DbClientPolicyBean.class,
                //DbRbPolicyTypeBeanLocal.class, DbRbPolicyTypeBean.class,
                //DbOrganizationBean.class, DbOrganizationBeanLocal.class,
                //DbClientContactBeanLocal.class, DbClientContactBean.class,
                //DbRbContactTypeBeanLocal.class, DbRbContactTypeBean.class,
                //DbClientIntoleranceMedicamentBeanLocal.class, DbClientIntoleranceMedicamentBean.class,
                //DbClientRelationBeanLocal.class, DbClientRelationBean.class,
                //DbRbRelationTypeBeanLocal.class, DbRbRelationTypeBean.class,
                //DbClientSocStatusBeanLocal.class, DbClientSocStatusBean.class,

                DbRbBloodTypeBeanLocal.class, DbRbBloodTypeBean.class,
                DbFDRecordBeanLocal.class, DbFDRecordBean.class,
                DbCustomQueryLocal.class, DbCustomQueryBean.class,
                DbEventBeanLocal.class, DbEventBean.class,
                DbActionTypeBeanLocal.class, DbActionTypeBean.class,
                DbRbSocStatusClassBeanLocal.class, DbRbSocStatusClassBean.class,
                DbRbCounterBeanLocal.class, DbRbCounterBean.class,

                ru.korus.tmis.core.entity.model.Patient.class,                  ru.korus.tmis.core.entity.model.RbBloodType.class,
                ru.korus.tmis.core.entity.model.fd.ClientFlatDirectory.class,   ru.korus.tmis.core.entity.model.fd.ClientFDProperty.class,
                ru.korus.tmis.core.entity.model.fd.FDRecord.class,              ru.korus.tmis.core.entity.model.fd.FlatDirectory.class,
                ru.korus.tmis.core.entity.model.ClientAddress.class,            ru.korus.tmis.core.entity.model.Address.class,
                ru.korus.tmis.core.entity.model.AddressHouse.class,             ru.korus.tmis.core.entity.model.ClientDocument.class,
                ru.korus.tmis.core.entity.model.RbDocumentType.class,           ru.korus.tmis.core.entity.model.RbDocumentTypeGroup.class,
                ru.korus.tmis.core.entity.model.ClientRelation.class,           ru.korus.tmis.core.entity.model.RbRelationType.class,
                ru.korus.tmis.core.entity.model.ClientAllergy.class,            ru.korus.tmis.core.entity.model.ClientIntoleranceMedicament.class,
                ru.korus.tmis.core.entity.model.Event.class,                    ru.korus.tmis.core.entity.model.EventType.class,
                ru.korus.tmis.core.entity.model.TempInvalid.class,              ru.korus.tmis.core.entity.model.RbTempInvalidDocument.class,
                ru.korus.tmis.core.entity.model.RbTempInvalidReason.class,      ru.korus.tmis.core.entity.model.fd.FDFieldValue.class,
                ClientSocStatus.class,          ru.korus.tmis.core.entity.model.RbSocStatusType.class,
                ru.korus.tmis.core.entity.model.RbSocStatusClass.class,         ru.korus.tmis.core.entity.model.RbSocStatusClassTypeAssoc.class,
                ru.korus.tmis.core.entity.model.ClientPolicy.class,             ru.korus.tmis.core.entity.model.ClientWork.class,
                ru.korus.tmis.core.entity.model.ClientContact.class,            ru.korus.tmis.core.entity.model.RbContactType.class,
                ru.korus.tmis.core.entity.model.RbPolicyType.class,             ru.korus.tmis.core.entity.model.Diagnostic.class,
                ru.korus.tmis.core.entity.model.Diagnosis.class,                ru.korus.tmis.core.entity.model.Mkb.class,
                ru.korus.tmis.core.entity.model.RbTraumaType.class,             ru.korus.tmis.core.entity.model.RbResult.class,
                ru.korus.tmis.core.entity.model.RbDiagnosisType.class,          ru.korus.tmis.core.entity.model.RbDispanser.class,
                ru.korus.tmis.core.entity.model.RbEventTypePurpose.class,       ru.korus.tmis.core.entity.model.RbHealthGroup.class,
                ru.korus.tmis.core.entity.model.fd.FDField.class,               ru.korus.tmis.core.entity.model.fd.FDFieldType.class,



                ru.korus.tmis.core.entity.model.Staff.class,                    ru.korus.tmis.core.entity.model.Role.class,
                ru.korus.tmis.core.entity.model.Speciality.class,               ru.korus.tmis.core.entity.model.OrgStructure.class,
                ru.korus.tmis.core.entity.model.RbPost.class,                   ru.korus.tmis.core.entity.model.UserRight.class,
                ru.korus.tmis.core.entity.model.Organisation.class,
                //AuthStorageBeanLocal.class, AuthStorageBean.class,
                //AuthData.class,

                DbStaffBeanLocal.class, DbStaffBean.class, DbCustomQueryBean.class, DbUUIDBeanLocal.class, DbPatientBean.class,
                DbEventBean.class, DbActionPropertyBean.class, DbUUIDBean.class, DbOrgStructureBeanLocal.class, DbOrgStructureBean.class,
                DbActionPropertyTypeBeanLocal.class, DbActionPropertyTypeBean.class, DbActionBeanLocal.class, DbActionBean.class,
                DbEventPersonBeanLocal.class, DbEventPersonBean.class,  DbContractBeanLocal.class, DbContractBean.class,
                AuthStorageBeanLocal.class, AuthStorageBean.class, RbFinance.class, UUID.class, Diagnostic.class, RbDiseaseStage.class

        };


        Set<Class<? extends Object>> allClasses = (new Reflections("ru.korus.tmis.core.database")).getSubTypesOf(Object.class);
        allClasses.addAll((new Reflections("ru.korus.tmis.core.auth")).getSubTypesOf(Object.class));

        //EnterpriseArchive - ear, WebArchive - war, JavaArchive - jar (используется апи ShrinkWrap)
        WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
        wa.addAsWebInfResource(new File("./src/test/resources/META-INF/persistence.xml"),"classes/META-INF/persistence.xml");
        wa.addClasses(classes);
        wa.addPackage(UUID.class.getPackage());
        for(Class<?> cl : allClasses) {
            wa.addClass(cl);
        }
        wa.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));

        System.out.println(wa.toString());
        assertTrue("testPatients>> true", true);
        return wa;
    }

    //Теcтирование сервисов Patient
    @Test
    @OperateOnDeployment("PatientTestCase-glassfish")
    @OverProtocol("EJB")
    public void testPatients() throws Exception{    //

        assertTrue("testPatients>> true", true);

        Date now  = new Date();
        Staff testUser = dbStaff.getStaffById(579);
        assertTrue("testPatients>> true", true);
        assertTrue("testPatients>> false",false);
        Patient pat  = dbPatient.insertOrUpdatePatient(-1,
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
                testUser,   //authData.getUser(),//
                0);
        assertTrue("testPatients>> true", true);
        assertTrue("testPatients>> false",false);

        dbManager.persist(pat);

        assertTrue("testPatients>> true", true);
        assertTrue("testPatients>> false",false);
    }
}
