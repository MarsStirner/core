package ru.korus.tmis.core.entity.model;

import java.io.Serializable;

public class APValueRLSWrapper implements Serializable, APValue {

    private static final long serialVersionUID = 1L;
    protected APValueRLS rlsValue;
    protected final Nomenclature nomenclature;

    public APValueRLSWrapper(final APValueRLS rlsValue,
                             final Nomenclature nomenclature) {
        this.rlsValue = rlsValue;
        this.nomenclature = nomenclature;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    @Override
    public Object getValue() {
        return nomenclature;
    }

    @Override
    public String getValueAsString() {
        return nomenclature.getTradeName();
    }

    @Override
    public String getValueAsId() {
        return Integer.toString(nomenclature.getId());
    }

    @Override
    public boolean setValueFromString(String value) {
        return false;
    }

    @Override
    public void linkToActionProperty(ActionProperty ap, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public APValue unwrap() {
        return rlsValue;
    }
}
