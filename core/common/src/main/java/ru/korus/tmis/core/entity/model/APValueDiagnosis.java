package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        31.03.2015, 20:05 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Entity
@Table(name = "ActionProperty_Diagnosis")
public class APValueDiagnosis extends AbstractAPValue implements Serializable, APValue {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "value")
    private Integer value;


    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }

    @Override
    public boolean setValueFromString(final String value)
            throws CoreException {
        if ("".equals(value)) {
            this.value = 0;
            return true;
        }

        try {
            this.value = TextUtils.getRobustInt(value);
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