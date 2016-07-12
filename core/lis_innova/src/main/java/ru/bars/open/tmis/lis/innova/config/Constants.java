package ru.bars.open.tmis.lis.innova.config;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 16.05.2016, 17:19 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class Constants {

    public static final String DIAG_CODE_diagReceivedMkb = "diagReceivedMkb";
    public static final String DIAG_CODE_admissionMkb = "admissionMkb";
    public static final String DIAG_CODE_finalMkb = "finalMkb";
    public static final String DIAG_CODE_aftereffectMkb = "aftereffectMkb";
    public static final String DIAG_CODE_attendantMkb = "attendantMkb";

    public static final Set<String> DIAGNOSIS_CODES_FOR_SEND = ImmutableSet.of(
            DIAG_CODE_diagReceivedMkb,
            DIAG_CODE_admissionMkb,
            DIAG_CODE_finalMkb,
            DIAG_CODE_aftereffectMkb,
            DIAG_CODE_attendantMkb
    );
}
