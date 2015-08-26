package ru.korus.tmis.core.ext.service;

import ru.korus.tmis.core.ext.model.AuthData;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.04.2015, 15:06 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface AuthService {

    AuthData getAuthData(HttpServletRequest request);
}
