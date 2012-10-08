package ru.korus.tmis.core.entity.model.fd;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FDFieldValueId implements java.io.Serializable {

    private FDRecord fdRecord;
    private FDField fdField;

    @ManyToOne
    public FDRecord getFDRecord() {
        return fdRecord;
    }

    public void setFDRecord(FDRecord fdRecord) {
        this.fdRecord = fdRecord;
    }

    @ManyToOne
    public FDField getFDField() {
        return fdField;
    }

    public void setFDField(FDField fdField) {
        this.fdField = fdField;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FDFieldValueId that = (FDFieldValueId) o;

        if (fdRecord != null ? !fdRecord.equals(that.fdRecord) : that.fdRecord != null) return false;
        if (fdField != null ? !fdField.equals(that.fdField) : that.fdField != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result += (fdRecord != null ? fdRecord.hashCode() : 0);
        result += (fdField != null ? fdField.hashCode() : 0);
        return result;
    }

}
