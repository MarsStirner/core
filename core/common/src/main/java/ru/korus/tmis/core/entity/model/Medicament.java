package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

/**
 * Класс для представления лекарств.
 */
public class Medicament implements Serializable {

    private int id;
    private String description;

    public Medicament() {
    }

    public Medicament(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
