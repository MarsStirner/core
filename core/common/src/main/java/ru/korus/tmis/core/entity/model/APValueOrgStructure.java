package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionProperty_OrgStructure")
@NamedQueries(
        {
                @NamedQuery(name = "APValueOrgStructure.findAll", query = "SELECT a FROM APValueOrgStructure a")
        })
@XmlType(name = "actionPropertyValueOrgStructure")
@XmlRootElement(name = "actionPropertyValueOrgStructure")
public class APValueOrgStructure extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer orgId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private OrgStructure value;

    public APValueOrgStructure() {
    }

    public APValueOrgStructure(IndexedId id) {
        this.id = id;
    }

    public APValueOrgStructure(int id, int index) {
        this.id = new IndexedId(id, index);
    }

    @Override
    public OrgStructure getValue() {
        return value;
    }

   /* public void setValue(OrgStructure value) {
        this.value = value;
    }*/

    @Override
    public String getValueAsString() {
        return value != null ? value.getName() : null;
    }

    @Override
    public String getValueAsId() {
        return orgId != null ? Integer.toString(orgId) : "";
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
        if ("".equals(value)) {
            return true;
        }

        try {
            this.orgId = TextUtils.getRobustInt(value);
            return true;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106,
                    "Не могу установить " + this.getClass().getSimpleName() + " в значение <" + value + ">"
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
        if (!(object instanceof APValueOrgStructure)) {
            return false;
        }
        APValueOrgStructure other = (APValueOrgStructure) object;

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
        return "ru.korus.tmis.core.entity.model.APValueOrgStructure[id=" + id + "]";
    }
}
