package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ActionProperty_MKB")
@NamedQueries(
        {
                @NamedQuery(name = "APValueMKB.findAll",
                            query = "SELECT a FROM APValueMKB a"),
                @NamedQuery(name = "APValueMKB.findById",
                            query = "SELECT a FROM APValueMKB a WHERE a.id.id = :id")
        })
public class APValueMKB extends AbstractAPValue implements Serializable, APValue {
    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer mkbId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private Mkb mkb;

    public APValueMKB() {
    }

    public APValueMKB(IndexedId id) {
        this.id = id;
    }

    public APValueMKB(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    public Mkb getMkb() {
        return mkb;
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
        if (!(object instanceof APValueMKB)) {
            return false;
        }
        APValueMKB other = (APValueMKB) object;

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
        return "ru.korus.tmis.core.entity.model.APValueMKB[ id=" + id + " ]";
    }

    @Override
    public Object getValue() {
        return mkb;
    }

    @Override
    public String getValueAsString() {
        if (mkb != null) {
            return mkb.getDiagID() + " " + mkb.getDiagName();
        } else {
            return "";
        }
    }

    @Override
    public String getValueAsId() {
        return mkbId != null ? Integer.toString(mkbId) : "";
    }

    @Override
    public boolean setValueFromString(String value)
            throws CoreException {
        if ("".equals(value)) {
            return true;
        }

        try {
            this.mkbId = Integer.valueOf(value);
            return true;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106, // TODO: Fix me!
                    "Не могу установить " +
                            this.getClass().getSimpleName() +
                            " в значение <" + value + ">"
            );
        }
    }
}
