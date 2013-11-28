package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 11/28/13
 * Time: 6:29 PM
 */

@Entity
@Table(name = "ActionProperty_HospitalBedProfile", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "APValueHospitalBedProfile.findAll", query = "SELECT a FROM APValueHospitalBedProfile a")
        })
@XmlType(name = "actionPropertyValueHospitalBedProfile")
@XmlRootElement(name = "actionPropertyValueHospitalBedProfile")
public class APValueHospitalBedProfile extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Column(name = "value")
    private Integer bedProfileId;

    @OneToOne
    @JoinColumn(name = "value", insertable = false, updatable = false)
    private RbHospitalBedProfile value;


    @Override
    public RbHospitalBedProfile getValue() {
        return value;
    }

    public void setValue(RbHospitalBedProfile value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value.getName();
    }

    @Override
    public boolean setValueFromString(final String value) throws CoreException {
        if ("".equals(value)) {
            this.bedProfileId = null;  //TODO: Возможно будет падать!!!!?????
            return true;
        }
        try {
            this.bedProfileId = TextUtils.getRobustInt(value);
            return true;
        } catch (NumberFormatException ex) {
            throw new CoreException(
                    0x0106,
                    "Не могу установить " + this.getClass().getSimpleName() + " в значение <" + value + ">"
            );
        }
    }
}
