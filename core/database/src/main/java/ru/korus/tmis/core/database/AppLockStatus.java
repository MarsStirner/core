package ru.korus.tmis.core.database;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.06.14, 11:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class AppLockStatus {

    private final Integer id;

    private final AppLockStatusType status;

    public AppLockStatus(Integer id, AppLockStatusType status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public AppLockStatusType getStatus() {
        return status;
    }
}
