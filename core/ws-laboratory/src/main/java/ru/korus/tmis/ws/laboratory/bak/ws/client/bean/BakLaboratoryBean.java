package ru.korus.tmis.ws.laboratory.bak.ws.client.bean;

import ru.cgm.service.*;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal;
import ru.korus.tmis.laboratory.data.request.BiomaterialInfo;
import ru.korus.tmis.laboratory.data.request.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.data.request.IndicatorMetodic;
import ru.korus.tmis.laboratory.data.request.OrderInfo;
import ru.korus.tmis.ws.laboratory.bak.ws.client.SendBakRequest;
import ru.korus.tmis.ws.laboratory.bak.ws.client.SendBakRequestWS;
import ru.korus.tmis.ws.laboratory.bak.ws.client.xml.SOAPEnvelopeHandlerResolver;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.util.Date;
import java.util.GregorianCalendar;

import static ru.korus.tmis.ws.laboratory.bak.ws.client.bean.BakLaboratoryBean.EntryFactory.*;


/**
 * Сервис для отправки заявки на анализы к сервису CGM
 * <p/>
 * test @see ru.korus.tmis.ws.laboratory.bak.ws.client.bean.BulkLaboratoryBeanImplTest
 *
 * @author anosov@outlook.com
 */
//@Interceptors(LoggingInterceptor.class)
@Stateless
public class BakLaboratoryBean implements IBakLaboratoryBean {

    //    private static final Logger log = LoggerFactory.getLogger(BakLaboratoryBean.class);
    public static final String ROOT = "2.16.840.1.113883.1.3";
    private static final String GUID = String.valueOf(System.currentTimeMillis() / 1000);

    private final static ObjectFactory FACTORY_BAK = new ObjectFactory();
    static final String ORDER_CUSTODIAN = "ФГБУ &quot;ФНКЦ ДГОИ им. Дмитрия Рогачева&quot; Минздрава России";


    @EJB
    private DbStaffBeanLocal staffBean;

    @EJB
    private LaboratoryBeanLocal laboratoryBean;

    @EJB
    private DbActionBeanLocal dbActionBean;

    /**
     * Метод для отсылки запроса на анализ в лабораторию
     *
     * @param actionId - ИД из бд для которого требуется выполнить сбор данных
     * @throws CoreException - ошибка при отправке
     */
    @Override
    public void sendLisAnalysisRequest(int actionId) throws CoreException {
        final SendBakRequestWS service = createCGMService();
//        log.info("Создаем запрос на анализ по actionId = "+ actionId);
        final HL7Document hl7Document = createDocument(actionId);
//        log.info("Запрос на анализ создан"+ hl7Document.toString());
        try {
//            log.info("Посылается запрос на анализ");
            service.queryAnalysis(hl7Document, new Holder<Integer>(1), new Holder<String>(GUID));
//            log.info("Запрос выполнился успешно");
        } catch (Exception e) {
//            log.error("Ошибка при отправке запроса:" + e.getMessage());
            throw new CoreException(e.getMessage());
        }
    }


    /**
     * Создание CGM-сервиса для запросов в ЛИС
     *
     * @return SendBakRequestWS - сервис для выполнения запросов
     * @see SendBakRequestWS
     */
    private SendBakRequestWS createCGMService() {
        final SendBakRequest SendBakRequest = new SendBakRequest();
        SendBakRequest.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
        return SendBakRequest.getService();
    }

    private HL7Document createDocument(int actionId) throws CoreException {
        final HL7Document document = createAndInitializeDocument(actionId);
        return document;
    }

    private HL7Document createAndInitializeDocument(final int actionId) throws CoreException {
        final HL7Document document = new HL7Document();

        final Action action = dbActionBean.getActionById(actionId);
        final ActionType actionType = getActionType(action);
        final Event eventInfo = action.getEvent();
        final Patient patientInfo = eventInfo.getPatient();
        final DiagnosticRequestInfo requestInfo = laboratoryBean.getDiagnosticRequestInfo(action); // Request section
        final BiomaterialInfo biomaterialInfo = laboratoryBean.getBiomaterialInfo(action, action.getTakenTissue()); // Biomaterial section
        final OrderInfo orderInfo = laboratoryBean.getOrderInfo(action, actionType); // Order section

        createTypeId(document);
        createMedDocId(document, requestInfo); // идентификатор мед документа
        document.setCode(null); // код для документа type - пустой в нашем случае
        document.setTitle("Заказ лабораторных исследований");
        createEffectiveTimeRequest(document, requestInfo); // дата и время создания направления врачом [orderMisDate]
        createConfLevel(document, "N"); // уровень конфиденциальности документа : Normal
        createLanguageDoc(document, "ru-RU"); // язык для этого документа
        createOrderStatus(document, orderInfo); // cтатус заказа [orderStatus]
        createRecordTarget(document, patientInfo); // демографические данные пациента
        createDocAuthor(document, eventInfo, requestInfo, staffBean); // создатель документа. Обязательный
        createComponentOf(document, patientInfo);
        createBody(document, biomaterialInfo, orderInfo, patientInfo, requestInfo, action);

        return document;
    }


