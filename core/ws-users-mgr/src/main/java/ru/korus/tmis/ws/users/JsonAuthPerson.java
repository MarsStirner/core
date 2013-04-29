package ru.korus.tmis.ws.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.04.13, 13:06 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonAuthPerson {

    @XmlElement(name = "login")
    private String login;

    @XmlElement(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
