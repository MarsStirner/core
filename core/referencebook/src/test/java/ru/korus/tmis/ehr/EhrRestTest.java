package ru.korus.tmis.ehr;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.korus.tmis.ehr.ws.DocumentQuery;
import ru.korus.tmis.ehr.ws.PatientQuery;
import ru.korus.tmis.ehr.ws.RetrieveDocumentQuery;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        24.02.14, 11:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class EhrRestTest {
    @Test
    public void patientQueryJson() {
        PatientQuery res = EhrRest.toEhrPatientQuery(
                "{\"headers\": {" +
                        "\"BinarySecurityToken\": \"UmVlOWVldGk=\"}, " +
                 "\"data\": {" +
                        "\"facilityCode\": \"1.2.643.5.1.13.3.25.58.57\", " +
                        "\"initiatedBy\": {" +
                            "\"specialty\": {\"codingSystem\": \"1.2.643.5.1.13.2.1.1.181\", \"code\": \"1135\", \"name\": \"\\u0414\\u0435\\u0442\\u0441\\u043a\\u0430\\u044f \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\\u0438\\u044f\"}, " +
                            "\"role\": {\"codingSystem\": \"1.2.643.5.1.13.2.1.1.607\", \"code\": \"23\", \"name\": \"\\u0432\\u0440\\u0430\\u0447 - \\u0434\\u0435\\u0442\\u0441\\u043a\\u0438\\u0439 \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\"}, \"snils\": \"128-971-396 05 \", \"familyName\": \"\\u0423\\u0448\\u0430\\u043a\\u043e\\u0432\", \"givenName\": \"\\u0415\\u0432\\u0433\\u0435\\u043d\\u0438\\u0439\", \"middleName\": \"\\u0410\\u043d\\u0434\\u0440\\u0435\\u0435\\u0432\\u0438\\u0447\"}, " +
                            "\"params\": {\"gender\": \"M\", \"dobLow\": \"1950-01-01\", \"dobHigh\": \"1950-01-01\", \"mrn\": {\"root\": \"1.2.643.5.1.13.3.25.58.57\", \"extension\": \"1\"}, \"familyName\": \"1\", \"givenName\": \"1\", \"middleName\": \"1\", \"snils\": \"128-971-396 05 \", \"omsPolicy\": {\"series\": \"1\", \"type\": \"\\u041e\\u041c\\u0421\", \"number\": \"1\"}, \"identityDocument\": {\"series\": \"1\", \"type\": \"01\", \"number\": \"1\"}}}}");
        Assert.assertNotNull(res.getParams());
        Assert.assertEquals("1.2.643.5.1.13.3.25.58.57", res.getFacilityCode());
        Assert.assertNotNull(res.getInitiatedBy());
        Assert.assertEquals("Детская хирургия", res.getInitiatedBy().getName());
        Assert.assertEquals("1", res.getParams().getFamilyName());
        //TODO add more assertions!
    }

    @Test
    public void documentQueryJson() {
        DocumentQuery res = EhrRest.toEhrDocumentQuery(
                "{\"headers\": {\n" +
                        "    \"BinarySecurityToken\": \"UmVlOWVldGk=\"\n" +
                        "}," +
                        " \"data\": {\n" +
                        "    \"facilityCode\": \"1.2.643.5.1.13.3.25.58.47\",\n" +
                        "    \"initiatedBy\": {\n" +
                        "        \"specialty\": {\n" +
                        "            \"codingSystem\": \"1.2.643.5.1.13.2.1.1.181\",\n" +
                        "            \"code\": \"1135\",\n" +
                        "            \"name\": \"\\u0414\\u0435\\u0442\\u0441\\u043a\\u0430\\u044f \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\\u0438\\u044f\"\n" +
                        "        },\n" +
                        "        \"role\": {\n" +
                        "            \"codingSystem\": \"1.2.643.5.1.13.2.1.1.607\",\n" +
                        "            \"code\": \"23\",\n" +
                        "            \"name\": \"\\u0432\\u0440\\u0430\\u0447 - \\u0434\\u0435\\u0442\\u0441\\u043a\\u0438\\u0439 \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\"\n" +
                        "        },\n" +
                        "        \"snils\": \"128-971-396 05\",\n" +
                        "        \"familyName\": \"\\u0423\\u0448\\u0430\\u043a\\u043e\\u0432\",\n" +
                        "        \"givenName\": \"\\u0415\\u0432\\u0433\\u0435\\u043d\\u0438\\u0439\",\n" +
                        "        \"middleName\": \"\\u0410\\u043d\\u0434\\u0440\\u0435\\u0435\\u0432\\u0438\\u0447\"\n" +
                        "    },\n" +
                        "    \"params\": {\n" +
                        "        \"patientMRN\": {\n" +
                        "            \"root\": \"1.2.643.5.1.13.3.25.58.47\",\n" +
                        "            \"extension\": \"1\"\n" +
                        "        },\n" +
                        "        \"status\": \"SUBMITTED\",\n" +
                        "        \"docDateLow\": \"2014-02-01\",\n" +
                        "        \"docDateHigh\": \"2014-02-26\"\n" +
                        "    }\n" +
                        "}}");
        Assert.assertNotNull(res.getParams());
        Assert.assertEquals("1.2.643.5.1.13.3.25.58.47", res.getFacilityCode());
        Assert.assertNotNull(res.getInitiatedBy());
        Assert.assertEquals("Детская хирургия", res.getInitiatedBy().getName());
        Assert.assertEquals("SUBMITTED", res.getParams().getStatus());
        try {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            final XMLGregorianCalendar datalow = factory.newXMLGregorianCalendar("2014-02-01T00:00:00.000+04:00");
            Assert.assertTrue(datalow.compare(res.getParams().getDocDateLow()) == DatatypeConstants.EQUAL);
            final XMLGregorianCalendar dataHigh = factory.newXMLGregorianCalendar("2014-02-26T00:00:00.000+04:00");
            Assert.assertTrue(dataHigh.compare(res.getParams().getDocDateHigh()) == DatatypeConstants.EQUAL);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Assert.fail("cannot create DatatypeFactory");
        }

        //TODO add more assertions!
    }

    @Test
    public void retrieveDocumentQueryJson() {
        RetrieveDocumentQuery res = EhrRest.toEhrRetrieveDocumentQuery(
                "{\"headers\": {\n" +
                        "    \"BinarySecurityToken\": \"UmVlOWVldGk=\"\n" +
                        "}, \"data\": {\n" +
                        "    \"facilityCode\": \"1.2.643.5.1.13.3.25.58.47\",\n" +
                        "    \"initiatedBy\": {\n" +
                        "        \"specialty\": {\n" +
                        "            \"codingSystem\": \"1.2.643.5.1.13.2.1.1.181\",\n" +
                        "            \"code\": \"1135\",\n" +
                        "            \"name\": \"\\u0414\\u0435\\u0442\\u0441\\u043a\\u0430\\u044f \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\\u0438\\u044f\"\n" +
                        "        },\n" +
                        "        \"role\": {\n" +
                        "            \"codingSystem\": \"1.2.643.5.1.13.2.1.1.607\",\n" +
                        "            \"code\": \"23\",\n" +
                        "            \"name\": \"\\u0432\\u0440\\u0430\\u0447 - \\u0434\\u0435\\u0442\\u0441\\u043a\\u0438\\u0439 \\u0445\\u0438\\u0440\\u0443\\u0440\\u0433\"\n" +
                        "        },\n" +
                        "        \"snils\": \"128-971-396 05\",\n" +
                        "        \"familyName\": \"\\u0423\\u0448\\u0430\\u043a\\u043e\\u0432\",\n" +
                        "        \"givenName\": \"\\u0415\\u0432\\u0433\\u0435\\u043d\\u0438\\u0439\",\n" +
                        "        \"middleName\": \"\\u0410\\u043d\\u0434\\u0440\\u0435\\u0435\\u0432\\u0438\\u0447\"\n" +
                        "    },\n" +
                        "    \"params\": {\n" +
                        "        \"documentId\": \"1\"\n" +
                        "    }\n" +
                        "}}");
        Assert.assertEquals("1.2.643.5.1.13.3.25.58.47", res.getFacilityCode());
        Assert.assertNotNull(res.getInitiatedBy());
        Assert.assertEquals("Детская хирургия", res.getInitiatedBy().getName());
        Assert.assertEquals("1", res.getDocumentId());

        //TODO add more assertions!
    }
}