    private static void createBody(HL7Document document, BiomaterialInfo biomaterialInfo, OrderInfo orderInfo, Patient patient, DiagnosticRequestInfo requestInfo, Action action) {
        final ComponentInfo component = new ComponentInfo();
        final StructuredBodyInfo structuredBody = new StructuredBodyInfo();
        final SubComponentInfo subComponentInfo = FACTORY_BAK.createSubComponentInfo();
        subComponentInfo.getEntry().add(createEntryBiomaterial(biomaterialInfo));

        final JAXBElement<SubComponentInfo> jaxbElement
                = new JAXBElement<SubComponentInfo>(QName.valueOf("http://cgm.ru"), SubComponentInfo.class, subComponentInfo);
        structuredBody.getContent().add(jaxbElement);

        final SubComponentInfo subComponentInfo2 = FACTORY_BAK.createSubComponentInfo();
        final SectionInfo section = new SectionInfo();
//        section.setText("");

        section.getEntry().add(createEntry("OBS", "RQO", requestInfo.orderDepartmentMisCode().get(), requestInfo.orderDepartmentName().get()));
        section.getEntry().add(createEntry("OBS", "RQO", action.getIsUrgent() + "", ""));
        section.getEntry().add(createEntry("OBS", "RQO", requestInfo.orderDiagCode().get(), requestInfo.orderDiagText().get()));
        section.getEntry().add(createEntry("OBS", "RQO", orderInfo.diagnosticCode().get(), orderInfo.diagnosticName().get()));

        for (IndicatorMetodic indicatorMetodic : orderInfo.indicators()) {
            section.getEntry().add(createEntry("OBS", "RQO", indicatorMetodic.indicatorCode().get(), indicatorMetodic.indicatorName().get()));
        }

        if (patient.getSex() == 2) { // появляется если пациент женщина и беременная
            section.getEntry().add(createEntryPregnat("", requestInfo));
        }

        subComponentInfo2.setSection(section);

        component.setStructuredBody(structuredBody);
        document.setComponent(component);
    }


    private static void createComponentOf(HL7Document document, Patient patient) {
        final ComponentOfInfo componentOf = new ComponentOfInfo();
        final EncompassingEncounterInfo encompassingEncounter = new EncompassingEncounterInfo();
        final EeIdInfo eeIdInfo = new EeIdInfo();
        eeIdInfo.setRoot(ORDER_CUSTODIAN);
        eeIdInfo.setExtension("patientNumber");
        encompassingEncounter.setId(eeIdInfo);

        final EeCodeInfo code = new EeCodeInfo();
        code.setCodeSystem("2.16.840.1.113883.5.4");
        code.setCodeSystemName("actCode");
        code.setCode("IMP");
        code.setDisplayName("Inpatient encounter");
        encompassingEncounter.setCode(code);
        final EffectiveTimeInfo effectiveTime = new EffectiveTimeInfo();
        effectiveTime.setNullFlavor("NI");
        encompassingEncounter.setEffectiveTime(effectiveTime);
        componentOf.setEncompassingEncounter(encompassingEncounter);
        document.setComponentOf(componentOf);
    }

