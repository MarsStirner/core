package ru.korus.tmis.laboratory.bak.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.laboratory.bak.model.PatientInfo;
import ru.korus.tmis.laboratory.bak.model.QueryHL7;
import ru.korus.tmis.laboratory.bak.utils.QueryInitializer;
import ru.korus.tmis.laboratory.bak.ws.CGMServiceImpl;
import ru.korus.tmis.laboratory.bak.ws.ICGMService;
import ru.korus.tmis.laboratory.bak.ws.xml.SOAPEnvelopeHandlerResolver;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Сервис для отправки заявки на анализы к сервису CGM
 *
 * test @see ru.korus.tmis.laboratory.bak.bean.BulkLaboratoryBeanImplTest
 *
 * @author anosov@outlook.com
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
public class BakLaboratoryBeanImpl implements BakLaboratoryBeanLocal {

    private static final Logger log = LoggerFactory.getLogger(BakLaboratoryBeanImpl.class);

    CGMServiceImpl cgmService;

    private final Map<String, Object> mockParams = new HashMap<String, Object>(){{
        put(QueryInitializer.ParamName.CUSTODIAN.getName(), "mock");
        put(QueryInitializer.ParamName.DIAGNOSTIC_CODE.getName(), "mock");
        put(QueryInitializer.ParamName.DIAGNOSTIC_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.IS_URGENT.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_BAR_CODE.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_CODE.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_COMMENT.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_BIOMATERIAL_VOLUME.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_COMMENT.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DEPARTMENT_MIS_ID.getName(),"mock");
        put(QueryInitializer.ParamName.ORDER_DEPARTMENT_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DIAG_CODE.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DIAG_TEXT.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DOCTOR_FAMILY.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DOCTOR_MIS_ID.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DOCTOR_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_DOCTOR_PATRONUM.getName(), "mock");
        put(QueryInitializer.ParamName.ORDER_MIS_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.ORDER_MIS_ID.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_PREGNAT.getName(), 1);
        put(QueryInitializer.ParamName.ORDER_PROBE_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.PATIENT_ADDRESS.getName(), "mock");
        put(QueryInitializer.ParamName.PATIENT_BIRTH_DATE.getName(), new Date());
        put(QueryInitializer.ParamName.PATIENT_FAMILY.getName(), "mock");
        put(QueryInitializer.ParamName.PATIENT_MIS_ID.getName(), 1);
        put(QueryInitializer.ParamName.PATIENT_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.PATIENT_NUMBER.getName(), "mock");
        put(QueryInitializer.ParamName.PATIENT_PATRONUM.getName(), "mock");
        put(QueryInitializer.ParamName.PATIENT_SEX.getName(), PatientInfo.Gender.MEN);
        put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), "mock");
        put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), "mock");
        put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), "mock");
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), 1);
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), "mock");
    }};

    @Override
    @Schedule(minute = "*/1", hour = "*")
    public void sendLisAnalysisRequest() {
        try {
            log.info("Create cgmService..");
            cgmService = new CGMServiceImpl();
            cgmService.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
            log.info("Sending query cgmService..");
            final ICGMService service = cgmService.getService();
            final QueryHL7 queryHL7 = buildQueryHL7(mockParams);
            final String result = service.queryAnalysis("GLASSFISH");
            log.info("Result query cgmService result: " + result);
        } catch (Exception e) {
            log.info("Error in BakLaboratoryBeanImpl: " + e, e);
        } finally {
            log.info("Not result");
        }
    }


    private QueryHL7 buildQueryHL7(Map<String, Object> params) {
        QueryHL7 query = new QueryHL7();
        query.biomaterialInfo = QueryInitializer.initBiomaterialInfo(params);
        query.patientInfo = QueryInitializer.initPatientInfo(params);
        query.diagnosticRequestInfo = QueryInitializer.initDiagnosticRequestInfo(params);
        query.orderInfo  = QueryInitializer.initOrderInfo(params);
        return query;
    }
}