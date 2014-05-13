package ru.korus.tmis.ws.medipad;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.RoleData;
import ru.korus.tmis.core.exception.AuthenticationException;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.io.Serializable;

@Local
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
