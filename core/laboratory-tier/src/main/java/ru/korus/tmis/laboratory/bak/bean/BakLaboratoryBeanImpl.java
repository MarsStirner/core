package ru.korus.tmis.laboratory.bak.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.laboratory.bak.utils.QueryInitializer;
import ru.korus.tmis.laboratory.bak.ws.CGMService;
import ru.korus.tmis.laboratory.bak.ws.ICGMService;
import ru.korus.tmis.laboratory.bak.ws.xml.SOAPEnvelopeHandlerResolver;
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal;
import ru.korus.tmis.laboratory.data.request.BiomaterialInfo;
import ru.korus.tmis.laboratory.data.request.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.data.request.OrderInfo;
import ru.korus.tmis.laboratory.data.request.PatientInfo;
import ru.korus.tmis.util.TextFormat;
import scala.Option;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.korus.tmis.laboratory.bak.utils.QueryInitializer.ParamName.*;


/**
 * Сервис для отправки заявки на анализы к сервису CGM
 * <p/>
 * test @see ru.korus.tmis.laboratory.bak.bean.BulkLaboratoryBeanImplTest
 *
 * @author anosov@outlook.com
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
public class BakLaboratoryBeanImpl implements BakLaboratoryBeanLocal {

    private static final Logger log = LoggerFactory.getLogger(BakLaboratoryBeanImpl.class);
    private static final String XML_TEMPLATE = "<ClinicalDocument xmlns='urn:hl7-org:v3' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='urn:hl7-org:v3 CDA.xsd'>" +
            "<typeId extension='POCD_HD000040' root='2.16.840.1.113883.1.3'/>" +
            "<id root='GUID'/>" +
            "<setID root='id'/>" +
            "<versionNumber orderStatus='2'/>" +
            "<code>${diagnosticCode}, ${diagnosticName}</code>" +
            "<title>${diagnosticName}</title>" +
            "<effectiveTime value='${orderMisDate}'/>" +
            "<confidentialityCode code='N' codeSystem='2.16.840.1.113883.5.25' displayName='Normal'/>" +
            "<recordTarget>" +
            "<patientRole>" +
            "<id root='${patientMisId}'/>" +
            "<addr>${patientAddress}</addr>" +
            "<telecom nullFlavor='NI'/>" +
            "<patient>" +
            "<id root='OID' extension='${patientNumber}' assigningAuthorityName='${custodian}' displayable='true'/>" +
            "<name> ${patientFamily} ${patientName} ${patientPatronum}" +
            "<family>${patientFamily}</family>" +
            "<given>${patientName}</given>" +
            "<given>${patientPatronum}</given>" +
            "</name>" +
            "<birthTime value='${patientBirthDate}'/>" +
            "<administrativeGenderCode code='${patientSex}'/>" +
            "</patient>" +
            "<providerOrganization>${custodian}</providerOrganization>" +
            "</patientRole>" +
            "</recordTarget>" +
            "<author>" +
            "<time value='${orderMisDate}'/>" +
            "<assignedAuthor>" +
            "<id root='${orderDoctorMisId}' extension='${orderDoctorMisId}'/>" +
            "<assignedAuthor>" +
            "<code code='DolgCode' displayName='DolgName'/>" +
            "</assignedAuthor>" +
            "<assignedPerson>" +
            "<prefix>${orderDepartmentMisId}</prefix>" +
            "<name>${orderDoctorFamily} ${orderDoctorName} ${orderDoctorPatronum}" +
            "<family>${orderDoctorFamily}</family>" +
            "<given>${orderDoctorName}</given>" +
            "<given>${orderDoctorPatronum}</given>" +
            "</name>" +
            "</assignedPerson>" +
            "</assignedAuthor>" +
            "</author>" +
            "<custodian>" +
            "<assignedCustodian>" +
            "<representedCustodianOrganization>" +
            "<id root='GUID'/>" +
            "<name>${сustodian}</name>" +
            "</representedCustodianOrganization>" +
            "</assignedCustodian>" +
            "</custodian>" +
            "<componentOf>" +
            "<encompassingEncounter>" +
            "<id root='GUID' extension='${patientNumber}'/>" +
            "<effectiveTime nullFlavor='NI'/>" +
            "</encompassingEncounter>" +
            "</componentOf>" +
            "<component>" +
            "<structuredBody>" +
            "<component>" +
            "<section>${orderDiagText}" +
            "<entry>" +
            "${orderDiagCode}" +
            "</entry>" +
            "<component>" +
            "<section>" +
            "<title>срочность заказа</title>" +
            "<text>${isUrgent}</text>" +
            "</section>" +
            "</component>" +
            "<component>" +
            "<section>" +
            "<title>средний срок беременности, в неделях</title>" +
            "<text>${orderPregnat}</text>" +
            "</section>" +
            "</component>" +
            "<component>" +
            "<section>" +
            "<title>Комментарий к анализу</title>" +
            "<text>${orderComment}</text>" +
            "</section>" +
            "</component>" +
            "</section>" +
            "</component>" +
            "<component>" +
            "<entry>" +
            "<observation classCode='OBS' moodCode='ENT'/>" +
            "<effectiveTime value='${orderProbeDate}'/>" +
            "</entry>" +
            "<specimen>" +
            "<specimenRole>" +
            "<id root='${orderBarCode}|${TakenTissueJournal}'/>" +
            "<specimenPlayingEntity>" +
            "<code code='${orderBiomaterialCode}'>" +
            "<translation displayName='${orderBiomaterialName}'/>" +
            "</code>" +
            "<quantity value='${orderBiomaterialVolume}'/>" +
            "<text value='${orderBiomaterialComment}'/>" +
            "</specimenPlayingEntity>" +
            "</specimenRole>" +
            "</specimen>" +
            "</component>" +
            "<component>" +
            "<entry>" +
            "<observation classCode='OBS' moodCode='RQO' negationInd='false'/>" +
            "<id root='${uuid}'/>" +
            "<id type='${FinanceCode}' extension='${typeFinanceName}'/>" +
            "<code code='${diagnosticCode}' displayName='${diagnosticName}'/>" +
            "</entry>" +
            "</component>" +
            "<component>" +
            "<section> indicators" +
            "<entry>" +
            "<code code='indicatorCode1' displayName='indicatorName1'/>" +
            "</entry>" +
            "<entry>" +
            "<code code='indicatorCoden' displayName='indicatorNamen'/>" +
            "</entry>" +
            "</section>" +
            "</component>" +
            "</structuredBody>" +
            "</component>" +
            "</ClinicalDocument>";

    private final Map<String, Object> mockParams = new HashMap<String, Object>() {{
        final String MOCK = "mock";
        put(CUSTODIAN.getName(), MOCK);
        put(DIAGNOSTIC_CODE.getName(), MOCK);
        put(DIAGNOSTIC_NAME.getName(), MOCK);
        put(IS_URGENT.getName(), 1);
        put("uuid", java.util.UUID.randomUUID().toString());
        put(QueryInitializer.ParamName.ORDER_BAR_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_COMMENT.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_VOLUME.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_COMMENT.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DEPARTMENT_MIS_ID.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DEPARTMENT_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DIAG_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DIAG_TEXT.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DOCTOR_FAMILY.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DOCTOR_MIS_ID.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DOCTOR_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_DOCTOR_PATRONUM.getName(), MOCK);
        put(QueryInitializer.ParamName.ORDER_MIS_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.ORDER_MIS_ID.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_PREGNAT.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_PROBE_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.PATIENT_ADDRESS.getName(), MOCK);
        put(QueryInitializer.ParamName.PATIENT_BIRTH_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.PATIENT_FAMILY.getName(), MOCK);
        put(QueryInitializer.ParamName.PATIENT_MIS_ID.getName(), 1);
        put(QueryInitializer.ParamName.PATIENT_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.PATIENT_NUMBER.getName(), MOCK);
        put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), MOCK);
//        put(QueryInitializer.ParamName.PATIENT_SEX.getName(), PatientInfo.Gender.MEN);
        put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), MOCK);
        put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), 1);
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), MOCK);
    }};

    @EJB
    private LaboratoryBeanLocal laboratoryBean;

    @EJB
    private DbActionBeanLocal dbActionBean;


    CGMService cgmService;

    /**
     * Метод для отсылки запроса на анализ в лабораторию
     */
    @Override
