package ru.korus.tmis.laboratory.bak.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
