package ru.korus.tmis.ws.laboratory.bak.ws.client.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal;
import ru.korus.tmis.laboratory.data.request.BiomaterialInfo;
import ru.korus.tmis.laboratory.data.request.DiagnosticRequestInfo;
import ru.korus.tmis.laboratory.data.request.OrderInfo;
import ru.korus.tmis.laboratory.data.request.PatientInfo;
import ru.korus.tmis.ws.laboratory.bak.model.QueryHL7;
import ru.korus.tmis.ws.laboratory.bak.utils.QueryInitializer;
import ru.korus.tmis.ws.laboratory.bak.ws.client.CGMServiceFactory;
import ru.korus.tmis.ws.laboratory.bak.ws.client.ICGMService;
import ru.korus.tmis.ws.laboratory.bak.ws.client.xml.SOAPEnvelopeHandlerResolver;
import scala.Option;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.korus.tmis.ws.laboratory.bak.utils.QueryInitializer.ParamName.*;


/**
 * Сервис для отправки заявки на анализы к сервису CGM
 * <p/>
 * test @see ru.korus.tmis.ws.laboratory.bak.ws.client.bean.BulkLaboratoryBeanImplTest
 *
 * @author anosov@outlook.com
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
public class BakLaboratoryBean implements IBakLaboratoryBean {

    private static final Logger log = LoggerFactory.getLogger(BakLaboratoryBean.class);

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
//    @Schedule(minute = "*/1", hour = "*")
    public void sendLisAnalysisRequest(int actionId) throws CoreException {
        log.debug("BakLis: create service factory");
        final ICGMService service = createCGMService();
        final String queryHL7 = createRequestMessage(actionId);
        String result = "";
        try {
            result = service.queryAnalysis(queryHL7);
        } catch (Exception e) {
            log.error("Error request to LIS Lab. Message:" + e.getMessage());
            log.info("====== queryHL7 request ======== \n" + queryHL7);
            throw new CoreException(e.getMessage());
        } finally {
            log.info("Result request to LIS Lab: \n" + result);
        }
    }


    /**
     * Создание CGM-сервиса для запросов в ЛИС
     * @see ICGMService
     * @return ICGMService - сервис для выполнения запросов
     */
    private ICGMService createCGMService() {
        final CGMServiceFactory cgmServiceFactory = new CGMServiceFactory();
        cgmServiceFactory.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
        return cgmServiceFactory.getService();
    }

    private String createRequestMessage(int actionId) throws CoreException {
        final QueryHL7 queryHL7 = new QueryHL7(getAnalysisRequest(actionId));
        return queryHL7.format();
    }

    /**
     * FIXME необходимо переделать, нужно добавить валидацию полученных данных, остается неизменным пока не определен окончательно формат сервисов
     * @param actionId
     * @return
     * @throws CoreException
     */
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
                put(CUSTODIAN.getName(), "ФНКЦ");
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


        return new PatientInfo(
                misId,
                Option.<String>apply(lastName),
                Option.<String>apply(firstName),
                Option.<String>apply(patrName),
                Option.<Date>apply(birthDate),
                sex);
    }

}
