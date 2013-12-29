package ru.rorus.tmis.schedule;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.12.13, 20:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum TypeOfQuota {
    FROM_REGISTRY(1),
    SECOND_VISIT(2),
    BETWEEN_CABINET(3),
    FROM_OTHER_LPU(4),
    FROM_PORTAL(5);

    private final int value;

    private TypeOfQuota(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}