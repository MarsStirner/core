package ru.korus.tmis.pharmacy;

import misexchange.*;
import misexchange.ObjectFactory;
import misexchange.PRPAIN302011UV02;
import misexchange.PRPAIN302012UV02;
import misexchange.RCMRIN000002UV02;
import org.hl7.v3.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * Author: Dmitriy E. Nosov <br>
 * Date: 05.12.12, 17:41 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class HL7PacketBuilder {

    final static Logger logger = LoggerFactory.getLogger(HL7PacketBuilder.class);


    private static final ObjectFactory FACTORY_MIS = new ObjectFactory();
    private static final org.hl7.v3.ObjectFactory FACTORY_HL7 = new org.hl7.v3.ObjectFactory();

    /**
     * Формирование и отправка сообщения о госпитализации PRPA_IN402001UV02
     */
    public static MCCIIN000002UV01 processReceived(
            final Action action,
            final OrgStructure orgStructure) throws SoapConnectionException {
        try {
            final Event event = action.getEvent();
            final Patient client = event.getPatient();
            final String uuidExternalId = event.getUuid().getUuid();
            final String externalId = event.getExternalId();
            final String uuidDocument = UUID.randomUUID().toString();
            final String uuidOrgStructure = orgStructure.getUuid().getUuid();
            final String uuidClient = client.getUuid().getUuid();

            logger.info("process RECEIVED document {}, action {}, event {}, orgStructure {}, client {}",
                    uuidDocument, action, event, orgStructure, client);

            final Request msg = FACTORY_MIS.createPRPAIN402001UV02();

            final PRPAIN402001UV022 prpain402001UV022 = FACTORY_HL7.createPRPAIN402001UV022();
            prpain402001UV022.setITSVersion("XML_1.0");

            final TS ts = FACTORY_HL7.createTS();
            final DateTime dateTime = new DateTime(action.getCreateDatetime());
            ts.setValue(dateTime.toString("yyyyMMddHHmmss"));
            prpain402001UV022.setCreationTime(ts);

            final CS askCode = FACTORY_HL7.createCS();
            askCode.setCode("AL");
            prpain402001UV022.setAcceptAckCode(askCode);

            final II id = FACTORY_HL7.createII();
            id.setRoot(uuidDocument);
            prpain402001UV022.setId(id);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN402001UV02");
            prpain402001UV022.setInteractionId(interactionId);

            final CS processingCode = FACTORY_HL7.createCS();
            processingCode.setCode("P");
            prpain402001UV022.setProcessingCode(processingCode);

            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            prpain402001UV022.setProcessingModeCode(processingModeCode);

            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II typeId = FACTORY_HL7.createII();
            typeId.setNullFlavor(NullFlavor.fromValue("NI"));
            device.getId().add(typeId);
            sender.setDevice(device);
            prpain402001UV022.setSender(sender);

            final MCCIMT000100UV01Receiver receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II idReceiver = FACTORY_HL7.createII();
            idReceiver.setNullFlavor(NullFlavor.NI);
            device1.getId().add(idReceiver);
            receiver.setDevice(device1);
            prpain402001UV022.getReceiver().add(receiver);

            final PRPAIN402001UV02MCAIMT700201UV01ControlActProcess controlActProcess = FACTORY_HL7.createPRPAIN402001UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN402001UV02MCAIMT700201UV01Subject2 subject2 = FACTORY_HL7.createPRPAIN402001UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT402001UV02InpatientEncounterEvent inpatientEncounterEvent = FACTORY_HL7.createPRPAMT402001UV02InpatientEncounterEvent();
            inpatientEncounterEvent.setClassCode(ActClassEncounter.ENC);
            inpatientEncounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);
            final II typeId1 = FACTORY_HL7.createII();
            typeId1.setRoot(uuidExternalId);
            typeId1.setExtension(externalId); //
            inpatientEncounterEvent.getId().add(typeId1);

            final CD code = FACTORY_HL7.createCD();
            code.setCodeSystem("2.16.840.1.113883.5.4");
            code.setCodeSystemName("actCode");
            code.setCode("IMP");
            code.setDisplayName("abc");
            inpatientEncounterEvent.setCode(code);

            final CS statusCode = FACTORY_HL7.createCS();
            statusCode.setCode("completed");
            inpatientEncounterEvent.setStatusCode(statusCode);

            final IVLTS effectiveTime = FACTORY_HL7.createIVLTS();
            final DateTime effectiveDate = new DateTime(action.getCreateDatetime());
            effectiveTime.setValue(effectiveDate.toString("yyyyMMdd"));

            inpatientEncounterEvent.setEffectiveTime(effectiveTime);

            final PQ lengthOfStayQuantity = FACTORY_HL7.createPQ();
            lengthOfStayQuantity.setValue("5");
            lengthOfStayQuantity.setUnit("d");
            inpatientEncounterEvent.setLengthOfStayQuantity(lengthOfStayQuantity);

            final PRPAMT402001UV02Subject subject = FACTORY_HL7.createPRPAMT402001UV02Subject();
            subject.setTypeCode(ParticipationTargetSubject.SBJ);
            subject.setContextControlCode(ContextControl.OP);
            final COCTMT050002UV07Patient patient = FACTORY_HL7.createCOCTMT050002UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final CS typeId2 = FACTORY_HL7.createCS();
            typeId2.setCode(uuidClient);
            patient.getId().add(typeId2);

            final COCTMT050002UV07Person uv07Person = FACTORY_HL7.createCOCTMT050002UV07Person();
            final JAXBElement<COCTMT050002UV07Person>
                    patientPerson = FACTORY_HL7.createCOCTMT050002UV07PatientPatientPerson(uv07Person);

            uv07Person.setClassCode(EntityClass.PSN);
            uv07Person.setDeterminerCode(EntityDeterminer.INSTANCE);
            if (client.getSnils() != null && !"".equals(client.getSnils())) {
                final II typeId3 = FACTORY_HL7.createII();
                typeId3.setExtension(client.getSnils());
                uv07Person.getId().add(typeId3);
            }

            final PN pn = FACTORY_HL7.createPN();
            final EnGiven enGiven = FACTORY_HL7.createEnGiven();
            enGiven.getContent().add(client.getFirstName());
            JAXBElement<EnGiven> givenJAXBElement = FACTORY_HL7.createENGiven(enGiven);
            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = FACTORY_HL7.createEnGiven();
            enGiven2.getContent().add(client.getPatrName());
            JAXBElement<EnGiven> givenJAXBElement2 = FACTORY_HL7.createENGiven(enGiven2);
            givenJAXBElement2.setValue(enGiven2);
            pn.getContent().add(givenJAXBElement2);


            final EnFamily enFamily = FACTORY_HL7.createEnFamily();
            enFamily.getContent().add(client.getLastName());
            JAXBElement<EnFamily> enFamilyJAXBElement = FACTORY_HL7.createENFamily(enFamily);
            pn.getContent().add(enFamilyJAXBElement);

            uv07Person.getName().add(pn);

            final CE administrativeGenderCode = FACTORY_HL7.createCE();
            administrativeGenderCode.setCode("M");
            administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
            uv07Person.setAdministrativeGenderCode(administrativeGenderCode);

            final TS birthTime = FACTORY_HL7.createTS();
            final DateTime dateBirthday = new DateTime(client.getBirthDate());
            birthTime.setValue(dateBirthday.toString("yyyyMMdd"));
            uv07Person.setBirthTime(birthTime);
            patientPerson.setValue(uv07Person);
            patient.setPatientPerson(patientPerson);
            subject.setPatient(patient);
            inpatientEncounterEvent.setSubject(subject);


            final PRPAMT402001UV02Admitter admitter = FACTORY_HL7.createPRPAMT402001UV02Admitter();
            admitter.setNullFlavor(NullFlavor.NI);
            admitter.setTypeCode(ParticipationAdmitter.ADM);
            final IVLTS time = FACTORY_HL7.createIVLTS();
            time.setNullFlavor(NullFlavor.NI);
            admitter.setTime(time);

            final COCTMT090100UV01AssignedPerson assignedPerson = FACTORY_HL7.createCOCTMT090100UV01AssignedPerson();
            assignedPerson.setClassCode(RoleClassAssignedEntity.ASSIGNED);
            admitter.setAssignedPerson(assignedPerson);

            inpatientEncounterEvent.setAdmitter(admitter);

            final PRPAMT402001UV02Location1 uv02Location1 = FACTORY_HL7.createPRPAMT402001UV02Location1();
            uv02Location1.setTypeCode(ParticipationTargetLocation.LOC);
            uv02Location1.setTime(time);
            final CS statusCode1 = FACTORY_HL7.createCS();
            statusCode1.setCode("active");
            uv02Location1.setStatusCode(statusCode1);

            final PRPAMT402001UV02ServiceDeliveryLocation deliveryLocation = FACTORY_HL7.createPRPAMT402001UV02ServiceDeliveryLocation();
            deliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II typeId4 = FACTORY_HL7.createII();
            typeId4.setRoot(uuidOrgStructure);
            deliveryLocation.getId().add(typeId4);
            uv02Location1.setServiceDeliveryLocation(deliveryLocation);

            inpatientEncounterEvent.getLocation().add(uv02Location1);

            subject2.setInpatientEncounterEvent(inpatientEncounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain402001UV022.setControlActProcess(controlActProcess);

            ((misexchange.PRPAIN402001UV02) msg).setMessage(prpain402001UV022);

            logger.info("prepare message... \n\n {}", marshallMessage(msg, "misexchange"));
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));

            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);

        }
        throw new SoapConnectionException("Bad connection to 1C Pharmacy");
    }

    /**
     * Формирование и отправка сообщения об отмене предыдущего сообщения о госпитализации
     * Формирование и отправка сообщения об отмене предыдущего сообщения о госпитализации
     * PRPA_IN402006UV02
     */
    public static MCCIIN000002UV01 processDelReceived(final Action action) throws SoapConnectionException {
        try {
            final Event event = action.getEvent();
            final Patient client = event.getPatient();
            final String uuidExternalId = event.getUuid().getUuid();
            final String externalId = event.getExternalId();
            final String uuidClient = client.getUuid().getUuid();

            final String uuidDocument = UUID.randomUUID().toString();
            logger.info("process DEL_RECEIVED document {}, action {}, uuidExternalId {}, externalId {}, uuidClient {}, client {}",
                    action, uuidExternalId, externalId, uuidClient, client);

            final Request msg = FACTORY_MIS.createPRPAIN402006UV02();
            final PRPAIN402006UV022 prpain402006UV02 = FACTORY_HL7.createPRPAIN402006UV022();
            prpain402006UV02.setITSVersion("XML_1.0");

            final II id = FACTORY_HL7.createII();
            id.setRoot(uuidDocument);
            prpain402006UV02.setId(id);

            final TS ts = FACTORY_HL7.createTS();
            final DateTime dateTime = new DateTime(action.getCreateDatetime());
            ts.setValue(dateTime.toString("yyyyMMddHHmmss"));
            prpain402006UV02.setCreationTime(ts);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.11.3883.1.18");
            interactionId.setExtension("PRPA_IN402006UV02");
            prpain402006UV02.setInteractionId(interactionId);

            final CS processCode = FACTORY_HL7.createCS();
            processCode.setCode("P");
            prpain402006UV02.setProcessingCode(processCode);


            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            prpain402006UV02.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = FACTORY_HL7.createCS();
            acceptAckCode.setCode("AL");
            prpain402006UV02.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = FACTORY_HL7.createII();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain402006UV02.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = FACTORY_HL7.createII();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain402006UV02.setSender(sender);

            // control act process
            final PRPAIN402006UV02MCAIMT700201UV01ControlActProcess controlActProcess = FACTORY_HL7.createPRPAIN402006UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);
            //  subject
            final PRPAIN402006UV02MCAIMT700201UV01Subject2 uv01Subject2 = FACTORY_HL7.createPRPAIN402006UV02MCAIMT700201UV01Subject2();
            uv01Subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);
            final COMTMT001103UV01ActGenericStatus actGenericStatus = FACTORY_HL7.createCOMTMT001103UV01ActGenericStatus();
            actGenericStatus.setClassCode(ActClassRoot.CACT);
            actGenericStatus.setMoodCode(ActMoodEventOccurrence.EVN);
            final II idroot = FACTORY_HL7.createII();
            idroot.setRoot(uuidExternalId);
            idroot.setExtension(externalId);
            actGenericStatus.getId().add(idroot);
            final CS statusCode = FACTORY_HL7.createCS();
            statusCode.setCode("nullfield");
            actGenericStatus.setStatusCode(statusCode);


            final COMTMT001103UV01RecordTarget recordTarget = FACTORY_HL7.createCOMTMT001103UV01RecordTarget();
            recordTarget.setTypeCode(ParticipationRecordTarget.RCT);
            final COCTMT050000UV01Patient patient = FACTORY_HL7.createCOCTMT050000UV01Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final II patientIdRoot = FACTORY_HL7.createII();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);

            final CS statusCode1 = FACTORY_HL7.createCS();
            statusCode1.setCode("active");
            patient.setStatusCode(statusCode1);

            final COCTMT030000UV09Person uv09Person = FACTORY_HL7.createCOCTMT030000UV09Person();
            final JAXBElement<COCTMT030000UV09Person> patientPerson =
                    new JAXBElement<COCTMT030000UV09Person>(new QName("urn:hl7-org:v3", "patientPerson"),
                            COCTMT030000UV09Person.class, uv09Person);

            final II typeId3 = FACTORY_HL7.createII();
            typeId3.setExtension(client.getSnils());
            uv09Person.getId().add(typeId3);

            final PN pn = FACTORY_HL7.createPN();
            final EnGiven enGiven = FACTORY_HL7.createEnGiven();
            enGiven.getContent().add(client.getFirstName());
            JAXBElement<EnGiven> givenJAXBElement = FACTORY_HL7.createENGiven(enGiven);
            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = FACTORY_HL7.createEnGiven();
            enGiven2.getContent().add(client.getPatrName());
            JAXBElement<EnGiven> givenJAXBElement2 = FACTORY_HL7.createENGiven(enGiven2);
            givenJAXBElement2.setValue(enGiven2);
            pn.getContent().add(givenJAXBElement2);


            final EnFamily enFamily = FACTORY_HL7.createEnFamily();
            enFamily.getContent().add(client.getLastName());
            JAXBElement<EnFamily> enFamilyJAXBElement = FACTORY_HL7.createENFamily(enFamily);
            pn.getContent().add(enFamilyJAXBElement);

            uv09Person.getName().add(pn);

            patient.setPatientPerson(patientPerson);

            recordTarget.setPatient(patient);
            actGenericStatus.getRecordTarget().add(recordTarget);


            uv01Subject2.setActGenericStatus(actGenericStatus);
            controlActProcess.getSubject().add(uv01Subject2);
            prpain402006UV02.setControlActProcess(controlActProcess);


            ((misexchange.PRPAIN402006UV02) msg).setMessage(prpain402006UV02);
            logger.info("prepare message... \n\n {}", marshallMessage(msg, "misexchange"));

            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));

            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);
        }
        throw new SoapConnectionException("Bad connection to 1C Pharmacy");
    }


    /**
     * Формирование и отправка сообщения о выписке пациента со стационара PRPA_IN402003UV02
     */
    public static MCCIIN000002UV01 processLeaved(final Action action, String displayName) throws SoapConnectionException {
        try {
            final Event event = action.getEvent();
            final Patient client = event.getPatient();
            final String externalId = event.getExternalId();
            final String externalUUID = event.getUuid().getUuid();
            final String clientUUID = client.getUuid().getUuid();
            final String rootUUID = UUID.randomUUID().toString();

            logger.info("process LEAVED action {}, externalId {}, externalUUID {}, client {}, clientUUID {}", action, externalId, externalUUID, client, clientUUID);

            final Request msg = FACTORY_MIS.createPRPAIN402003UV02();

            final PRPAIN402003UV022 prpain402003UV02 = FACTORY_HL7.createPRPAIN402003UV022();
            prpain402003UV02.setITSVersion("XML_1.0");

            final TS ts = FACTORY_HL7.createTS();

            final DateTime dateTime = DateTime.now();
            ts.setValue(dateTime.toString("yyyyMMddHHmmss"));
            prpain402003UV02.setCreationTime(ts);

            final CS askCode = FACTORY_HL7.createCS();
            askCode.setCode("AL");
            prpain402003UV02.setAcceptAckCode(askCode);

            final II id = FACTORY_HL7.createII();
            id.setRoot(rootUUID);
            prpain402003UV02.setId(id);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN402003UV02");
            prpain402003UV02.setInteractionId(interactionId);

            final CS processingCode = FACTORY_HL7.createCS();
            processingCode.setCode("P");
            prpain402003UV02.setProcessingCode(processingCode);

            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            prpain402003UV02.setProcessingModeCode(processingModeCode);

            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II typeId = FACTORY_HL7.createII();
            typeId.setNullFlavor(NullFlavor.fromValue("NI"));
            device.getId().add(typeId);
            sender.setDevice(device);
            prpain402003UV02.setSender(sender);
            final MCCIMT000100UV01Receiver receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II idReceiver = FACTORY_HL7.createII();
            idReceiver.setNullFlavor(NullFlavor.NI);
            device1.getId().add(idReceiver);
            receiver.setDevice(device1);
            prpain402003UV02.getReceiver().add(receiver);

            final PRPAIN402003UV02MCAIMT700201UV01ControlActProcess controlActProcess = FACTORY_HL7.createPRPAIN402003UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN402003UV02MCAIMT700201UV01Subject2 subject2 = FACTORY_HL7.createPRPAIN402003UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT402003UV02InpatientEncounterEvent inpatientEncounterEvent = FACTORY_HL7.createPRPAMT402003UV02InpatientEncounterEvent();
            inpatientEncounterEvent.setClassCode(ActClassEncounter.ENC);
            inpatientEncounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);
            final II typeId1 = FACTORY_HL7.createII();
            typeId1.setRoot(externalUUID);
            typeId1.setExtension(externalId); //
            inpatientEncounterEvent.getId().add(typeId1);

            final CD code = FACTORY_HL7.createCD();
            code.setCodeSystem("2.16.840.1.113883.5.4");
            code.setCodeSystemName("actCode");
            code.setCode("IMP");
            code.setDisplayName(displayName);
            inpatientEncounterEvent.setCode(code);

            final CS statusCode = FACTORY_HL7.createCS();
            statusCode.setCode("completed");
            inpatientEncounterEvent.setStatusCode(statusCode);

            final IVLTS effectiveTime = FACTORY_HL7.createIVLTS();
            effectiveTime.setNullFlavor(NullFlavor.NI);
            inpatientEncounterEvent.setEffectiveTime(effectiveTime);

//            final PQ lengthOfStayQuantity = FACTORY_HL7.createPQ();
//            lengthOfStayQuantity.setValue("5");
//            lengthOfStayQuantity.setUnit("d");
//            event.setLengthOfStayQuantity(lengthOfStayQuantity);


            final PRPAMT402003UV02Subject subject = FACTORY_HL7.createPRPAMT402003UV02Subject();
            subject.setTypeCode(ParticipationTargetSubject.SBJ);
//            subject.setContextControlCode(ContextControl.OP);
            final COCTMT050002UV07Patient patient = FACTORY_HL7.createCOCTMT050002UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final CS typeId2 = FACTORY_HL7.createCS();
            typeId2.setCode(clientUUID);
            patient.getId().add(typeId2);

            final COCTMT050002UV07Person uv07Person = FACTORY_HL7.createCOCTMT050002UV07Person();
            final JAXBElement<COCTMT050002UV07Person> patientPerson = FACTORY_HL7.createCOCTMT050002UV07PatientPatientPerson(uv07Person);

            uv07Person.setClassCode(EntityClass.PSN);
            uv07Person.setDeterminerCode(EntityDeterminer.INSTANCE);
            final II typeId3 = FACTORY_HL7.createII();
            typeId3.setExtension(client.getSnils());
            uv07Person.getId().add(typeId3);

            final PN pn = FACTORY_HL7.createPN();
            final EnGiven enGiven = FACTORY_HL7.createEnGiven();
            enGiven.getContent().add(client.getFirstName());
            JAXBElement<EnGiven> givenJAXBElement = FACTORY_HL7.createENGiven(enGiven);
            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = FACTORY_HL7.createEnGiven();
            enGiven2.getContent().add(client.getPatrName());
            JAXBElement<EnGiven> givenJAXBElement2 = FACTORY_HL7.createENGiven(enGiven2);
            givenJAXBElement2.setValue(enGiven2);
            pn.getContent().add(givenJAXBElement2);


            final EnFamily enFamily = FACTORY_HL7.createEnFamily();
            enFamily.getContent().add(client.getLastName());
            JAXBElement<EnFamily> enFamilyJAXBElement = FACTORY_HL7.createENFamily(enFamily);
            pn.getContent().add(enFamilyJAXBElement);

            uv07Person.getName().add(pn);

            final CE administrativeGenderCode = FACTORY_HL7.createCE();
            administrativeGenderCode.setCode("M");
            administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
            uv07Person.setAdministrativeGenderCode(administrativeGenderCode);

            final TS birthTime = FACTORY_HL7.createTS();

            final DateTime dateBirthday = new DateTime(client.getBirthDate());
            birthTime.setValue(dateBirthday.toString("YYYYddMM"));
            uv07Person.setBirthTime(birthTime);

            patientPerson.setValue(uv07Person);
            patient.setPatientPerson(patientPerson);
            subject.setPatient(patient);
            inpatientEncounterEvent.setSubject(subject);
            subject2.setInpatientEncounterEvent(inpatientEncounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain402003UV02.setControlActProcess(controlActProcess);

            ((misexchange.PRPAIN402003UV02) msg).setMessage(prpain402003UV02);

            logger.info("prepare message... \n\n {}", marshallMessage(msg, "misexchange"));

            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));

            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);
        }
        throw new SoapConnectionException("Bad connection to 1C Pharmacy");
    }


    /**
     * Формирование и отправка сообщения о переводе пациента между отделениями стационара PRPA_IN302011UV02
     */
    public static MCCIIN000002UV01 processMoving(
            final Action action,
            final OrgStructure orgStructureOut,
            final OrgStructure orgStructureIn) throws SoapConnectionException {
        try {
            final Event event = action.getEvent();
            final Patient client = event.getPatient();
            final String externalId = event.getExternalId();
            final String uuidExternal = event.getUuid().getUuid();
            final String uuidClient = client.getUuid().getUuid();
            final String uuidLocationOut = orgStructureOut.getUuid().getUuid();
            final String uuidLocationIn = orgStructureIn.getUuid().getUuid();

            final String uuidDocument = UUID.randomUUID().toString();
            logger.info("process MOVING document {}, action {}", uuidDocument, action);

            final Request msg = FACTORY_MIS.createPRPAIN302011UV02();
            final PRPAIN302011UV022 prpain302011UV022 = FACTORY_HL7.createPRPAIN302011UV022();
            prpain302011UV022.setITSVersion("XML_1.0");

            final II id = FACTORY_HL7.createII();
            id.setRoot(uuidDocument);
            prpain302011UV022.setId(id);

            final TS ts = FACTORY_HL7.createTS();
            final DateTime dateTime = new DateTime(action.getCreateDatetime());
            ts.setValue(dateTime.toString("yyyyMMddHHmmss"));
            prpain302011UV022.setCreationTime(ts);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN302011UV02");
            prpain302011UV022.setInteractionId(interactionId);

            final CS processCode = FACTORY_HL7.createCS();
            processCode.setCode("P");
            prpain302011UV022.setProcessingCode(processCode);


            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            prpain302011UV022.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = FACTORY_HL7.createCS();
            acceptAckCode.setCode("AL");
            prpain302011UV022.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = FACTORY_HL7.createII();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain302011UV022.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = FACTORY_HL7.createII();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain302011UV022.setSender(sender);

            // control act process
            final PRPAIN302011UV02MCAIMT700201UV01ControlActProcess controlActProcess = FACTORY_HL7.createPRPAIN302011UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN302011UV02MCAIMT700201UV01Subject2 subject2 = FACTORY_HL7.createPRPAIN302011UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT302011UV02EncounterEvent encounterEvent = FACTORY_HL7.createPRPAMT302011UV02EncounterEvent();
            encounterEvent.setClassCode(ActClassEncounter.ENC);
            encounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);

            final II idRoot = FACTORY_HL7.createII();
            idRoot.setRoot(uuidExternal);
            idRoot.setExtension(externalId);
            encounterEvent.getId().add(idRoot);

            final PRPAMT302011UV02Subject subjectPatient = FACTORY_HL7.createPRPAMT302011UV02Subject();
            subjectPatient.setTypeCode(ParticipationTargetSubject.SBJ);
            final COCTMT050001UV07Patient patient = FACTORY_HL7.createCOCTMT050001UV07Patient();
            final II patientIdRoot = FACTORY_HL7.createII();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);

            patient.setClassCode(RoleClassPatient.PAT);
            subjectPatient.setPatient(patient);
            encounterEvent.setSubject(subjectPatient);

            // location1
            final PRPAMT302011UV02Location1 location1 = FACTORY_HL7.createPRPAMT302011UV02Location1();
            location1.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut = FACTORY_HL7.createIVLTS();
            final TS ts1 = FACTORY_HL7.createTS();

            ts1.setValue(dateTime.toString("yyyyMMddHHmmss"));
            timeOut.setCenter(ts1);
            location1.setTime(timeOut);

            final CS statusCode = FACTORY_HL7.createCS();
            statusCode.setCode("active");
            location1.setStatusCode(statusCode);

            final PRPAMT302011UV02ServiceDeliveryLocation serviceDeliveryLocation = FACTORY_HL7.createPRPAMT302011UV02ServiceDeliveryLocation();
            serviceDeliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc = FACTORY_HL7.createII();
            idRootLoc.setRoot(uuidLocationOut);
            serviceDeliveryLocation.getId().add(idRootLoc);
            location1.setServiceDeliveryLocation(serviceDeliveryLocation);
            encounterEvent.setLocation1(location1);

            // location2
            final PRPAMT302011UV02Location2 location2 = FACTORY_HL7.createPRPAMT302011UV02Location2();
            location2.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut2 = FACTORY_HL7.createIVLTS();
            final TS ts2 = FACTORY_HL7.createTS();

            ts2.setValue(dateTime.toString("yyyyMMdd"));
            timeOut2.setCenter(ts2);
            location2.setTime(timeOut2);

            final CS statusCode2 = FACTORY_HL7.createCS();
            statusCode2.setCode("active");
            location2.setStatusCode(statusCode2);

            final PRPAMT302011UV02ServiceDeliveryLocation serviceDeliveryLocation2 = FACTORY_HL7.createPRPAMT302011UV02ServiceDeliveryLocation();
            serviceDeliveryLocation2.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc2 = FACTORY_HL7.createII();
            idRootLoc2.setRoot(uuidLocationIn);
            serviceDeliveryLocation2.getId().add(idRootLoc2);
            location2.setServiceDeliveryLocation(serviceDeliveryLocation2);
            encounterEvent.setLocation2(location2);

            subject2.setEncounterEvent(encounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain302011UV022.setControlActProcess(controlActProcess);

            ((PRPAIN302011UV02) msg).setMessage(prpain302011UV022);
            logger.info("prepare message... \n\n {}", marshallMessage(msg, "misexchange"));

            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));

            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);
        }
        throw new SoapConnectionException("Bad connection to 1C Pharmacy");
    }

    /**
     * Формирование и отправка сообщения об отмене предыдущего сообщения о переводе пациента между отделениями стационара
     * PRPA_IN302012UV02
     */
    public static MCCIIN000002UV01 processDelMoving(
            final Action action,
            final OrgStructure orgStructureOut,
            final OrgStructure orgStructureIn
    ) throws SoapConnectionException {
        try {
            final Event event = action.getEvent();
            final Patient client = event.getPatient();
            final String externalId = event.getExternalId();
            final String uuidExternal = event.getUuid().getUuid();
            final String uuidClient = client.getUuid().getUuid();
            final String uuidLocationOut = orgStructureOut.getUuid().getUuid();
            final String uuidLocationIn = orgStructureIn.getUuid().getUuid();

            final String uuidDocument = UUID.randomUUID().toString();
            logger.info("process DEL_MOVING document {}, action {}", uuidDocument, action);

            final Request msg = FACTORY_MIS.createPRPAIN302012UV02();
            final PRPAIN302012UV022 prpain302012UV022 = FACTORY_HL7.createPRPAIN302012UV022();
            prpain302012UV022.setITSVersion("XML_1.0");


            final II id = FACTORY_HL7.createII();
            id.setRoot(uuidDocument);
            prpain302012UV022.setId(id);

            final TS ts = FACTORY_HL7.createTS();
            final DateTime dateTime = new DateTime(action.getCreateDatetime());
            ts.setValue(dateTime.toString("yyyyMMddHHmmss"));
            prpain302012UV022.setCreationTime(ts);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("PRPA_IN302012UV02");
            prpain302012UV022.setInteractionId(interactionId);

            final CS processCode = FACTORY_HL7.createCS();
            processCode.setCode("P");
            prpain302012UV022.setProcessingCode(processCode);


            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            prpain302012UV022.setProcessingModeCode(processingModeCode);


            final CS acceptAckCode = FACTORY_HL7.createCS();
            acceptAckCode.setCode("AL");
            prpain302012UV022.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = FACTORY_HL7.createII();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            prpain302012UV022.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = FACTORY_HL7.createII();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            prpain302012UV022.setSender(sender);

            // control act process
            final PRPAIN302012UV02MCAIMT700201UV01ControlActProcess controlActProcess = FACTORY_HL7.createPRPAIN302012UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);
            final PRPAIN302012UV02MCAIMT700201UV01Subject2 subject2 = FACTORY_HL7.createPRPAIN302012UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT302012UV02EncounterEvent encounterEvent = FACTORY_HL7.createPRPAMT302012UV02EncounterEvent();
            encounterEvent.setClassCode(ActClassEncounter.ENC);
            encounterEvent.setMoodCode(ActMoodEventOccurrence.EVN);

            final II idRoot = FACTORY_HL7.createII();
            idRoot.setRoot(uuidExternal);
            idRoot.setExtension(externalId);
            encounterEvent.getId().add(idRoot);

            final PRPAMT302012UV02Subject subjectPatient = FACTORY_HL7.createPRPAMT302012UV02Subject();
            subjectPatient.setTypeCode(ParticipationTargetSubject.SBJ);
            final COCTMT050001UV07Patient patient = FACTORY_HL7.createCOCTMT050001UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final II patientIdRoot = FACTORY_HL7.createII();
            patientIdRoot.setRoot(uuidClient);
            patient.getId().add(patientIdRoot);
            subjectPatient.setPatient(patient);
            encounterEvent.setSubject(subjectPatient);

            // location1
            final PRPAMT302012UV02Location1 location1 = FACTORY_HL7.createPRPAMT302012UV02Location1();
            location1.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut = FACTORY_HL7.createIVLTS();
            final TS ts1 = FACTORY_HL7.createTS();
            ts1.setValue(dateTime.toString("yyyyMMdd"));
            timeOut.setCenter(ts1);
            location1.setTime(timeOut);

            final CS statusCode = FACTORY_HL7.createCS();
            statusCode.setCode("active");
            location1.setStatusCode(statusCode);

            final PRPAMT302012UV02ServiceDeliveryLocation serviceDeliveryLocation = FACTORY_HL7.createPRPAMT302012UV02ServiceDeliveryLocation();
            serviceDeliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc = FACTORY_HL7.createII();
            idRootLoc.setRoot(uuidLocationOut);
            serviceDeliveryLocation.getId().add(idRootLoc);
            location1.setServiceDeliveryLocation(serviceDeliveryLocation);
            encounterEvent.setLocation1(location1);

            // location2
            final PRPAMT302012UV02Location2 location2 = FACTORY_HL7.createPRPAMT302012UV02Location2();
            location2.setTypeCode(ParticipationTargetLocation.LOC);
            final IVLTS timeOut2 = FACTORY_HL7.createIVLTS();
            final TS ts2 = FACTORY_HL7.createTS();
            ts2.setValue(dateTime.toString("yyyyMMdd"));
            timeOut2.setCenter(ts2);
            location2.setTime(timeOut2);

            final CS statusCode2 = FACTORY_HL7.createCS();
            statusCode2.setCode("active");
            location2.setStatusCode(statusCode2);

            final PRPAMT302012UV02ServiceDeliveryLocation serviceDeliveryLocation2 = FACTORY_HL7.createPRPAMT302012UV02ServiceDeliveryLocation();
            serviceDeliveryLocation2.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II idRootLoc2 = FACTORY_HL7.createII();
            idRootLoc2.setRoot(uuidLocationIn);
            serviceDeliveryLocation2.getId().add(idRootLoc2);
            location2.setServiceDeliveryLocation(serviceDeliveryLocation2);
            encounterEvent.setLocation2(location2);

            subject2.setEncounterEvent(encounterEvent);
            controlActProcess.getSubject().add(subject2);
            prpain302012UV022.setControlActProcess(controlActProcess);

            ((PRPAIN302012UV02) msg).setMessage(prpain302012UV022);
            logger.info("prepare message... \n\n {}", marshallMessage(msg, "misexchange"));
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(msg);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));

            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);
        }
        throw new SoapConnectionException("Bad connection to 1C Pharmacy");
    }

    /**
     * Назначения врача
     */
    public static MCCIIN000002UV01 processRCMRIN000002UV02(
            final Action action,
            final String clientUUID,
            final String externalId,
            final Patient client,
            final Staff createPerson,
            final String organizationName,
            final String externalUUID,
            final String custodianUUID,
            final Staff doctorPerson
    ) throws SoapConnectionException {
        try {
            final String uuidDocument = UUID.randomUUID().toString();
            OrgStructure orgStruct = new OrgStructure(1);
            orgStruct.setName("ФНКЦ");
            final ru.korus.tmis.core.entity.model.UUID uuidStruct = new ru.korus.tmis.core.entity.model.UUID();
            uuidStruct.setUuid("5555db7d-5555-43b8-9617-e2d2f229dac3");
            orgStruct.setUuid(uuidStruct);

            logger.info("process RCMRIN000002UV02 document {}, action {}", uuidDocument, action);

            final POCDMT000040ClinicalDocument clinicalDocument =
                    getClinicalDocument(
                            action,
                            clientUUID,
                            externalId,
                            client,
                            organizationName,
                            custodianUUID,
                            externalUUID,
                            orgStruct,
                            doctorPerson,
                            getRandomDrug());
            final String innerDocument = marshallMessage(clinicalDocument, "org.hl7.v3");
            logger.info("prepare inner document... \n\n{}", innerDocument);


            final Request request = FACTORY_MIS.createRCMRIN000002UV02();
            final RCMRIN000002UV022 message = FACTORY_HL7.createRCMRIN000002UV022();

            message.setITSVersion("XML_1.0");
            final II idRoot = FACTORY_HL7.createII();
            idRoot.setRoot(uuidDocument);
            message.setId(idRoot);

            final TS creationTime = FACTORY_HL7.createTS();
            final DateTime dateTime = new DateTime(action.getCreateDatetime());
            creationTime.setValue(dateTime.toString("yyyyMMddHHmmss"));
            message.setCreationTime(creationTime);

            final II interactionId = FACTORY_HL7.createII();
            interactionId.setRoot("2.16.840.1.113883.1.18");
            interactionId.setExtension("RCMR_IN000002UV02");
            message.setInteractionId(interactionId);

            final CS processCode = FACTORY_HL7.createCS();
            processCode.setCode("P");
            message.setProcessingCode(processCode);

            final CS processingModeCode = FACTORY_HL7.createCS();
            processingModeCode.setCode("T");
            message.setProcessingModeCode(processingModeCode);

            final CS acceptAckCode = FACTORY_HL7.createCS();
            acceptAckCode.setCode("AL");
            message.setAcceptAckCode(acceptAckCode);

            // receiver
            final MCCIMT000100UV01Receiver uv01Receiver = FACTORY_HL7.createMCCIMT000100UV01Receiver();
            uv01Receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device = FACTORY_HL7.createMCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii = FACTORY_HL7.createII();
            ii.setNullFlavor(NullFlavor.NI);
            device.getId().add(ii);
            uv01Receiver.setDevice(device);
            message.getReceiver().add(uv01Receiver);

            // sender
            final MCCIMT000100UV01Sender sender = FACTORY_HL7.createMCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device1 = FACTORY_HL7.createMCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II ii1 = FACTORY_HL7.createII();
            ii1.setNullFlavor(NullFlavor.NI);
            device1.getId().add(ii1);
            sender.setDevice(device1);
            message.setSender(sender);

            final RCMRIN000002UV02MCAIMT700201UV01ControlActProcess
                    controlActProcess = FACTORY_HL7.createRCMRIN000002UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final ED text = FACTORY_HL7.createED();
            text.setMediaType("multipart/related");
            text.setRepresentation(BinaryDataEncoding.B_64);

            text.getContent().add("MIME-Version: 1.0\n");
            text.getContent().add("Content-Type: multipart/related; boundary=\"HL7-CDA-boundary\"; type=\"text/xml\";\n");
            text.getContent().add("Content-Transfer-Encoding: BASE64\n\n");
            text.getContent().add("--HL7-CDA-boundary \n");
            text.getContent().add("Content-Type: text/xml; charset=UTF-8\n\n");
            text.getContent().add(javax.xml.bind.DatatypeConverter.printBase64Binary(innerDocument.getBytes()));
            text.getContent().add("\n\n--HL7-CDA-boundary-- ");

            controlActProcess.setText(text);
            message.setControlActProcess(controlActProcess);

            // ------------------------------------

            ((RCMRIN000002UV02) request).setMessage(message);
            logger.info("prepare message... \n\n {}", marshallMessage(request, "misexchange"));
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            logger.info("Connection successful. Result: {} \n\n {}", mcciin000002UV01, marshallMessage(mcciin000002UV01, "org.hl7.v3"));


            return mcciin000002UV01;
        } catch (Exception e) {
            logger.error("Exception: " + e, e);
        }
        throw new SoapConnectionException("Connection error");
    }

    private static POCDMT000040ClinicalDocument getClinicalDocument(
            final Action action,
            final String clientUUID,
            final String externalId,
            final Patient client,
            final String organizationName,
            final String custodianUUID,
            final String externalUUID,
            final OrgStructure orgStructure,
            final Staff doctor,
            POCDMT000040LabeledDrug drug) {

        final String uuidDocument = UUID.randomUUID().toString();
        // Версия документа, должна инкрементироваться при повторной передаче
        final String versionOfDocument = "1";

        final POCDMT000040ClinicalDocument clinicalDocument = FACTORY_HL7.createPOCDMT000040ClinicalDocument();

        final CS realmCode = FACTORY_HL7.createCS();
        realmCode.setCode("RU");
        clinicalDocument.getRealmCode().add(realmCode);

        final POCDMT000040InfrastructureRootTypeId rootTypeId = FACTORY_HL7.createPOCDMT000040InfrastructureRootTypeId();

        rootTypeId.setExtension("POCD_HD000040");
        rootTypeId.setRoot("2.16.840.1.113883.1.3");
        clinicalDocument.setTypeId(rootTypeId);

        final II idRoot = FACTORY_HL7.createII();

        idRoot.setRoot(uuidDocument);
        clinicalDocument.setId(idRoot);
        final CE processingCode = FACTORY_HL7.createCE();
        processingCode.setCode("18610-6");
        processingCode.setDisplayName("MEDICATION ADMINISTERED");
        processingCode.setCodeSystem("2.16.840.1.113883.6.1");
        processingCode.setCodeSystemName("LOINC");
        clinicalDocument.setCode(processingCode);

        final TS creationTime = FACTORY_HL7.createTS();
        final DateTime dateTime = new DateTime(action.getCreateDatetime());
        creationTime.setValue(dateTime.toString("yyyyMMddHHmmss"));
        clinicalDocument.setEffectiveTime(creationTime);


        final CE confidentialityCode = FACTORY_HL7.createCE();
        confidentialityCode.setCode("N");
        confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");
        clinicalDocument.setConfidentialityCode(confidentialityCode);


        final CS languageCode = FACTORY_HL7.createCS();
        languageCode.setCode("ru-RU");
        clinicalDocument.setLanguageCode(languageCode);


        final II setId = FACTORY_HL7.createII();
        setId.setRoot(uuidDocument);
        clinicalDocument.setSetId(setId);


        final INT versionNumber = FACTORY_HL7.createINT();
        versionNumber.setValue(new BigInteger(versionOfDocument));
        clinicalDocument.setVersionNumber(versionNumber);

        // --- record target
        final POCDMT000040RecordTarget recordTarget = FACTORY_HL7.createPOCDMT000040RecordTarget();
        final POCDMT000040PatientRole patientRole = FACTORY_HL7.createPOCDMT000040PatientRole();

        final II idRootPatient = FACTORY_HL7.createII();
        idRootPatient.setRoot(clientUUID);
        idRootPatient.setExtension(externalId);
        patientRole.getId().add(idRootPatient);

        final POCDMT000040Patient patient = FACTORY_HL7.createPOCDMT000040Patient();
        final PN pn = FACTORY_HL7.createPN();
        final EnGiven enGiven = FACTORY_HL7.createEnGiven();
        enGiven.getContent().add(client.getFirstName());
        JAXBElement<EnGiven> givenJAXBElement = FACTORY_HL7.createENGiven(enGiven);
        pn.getContent().add(givenJAXBElement);

        final EnGiven enGiven2 = FACTORY_HL7.createEnGiven();
        enGiven2.getContent().add(client.getPatrName());
        JAXBElement<EnGiven> givenJAXBElement2 = FACTORY_HL7.createENGiven(enGiven2);
        givenJAXBElement2.setValue(enGiven2);
        pn.getContent().add(givenJAXBElement2);

        final EnFamily enFamily = FACTORY_HL7.createEnFamily();
        enFamily.getContent().add(client.getLastName());
        JAXBElement<EnFamily> enFamilyJAXBElement = FACTORY_HL7.createENFamily(enFamily);
        pn.getContent().add(enFamilyJAXBElement);
        patient.getName().add(pn);


        final CE administrativeGenderCode = FACTORY_HL7.createCE();
        administrativeGenderCode.setCode("M");
        administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
        patient.setAdministrativeGenderCode(administrativeGenderCode);

        final TS birthTime = FACTORY_HL7.createTS();

        final DateTime dateBirthday = new DateTime(client.getBirthDate());
        birthTime.setValue(dateBirthday.toString("yyyyMMdd"));
        patient.setBirthTime(birthTime);

        patientRole.setPatient(patient);
        recordTarget.setPatientRole(patientRole);
        clinicalDocument.getRecordTarget().add(recordTarget);

        // --- author
        final POCDMT000040Author author = FACTORY_HL7.createPOCDMT000040Author();
        final TS time = FACTORY_HL7.createTS();
        final DateTime dateNow = DateTime.now();
        time.setValue(dateNow.toString("yyyyMMddHHmmss")); //todo
        author.setTime(time);

        final POCDMT000040AssignedAuthor assignedAuthor = FACTORY_HL7.createPOCDMT000040AssignedAuthor();
        final II idRootAuthor = FACTORY_HL7.createII();
        idRootAuthor.setRoot(doctor.getUuid().getUuid()); //todo
        assignedAuthor.getId().add(idRootAuthor);

        final POCDMT000040Person assignedPerson = FACTORY_HL7.createPOCDMT000040Person();
        final PN authorPerson = FACTORY_HL7.createPN();

        final EnPrefix enPrefix = FACTORY_HL7.createEnPrefix();
        enPrefix.getContent().add(doctor.getSpeciality().getName());
        authorPerson.getContent().add(FACTORY_HL7.createENPrefix(enPrefix));

        final EnGiven enGivenAuthor = FACTORY_HL7.createEnGiven();
        enGivenAuthor.getContent().add(doctor.getFirstName() + " " + doctor.getPatrName());  // todo
        authorPerson.getContent().add(FACTORY_HL7.createENGiven(enGivenAuthor));

        final EnFamily enFamilyAuthor = FACTORY_HL7.createEnFamily();
        enFamilyAuthor.getContent().add(doctor.getLastName());   //todo
        authorPerson.getContent().add(FACTORY_HL7.createENFamily(enFamilyAuthor));

        assignedPerson.getName().add(authorPerson);
        assignedAuthor.setAssignedPerson(assignedPerson);
        author.setAssignedAuthor(assignedAuthor);
        clinicalDocument.getAuthor().add(author);


        // --- custodian
        final POCDMT000040Custodian custodian = FACTORY_HL7.createPOCDMT000040Custodian();
        final POCDMT000040AssignedCustodian assignedCustodian = FACTORY_HL7.createPOCDMT000040AssignedCustodian();
        final POCDMT000040CustodianOrganization representedCustodianOrganization = FACTORY_HL7.createPOCDMT000040CustodianOrganization();
        final II idRootCustodian = FACTORY_HL7.createII();
        idRootCustodian.setRoot(orgStructure.getUuid().getUuid());

        final ON name = FACTORY_HL7.createON();
        name.getContent().add(orgStructure.getName()); //todo
        representedCustodianOrganization.setName(name);
        representedCustodianOrganization.getId().add(idRootCustodian);
        assignedCustodian.setRepresentedCustodianOrganization(representedCustodianOrganization);
        custodian.setAssignedCustodian(assignedCustodian);
        clinicalDocument.setCustodian(custodian);


        // -- componentOf
        final POCDMT000040Component1 componentOf = FACTORY_HL7.createPOCDMT000040Component1();
        final POCDMT000040EncompassingEncounter encompassingEncounter = FACTORY_HL7.createPOCDMT000040EncompassingEncounter();

        final II idRootEncounter = FACTORY_HL7.createII();
        idRootEncounter.setRoot(externalUUID); //todo
        idRootEncounter.setExtension(externalId);  //todo
        encompassingEncounter.getId().add(idRootEncounter);

        final CE code = FACTORY_HL7.createCE();
        code.setCode("IMP");
        code.setCodeSystem("2.16.840.1.113883.5.4");
        code.setCodeSystemName("actCode");
        code.setDisplayName("Inpatient encounter");
        encompassingEncounter.setCode(code);

        final IVLTS value = FACTORY_HL7.createIVLTS();
        value.setNullFlavor(NullFlavor.NI);
        encompassingEncounter.setEffectiveTime(value);

        componentOf.setEncompassingEncounter(encompassingEncounter);
        clinicalDocument.setComponentOf(componentOf);

        // --- component
        final POCDMT000040Component2 component = FACTORY_HL7.createPOCDMT000040Component2();

        final POCDMT000040StructuredBody structuredBody = FACTORY_HL7.createPOCDMT000040StructuredBody();
        final POCDMT000040Component3 component3 = FACTORY_HL7.createPOCDMT000040Component3();
        final POCDMT000040Section section = FACTORY_HL7.createPOCDMT000040Section();
        final StrucDocText text = FACTORY_HL7.createStrucDocText();
        text.getContent().add("Take captopril 25mg PO every 12 hours, starting on Jan 01, 2002, ending on Feb 01, 2002");

        JAXBElement<StrucDocList> docItemList = FACTORY_HL7.createStrucDocItemList(FACTORY_HL7.createStrucDocList());
        final StrucDocList docList = FACTORY_HL7.createStrucDocList();

        final StrucDocItem item = FACTORY_HL7.createStrucDocItem();
        item.getContent().add("Анальгин");
        docList.getItem().add(item);

        final StrucDocItem item2 = FACTORY_HL7.createStrucDocItem();
        item2.getContent().add("Esidrix");

        docList.getItem().add(item2);

        docItemList.setValue(docList);

        text.getContent().add(docItemList);


        final JAXBElement<StrucDocContent> strucDocItemContent = FACTORY_HL7.createStrucDocItemContent(FACTORY_HL7.createStrucDocContent());
        final StrucDocContent content = FACTORY_HL7.createStrucDocContent();
        content.getContent().add("Во время еды");
        content.setID1("patient-instruction");
        strucDocItemContent.setValue(content);
        text.getContent().add(strucDocItemContent);

        section.setText(text);


        final POCDMT000040Entry entry = FACTORY_HL7.createPOCDMT000040Entry();
        //----------------
        final POCDMT000040SubstanceAdministration substanceAdministration = FACTORY_HL7.createPOCDMT000040SubstanceAdministration();
        substanceAdministration.setClassCode(ActClass.SBADM);
        substanceAdministration.setMoodCode(XDocumentSubstanceMood.EVN);  // назначение
        final II idRoot2 = FACTORY_HL7.createII();
        idRoot2.setRoot(UUID.randomUUID().toString()); // UUID назначения
        substanceAdministration.getId().add(idRoot2);

        final II idRootEx = FACTORY_HL7.createII();
        idRootEx.setExtension("ОМС");
        substanceAdministration.getId().add(idRootEx);


        final IVLTS ivlts = FACTORY_HL7.createIVLTS();
        final IVXBTS low = FACTORY_HL7.createIVXBTS();
        low.setValue("20121010");  // todo
        ivlts.setLow(low);

        final IVXBTS high = FACTORY_HL7.createIVXBTS();
        high.setValue("20121020"); //todo
        ivlts.setHigh(high);
        substanceAdministration.getEffectiveTime().add(ivlts);


        final PIVLTS pivlts = FACTORY_HL7.createPIVLTS();
        pivlts.setOperator(SetOperator.A);
        final PQ period = FACTORY_HL7.createPQ();
        period.setValue("12"); // todo
        period.setUnit("h");
        pivlts.setPeriod(period);
        substanceAdministration.getEffectiveTime().add(pivlts);


        final CE priorityCode = FACTORY_HL7.createCE();
        priorityCode.setCode("R");
        priorityCode.setCodeSystem("2.16.840.1.113883.5.7");
        priorityCode.setCodeSystemName("ActPriority");
        priorityCode.setDisplayName("Планово");
        substanceAdministration.setPriorityCode(priorityCode);


        final CE routeCode = FACTORY_HL7.createCE();
        routeCode.setCode("IV");
        routeCode.setCodeSystem("2.16.840.1.113883.5.112");
        routeCode.setCodeSystemName("RouteOfAdministration");
        substanceAdministration.setRouteCode(routeCode);


        final IVLPQ doseQuantity = FACTORY_HL7.createIVLPQ();
        final PQ center = FACTORY_HL7.createPQ();
        center.setUnit("mg"); //todo
        center.setValue("25");
        final PQR pqr = FACTORY_HL7.createPQR();
        pqr.setCodeSystemName("RLS");
        final ED originalText = FACTORY_HL7.createED();
        originalText.getContent().add("мг"); //todo
        pqr.setOriginalText(originalText);
        center.getTranslation().add(pqr);
        doseQuantity.setCenter(center);
        substanceAdministration.setDoseQuantity(doseQuantity);


        // -consumable
        final POCDMT000040Consumable consumable = FACTORY_HL7.createPOCDMT000040Consumable();
        final POCDMT000040ManufacturedProduct manufacturedProduct = FACTORY_HL7.createPOCDMT000040ManufacturedProduct();
        final POCDMT000040LabeledDrug manufacturedLabeledDrug = FACTORY_HL7.createPOCDMT000040LabeledDrug();

        final CE code1 = FACTORY_HL7.createCE();
        code1.setCodeSystem("1.2.643.2.0");
        code1.setCodeSystemName("RLS");


        final CD cd = FACTORY_HL7.createCD();
        final CD cdTrans = FACTORY_HL7.createCD();
        cdTrans.setCode("Фенистил");
        cdTrans.setDisplayName("Фенистил");
        cdTrans.setCodeSystemName("RLS_ACTMATTERS");
        cd.getTranslation().add(cdTrans);
        code1.getTranslation().add(cd);


        final CD cdTrans2 = FACTORY_HL7.createCD();

        cdTrans2.setCode("капли");
        cdTrans2.setDisplayName("капли");
        cdTrans2.setCodeSystemName("RLS_CLSDRUGFORMS");

        final CR cr = FACTORY_HL7.createCR();
        final CV cv = FACTORY_HL7.createCV();
        cv.setCode("DFMASS");
        cv.setCodeSystemName("RLS");
        cr.setName(cv);

        final CD cdValue = FACTORY_HL7.createCD();
        cdValue.setCode("мг");
        cdValue.setDisplayName("мг");
        cdValue.setCodeSystemName("RLS_MASSUNITS");
        final ED originalText1 = FACTORY_HL7.createED();
        originalText1.getContent().add("5");
        cdValue.setOriginalText(originalText1);
        cr.setValue(cdValue);

        cdTrans2.getQualifier().add(cr);
        code1.getTranslation().add(cdTrans2);

        manufacturedLabeledDrug.setCode(code1);


        manufacturedProduct.setManufacturedLabeledDrug(/*manufacturedLabeledDrug*/drug);
        consumable.setManufacturedProduct(manufacturedProduct);
        substanceAdministration.setConsumable(consumable);


        entry.setSubstanceAdministration(substanceAdministration);

        //-----------------
        section.getEntry().add(entry);

        component3.setSection(section);

        structuredBody.getComponent().add(component3);

        component.setStructuredBody(structuredBody);
        clinicalDocument.setComponent(component);


        return clinicalDocument;
    }


    public static POCDMT000040LabeledDrug getRandomDrug() {
        //logger.info("prepare message... \n\n {}", marshallMessage(request, "misexchange"));
        final DrugList drugList = new MISExchange().getMISExchangeSoap().getDrugList();
        final List<POCDMT000040LabeledDrug> drug = drugList.getDrug();
        logger.info("Connection successful...");

        //     final POCDMT000040LabeledDrug randomDrug = drug.get(rnd.nextInt(drug.size()));
        for (POCDMT000040LabeledDrug d : drug) {
            if (d.getCode().getCode().equals("20044")) {
                logger.info("Fetch random drug {}", marshallMessage(d, "org.hl7.v3"));
                return d;
            }
        }
        return null;
    }


    private static String marshallMessage(Object msg, String contextPath) {
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
