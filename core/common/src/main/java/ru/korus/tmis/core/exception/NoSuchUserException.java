package ru.korus.tmis.core.exception;

/**
 * Класс исключения, возникающего при отсутствии пользователя или при
 * несовпадении логина и пароля.
 */
public class NoSuchUserException extends CoreException {
    final String userLogin;

    public String getUserLogin() {
        return userLogin;
    }

    public NoSuchUserException(final int errorCode,
                               final String userLogin,
                               final String message) {
        super(errorCode, message);
        this.userLogin = userLogin;
    }
}
