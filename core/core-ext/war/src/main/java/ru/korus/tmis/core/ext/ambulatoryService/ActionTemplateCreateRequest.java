package ru.korus.tmis.core.ext.ambulatoryService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Author: Upatov Egor <br>
 * Date: 29.10.2015, 13:52 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description:
 * json={
 * "user_id": int,           // id текущего пользователя
 * "speciality_id": int,   // id специальности текущего пользователя
 * "aid": int,                 // id action, на основе которого будет создаваться шаблон
 * "gid": int,                 // ActionTempalte.group_id
 * "name": str,             // .name
 * "code": str,              // .code
 * "owner": int,            // Person.id хозяин шаблона, может совпадать с user_id
 * "speciality": int,       // rbSpeciality.id, для которых доступен шаблон, может совпадать с speciality_id
 * }
 * ни одно из полей не является обязательным
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTemplateCreateRequest {
    // id текущего пользователя
    private Integer user_id;
    // id специальности текущего пользователя
    private Integer speciality_id;
    // id action, на основе которого будет создаваться шаблон
    private Integer aid;
    // ActionTemplate.group_id
    private Integer gid;
    // ActionTemplate.name
    private String name;
    // ActionTemplate.code
    private String code;
    // Person.id хозяин шаблона, может совпадать с user_id
    private Integer owner;
    // rbSpeciality.id, для которых доступен шаблон, может совпадать с speciality_id
    private Integer speciality;

    public ActionTemplateCreateRequest() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(final Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSpeciality_id() {
        return speciality_id;
    }

    public void setSpeciality_id(final Integer speciality_id) {
        this.speciality_id = speciality_id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(final Integer aid) {
        this.aid = aid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(final Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(final Integer owner) {
        this.owner = owner;
    }

    public Integer getSpeciality() {
        return speciality;
    }

    public void setSpeciality(final Integer speciality) {
        this.speciality = speciality;
    }
}
