package ru.korus.tmis.ws.users;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2013, 9:45:49 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@XmlRootElement
public class JsonRole {
    private String code;
    private String title;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
