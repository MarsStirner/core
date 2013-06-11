package ru.korus.tmis.laboratory.bak.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.laboratory.bak.model.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * инициализатор для значений полей запроса
 *
 * @author anosov@outlook.com
 *         date: 5/25/13
 */
public class QueryInitializer {

    private static final Logger log = LoggerFactory.getLogger(QueryInitializer.class);


    public static QueryHL7 buildQueryHL7(Map<String, Object> params) {
        final QueryHL7 query = new QueryHL7();
        query.biomaterialInfo = initBiomaterialInfo(params);
        query.patientInfo = initPatientInfo(params);
        query.diagnosticRequestInfo = initDiagnosticRequestInfo(params);
        query.orderInfo  = initOrderInfo(params);
        return query;
    }

    public static DiagnosticRequestInfo initDiagnosticRequestInfo(Map<String, Object> params) {
        DiagnosticRequestInfo diagnosticRequestInfo = new DiagnosticRequestInfo();
        diagnosticRequestInfo.setOrderMisId(initValueInterger(params, ParamName.ORDER_MIS_ID.name, true));
        diagnosticRequestInfo.setOrderMisDate(initValueXMLDate(params, ParamName.ORDER_MIS_DATE.name, true));
        diagnosticRequestInfo.setUrgent(initValueInterger(params, ParamName.IS_URGENT.name, true));
        diagnosticRequestInfo.setOrderPregnat(initValueInterger(params, ParamName.ORDER_PREGNAT.name, false));
        diagnosticRequestInfo.setOrderDiagCode(initValueString(params, ParamName.ORDER_DIAG_CODE.name, false));
        diagnosticRequestInfo.setOrderDiagText(initValueString(params, ParamName.ORDER_DIAG_TEXT.name, false));
        diagnosticRequestInfo.setOrderComment(initValueString(params, ParamName.ORDER_COMMENT.name, false));
        diagnosticRequestInfo.setOrderDepartmentName(initValueString(params, ParamName.ORDER_DEPARTMENT_NAME.name, false));
        diagnosticRequestInfo.setOrderDepartmentMisId(initValueString(params, ParamName.ORDER_DEPARTMENT_MIS_ID.name, true));
        diagnosticRequestInfo.setOrderDoctorMisId(initValueString(params, ParamName.ORDER_DOCTOR_MIS_ID.name, true));
        diagnosticRequestInfo.setOrderDoctorFamily(initValueString(params, ParamName.ORDER_DOCTOR_FAMILY.name, false));
        diagnosticRequestInfo.setOrderDoctorName(initValueString(params, ParamName.ORDER_DOCTOR_NAME.name, false));
        diagnosticRequestInfo.setOrderDoctorPatronum(initValueString(params, ParamName.ORDER_DOCTOR_PATRONUM.name, false));
        return diagnosticRequestInfo;
    }

