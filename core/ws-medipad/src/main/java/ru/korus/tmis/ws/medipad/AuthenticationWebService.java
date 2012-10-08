package ru.korus.tmis.ws.medipad;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.RoleData;
import ru.korus.tmis.core.exception.AuthenticationException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.Serializable;

@WebService(
        targetNamespace = "http://korus.ru/tmis/authentication",
        name = "authentication"
)
public interface AuthenticationWebService extends Serializable {
    @WebMethod
    RoleData getRoles(@WebParam(name = "userName") String login,
                      @WebParam(name = "password") String password)
            throws AuthenticationException;

    @WebMethod
    AuthData authenticate(@WebParam(name = "userName") String userName,
                          @WebParam(name = "password") String password,
                          @WebParam(name = "roleId") int roleId)
            throws AuthenticationException;
}
