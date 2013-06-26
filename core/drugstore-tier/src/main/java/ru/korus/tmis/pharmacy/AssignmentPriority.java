package ru.korus.tmis.pharmacy;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        13.03.13, 19:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum AssignmentPriority {
    /**
     * Приоритет выполнения назначения - Планово
     */
    PLANED("Планово"),

    /**
     * Приоритет выполнения назначения - Срочно
     */
    QUICKLY("Срочно");

    private String value;

    private AssignmentPriority(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
