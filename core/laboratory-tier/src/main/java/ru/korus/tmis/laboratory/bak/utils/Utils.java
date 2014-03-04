package ru.korus.tmis.laboratory.bak.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 11:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static String marshallMessage(final Object msg, final String contextPath) {
        final StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(msg, writer);
        } catch (Exception e) {
            logger.error("jaxb exception", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                //skip
            }
        }
        return writer.toString();
    }

}
