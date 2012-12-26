package ru.korus.tmis.pharmacy;

import org.hl7.v3.MCCIIN000002UV01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author      Dmitriy E. Nosov <br>
 * Date:        08.11.12, 11:38 <br>
 * Company:     Korus Consulting IT<br>
 * Description: <br>
 */

public class PharmacyHL7 {

    final static Logger logger = LoggerFactory.getLogger(PharmacyHL7.class);

    private final Action action = new Action(1);
    private final Patient client = new Patient(2);
    private final Staff doctor = new Staff(11);
    private final Staff doctorAssigPerson = new Staff(11);
    private final String externalId = "2012/11782";  // номер карты в мис
    private final String externalUUID = "1b264840-5555-4444-89fd-1f6b355dfa91";  // UUID карты
    private final String orgUUID = "0eea8235-1c12-11e1-7085-000c29d5ecf8";   // дневной стационар
    private final String orgUUID2 = "0eea8235-1c12-11e1-7085-000c29d5ecf8";
    private final String clientUUID = "5128db7d-4444-43b8-9617-e2d2f229dac3";  // UUID пациента
    private final String custodianUUID = "1111822f-2222-11e1-7085-000c29d5ecf8";


    @DataProvider(name = "test1")
    public Object[][] createData1() {
        return new Object[][] {
                { "Cedric", new Integer(36) },
                { "Anne", new Integer(37)},
        };
    }



    @BeforeSuite
    private void init() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        action.setCreateDatetime(new Date());
        client.setBirthDate(format.parse("12.05.2005"));
        client.setFirstName("Данил");
        client.setPatrName("Матвеевич");
        client.setLastName("Агафонов");
        client.setSnils("1122-111-222");

        doctorAssigPerson.setFirstName("Медсестра");
        doctorAssigPerson.setPatrName("Владимировна");
        doctorAssigPerson.setLastName("Регистраторова");

        doctor.setFirstName("Медсестра");
        doctor.setPatrName("Владимировна");
        doctor.setLastName("Регистраторова");
        doctor.setUuid(new UUID("5555db7d-5555-43b8-9617-e2d2f229dac3"));
        final Speciality speciality = new Speciality(1);
        speciality.setName("нейрохирург");
        doctor.setSpeciality(speciality);

        logger.info("Start test 1C Pharmacy integration with patameter: " +
                "Action {}, Patient {}, clientUUID {}, externalId {}, externalUUUID {}, " +
                "orgUUID {}, org2UUID {}, staff {}",
                action, client, clientUUID, externalId, externalUUID, orgUUID, orgUUID2, doctor);
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
                    externalUUID,
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
                    doctor,
                    "ФНКЦ",
                    externalUUID,
                    custodianUUID, doctor);

            final String docUUID = result.getId().getRoot();
            logger.info("docUUID = " + docUUID);

        } catch (SoapConnectionException e) {
            logger.error("SoapConnectionException: " + e, e);
        }
    }
}