    private static void createDocAuthor(HL7Document document, Event eventInfo, DiagnosticRequestInfo requestInfo, DbStaffBeanLocal dbStaffBean) throws CoreException {
        final AuthorInfo author = new AuthorInfo();
        author.setTypeCode("AUT");
        final Date execDate = eventInfo.getExecDate();
        if (execDate != null) {
            final DateTimeInfo time = new DateTimeInfo();
            XMLGregorianCalendar xmlTime = null;
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(execDate);
            try {
                xmlTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            } catch (DatatypeConfigurationException e) {
//            log.error(e.getMessage());
            }
            time.setValue(xmlTime);
            author.setTime(time);
        }

        final AssignedAuthorInfo assignedAuthor = new AssignedAuthorInfo();
        assignedAuthor.setClassCode("ASSIGNED");

        final DoctorIdInfo doctorId = new DoctorIdInfo();
        final Integer docId = (Integer) requestInfo.orderDoctorMisId().get();
        final Staff doctor = dbStaffBean.getStaffById(docId);
        doctorId.setExtension(doctor.getId().toString());
        doctorId.setRoot(doctor.getUuid().getUuid());
        assignedAuthor.setId(doctorId);

        final AssignedPersonInfo assignedPerson = new AssignedPersonInfo();
        assignedPerson.setClassCode("PSN");
        assignedPerson.setDeterminerCode("INSTANCE");

        final NameInfo assignedName = new NameInfo();
        assignedName.setName(doctor.getFirstName());
        assignedName.setFamily(doctor.getLastName());
        assignedName.setSuffix(doctor.getPatrName());
        assignedPerson.setName(assignedName);

        assignedAuthor.setAssignedPerson(assignedPerson);

        final RepresentedOrganizationInfo representedOrganization = new RepresentedOrganizationInfo();
        final ReporgIDInfo reporgIDInfo = new ReporgIDInfo();
        reporgIDInfo.setRoot(doctor.getOrgStructure().getUuid().getUuid());
        representedOrganization.setId(reporgIDInfo);
        representedOrganization.setName(ORDER_CUSTODIAN);
        assignedAuthor.setRepresentedOrganization(representedOrganization);

        author.setAssignedAuthor(assignedAuthor);
        document.setAuthor(author);
    }

    private static void createRecordTarget(HL7Document document, Patient patientInfo) {
        final RecordTargetInfo recordTarget = new RecordTargetInfo();
        recordTarget.setTypeCode("RCT");

        final PatientRoleInfo patientRole = new PatientRoleInfo();
        patientRole.setClassCode("PAT");
        patientRole.setAddr(patientInfo.getClientAddresses().get(0).getFreeInput());

        final PatientIDInfo patientId = new PatientIDInfo();
        patientId.setExtension(String.valueOf(patientInfo.getId()));
        patientId.setRoot(GUID);
        patientRole.setId(patientId);

        final ru.cgm.service.PatientInfo patient = new ru.cgm.service.PatientInfo();
        patient.setClassCode("PSN");
        patient.setDeterminerCode("INSTANCE");

        final NameInfo name = new NameInfo();
        name.setName(patientInfo.getFirstName());
        name.setSuffix(patientInfo.getPatrName());
        name.setFamily(patientInfo.getLastName());
        patient.setName(name);

        final AdministrativeGenderCodeInfo administrativeGenderCode = new AdministrativeGenderCodeInfo();
        administrativeGenderCode.setCode(patientInfo.getSex() + "");
        patient.setAdministrativeGenderCode(administrativeGenderCode);

        final BirthTimeInfo birthTime = new BirthTimeInfo();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(patientInfo.getBirthDate());
        XMLGregorianCalendar xmlBirthTime = null;
        try {
            xmlBirthTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
//            log.error(e.getMessage());
        }
        birthTime.setValue(xmlBirthTime);
        patient.setBirthTime(birthTime);

        patientRole.setPatient(patient);

        final ProviderOrganizationInfo providerOrganization = new ProviderOrganizationInfo();
        providerOrganization.setClassCode("ORG");
        providerOrganization.setDeterminerCode("INSTANCE");
        final LpuIDInfo orgId = new LpuIDInfo();
        orgId.setRoot(patientInfo.getUuid().getUuid()); // todo провериь толи значение я сейчас беру!
        providerOrganization.setId(orgId);
        providerOrganization.setName(ORDER_CUSTODIAN);
        patientRole.setProviderOrganization(providerOrganization);

        recordTarget.setPatientRole(patientRole);

        document.setRecordTarget(recordTarget);
    }

    private static void createOrderStatus(HL7Document document, OrderInfo orderInfo) {
        final HL7Document.VersionNumber versionNumber = new HL7Document.VersionNumber();
        versionNumber.setValue("N");
        document.setVersionNumber(versionNumber);
    }

    private static void createLanguageDoc(HL7Document document, String langCode) {
        final HL7Document.LanguageCode languageCode = new HL7Document.LanguageCode();
        languageCode.setCode(langCode);
        document.setLanguageCode(languageCode);
    }

    private static void createConfLevel(HL7Document document, String levelCode) {
        final HL7Document.ConfidentialityCode confidentialityCode = new HL7Document.ConfidentialityCode();
        confidentialityCode.setCode(levelCode);
        confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");
        document.setConfidentialityCode(confidentialityCode);
    }

    private static void createEffectiveTimeRequest(HL7Document document, DiagnosticRequestInfo requestInfo) {
        final HL7Document.EffectiveTime effectiveTime = new HL7Document.EffectiveTime();
        final Date orderMisDate = requestInfo.orderMisDate().get();
        effectiveTime.setValue(orderMisDate.getTime() + "");
        document.setEffectiveTime(effectiveTime);
    }

