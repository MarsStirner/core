package ru.korus.tmis.core.ext.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.04.2015, 15:10 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class AuthData {

    private Integer userId;
    private String authToken;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
