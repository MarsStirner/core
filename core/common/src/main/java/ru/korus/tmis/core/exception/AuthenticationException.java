package ru.korus.tmis.core.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "ExceptionFault",
          faultBean = "ru.korus.tmis.core.exception.FaultBean")
public class AuthenticationException extends CoreException {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(final int id) {
        super(id);
    }

    public AuthenticationException(final String message) {
        super(message);
    }

    public AuthenticationException(final int id,
                                   final String message) {
        super(id, message);
    }
}
