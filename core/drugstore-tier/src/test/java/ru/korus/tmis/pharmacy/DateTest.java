package ru.korus.tmis.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        10.12.12, 11:17 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */
public class DateTest {

    final static Logger logger = LoggerFactory.getLogger(DateTest.class);

    @Test(enabled = false)
    public void test() {
        logger.info("!!!!!!!!!!!!");
        System.out.println("ssssss!!!!!");
        Date date = new Date(2011, 10, 11);
        Date lastDate = new Date(2012, 10, 11);
        Date res = updateLastDate(date, lastDate);

        Date date2 = new Date(2011, 10, 11);
        Date lastDate2 = null;
        Date res2 = updateLastDate(date2, lastDate2);

        Date date3 = new Date(2012, 10, 11);
        Date lastDate3 = new Date(2011, 10, 11);
        Date res3 = updateLastDate(date3, lastDate3);

        int i = 0;


    }


    public Date updateLastDate(Date date, Date lastDate) {
        if (lastDate == null) {
            return date;
        }
        return date.before(lastDate) ? lastDate : date;
    }


}
