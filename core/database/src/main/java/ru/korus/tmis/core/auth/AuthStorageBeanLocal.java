package ru.korus.tmis.core.auth;

import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.NoSuchUserException;

import java.util.Date;
import java.util.Set;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

@Local
public interface AuthStorageBeanLocal {

    /**
     * Получить список возможных ролей пользователя.
     *
     * @param login
     * @param password
     *
     * @return список ролей для пользователя
     *
     * @throws NoSuchUserException
     */
    Set<Role> getRoles(String login, String password)
            throws CoreException;

    /**
     * Создать данные аутентификации для пользователя.
     *
     * @param login
     * @param password
     * @param roleId
     *
     * @return данные пользователя
     *
     * @throws NoSuchUserException
     */
    AuthData createToken(String login, String password, int roleId)
            throws CoreException;

    /**
     * Получить данные аутентификации по токену.
     *
     * @param token токен
     *
     * @return данные аутентификации
     */
    AuthData getAuthData(AuthToken token)
            throws CoreException;

    /**
     * Получить время создания токена.
     *
     * @param token токен
     *
     * @return время создания токена
     */
    Date getAuthDateTime(AuthToken token)
            throws CoreException;

    AuthData checkTokenCookies(HttpServletRequest srvletRequest)
           throws CoreException;//AuthenticationException

    void timeoutHandler();
}
