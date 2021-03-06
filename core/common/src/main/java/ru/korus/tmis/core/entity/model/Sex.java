package ru.korus.tmis.core.entity.model;

public enum Sex {
    UNDEFINED((short) 0),
    MEN((short) 1),
    WOMEN((short) 2);

    private short code;

    private Sex(short code) {
        this.code = code;
    }

    public static Sex valueOf(short code) {
        for (Sex g : values()) {
            if (g.code == code) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if(this == UNDEFINED)
            return "Любой";
        else if (this == MEN)
            return "Мужской";
        else if (this == WOMEN)
            return "Женский";
        else
            throw new IllegalStateException("Unknown state of Sex instance");
    }
}
