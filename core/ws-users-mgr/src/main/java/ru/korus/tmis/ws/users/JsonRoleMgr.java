package ru.korus.tmis.ws.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2013, 12:03:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JsonRoleMgr {

    @XmlElement(name = "role_code")
    private String roleCode;

    @XmlElement(name = "person_uuid")
    private String personUuid;

    /**
     * @return the roleCode
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * @param roleCode
     *            the roleCode to set
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return the personUuid
     */
    public String getPersonUuid() {
        return personUuid;
    }

    /**
     * @param personUuid
     *            the personUuid to set
     */
    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

}
