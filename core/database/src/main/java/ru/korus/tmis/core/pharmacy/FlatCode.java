package ru.korus.tmis.core.pharmacy;

import java.util.Arrays;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.12.12, 18:06 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum FlatCode {
    /**
     * Поступление в стационар
     */
    RECEIVED("received"),
    /**
     * Выписка из стационара
     */
    LEAVED("leaved"),
    /**
     * Отмена госпитализации в стационар
     */
    DEL_RECEIVED("del_received"),
    /**
     * Движение между отделениями внутри стационара
     */
    MOVING("moving"),
    /**
     * Отмена движения между отделениями внутри стационара
     */
    DEL_MOVING("del_moving"),
    /**
     * Назначение пациенту
     */
    PRESCRIPTION("prescription"),
    /**
     * Назначение пациенту
     */
    CHEMOTHERAPY("chemotherapy"),
    /**
     * Назначение пациенту
     */
    INFUSION("infusion"),
    /**
     * Назначение пациенту
     */
    ANALGESIA("analgesia"),
    /**
     * Исполнение назначения пациенту
     */
    RELEASE_PRESCRIPTION("release_prescription");

    private String code;

    private FlatCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static List<String> getPrescriptionCodeList() {
        return Arrays.asList(PRESCRIPTION.getCode(), CHEMOTHERAPY.getCode(), INFUSION.getCode(), ANALGESIA.getCode());
    }
}