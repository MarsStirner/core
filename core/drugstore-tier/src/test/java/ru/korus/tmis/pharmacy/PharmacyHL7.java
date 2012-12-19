package ru.korus.tmis.pharmacy;

import org.hl7.v3.MCCIIN000002UV01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    final static Logger logger = LoggerFactory.getLogger(PharmacyHL7.class);

    private final Action action = new Action(1);
    private final Patient client = new Patient(2);
    private final Patient au = new Patient(3);
    private final Staff createPerson = new Staff(11);
    private final String externalId = "2012/4251";
    private final String externalUUID = UUID.randomUUID().toString();
    private final String orgUUID = "9a8d8f2a-57d7-11e1-b43a-005056a41f97";
    private final String orgUUID2 = "0eea8235-1c12-11e1-7085-000c29d5ecf8";
    private final String clientUUID = UUID.randomUUID().toString();

    @BeforeSuite
    private void init() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        action.setCreateDatetime(new Date());
        client.setBirthDate(format.parse("06.05.2005"));
        client.setFirstName("Елена");
        client.setPatrName("Игоревна");
        client.setLastName("Абдикаримова");
        client.setSnils("1122-111-222");

        au.setBirthDate(new Date(300, 9, 9));
        au.setFirstName("Сидор");
        au.setPatrName("Сидорович");
        au.setLastName("Администраторов");
        au.setSnils("99-199-999");

        createPerson.setFirstName("Доктор");
        createPerson.setPatrName("Докторович");
        createPerson.setLastName("Айболит");

        logger.info("Start test 1C Pharmacy integration with patameter: " +
                "Action {}, Patient {}, clientUUID {}, externalId {}, externalUUUID {}, " +
                "orgUUID {}, org2UUID {}, staff {}",
                action, client, clientUUID, externalId, externalUUID, orgUUID, orgUUID2, createPerson);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
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
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
        }
    }
}
