package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.exception.CoreException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 13:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    public static final String AUTH_SESSION = "AUTH_SESSION";

    @Autowired
    AuthStorageBeanLocal authStorageBeanLocal;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        AuthData authData = null;

        try {
            authData = authStorageBeanLocal.checkTokenCookies(Arrays.asList(request.getCookies()));
        } catch (CoreException ex) {
        }

        request.getSession().setAttribute(
                AUTH_SESSION, authData != null);

        return super.preHandle(request, response, handler);
    }

}