    private static void createMedDocId(HL7Document document, DiagnosticRequestInfo requestInfo) {
        final HL7Document.Id id = new HL7Document.Id();
        id.setRoot(ROOT);
        id.setExtention(String.valueOf(requestInfo.orderMisId()));
        document.setId(id);
    }

    private static void createTypeId(HL7Document document) {
        final HL7Document.TypeId typeId = new HL7Document.TypeId();
        typeId.setExtention("POCD_HD000040");
        typeId.setRoot(ROOT);
        document.setTypeId(typeId);
    }

    private ActionType getActionType(Action action) throws CoreException {
        final ActionType actionType = action.getActionType();

        if (actionType.getId() == -1) {
            throw new CoreException("Error no Type For Action" + action.getId());
        }
        return actionType;
    }


    static class EntryFactory {
        static EntryInfo createEntry(String classCode, String moodCode, String code, String displayName) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode(classCode);
            observation.setMoodCode(moodCode);
            observation.setNegationInd("false");
            final ObsIdInfo id = new ObsIdInfo();
            id.setRoot(GUID);
            id.setExtension("1");
            observation.setId(id);
            final ObsCodeInfo codeInfo = new ObsCodeInfo();
            codeInfo.setCode(code);
            codeInfo.setDisplayName(displayName);
            observation.setCode(codeInfo);
            entry.setObservation(observation);
            return entry;
        }

        static EntryInfo createEntryPregnat(String code, DiagnosticRequestInfo requestInfo) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode("OBS");
            observation.setMoodCode("EVN");
            final ObsCodeInfo codeInfo = new ObsCodeInfo();
            codeInfo.setCode(code);
            final ObsTranslationInfo translation = new ObsTranslationInfo();
            translation.setDisplayName("средний срок беременности, в неделях");
            codeInfo.setTranslation(translation);
            observation.setCode(codeInfo);
            final ObsValueInfo value = new ObsValueInfo();
            value.setUnit("нед");
            value.setValue(requestInfo.orderPregnatMin().get() + " ~ " + requestInfo.orderPregnatMax().get());
            observation.setValue(value);
            entry.setObservation(observation);
            return entry;
        }

        static EntryInfo createEntryBiomaterial(BiomaterialInfo biomaterialInfo) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode("OBS");
            observation.setMoodCode("ENT");
            final ObsEffectiveTimeInfo effectiveTime = new ObsEffectiveTimeInfo();
            XMLGregorianCalendar xmlTime2 = null;
            GregorianCalendar c2 = new GregorianCalendar();
            c2.setTime(biomaterialInfo.orderProbeDate().get());
            try {
                xmlTime2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            } catch (DatatypeConfigurationException e) {
//                log.error(e.getMessage());
            }
            effectiveTime.setValue(xmlTime2);
            observation.setEffectiveTime(effectiveTime);
            entry.setObservation(observation);

            final SpecimenInfo specimen = new SpecimenInfo();
            final SpecimenRoleInfo specimenRole = new SpecimenRoleInfo();
            final SrIdInfo srIdInfo = new SrIdInfo();
            srIdInfo.setRoot(biomaterialInfo.orderBarCode().get());
            specimenRole.setId(srIdInfo);

            final SpecimenPlayingEntityInfo specimenPlayingEntity = new SpecimenPlayingEntityInfo();
            final SpCodeInfo spCodeInfo = new SpCodeInfo();
            spCodeInfo.setCode(biomaterialInfo.orderBiomaterialCode().get());
            final SpTranslationInfo spTranslationInfo = new SpTranslationInfo();
            spTranslationInfo.setDisplayName(biomaterialInfo.orderBiomaterialname().get());
            spCodeInfo.setTranslation(spTranslationInfo);
            specimenPlayingEntity.setCode(spCodeInfo);

            final SpQuantityInfo quantityInfo = new SpQuantityInfo();
            quantityInfo.setValue("orderBiomaterialVolume");
            specimenPlayingEntity.setQuantity(quantityInfo);

            final SpTextInfo text = new SpTextInfo();
            text.setValue(biomaterialInfo.orderBiomaterialComment().get());
            specimenPlayingEntity.setText(text);

            final SpUnitInfo spUnitInfo = new SpUnitInfo();
            spCodeInfo.setCode(biomaterialInfo.orderBiomaterialCode().get());
            specimenPlayingEntity.setUnit(spUnitInfo);

            specimenRole.setSpecimenPlayingEntity(specimenPlayingEntity);
            specimen.setSpecimenRole(specimenRole);
            observation.setSpecimen(specimen);

            entry.setObservation(observation);
            return entry;
        }

    }
}
