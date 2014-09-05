package ru.korus.tmis.admin.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

@Controller
@Scope("session")
public class RootController implements Serializable {

    public static final String VIEW_STATE = "ViewState";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootDispatcher(Map<String, Object> model, HttpServletRequest request) {

        Object auth = request.getSession().getAttribute(AuthInterceptor.AUTH_SESSION);

        Object flowState = request.getSession().getAttribute(VIEW_STATE);

        final ViewState curFlowState = (flowState instanceof ViewState
                && auth instanceof Boolean
                && (Boolean) auth) ? (ViewState) flowState : ViewState.AUTH;

        return curFlowState.redirect();
    }

}
