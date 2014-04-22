package ru.korus.tmis.core.transmit;

import ru.korus.tmis.core.exception.CoreException;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.14, 17:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface Sender {
    void  sendEntity(Object entity) throws CoreException;
}
