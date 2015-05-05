package ru.korus.tmis.vmp.model;

import ru.korus.tmis.vmp.entities.s11r64.ReferenceBook;

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

    public IdCodeName(ReferenceBook referenceBook) {
        id = referenceBook.getId();
        code = referenceBook.getCode();
        name = referenceBook.getName();
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
