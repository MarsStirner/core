package ru.korus.tmis.admin.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.korus.tmis.admin.model.User;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "admin")
@Scope("session")
public class AdminController implements Serializable {

    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(@ModelAttribute("userForm") User user, Map<String, Object> model) {
        model.put("userForm", user);
        return "admin";
    }

}
