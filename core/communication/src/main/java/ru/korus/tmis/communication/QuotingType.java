package ru.korus.tmis.communication;

/**
 * User: eupatov<br>
 * Date: 28.01.13 at 12:50<br>
 * Company Korus Consulting IT<br>
 */
public enum QuotingType {
    /**
     * из регистратуры
     */
    FROM_REGISTRY(1),
    /**
     * повторная запись врачем
     */
    SECOND_VISIT(2),
    /**
     * меж-кабинетная запись
     */
    BETWEEN_CABINET(3),
    /**
     * другое ЛПУ
     */
    FROM_OTHER_LPU(4),
    /**
     * с портала
     */
    FROM_PORTAL(5);

    private QuotingType(final int id) {
        this.id = id;
    }

    private int id;

    public int getValue() {
        return id;
    }
}
