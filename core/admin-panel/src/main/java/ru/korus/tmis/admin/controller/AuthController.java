package ru.korus.tmis.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.User;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.TextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "auth")
@Scope("session")
public class AuthController implements Serializable {

    @Autowired
    AuthStorageBeanLocal authStorageBeanLocal;

    private String displayError = "none";

    private String displayErrorMsg = "tst";

    private User userForm = new User("", "");

    @RequestMapping(method = RequestMethod.GET)
    public String viewAuth(Map<String, Object> model) {
        model.put("displayError", displayError);
        model.put("displayErrorMsg", displayErrorMsg);
        model.put("userForm", userForm);

        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processAuth(@ModelAttribute("userForm") User user,
                              Map<String, Object> model,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        displayError = "yes";
        userForm = user;
        Cookie authCooke = new Cookie("authToken", "");
        authCooke.setMaxAge(0);
        response.addCookie(authCooke);
        request.getSession().setAttribute(AuthInterceptor.AUTH_SESSION, false);
        try {
            AuthData authData = authStorageBeanLocal.createToken(user.getUsername(), TextUtils.getMD5(user.getPassword()), 1);
            request.getSession().setAttribute(RootController.VIEW_STATE, ViewState.MAIN);
            response.addCookie(new Cookie("authToken", authData.authToken().id()));
            request.getSession().setAttribute(AuthInterceptor.AUTH_SESSION, true);
            displayError = "none";
        } catch (CoreException e) {
            displayErrorMsg = e.getMessage();
        } catch (Exception e) {
            displayErrorMsg = "Internal server error";
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return ViewState.ROOT.redirect();
    }

}
