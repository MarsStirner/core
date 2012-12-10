package ru.korus.tmis.pharmacy;

import misexchange.*;
import misexchange.ObjectFactory;
import misexchange.PRPAIN302011UV02;
import misexchange.PRPAIN302012UV02;
import org.hl7.v3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Patient;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 05.12.12
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class HL7PacketBuilder {

    final static Logger logger = LoggerFactory.getLogger(HL7PacketBuilder.class);

    /**
     * Формирование и отправка сообщения о госпитализации PRPA_IN402001UV02
     */
    public static void processReceived(
            Action action, String externalId, String externalUUID, String orgUUID, Patient client, String clientUUID) {

        final String uuidDocument = UUID.randomUUID().toString();
        logger.info("process RECEIVED document {}, action {}, orgStrucUUID {}, client {}", uuidDocument, action, orgUUID, client);
        final ObjectFactory factory = new ObjectFactory();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            final Request msg = factory.createPRPAIN402001UV02();

            final PRPAIN402001UV022 prpain402001UV022 = new PRPAIN402001UV022();
            prpain402001UV022.setITSVersion("XML_1.0");

            final TS ts = new TS();
            ts.setValue(sdf.format(action.getCreateDatetime()));
            prpain402001UV022.setCreationTime(ts);

            final CS askCode = new CS();
            askCode.setCode("AL");
            prpain402001UV022.setAcceptAckCode(askCode);

            final II id = new II();
            id.setRoot(uuidDocument);
            prpain402001UV022.setId(id);

            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN402001UV02");
            prpain402001UV022.setInteractionId(interactionId);

            final CS processingCode = new CS();
            processingCode.setCode("P");
            prpain402001UV022.setProcessingCode(processingCode);

            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain402001UV022.setProcessingModeCode(processingModeCode);

            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II typeId = new II();
            typeId.setNullFlavor(NullFlavor.fromValue("NI"));
            device.getId().add(typeId);
            sender.setDevice(device);
            prpain402001UV022.setSender(sender);

            final MCCIMT000100UV01Receiver receiver = new MCCIMT000100UV01Receiver();
            receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II idReceiver = new II();
            idReceiver.setNullFlavor(NullFlavor.NI);
            device1.getId().add(idReceiver);
            receiver.setDevice(device1);
            prpain402001UV022.getReceiver().add(receiver);

            final PRPAIN402001UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN402001UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN402001UV02MCAIMT700201UV01Subject2 subject2 = new PRPAIN402001UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT402001UV02InpatientEncounterEvent event = new PRPAMT402001UV02InpatientEncounterEvent();
            event.setClassCode(ActClassEncounter.ENC);
            event.setMoodCode(ActMoodEventOccurrence.EVN);
            final II typeId1 = new II();
            typeId1.setRoot(externalUUID);
            typeId1.setExtension(externalId); //
            event.getId().add(typeId1);

            final CD code = new CD();
            code.setCodeSystem("2.16.840.1.113883.5.4");
            code.setCodeSystemName("actCode");
            code.setCode("IMP");
            code.setDisplayName("abc");
            event.setCode(code);

            final CS statusCode = new CS();
            statusCode.setCode("completed");
            event.setStatusCode(statusCode);

            final IVLTS effectiveTime = new IVLTS();
            final SimpleDateFormat effectiveDateFormat = new SimpleDateFormat("yyyyMMdd");
            final IVXBTS dateBegin = new IVXBTS();
            dateBegin.setValue(effectiveDateFormat.format(action.getCreateDatetime()));
            effectiveTime.setCenter(dateBegin);


            event.setEffectiveTime(effectiveTime);

            final PQ lengthOfStayQuantity = new PQ();
            lengthOfStayQuantity.setValue("5");
            lengthOfStayQuantity.setUnit("d");
            event.setLengthOfStayQuantity(lengthOfStayQuantity);

            final PRPAMT402001UV02Subject subject = new PRPAMT402001UV02Subject();
            subject.setTypeCode(ParticipationTargetSubject.SBJ);
            subject.setContextControlCode(ContextControl.OP);
            final COCTMT050002UV07Patient patient = new COCTMT050002UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final CS typeId2 = new CS();
            typeId2.setCode(clientUUID);
            patient.getId().add(typeId2);

            final COCTMT050002UV07Person uv07Person = new COCTMT050002UV07Person();
            final JAXBElement<COCTMT050002UV07Person> patientPerson = new JAXBElement<COCTMT050002UV07Person>(
                    new QName("urn:hl7-org:v3", "patientPerson"),
                    COCTMT050002UV07Person.class,
                    COCTMT050002UV07Patient.class,
                    uv07Person);

            uv07Person.setClassCode(EntityClass.PSN);
            uv07Person.setDeterminerCode(EntityDeterminer.INSTANCE);
            final II typeId3 = new II();
            typeId3.setExtension(client.getSnils());
            uv07Person.getId().add(typeId3);

            final PN pn = new PN();
            final EnGiven enGiven = new EnGiven();
            enGiven.setRepresentation(BinaryDataEncoding.TXT);
            enGiven.setPartType(EntityNamePartType.FAM);



            JAXBElement<EnGiven> givenJAXBElement = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, enGiven);
            givenJAXBElement.setValue(enGiven);
            pn.getContent().add(givenJAXBElement);



            final EnGiven enGiven2 = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement2 = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, enGiven2);
            givenJAXBElement2.setValue(enGiven2);
            pn.getContent().add(givenJAXBElement2);



            final EnFamily enFamily = new EnFamily();
            JAXBElement<EnFamily> enFamilyJAXBElement = new JAXBElement<EnFamily>(
                    new QName("urn:hl7-org:v3", "family"), EnFamily.class, /*EN.class,*/ enFamily);

            pn.getContent().add(enFamilyJAXBElement);

            uv07Person.getName().add(pn);

            final CE administrativeGenderCode = new CE();
            administrativeGenderCode.setCode("M");
            administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
            uv07Person.setAdministrativeGenderCode(administrativeGenderCode);

            final TS birthTime = new TS();
            SimpleDateFormat sdfBirthday = new SimpleDateFormat("YYYYddMM");
            birthTime.setValue(sdfBirthday.format(client.getBirthDate()));
            uv07Person.setBirthTime(birthTime);
            patientPerson.setValue(uv07Person);
            patient.setPatientPerson(patientPerson);
            subject.setPatient(patient);
            event.setSubject(subject);


            final PRPAMT402001UV02Admitter admitter = new PRPAMT402001UV02Admitter();
            admitter.setNullFlavor(NullFlavor.NI);
            admitter.setTypeCode(ParticipationAdmitter.ADM);
            final IVLTS time = new IVLTS();
            time.setNullFlavor(NullFlavor.NI);
            admitter.setTime(time);

            final COCTMT090100UV01AssignedPerson assignedPerson = new COCTMT090100UV01AssignedPerson();
            assignedPerson.setClassCode(RoleClassAssignedEntity.ASSIGNED);
            admitter.setAssignedPerson(assignedPerson);

            event.setAdmitter(admitter);

            final PRPAMT402001UV02Location1 uv02Location1 = new PRPAMT402001UV02Location1();
            uv02Location1.setTypeCode(ParticipationTargetLocation.LOC);
            uv02Location1.setTime(time);
            final CS statusCode1 = new CS();
            statusCode1.setCode("active");
            uv02Location1.setStatusCode(statusCode1);

            final PRPAMT402001UV02ServiceDeliveryLocation deliveryLocation = new PRPAMT402001UV02ServiceDeliveryLocation();
            deliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II typeId4 = new II();
            typeId4.setRoot(orgUUID);
            deliveryLocation.getId().add(typeId4);
            uv02Location1.setServiceDeliveryLocation(deliveryLocation);

            event.getLocation().add(uv02Location1);

            subject2.setInpatientEncounterEvent(event);
            controlActProcess.getSubject().add(subject2);
            prpain402001UV022.setControlActProcess(controlActProcess);

            ((misexchange.PRPAIN402001UV02) msg).setMessage(prpain402001UV022);

            printMessage(msg, "misexchange");

            logger.info("message is prepared, sending now...");
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("message sended, result " + mcciin000002UV01);

            printMessage(mcciin000002UV01, "org.hl7.v3");

        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("throwable", e);
        }

    }


    /**
     * Формирование и отправка сообщения об отмене предыдущего сообщения о госпитализации
     * PRPA_IN402006UV02
     */
    public static void processDelReceived(Action action, String uuidExternalId, String externalId, String uuidClient, Patient client) {
        final String uuidDocument = UUID.randomUUID().toString();
        logger.info("process DEL_RECEIVED document {}, action {}, uuidExternalId {}, externalId {}, uuidClient {}, client {}",
                action, uuidExternalId, externalId, uuidClient, client);
        final ObjectFactory factory = new ObjectFactory();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            final Request msg = factory.createPRPAIN402006UV02();
            final PRPAIN402006UV022 prpain402006UV02 = new PRPAIN402006UV022();
            prpain402006UV02.setITSVersion("XML_1.0");

            final II id = new II();
            id.setRoot(uuidDocument);
            prpain402006UV02.setId(id);

            final TS ts = new TS();
            ts.setValue(sdf.format(action.getCreateDatetime()));
            prpain402006UV02.setCreationTime(ts);

            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.11.3883.1.18");
            interactionId.setExtension("PRPA_IN402006UV02");
            prpain402006UV02.setInteractionId(interactionId);

            final CS processCode = new CS();
            processCode.setCode("P");
            prpain402006UV02.setProcessingCode(processCode);


            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain402006UV02.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = new CS();
            acceptAckCode.setCode("AL");
            prpain402006UV02.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = new MCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = new II();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain402006UV02.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = new II();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain402006UV02.setSender(sender);

            // control act process
            final PRPAIN402006UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN402006UV02MCAIMT700201UV01ControlActProcess();

            //  subject
            final PRPAIN402006UV02MCAIMT700201UV01Subject2 uv01Subject2 = new PRPAIN402006UV02MCAIMT700201UV01Subject2();
            uv01Subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);
            final COMTMT001103UV01ActGenericStatus actGenericStatus = new COMTMT001103UV01ActGenericStatus();
            actGenericStatus.setClassCode(ActClassRoot.CACT);
            actGenericStatus.setMoodCode(ActMoodEventOccurrence.EVN);
            final II idroot = new II();
            idroot.setRoot(uuidExternalId);
            idroot.setExtension(externalId);
            actGenericStatus.getId().add(idroot);
            final CS statusCode = new CS();
            statusCode.setCode("nullfield");
            actGenericStatus.setStatusCode(statusCode);


            final COMTMT001103UV01RecordTarget recordTarget = new COMTMT001103UV01RecordTarget();
            recordTarget.setTypeCode(ParticipationRecordTarget.RCT);
            final COCTMT050000UV01Patient patient = new COCTMT050000UV01Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final II patientIdRoot = new II();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);

            final CS statusCode1 = new CS();
            statusCode1.setCode("active");
            patient.setStatusCode(statusCode1);

            final COCTMT030000UV09Person uv09Person = new COCTMT030000UV09Person();
            final JAXBElement<COCTMT030000UV09Person> patientPerson =
                    new JAXBElement<COCTMT030000UV09Person>(new QName("urn:hl7-org:v3", "patientPerson"),
                            COCTMT030000UV09Person.class, uv09Person);

            final II typeId3 = new II();
            typeId3.setExtension(client.getSnils());
            uv09Person.getId().add(typeId3);

            final PN pn = new PN();
            final EnGiven enGiven = new EnGiven();
            enGiven.setRepresentation(BinaryDataEncoding.TXT);
            enGiven.setMediaType("123333333333333333333333333333333");
            JAXBElement<EnGiven> givenJAXBElement = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, EN.class, enGiven);

            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement2 = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, EN.class, enGiven2);
            pn.getContent().add(givenJAXBElement2);

            final EnFamily enFamily = new EnFamily();
            JAXBElement<EnFamily> enFamilyJAXBElement = new JAXBElement<EnFamily>(
                    new QName("urn:hl7-org:v3", "family"), EnFamily.class, EN.class, enFamily);

            pn.getContent().add(enFamilyJAXBElement);
            uv09Person.getName().add(pn);

            patient.setPatientPerson(patientPerson);

            recordTarget.setPatient(patient);
            actGenericStatus.getRecordTarget().add(recordTarget);


            uv01Subject2.setActGenericStatus(actGenericStatus);
            controlActProcess.getSubject().add(uv01Subject2);
            prpain402006UV02.setControlActProcess(controlActProcess);


            ((misexchange.PRPAIN402006UV02) msg).setMessage(prpain402006UV02);
            printMessage(msg, "misexchange");
            logger.info("message is prepared, sending now...");
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("message sended, result " + mcciin000002UV01);
            printMessage(mcciin000002UV01, "org.hl7.v3");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throwable", e);
        }
    }


    /**
     * Формирование и отправка сообщения о выписке пациента со стационара PRPA_IN402003UV02
     *
     * @param action
     * @param evt
     * @param orgStructure
     * @param client
     */
    public static void processLeaved(Action action, Event evt, OrgStructure orgStructure, Patient client) {
        logger.info("process LEAVED action {}, event {}, orgStruc {}", action, evt, orgStructure);
        final ObjectFactory factory = new ObjectFactory();
        try {
            final Request msg = factory.createPRPAIN402003UV02();

            final PRPAIN402003UV022 prpain402003UV02 = new PRPAIN402003UV022();
            prpain402003UV02.setITSVersion("XML_1.0");

            final TS ts = new TS();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            ts.setValue(sdf.format(new Date()));
            prpain402003UV02.setCreationTime(ts);

            final CS askCode = new CS();
            askCode.setCode("AL");
            prpain402003UV02.setAcceptAckCode(askCode);

            final II id = new II();
            id.setRoot(UUID.randomUUID().toString());
            prpain402003UV02.setId(id);

            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN402003UV02");
            prpain402003UV02.setInteractionId(interactionId);

            final CS processingCode = new CS();
            processingCode.setCode("P");
            prpain402003UV02.setProcessingCode(processingCode);

            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain402003UV02.setProcessingModeCode(processingModeCode);

            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II typeId = new II();
            typeId.setNullFlavor(NullFlavor.fromValue("NI"));
            device.getId().add(typeId);
            sender.setDevice(device);
            prpain402003UV02.setSender(sender);
            final MCCIMT000100UV01Receiver receiver = new MCCIMT000100UV01Receiver();
            receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II idReceiver = new II();
            idReceiver.setNullFlavor(NullFlavor.NI);
            device1.getId().add(idReceiver);
            receiver.setDevice(device1);
            prpain402003UV02.getReceiver().add(receiver);

            final PRPAIN402003UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN402003UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN402003UV02MCAIMT700201UV01Subject2 subject2 = new PRPAIN402003UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT402003UV02InpatientEncounterEvent event = new PRPAMT402003UV02InpatientEncounterEvent();
            event.setClassCode(ActClassEncounter.ENC);
            event.setMoodCode(ActMoodEventOccurrence.EVN);
            final II typeId1 = new II();
            typeId1.setRoot(UUID.randomUUID().toString());
            typeId1.setExtension("123"); //
            event.getId().add(typeId1);

            final CD code = new CD();
            code.setCodeSystem("2.16.840.1.113883.5.4");
            code.setCodeSystemName("actCode");
            code.setCode("IMP");
            code.setDisplayName("abc");
            event.setCode(code);

            final CS statusCode = new CS();
            statusCode.setCode("completed");
            event.setStatusCode(statusCode);

            final IVLTS effectiveTime = new IVLTS();
            final SimpleDateFormat effectiveDateFormat = new SimpleDateFormat("yyyyMMdd");
            final IVXBTS low = new IVXBTS();
            low.setValue(effectiveDateFormat.format(new Date()));
            effectiveTime.setLow(low);

            final IVXBTS high = new IVXBTS();
            high.setValue(effectiveDateFormat.format(new Date()));
            effectiveTime.setHigh(high);

            event.setEffectiveTime(effectiveTime);

            final PQ lengthOfStayQuantity = new PQ();
            lengthOfStayQuantity.setValue("5");
            lengthOfStayQuantity.setUnit("d");
            event.setLengthOfStayQuantity(lengthOfStayQuantity);


            final PRPAMT402003UV02Subject subject = new PRPAMT402003UV02Subject();
            subject.setTypeCode(ParticipationTargetSubject.SBJ);
            subject.setContextControlCode(ContextControl.OP);
            final COCTMT050002UV07Patient patient = new COCTMT050002UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final CS typeId2 = new CS();
            typeId2.setCode("123");
            patient.getId().add(typeId2);

            final COCTMT050002UV07Person uv07Person = new COCTMT050002UV07Person();
            final JAXBElement<COCTMT050002UV07Person> patientPerson = new JAXBElement<COCTMT050002UV07Person>(
                    new QName("urn:hl7-org:v3", "patientPerson"), COCTMT050002UV07Person.class, COCTMT050002UV07Patient.class, uv07Person);

            uv07Person.setClassCode(EntityClass.PSN);
            uv07Person.setDeterminerCode(EntityDeterminer.INSTANCE);
            final II typeId3 = new II();
            typeId3.setExtension("00001");
            uv07Person.getId().add(typeId3);

            final PN pn = new PN();
            final EnGiven enGiven = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, EN.class, enGiven);
            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement2 = new JAXBElement<EnGiven>(
                    new QName("urn:hl7-org:v3", "given"), EnGiven.class, EN.class, enGiven2);
            pn.getContent().add(givenJAXBElement2);

            final EnFamily enFamily = new EnFamily();
            JAXBElement<EnFamily> enFamilyJAXBElement = new JAXBElement<EnFamily>(
                    new QName("urn:hl7-org:v3", "family"), EnFamily.class, EN.class, enFamily);
            pn.getContent().add(enFamilyJAXBElement);

            uv07Person.getName().add(pn);

            final CE administrativeGenderCode = new CE();
            administrativeGenderCode.setCode("M");
            administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
            uv07Person.setAdministrativeGenderCode(administrativeGenderCode);

            final TS birthTime = new TS();
            SimpleDateFormat sdfBirthday = new SimpleDateFormat("YYYYddMM");
            birthTime.setValue(sdfBirthday.format(client.getBirthDate()));
            uv07Person.setBirthTime(birthTime);

            patientPerson.setValue(uv07Person);
            patient.setPatientPerson(patientPerson);
            subject.setPatient(patient);
            event.setSubject(subject);
            subject2.setInpatientEncounterEvent(event);
            controlActProcess.getSubject().add(subject2);
            prpain402003UV02.setControlActProcess(controlActProcess);

            ((misexchange.PRPAIN402003UV02) msg).setMessage(prpain402003UV02);

            printMessage(msg, "misexchange");

            logger.info("message is prepared, sending now...");
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("message sended, result " + mcciin000002UV01);

            printMessage(mcciin000002UV01, "org.hl7.v3");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throwable", e);
        }
    }


    /**
     * Формирование и отправка сообщения о переводе пациента между отделениями стационара PRPA_IN302011UV02
     *
     */
    public static void processMoving(Action action, String uuidExternal, String externalId, String uuidClient,
                                     String uuidLocationOut, String uuidLocationIn) {
        final String uuidDocument = UUID.randomUUID().toString();
        logger.info("process MOVING document {}, action {}", uuidDocument, action);
        final ObjectFactory factory = new ObjectFactory();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        try {
            final Request msg = factory.createPRPAIN302011UV02();
            final PRPAIN302011UV022 prpain302011UV022 = new PRPAIN302011UV022();
            prpain302011UV022.setITSVersion("XML_1.0");

            final II id = new II();
            id.setRoot(uuidDocument);
            prpain302011UV022.setId(id);

            final TS ts = new TS();
            ts.setValue(sdf.format(action.getCreateDatetime()));
            prpain302011UV022.setCreationTime(ts);

            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN302011UV02");
            prpain302011UV022.setInteractionId(interactionId);

            final CS processCode = new CS();
            processCode.setCode("P");
            prpain302011UV022.setProcessingCode(processCode);


            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain302011UV022.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = new CS();
            acceptAckCode.setCode("AL");
            prpain302011UV022.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = new MCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = new II();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain302011UV022.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = new II();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain302011UV022.setSender(sender);

            // control act process
            final PRPAIN302011UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN302011UV02MCAIMT700201UV01ControlActProcess();

            final PRPAIN302011UV02MCAIMT700201UV01Subject2 subject2 = new PRPAIN302011UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT302011UV02EncounterEvent encounterEvent = new PRPAMT302011UV02EncounterEvent();
            encounterEvent.setClassCode(ActClassEncounter.ENC);
            encounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);

            final II idRoot = new II();
            idRoot.setRoot(uuidExternal);
            idRoot.setExtension(externalId);
            encounterEvent.getId().add(idRoot);

            final PRPAMT302011UV02Subject subjectPatient = new PRPAMT302011UV02Subject();
            subjectPatient.setTypeCode(ParticipationTargetSubject.SBJ);
            final COCTMT050001UV07Patient patient = new COCTMT050001UV07Patient();
            final II patientIdRoot = new II();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);
            subjectPatient.setPatient(patient);
            encounterEvent.setSubject(subjectPatient);

            // location1
            final PRPAMT302011UV02Location1 location1 = new PRPAMT302011UV02Location1();
            location1.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut = new IVLTS();
            final TS ts1 = new TS();
            ts1.setValue(sdfDate.format(action.getCreateDatetime()));
            timeOut.setCenter(ts1);
            location1.setTime(timeOut);

            final CS statusCode = new CS();
            statusCode.setCode("active");
            location1.setStatusCode(statusCode);

            final PRPAMT302011UV02ServiceDeliveryLocation serviceDeliveryLocation = new PRPAMT302011UV02ServiceDeliveryLocation();
            serviceDeliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc = new II();
            idRootLoc.setRoot(uuidLocationOut);
            serviceDeliveryLocation.getId().add(idRootLoc);
            location1.setServiceDeliveryLocation(serviceDeliveryLocation);
            encounterEvent.setLocation1(location1);

            // location2
            final PRPAMT302011UV02Location2 location2 = new PRPAMT302011UV02Location2();
            location2.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut2 = new IVLTS();
            final TS ts2 = new TS();
            ts2.setValue(sdfDate.format(action.getCreateDatetime()));
            timeOut2.setCenter(ts2);
            location2.setTime(timeOut2);

            final CS statusCode2 = new CS();
            statusCode2.setCode("active");
            location2.setStatusCode(statusCode2);

            final PRPAMT302011UV02ServiceDeliveryLocation serviceDeliveryLocation2 = new PRPAMT302011UV02ServiceDeliveryLocation();
            serviceDeliveryLocation2.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc2 = new II();
            idRootLoc2.setRoot(uuidLocationIn);
            serviceDeliveryLocation2.getId().add(idRootLoc2);
            location2.setServiceDeliveryLocation(serviceDeliveryLocation2);
            encounterEvent.setLocation2(location2);

            subject2.setEncounterEvent(encounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain302011UV022.setControlActProcess(controlActProcess);

            ((PRPAIN302011UV02) msg).setMessage(prpain302011UV022);
            printMessage(msg, "misexchange");
            logger.info("message is prepared, sending now...");
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("message sended, result " + mcciin000002UV01);
            printMessage(mcciin000002UV01, "org.hl7.v3");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throwable", e);
        }
    }

    /**
     * Формирование и отправка сообщения об отмене предыдущего сообщения о переводе пациента между отделениями стационара
     * PRPA_IN302012UV02
     */
    public static void processDelMoving(Action action, String uuidExternal, String externalId, String uuidClient,
                                        String uuidLocationOut, String uuidLocationIn) {
        final String uuidDocument = UUID.randomUUID().toString();
        logger.info("process DEL_MOVING document {}, action {}", uuidDocument, action);
        final ObjectFactory factory = new ObjectFactory();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        try {
            final Request msg = factory.createPRPAIN302012UV02();
            final PRPAIN302012UV022 prpain302012UV022 = new PRPAIN302012UV022();
            prpain302012UV022.setITSVersion("XML_1.0");


            final II id = new II();
            id.setRoot(uuidDocument);
            prpain302012UV022.setId(id);

            final TS ts = new TS();
            ts.setValue(sdf.format(action.getCreateDatetime()));
            prpain302012UV022.setCreationTime(ts);

            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN302012UV02");
            prpain302012UV022.setInteractionId(interactionId);

            final CS processCode = new CS();
            processCode.setCode("P");
            prpain302012UV022.setProcessingCode(processCode);


            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain302012UV022.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = new CS();
            acceptAckCode.setCode("AL");
            prpain302012UV022.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = new MCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = new II();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain302012UV022.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = new II();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain302012UV022.setSender(sender);

            // control act process
            final PRPAIN302012UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN302012UV02MCAIMT700201UV01ControlActProcess();

            final PRPAIN302012UV02MCAIMT700201UV01Subject2 subject2 = new PRPAIN302012UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT302012UV02EncounterEvent encounterEvent = new PRPAMT302012UV02EncounterEvent();
            encounterEvent.setClassCode(ActClassEncounter.ENC);
            encounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);

            final II idRoot = new II();
            idRoot.setRoot(uuidExternal);
            idRoot.setExtension(externalId);
            encounterEvent.getId().add(idRoot);

            final PRPAMT302012UV02Subject subjectPatient = new PRPAMT302012UV02Subject();
            subjectPatient.setTypeCode(ParticipationTargetSubject.SBJ);
            final COCTMT050001UV07Patient patient = new COCTMT050001UV07Patient();
            final II patientIdRoot = new II();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);
            subjectPatient.setPatient(patient);
            encounterEvent.setSubject(subjectPatient);

            // location1
            final PRPAMT302012UV02Location1 location1 = new PRPAMT302012UV02Location1();
            location1.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut = new IVLTS();
            final TS ts1 = new TS();
            ts1.setValue(sdfDate.format(action.getCreateDatetime()));
            timeOut.setCenter(ts1);
            location1.setTime(timeOut);

            final CS statusCode = new CS();
            statusCode.setCode("active");
            location1.setStatusCode(statusCode);

            final PRPAMT302012UV02ServiceDeliveryLocation serviceDeliveryLocation = new PRPAMT302012UV02ServiceDeliveryLocation();
            serviceDeliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc = new II();
            idRootLoc.setRoot(uuidLocationOut);
            serviceDeliveryLocation.getId().add(idRootLoc);
            location1.setServiceDeliveryLocation(serviceDeliveryLocation);
            encounterEvent.setLocation1(location1);

            // location2
            final PRPAMT302012UV02Location2 location2 = new PRPAMT302012UV02Location2();
            location2.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut2 = new IVLTS();
            final TS ts2 = new TS();
            ts2.setValue(sdfDate.format(action.getCreateDatetime()));
            timeOut2.setCenter(ts2);
            location2.setTime(timeOut2);

            final CS statusCode2 = new CS();
            statusCode2.setCode("active");
            location2.setStatusCode(statusCode2);

            final PRPAMT302012UV02ServiceDeliveryLocation serviceDeliveryLocation2 = new PRPAMT302012UV02ServiceDeliveryLocation();
            serviceDeliveryLocation2.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc2 = new II();
            idRootLoc2.setRoot(uuidLocationIn);
            serviceDeliveryLocation2.getId().add(idRootLoc2);
            location2.setServiceDeliveryLocation(serviceDeliveryLocation2);
            encounterEvent.setLocation2(location2);

            subject2.setEncounterEvent(encounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain302012UV022.setControlActProcess(controlActProcess);


            ((PRPAIN302012UV02) msg).setMessage(prpain302012UV022);
            printMessage(msg, "misexchange");
            logger.info("message is prepared, sending now...");
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("message sended, result " + mcciin000002UV01);
            printMessage(mcciin000002UV01, "org.hl7.v3");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("throwable", e);
        }
    }


    /**
     * Вывод сообщения в лог
     *
     * @param msg
     * @param contextPath
     */
    private static void printMessage(Object msg, String contextPath) {
        final StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(msg, writer/*System.out*/);
            logger.info("marchall message: {}", writer.toString());
        } catch (Exception e) {
            logger.error("jaxb exception", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                //skip
            }
        }
    }


}
