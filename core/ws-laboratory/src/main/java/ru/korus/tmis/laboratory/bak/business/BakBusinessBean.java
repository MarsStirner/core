package ru.korus.tmis.laboratory.bak.business;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.bak.BakDiagnosis;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.bak.BakSendService;
import ru.korus.tmis.laboratory.bak.model.BiomaterialInfo;
import ru.korus.tmis.laboratory.bak.model.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.bak.model.IndicatorMetodic;
import ru.korus.tmis.laboratory.bak.model.OrderInfo;
import ru.korus.tmis.laboratory.bak.service.*;
import ru.korus.tmis.laboratory.bak.ws.client.BakSend;
import ru.korus.tmis.laboratory.bak.ws.client.handlers.SOAPEnvelopeHandlerResolver;
import ru.korus.tmis.laboratory.bak.ws.server.Utils;
import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.util.logs.ToLog;

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.util.*;

import static ru.korus.tmis.laboratory.bak.business.BakBusinessBean.EntryFactory.*;


/**
 * Сервис для отправки заявки на анализы к сервису CGM
 * <p/>
 * test @see ru.korus.tmis.laboratory.bak.ws.client.bean.BulkLaboratoryBeanImplTest
 *
 * @author anosov@outlook.com
 */
//@Interceptors(LoggingInterceptor.class)
@Stateless
public class BakBusinessBean implements BakBusinessBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(BakBusinessBean.class);

    private static final String ROOT = "2.16.840.1.113883.1.3";

    private static final String GUID = String.valueOf(System.currentTimeMillis() / 1000);

    private final static ObjectFactory FACTORY_BAK = new ObjectFactory();

    private static final String CUSTODIAN_NAME = "ФГБУ &quot;ФНКЦ ДГОИ им. Дмитрия Рогачева&quot; Минздрава России";

    public static final String DATE_FORMAT = "YYYY-MM-dd HH:mm";

    @EJB
    private DbStaffBeanLocal staffBean;

    @EJB
    private DbActionBeanLocal dbActionBean;

    @EJB
    private DbCustomQueryLocal dbCustomQuery;

    @EJB
    private DbActionTypeBeanLocal dbActionTypeBean;

    @EJB
    private DbActionPropertyTypeBeanLocal dbActionPropertyType;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

    /**
     * Метод для отсылки запроса на анализ в лабораторию
     *
     * @param actionId - ИД из бд для которого требуется выполнить сбор данных
     * @throws CoreException - ошибка при отправке
     */
    @Override
    public void sendLisAnalysisRequest(final int actionId) throws CoreException {
        ToLog toLog = new ToLog("Analysis Request");
        toLog.addN(ConfigManager.LaboratoryBak().ServiceUrl().toString());
        try {
            final BakSendService service = createCGMService();
            final HL7Document hl7Document = createDocument(actionId, toLog);
            toLog.addN("Query: #", Utils.marshallMessage(hl7Document, "ru.korus.tmis.laboratory.bak.service"));
            toLog.addN("Sending...");
            final Holder<Integer> id = new Holder<Integer>(1);
            final Holder<String> guid = new Holder<String>(GUID);
            service.queryAnalysis(hl7Document, id, guid);
            toLog.addN("Result id[#], guid [#]", id.value, guid.value);

        } catch (Exception e) {
            logger.error("Sending error:" + e.getMessage(), e);
            toLog.addN("Sending error: #", e.getMessage());
            throw new CoreException("Ошибка отправки направления в БАК: " + e.getMessage());
        } finally {
            logger.info(toLog.releaseString());
        }
    }


    /**
     * Создание CGM-сервиса для запросов в ЛИС
     *
     * @return BakSend - сервис для выполнения запросов
     * @see BakSendService
     */
    private BakSendService createCGMService() {
        final BakSend BakSend = new BakSend();
        BakSend.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
        return BakSend.getService();
    }

    private HL7Document createDocument(final int actionId, final ToLog toLog) throws CoreException {
        final HL7Document document = new HL7Document();

        final Action action = dbActionBean.getActionById(actionId);
        final ActionType actionType = getActionType(action);
        final Event eventInfo = action.getEvent();
        final Patient patientInfo = eventInfo.getPatient();
        final DiagnosticRequestInfo requestInfo = getDiagnosticRequestInfo(action, toLog); // Request section
        // final Diagnosis diagnosis = new Diagnosis(requestInfo.getOrderDiagCode(), requestInfo.getOrderDiagText());
        final BiomaterialInfo biomaterialInfo = getBiomaterialInfo(action, toLog); // Biomaterial section
        final OrderInfo orderInfo = getOrderInfo(action, actionType, toLog); // Order section

        createTypeId(document);
        createMedDocId(document, requestInfo); // идентификатор мед документа
        document.setCode(null); // код для документа type - пустой в нашем случае
        document.setTitle("Заказ лабораторных исследований");
        createEffectiveTimeRequest(document, requestInfo); // дата и время создания направления врачом [orderMisDate]
        createConfLevel(document, "N"); // уровень конфиденциальности документа : Normal
        createLanguageDoc(document, "ru-RU"); // язык для этого документа
        createOrderStatus(document, action); // cтатус заказа [orderStatus]
        createRecordTarget(document, patientInfo, eventInfo); // демографические данные пациента
        createDocAuthor(document, action, requestInfo); // создатель документа. Обязательный
        createComponentOf(document, patientInfo);
        createBody(document, biomaterialInfo, orderInfo, patientInfo, requestInfo, action, eventInfo);

        return document;
    }

    private OrderInfo getOrderInfo(final Action action, final ActionType actionType, final ToLog toLog) throws CoreException {

        final OrderInfo orderInfo = new OrderInfo();
        // Код исследования
        final String code = actionType.getCode();
        toLog.addN("OrderInfo:Code=#", code);
        orderInfo.setDiagnosticCode(code);

        // Наименование исследования
        final String name = actionType.getName();
        toLog.addN("OrderInfo:Name=#", name);
        orderInfo.setDiagnosticName(name);

        // Флаг срочности
        final OrderInfo.Priority priority = action.getIsUrgent() ? OrderInfo.Priority.URGENT : OrderInfo.Priority.NORMAL;
        toLog.addN("OrderInfo:Urgent=#", priority);
        orderInfo.setOrderPriority(priority);

        ActionPropertyType aa = null;
        // Показатели
        final List<ActionPropertyType> apts = dbActionTypeBean.getActionTypePropertiesById(actionType.getId());
        for (ActionPropertyType apt : apts) {
            if (apt.getTest() != null) {
                aa = apt;
            }
        }
        if (aa == null) {
            // Если для данного исследования не определены показатели из rbTest, то не нужно отправлять анализ в ЛИС
            throw new CoreException("не определены показатели из rbTest, не нужно отправлять анализ в ЛИС actionId: " + action.getId());
        }

        Set<ActionPropertyType> aptsSet = new HashSet<ActionPropertyType>();
        for (ActionPropertyType apt : apts) {
            aptsSet.add(apt);
        }

        // Получаем map из APT в AP
        Map<ActionPropertyType, ActionProperty> apsMap = action.getActionPropertiesByTypes(aptsSet);
        // Фильтруем map чтобы найти показатели/методы
        for (Map.Entry<ActionPropertyType, ActionProperty> entry : apsMap.entrySet()) {
            final ActionProperty ap = entry.getValue();
            final ActionPropertyType apt = entry.getKey();
            if (apt.getTest() != null && (apt.getIsAssignable() == false || ap.getIsAssigned() == true)) {
                toLog.addN("ap.id=# apt.name=# code=# name=#", ap.getId(), apt.getName(), apt.getTest().getCode(), apt.getTest().getName());
                orderInfo.addIndicatorList(new IndicatorMetodic(apt.getTest().getName(), apt.getTest().getCode()));
            }
        }
        return orderInfo;
    }

    private BiomaterialInfo getBiomaterialInfo(final Action action, final ToLog toLog) throws CoreException {
        final TakenTissue tt = action.getTakenTissue();
        final RbTissueType type = tt.getType();
        if (type != null) {
            final BiomaterialInfo bi = new BiomaterialInfo(
                    type.getCode(),
                    type.getName(),
                    String.valueOf(tt.getBarcode()),
                    tt.getPeriod(),
                    0,
                    new DateTime(tt.getDatetimeTaken()),
                    tt.getNote());

            toLog.addN("Biomaterial:BarCode=#", bi.getOrderBarCode());
            toLog.addN("Biomaterial:SamplingDate=#", bi.getOrderProbeDate().toString(DATE_FORMAT));
            toLog.addN("Biomaterial:Code=#", bi.getOrderBiomaterialCode());
            toLog.addN("Biomaterial:Name=#", bi.getOrderBiomaterialname());
            toLog.addN("Biomaterial:Full=#", bi);
            return bi;
        }
        toLog.addN("Error RbTissueType not found, actionId " + action.getId());
        throw new CoreException("Не найден RbTissueType для actionId " + action.getId());
    }

    private DiagnosticRequestInfo getDiagnosticRequestInfo(final Action action, final ToLog toLog) throws CoreException {
        final DiagnosticRequestInfo diag = new DiagnosticRequestInfo();

        // Id (long) -- уникальный идентификатор направления в МИС (Action.id)
        final int id = action.getId();
        toLog.addN("Request:Id=#", id);
        diag.setOrderMisId(id);

        // номер истории болезни eventid
        final String orderCaseId = "" + action.getEvent().getExternalId();
        toLog.addN("Request:OrderCaseId=#", orderCaseId);
        diag.setOrderCaseId(orderCaseId);

        // код финансирования
        final int orderFinanceId = dbCustomQuery.getFinanceId(action.getEvent());
        toLog.addN("Request:OrderFinanceId=#", orderFinanceId);
        diag.setOrderFinanceId(orderFinanceId);

        // CreateDate (datetime) -- дата создания направления врачом (Action.createDatetime)
        final DateTime date = new DateTime(action.getCreateDatetime());
        toLog.addN("Request:CreateDate=#", date.toString(DATE_FORMAT));
        diag.setOrderMisDate(date);

        // PregnancyDurationWeeks (int) -- срок беременности пациентки (в неделях)
        int pregMin = (action.getEvent().getPregnancyWeek() * 7);
        int pregMax = (action.getEvent().getPregnancyWeek() * 7);
        toLog.addN("Request:PregnancyDurationWeeks=" + action.getEvent().getPregnancyWeek());
        diag.setOrderPregnatMin(pregMin);
        diag.setOrderPregnatMax(pregMax);

        // Diagnosis
        final BakDiagnosis diagnosisBak = dbCustomQuery.getBakDiagnosis(action);
        toLog.addN("Request:#", diagnosisBak);
        if (diagnosisBak != null) {
            diag.setOrderDiagCode(diagnosisBak.getCode());
            diag.setOrderDiagText(diagnosisBak.getName());
        }

        // Comment (string) (необязательно) – произвольный текстовый комментарий к направлению
        final String comment = action.getNote();
        toLog.addN("Request:Comment=" + comment);
        diag.setOrderComment(comment);

        final OrgStructure department = getOrgStructureByEvent(action.getEvent(), toLog);
        toLog.addN("Request:Department: #", department);
        if (department != null) {
            toLog.addN("Request:Department:Code #,Name #", department.getCode(), department.getName());
            diag.setOrderDepartmentMisCode(department.getCode());// DepartmentCode (string) -- уникальный код подразделения (отделения)
            diag.setOrderDepartmentName(department.getName()); // DepartmentName (string) -- название подразделения - отделение
        }

        final String doctorLastname = action.getAssigner().getLastName();
        final String doctorFirstname = action.getAssigner().getFirstName();
        final String doctorPartname = action.getAssigner().getPatrName();
        final int doctorCode = action.getAssigner().getId();

        toLog.addN("Request.DoctorLastName=" + doctorLastname);
        toLog.addN("Request.DoctorFirstName=" + doctorFirstname);
        toLog.addN("Request.DoctorMiddleName=" + doctorPartname);
        toLog.addN("Request.DoctorCode=" + doctorCode);
        diag.setOrderDoctorFamily(doctorLastname);
        diag.setOrderDoctorName(doctorFirstname);
        diag.setOrderDoctorPatronum(doctorPartname);
        diag.setOrderDoctorMisId(doctorCode);

        return diag;
    }

    @Nullable
    private OrgStructure getOrgStructureByEvent(final Event e, final ToLog toLog) throws CoreException {
        Map<Event, ActionProperty> hospitalBeds = dbCustomQuery.getHospitalBedsByEvents(Collections.singletonList(e));
        if (hospitalBeds == null) {
            toLog.addN("Hospital bed not found for #", e);
            return null;
        }
        for (Event event : hospitalBeds.keySet()) {
            final ActionProperty actionProperty = hospitalBeds.get(event);
            final List<APValue> actionPropertyValue = dbActionProperty.getActionPropertyValue(actionProperty);
            for (APValue apValue : actionPropertyValue) {
                if (apValue.getValue() instanceof OrgStructureHospitalBed) {
                    return ((OrgStructureHospitalBed) apValue.getValue()).getMasterDepartment();
                }
            }
        }
        return null;
    }

    private static void createBody(final HL7Document document,
                                   final BiomaterialInfo biomaterialInfo,
                                   final OrderInfo orderInfo,
                                   final Patient patient,
                                   final DiagnosticRequestInfo requestInfo,
                                   final Action action,
                                   final Event eventInfo) {
        final ComponentInfo component = new ComponentInfo();
        final StructuredBodyInfo structuredBody = new StructuredBodyInfo();
        final SubComponentInfo subComponentInfo = FACTORY_BAK.createSubComponentInfo();
        subComponentInfo.getEntry().add(createEntryBiomaterial(biomaterialInfo, action));

        final JAXBElement<SubComponentInfo> jaxbElement
                = new JAXBElement<SubComponentInfo>(QName.valueOf("component"), SubComponentInfo.class, subComponentInfo);
        structuredBody.getContent().add(jaxbElement);

        final SubComponentInfo subComponentInfo2 = FACTORY_BAK.createSubComponentInfo();
        final SectionInfo section = new SectionInfo();
//        section.setText("");

        section.getEntry().add(createEntry(eventInfo.getOrganisation().getUuid().getUuid(), "OBS", "RQO", requestInfo.getOrderDepartmentMisCode(), requestInfo.getOrderDepartmentName()));
        section.getEntry().add(createEntry(action.getUuid().getUuid(), "OBS", "RQO", action.getIsUrgent() + "", ""));
        // MKB.DiagName
        section.getEntry().add(createEntry("", "OBS", "RQO", requestInfo.getOrderDiagCode(), requestInfo.getOrderDiagText()));
        section.getEntry().add(createEntry(eventInfo.getEventType().getFinance().getName(), "OBS", "RQO", orderInfo.getDiagnosticCode(), orderInfo.getDiagnosticName()));

        for (IndicatorMetodic indicatorMetodic : orderInfo.getIndicatorList()) {
            section.getEntry().add(createEntry("", "OBS", "RQO", indicatorMetodic.getCode(), indicatorMetodic.getName()));
        }


        subComponentInfo2.setSection(section);
        final JAXBElement<SubComponentInfo> jaxbElement2
                = new JAXBElement<SubComponentInfo>(QName.valueOf("component"), SubComponentInfo.class, subComponentInfo2);
        structuredBody.getContent().add(jaxbElement2);

        final SubComponentInfo subComponentInfo3 = FACTORY_BAK.createSubComponentInfo();
        final SectionInfo section3 = new SectionInfo();

        if (patient.getSex() == 2) { // появляется если пациент женщина и беременная
            section3.getEntry().add(createEntryPregnat("", requestInfo));
        }
        subComponentInfo3.setSection(section3);
        final JAXBElement<SubComponentInfo> jaxbElement3
                = new JAXBElement<SubComponentInfo>(QName.valueOf("component"), SubComponentInfo.class, subComponentInfo3);
        structuredBody.getContent().add(jaxbElement3);


        final SubComponentInfo subComponentInfo4 = FACTORY_BAK.createSubComponentInfo();
        final SectionInfo section4 = new SectionInfo();

        if (patient.getSex() == 2) { // появляется если пациент женщина и беременная
            section4.getEntry().add(createEntryComment("", action.getTakenTissue().getNote()));
        }
        subComponentInfo4.setSection(section4);
        final JAXBElement<SubComponentInfo> jaxbElement4
                = new JAXBElement<SubComponentInfo>(QName.valueOf("component"), SubComponentInfo.class, subComponentInfo4);
        structuredBody.getContent().add(jaxbElement4);

        component.setStructuredBody(structuredBody);
        document.setComponent(component);
    }


    private static void createComponentOf(final HL7Document document, final Patient patientInfo) {
        final ComponentOfInfo componentOf = new ComponentOfInfo();
        final EncompassingEncounterInfo encompassingEncounter = new EncompassingEncounterInfo();
        final EeIdInfo eeIdInfo = new EeIdInfo();
        eeIdInfo.setRoot(CUSTODIAN_NAME);
        eeIdInfo.setExtension(patientInfo.getId().toString());
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

    private void createDocAuthor(final HL7Document document, final Action action, final DiagnosticRequestInfo requestInfo) throws CoreException {
        final AuthorInfo author = new AuthorInfo();
        author.setTypeCode("AUT");
        final Date execDate = action.getCreateDatetime();
        if (execDate != null) {
            final DateTimeInfo time = new DateTimeInfo();
            XMLGregorianCalendar xmlTime = null;
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(execDate);
            try {
                xmlTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            } catch (DatatypeConfigurationException e) {
            }
            time.setValue(xmlTime);
            author.setTime(time);
        }

        final AssignedAuthorInfo assignedAuthor = new AssignedAuthorInfo();
        assignedAuthor.setClassCode("ASSIGNED");

        final DoctorIdInfo doctorId = new DoctorIdInfo();
        final Integer docId = requestInfo.getOrderDoctorMisId();
        final Staff doctor = staffBean.getStaffById(docId);
        if (doctor == null) {
            throw new CoreException("Не указан автор назначения");
        }
        doctorId.setExtension(doctor.getId().toString());
        doctorId.setRoot(doctor.getUuid() != null ? doctor.getUuid().getUuid() : "");
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
        if (doctor.getOrgStructure() != null && doctor.getOrgStructure().getUuid() != null) {
            final ReporgIDInfo reporgIDInfo = new ReporgIDInfo();
            reporgIDInfo.setRoot(doctor.getOrgStructure().getUuid().getUuid());
            representedOrganization.setId(reporgIDInfo);
        }
        representedOrganization.setName(CUSTODIAN_NAME);
        assignedAuthor.setRepresentedOrganization(representedOrganization);

        author.setAssignedAuthor(assignedAuthor);
        document.setAuthor(author);
    }

    private static void createRecordTarget(final HL7Document document, final Patient patientInfo, final Event event) {
        final RecordTargetInfo recordTarget = new RecordTargetInfo();
        recordTarget.setTypeCode("RCT");

        final PatientRoleInfo patientRole = new PatientRoleInfo();
        patientRole.setClassCode("PAT");
        patientRole.setAddr(patientInfo.getClientAddresses().get(0).getFreeInput());

        final PatientIDInfo patientId = new PatientIDInfo();
        patientId.setExtension(String.valueOf(patientInfo.getId()));
        patientId.setRoot(GUID);
        patientRole.setId(patientId);

        final PatientInfo patient = new PatientInfo();
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
            logger.error(e.getMessage());
        }
        birthTime.setValue(xmlBirthTime);
        patient.setBirthTime(birthTime);

        patientRole.setPatient(patient);

        final ProviderOrganizationInfo providerOrganization = new ProviderOrganizationInfo();
        providerOrganization.setClassCode("ORG");
        providerOrganization.setDeterminerCode("INSTANCE");
        final LpuIDInfo orgId = new LpuIDInfo();
        final Organisation organisation = event.getOrganisation();
        orgId.setRoot(organisation.getUuid().getUuid());
        providerOrganization.setId(orgId);
        providerOrganization.setName(organisation.getTitle());
        patientRole.setProviderOrganization(providerOrganization);

        recordTarget.setPatientRole(patientRole);

        document.setRecordTarget(recordTarget);
    }

    private static void createOrderStatus(final HL7Document document, final Action action) {
        final HL7Document.VersionNumber versionNumber = new HL7Document.VersionNumber();
        versionNumber.setValue(String.valueOf(action.getStatus()));
        document.setVersionNumber(versionNumber);
    }

    private static void createLanguageDoc(final HL7Document document, final String langCode) {
        final HL7Document.LanguageCode languageCode = new HL7Document.LanguageCode();
        languageCode.setCode(langCode);
        document.setLanguageCode(languageCode);
    }

    private static void createConfLevel(final HL7Document document, final String levelCode) {
        final HL7Document.ConfidentialityCode confidentialityCode = new HL7Document.ConfidentialityCode();
        confidentialityCode.setCode(levelCode);
        confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");
        document.setConfidentialityCode(confidentialityCode);
    }

    private static void createEffectiveTimeRequest(final HL7Document document, final DiagnosticRequestInfo requestInfo) {
        final HL7Document.EffectiveTime effectiveTime = new HL7Document.EffectiveTime();
        effectiveTime.setValue(requestInfo.getOrderMisDate().toString(DATE_FORMAT));
        document.setEffectiveTime(effectiveTime);
    }

    private static void createMedDocId(final HL7Document document, final DiagnosticRequestInfo requestInfo) {
        final HL7Document.Id id = new HL7Document.Id();
        id.setRoot(ROOT);
        id.setExtention(String.valueOf(requestInfo.getOrderMisId()));
        document.setId(id);
    }

    private static void createTypeId(final HL7Document document) {
        final HL7Document.TypeId typeId = new HL7Document.TypeId();
        typeId.setExtention("POCD_HD000040");
        typeId.setRoot(ROOT);
        document.setTypeId(typeId);
    }

    private ActionType getActionType(final Action action) throws CoreException {
        final ActionType actionType = action.getActionType();

        if (actionType.getId() == -1) {
            throw new CoreException("Error no Type For Action" + action.getId());
        }
        return actionType;
    }


    static class EntryFactory {
        static EntryInfo createEntry(final String root,
                                     final String classCode,
                                     final String moodCode,
                                     final String code,
                                     final String displayName) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode(classCode);
            observation.setMoodCode(moodCode);
            observation.setNegationInd("false");
            final ObsIdInfo id = new ObsIdInfo();
            id.setRoot(root);
            id.setExtension("1");
            observation.setId(id);
            final ObsCodeInfo codeInfo = new ObsCodeInfo();
            codeInfo.setCode(code);
            codeInfo.setDisplayName(displayName);
            observation.setCode(codeInfo);
            entry.setObservation(observation);
            return entry;
        }

        static EntryInfo createEntryPregnat(final String code, final DiagnosticRequestInfo requestInfo) {
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
            value.setValue(requestInfo.getOrderPregnatMin() + " ~ " + requestInfo.getOrderPregnatMax());
            observation.setValue(value);
            entry.setObservation(observation);
            return entry;
        }

        static EntryInfo createEntryComment(final String code, final String comment) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode("OBS");
            observation.setMoodCode("EVN");
            final ObsCodeInfo codeInfo = new ObsCodeInfo();
            codeInfo.setCode(code);
            final ObsTranslationInfo translation = new ObsTranslationInfo();
            translation.setDisplayName("комментарий к направлению");
            codeInfo.setTranslation(translation);
            observation.setCode(codeInfo);
            final ObsValueInfo value = new ObsValueInfo();
            value.setValue(comment);
            observation.setValue(value);
            entry.setObservation(observation);
            return entry;
        }

        static EntryInfo createEntryBiomaterial(final BiomaterialInfo biomaterialInfo, final Action action) {
            final EntryInfo entry = new EntryInfo();
            final ObservationInfo observation = new ObservationInfo();
            observation.setClassCode("OBS");
            observation.setMoodCode("ENT");
            final ObsEffectiveTimeInfo effectiveTime = new ObsEffectiveTimeInfo();
            XMLGregorianCalendar xmlTime2 = null;
            GregorianCalendar c2 = new GregorianCalendar();
            c2.setTime(biomaterialInfo.getOrderProbeDate().toDate());
            try {
                xmlTime2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            } catch (DatatypeConfigurationException e) {
//                logger.error(e.getMessage());
            }
            effectiveTime.setValue(xmlTime2);
            observation.setEffectiveTime(effectiveTime);
            entry.setObservation(observation);

            final SpecimenInfo specimen = new SpecimenInfo();
            final SpecimenRoleInfo specimenRole = new SpecimenRoleInfo();
            final SrIdInfo srIdInfo = new SrIdInfo();
            srIdInfo.setRoot(biomaterialInfo.getOrderBarCode());
            specimenRole.setId(srIdInfo);

            final SpecimenPlayingEntityInfo specimenPlayingEntity = new SpecimenPlayingEntityInfo();
            final SpCodeInfo spCodeInfo = new SpCodeInfo();
            spCodeInfo.setCode(biomaterialInfo.getOrderBiomaterialCode());
            final SpTranslationInfo spTranslationInfo = new SpTranslationInfo();
            spTranslationInfo.setDisplayName(biomaterialInfo.getOrderBiomaterialname());
            spCodeInfo.setTranslation(spTranslationInfo);
            specimenPlayingEntity.setCode(spCodeInfo);

            final SpQuantityInfo quantityInfo = new SpQuantityInfo();
            final TakenTissue takenTissue = action.getTakenTissue();
            quantityInfo.setValue(String.valueOf(takenTissue.getAmount()));
            specimenPlayingEntity.setQuantity(quantityInfo);

            final SpTextInfo text = new SpTextInfo();
            final RbUnit unit = takenTissue.getUnit();
            if (unit != null) {
                text.setValue(unit.getId().toString());
                specimenPlayingEntity.setText(text);

            }

            final SpUnitInfo spUnitInfo = new SpUnitInfo();
            final RbTissueType tissueType = takenTissue.getType();
            if (tissueType != null) {
                spCodeInfo.setCode(tissueType.getName());
            }
            specimenPlayingEntity.setUnit(spUnitInfo);

            specimenRole.setSpecimenPlayingEntity(specimenPlayingEntity);
            specimen.setSpecimenRole(specimenRole);
            observation.setSpecimen(specimen);

            entry.setObservation(observation);
            return entry;
        }

    }
}
