package ru.korus.tmis.ws.webmis.rest.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.korus.tmis.util.reflect.LoggingManager;
import ru.korus.tmis.util.reflect.LoggingManager$;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 10/29/12
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */

@WebFilter("/rest/*")
public class AuthenticationFilter implements Filter {

    private static final AtomicLong requestCount = new AtomicLong(0L);

    @Override
    public void init(FilterConfig config) {

    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(true);

            String url = request.getRequestURL().toString(); //URL
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("user-agent");

            LoggingManager.setValueForKey(LoggingManager$.MODULE$.getFirstCall(), "111", LoggingManager$.MODULE$.getOkStatus());
            LoggingManager.setValueForKey(LoggingManager$.MODULE$.getURL(),url, LoggingManager$.MODULE$.getOkStatus());
            LoggingManager.setValueForKey(LoggingManager$.MODULE$.getIP(),ip, LoggingManager$.MODULE$.getOkStatus());
            LoggingManager.setValueForKey(LoggingManager$.MODULE$.getUserAgent(), userAgent, LoggingManager$.MODULE$.getOkStatus());

            chain.doFilter(req, res); // Logged-in user found, so just continue request.


        } finally {
            //MDC.remove("userName");
        }

    }
    @Override
    public void destroy(){

    }

}
