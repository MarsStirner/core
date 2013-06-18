package ru.korus.tmis.laboratory.bak.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.laboratory.bak.model.PatientInfo;
import ru.korus.tmis.laboratory.bak.model.QueryHL7;
import ru.korus.tmis.laboratory.bak.utils.QueryInitializer;
import ru.korus.tmis.laboratory.bak.ws.CGMService;
import ru.korus.tmis.laboratory.bak.ws.ICGMService;
import ru.korus.tmis.laboratory.bak.ws.xml.SOAPEnvelopeHandlerResolver;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.korus.tmis.laboratory.bak.utils.QueryInitializer.buildQueryHL7;


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

    private final Map<String, Object> mockParams = new HashMap<String, Object>(){{
        final String MOCK = "mock";
        put(QueryInitializer.ParamName.CUSTODIAN.getName(), MOCK);
        put(QueryInitializer.ParamName.DIAGNOSTIC_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.DIAGNOSTIC_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.IS_URGENT.getName(), 1);
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
        put(QueryInitializer.ParamName.PATIENT_SEX.getName(), PatientInfo.Gender.MEN);
        put(QueryInitializer.ParamName.TAKEN_TISSUE_JOURNAL.getName(), MOCK);
        put(QueryInitializer.ParamName.TYPE_FINANCE_CODE.getName(), MOCK);
        put(QueryInitializer.ParamName.TYPE_FINANCE_NAME.getName(), MOCK);
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_CODE.getName(), 1);
        put(QueryInitializer.ParamName.UNIT_BIOMETRIAL_NAME.getName(), MOCK);
    }};

    CGMService cgmService;

    /**
     * Метод для отсылки запроса на анализ в лабораторию
     */
    @Override
//    @Schedule(minute = "*/1", hour = "*")
    public void sendLisAnalysisRequest() throws CoreException {
        log.info("Create cgmService..");
        cgmService = new CGMService();
        cgmService.setHandlerResolver(new SOAPEnvelopeHandlerResolver());
        try {
            log.info("Sending query cgmService..");
            final ICGMService service = cgmService.getService();
            final QueryHL7 queryHL7 = buildQueryHL7(mockParams);
            final String xml = queryHL7.toXML();
            final String result = service.queryAnalysis(xml);
            log.info("Result query cgmService result: " + result);
        } catch (Exception e) {
            log.info("Error in BakLaboratoryBeanImpl: " + e, e);
        } finally {
            log.info("Not result");
        }
    }


}
