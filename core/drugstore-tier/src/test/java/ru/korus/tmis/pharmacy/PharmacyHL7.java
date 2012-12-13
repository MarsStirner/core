package ru.korus.tmis.pharmacy;

import org.hl7.v3.MCCIIN000002UV01;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.Date;
import java.util.UUID;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        08.11.12, 11:38 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */

public class PharmacyHL7 {


    private final Action action = new Action(1);
    private final Patient client = new Patient(2);
    private final Patient au = new Patient(3);
    private final Staff createPerson = new Staff(11);
    private final String externalId = "2012/4251";
    private final String externalUUID = UUID.randomUUID().toString();
    private final String orgUUID = "50c965c7-7422-11e1-b47f-005056a41f97";
    private final String orgUUID2 = "8db3a054-41c3-11e1-b38c-005056a46489";
    private final String clientUUID = UUID.randomUUID().toString();

    @BeforeSuite
    private void init() {

        action.setCreateDatetime(new Date());
        client.setBirthDate(new Date(10, 1, 1));
        client.setFirstName("Григорий");
        client.setPatrName("Петрович");
        client.setLastName("Иванов");
        client.setSnils("1122-111-222");

        au.setBirthDate(new Date(300, 9, 9));
        au.setFirstName("Сидор");
        au.setPatrName("Сидорович");
        au.setLastName("Администраторов");
        au.setSnils("99-199-999");

        createPerson.setFirstName("Доктор");
        createPerson.setPatrName("Докторович");
        createPerson.setLastName("Айболит");
    }


    @Test(enabled = true, priority = 1)
    public void processReceived() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processReceived(
                    action,
                    externalId,
                    externalUUID,
                    orgUUID,
                    client,
                    clientUUID);

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }

    }

    @Test(enabled = true, priority = 2)
    public void processMoving() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processMoving(
                    action,
                    externalUUID,
                    externalId,
                    clientUUID,
                    orgUUID,
                    orgUUID2);

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, priority = 3)
    public void processDelMoving() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processDelMoving(
                    action,
                    externalUUID,
                    externalId,
                    clientUUID,
                    orgUUID,
                    orgUUID2);

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, priority = 4)
    public void processLeaved() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processLeaved(
                    action,
                    externalId,
                    clientUUID,
                    client, "Стационар");

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, priority = 5)
    public void processDelReceived() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processDelReceived(
                    action,
                    externalUUID,
                    externalId,
                    clientUUID,
                    client);

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, priority = 6)
    public void processCDA() {
        try {
            final MCCIIN000002UV01 result = HL7PacketBuilder.processRCMRIN000002UV02(
                    action,
                    clientUUID,
                    externalId,
                    client,
                    createPerson,
                    "ФНКЦ");

            final String docUUID = result.getId().getRoot();
            System.out.println("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            e.printStackTrace();
        }
    }


    private void demarshalling(MCCIIN000002UV01 response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.hl7.v3");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "urn:hl7-org:v3 PRPA_IN302011UV02.xsd");
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");
            marshaller.marshal(response, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
