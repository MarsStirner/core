package ru.korus.tmis.core.auth;

import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Staff;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.04.13, 15:10 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

/**
 * Персональные данные пользователя
 */
@XmlRootElement
public class JsonPerson {
    /**
     * Имя
     */
    private String fname;

    /**
     * Отчество
     */
    private String pname;

    /**
     * Фамилия
     */
    private String lname;

    /**
     * Должность
     */
    private String position;

    /**
     * Код должность
     */
    private String code;

    /**
     * (UUID) подразделения (элемента организационной структуры, к которой привязан сотрудник)
     */
    private String subdivision;

    /**
     * Роли, присваиваемые пользователю. Если параметр не указан, присвоить роль guest
     */
    private List<String> roles;

    /**
     * Имя пользователя для входа в МИС
     */
    private String login;

    /**
     * Md5-хэш пароля для входа в МИС
     */
    private String password;

    static public JsonPerson create(Staff staff) {
        JsonPerson res = new JsonPerson();
        res.setFname(staff.getFirstName());
        res.setPname(staff.getPatrName());
        res.setLname(staff.getLastName());
        if (staff.getPost() != null) {
            res.setPosition(staff.getPost().getName());
            res.setCode(staff.getPost().getCode());
        }
        if (staff.getOrgStructure() != null) {
            res.setSubdivision(staff.getOrgStructure().getUuid().toString());
        }
        res.setLogin(staff.getLogin());
        List<String> roleCodes = new LinkedList<String>();
        for (Role role : staff.getRoles()) {
            roleCodes.add(role.getCode());
        }
        res.setRoles(roleCodes);
        return res;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
