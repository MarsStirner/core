package ru.korus.tmis.core.entity.model.pharmacy;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.01.14, 12:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum PrescriptionStatus {
    /**
     * Назначен/Не исполнен (ещё или уже)
     */
    PS_NEW, // 0

    /**
     * исполнен
     */
    PS_FINISHED, // 1

    /**
     * Отменён
     */
    PS_CANCELED,// = 2;

    /**
     * Пауза
     */
    PS_PAUSE; // = 3;

}
