package ru.korus.tmis.core.ext.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 13:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

/*
    public static final String AUTH_SESSION = "AUTH_SESSION";

    @Autowired
    AuthService authService;
*/

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
/*
        boolean isAuth = authService.checkTokenCookies(request, response);
        request.getSession().setAttribute(AUTH_SESSION, true);
        String servletInfo = request.getServletPath();
        if (!isAuth &&
                !(ViewState.ROOT.getPath().equals(servletInfo) ||
                        ViewState.AUTH.getPath().equals(servletInfo))) {
            String path = serviceUrl(request, ViewState.AUTH.getPath());
            response.sendRedirect(path);
            return false;
        }
*/
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
      //  response.setContentType("application/javascript");
        return super.preHandle(request, response, handler);
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