    public static OrderInfo initOrderInfo(Map<String, Object> params) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setDiagnosticCode(initValueString(params, ParamName.DIAGNOSTIC_CODE.name, true));
        orderInfo.setDiagnosticName(initValueString(params, ParamName.DIAGNOSTIC_NAME.name, false));
        orderInfo.setTypeFinanceCode(initValueString(params, ParamName.TYPE_FINANCE_CODE.name, true));
        orderInfo.setTypeFinanceName(initValueString(params, ParamName.TYPE_FINANCE_NAME.name, true));
        return orderInfo;
    }

    public static PatientInfo initPatientInfo(Map<String, Object> params) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setCustodian(initValueString(params, ParamName.CUSTODIAN.name, true));
        patientInfo.setPatientMisId(initValueInterger(params, ParamName.PATIENT_MIS_ID.name, true));
        patientInfo.setPatientNumber(initValueString(params, ParamName.PATIENT_NUMBER.name, true));
        patientInfo.setPatientFamily(initValueString(params, ParamName.PATIENT_FAMILY.name, false));
        patientInfo.setPatientName(initValueString(params, ParamName.PATIENT_NAME.name, false));
        patientInfo.setPatientPatronum(initValueString(params, ParamName.PATIENT_PATRONUM.name, false));
        patientInfo.setPatientBirthDate(initValueDate(params, ParamName.PATIENT_BIRTH_DATE.name, true));
        patientInfo.setPatientSex(QueryInitializer.<PatientInfo.Gender>initValueCustomType(params, ParamName.PATIENT_SEX.name, true));
        patientInfo.setPatientAddress(initValueString(params, ParamName.PATIENT_ADDRESS.name, false));
        return patientInfo;
    }

    public static BiomaterialInfo initBiomaterialInfo(Map<String, Object> params) {
        BiomaterialInfo bio = new BiomaterialInfo();
        bio.setOrderBarCode(initValueString(params, ParamName.ORDER_BAR_CODE.name, true));
        bio.setTakenTissueJournal(initValueString(params, ParamName.TAKEN_TISSUE_JOURNAL.name, true));
        bio.setOrderProbeDate(initValueXMLDate(params, ParamName.ORDER_PROBE_DATE.name, true));
        bio.setOrderBiomaterialCode(initValueString(params, ParamName.ORDER_BIOMATERIAL_CODE.name, false));
        bio.setOrderBiomaterialname(initValueString(params, ParamName.ORDER_BIOMATERIAL_NAME.name, false));
        bio.setOrderBiomaterialVolume(initValueInterger(params, ParamName.ORDER_BIOMATERIAL_VOLUME.name, false));
        bio.setUnitBiomaterialCode(initValueInterger(params, ParamName.UNIT_BIOMETRIAL_CODE.name, false));
        bio.setUnitBiomaterialName(initValueString(params, ParamName.UNIT_BIOMETRIAL_NAME.name, false));
        bio.setOrderBiomaterialComment(initValueString(params, ParamName.ORDER_BIOMATERIAL_COMMENT.name, false));
        return bio;
    }

    // {{ Helper's methods
    private static String initValueString(Map<String, Object> params, String param, boolean required) {
        String value = initValueString(params, param);
        if (required && StringUtils.isEmpty(value)) {
            throw new IllegalStateException("Parameter " + param + " must be not null or empty");
        }
        return value;
    }

    private static String initValueString(Map<String, Object> params, String param) {
        checkNotNullOrEmpty(params, param);
        final String value = (String) params.get(param);
        log.debug("Initialize query. Pass param [" + param + "] " + " by value [" + value + "]");
        return value;
    }

    private static Integer initValueInterger(Map<String, Object> params, String param, boolean required) {
        Integer value = initValueInterger(params, param);
        if (required && value == null) {
            throw new IllegalStateException("Parameter " + param + " must be not null");
        }
        return value;
    }

    private static Integer initValueInterger(Map<String, Object> params, String param) {
        checkNotNullOrEmpty(params, param);
        final Integer value = (Integer) params.get(param);
        log.debug("Initialize query. Pass param [" + param + "] " + " by value [" + value + "]");
        return value;
    }

    private static Date initValueDate(Map<String, Object> params, String param, boolean required) {
        Date value = initValueDate(params, param);
        if (required && value == null) {
            throw new IllegalStateException("Parameter " + param + " must be not null");
        }
        return value;
    }

    private static Date initValueDate(Map<String, Object> params, String param) {
        checkNotNullOrEmpty(params, param);
        final Date value = (Date) params.get(param);
        log.debug("Initialize query. Pass param [" + param + "] " + " by value [" + value.getTime() + "]");
        return value;
    }

    private static <T> T initValueCustomType(Map<String, Object> params, String param, boolean required) {
        T value = initValueCustomType(params, param);
        if (required && value == null) {
            throw new IllegalStateException("Parameter " + param + " must be not null");
        }
        return value;
    }

    private static <T> T initValueCustomType(Map<String, Object> params, String param) {
        checkNotNullOrEmpty(params, param);
        final T value = (T) params.get(param);
        log.debug("Initialize query. Pass param [" + param + "] " + " by value [" + value + "]");
        return value;
    }

    private static XMLGregorianCalendar initValueXMLDate(Map<String, Object> params, String param, boolean required) {
        XMLGregorianCalendar value = initValueXMLDate(params, param);
        if (required && value == null) {
            throw new IllegalStateException("Parameter " + param + " must be not null");
        }
        return value;
    }

    private static XMLGregorianCalendar initValueXMLDate(Map<String, Object> params, String param) {
        checkNotNullOrEmpty(params, param);
        GregorianCalendar c = new GregorianCalendar();
        final Date temp = (Date) params.get(param);
        c.setTime(temp);
        XMLGregorianCalendar value = null;
        try {
            value = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        log.debug("Initialize query. Pass param [" + param + "] " + " by value [" + value + "]");
        return value;
    }

    private static void checkNotNullOrEmpty(Map<String, Object> params, String param) {
        if (params == null || params.isEmpty() || StringUtils.isEmpty(param)) {
            throw new IllegalArgumentException("Map<String, Object> params or String param - could not be empty or null");
        }
    }
    // }}


    public enum ParamName {
        // <---- Биоматериалы ---->
        ORDER_BAR_CODE("orderBarCode"),
        ORDER_BIOMATERIAL_CODE("orderBiomaterialCode"),
        TAKEN_TISSUE_JOURNAL("TakenTissueJournal"),
        ORDER_BIOMATERIAL_COMMENT("orderBiomaterialComment"),
        ORDER_BIOMATERIAL_NAME("orderBiomaterialname"),
        ORDER_PROBE_DATE("orderProbeDate"),
        ORDER_BIOMATERIAL_VOLUME("orderBiomaterialVolume"),
        UNIT_BIOMETRIAL_CODE("UnitBiomaterialCode"),
        UNIT_BIOMETRIAL_NAME("UnitBiomaterialName"),
        // <---- Биоматериалы ---->

        // <---- PatientInfo ---->
        CUSTODIAN("custodian"),
        PATIENT_MIS_ID("patientMisId"),
        PATIENT_NUMBER("patientNumber"),
        PATIENT_FAMILY("patientFamily"),
        PATIENT_NAME("patientName"),
        PATIENT_PATRONUM("patientPatronum"),
        PATIENT_BIRTH_DATE("patientBirthDate"),
        PATIENT_SEX("patientSex"),
        PATIENT_ADDRESS("patientAddress"),
        // <---- PatientInfo ---->

        // <---- DiagnosticRequestInfo ---->
        ORDER_MIS_ID("orderMisId"),
        ORDER_MIS_DATE("orderMisDate"),
        IS_URGENT("isUrgent"),
        ORDER_PREGNAT("orderPregnat"),
        ORDER_DIAG_CODE("orderDiagCode"),
        ORDER_DIAG_TEXT("orderDiagText"),
        ORDER_COMMENT("orderComment"),
        ORDER_DEPARTMENT_NAME("orderDepartmentName"),
        ORDER_DEPARTMENT_MIS_ID("orderDepartmentMisId"),
        ORDER_DOCTOR_MIS_ID("orderDoctorMisId"),
        ORDER_DOCTOR_FAMILY("orderDoctorFamily"),
        ORDER_DOCTOR_NAME("orderDoctorName"),
        ORDER_DOCTOR_PATRONUM("orderDoctorPatronum"),

        // <---- OrderInfo ---->
        DIAGNOSTIC_CODE("diagnosticCode"),
        DIAGNOSTIC_NAME("diagnosticName"),
        TYPE_FINANCE_CODE("typeFinanceCode"),
        TYPE_FINANCE_NAME("typeFinanceName");

        private String name;

        ParamName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
