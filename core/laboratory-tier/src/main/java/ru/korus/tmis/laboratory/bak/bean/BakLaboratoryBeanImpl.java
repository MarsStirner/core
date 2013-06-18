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
import ru.korus.tmis.laboratory.business.LaboratoryBean;
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
    private static final String XML_TEMPLATE = "<custodian>${custodian}</custodian>\n" +
            "<patientMisId>${patientMisId}</patientMisId>\n" +
            "<patientNumber>${patientNumber}</patientNumber>\n" +
            "<patientFamily>${patientFamily}</patientFamily>\n" +
            "<patientName>${patientName}</patientName>\n" +
            "<patientPatronum>${patientPatronum}</patientPatronum>\n" +
            "<patientBirthDate>${patientBirthDate}</patientBirthDate>\n" +
            "<patientSex>${patientSex}</patientSex>\n" +
            "<patientAddress>${patientAddress}</patientAddress>\n" +
            "\n" +
            "<orderMisId>${orderMisId}</orderMisId>\n" +
            "<orderMisDate>${orderMisDate}</orderMisDate>\n" +
            "<isUrgent>${isUrgent}</isUrgent>\n" +
            "<orderPregnat>${orderPregnat}</orderPregnat>\n" +
            "<orderDiagCode>${orderDiagCode}</orderDiagCode>\n" +
            "<orderDiagText>${orderDiagText}</orderDiagText>\n" +
            "<orderComment>${orderComment}</orderComment>\n" +
            "<orderDepartmentName>${orderDepartmentName}</orderDepartmentName>\n" +
            "<orderDepartmentMisId>${orderDepartmentMisId}</orderDepartmentMisId>\n" +
            "<orderDoctorMisId>${orderDoctorMisId}</orderDoctorMisId>\n" +
            "<orderDoctorFamily>${orderDoctorFamily}</orderDoctorFamily>\n" +
            "<orderDoctorName>${orderDoctorName}</orderDoctorName>\n" +
            "<orderDoctorPatronum>${orderDoctorPatronum}</orderDoctorPatronum>\n" +
            "\n" +
            "<diagnosticCode>${diagnosticCode}</diagnosticCode>\n" +
            "<diagnosticName>${diagnosticName}</diagnosticName>\n" +
            "<typeFinanceCode>${typeFinanceCode}</typeFinanceCode>\n" +
            "<typeFinanceName>${typeFinanceName}</typeFinanceName>\n" +
            "\n" +
            "<orderBarCode>${orderBarCode}</orderBarCode>\n" +
            "<orderBiomaterialCode>${orderBiomaterialCode}</orderBiomaterialCode>\n" +
            "<TakenTissueJournal>${TakenTissueJournal}</TakenTissueJournal>\n" +
            "<orderBiomaterialComment>${orderBiomaterialComment}</orderBiomaterialComment>\n" +
            "<orderBiomaterialname>${orderBiomaterialname}</orderBiomaterialname>\n" +
            "<orderProbeDate>${orderProbeDate}</orderProbeDate>\n" +
            "<orderBiomaterialVolume>${orderBiomaterialVolume}</orderBiomaterialVolume>\n" +
            "<UnitBiomaterialCode>${UnitBiomaterialCode}</UnitBiomaterialCode>\n" +
            "<UnitBiomaterialName>${UnitBiomaterialName}</UnitBiomaterialName>";

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
    LaboratoryBean laboratoryBean;

    @EJB
    DbActionBeanLocal dbActionBean;


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


        return new HashMap<String, Object>() {{
            final String MOCK = "";
            put(CUSTODIAN.getName(), MOCK);
            put(DIAGNOSTIC_CODE.getName(), orderInfo.diagnosticCode());
            put(DIAGNOSTIC_NAME.getName(), orderInfo.diagnosticName());
            put(IS_URGENT.getName(), MOCK);

            put(QueryInitializer.ParamName.ORDER_BAR_CODE.getName(), biomaterialInfo.orderBarCode().get());
            put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_CODE.getName(), biomaterialInfo.orderBiomaterialCode().get());
            put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), biomaterialInfo.orderTakenTissueId().get());
            put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_COMMENT.getName(), biomaterialInfo.orderBiomaterialComment().get());
            put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_NAME.getName(), biomaterialInfo.orderBiomaterialname().get());
            put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_VOLUME.getName(), MOCK);

            put(QueryInitializer.ParamName.ORDER_COMMENT.getName(), requestInfo.orderComment().get());
            put(QueryInitializer.ParamName.ORDER_DEPARTMENT_MIS_ID.getName(), requestInfo.orderDepartmentMisCode().get());
            put(QueryInitializer.ParamName.ORDER_DEPARTMENT_NAME.getName(), requestInfo.orderDepartmentName().get());
            put(QueryInitializer.ParamName.ORDER_DIAG_CODE.getName(), requestInfo.orderDiagCode().get());
            put(QueryInitializer.ParamName.ORDER_DIAG_TEXT.getName(), requestInfo.orderDiagText().get());
            put(QueryInitializer.ParamName.ORDER_DOCTOR_FAMILY.getName(), requestInfo.orderDoctorFamily().get());
            put(QueryInitializer.ParamName.ORDER_DOCTOR_MIS_ID.getName(), requestInfo.orderDoctorMisId().get());
            put(QueryInitializer.ParamName.ORDER_DOCTOR_NAME.getName(), requestInfo.orderDoctorName().get());
            put(QueryInitializer.ParamName.ORDER_DOCTOR_PATRONUM.getName(), requestInfo.orderDoctorPatronum().get());
            put(QueryInitializer.ParamName.ORDER_MIS_DATE.getName(), requestInfo.orderMisDate().get());
            put(QueryInitializer.ParamName.ORDER_MIS_ID.getName(), requestInfo.orderMisId());
            put(QueryInitializer.ParamName.ORDER_PREGNAT.getName(), requestInfo.orderPregnatMin().get());

            put(QueryInitializer.ParamName.ORDER_PROBE_DATE.getName(), biomaterialInfo.orderProbeDate().get());
            put(QueryInitializer.ParamName.PATIENT_ADDRESS.getName(), "");
            put(QueryInitializer.ParamName.PATIENT_BIRTH_DATE.getName(), patientInfo.patientBirthDate().get());
            put(QueryInitializer.ParamName.PATIENT_FAMILY.getName(), patientInfo.patientFamily().get());
            put(QueryInitializer.ParamName.PATIENT_MIS_ID.getName(), patientInfo.patientMisId());
            put(QueryInitializer.ParamName.PATIENT_NAME.getName(), patientInfo.patientName().get());
            put(QueryInitializer.ParamName.PATIENT_NUMBER.getName(), MOCK);
            put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), patientInfo.patientPatronum().get());
            put(QueryInitializer.ParamName.PATIENT_SEX.getName(), patientInfo.patientSex().toString());
            put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), MOCK);
            put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), MOCK);
            put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), MOCK);
            put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), MOCK);
        }};
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