//    @Schedule(minute = "*/1", hour = "*")
    public void sendLisAnalysisRequest(int actionId) throws CoreException {
        log.info("Create cgmService..");
        cgmService = new CGMService();
        cgmService.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
        try {
            log.info("Sending query cgmService..");
            final ICGMService service = cgmService.getService();
//            final QueryHL7 queryHL7 = buildQueryHL7(mockParams);

//            final String xml = queryHL7.toXML();
            TextFormat tf = new TextFormat(XML_TEMPLATE);
            final String xml = tf.format(getAnalysisRequest(actionId));
            log.info("Bak XML request: \n " + xml);
            final String result = service.queryAnalysis(xml);
            log.info("Result query cgmService result: " + result);
        } catch (Exception e) {
            log.error("Error in BakLaboratoryBeanImpl: " + e, e);
        } finally {
            log.info("Not result");
        }
    }

    private Map<String, Object> getAnalysisRequest(int actionId) throws CoreException {
        final Action action = dbActionBean.getActionById(actionId);
        final ActionType actionType = action.getActionType();

        if (actionType.getId() == -1) {
            throw new CoreException("Error no Type For Action" + action.getId());
        }

        log.info("sendLisAnalysisRequest actionId=" + actionId);

        // Patient section
        Event event = action.getEvent();
        final Patient patient = event.getPatient();
        final PatientInfo patientInfo = getPatientInfo(patient);
        // Request section
        final DiagnosticRequestInfo requestInfo = laboratoryBean.getDiagnosticRequestInfo(action);
        // Biomaterial section
        final BiomaterialInfo biomaterialInfo = laboratoryBean.getBiomaterialInfo(action, action.getTakenTissue());
        // Order section
        final OrderInfo orderInfo = laboratoryBean.getOrderInfo(action, actionType);


        return new HashMap<String, Object>() {
            {
                final String MOCK = "";
                put(CUSTODIAN.getName(), "ФМКЦ");
                put(DIAGNOSTIC_CODE.getName(), orderInfo.diagnosticCode());
                put(DIAGNOSTIC_NAME.getName(), orderInfo.diagnosticName());
                put(IS_URGENT.getName(), MOCK);

                put(QueryInitializer.ParamName.ORDER_BAR_CODE.getName(), biomaterialInfo.orderBarCode().get());
                put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_CODE.getName(), biomaterialInfo.orderBiomaterialCode().get());
                try {
                    put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), biomaterialInfo.orderTakenTissueId().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_COMMENT.getName(), biomaterialInfo.orderBiomaterialComment().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_COMMENT.getName(), MOCK);
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_NAME.getName(), biomaterialInfo.orderBiomaterialname().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_NAME.getName(), "");
                }
                put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_VOLUME.getName(), MOCK);
                try {
                    put(QueryInitializer.ParamName.ORDER_COMMENT.getName(), requestInfo.orderComment().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_COMMENT.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DEPARTMENT_MIS_ID.getName(), requestInfo.orderDepartmentMisCode().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DEPARTMENT_MIS_ID.getName(), "");

                }
                try {
                    put(QueryInitializer.ParamName.ORDER_DEPARTMENT_NAME.getName(), requestInfo.orderDepartmentName().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DEPARTMENT_NAME.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_DIAG_CODE.getName(), requestInfo.orderDiagCode().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DIAG_CODE.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DIAG_TEXT.getName(), requestInfo.orderDiagText().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DIAG_TEXT.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_FAMILY.getName(), requestInfo.orderDoctorFamily().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_FAMILY.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_MIS_ID.getName(), requestInfo.orderDoctorMisId().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_MIS_ID.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_NAME.getName(), requestInfo.orderDoctorName().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_NAME.getName(), "");
                }

                try {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_PATRONUM.getName(), requestInfo.orderDoctorPatronum().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_DOCTOR_PATRONUM.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_MIS_DATE.getName(), requestInfo.orderMisDate().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_MIS_DATE.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_MIS_ID.getName(), requestInfo.orderMisId());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_MIS_ID.getName(), "0");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_PREGNAT.getName(), requestInfo.orderPregnatMin().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_PREGNAT.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.ORDER_PROBE_DATE.getName(), biomaterialInfo.orderProbeDate().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.ORDER_PROBE_DATE.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_ADDRESS.getName(), "");
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_ADDRESS.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_BIRTH_DATE.getName(), patientInfo.patientBirthDate().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_BIRTH_DATE.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_FAMILY.getName(), patientInfo.patientFamily().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_FAMILY.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_MIS_ID.getName(), patientInfo.patientMisId());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_MIS_ID.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_NAME.getName(), patientInfo.patientName().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_NAME.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_NUMBER.getName(), MOCK);
                } catch (Exception e) {
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), patientInfo.patientPatronum().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), "");
                }
                try {
                    put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), patientInfo.patientPatronum().get());
                } catch (Exception e) {
                    put(QueryInitializer.ParamName.PATIENT_SEX.getName(), patient.getSex());
                }
                put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), "00a012234");
                put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), MOCK);
                put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), MOCK);
                put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), MOCK);
                put("GUID", "17891798");
                put("custodian", "17891798");
            }
        };
    }

    private PatientInfo getPatientInfo(Patient patient) {
        Integer misId = patient.getId();
        log.info("Patient:Code=" + patient.getId());
        // LastName (string) -- фамилия
        String lastName = patient.getLastName();
        log.info("Patient:Family=" + patient.getLastName());
        // FirstName (string) -- имя
        String firstName = patient.getFirstName();
        log.info("Patient:FirstName=" + patient.getFirstName());
        // MiddleName (string) -- отчество
        String patrName = patient.getPatrName();
        log.info("Patient:MiddleName=" + patient.getPatrName());
        // BirthDate (datetime) -- дата рождения
        Date birthDate = patient.getBirthDate();
        log.info("Patient:BirthDate=" + patient.getBirthDate());
        // Sex (enum) -- пол (мужской/женский/не определен)
        Sex sex = Sex.valueOf(patient.getSex());
        log.info("Patient:Sex=" + patient.getSex());


        final PatientInfo patientInfo = new PatientInfo(
                misId,
                Option.<String>apply(lastName),
                Option.<String>apply(firstName),
                Option.<String>apply(patrName),
                Option.<Date>apply(birthDate),
                sex);

        return patientInfo;
    }

}
