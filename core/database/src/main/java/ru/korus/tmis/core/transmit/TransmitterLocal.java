package ru.korus.tmis.core.transmit;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.14, 17:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface TransmitterLocal {
    int MAX_RESULT = 100;

    void send(Sender sender, Class entityClass, String namedQuery);
}
