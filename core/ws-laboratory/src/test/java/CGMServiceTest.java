import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        16.07.13, 23:49 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class CGMServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CGMServiceTest.class);


    public static void main(String[] args) throws DatatypeConfigurationException {

//        final CGMSOAP cgmsoap = new CGMSOAP();
//        final ObjectFactory obj = new ObjectFactory();
//        final HL7Document hl7Document = obj.createHL7Document();
//
//        hl7Document.setId("2222");
//        hl7Document.setCode("11122");
//        hl7Document.setSetID("333311122");
//        hl7Document.setTitle("title");
//        hl7Document.setTypeId("typeid");
//        hl7Document.setVersionNumber(111);
//        hl7Document.setConfidentialityCode("3333");
//
//        final ComponentInfo componentInfo = obj.createComponentInfo();
//        hl7Document.setComponent(componentInfo);
//
//        final ComponentOfInfo componentOfInfo = obj.createComponentOfInfo();
//        hl7Document.setComponentOf(componentOfInfo);
//
//        final CustodianInfo custodianInfo = obj.createCustodianInfo();
//        hl7Document.setCustodian(custodianInfo);
//
//        final RecordTargetInfo recordTargetInfo = obj.createRecordTargetInfo();
//        hl7Document.setRecordTarget(recordTargetInfo);
//
//        final GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
//        final XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
//        hl7Document.setEffectiveTime(xgcal);
//
//
//        final AuthorInfo authorInfo = obj.createAuthorInfo();
//        final AssignedAuthorInfo assignedAuthorInfo = obj.createAssignedAuthorInfo();
//        assignedAuthorInfo.setId(1);
//        final AssignedPersonInfo assignedPersonInfo = obj.createAssignedPersonInfo();
//
//        final JAXBElement<String> family = obj.createAssignedPersonInfoGiven("Family");
//        final JAXBElement<String> name = obj.createAssignedPersonInfoGiven("Name");
//        final JAXBElement<String> patr = obj.createAssignedPersonInfoGiven("Patr");
//
//        assignedPersonInfo.getContent().add(family);
//        assignedPersonInfo.getContent().add(name);
//        assignedPersonInfo.getContent().add(patr);
//
//
//        assignedAuthorInfo.setAssignedPerson(assignedPersonInfo);
//        authorInfo.setAssignedAuthor(assignedAuthorInfo);
//        hl7Document.setAuthor(authorInfo);
//
//        final PatientInfo patientInfo = obj.createPatientInfo();
//
//        System.out.println("start = " + marshallMessage(hl7Document, "ru.cgm"));
//
//        final int result = cgmsoap.getCgmsoapPortType().queryAnalysis(hl7Document);
//
//        System.out.println("result = " + result);

    }


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
