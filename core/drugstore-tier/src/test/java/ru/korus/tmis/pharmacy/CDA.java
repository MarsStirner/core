package ru.korus.tmis.pharmacy;

import misexchange.MISExchange;
import misexchange.ObjectFactory;
import misexchange.RCMRIN000002UV02;
import misexchange.Request;
import org.hl7.v3.*;
import org.testng.annotations.Test;

import javax.xml.bind.*;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 10.12.12
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class CDA {


    public static final String EXTERNAL_ID = "2011/5";
    public static final String PATIENT_UUID = UUID.randomUUID().toString();
    public static final String PATIENT_NAME = "name";
    public static final String PATIENT_PATNAME = "patname";
    public static final String PATIENT_FAMILY = "fam";
    public static final String AUTHOR_NAME = "name";
    public static final String AUTHOR_PATNAME = "patname";
    public static final String AUTHOR_FAMILY = "fam";
    public static final String ORG_NAME = "ФНКЦ";
    private static JAXBContext jaxbContext = null;

    static {
        try {
            jaxbContext = JAXBContext.newInstance("org.hl7.v3");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void cdaTest() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        ObjectFactory factory = new ObjectFactory();
        org.hl7.v3.ObjectFactory f = new org.hl7.v3.ObjectFactory();

        final POCDMT000040ClinicalDocument clinicalDocument = getClinicalDocument(f);

        try {
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(clinicalDocument, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------- = ");


        final Request request = factory.createRCMRIN000002UV02();
        final RCMRIN000002UV022 message = f.createRCMRIN000002UV022();

        message.setITSVersion("XML_1.0");
        final II idRoot = f.createII();
        idRoot.setRoot(UUID.randomUUID().toString());
        message.setId(idRoot);

        final TS creationTime = f.createTS();
        creationTime.setValue(sdf.format(/*action.getCreateDatetime()*/new Date()));
        message.setCreationTime(creationTime);

        final II interactionId = f.createII();
        interactionId.setRoot("2.16.840.1.113883.1.18");
        interactionId.setExtension("RCMR_IN000002UV02");
        message.setInteractionId(interactionId);

        final CS processCode = new CS();
        processCode.setCode("P");
        message.setProcessingCode(processCode);

        final CS processingModeCode = new CS();
        processingModeCode.setCode("T");
        message.setProcessingModeCode(processingModeCode);

        final CS acceptAckCode = new CS();
        acceptAckCode.setCode("AL");
        message.setAcceptAckCode(acceptAckCode);

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
        message.getReceiver().add(uv01Receiver);

        // sender
        final MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();
        sender.setTypeCode(CommunicationFunctionType.SND);
        final MCCIMT000100UV01Device device1 = new MCCIMT000100UV01Device();
        device1.setClassCode(EntityClassDevice.DEV);
        device1.setDeterminerCode(EntityDeterminerSpecific.INSTANCE);
        final II ii1 = new II();
        ii1.setNullFlavor(NullFlavor.NI);
        device1.getId().add(ii1);
        sender.setDevice(device1);
        message.setSender(sender);

        final RCMRIN000002UV02MCAIMT700201UV01ControlActProcess
                controlActProcess = new RCMRIN000002UV02MCAIMT700201UV01ControlActProcess();
        controlActProcess.setClassCode(ActClassControlAct.CACT);
        controlActProcess.setMoodCode(XActMoodIntentEvent.EVN);

        final ED text = f.createED();
        text.setMediaType("multipart/related");
        text.setRepresentation(BinaryDataEncoding.B_64);

        text.getContent().add("MIME-Version: 1.0\n");
        text.getContent().add("Content-Type: multipart/related; boundary=\"HL7-CDA-boundary\"; type=\"text/xml\";\n");
        text.getContent().add("Content-Transfer-Encoding: BASE64\n\n");
        text.getContent().add("--HL7-CDA-boundary \n");
        text.getContent().add("Content-Type: text/xml; charset=UTF-8\n\n");
        text.getContent().add(javax.xml.bind.DatatypeConverter.printBase64Binary(marshalDocument(clinicalDocument).getBytes()));
        text.getContent().add("\n\n--HL7-CDA-boundary-- ");

        controlActProcess.setText(text);
        message.setControlActProcess(controlActProcess);

        // ------------------------------------

        ((RCMRIN000002UV02) request).setMessage(message);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("misexchange");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(request, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------send---------------");

        try {
            final MCCIIN000002UV01 mcciin000002UV01 = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            System.out.println("mcciin000002UV01 = " + mcciin000002UV01);

            try {

                final Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(mcciin000002UV01, System.out);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String marshalDocument(POCDMT000040ClinicalDocument document) {

        final StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.hl7.v3");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(document, writer);
            // logger.info("marchall message: {}", writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // logger.error("jaxb exception", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                //skip
            }
        }
        return writer.toString();
    }

    private POCDMT000040ClinicalDocument getClinicalDocument(org.hl7.v3.ObjectFactory f) {

        final POCDMT000040ClinicalDocument clinicalDocument = f.createPOCDMT000040ClinicalDocument();

        final CS realmCode = f.createCS();
        realmCode.setCode("RU");
        clinicalDocument.getRealmCode().add(realmCode);


        //       final II typeId = new II();
        final POCDMT000040InfrastructureRootTypeId rootTypeId = new POCDMT000040InfrastructureRootTypeId();

        rootTypeId.setExtension("POCD_HD000040");
        rootTypeId.setRoot("2.16.840.1.113883.1.3");
        clinicalDocument.setTypeId(rootTypeId);

        final II idRoot = f.createII();
        idRoot.setRoot(UUID.randomUUID().toString());
        clinicalDocument.setId(idRoot);

        final CE processingCode = new CE();
        processingCode.setCode("18610-6");
        processingCode.setDisplayName("MEDICATION ADMINISTERED");
        processingCode.setCodeSystem("2.16.840.1.113883.6.1");
        processingCode.setCodeSystemName("LOINC");
        clinicalDocument.setCode(processingCode);

        final TS creationTime = new TS();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        creationTime.setValue(sdf.format(new Date()));
        clinicalDocument.setEffectiveTime(creationTime);


        final CE confidentialityCode = new CE();
        confidentialityCode.setCode("N");
        confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");
        clinicalDocument.setConfidentialityCode(confidentialityCode);


        final CS languageCode = new CS();
        languageCode.setCode("ru-RU");
        clinicalDocument.setLanguageCode(languageCode);


        final II setId = new II();
        setId.setRoot(UUID.randomUUID().toString());
        clinicalDocument.setSetId(setId);


        final INT versionNumber = new INT();
        versionNumber.setValue(new BigInteger("1"));
        clinicalDocument.setVersionNumber(versionNumber);

        // --- record target
        final POCDMT000040RecordTarget recordTarget = new POCDMT000040RecordTarget();
        final POCDMT000040PatientRole patientRole = new POCDMT000040PatientRole();

        final II idRootPatient = new II();
        idRootPatient.setRoot(PATIENT_UUID);
        idRootPatient.setExtension(EXTERNAL_ID);
        patientRole.getId().add(idRootPatient);

        final POCDMT000040Patient patient = new POCDMT000040Patient();
        final PN pn = new PN();
        final EnGiven enGiven = new EnGiven();
        enGiven.getContent().add(/*client.getFirstName()*/PATIENT_NAME);
        JAXBElement<EnGiven> givenJAXBElement = f.createENGiven(enGiven);
        pn.getContent().add(givenJAXBElement);

        final EnGiven enGiven2 = new EnGiven();
        enGiven2.getContent().add(/*client.getPatrName()*/PATIENT_PATNAME);
        JAXBElement<EnGiven> givenJAXBElement2 = f.createENGiven(enGiven2);
        givenJAXBElement2.setValue(enGiven2);
        pn.getContent().add(givenJAXBElement2);

        final EnFamily enFamily = f.createEnFamily();
        enFamily.getContent().add(/*client.getLastName()*/PATIENT_FAMILY);
        JAXBElement<EnFamily> enFamilyJAXBElement = f.createENFamily(enFamily);
        pn.getContent().add(enFamilyJAXBElement);
        patient.getName().add(pn);


        final CE administrativeGenderCode = new CE();
        administrativeGenderCode.setCode("M");
        administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");
        patient.setAdministrativeGenderCode(administrativeGenderCode);

        final TS birthTime = new TS();
        SimpleDateFormat sdfBirthday = new SimpleDateFormat("yyyyMMdd");
        birthTime.setValue(sdfBirthday.format(/*client.getBirthDate()*/new Date()));
        patient.setBirthTime(birthTime);

        patientRole.setPatient(patient);
        recordTarget.setPatientRole(patientRole);
        clinicalDocument.getRecordTarget().add(recordTarget);

        // --- author
        final POCDMT000040Author author = new POCDMT000040Author();
        final TS time = new TS();
        time.setValue(sdf.format(new Date())); //todo
        author.setTime(time);

        final POCDMT000040AssignedAuthor assignedAuthor = new POCDMT000040AssignedAuthor();
        final II idRootAuthor = new II();
        idRootAuthor.setRoot(UUID.randomUUID().toString()); //todo
        assignedAuthor.getId().add(idRootAuthor);

        final POCDMT000040Person assignedPerson = new POCDMT000040Person();
        final PN authorPerson = new PN();

        final EnGiven enGivenAuthor = new EnGiven();
        enGivenAuthor.getContent().add(/*client.getFirstName()*/ AUTHOR_NAME);  // todo
        JAXBElement<EnGiven> givenJAXBElementAuthor = f.createENGiven(enGivenAuthor);
        authorPerson.getContent().add(givenJAXBElementAuthor);

        final EnGiven enGivenAuthor2 = new EnGiven();
        enGivenAuthor2.getContent().add(/*client.getPatrName()*/ AUTHOR_PATNAME);     //todo
        JAXBElement<EnGiven> givenJAXBElementAuthor2 = f.createENGiven(enGivenAuthor2);
        givenJAXBElement2.setValue(enGivenAuthor2);
        authorPerson.getContent().add(givenJAXBElementAuthor2);

        final EnFamily enFamilyAuthor = f.createEnFamily();
        enFamilyAuthor.getContent().add(/*client.getLastName()*/ AUTHOR_FAMILY);   //todo
        JAXBElement<EnFamily> enFamilyJAXBElementAuthor = f.createENFamily(enFamilyAuthor);
        authorPerson.getContent().add(enFamilyJAXBElementAuthor);

        assignedPerson.getName().add(authorPerson);
        assignedAuthor.setAssignedPerson(assignedPerson);
        author.setAssignedAuthor(assignedAuthor);
        clinicalDocument.getAuthor().add(author);


        // --- custodian
        final POCDMT000040Custodian custodian = new POCDMT000040Custodian();

        final POCDMT000040AssignedCustodian assignedCustodian = new POCDMT000040AssignedCustodian();

        final POCDMT000040CustodianOrganization representedCustodianOrganization = new POCDMT000040CustodianOrganization();
        final II idRootCustodian = new II();
        idRootCustodian.setRoot(UUID.randomUUID().toString());

        final ON name = new ON();
        name.getContent().add(ORG_NAME); //todo
        representedCustodianOrganization.setName(name);
        representedCustodianOrganization.getId().add(idRootCustodian);
        assignedCustodian.setRepresentedCustodianOrganization(representedCustodianOrganization);
        custodian.setAssignedCustodian(assignedCustodian);
        clinicalDocument.setCustodian(custodian);


        // -- componentOf
        final POCDMT000040Component1 componentOf = new POCDMT000040Component1();
        final POCDMT000040EncompassingEncounter encompassingEncounter = new POCDMT000040EncompassingEncounter();

        final II idRootEncounter = new II();
        idRootEncounter.setRoot(UUID.randomUUID().toString()); //todo
        idRootEncounter.setExtension(EXTERNAL_ID);  //todo
        encompassingEncounter.getId().add(idRootEncounter);

        final CE code = new CE();
        code.setCode("IMP");
        code.setCodeSystem("2.16.840.1.113883.5.4");
        code.setCodeSystemName("actCode");
        code.setDisplayName("Inpatient encounter");
        encompassingEncounter.setCode(code);

        final IVLTS value = new IVLTS();
        value.setNullFlavor(NullFlavor.NI);
        encompassingEncounter.setEffectiveTime(value);

        componentOf.setEncompassingEncounter(encompassingEncounter);
        clinicalDocument.setComponentOf(componentOf);

        // --- component
        final POCDMT000040Component2 component = new POCDMT000040Component2();

        final POCDMT000040StructuredBody structuredBody = new POCDMT000040StructuredBody();
        final POCDMT000040Component3 component3 = new POCDMT000040Component3();
        final POCDMT000040Section section = new POCDMT000040Section();
        final StrucDocText text = f.createStrucDocText();
        text.getContent().add("Take captopril 25mg PO every 12 hours, starting on Jan 01, 2002, ending on Feb 01, 2002");
        section.setText(text);


        final POCDMT000040Entry entry = new POCDMT000040Entry();
        //----------------
        final POCDMT000040SubstanceAdministration substanceAdministration = new POCDMT000040SubstanceAdministration();
        substanceAdministration.setClassCode(ActClass.SBADM);
        substanceAdministration.setMoodCode(XDocumentSubstanceMood.EVN);
        final II idRoot2 = new II();
        idRoot2.setRoot(UUID.randomUUID().toString());
        substanceAdministration.getId().add(idRoot2);

        final II idRootEx = new II();
        idRootEx.setExtension("OMC");
        substanceAdministration.getId().add(idRootEx);


        final IVLTS ivlts = new IVLTS();
        final IVXBTS low = new IVXBTS();
        low.setValue("20121010");  // todo
        ivlts.setLow(low);

        final IVXBTS high = new IVXBTS();
        high.setValue("20121020"); //todo
        ivlts.setHigh(high);
        substanceAdministration.getEffectiveTime().add(ivlts);


        final PIVLTS pivlts = new PIVLTS();
        pivlts.setOperator(SetOperator.A);
        final PQ period = new PQ();
        period.setValue("12"); // todo
        period.setUnit("h");
        pivlts.setPeriod(period);
        substanceAdministration.getEffectiveTime().add(pivlts);


        final CE priorityCode = new CE();
        priorityCode.setCode("R");
        priorityCode.setCodeSystem("2.16.840.1.113883.5.7");
        priorityCode.setCodeSystemName("ActPriority");
        priorityCode.setDisplayName("Планово");
        substanceAdministration.setPriorityCode(priorityCode);


        final CE routeCode = new CE();
        routeCode.setCode("IV");
        routeCode.setCodeSystem("2.16.840.1.113883.5.112");
        routeCode.setCodeSystemName("RouteOfAdministration");
        substanceAdministration.setRouteCode(routeCode);


        final IVLPQ doseQuantity = new IVLPQ();
        final PQ center = new PQ();
        center.setUnit("mg"); //todo
        center.setValue("25");
        final PQR pqr = new PQR();
        pqr.setCodeSystemName("RLS");
        final ED originalText = new ED();
        originalText.getContent().add("мг"); //todo
        pqr.setOriginalText(originalText);
        center.getTranslation().add(pqr);
        doseQuantity.setCenter(center);
        substanceAdministration.setDoseQuantity(doseQuantity);


        // -consumable
        final POCDMT000040Consumable consumable = new POCDMT000040Consumable();
        final POCDMT000040ManufacturedProduct manufacturedProduct = new POCDMT000040ManufacturedProduct();
        final POCDMT000040LabeledDrug manufacturedLabeledDrug = new POCDMT000040LabeledDrug();

        final CE code1 = new CE();
        code1.setCodeSystem("1.2.643.2.0");
        code1.setCodeSystemName("RLS");


        final CD cd = new CD();
        final CD cdTrans = new CD();
        cdTrans.setCode("Анальгин");
        cdTrans.setDisplayName("Анальгин");
        cdTrans.setCodeSystemName("RLS_ACTMATTERS");
        cd.getTranslation().add(cdTrans);
        code1.getTranslation().add(cd);


        final CD cdTrans2 = new CD();

        cdTrans2.setCode("р-р");
        cdTrans2.setDisplayName("р-р");
        cdTrans2.setCodeSystemName("RLS_CLSDRUGFORMS");

        final CR cr = new CR();
        final CV cv = new CV();
        cv.setCode("DFMASS");
        cv.setCodeSystemName("RLS");
        cr.setName(cv);

        final CD cdValue = new CD();
        cdValue.setCode("мл");
        cdValue.setDisplayName("мл");
        cdValue.setCodeSystemName("RLS_MASSUNITS");
        final ED originalText1 = new ED();
        originalText1.getContent().add("5");
        cdValue.setOriginalText(originalText1);
        cr.setValue(cdValue);

        cdTrans2.getQualifier().add(cr) ;
        code1.getTranslation().add(cdTrans2);

        manufacturedLabeledDrug.setCode(code1);


        manufacturedProduct.setManufacturedLabeledDrug(manufacturedLabeledDrug);
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

    private void demarshalling(MCCIIN000002UV01 response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.hl7.v3");
            final Marshaller marshaller = jaxbContext.createMarshaller();
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(response, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
