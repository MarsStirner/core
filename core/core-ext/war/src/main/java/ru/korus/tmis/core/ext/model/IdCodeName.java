package ru.korus.tmis.core.ext.model;

import ru.korus.tmis.core.ext.entities.s11r64.ReferenceBook;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.QuotaType;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:06 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class IdCodeName {

    private Integer id;

    private String code;

    private String name;


    public IdCodeName() {

    }

    public IdCodeName(ReferenceBook referenceBook, Boolean isAddCode) {
        id = referenceBook.getId();
        code = referenceBook.getCode();
        name =  (isAddCode ? (code + " ") : "") + referenceBook.getName();
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
