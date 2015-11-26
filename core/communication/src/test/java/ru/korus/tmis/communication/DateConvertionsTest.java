package ru.korus.tmis.communication;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.korus.tmis.schedule.DateConvertions;

import java.util.Date;

import static org.testng.Assert.assertTrue;

/**
 * Author: Upatov Egor <br>
 * Date: 18.06.2014, 14:04 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class DateConvertionsTest {
    final static Logger logger = LoggerFactory.getLogger(DateConvertionsTest.class);

    @Test
    public void testJavaUtilDateConvertions() {
        final Date currentDateTime = new Date();
        final long currentMillisDate = DateConvertions.convertDateToUTCMilliseconds(currentDateTime);
        logger.info("Test " + currentDateTime.getClass().getCanonicalName() + ": " + currentDateTime.toString() + " = " + currentMillisDate);
        assertTrue(currentDateTime.equals(DateConvertions.convertUTCMillisecondsToDate(currentMillisDate)));
        assertTrue(currentMillisDate == DateConvertions.convertDateToUTCMilliseconds(currentDateTime));
    }

    @Test
    public void testJodaTimeLocalDateConvertions() {
        final LocalDate localDate = new LocalDate();
        final long currentMillisDate = DateConvertions.convertLocalDateToUTCMilliseconds(localDate);
        logger.info("Test " + localDate.getClass().getCanonicalName() + ": " + localDate.toString() + " = " + currentMillisDate);
        assertTrue(localDate.equals(DateConvertions.convertUTCMillisecondsToLocalDate(currentMillisDate)));
        assertTrue(currentMillisDate == DateConvertions.convertLocalDateToUTCMilliseconds(localDate));
    }

    @Test
    public void testJodaTimeLocalTimeConvertions() {
        final LocalTime localtime = new LocalTime();
        final long currentMillisDate = DateConvertions.convertLocalTimeToUTCMilliseconds(localtime);
        logger.info("Test " + localtime.getClass().getCanonicalName() + ": " + localtime.toString() + " = " + currentMillisDate);
        assertTrue(localtime.equals(DateConvertions.convertUTCMillisecondsToLocalTime(currentMillisDate)));
        assertTrue(currentMillisDate == DateConvertions.convertLocalTimeToUTCMilliseconds(localtime));
    }

    @Test
    public void testJodaTimeLocalDateTimeConvertions() {
        final LocalDateTime localdatetime = new LocalDateTime();
        final long currentMillisDate = DateConvertions.convertLocalDateTimeToUTCMilliseconds(localdatetime);
        logger.info("Test " + localdatetime.getClass().getCanonicalName() + ": " + localdatetime.toString() + " = " + currentMillisDate);
        assertTrue(localdatetime.equals(DateConvertions.convertUTCMillisecondsToLocalDateTime(currentMillisDate)));
        assertTrue(currentMillisDate == DateConvertions.convertLocalDateTimeToUTCMilliseconds(localdatetime));
    }

}
