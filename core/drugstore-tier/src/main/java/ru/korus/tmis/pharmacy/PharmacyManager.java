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
public interface PharmacyManager {
    /**
     * Произошло событие, создан Action
     */
    void eventActionCreated(int actionId);

}
