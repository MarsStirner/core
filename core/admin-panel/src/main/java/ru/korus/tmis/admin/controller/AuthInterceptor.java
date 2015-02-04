package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.exception.CoreException;

import javax.servlet.http.Cookie;
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
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                int countAuthToken = 0;
                for (int i = 0; i < cookies.length && countAuthToken < 2; ++i) {
                    if ("authToken".equals(cookies[i].getName())) {
                        ++countAuthToken;
                    }
                }
                if (cookies != null && countAuthToken == 1) {
                    authData = authStorageBeanLocal.checkTokenCookies(Arrays.asList(request.getCookies()));
                } else if (countAuthToken > 1) {
                    clearCooke(response, cookies);
                }
            }
        } catch (CoreException ex) {
        }

        boolean isAuth = authData != null;
        request.getSession().setAttribute(
                AUTH_SESSION, isAuth);
        String servletInfo = request.getServletPath();
        if (!isAuth &&
                !(ViewState.ROOT.getPath().equals(servletInfo) ||
                        ViewState.AUTH.getPath().equals(servletInfo))) {
            String path = serviceUrl(request, ViewState.AUTH.getPath());
            response.sendRedirect(path);
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private void clearCooke(HttpServletResponse response, Cookie[] cookies) {
        for (Cookie c : cookies) {
            c.setValue("");
            c.setPath("/");
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }

    private String serviceUrl(final HttpServletRequest request,
                              final String page) {
        final StringBuilder sb = new StringBuilder();

        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());

        final int port = request.getServerPort();

        if (port != 80) {
            sb.append(":");
            sb.append(port);
        }

        sb.append(request.getContextPath());

        sb.append(page);

        return (sb.toString());
    }


}
