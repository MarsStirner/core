package ru.korus.tmis.hl7;

import org.hl7.v3.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        23.10.12, 12:39 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */

public class Test2 {
    public static void main(String[] args) {


//        if (ConfigManager.Laboratory2.User != null && ConfigManager.Laboratory2.Password != null) {
//            Authenticator.setDefault(new Authenticator() {
//                override def getPasswordAuthentication(): PasswordAuthentication = {
//                    info("Authentication requested")
//                    info("host: " + getRequestingHost)
//                    info("site: " + getRequestingSite.toString)
//                    info("url: " + getRequestingURL.toString)
//
//                    return new PasswordAuthentication(ConfigManager.Laboratory2.User, ConfigManager.Laboratory2.Password.toCharArray);
//                }
//            });
//        }
        String ServiceUrl = "http://pharmacy3.fccho-moscow.ru/ws/MISExchange";
        String XmlNamespace = "urn:hl7-org:v3";
        String DefaultXsiType = "";

        String XsiNamespace = "http://www.w3.org/2001/XMLSchema-instance";


  /*      try {
//            MISExchange misExchange = new MISExchange(new URL("../resources/MISExchange.wsdl"), new QName("urn:hl7-org:v3", "MISExchangeSoap"));

            ObjectFactory objectFactory = new ObjectFactory();
            PRPAIN402001UV02 obj = objectFactory.createPRPAIN402001UV02();
            TS ts = new TS();
            ts.setValue("123");
            obj.setCreationTime(ts);

         //   misExchange.getMISExchangeSoap().processHL7V3Message(obj);


            JAXBContext jaxbContext = JAXBContext.newInstance("org.hl7.v3");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            //форматированный вывод
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(obj, System.out);

//        } catch (MalformedURLException e) {
//            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
*/

    }
}
