package ru.korus.tmis.ws.users;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Staff;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.04.13, 15:10 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@XmlRootElement
public class JsonPerson {
    private String fname;
    private String pname;
    private String lname;
    private String position;
    private String subdivision;
    private List<String> roles;
    private String login;
    private String password;

    static public JsonPerson create(Staff staff) {
        JsonPerson res = new JsonPerson();
        res.setFname(staff.getFirstName());
        res.setPname(staff.getPatrName());
        res.setLname(staff.getLastName());
        if (staff.getPost() != null) {
            res.setPosition(staff.getPost().getName());
        }
        if (staff.getOrgStructure() != null) {
            res.setSubdivision(staff.getOrgStructure().getUuid().getUuid());
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
