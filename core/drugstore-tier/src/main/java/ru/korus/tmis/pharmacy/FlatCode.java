package ru.korus.tmis.pharmacy;

/**
 * @author Dmitriy E. Nosov <br>
 *         Date:        25.12.12, 18:06 <br>
 *         Company:     Korus Consulting IT<br>
 *         Description:  <br>
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
    DEL_MOVING("del_moving");

    private String code;

    private FlatCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}