package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_Integer", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueAnalysisStatus.findAll", query = "SELECT a FROM APValueAnalysisStatus a")
        })
@XmlType(name = "actionPropertyValueRbAnalysisStatus")
@XmlRootElement(name = "actionPropertyValueRbAnalysisStatus")
public class APValueAnalysisStatus extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "value")
    private RbAnalysisStatus value;

    public APValueAnalysisStatus() {
    }

    public APValueAnalysisStatus(final IndexedId id) {
        this.id = id;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.getStatusName();
    }

    @Override
    public boolean setValueFromString(final String value)
            throws CoreException {
        RbAnalysisStatus ras;
        try {
            ras = RbAnalysisStatus.getById(TextUtils.getRobustInt(value));
            if (ras != null) {
                this.value = ras;
            } else {
                throw new CoreException(
                        0x0106, // TODO: Fix me!
                        "Не могу установить " +
                                this.getClass().getSimpleName() +
                                " в значение <" + value + ">"
                );
            }
        } catch (NumberFormatException ex) {
            ras = RbAnalysisStatus.getByName(value);
            if (ras != null) {
                this.value = ras;
            } else {
                throw new CoreException(
                        0x0106, // TODO: Fix me!
                        "Не могу установить " +
                                this.getClass().getSimpleName() +
                                " в значение <" + value + ">"
                );
            }
        }
        return true;
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
        if (!(object instanceof APValueAnalysisStatus)) {
            return false;
        }
        APValueAnalysisStatus other = (APValueAnalysisStatus) object;

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
        return "ru.korus.tmis.core.entity.model.APValueAnalysisStatus[id=" + id + "]";
    }
}
