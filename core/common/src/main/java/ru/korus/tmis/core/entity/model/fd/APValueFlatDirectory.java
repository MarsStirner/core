package ru.korus.tmis.core.entity.model.fd;

import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.AbstractAPValue;
import ru.korus.tmis.core.entity.model.IndexedId;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ActionProperty_FDRecord")
@NamedQueries(
        {
                @NamedQuery(name = "APValueFlatDirectory.findAll",
                        query = "SELECT a FROM APValueFlatDirectory a"),
                @NamedQuery(name = "APValueFlatDirectory.findById",
                        query = "SELECT a FROM APValueFlatDirectory a WHERE a.id.id = :id")
        })
public class APValueFlatDirectory extends AbstractAPValue implements Serializable, APValue {
    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer fdRecordId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private FDRecord fdRecord;

    public APValueFlatDirectory() {
    }

    public APValueFlatDirectory(IndexedId id) {
        this.id = id;
    }

    public APValueFlatDirectory(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    /*public FDRecord getFDRecord() {
        return fdRecord;
    }*/

    @Override
    public FDRecord getValue() {
        return fdRecord;
    }

    public void setValue(FDRecord value) {
        this.fdRecord = value;
    }

    @Override
    public String getValueAsString() {
        if (fdRecord != null) {
            String res = fdRecord.getId().toString();
            for (FDFieldValue fdFieldValue : fdRecord.getFieldValues()) {
                if (fdFieldValue.getPk() != null
                        && fdFieldValue.getPk().getFDField() != null
                        &&  "Наименование".equals(fdFieldValue.getPk().getFDField().getName().trim())) {
                    return fdFieldValue.getValue();
                }
            }
            return res;
        } else {
            return "";
        }
    }

    @Override
    public String getValueAsId() {
        return fdRecordId != null ? Integer.toString(fdRecordId) : "";
    }

    @Override
    public boolean setValueFromString(String value)
            throws CoreException {
        if ("".equals(value)) {
            return true;
        }

        try {
            Integer res = TextUtils.getRobustInt(value);
            if (res > 0) {
                this.fdRecordId = res;
                return true;
            } else
                return false;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106, // TODO: Fix me!
                    "Не могу установить " +
                            this.getClass().getSimpleName() +
                            " в значение <" + value + ">"
            );
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof APValueFlatDirectory)) {
            return false;
        }
        APValueFlatDirectory other = (APValueFlatDirectory) object;

        if (this.id == null && other.id == null && this != other) {
            return false;
        }

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.fd.APValueFlatDirectory[ id=" + id + " ]";
    }

}
