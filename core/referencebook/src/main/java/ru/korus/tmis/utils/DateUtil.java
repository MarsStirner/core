package ru.korus.tmis.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 16:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public final class DateUtil {

    public static Date getDate(final XMLGregorianCalendar gc) {
        return gc != null ? gc.toGregorianCalendar().getTime() : null;
    }

    private DateUtil() {
    }
}
