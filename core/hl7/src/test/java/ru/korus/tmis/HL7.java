package ru.korus.tmis;

import org.hl7.v3.*;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
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

public class HL7 {

    @Test(enabled = false)
    public void processPRPA() {
        ObjectFactory factory = new ObjectFactory();
        try {
            MISExchange misExchange = new MISExchange();
            ProcessHL7V3Message hl7V3Message = factory.createProcessHL7V3Message();
//            PRPAIN402002UV02MCCIMT000100UV01Message hl7V3Message = factory.createPRPAIN402002UV02MCCIMT000100UV01Message();
            TS ts = factory.createTS();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            ts.setValue(sdf.format(new Date()));


            final PRPAIN402002UV02 prpain402002UV02 = factory.createPRPAIN402002UV02();
            prpain402002UV02.setITSVersion("XML_1.0");

            prpain402002UV02.setCreationTime(ts);

            final CS askCode = new CS();
            askCode.setCode("AL");
            prpain402002UV02.setAcceptAckCode(askCode);

            final II id = new II();
            id.setRoot(UUID.randomUUID().toString());
            prpain402002UV02.setId(id);


            final II interactionId = new II();
            interactionId.setRoot("2.16.840.1.11.3883.1.18");
            interactionId.setExtension("PRPA_IN402002UV02");
            prpain402002UV02.setInteractionId(interactionId);


            final CS processingCode = new CS();
            processingCode.setCode("P");
            prpain402002UV02.setProcessingCode(processingCode);

            final CS processingModeCode = new CS();
            processingModeCode.setCode("T");
            prpain402002UV02.setProcessingModeCode(processingModeCode);


            final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
            sender.setTypeCode(CommunicationFunctionType.SND);
            final MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
            device.setClassCode(EntityClassDevice.DEV);
            device.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II typeId = new II();
            typeId.setNullFlavor(NullFlavor.fromValue("NI"));
            device.getId().add(typeId);
            sender.setDevice(device);
            prpain402002UV02.setSender(sender);


            final MCCIMT000100UV01Receiver receiver = new MCCIMT000100UV01Receiver();
            receiver.setTypeCode(CommunicationFunctionType.RCV);
            final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
            device1.setClassCode(EntityClassDevice.DEV);
            device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
            final II idReceiver = new II();
            idReceiver.setNullFlavor(NullFlavor.NI);
            device1.getId().add(idReceiver);
            receiver.setDevice(device1);
            prpain402002UV02.getReceiver().add(receiver);

            final PRPAIN402002UV02MCAIMT700201UV01ControlActProcess controlActProcess = new PRPAIN402002UV02MCAIMT700201UV01ControlActProcess();
            controlActProcess.setClassCode(ActClassControlAct.CACT);
            controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

            final PRPAIN402002UV02MCAIMT700201UV01Subject2 subject2 = new PRPAIN402002UV02MCAIMT700201UV01Subject2();
            subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);

            final PRPAMT402002UV02InpatientEncounterEvent event = factory.createPRPAMT402002UV02InpatientEncounterEvent();
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


            final PRPAMT402002UV02Subject subject = new PRPAMT402002UV02Subject();
            subject.setTypeCode(ParticipationTargetSubject.SBJ);
            subject.setContextControlCode(ContextControl.OP);
            final COCTMT050002UV07Patient patient = new COCTMT050002UV07Patient();
            patient.setClassCode(RoleClassPatient.PAT);
            final CS typeId2 = new CS();
            typeId2.setCode("123");
            patient.getId().add(typeId2);

            final COCTMT050002UV07Person uv07Person = new COCTMT050002UV07Person();
            final JAXBElement<COCTMT050002UV07Person> patientPerson = factory.createCOCTMT050002UV07PatientPatientPerson(uv07Person);

            uv07Person.setClassCode(EntityClass.PSN);
            uv07Person.setDeterminerCode(EntityDeterminer.INSTANCE);
            final II typeId3 = new II();
            typeId3.setExtension("00001");
            uv07Person.getId().add(typeId3);

            final PN pn = new PN();
            final EnGiven enGiven = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement = factory.createENGiven(enGiven);
            pn.getContent().add(givenJAXBElement);

            final EnGiven enGiven2 = new EnGiven();
            JAXBElement<EnGiven> givenJAXBElement2 = factory.createENGiven(enGiven2);
            pn.getContent().add(givenJAXBElement2);


            final EnFamily enFamily = new EnFamily();

            JAXBElement<EnFamily> enFamilyJAXBElement = factory.createENFamily(enFamily);
            pn.getContent().add(enFamilyJAXBElement);

            uv07Person.getName().add(pn);

            final CE administrativeGenderCode = new CE();
            administrativeGenderCode.setCode("M");
            administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
            uv07Person.setAdministrativeGenderCode(administrativeGenderCode);

            final TS birthTime = new TS();
            birthTime.setValue("1221223");
            uv07Person.setBirthTime(birthTime);

            patientPerson.setValue(uv07Person);


            patient.setPatientPerson(patientPerson);

            subject.setPatient(patient);


            event.setSubject(subject);


            final PRPAMT402002UV02Admitter admitter = new PRPAMT402002UV02Admitter();
            admitter.setNullFlavor(NullFlavor.NI);
            admitter.setTypeCode(ParticipationAdmitter.ADM);
            final IVLTS time = new IVLTS();
            time.setNullFlavor(NullFlavor.NI);
            admitter.setTime(time);

            final COCTMT090100UV01AssignedPerson assignedPerson = new COCTMT090100UV01AssignedPerson();
            assignedPerson.setClassCode(RoleClassAssignedEntity.ASSIGNED);
            admitter.setAssignedPerson(assignedPerson);

            event.setAdmitter(admitter);


            final PRPAMT402002UV02Location1 uv02Location1 = factory.createPRPAMT402002UV02Location1();
            uv02Location1.setTypeCode(ParticipationTargetLocation.LOC);
            uv02Location1.setTime(time);
            final CS statusCode1 = new CS();
            statusCode1.setCode("active");
            uv02Location1.setStatusCode(statusCode1);

            final PRPAMT402002UV02ServiceDeliveryLocation deliveryLocation = new PRPAMT402002UV02ServiceDeliveryLocation();
            deliveryLocation.setClassCode(RoleClassServiceDeliveryLocation.SDLOC);
            final II typeId4 = new II();
            typeId4.setRoot(UUID.randomUUID().toString());
            deliveryLocation.getId().add(typeId4);
            uv02Location1.setServiceDeliveryLocation(deliveryLocation);

            event.getLocation().add(uv02Location1);


            subject2.setInpatientEncounterEvent(event);
            controlActProcess.getSubject().add(subject2);
            prpain402002UV02.setControlActProcess(controlActProcess);

            hl7V3Message.setMessage(prpain402002UV02);



            JAXBContext jaxbContext = JAXBContext.newInstance("org.hl7.v3");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            //  final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
           // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "urn:hl7-org:v3 PRPA_IN302011UV02.xsd");
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");

            marshaller.marshal(hl7V3Message, System.out);

            misExchange.getMISExchangeSoap().processHL7V3Message(hl7V3Message);
            System.out.println("send");



            System.out.println("------------- = ");



//            final DrugsList drugsList = misExchange.getMISExchangeSoap().getDrugsList();
//            System.out.println("drugsList = " + Arrays.asList(drugsList));
            //     processHL7V3Message.


        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
}
