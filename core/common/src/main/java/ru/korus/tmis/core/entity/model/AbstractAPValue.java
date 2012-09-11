package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAPValue implements APValue {
    @EmbeddedId
    protected IndexedId id;

    @Override
    public abstract Object getValue();

    @Override
    public abstract String getValueAsString();

    @Override
    public String getValueAsId() {
        return "";
    }

    @Override
    public abstract boolean setValueFromString(String value)
            throws CoreException;

    @Override
    public APValue unwrap() {
        return this;
    }

    @Override
    public void linkToActionProperty(ActionProperty ap) {
        this.setId(new IndexedId(ap.getId(), 0));
    }

    public IndexedId getId() {
        return id;
    }

    public void setId(IndexedId id) {
        this.id = id;
    }
}
