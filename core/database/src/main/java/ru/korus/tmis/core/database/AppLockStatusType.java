package ru.korus.tmis.core.database;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.06.14, 11:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum AppLockStatusType {
    /**
     * хранимая процедура GetAppLock_ занята другим процессом
     */
    busy,

    /**
     * Запись уже залочена другим mysql клиентом
     */
    alreadyLocked,

    /**
     * Запись залочена успешно
     */
    lock
}
