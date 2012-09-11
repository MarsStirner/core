package ru.korus.tmis.core.entity.model;

public enum ActionStatus {
    STARTED((short) 0, "Начато"),
    WAITING((short) 1, "Ожидание"),
    FINISHED((short) 2, "Закончено"),
    CANCELLED((short) 3, "Отменено"),
    NORESULT((short) 4, "Без результата");

    /**
     * Строковое имя статуса
     */
    private final String name;

    /**
     * Код статуса
     */
    private final short code;

    public static ActionStatus fromString(final String code) {
        for (ActionStatus s : ActionStatus.values()) {
            if (code.equals(Short.toString(s.code))) {
                return s;
            }
        }
        return null;
    }

    public static ActionStatus fromShort(final short code) {
        for (ActionStatus s : ActionStatus.values()) {
            if (code == s.code) {
                return s;
            }
        }
        return null;
    }

    private ActionStatus(final short code, final String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return Integer.toString(code);
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
