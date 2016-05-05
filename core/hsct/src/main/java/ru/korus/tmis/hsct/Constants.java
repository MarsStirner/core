package ru.korus.tmis.hsct;

import com.google.common.collect.ImmutableSet;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:17 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class Constants {
    public static final String ACTION_TYPE_CODE = "hsct";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";

    //APT codes
    public static final String APT_CODE_DISEASE_STATUS = "disease_status";
    public static final String APT_CODE_DIAGNOSIS = "diagnosis";
    public static final String APT_CODE_DIAGNOSIS_ICD_CODE = "diagnosis_icd_code";
    public static final String APT_CODE_DIAGNOSIS_DATE = "diagnosis_date";
    public static final String APT_CODE_COMPLICATIONS = "complications";
    public static final String APT_CODE_SECONDARY_DIAGNOSES = "secondary_diagnoses";
    public static final String APT_CODE_ANTI_CVM_IGG_CODE = "anti_cmv_igg_code";
    public static final String APT_CODE_INDICATIONS = "indications";
    public static final String APT_CODE_INDICATIONS_DATE = "indications_date";
    public static final String APT_CODE_OPTIMAL_HSCT_DATE = "optimal_hsct_date";
    public static final String APT_CODE_HSCT_TYPE_CODE = "hsct_type_code";
    public static final String APT_CODE_HAS_SIBLINGS = "has_siblings";
    public static final ImmutableSet<String> HSCT_REQUEST_APT_CODES = ImmutableSet.of(
            APT_CODE_DISEASE_STATUS,
            APT_CODE_DIAGNOSIS,
            APT_CODE_DIAGNOSIS_ICD_CODE,
            APT_CODE_DIAGNOSIS_DATE,
            APT_CODE_COMPLICATIONS,
            APT_CODE_SECONDARY_DIAGNOSES,
            APT_CODE_ANTI_CVM_IGG_CODE,
            APT_CODE_INDICATIONS,
            APT_CODE_INDICATIONS_DATE,
            APT_CODE_OPTIMAL_HSCT_DATE,
            APT_CODE_HSCT_TYPE_CODE,
            APT_CODE_HAS_SIBLINGS
    );
    //Свойства куда будут писаться результаты
    public static final String APT_CODE_RESULT = "result";
    public static final String APT_CODE_IS_COMPLETED = "is_completed";
    ///////////Свойства дневников
    public static final String APT_JOURNAL_THERAPY_TITLE = "therapyTitle";
    public static final String APT_JOURNAL_THERAPY_BEG_DATE = "therapyBegDate";
    public static final String APT_JOURNAL_THERAPY_END_DATE = "therapyEndDate";
    public static final String APT_JOURNAL_PHASE_TITLE = "therapyPhaseTitle";
    public static final String APT_JOURNAL_PHASE_BEG_DATE = "therapyPhaseBegDate";
    public static final String APT_JOURNAL_PHASE_END_DATE = "therapyPhaseEndDate";
    public static final ImmutableSet<String> THERAPY_APT_CODES = ImmutableSet.of(
            APT_JOURNAL_THERAPY_TITLE,
            APT_JOURNAL_THERAPY_BEG_DATE,
            APT_JOURNAL_THERAPY_END_DATE,
            APT_JOURNAL_PHASE_TITLE,
            APT_JOURNAL_PHASE_BEG_DATE,
            APT_JOURNAL_PHASE_END_DATE
    );
    public static final ImmutableSet<String> THERAPY_AT_CODES = ImmutableSet.of("1_1_01", "1_2_18", "1_2_19", "1_2_20", "1_2_22", "1_2_23");

    public static final ImmutableSet<String> WEIGHT_AT_CODES = ImmutableSet.of(
            "1_1_32", "1_1_31", "1_1_03_1", "1_1_01", "1_2_18", "1_2_19", "1_2_20", "1_2_22", "1_2_23"
    );

    public static final String APT_WEIGHT = "WEIGHT";
    public static final ImmutableSet<String> WEIGHT_APT_CODES = ImmutableSet.of(APT_WEIGHT);

}
