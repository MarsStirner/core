package ru.korus.tmis.core.auth;

import ru.korus.tmis.core.database.AppLockStatus;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.AppLock;
import ru.korus.tmis.core.entity.model.AppLockDetail;
import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.NoSuchUserException;
import ru.korus.tmis.core.lock.ActionWithLockInfo;

import java.util.Date;
import java.util.Set;
import javax.ejb.Local;
import javax.servlet.http.Cookie;

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

    /**
     * Проверка аутентификации
     * @param cookies Cookies, передаваемые в запросе
     * @return Данные аутентификации пользователя
     * @throws CoreException
     */
    AuthData checkTokenCookies(Iterable<Cookie> cookies)
           throws CoreException;//AuthenticationException

    void timeoutHandler();

    /**
     * Залочить запись для редактирования
     * @param token - пользователь
     * @param tableName - имя таблицы
     * @param id - ИД записи в таблицы
     * @return - информацию о новом локе
     * @throws CoreException - если запись уже залочена
     */
    AppLockDetail getAppLock(AuthToken token, String tableName, Integer id) throws CoreException;

    /**
     * Продлить лок запись для редактирования
     * @param token - пользователь
     * @param tableName - имя таблицы
     * @param id - ИД записи в таблицы
     * @return информацию о локе
     * @throws CoreException - если запись не залочена или залочена другим пользователем
     */
    AppLockDetail prolongAppLock(AuthToken token, String tableName, Integer id);

    /**
     * Разлочить запись для редактирования
     * @param token - пользователь
     * @param tableName - имя таблицы
     * @param id - ИД записи в таблицы
     */
    void releaseAppLock(AuthToken token, String tableName, Integer id);

    /**
     * Информация о локе записи в таблице Action
     * @param action
     * @return
     */
    ActionWithLockInfo getLockInfo(Action action);

    //TODO remove after fix front-end!
    @Deprecated
    Integer acquireLock(String table,
                             int recordId,
                             int recordIndex,
                             AuthData userData)
            throws CoreException;

    //TODO remove after fix front-end!
    @Deprecated
    void releaseLock(Integer id);

}
