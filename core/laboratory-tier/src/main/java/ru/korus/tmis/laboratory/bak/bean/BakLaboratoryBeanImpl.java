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
    private static final String XML_TEMPLATE = "<ClinicalDocument xmlns='urn:hl7-org:v3' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='urn:hl7-org:v3 CDA.xsd'>\n" +
            "\t<typeId extension='POCD_HD000040' root='2.16.840.1.113883.1.3'/>\n" +
            "\n" +
            "\t<id root='GUID'/>\n" +
            "\t<setID root='id'/>\n" +
            "\t<versionNumber orderStatus='2'/>\n" +
            "\n" +
            "\t<code>${diagnosticCode}, ${diagnosticName}</code>\n" +
            "\n" +
            "\t<title>${diagnosticName}</title>\n" +
            "\t<effectiveTime value='${orderMisDate}'/>\n" +
            "\t<confidentialityCode code='N' codeSystem='2.16.840.1.113883.5.25' displayName='Normal'/>\n" +
            "\t<recordTarget>\n" +
            "\t\t<patientRole>\n" +
            "\t\t\t<id root='${patientMisId}'/>\n" +
            "\t\t\t<addr>${patientAddress}</addr>\n" +
            "\t\t\t<telecom nullFlavor='NI'/>\n" +
            "\t\t\t<patient>\n" +
            "\t\t\t\t<id root='OID' extension='${patientNumber}' assigningAuthorityName='${custodian}' displayable='true'/>\n" +
            "\t\t\t\t<name> ${patientFamily} ${patientName} ${patientPatronum}\n" +
            "\t\t\t\t\t<family>${patientFamily}</family>\n" +
            "\t\t\t\t\t<given>${patientName}</given>\n" +
            "\t\t\t\t\t<given>${patientPatronum}</given>\n" +
            "\t\t\t\t</name>\n" +
            "\t\t\t\t<birthTime value='${patientBirthDate}'/>\n" +
            "\t\t\t\t<administrativeGenderCode code='${patientSex}'/>\n" +
            "\t\t\t</patient>\n" +
            "\t\t\t<providerOrganization>${custodian}</providerOrganization>\n" +
            "\t\t</patientRole>\n" +
            "\t</recordTarget>\n" +
            "\n" +
            "\t<author>\n" +
            "\t\t<time value='${orderMisDate}'/>\n" +
            "\t\t\t<assignedAuthor>\n" +
            "\t\t\t\t<id root='${orderDoctorMisId}' extension='${orderDoctorMisId}'/>\n" +
            "\t\t\t\t<assignedAuthor>\n" +
            "\t\t\t\t\t<code code='DolgCode' displayName='DolgName'/>\n" +
            "\t\t\t\t</assignedAuthor>\n" +
            "\n" +
            "\t\t\t\t<assignedPerson>\n" +
            "\t\t\t\t\t<prefix>${orderDepartmentMisId}</prefix>\n" +
            "\t\t\t\t\t<name>${orderDoctorFamily} ${orderDoctorName} ${orderDoctorPatronum}\n" +
            "\t\t\t\t\t\t<family>${orderDoctorFamily}</family>\n" +
            "\t\t\t\t\t\t<given>${orderDoctorName}</given>\n" +
            "\t\t\t\t\t\t<given>${orderDoctorPatronum}</given>\n" +
            "\t\t\t\t\t</name>\n" +
            "\t\t\t\t</assignedPerson>\n" +
            "\t\t\t</assignedAuthor>\n" +
            "\t</author>\n" +
            "\n" +
            "\t<custodian>\n" +
            "\t\t<assignedCustodian>\n" +
            "\t\t\t<representedCustodianOrganization>\n" +
            "\t\t\t\t<id root='GUID'/>\n" +
            "\t\t\t\t<name>${orderCustodian}</name>\n" +
            "\t\t\t</representedCustodianOrganization>\n" +
            "\t\t</assignedCustodian>\n" +
            "\t</custodian>\n" +
            "\n" +
            "\t<componentOf>\n" +
            "\t\t<encompassingEncounter>\n" +
            "\t\t\t<id root='GUID' extension='${patientNumber}'/>\n" +
            "\t\t\t<effectiveTime nullFlavor='NI'/>\n" +
            "\t\t</encompassingEncounter>\n" +
            "\t</componentOf>\n" +
            "\n" +
            "\t<component>\n" +
            "\t\t<structuredBody>\n" +
            "\t\t\t<component>\n" +
            "\t\t\t\t<section>${orderDiagText}\n" +
            "\t\t\t\t\t<entry>\n" +
            "\t\t\t\t\t\t${orderDiagCode}\n" +
            "\t\t\t\t\t</entry>\n" +
            "\t\t\t\t\t<component>\n" +
            "\t\t\t\t\t\t<section>\n" +
            "\t\t\t\t\t\t\t<title>срочность заказа</title>\n" +
            "\t\t\t\t\t\t\t<text>${isUrgent}</text>\n" +
            "\t\t\t\t\t\t</section>\n" +
            "\t\t\t\t\t</component>\n" +
            "\t\t\t\t\t<component>\n" +
            "\t\t\t\t\t\t<section>\n" +
            "\t\t\t\t\t\t\t<title>средний срок беременности, в неделях</title>\n" +
            "\t\t\t\t\t\t\t<text>${orderPregnat}</text>\n" +
            "\t\t\t\t\t\t</section>\n" +
            "\t\t\t\t\t</component>\n" +
            "\t\t\t\t\t<component>\n" +
            "\t\t\t\t\t\t<section>\n" +
            "\t\t\t\t\t\t\t<title>Комментарий к анализу</title>\n" +
            "\t\t\t\t\t\t\t<text>${orderComment}</text>\n" +
            "\t\t\t\t\t\t</section>\n" +
            "\t\t\t\t\t</component>\n" +
            "\t\t\t\t</section>\n" +
            "\t\t\t</component>\n" +
            "\n" +
            "\t\t\t<component>\n" +
            "\t\t\t\t<entry>\n" +
            "\t\t\t\t\t<observation classCode='OBS' moodCode='ENT'/>\n" +
            "\t\t\t\t\t<effectiveTime value='${orderProbeDate}'/>\n" +
            "\t\t\t\t</entry>\n" +
            "\t\t\t\t<specimen>\n" +
            "\t\t\t\t\t<specimenRole>\n" +
            "\t\t\t\t\t\t<id root='${orderBarCode}|${TakenTissueJournal}'/>\n" +
            "\t\t\t\t\t\t\t<specimenPlayingEntity>\n" +
            "\t\t\t\t\t\t\t\t<code code='${orderBiomaterialCode}'>\n" +
            "\t\t\t\t\t\t\t\t\t<translation displayName='${orderBiomaterialName}'/>\n" +
            "\t\t\t\t\t\t\t\t</code>\n" +
            "\t\t\t\t\t\t\t\t<quantity value='${orderBiomaterialVolume}'/>\n" +
            "\t\t\t\t\t\t\t\t<text value='${orderBiomaterialComment}'/>\n" +
            "\t\t\t\t\t\t\t</specimenPlayingEntity>\n" +
            "\t\t\t\t\t</specimenRole>\n" +
            "\t\t\t\t</specimen>\n" +
            "\t\t\t</component>\n" +
            "\n" +
            "\t\t\t<component>\n" +
            "\t\t\t\t<entry>\n" +
            "\t\t\t\t\t<observation classCode='OBS' moodCode='RQO' negationInd='false'/>\n" +
            "\t\t\t\t\t<id root='GUID'/>\n" +
            "\t\t\t\t\t<id type='${FinanceCode}' extension='${typeFinanceName}'/>\n" +
            "\t\t\t\t\t<code code='${diagnosticCode}' displayName='${diagnosticName}'/>\n" +
            "\t\t\t\t</entry>\n" +
            "\t\t\t</component>\n" +
            "\t\t\t\n" +
            "\t\t\t<component>\n" +
            "\t\t\t\t<section> indicators\n" +
            "\t\t\t\t\t<entry>\n" +
            "\t\t\t\t\t\t<code code='indicatorCode1' displayName='indicatorName1'/>\n" +
            "\t\t\t\t\t</entry>\n" +
            "\n" +
            "\t\t\t\t\t<entry>\n" +
            "\t\t\t\t\t\t<code code='indicatorCoden' displayName='indicatorNamen'/>\n" +
            "\t\t\t\t\t</entry>\n" +
            "\t\t\t\t</section>\n" +
            "\t\t\t</component>\n" +
            "\t\t</structuredBody>\n" +
            "\t</component>\n" +
            "\n" +
            "</ClinicalDocument>\n";

    private final Map<String, Object> mockParams = new HashMap<String, Object>() {{
        final String MOCK = "mock";
        put(CUSTODIAN.getName(), MOCK);
        put(DIAGNOSTIC_CODE.getName(), MOCK);
        put(DIAGNOSTIC_NAME.getName(), MOCK);
        put(IS_URGENT.getName(), 1);
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
                put(CUSTODIAN.getName(), MOCK);
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
                    put(QueryInitializer.ParamName.PATIENT_SEX.getName(), "");
                }
                put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), "00a012234");
                put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), MOCK);
                put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), MOCK);
                put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), MOCK);
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
