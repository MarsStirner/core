package ru.korus.tmis.communication;

/**
 * User: eupatov<br>
 * Date: 28.01.13 at 12:50<br>
 * Company Korus Consulting IT<br>
 */
public enum QuotingType {
    FROM_REGISTRY(1),//из регистратуры
    SECOND_VISIT(2),//повторная запись врачем
    BETWEEN_CABINET(3),//меж-кабинетная запись
    FROM_OTHER_LPU(4),//другое ЛПУ
    FROM_PORTAL(5);//с портала

    private QuotingType(int id) {
        this.id = id;
    }

    private int id;

    public int getValue() {
        return id;
    }
}
