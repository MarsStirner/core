package ru.korus.tmis.pharmacy;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:28 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */

@Local
public interface PharmacyBeanLocal {
    /**
     * Произошло событие, создан Action
     * @param actionId идентификатор события
     */
    void eventActionCreated(int actionId);

}
