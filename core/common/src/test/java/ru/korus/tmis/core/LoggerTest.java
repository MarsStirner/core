package ru.korus.tmis.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @Author: Dmitriy E. Nosov <br>
 * @Date: 14.12.12, 20:22 <br>
 * @Company: Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * @Description: <br>
 */
public class LoggerTest {

    final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);



    @Test(enabled = false)
    public void test() throws Exception {
        for (int i = 0; i < 1000; i++) {
            logger.info("iiiiiiiiiiiiiiiiiiiiiiiiiii " + Math.random());
//            logger.error("eeeeeeeeeeeeeeeeeeeeeeeeee " + Math.random());
//            logger.warn("wwwwwwwwwwwwwwwwwwwwwwww " + Math.random());
            logger.debug("ddddddddddddddddddddd " + Math.random());
//            System.out.println("i = " + i);
//            final IllegalArgumentException e = new IllegalArgumentException("r");
//            logger.error("exception", e);

        }
    }

}
