package ru.korus.tmis.core.transmit;

import java.sql.Timestamp;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.14, 13:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface Transmittable {
    Integer getId();

    int getErrCount();

    void setErrCount(int i);

    Timestamp getSendTime();

    void setSendTime(Timestamp timestamp);

    void setInfo(String message);
}
