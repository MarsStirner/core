package ru.korus.tmis.laboratory.bak.utils

import ru.korus.tmis.laboratory.bak.model.PatientInfo
import ru.korus.tmis.laboratory.bak.model.QueryHL7
import spock.lang.Shared
import spock.lang.Specification

/**
 *
 * @author anosov@outlook.com 
 * date: 5/25/13
 */
class QueryInitializerSpec extends Specification {

    @Shared
    def mockParams

    def setup() {
        mockParams = new HashMap<String, Object>(){{
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

    }

    def "тестировние построение запроса через QueryInitializer"() {
        given:
        QueryHL7 query = new QueryHL7();
        query.setBiomaterialInfo(QueryInitializer.initBiomaterialInfo(mockParams));
        query.setPatientInfo(QueryInitializer.initPatientInfo(mockParams));
        query.setDiagnosticRequestInfo(QueryInitializer.initDiagnosticRequestInfo(mockParams));
        query.setOrderInfo(QueryInitializer.initOrderInfo(mockParams));

        expect:
        query != null
        query.biomaterialInfo != null
        query.diagnosticRequestInfo != null
        query.orderInfo != null
        query.patientInfo != null
    }
}
