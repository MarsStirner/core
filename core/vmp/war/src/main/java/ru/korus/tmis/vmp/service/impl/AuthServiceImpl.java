package ru.korus.tmis.vmp.service.impl;

import org.springframework.stereotype.Service;
import ru.korus.tmis.vmp.model.AuthData;
import ru.korus.tmis.vmp.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.04.2015, 15:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthData getAuthData(HttpServletRequest request) {
        AuthData res = new AuthData();
        for(Cookie c : request.getCookies()) {
            if("userId".equals(c.getName()) && c.getValue() != null) {
                try {
                    res.setUserId(Integer.parseInt(c.getValue()));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return res;
    }
}
