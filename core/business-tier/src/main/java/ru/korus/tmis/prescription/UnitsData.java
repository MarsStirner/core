package ru.korus.tmis.prescription;

import ru.korus.tmis.core.entity.model.RbUnit;

import javax.xml.bind.annotation.XmlType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 12:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@XmlType
public class UnitsData {

    private Integer id;

    /**
     * Наименования ед. измерения RbUnit.name
     */
    private String code;

    public UnitsData(RbUnit rbUnit) {
        id = rbUnit.getId();
        code = rbUnit.getName();
    }

    public UnitsData() {
        id = null;
        code = null;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
