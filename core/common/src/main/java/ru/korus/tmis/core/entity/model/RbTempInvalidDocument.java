package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbTempInvalidDocument database table.
 */
@Entity
@Table(name = "rbTempInvalidDocument")
@NamedQueries(
        {
                @NamedQuery(name = "RbTempInvalidDocument.findAll", query = "SELECT r FROM RbTempInvalidDocument r"),
                @NamedQuery(name = "RbTempInvalidDocument.findByCode", query = "SELECT r FROM RbTempInvalidDocument r WHERE r.code = :code")
        })
public class RbTempInvalidDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private String name;

    private boolean type;

    public RbTempInvalidDocument() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getType() {
        return this.type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

}