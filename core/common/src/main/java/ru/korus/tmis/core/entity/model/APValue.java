package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

public interface APValue {

    Object getValue();

    String getValueAsString();

    String getValueAsId();

    boolean setValueFromString(String value)
            throws CoreException;

    APValue unwrap();

    void linkToActionProperty(ActionProperty ap);
}
