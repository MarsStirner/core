package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbTempInvalidReason database table.
 */
@Entity
@Table(name = "rbTempInvalidReason")
@NamedQueries(
        {
                @NamedQuery(name = "RbTempInvalidReason.findAll", query = "SELECT r FROM RbTempInvalidReason r"),
                @NamedQuery(name = "RbTempInvalidReason.findByCode", query = "SELECT r FROM RbTempInvalidReason r WHERE r.code = :code")
        })
public class RbTempInvalidReason implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private boolean grouping;

    private String name;

    private int primary;

    private int prolongate;

    private String regionalCode;

    private boolean requiredDiagnosis;

    private int restriction;

    private boolean type;

    public RbTempInvalidReason() {
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

    public boolean getGrouping() {
        return this.grouping;
    }

    public void setGrouping(boolean grouping) {
        this.grouping = grouping;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrimary() {
        return this.primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getProlongate() {
        return this.prolongate;
    }

    public void setProlongate(int prolongate) {
        this.prolongate = prolongate;
    }

    public String getRegionalCode() {
        return this.regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public boolean getRequiredDiagnosis() {
        return this.requiredDiagnosis;
    }

    public void setRequiredDiagnosis(boolean requiredDiagnosis) {
        this.requiredDiagnosis = requiredDiagnosis;
    }

    public int getRestriction() {
        return this.restriction;
    }

    public void setRestriction(int restriction) {
        this.restriction = restriction;
    }

    public boolean getType() {
        return this.type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

}